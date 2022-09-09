<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog', false)">

    <template #title>
        <h1 style="margin-bottom: 0;" v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0" @click="$emit('success', 'update')">修改</h1>
        <h1 style="margin-bottom: 0;" v-else @click="$emit('success', 'add')">新增</h1>
    </template>
    <el-form :model="objectInfoForm">
        <el-form-item label="评论ID">
            <el-input @input="forceUpdate" readonly="readonly" type="text" v-model="objectInfoForm.id" placeholder="请输入分类名字" clearable />
        </el-form-item>
        <el-form-item label="评论内容">
            <el-input @input="forceUpdate" type="textarea" v-model="objectInfoForm.content" placeholder="请输入分类描述" clearable />
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
    name: "CommentDialog",
    props: {
        centerDialogVisible: {
            type: Boolean,
            default: false
        },

        objectInfo: {
            id: '',
            articleId:'',
            rootCommentId: '',
            accountId: '',
            nickname: '',
            avatar: '',
            replyCommentAccountId: '',
            replyNickname: '',
            replyCommentId: '',
            commentLevel: '',
            content: '',
            articleId:'',
            praiseNum: '',
            createTime: '',
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
