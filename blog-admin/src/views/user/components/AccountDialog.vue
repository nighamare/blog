<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog',false)">
    <el-form :model="accountInfoForm">
        <el-form-item label="用户名">
            <el-input @input="forceUpdate" type="text" v-model="accountInfoForm.username" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="密码">
            <el-input @input="forceUpdate" type="password" v-model="accountInfoForm.password" placeholder="请输入密码,不输入则不修改密码" clearable />
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="accountInfoForm.status" placeholder="请选择状态" clearable>
                <el-option :value="0" label="正常" />
                <el-option :value="1" label="禁用" />
            </el-select>
        </el-form-item>
        <el-form-item label="用户类型">
            <el-select v-model="accountInfoForm.accountType" placeholder="请选择用户类型" clearable>
                <el-option :value="0" label="用户" />
                <el-option :value="1" label="管理员" />
            </el-select>
        </el-form-item>

    </el-form>
    <template #footer>
        <el-button v-if="accountInfoForm && accountInfoForm.id&&accountInfoForm.id>0" @click="$emit('success','update')">修改</el-button>
        <el-button v-else @click="$emit('success','add')">新增</el-button>
    </template>
</el-dialog>
</template>

<script>
export default {
    name: "AccountDialog",
    props: {
        centerDialogVisible: {
            type: Boolean,
            default: false
        },

        accountInfo: {
            id: 0,
            username: '',
            password: '',
            accountType: '',
            status: 0
        }
    },
    data() {
        return {
            accountInfoForm: '',
            accountDialogVisible: ''
        }
    },
    methods: {
        forceUpdate() {
            this.$forceUpdate()
        },
        // handleClose(done) {
        //     this.$confirm('确认关闭？')
        //         .then(_ => {
        //             done();
        //         })
        //         .catch(_ => {});
        // }
    },
    mounted() {
        this.accountInfoForm = this.accountInfo
        this.accountDialogVisible = this.centerDialogVisible
    }

}
</script>

<style scoped>

</style>
