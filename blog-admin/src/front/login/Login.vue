<template>
<div class="body-div">
    <div class="container">
        <div class="welcome">
            <div class="pinkbox" ref="pinkBox" v-loading="loading">
                <!-- 注册 -->
                <div class="register-div nodisplay" ref="registerDiv">
                    <h1>注册</h1>
                    <el-form :model="registerForm" autocomplete="off" :rules="rules" ref="ruleForm">
                        <el-form-item prop="username">
                            <el-input type="text" placeholder="Username" v-model="registerForm.username" />
                        </el-form-item>
                        <el-form-item prop="phoneNumber">
                            <el-input type="text" placeholder="Phone Number" v-model="registerForm.phoneNumber" />
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input type="password" placeholder="Password" v-model="registerForm.password" />
                        </el-form-item>
                        <el-form-item prop="checkPassword">
                            <el-input type="password" placeholder="Confirm Password" v-model="registerForm.checkPassword" />
                        </el-form-item>
                        <el-form-item>
                            <el-button class="sub-btn" @click="registerSubmit">创建账号</el-button>
                        </el-form-item>

                        <!--          <input type="email" placeholder="Email" name="email">-->

                    </el-form>
                </div>

                <!-- 登录 -->
                <div class="login-div" ref="loginDiv">
                    <h1>登录</h1>
                    <el-form :model="loginForm" class="more-padding" autocomplete="off">
                        <el-form-item>
                            <el-input type="text" placeholder="Username" v-model="loginForm.username"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input type="password" placeholder="Password" v-model="loginForm.password"></el-input>
                        </el-form-item>
                        <el-form-item style="margin-bottom: 0;">
                            <el-checkbox class="check-box" label="Remember Me" v-model="loginForm.checked"></el-checkbox>
                        </el-form-item>
                        <el-button class="sub-btn" @click="submitLoginForm(loginForm)">立即登录</el-button>
                    </el-form>
                </div>
            </div>

            <div class="leftbox">
                <h2 class="title"><span>Lycoris</span>&<br>My_Blog</h2>
                <p class="desc">Pick your perfect <span>Blog</span></p>
                <el-image class="flower smaller" :src="require('@/assets/static/images/login01.png')" />
                <p class="account">Have an account?</p>
                <el-button class="login-btn" @click="loginBox">登 录</el-button>
            </div>

            <div class="rightbox">
                <h2 class="title"><span>BLOOM</span>&<br>Blog</h2>
                <p class="desc">Pick your perfect <span>My_Blog</span></p>
                <el-image class="flower smaller" :src="require('@/assets/static/images/login02.png')" />
                <p class="account">Don't have an account?</p>
                <el-button class="signup-btn" @click="registerBox">注 册</el-button>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {
    login,
    register
} from '@/axios/api/loginAndLogout'
import {
    Message
} from 'element-ui';
const Base64 = require("js-base64").Base64
export default {
    name: "LoginAndRegister",
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.registerForm.checkPassword !== '') {
                    this.$refs.registerForm.validateField('checkPassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.registerForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        var validatePhone = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入电话号码'))
            }
            if (value.length !== 11) {
                callback(new Error('请输入正确电话号码'))
            }
            if (value.length !== 11) {
                callback(new Error('请输入正确电话号码'))
            }
            // 验证电话号码手机号码，包含至今所有号段? ?
            var ab = /^[1][3,4,5,7,8][0-9]{9}$/
            if (ab.test(value) === false) {
                callback(new Error('请输入正确电话号码'))
            }
            callback()
        };
        return {
            loginForm: {
                username: '',
                password: '',
                checked: false
            },
            registerForm: {
                username: '',
                password: '',
                checkPassword: '',
                phoneNumber: ""
            },
            loading: false,
            rules: {
                username: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    validator: validatePass,
                    trigger: 'blur'
                }],
                checkPassword: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    validator: validatePass2,
                    trigger: 'blur'
                }],
                phoneNumber: [{
                        required: true,
                        message: '请输入号码',
                        trigger: 'blur'
                    },
                    {
                        validator: validatePhone,
                        trigger: 'blur'
                    }
                ]
            }

        }
    },
    methods: {
        registerBox() {
            this.$refs.registerDiv.className = "register-div",
                this.$refs.loginDiv.className = "login-div nodisplay",
                this.$refs.pinkBox.style = "transform:translateX(80%)"

        },
        loginBox() {
            this.$refs.registerDiv.className = "register-div nodisplay",
                this.$refs.loginDiv.className = "login-div",
                this.$refs.pinkBox.style = "transform:translateX(0%)"
        },
        submitLoginForm(e) {
            if (this.loginForm.checked) {

                let password = Base64.encode(this.loginForm.password); // base64加密
                this.setCookie(this.loginForm.username, password, 7);;

            } else {
                this.setCookie("", "", -1); // 修改2值都为空，天数为负1天就好了

            }
            this.loading = true
            login(e).then(rs => {

                this.$store.commit('login', rs.accountInfo)
                this.$store.commit('token', rs.token)
                this.$router.push({
                    path: '/home'
                })

            })
        },
        registerSubmit() {
            register(this.registerForm).then(rs => {
                if (rs.code == 0) {
                    let account = {
                        username: this.registerForm.username,
                        password: this.registerForm.password
                    }
                    Message.success("注册成功，正在登录！")
                    this.loginBox()
                    this.loading = true
                    setTimeout(() => {
                        this.submitLoginForm(account)
                    }, 2000)

                } else {
                    Message.error('注册失败，请稍后再试！')
                }

            })
        },
        // 设置cookie

        setCookie(username, password, days) {

            let date = new Date(); // 获取时间
            date.setTime(date.getTime() + 24 * 60 * 60 * 1000 * days); // 保存的天数
            // 字符串拼接cookie
            window.document.cookie =
                "username" + "=" + username + ";path=/;expires=" + date.toGMTString();
            window.document.cookie =
                "password" + "=" + password + ";path=/;expires=" + date.toGMTString();
        },
        // 读取cookie 将用户名和密码回显到input框中
        getCookie() {
            if (document.cookie.length > 0) {
                let arr = document.cookie.split("; "); //分割成一个个独立的“key=value”的形式
                for (let i = 0; i < arr.length; i++) {
                    let arr2 = arr[i].split("="); // 再次切割，arr2[0]为key值，arr2[1]为对应的value
                    if (arr2[0] === "username") {
                        this.loginForm.username = arr2[1];
                    } else if (arr2[0] === "password") {
                        this.loginForm.password = Base64.decode(arr2[1]); // base64解密
                        this.loginForm.checked = true;
                    }
                }
            }
        },

    },
    mounted() {
        this.getCookie();
    },

}
</script>

<style lang="less" scoped>
.body-div {
    width: 100%;
    height: 100%;
    // background: #cbc0d3;
    background: linear-gradient(90deg, rgba(247, 149, 51, .1), rgba(243, 112, 85, .1) 15%, rgba(239, 78, 123, .1) 30%, rgba(161, 102, 171, .1) 44%, rgba(80, 115, 184, .1) 58%, rgba(16, 152, 173, .1) 72%, rgba(7, 179, 155, .1) 86%, rgba(109, 186, 130, .1));
    position: fixed;
    background-size: 100% 100%;
    overflow: scroll;
}

/* 容器的样式 */
.container {
    margin: auto;
    width: 650px;
    height: 550px;
    position: relative;
}

.welcome {
    background: #f6f6f6;
    width: 650px;
    height: 415px;
    position: absolute;
    top: 25%;
    border-radius: 5px;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
}

.pinkbox {
    position: absolute;
    top: -10%;
    left: 5%;
    background: #eac7cc;
    width: 320px;
    height: 500px;
    border-radius: 5px;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    transition: all 0.5s ease-in-out;
    z-index: 2;
}

.nodisplay {
    display: none;
    transition: all 0.5s ease;
}

.leftbox,
.rightbox {
    position: absolute;
    width: 50%;
    transition: 1s all ease;
}

.leftbox {
    left: -2%;
}

.rightbox {
    right: -2%;
}

/* 字体和按钮的样式 */
h1 {
    font-family: "Open Sans", sans-serif;
    text-align: center;
    margin-top: 35px;
    text-transform: uppercase;
    color: #f6f6f6;
    font-size: 2em;
    letter-spacing: 8px;
}

.title {
    font-family: "Lora", serif;
    color: #8e9aaf;
    font-size: 1.8em;
    line-height: 1.1em;
    letter-spacing: 3px;
    text-align: center;
    font-weight: 300;
    margin-top: 20%;
}

.desc {
    margin-top: -8px;
}

.account {
    margin-top: 45%;
    font-size: 10px;
}

p {
    font-family: "Open Sans", sans-serif;
    font-size: 0.7em;
    letter-spacing: 2px;
    color: #8e9aaf;
    text-align: center;
}

span {
    color: #eac7cc;
}

.flower {
    position: absolute;
    width: 150px;
    height: 150px;
    top: 45%;
    left: 27%;
    opacity: 0.8;
}

.smaller {
    width: 130px;
    height: 130px;
    top: 48%;
    left: 30%;
    opacity: 0.9;
}

.signup-btn,
.sub-btn,
.login-btn {
    padding: 12px;
    font-family: "Open Sans", sans-serif;
    text-transform: uppercase;
    letter-spacing: 3px;
    font-size: 11px;
    border-radius: 10px;
    margin: auto;
    outline: none;
    display: block;
}

.sub-btn:hover {
    background: #eac7cc;
    color: #f6f6f6;
    transition: background-color 1s ease-out;
}

.sub-btn {
    margin-top: 3%;
    background: #f6f6f6;
    color: #ce7d88;
    border: solid 1px #eac7cc;
    margin-top: 25px;
    padding: 12px;
    border-color: #ce7d88;
}

/* 表单样式 */
form {
    display: flex;
    align-items: center;
    flex-direction: column;
    padding-top: 7px;
}

.more-padding {
    padding-top: 35px;
}

.sub-btn:hover {
    background: #cbc0d3;
    border-color: #bfb1c9;
}

input {
    background: #eac7cc;
    width: 65%;
    color: #ce7d88;
    border: none;
    border-bottom: 1px solid rgba(246, 246, 246, 0.5);
    padding: 9px;
    font-weight: 100;
}

input::placeholder {
    color: #f6f6f6;
    letter-spacing: 2px;
    font-size: 1.0em;
    font-weight: 100;
}

input:focus {
    color: #ce7d88;
    outline: none;
    border-bottom: 1.2px solid rgba(206, 125, 136, 0.7);
    font-size: 1.0em;
    transition: 0.8s all ease;
}

input:focus::placeholder {
    opacity: 0;
}

label {
    font-family: "Open Sans", sans-serif;
    color: #ce7d88;
    font-size: 0.8em;
    letter-spacing: 1px;
}

.check-box {
    display: inline;
    white-space: nowrap;
    position: relative;
    text-align: center;
}
</style>
