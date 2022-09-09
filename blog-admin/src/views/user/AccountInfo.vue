<template>
<div>
    <div class="serach">
        <el-form v-model="formData" :inline="true">

            <el-form-item label="账户ID">
                <el-input @input="forceUpdate" type="text" v-model="formData.accountId" placeholder="请输入账户ID" clearable />
            </el-form-item>
            <el-form-item label="昵称">
                <el-input @input="forceUpdate" type="text" v-model="formData.nickname" placeholder="请输入昵称" clearable />
            </el-form-item>
            <el-form-item label="电话">
                <el-input @input="forceUpdate" type="text" v-model="formData.phoneNum" placeholder="请输入电话" clearable />
            </el-form-item>
            <el-form-item label="邮件">
                <el-input @input="forceUpdate" type="text" v-model="formData.email" placeholder="请输入邮件地址" clearable />
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
        <template #avatar="scope">
            <el-avatar v-if="scope.row.avatar" :size="100" :src="scope.row.avatar"></el-avatar>
            <el-avatar v-else :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
        </template>
        <template #sex="scope">
            <el-tag v-if="scope.row.sex==0" type="success">
                女
            </el-tag>
            <el-tag v-else type="primary">
                男
            </el-tag>
        </template>

        <template #action="scope">
            <el-button-group>
                <el-tooltip class="item" :hide-after="800" effect="dark" content="更新用户信息" placement="top">
                    <el-button type="primary" size="small" @click="updateObjectInfo(scope.row)">
                        <i class="el-icon-edit"></i>
                    </el-button>
                </el-tooltip>
                <el-tooltip class="item" :hide-after="800" effect="dark" content="修改头像" placement="top">
                    <el-button type="warning" size="small" @click="changeAvatar(scope.row)">
                        <i class="el-icon-edit"></i>
                    </el-button>
                </el-tooltip>

            </el-button-group>
        </template>
    </CommonTable>
    <avatarCropper :dialogVisible.sync="dialogVisible" @uploadAvatarDialog="uploadAvatarDialog"></avatarCropper>
    <AccountInfoDialog :objectInfo="objectInfo" :centerDialogVisible.sync="centerDialogVisible" @closeDialog="updateCenterDialogVisible" @success="addOrUpdateObject" />
</div>
</template>

<script>
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'
import AccountInfoDialog from './components/AccountInfoDialog.vue'
import avatarCropper from './components/AvatarCorpper.vue'

export default {

    name: "AccountInfo",
    components: {
        AccountInfoDialog,
        avatarCropper
    },
    data() {
        return {
            accountInfo: {},
            dialogVisible: false,
            centerDialogVisible: false,
            multipleSection: [],
            deleteUsernames: [],
            //删除记录的code
            deleteIds: [],
            formData: {
                accountId: '',
                nickname: '',
                phoneNum: '',
                email: ''
            },
            objectInfo: {
                accountId: '',
                nickname: '',
                sex: '',
                phoneNum: '',
                email: '',
                address: '',
                personalSignature: '',

            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
            columns: [{
                    prop: 'avatar',
                    label: '头像'
                },
                {
                    prop: 'accountId',
                    label: 'ID'
                },
                {
                    prop: 'nickname',
                    label: '昵称'
                },
                {
                    prop: 'sex',
                    label: '性别'
                },
                {
                    prop: 'phoneNum',
                    label: '电话号码'
                },
                {
                    prop: 'email',
                    label: '邮箱'
                },

                {
                    prop: 'address',
                    label: '地址'
                },
                {
                    prop: 'personalSignature',
                    label: '个人简介'
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
            this.$get('/account/infolist', {
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

        searchSubmit() {
            this.selectObjectList()
        },

        //endregion
        addOrUpdateObject(type) {
            this.centerDialogVisible = false
            const actionType = {
                update: '修改',
                add: '新增'
            }
            const service = Loading.service({
                text: `正在${actionType[type]}账号${this.objectInfo.nickname},请稍等...`,
                target: '.el-main'
            })
            if (type === 'add') {
                this.$post('', this.objectInfo).then(rs => {
                    Message.success('新增成功')
                    this.searchSubmit()
                }).finally(() => service.close())

            } else {
                this.$put('/account/updateainfo', this.objectInfo).then(rs => {
                    Message.success('修改成功')
                    this.searchSubmit()
                }).finally(() => service.close())
            }

        },
        updateObjectInfo(e) {
            this.objectInfo.accountId = e.accountId;
            this.objectInfo.nickname = e.nickname;
            this.objectInfo.sex = e.sex;
            this.objectInfo.phoneNum = e.phoneNum;
            this.objectInfo.email = e.email;
            this.objectInfo.personalSignature = e.personalSignature;
            this.objectInfo.address = e.address;
            this.centerDialogVisible = true
        },
        addObjectInfo() {
            this.objectInfo.accountId = '';
            this.objectInfo.nickname = '';
            this.objectInfo.sex = '';
            this.objectInfo.phoneNum = '';
            this.objectInfo.email = '';
            this.objectInfo.personalSignature = '';
            this.objectInfo.address = '';
            this.centerDialogVisible = true
        },
        getSelectIds(val) {

            this.multipleSection = val
        },
        //批量删除选中数据方法
        async batchDeleteBuild() {
            //判断是否选择了数据

            if (this.multipleSection.length == 0) {
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
            if (confirmDelete !== "confirm") {
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
        changeAvatar(e) {
            this.dialogVisible = true;
            this.accountInfo = e
        },
        uploadAvatarDialog(data) {

            let str = this.base64toFile(data, 'file')
            if (!this.accountInfo.accountId) {
                this.$router.push({
                    path: '/login',
                })
                Message({
                    message: '请重新登录！',
                    type: 'warning'
                })
                return;
            }
            let formData = new FormData();
            formData.append('file', str)
            this.$upload('/account/updateAvatar/' + this.accountInfo.accountId,
                formData
            ).then(res => {
                this.$message.success('上传成功！')
                this.searchSubmit()
            })
            this.dialogVisible = false
        },
        //data:base64图片格式字符串
        //filename：文件名称
        base64toFile(data, fileName) {
            const dataArr = data.split(",");
            const byteString = atob(dataArr[1]);
            const options = {
                type: "image/jpeg",
                endings: "native"
            };
            const u8Arr = new Uint8Array(byteString.length);
            for (let i = 0; i < byteString.length; i++) {
                u8Arr[i] = byteString.charCodeAt(i);
            }
            return new File([u8Arr], fileName + ".jpg", options); //返回文件流
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
