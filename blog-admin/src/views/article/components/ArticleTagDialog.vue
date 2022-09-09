<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog',false)">
    <el-form :model="objectInfoForm">

        <el-form-item label="车站名字">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.stationName" placeholder="请输入用户名" clearable />
        </el-form-item>

        <el-form-item label="省份">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.province" placeholder="请输入身份证号码" clearable />
        </el-form-item>
        <el-form-item label="城市">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.city" placeholder="请输入昵称" clearable />
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="objectInfoForm.status" placeholder="请选择用户类型" clearable>
                <el-option :value="0" label="启用" />
                <el-option :value="1" label="禁用" />
            </el-select>
        </el-form-item>

        <el-form-item label="站台数量">
            <el-input @input="forceUpdate" type="text" v-model.number="objectInfoForm.platformNum" placeholder="请输入电话" clearable />
        </el-form-item>

    </el-form>
    <template #footer>
        <el-button v-if="objectInfoForm && objectInfoForm.id&&objectInfoForm.id>0" @click="$emit('success','update')">修改</el-button>
        <el-button v-else @click="$emit('success','add')">新增</el-button>
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
            id: 0,
            stationName: '',
            province: '',
            city: '',
            status: '',
            platformNum: '',
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
