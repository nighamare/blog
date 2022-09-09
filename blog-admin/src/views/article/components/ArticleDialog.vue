<template>
<el-dialog :visible="centerDialogVisible" @close="$emit('closeDialog',false)">
    <el-form :model="objectInfoForm">

        <el-form-item label="车次">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.trainId" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="车站ID">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.stationId" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="下一站ID">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.nextStationId" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="到下一站的距离">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.distance" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="检票口">
            <el-input @input="forceUpdate" type="text" v-model="objectInfoForm.wicket" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="站台">
            <el-input @input="forceUpdate" type="text" v-model.number="objectInfoForm.platform" placeholder="请输入用户名" clearable />
        </el-form-item>

        <el-form-item label="状态">
            <el-select v-model="objectInfoForm.status" placeholder="请选择用户类型" clearable>
                <el-option :value="0" label="正常" />
                <el-option :value="1" label="停运" />
            </el-select>
        </el-form-item>
        <el-form-item label="出发时间">
            <el-time-picker v-model="objectInfoForm.leaveTime" value-format="HH:mm:ss" :picker-options="{
                selectableRange: '00:00:00 - 23:59:59',
            }" placeholder="选择出发时间">
            </el-time-picker>
        </el-form-item>
        <el-form-item label="到达时间">
            <el-time-picker v-model="objectInfoForm.arriveTime" value-format="HH:mm:ss" :picker-options="{
                selectableRange: '00:00:00 - 23:59:59',
            }" placeholder="选择到达时间">
            </el-time-picker>
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
            trainId: '',
            stationId: '',
            nextStationId: '',
            distance: '',
            wicket: '',
            platform: '',
            status: '',
            arriveTime: '',
            leaveTime: '',

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
