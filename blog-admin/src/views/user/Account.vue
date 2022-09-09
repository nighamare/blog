<template>
<div>
    <div class="serach">
        <el-form v-model="formData" :inline="true">
            <el-form-item label="用户名">
                <el-input v-model="formData.username" placeholder="请输入用户名" clearable />
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="formData.status" placeholder="请选择状态" clearable>
                    <el-option :value="0" label="正常" />
                    <el-option :value="1" label="禁用" />
                </el-select>
            </el-form-item>
            <el-form-item class="btn">
                <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div style="float: left;">
        <el-button @click="addAccountInfo">新增账户</el-button>
        <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain :disabled="multipleSection.length === 0">批量删除</el-button>
    </div>

    <el-table :data="pageInfo.list" style="width: 100%" @selection-change="handleSelectChange">
        <el-table-column type="selection" width="50">
        </el-table-column>
        <el-table-column type="expand">
            <template slot-scope="props">
                <div style="text-align: center;" class="avatar-div">
                    <div style="margin: auto;height: 100px; width: 100px; line-height: 100px; ">
                        <el-avatar :size="100" :src="props.row.avatar"></el-avatar>
                        <div class="avatar-hover" ref="avatatHoverRef">
                            <img src="@/assets/static/images/pic.png" alt="">
                        </div>
                    </div>
                </div>
                <el-descriptions class="margin-top" :column="2" border>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                        </template>
                        <span>{{ props.row.username }}</span>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            注册时间
                        </template>
                        <span style="font-size: 12px;">{{ props.row.createTime | DateTimeFormat }}</span>

                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            昵称
                        </template>
                        <span>{{ props.row.nickname }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            性别
                        </template>
                        <el-tag size="small" v-if="props.row.sex  === 0">女</el-tag>
                        <el-tag size="small" v-else-if="props.row.sex=== 1">男</el-tag>
                        <el-tag size="small" v-else>未知</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            电话号码
                        </template>
                        <span>{{ props.row.phoneNum }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            邮箱
                        </template>
                        <span>{{ props.row.email }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            地址
                        </template>
                        <span>{{ props.row.address }}</span>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            个人简介
                        </template>
                        {{ props.row.personalSignature }}
                    </el-descriptions-item>
                </el-descriptions>

            </template>
        </el-table-column>
        <el-table-column type="index" width="55" label="序号">
        </el-table-column>
        <el-table-column label="ID" prop="id" sortable>
        </el-table-column>
        <el-table-column label="用户名" prop="username" sortable>
        </el-table-column>
        <el-table-column label="密码" prop="password">
            <template slot-scope="scope">
                {{scope.row.password|changePwd}}
            </template>
        </el-table-column>
        <el-table-column label="状态" prop="status">
        </el-table-column>
        <el-table-column label="账号类型" prop="accountType">
        </el-table-column>
        <el-table-column label="更新时间" prop="lastUpdate" sortable width="100">
            <template slot-scope="scope">
                {{scope.row.lastUpdate|DateTimeFormat }}
            </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" sortable width="100">
            <template slot-scope="scope">
                {{scope.row.createTime|DateTimeFormat }}
            </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
            <template slot-scope="scope">
                <el-button-group>
                    <el-tooltip class="item" :hide-after="800" effect="dark" content="更新用户信息" placement="top">
                        <el-button type="primary" size="small" @click="updateAccountInfo(scope.row)">
                            <i class="el-icon-edit"></i>
                        </el-button>
                    </el-tooltip>
                    <el-tooltip class="item" :hide-after="800" effect="dark" content="删除该用户" placement="bottom">
                        <el-button type="danger" size="small" @click="deleteAccount(scope.row)">
                            <i class="el-icon-delete"></i>
                        </el-button>
                    </el-tooltip>
                </el-button-group>
            </template>
        </el-table-column>
    </el-table>
    <div>
        <el-pagination style="margin: auto; text-align: center;" :currentPage="pageInfo.pageNum" :page-sizes="[10,20,30,40,100]" :small="false" :disabled="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :page-size="pageInfo.pageSize" :total="pageInfo.total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
    <account-dialog :accountInfo="accountInfo" :centerDialogVisible.sync="centerDialogVisible" @closeDialog="updateCenterDialogVisible" @success="addOrUpdateAccount"></account-dialog>
</div>
</template>

<script>
import AccountDialog from './components/AccountDialog.vue';
import {
    MessageBox,
    Loading,
    Message
} from 'element-ui'
export default {
    name: 'Account',
    components: {
        AccountDialog,
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
                username: '',
                status: ''
            },
            accountInfo: {
                id: 0,
                username: '',
                password: '',
                accountType: '',
                status: 0

            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
        }
    },
    methods: {
        selectAccountList() {
            var _this = this;
            let formData = _this.formData
            let pageNum = _this.pageInfo.pageNum
            let pageSize = _this.pageInfo.pageSize
            this.$get('/account/findAll', {
                ...formData,
                pageNum: pageNum,
                pageSize: pageSize
            }).then(rs => {
                this.pageInfo = rs.data
            })
        },
        updateCenterDialogVisible(val) {
            this.centerDialogVisible = val
        },
        getIsCollapse(e) {
            this.isCollapse = e
        },

        searchSubmit() {
            this.selectAccountList()
        },

        //region 删除账号
        deleteAccount(accountInfo) {
            MessageBox.confirm(`是否确认删除${accountInfo.username}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除账号...`,
                    target: '.el-main'
                })
                this.$delete('/account/delete/' + accountInfo.id).then(rs => {
                    Message.success(`删除${accountInfo.username}成功！`)
                    this.searchSubmit()
                }).finally(() => service.close())
            })

        },

        //endregion
        addOrUpdateAccount(type) {
            this.centerDialogVisible = false
            const actionType = {
                update: '修改',
                add: '新增'
            }
            const service = Loading.service({
                text: `正在${actionType[type]}账号${this.accountInfo.username},请稍等...`,
                target: '.el-main'
            })
            if (type === 'add') {
                this.$post('/account/insert', this.accountInfo).then(rs => {
                    Message.success('新增成功')
                    this.searchSubmit()
                }).finally(() => service.close())

            } else {
                this.$put('/account/updateAccount', this.accountInfo).then(rs => {
                    Message.success('修改成功')
                    this.searchSubmit()
                }).finally(() => service.close())
            }

        },
        updateAccountInfo(e) {
            this.accountInfo.id = e.id;
            this.accountInfo.username = e.username;
            this.accountInfo.password = '';
            this.accountInfo.accountType = e.accountType;
            this.accountInfo.status = e.status;
            this.centerDialogVisible = true
        },
        addAccountInfo() {
            this.accountInfo.id = '';
            this.accountInfo.username = '';
            this.accountInfo.password = '';
            this.accountInfo.accountType = '';
            this.accountInfo.status = '';
            this.centerDialogVisible = true
        },
        getSelectIds(val) {

            this.multipleSection = val
 
        },
        handleSelectChange(sels) {
            this.getSelectIds(sels)
            
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
                var username = this.multipleSection[j].username;
                this.deleteIds.push(id);
                this.deleteUsernames.push(username);
            }
            //删除deleteIds中的数据
            let ids = this.deleteIds
            this.$delete('/account/deleteByIds?'+ids.map(id => 'ids=' + id).join('&')).then(rs => {
                Message.success(`批量${this.deleteUsernames}删除成功！`)
                this.searchSubmit()
            }).then(rs => {
                this.deleteIds = [];
            })

        },
        handleSizeChange(val) {
            this.pageInfo.pageSize = val
            this.selectAccountList()
        },
        handleCurrentChange(val) {
            this.pageInfo.pageNum = val
            this.selectAccountList()
        }

    },
    mounted() {
        this.selectAccountList(this.pageInfo)

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

<style>
.demo-table-expand {
    font-size: 0;
}

.demo-table-expand label {
    width: 90px;
    color: #99a9bf;
}

.demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
}
</style>
