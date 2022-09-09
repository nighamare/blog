<template>
    <div>
        <div class="serach">
            <el-form v-model="formData" :inline="true">

                <el-form-item label="友链名字">
                    <el-input @input="forceUpdate" type="text" v-model="formData.linkName" placeholder="请输入友链名字"
                        clearable />
                </el-form-item>

                <el-form-item label="友链状态">
                <el-select v-model="formData.status"  placeholder="请选择友链状态" clearable>
                    <el-option :value="0" label="正常" />
                    <el-option :value="1" label="禁用" />
                    <el-option :value="1" label="待审核" />
                </el-select>
            </el-form-item>
                <el-form-item class="btn">
                    <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div style="float: left;">
            <el-button type="primary" @click="addObjectInfo">新增友链</el-button>
            <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain
                :disabled="multipleSection.length === 0">批量删除</el-button>

        </div>
        <!-- Main -->
        <CommonTable @handleCurrentChange="selectObjectList" @handleSizeChange="selectObjectList"
            @sendSelectIds="getSelectIds" :pageInfoProps="pageInfo" :columnsProps="columns">
            <template #content="scope">
                <div v-html="scope.row.content"></div>
            </template>
            <template #status="scope">
                <el-tag type="success" v-if="scope.row.status===0">正常</el-tag>
                <el-tag type="info" v-else>删除</el-tag>
            </template>
            <template #createTime="scope">
               {{scope.row.createTime |DateTimeFormat}}
            </template>
            
            
            <template #action="scope">
                <el-button-group>
                    <el-tooltip class="item" :hide-after="800" effect="dark" content="更新分类信息" placement="top">
                        <el-button type="primary" size="small" @click="updateObjectInfo(scope.row)">
                            <i class="el-icon-edit"></i>
                        </el-button>
                    </el-tooltip>

                    <el-tooltip class="item" :hide-after="800" effect="dark" content="删除该分类" placement="bottom">
                        <el-button type="danger" size="small" @click="deleteObject(scope.row)">
                            <i class="el-icon-delete"></i>
                        </el-button>
                    </el-tooltip>
                </el-button-group>
            </template>
        </CommonTable>
        <FriendshipChainDialog :objectInfo="objectInfo" :centerDialogVisible.sync="centerDialogVisible"
            @closeDialog="updateCenterDialogVisible" @success="addOrUpdateObject" />
    </div>
</template>

<script>
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'
import FriendshipChainDialog from './components/FriendshipChainDialog.vue'

export default {

    name: "FriendshipChain",
    components: {
        FriendshipChainDialog,
    },
    data() {
        return {
            isCollapse: false,
            centerDialogVisible: false,
            multipleSection: [],
            deleteUsernames: [],
            //删除记录的code
            deleteIds: [],
            formData: {
                linkName: '',
                status: '',
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
            columns: [
                {
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
            this.$get('/friend/listbp',{
                ...formData,
                pageNum: pageNum,
                pageSize: pageSize
            }).then(rs => {
                _this.pageInfo = rs.data

            })
        },
        updateCenterDialogVisible(val) {
            this.centerDialogVisible = val
        },
        getIsCollapse(e) {
            this.isCollapse = e
        },
        deleteObject() {
            // 删除账号
        },
        searchSubmit() {
            this.selectObjectList()
        },
        // 点击查看密码
        view(index, row) {
            console.log("密码");
            this.viewForm = Object.assign({}, row); // 重置对象
            this.index_x = index;
            var viewData = this.index_x;
            console.log(index)
            console.log(row)

            /*  // console.log(this.tableData[viewData].password_d);
             console.log(this.pageInfo.list[viewData].password);
             if (this.pageInfo.list[viewData].password === "******") {
                 this.pageInfo.list[viewData].password = this.pageInfo.list[viewData].password;
             } else {
                 this.pageInfo.list[viewData].password = "******";
             } */
        },
        //region 删除账号
        deleteObject(objectInfo) {
            MessageBox.confirm(`是否确认删除${objectInfo.id}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除账号...`,
                    target: '.el-main'
                })
                this.$delete('/friend/delete',{ids:objectInfo.id}).then(rs => {
                    Message.success(`删除${objectInfo.id}成功！`)
                    this.searchSubmit()
                }).finally(() => service.close())
            })

        },

        //endregion
        addOrUpdateObject(type) {
            this.centerDialogVisible = false
            const actionType = {
                update: '修改',
                add: '新增'
            }
            const service = Loading.service({
                text: `正在${actionType[type]}账号${this.objectInfo.id},请稍等...`,
                target: '.el-main'
            })
            if (type === 'add') {
                this.$post('/friend/insert',this.objectInfo).then(rs => {
                    Message.success('新增成功')
                    this.searchSubmit()
                }).finally(() => service.close())

            } else {
                this.$put('/friend/update',this.objectInfo).then(rs => {
                    Message.success('修改成功')
                    this.searchSubmit()
                }).finally(() => service.close())
            }

        },
        updateObjectInfo(e) {
            this.objectInfo.id = e.id;
            this.objectInfo.linkName = e.linkName;
            this.objectInfo.email = e.email;
            this.objectInfo.status = e.status;
            this.objectInfo.linkAvatar = e.linkAvatar;
            this.objectInfo.linkAddress = e.linkAddress;
            this.objectInfo.linkIntro = e.linkIntro;
            this.centerDialogVisible = true
        },
        addObjectInfo() {
            this.objectInfo.id = '';
            this.objectInfo.linkName = '';
            this.objectInfo.email = '';
            this.objectInfo.status = '';
            this.objectInfo.linkAvatar = '';
            this.objectInfo.linkAddress = '';
            this.objectInfo.linkIntro = '';
            this.centerDialogVisible = true
        },
        getSelectIds(val) {

            this.multipleSection = val
        },
        //批量删除选中数据方法
        async batchDeleteBuild() {
            //判断是否选择了数据

            if (this.multipleSection.length === 0) {
                alert("请选择要删除的数据");
                return;
            }
            //如果有选中的数据，那么弹出消息框
            const confirmDelete = await this.$confirm(
                "此操作会永久删除账号信息，是否删除？",
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
                var name = this.multipleSection[j].name;
                this.deleteIds.push(id);
                this.deleteUsernames.push(name);
            }
            //删除deleteIds中的数据
            let ids = this.deleteIds
            this.$delete('/tag/delete?' + ids.map(id => 'ids=' + id).join('&')).then(rs => {
                Message.success(`批量${this.deleteUsernames}删除成功！`)
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

.item {
    margin: 4px;
}
</style>
