<template>
<div>
    <div class="header">
        <div class="warapper">
            <div class="logo">
                <el-link class="brand" :underline="false">
                    <span class="logo-line-before"><i style="transform: translateX(100%);"></i></span>
                    <span class="site-title" style="opacity: 1; top: 0px;">M.Lycoris</span>
                    <span class="logo-line-after"><i style="transform: translateX(-100%);"></i></span>
                </el-link>
            </div>
            <div class="search-div">

                <el-input placeholder="搜索我的博客" v-model="searchInput" class="search-input" @keyup.enter.native="searchSubmit">
                    <i slot="prefix" class="iconfont icon-fire"></i>
                    <el-button slot="append" icon="el-icon-search" @click="searchSubmit">搜索</el-button>
                </el-input>
            </div>
            <div class="header-menu">
                <el-menu :default-active="activeIndex" text-color="#222" active-text-color="#1989fa" class="el-menu-header" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="1"><i slot="prefix" class="iconfont icon-home"></i>首页</el-menu-item>
                    <el-menu-item index="2"><i slot="prefix" class="iconfont icon-tag"></i>标签</el-menu-item>
                    <el-menu-item index="3"><i slot="prefix" class="iconfont icon-catg"></i>分类</el-menu-item>
                    <el-menu-item index="4"><i slot="prefix" class="iconfont icon-liuyan"></i>留言板</el-menu-item>
                    <el-menu-item index="5"><i slot="prefix" class="iconfont icon-link"></i>友链</el-menu-item>
                    <el-menu-item index="6"><i slot="prefix" class="iconfont icon-lianxi"></i>联系站长</el-menu-item>
                    <el-menu-item index="7"><i slot="prefix" class="iconfont icon-about"></i>关于</el-menu-item>
                    <div style="float: left;">
                        <div class="login-register" v-if="!accountInfo">
                            <el-button size="small" type="primary" @click="goToLogin">登录 </el-button>
                            <el-button size="small" @click="goToRegister"> 注册</el-button>
                        </div>
                        <div class="user-info" v-else>
                            <el-dropdown trigger="click" @command="handleCommand" style="height: 60px;">
                                <span class="el-dropdown-link" style="line-height: 60px;height: 60px;">
                                    <el-avatar :size="40" :src="accountInfo.avatar">
                                        {{accountInfo.nickname}}</el-avatar>
                                    <i class="el-icon-caret-bottom el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="info">个人信息</el-dropdown-item>
                                    <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
                                    <el-dropdown-item command="logout">退出</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        <el-button class="btn-publish" icon="el-icon-edit" size="small" @click="goToEdit">发布文章</el-button>

                        </div>
                       
              

                    </div>
                    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" class="dialog-con">
                        <el-form :model="form" status-icon :rules="rules" ref="form" label-width="80px" class="demo-ruleForm">
                            <el-form-item label="密码" prop="oldPassword">
                                <el-input type="password" v-model="form.oldPassword" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码" prop="checkPass">
                                <el-input type="password" v-model="form.checkPass" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="submitChangePwd('form')">确 定</el-button>
                        </div>
                    </el-dialog>

                </el-menu>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {
    Message
} from 'element-ui';
export default {
    name: 'Header',
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.form.checkPass !== '') {
                    this.$refs.form.validateField('checkPass');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            searchInput: '',
            activeIndex: '1',
            accountInfo: {},
            dialogFormVisible: false,
            form: {

            },
            form: {
                oldPassword: '',
                password: '',
                checkPass: '',
            },
            rules: {
                oldPassword: [{
                    validator: validatePass,
                    trigger: 'blur'
                }],
                password: [{
                    validator: validatePass,
                    trigger: 'blur'
                }],
                checkPass: [{
                    validator: validatePass2,
                    trigger: 'blur'
                }],
            }
        };

    },
    methods: {
        goToEdit(){
            if(this.accountInfo){
                this.$router.push({
                    path: '/write'
                })
            }else{
                Message.error('请先登录！')
                this.$router.push({
                    path: '/login'
                })
            }
        },
        searchSubmit() {
            if (this.searchInput === '') {
                Message.error("请输入搜索关键词")
                return;
            }
            this.$router.push({
                path: '/search',
                query: {
                    search: this.searchInput
                }
            })
        },
        handleCommand(command) {
            if (command === 'logout') {
                this.logout();
                this.goToLogin();
                return;
            }
            if (command === 'info') {
                this.$router.push({
                    path: '/userInfo',

                });
                return;
            }
            if (command === 'pwd') {
                this.dialogFormVisible = true;

            }
        },
        handleSelect(key, keyPath) {

            if (key == 1) {
                this.$router.push({
                    path: '/'
                })
            } else if (key == 2) {
                this.$router.push({
                    path: '/tag'
                })
            } else if (key == 3) {
                this.$router.push({
                    path: '/category'
                })
            } else if (key == 4) {
                this.$router.push({
                    path: '/message'
                })
            } else if (key == 5) {
                this.$router.push({
                    path: '/friend'
                })
            } else if (key == 6) {
                window.open("http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=SycyKCQ5IjgvLj0uCzo6ZSgkJg", '_blank')
            } else if (key == 7) {
                this.$router.push({
                    path: '/about'
                })
            }
        },
        goToLogin() {
            this.$router.push('/login')
        },
        goToRegister() {
            this.$router.push('/login')
        },
        logout() {
            console.log('logout')
            this.$post('/account/logout').then(res => {

                Message({
                    type: 'success',
                    message: 'You have been logged out.'
                })

            })
        },
        submitChangePwd(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {

                    this.$post('/changePwd', {
                        id: this.accountInfo.id,
                        oldPassword: this.form.oldPassword,
                        password: this.form.password

                    }).then(res => {
                        Message({
                            type: 'success',
                            message: '修改成功！请重新登录！.'
                        })
                        this.logout();
                    });
                } else {
                    Message({
                        type: 'error',
                        message: 'Please enter a valid password!'

                    });
                    return false;

                }
            })
        }
    },
    mounted() {
        this.accountInfo = JSON.parse(sessionStorage.getItem('accountInfo'))

    }

}
</script>

<style lang="less" scoped>
:deep(.btn-publish){
    margin-bottom: 5px;
    margin-left: 10px;
    padding: 5px;
    float: right;
    border-radius: 25%;
    line-height: 18px;
    background: #fc5531;
    color:#fff;
}

:deep(.dialog-con) {
    background: linear-gradient(90deg, rgba(247, 149, 51, .1), rgba(243, 112, 85, .1) 15%, rgba(239, 78, 123, .1) 30%, rgba(161, 102, 171, .1) 44%, rgba(80, 115, 184, .1) 58%, rgba(16, 152, 173, .1) 72%, rgba(7, 179, 155, .1) 86%, rgba(109, 186, 130, .1));

}

:deep(.el-dialog) {
    background: rgba(255, 255, 255, 0.8);
    border-radius: 6px;
    width: 400px;
}

:deep(.el-menu.el-menu--horizontal) {
    height: 60px;
}

.search-input {
    width: 300px;
    margin: 0;
    border-radius: 16px;

    :deep(.el-input__inner) {
        border-radius: 16px 0 0 16px;
        padding-right: 0;
    }

    :deep(.el-input__icon) {
        border-radius: 0 16px 16px 0;
    }

}

:deep(.el-input-group__append) {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 16px;
    border-top-right-radius: 16px;
    background-color: #fc5531;
    color: #fff;
    padding: 0;
    margin: 0;
    height: 38px;
    width: 70px;
}

:deep(.el-input__prefix) {
    left: 5px;
    transition: all .3s;
    font-size: 16px;
    line-height: 40px;
}

.header {
    height: 60px;
    float: left;
    width: 100%;
    text-align: center;

    .warapper {
        border-bottom: 1px solid #ebeef5;
        height: 60px;
        min-width: 1200px;
        margin: auto 100px;

        .search-div {
            float: left;
            width: 300px;
            height: 60px;
            line-height: 60px;
            margin-left: 100px;
        }

        .header-menu {
            float: left;
            margin-left: 20px;

            .el-menu-header {
                float: left;
                margin-left: 20px;
                background-color: transparent;

            }

            .login-register {
                float: right;
                margin-left: 10px;
                height: 60px;
                line-height: 60px;

            }

            .user-info {
                float: right;
                margin-left: 10px;
                height: 60px;
                line-height: 60px;
                margin-top: 10px;

            }

        }

        .logo {
            width: 160px;
            margin: 0;
            float: left;
            text-align: center;

            .brand {
                position: relative;
                display: inline-block;
                color: #222;
                border-bottom: none;
                background: none;
                padding: 2px 2px 1px 20px;
                margin: 8px auto;
            }

            .logo-line-before {
                display: block;
                overflow: hidden;
                margin: 0 auto;
                width: 75%;
            }

            .logo-line-before i {
                position: relative;
                display: block;
                height: 2px;
                left: -100%;
                background: -webkit-linear-gradient(left,
                        #ffffff,
                        #ff0000 6.25%,
                        #ff7d00 12.5%,
                        #ffff00 18.75%,
                        #00ff00 25%,
                        #00ffff 31.25%,
                        #0000ff 37.5%,
                        #ff00ff 43.75%,
                        #ffff00 50%,
                        #ff0000 56.25%,
                        #ff7d00 62.5%,
                        #ffff00 68.75%,
                        #00ff00 75%,
                        #00ffff 81.25%,
                        #0000ff 87.5%,
                        #ff00ff 93.75%,
                        #ffff00 100%);

            }

            .site-title {
                display: inline-block;
                vertical-align: top;
                line-height: 36px;
                font-size: 24px;
                position: relative;
                font-weight: bolder;

                cursor: pointer;
                color: #fff;
                text-align: left;
                background: -webkit-linear-gradient(left,
                        #ffffff,
                        #ff0000 6.25%,
                        #ff7d00 12.5%,
                        #ffff00 18.75%,
                        #00ff00 25%,
                        #00ffff 31.25%,
                        #0000ff 37.5%,
                        #ff00ff 43.75%,
                        #ffff00 50%,
                        #ff0000 56.25%,
                        #ff7d00 62.5%,
                        #ffff00 68.75%,
                        #00ff00 75%,
                        #00ffff 81.25%,
                        #0000ff 87.5%,
                        #ff00ff 93.75%,
                        #ffff00 100%);
                background-clip: text;
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-size: 200% 100%;
                animation: masked-animation 2s infinite linear;

            }

            .logo-line-after {
                display: block;
                overflow: hidden;
                margin: 0 auto;
                width: 75%;
            }

            .logo-line-after i {
                position: relative;
                display: block;
                height: 2px;

                right: -100%;
                background: -webkit-linear-gradient(left,
                        #ffffff,
                        #ff0000 6.25%,
                        #ff7d00 12.5%,
                        #ffff00 18.75%,
                        #00ff00 25%,
                        #00ffff 31.25%,
                        #0000ff 37.5%,
                        #ff00ff 43.75%,
                        #ffff00 50%,
                        #ff0000 56.25%,
                        #ff7d00 62.5%,
                        #ffff00 68.75%,
                        #00ff00 75%,
                        #00ffff 81.25%,
                        #0000ff 87.5%,
                        #ff00ff 93.75%,
                        #ffff00 100%);

            }

        }
    }
}
</style>
