<template>
    <div class="navbar">
        <!-- <breadcrumb id="breadcrumb-container" class="breadcrumb-container" @toggleClick="toggleSideBar" /> -->
        <div class="container">
            <div class="icon-div" @click="toggleClick">
                <i :class="iconChange" ref="icon"></i>
            </div>
            <div class="breadcrumb-div">
                <el-breadcrumb class="app-breadcrumb" separator="/">
                    <transition-group name="breadcrumb">
                        <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
                            <span v-if="item.redirect === 'noRedirect' || index == levelList.length - 1"
                                class="no-redirect">{{
                                        item.meta.title
                                }}</span>
                            <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
                        </el-breadcrumb-item>
                    </transition-group>
                </el-breadcrumb>
            </div>
        </div>
        <div class="right-menu">
            <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
                <div class="avatar-wrapper">
                    <img v-if="accountInfo" :src="accountInfo.avatar" alt="GG" class="user-avatar">
                    <img v-else src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" alt="GG"
                        class="user-avatar">
                    <i class="el-icon-caret-bottom" />
                </div>
                <el-dropdown-menu slot="dropdown">
                    <router-link to="/">
                        <el-dropdown-item>首页</el-dropdown-item>
                    </router-link>
                    <router-link to="/account">
                        <el-dropdown-item>我的信息</el-dropdown-item>
                    </router-link>
                    <router-link to="/edit">
                        <el-dropdown-item>撰写文章</el-dropdown-item>
                    </router-link>
                    <el-dropdown-item divided @click.native="logout">
                        <span style="display:block;">退 出 登 录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>

    </div>
</template>

<script>
import pathToRegexp from 'path-to-regexp'
export default {
    components: {

    },
    data() {
        return {
            accountInfo: {},
            isCollapse: false,
            levelList: null,
            iconChange: 'el-icon-s-fold breadcrumb-icon',

        }
    },
    methods: {
        toggleSideBar() {
            this.$store.dispatch('app/toggleSideBar')
        },
        async logout() {
            this.$post('/account/logout').then(() => {
                this.$router.push({
                    path: '/login',
                })
            }).catch(err => {
                this.$router.push({
                    path: '/login',
                })
            })

        },

        getBreadcrumb() {
            // only show routes with meta.title
            let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
            const first = matched[0]

            if (!this.isDashboard(first)) {
                matched = [{
                    path: '/index',
                    meta: {
                        title: '首页'
                    }
                }].concat(matched)
            }

            this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
        },
        isDashboard(route) {
            const name = route && route.name
            if (!name) {
                return false
            }
            return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
        },
        pathCompile(path) {
            // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
            const {
                params
            } = this.$route
            var toPath = pathToRegexp.compile(path)
            return toPath(params)
        },
        handleLink(item) {
            const {
                redirect,
                path
            } = item
            if (redirect) {
                this.$router.push(redirect)
                return
            }
            this.$router.push(this.pathCompile(path))
        },
        toggleClick() {
            this.isCollapse = !this.isCollapse
            if (this.isCollapse) {
                this.iconChange = 'el-icon-s-unfold breadcrumb-icon'

            } else {
                this.iconChange = 'el-icon-s-fold breadcrumb-icon'
            }
            this.$emit("sendIsCollapse", this.isCollapse)

        }
    },
    watch: {
        $route(route) {
            // if you go to the redirect page, do not update the breadcrumbs
            if (route.path.startsWith('/redirect/')) {
                return
            }
            this.getBreadcrumb()
        }
    },
    created() {
        this.getBreadcrumb()
        this.accountInfo = JSON.parse(sessionStorage.getItem("adminInfo"))
    },

}
</script>

<style lang="less" scoped>
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .right-menu {
        float: right;
        height: 100%;
        line-height: 50px;

        &:focus {
            outline: none;
        }

        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;

            &.hover-effect {
                cursor: pointer;
                transition: background .3s;

                &:hover {
                    background: rgba(0, 0, 0, .025)
                }
            }
        }

        .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
                margin-top: 5px;
                position: relative;

                .user-avatar {
                    cursor: pointer;
                    width: 40px;
                    height: 40px;
                    border-radius: 10px;
                }

                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: -20px;
                    top: 25px;
                    font-size: 12px;
                }
            }
        }
    }
}

.container {
    height: 50px;
    line-height: 50px;
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
    float: left;
    display: inline-flex;

    .icon-div {
        margin: 0;
        height: 50px;
        width: 50px;
        line-height: 50px;
        cursor: pointer;

        &:hover {
            background: rgba(0, 0, 0, .025)
        }
    }

}

.breadcrumb-icon {
    margin: 10px;
    font-size: 30px;
    position: relative;

}

.breadcrumb-div {
    position: relative;
    float: left;
}

.app-breadcrumb.el-breadcrumb {
    font-size: 14px;
    line-height: 50px;
    margin-left: 8px;
    float: left;

    .no-redirect {
        color: #97a8be;
        cursor: text;
    }
}
</style>
