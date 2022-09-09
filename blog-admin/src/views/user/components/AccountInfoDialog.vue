<template>
    <el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog', false)">
        <el-form :model="objectInfoForm">
            <el-form-item label="账户ID" v-if="objectInfoForm.accountId == ''">
                <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.accountId" placeholder="请输入账户ID"
                    clearable />
            </el-form-item>
            <el-form-item label="账户ID" v-else>
                <el-input readonly="readonly" @input="forceUpdate" type="text" v-model="objectInfoForm.accountId"
                    placeholder="请输入账户ID" clearable />
            </el-form-item>
            <el-form-item label="昵称">
                <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.nickname" placeholder="请输入用户名"
                    clearable />
            </el-form-item>
            <el-form-item label="性别">
                <el-select v-model="objectInfoForm.sex" placeholder="请选择性别" clearable>
                    <el-option :value="0" label="女" />
                    <el-option :value="1" label="男" />
                </el-select>
            </el-form-item>
            <el-form-item label="电话">
                <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.phoneNum" placeholder="请输入电话"
                    clearable />
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.email" placeholder="请输入邮箱"
                    clearable />
            </el-form-item>
            <el-form-item label="地址">
                <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.address" placeholder="请输入地址"
                    clearable />
            </el-form-item>
            <el-form-item label="个人简介">
               <el-input type="textarea" placeholder="请输入个人简介" v-model="objectInfoForm.personalSignature " clearable></el-input>  
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button v-if="objectInfoForm && objectInfoForm.accountId && objectInfoForm.accountId > 0"
                @click="$emit('success', 'update')">修改</el-button>
            <el-button v-else @click="$emit('success', 'add')">新增</el-button>
        </template>
    </el-dialog>
</template>

<script>
export default {
    name: "AddOrUpdateDialog",
    props: {
        centerDialogVisible: {
            type: Boolean,
            default: false
        },

        objectInfo: {
            accountId: 0,
            nickname: '',
            sex: '',
            phoneNum: '',
            email: '',
            address: '',
            personalSignature: '',

        }
    },
    data() {
        return {
            objectInfoForm: '',
            accountDialogVisible: ''
        }
    },
    methods: {
        forceUpdate() {
            this.$forceUpdate()
        },
    },
    mounted() {
        this.objectInfoForm = this.objectInfo
        this.accountDialogVisible = this.centerDialogVisible
    }

}
</script>

<style scoped>
</style>
