<template>
<div class="login-container">
    <div>
        <el-form ref="loginForm" :model="loginForm" class="login-form" autocomplete="on" label-position="left">

            <div class="title-container">
                <h3 class="title">登 录</h3>
            </div>

            <el-form-item prop="username">
                <span class="svg-container">
                    <i class="el-icon-user-solid login-icon"></i>
                </span>
                <el-input ref="username" v-model="loginForm.username" placeholder="Username" name="username" type="text" tabindex="1" autocomplete="on" />
            </el-form-item>

            <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
                <el-form-item prop="password">
                    <span class="svg-container">
                        <i class="el-icon-lock login-icon"></i>
                    </span>
                    <el-input ref="password" v-model="loginForm.password" show-password type="password" placeholder="Password" name="password" tabindex="2" autocomplete="on" @keyup.native="checkCapslock" @blur="capsTooltip = false" @keyup.enter.native="handleLogin" />

                </el-form-item>
            </el-tooltip>
            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">Login</el-button>
        </el-form>
    </div>
</div>
</template>

<script>
export default {
    name: 'Login',
    components: {

    },
    data() {

        return {
            loginForm: {
                username: '',
                password: ''
            },

            capsTooltip: false,
            loading: false,
            showDialog: false,
            redirect: undefined,
            otherQuery: {}
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                const query = route.query
                if (query) {
                    this.redirect = query.redirect
                    this.otherQuery = this.getOtherQuery(query)
                }
            },
            immediate: true
        }
    },
    created() {
        // window.addEventListener('storage', this.afterQRScan)
    },
    mounted() {
        if (this.loginForm.username === '') {
            this.$refs.username.focus()
        } else if (this.loginForm.password === '') {
            this.$refs.password.focus()
        }
    },
    destroyed() {
        // window.removeEventListener('storage', this.afterQRScan)
    },
    methods: {
        checkCapslock(e) {
            const {
                key
            } = e
            this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
        },

        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true
                    this.$post('/account/user/login', this.loginForm).then(rs => {
                            this.$store.commit('login', rs.accountInfo)
                            this.$store.commit('token', rs.token)
                            this.$router.push('/')
                            // this.$router.push({
                            //     path: this.redirect || '/',
                            //     query: this.otherQuery
                            // })
                            this.loading = false
                        })
                        .catch(() => {
                            this.loading = false
                        })
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        getOtherQuery(query) {
            return Object.keys(query).reduce((acc, cur) => {
                if (cur !== 'redirect') {
                    acc[cur] = query[cur]
                }
                return acc
            }, {})
        }

    }
}
</script>

<style lang="less">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

@bg: #283443;
@light_gray: #fff;
@cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: @cursor)) {
    .login-container .el-input input {
        color: @cursor;
    }
}

/* reset element-ui css */
.login-container {
    height: 700px;

    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: @light_gray;
            height: 47px;
            caret-color: @cursor;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px @bg inset !important;
                -webkit-text-fill-color: @cursor  !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style><style lang="less" scoped>
@bg: #2d3a4b;
@dark_gray: #889aa4;
@light_gray: #eee;

.login-container {
    min-height: 100%;
    width: 100%;
    background-color: @bg;
    overflow: hidden;

    .login-form {
        position: relative;
        width: 520px;
        max-width: 100%;
        padding: 160px 35px 0;
        margin: 0 auto;
        overflow: hidden;
    }

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: @dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .title-container {
        position: relative;

        .title {
            font-size: 26px;
            color: @light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
    }

}

.login-icon {
    font-size: 20px;
}
</style>
