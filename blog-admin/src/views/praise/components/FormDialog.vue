<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog', false)">

    <template #title>
        <h1 style="margin-bottom: 0;" v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0" @click="$emit('success', 'update')">修改</h1>
        <h1 style="margin-bottom: 0;" v-else @click="$emit('success', 'add')">新增</h1>
    </template>
    <el-form :model="objectInfoForm">
        <el-form-item label="点赞者ID">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.accountId" placeholder="请输入点赞者ID" clearable />
        </el-form-item>
        <el-form-item label="被点赞ID">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.praiseForId" placeholder="请输入被点赞文章或评论的ID" clearable />
        </el-form-item>
        <el-form-item label="点赞类型">
            <el-select v-model="objectInfoForm.praiseType" placeholder="请选择点赞类型" clearable>
                <el-option :value="0" label="文章" />
                <el-option :value="1" label="评论" />
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
    name: "MessageDialog",
    props: {
        centerDialogVisible: {
            type: Boolean,
            default: false
        },

        objectInfo: {
            id: '',
            accountId: '',
            content: '',
            status: '',
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
