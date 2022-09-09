<template>
    <el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog', false)">

        <template #title>
            <h1 style="margin-bottom: 0;" v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0"
                @click="$emit('success', 'update')">修改</h1>
            <h1 style="margin-bottom: 0;" v-else @click="$emit('success', 'add')">新增</h1>
        </template>
        <el-form :model="objectInfoForm">
            <el-form-item label="留言者ID">
                <el-input @input="forceUpdate" readonly="readonly" type="text" v-model="objectInfoForm.accountId" 
                    clearable />
            </el-form-item>
            <el-form-item label="留言内容">
                <el-input @input="forceUpdate" type="textarea" v-model="objectInfoForm.content" placeholder="请输入留言内容"
                    clearable />
            </el-form-item>
            <el-form-item label="留言状态">
            <el-select v-model="objectInfoForm.status" placeholder="请选择留言状态" clearable>
                <el-option :value="0" label="正常" />
                <el-option :value="1" label="禁用" />
            </el-select>
        </el-form-item>
        </el-form>
        <template #footer>
            <el-button v-if="objectInfoForm && objectInfoForm.id && objectInfoForm.id > 0"
                @click="$emit('success', 'update')">修改</el-button>
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
