<template>
<div>
    <div class="serach">
        <el-form v-model="formData" :inline="true">

            <el-form-item label="友链名字">
                <el-input @input="forceUpdate" type="text" v-model="formData.linkName" placeholder="请输入友链名字" clearable />
            </el-form-item>

            <el-form-item class="btn">
                <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
            <el-form-item label="友链状态" style="margin-left: 200px">
                <el-select @change="onChange" style="width: 300px;" v-model="formData.status" placeholder="请选择友链状态" clearable>
                    <el-option value="" label="全部" />
                    <el-option :value="0" label="正常" />
                    <el-option :value="1" label="禁用" />
                    <el-option :value="2" label="待审核" />
                </el-select>
            </el-form-item>
        </el-form>
    </div>
    <div style="float: left;">
        <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain :disabled="multipleSection.length === 0">批量审核</el-button>

    </div>
    <!-- Main -->
    <CommonTable @handleCurrentChange="selectObjectList" @handleSizeChange="selectObjectList" @sendSelectIds="getSelectIds" :pageInfoProps="pageInfo" :columnsProps="columns">
        <template #content="scope">
            <div v-html="scope.row.content"></div>
        </template>
        <template #status="scope">
            <el-tag type="success" v-if="scope.row.status === 0">正常</el-tag>
            <el-tag type="info" v-else>删除</el-tag>
        </template>
        <template #createTime="scope">
            {{ scope.row.createTime | DateTimeFormat }}
        </template>

        <template #action="scope">
            <el-button-group>
                <el-tooltip class="item" :hide-after="800" effect="dark" content="预览文章" placement="top">
                    <el-button class="btn-action" type="primary" size="small" @click="previewFriendLink(scope.row)">
                        <i class="el-icon-edit"></i>预览
                    </el-button>
                </el-tooltip>
                <el-tooltip v-if="scope.row.status===2" class="item" :hide-after="800" effect="dark" content="审核通过" placement="bottom">
                    <el-button class="btn-action" type="success" size="small" @click="approvAudit(scope.row)">
                        <i class="el-icon-circle-check"></i>通过
                    </el-button>
                </el-tooltip>
                <el-tooltip v-if="scope.row.status===2" class="item" :hide-after="800" effect="dark" content="删除该链接" placement="bottom">
                    <el-button class="btn-action" type="danger" size="small" @click="deleteObject(scope.row)">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </el-tooltip>
            </el-button-group>
        </template>
    </CommonTable>
    <el-dialog title="预览友链" :visible.sync="dialogFormVisible">
        <div>
            <div style="text-align: center;" class="avatar-div">
                <div style="margin: auto;height: 100px; width: 100px; line-height: 100px; ">
                    <el-avatar :size="100" :src="objectInfo.linkAvatar"></el-avatar>
                    <div class="avatar-hover" ref="avatatHoverRef">
                        <img src="@/assets/static/images/pic.png" alt="">
                    </div>
                </div>
            </div>
            <el-descriptions class="margin-top" :column="2" border>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        ID
                    </template>
                    <span>{{ objectInfo.id }}</span>
                </el-descriptions-item>

                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        博客名字
                    </template>
                    <span>{{ objectInfo.linkName }}</span>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        状态
                    </template>
                    <el-tag size="small" v-if="objectInfo.status  === 0">正常</el-tag>
                    <el-tag size="small" v-else-if="objectInfo.status=== 1">禁用</el-tag>
                    <el-tag size="small" v-else-if="objectInfo.status=== 2">代审核</el-tag>
                    <el-tag size="small" v-else>未知</el-tag>
                </el-descriptions-item>

                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        邮箱
                    </template>
                    <span>{{ objectInfo.email }}</span>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        地址
                    </template>
                    <span>{{ objectInfo.linkAddress }}</span>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        申请时间
                    </template>
                    <span style="font-size: 12px;">{{ objectInfo.createTime | DateTimeFormat }}</span>

                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        链接介绍
                    </template>
                    <span>{{ objectInfo.linkIntro }}</span>
                </el-descriptions-item>

                

            </el-descriptions>

        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">返 回</el-button>
            <el-button type="success" @click="passAudit">审核通过</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'

export default {

    name: "FriendshipChain",
    components: {},
    data() {
        return {
            dialogFormVisible: false,
            multipleSection: [],
            deleteUsernames: [],
            //删除记录的code
            deleteIds: [],
            formData: {
                linkName: '',
                status: 2,
            },
            objectInfo: {
                id: '',
                linkName: '',
                email: '',
                status: '',
                linkAvatar: '',
                linkAddress: '',
                linkIntro: '',
                createTime: ''

            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
            columns: [{
                    prop: 'id',
                    label: 'ID'
                },
                {
                    prop: 'linkName',
                    label: '博客名字'
                },
                {
                    prop: 'email',
                    label: '邮箱'
                },
                {
                    prop: 'status',
                    label: '状态'
                },
                {
                    prop: 'linkAvatar',
                    label: '链接头像'
                },
                {
                    prop: 'linkAvatar',
                    label: '链接头像'
                },
                {
                    prop: 'linkAddress',
                    label: '链接地址'
                },
                {
                    prop: 'linkIntro',
                    label: '链接介绍'
                },
                {
                    prop: 'createTime',
                    label: '申请时间'
                },
                // {
                //     prop: 'action',
                //     label: '操作'
                // }
            ]

        }
    },
    methods: {
        deleteObject(objectInfo) {
            MessageBox.confirm(`是否确认删除${objectInfo.id}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除链接...`,
                    target: '.el-main'
                })
                this.$delete('/friend/delete/' + objectInfo.id).then(rs => {
                    Message.success(`删除${objectInfo.id}成功！`)
                    this.searchSubmit()
                }).finally(() => service.close())
            })

        },
        passAudit(){
            this.approvAudit(this.objectInfo)
            this.dialogFormVisible = false
        },
        previewFriendLink(e) {
            if (!e.id) {
                this.selectObjectList()
                Message.error('系统出错，请重新尝试！')
                return;
            }
            this.dialogFormVisible = true
            this.objectInfo = e
            // test
           
        },
        approvAudit(e){
            this.$put('/friend/passaudit/'+e.id).then(res=>{
                this.selectObjectList();
                Message({
                    type: "success",
                    message: '审核通过'
                })
            })
        },
        onChange() {
            this.selectObjectList()
        },
        selectObjectList(e) {
            var _this = this;
            if (e) {
                if (e.pageNum) {
                    _this.pageInfo.pageNum = e.pageNum
                }
                if (e.pageSize) {
                    this.pageInfo.pageSize = e.pageSize
                }
            }
            let formData = _this.formData
            let pageNum = _this.pageInfo.pageNum
            let pageSize = _this.pageInfo.pageSize
            this.$get('/friend/listbp', {
                ...formData,
                pageNum: pageNum,
                pageSize: pageSize
            }).then(rs => {
                _this.pageInfo = rs.data

            })
        },

        searchSubmit() {
            this.selectObjectList()
        },

        
        updateObjectInfo(e) {
            this.objectInfo = e;
            this.centerDialogVisible = true
        },

        getSelectIds(val) {

            this.multipleSection = val
        },
        //批量删除选中数据方法
        async batchDeleteBuild() {
            //判断是否选择了数据

            if (this.multipleSection.length === 0) {
                alert("请选择要审核的数据");
                return;
            }
            //如果有选中的数据，那么弹出消息框
            const confirmDelete = await this.$confirm(
                "此操作会审核通过文章信息，是否通过？",
                "提示", {
                    confimrButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
            ).catch(err => err);
            //如果用户确认删除，则返回字符串confirm
            //如果用户取消删除，则返回字符串cancel
            if (confirmDelete != "confirm") {
                return Message.info("已取消");
            }
            //将选中的数据推到deleteIds数组中
            for (var i = 0; i < this.multipleSection.length; i++) {
                var j = i;
                var id = this.multipleSection[j].id;
                var name = this.multipleSection[j].id;
                this.deleteIds.push(id);
                this.deleteUsernames.push(name);
            }
            //删除deleteIds中的数据
            let ids = this.deleteIds
            this.$put('/friend/allpass?' + ids.map(id => 'ids=' + id).join('&')).then(rs => {
                Message.success(`批量审核${this.deleteUsernames}成功！`)
                this.searchSubmit()
            }).then(rs => {
                this.deleteIds = [];
            })

        },
        forceUpdate() {
            this.$forceUpdate()
        },
    },
    mounted() {
        this.selectObjectList(this.pageInfo)

    },
    // 数字中间加*号 value是传的参数值(后端返回的，需要你处理的数据)
    filters: {
        changePwd(value) {
            const start = value.slice(0, 3)
            const end = value.slice(-3)
            return `${start}***${end}`
        }
    },
}
</script>

<style lang="less" scoped>
    :deep(.btn-action) {
    padding: 9px 10px;
}

.item {
    margin: 4px;
}
</style>
