<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog', false)">

    <template #title>
        <h1 style="margin-bottom: 0;" v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0" @click="$emit('success', 'update')">修改</h1>
        <h1 style="margin-bottom: 0;" v-else @click="$emit('success', 'add')">新增</h1>
    </template>
    <el-form :model="objectInfoForm">
        <el-form-item label="站点名称" prop="linkName">
            <el-input v-model="objectInfoForm.linkName" placeholder="请输入站点名称"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址" prop="email">
            <el-input v-model="objectInfoForm.email" placeholder="请输入有效的邮箱地址"></el-input>
        </el-form-item>
        <el-form-item label="站点地址" prop="linkAddress">
            <el-input v-model="objectInfoForm.linkAddress" placeholder="https://或者http://"></el-input>
        </el-form-item>
        <el-form-item label="站点图片" prop="linkAvatar">
            <el-input v-model="objectInfoForm.linkAvatar" placeholder="https://或者http://+你的站点头像图片地址"></el-input>
        </el-form-item>
        <el-form-item label="站点描述" prop="linkIntro">
            <el-input v-model="objectInfoForm.linkIntro" placeholder="请一句话描述一下您的站点"></el-input>
        </el-form-item>
        <el-form-item label="友链状态">
            <el-select v-model="objectInfoForm.status" placeholder="请选择留言状态" clearable>
                <el-option :value="0" label="正常" />
                <el-option :value="1" label="禁用" />
            </el-select>
        </el-form-item>
    </el-form>
    <template #footer>
        <el-button v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0" @click="$emit('success', 'update')">修改</el-button>
        <el-button v-else @click="$emit('success', 'add')">新增</el-button>
    </template>
</el-dialog>
</template>

<script>
export default {
    name: "FriendshipChainDialog",
    props: {
        centerDialogVisible: {
            type: Boolean,
            default: false
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
        // handleClose(done) {
        //     this.$confirm('确认关闭？')
        //         .then(_ => {
        //             done();
        //         })
        //         .catch(_ => {});
        // }
    },
    mounted() {
        this.objectInfoForm = this.objectInfo
        this.accountDialogVisible = this.centerDialogVisible
    }

}
</script>

<style scoped>
</style>
