<template>
<div>
    <div class="serach">
        <el-form v-model="formData" :inline="true">

            <el-form-item label="文章ID">
                <el-input @input="forceUpdate" type="text" v-model="formData.articleId" placeholder="请输入用户名" clearable />
            </el-form-item>
            <el-form-item label="标签名字">
                <el-input @input="forceUpdate" type="text" v-model="formData.tagName" placeholder="请输入用户名" clearable />
            </el-form-item>
            <el-form-item class="btn">
                <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div style="float: left;">
        <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain :disabled="multipleSection.length === 0">批量删除</el-button>
    </div>
    <!-- Main -->
    <CommonTable @handleCurrentChange="selectObjectList" @handleSizeChange="selectObjectList" @sendSelectIds="getSelectIds" :pageInfoProps="pageInfo" :columnsProps="columns">
        <template #action="scope">
            <!-- <el-button-group>
                <el-tooltip class="item" :hide-after="800" effect="dark" content="更新用户信息" placement="top">
                    <el-button type="primary" size="small" @click="updateObjectInfo(scope.row)">
                        <i class="el-icon-edit"></i>
                    </el-button>
                </el-tooltip>

                <el-tooltip class="item" :hide-after="800" effect="dark" content="删除该用户" placement="bottom">
                    <el-button type="danger" size="small" @click="deleteObject(scope.row)">
                        <i class="el-icon-delete"></i>
                    </el-button>
                </el-tooltip>
            </el-button-group> -->
            {{scope.$index}}暂不支持操作，请到文章修改处修改标签
        </template>
    </CommonTable>
    <ArticleTagDialog :objectInfo="objectInfo" :centerDialogVisible.sync="centerDialogVisible" @closeDialog="updateCenterDialogVisible" @success="addOrUpdateObject" />
</div>
</template>

<script>
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'
import ArticleTagDialog from './components/ArticleTagDialog.vue'

export default {

    name: "ArticleTagList",
    components: {
        ArticleTagDialog,
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
                articleId: '',
                tagName: '',
            },
            objectInfo: {
                id: 0,
                articleId: '',
                tagId: '',
                tagName: '',
                tagDesc: '',
            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
            columns: [{
                    prop: 'id',
                    label: 'ID'
                },
                {
                    prop: 'articleId',
                    label: '文章ID'
                },
                {
                    prop: 'tagId',
                    label: '标签ID'
                },
                {
                    prop: 'tagName',
                    label: '标签名字'
                },
                {
                    prop: 'tagDesc',
                    label: '标签描述'
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

            this.$get('/articleTag/artaglist', {
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
            MessageBox.confirm(`是否确认删除${objectInfo.name}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除账号...`,
                    target: '.el-main'
                })
                deleteByIds(objectInfo.id).then(rs => {
                    Message.success(`删除${objectInfo.name}成功！`)
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
                text: `正在${actionType[type]}账号${this.objectInfo.name},请稍等...`,
                target: '.el-main'
            })
            if (type === 'add') {
                insert(this.objectInfo).then(rs => {
                    Message.success('新增成功')
                    this.searchSubmit()
                }).finally(() => service.close())

            } else {
                update(this.objectInfo).then(rs => {
                    Message.success('修改成功')
                    this.searchSubmit()
                }).finally(() => service.close())
            }

        },
        updateObjectInfo(e) {
            this.objectInfo.id = e.id;
            this.objectInfo.stationName = e.stationName;
            this.objectInfo.province = e.province;
            this.objectInfo.city = e.city;
            this.objectInfo.status = e.status;
            this.objectInfo.platformNum = e.platformNum;
            this.centerDialogVisible = true
        },
        addObjectInfo() {
            this.objectInfo.id = '';
            this.objectInfo.stationName = '';
            this.objectInfo.province = '';
            this.objectInfo.city = '';
            this.objectInfo.status = '';
            this.objectInfo.platformNum = '';
            this.centerDialogVisible = true
            /* id: 0,
               stationName: '',
               province: '',
               city: '',
               status: '',
               platformNum: '', */
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
            deleteByIds(this.deleteIds).then(rs => {
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
