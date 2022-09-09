<template>
    <div>
        <div class="serach">
            <el-form v-model="formData" :inline="true">
                <el-form-item label="文章ID">
                    <el-input @input="forceUpdate" type="text" v-model="formData.id" placeholder="请输入文章ID" clearable />
                </el-form-item>

                <el-form-item label="博主ID">
                    <el-input @input="forceUpdate" type="text" v-model="formData.accountId" placeholder="请输入博主ID"
                        clearable />
                </el-form-item>
                <el-form-item label="文章标题">
                    <el-input @input="forceUpdate" type="text" v-model="formData.title" placeholder="请输入文章标题"
                        clearable />
                </el-form-item>
                <el-form-item class="btn">
                    <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div style="float: left;">
            <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain
                :disabled="multipleSection.length === 0">批量删除</el-button>

        </div>
        <!-- Main -->
        <CommonTable @handleCurrentChange="selectObjectList" @handleSizeChange="selectObjectList"
            @sendSelectIds="getSelectIds" :pageInfoProps="pageInfo" :columnsProps="columns">
            <template #action="scope">
                <el-button-group>
                    <el-tooltip class="item" :hide-after="800" effect="dark" content="修改文章" placement="top">
                        <el-button type="primary" size="small" @click="updateObjectInfo(scope.row)">
                            <i class="el-icon-edit"></i>
                        </el-button>
                    </el-tooltip>
                    <el-tooltip class="item" :hide-after="800" effect="dark" content="删除文章" placement="bottom">
                        <el-button type="danger" size="small" @click="deleteObject(scope.row)">
                            <i class="el-icon-delete"></i>
                        </el-button>
                    </el-tooltip>
                </el-button-group>
            </template>
        </CommonTable>
    </div>
</template>

<script>
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'

export default {

    name: "ArticleList",
    components: {
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
                id: '',
                accountId: '',
                title: ''
            },
            objectInfo: {
                id: 0,
                accountId: '',
                title: '',
                summary: '',
                content: '',
                categoryId: '',
                status: '',
                commentOpen: '',
                appreciation: '',
                recommend: '',
                reprint: '',
                reprintUrl: '',
                blogImg: '',
                views: '',
                commentNum: '',
                likes: '',
                createTime: '',
                lastUpdate: '',

            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
            columns: [{
                prop: 'id',
                label: 'ID'
            },
            {
                prop: 'accountId',
                label: '博主ID'
            },
            {
                prop: 'title',
                label: '标题'
            },
            {
                prop: 'summary',
                label: '摘要'
            },
            {
                prop: 'categoryId',
                label: '博客类型ID'
            },
            {
                prop: 'status',
                label: '状态'
            },
            {
                prop: 'commentOpen',
                label: '是否开启评论'
            },
            {
                prop: 'appreciation',
                label: '是否开启收藏'
            },

            {
                prop: 'recommend',
                label: '是否开启推荐'
            },
            {
                prop: 'reprint',
                label: '是否转载'
            },
            {
                prop: 'reprintUrl',
                label: '转载地址'
            },
            // {
            //     prop: 'blogImg',
            //     label: '博客封面图片路径'
            // },
            {
                prop: 'views',
                label: '点击数'
            },
            {
                prop: 'commentNum',
                label: '评论数'
            },
            {
                prop: 'likes',
                label: '点赞数'
            },
            {
                prop: 'createTime',
                label: '发表时间'
            },
            {
                prop: 'lastUpdate',
                label: '最后修改时间'
            },


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
            this.$get('/article/page', {
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
        searchSubmit() {
            this.selectObjectList()
        },

        //region 删除账号
        deleteObject(objectInfo) {
            MessageBox.confirm(`是否确认删除${objectInfo.title}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除账号...`,
                    target: '.el-main'
                })
                this.$delete('/article/delete/' + objectInfo.id).then(rs => {
                    Message.success(`删除${objectInfo.title}成功！`)
                    this.searchSubmit()
                }).finally(() => service.close())
            })

        },


        updateObjectInfo(e) {
            if (!e.id) {
                this.selectObjectList()
                Message.error('系统出错，请重新尝试！')
                return;
            }
            this.$router.push({
                path: '/edit',
                query: {
                    id: e.id
                }
            })
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
                var name = this.multipleSection[j].id;
                this.deleteIds.push(id);
                this.deleteUsernames.push(name);
            }
            //删除deleteIds中的数据
            let ids = this.deleteIds
            this.$delete('/category/delete?' + ids.map(id => 'ids=' + id).join('&')).then(rs => {
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
