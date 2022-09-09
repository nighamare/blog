<template>
    <div>
        <!-- logo -->
        <div class="sidebar-logo-container" :class="{ 'collapse': collapse }">
            <transition name="sidebarLogoFade">
                <router-link v-if="collapse" key="collapse" class="logo-collapse" to="/index">
                    <img v-if="logo" :src="logo" class="sidebar-logo">
                    <h1 v-else class="sidebar-title">{{  title  }} </h1>
                </router-link>
                <router-link v-else key="expand" class="logo-expand" to="/">
                    <img v-if="logo" :src="logo" class="sidebar-logo" />
                    <h1 class="sidebar-title">{{  title  }} </h1>
                </router-link>
            </transition>
        </div>
        <!-- end logo -->
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu class="box-menu" :default-active="activeMenu" :collapse="collapse" :unique-opened="true"
                background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :collapse-transition="false"
                @select="handleSelect" mode="vertical">
                <el-menu-item index="0" >
                    <i class="el-icon-s-home"></i>
                    <span slot="title">首页信息</span>
                </el-menu-item>
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-s-check"></i>
                        <span slot="title">审核管理</span>
                    </template>
                    <el-menu-item index="1-1">文章审核</el-menu-item>
                    <el-menu-item index="1-2">友链审核</el-menu-item>
                </el-submenu>
                <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-user-solid"></i>
                        <span slot="title">用户管理</span>
                    </template>
                    <el-menu-item index="2-1">账户信息</el-menu-item>
                    <el-menu-item index="2-2">用户信息</el-menu-item>
                </el-submenu>
                <el-submenu index="3">
                    <template slot="title">
                        <i class="el-icon-reading"></i>
                        <span slot="title">文章管理</span>
                    </template>
                    <el-menu-item index="3-1">文章信息</el-menu-item>
                    <el-menu-item index="3-2">撰写文章</el-menu-item>
                    <el-menu-item index="3-3">文章标签</el-menu-item>
                </el-submenu>
                <el-menu-item index="4">
                    <i class="el-icon-menu"></i>
                    <span slot="title">分类管理</span>
                </el-menu-item>
                <el-menu-item index="5">
                    <i class="el-icon-collection-tag"></i>
                    <span slot="title">标签管理</span>
                </el-menu-item>
                <el-menu-item index="6">
                    <i class="el-icon-s-comment"></i>
                    <span slot="title">评论管理</span>
                </el-menu-item>
                <el-menu-item index="7">
                    <i class="el-icon-message"></i>
                    <span slot="title">消息管理</span>
                </el-menu-item>
                <el-menu-item index="8">
                    <i class="el-icon-connection"></i>
                    <span slot="title">友链管理</span>
                </el-menu-item>
                <el-menu-item index="9">
                    <i class="el-icon-thumb"></i>
                    <span slot="title">点赞管理</span>
                </el-menu-item>

                <el-menu-item index="10">
                    <i class="el-icon-s-promotion"></i>
                    <span slot="title">置顶管理</span>
                </el-menu-item>
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
export default {
    name: 'Aside',
    props: {
        collapse: {
            type: Boolean,
            required: true
        }
    },
    data() {
        return {
            title: 'Blog-Admin',
            logo: 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png',
            activeMenu: '0',
            
        }
    },
    methods: {

        handleSelect(key, keyPath) {

            if (key == "0") {
                this.$router.push({
                    path: "/",
                })
            }
            if (key == "1-1") {
                this.$router.push({
                    path: "/audit/article",
                })
            }
            if (key == "1-2") {
                this.$router.push({
                    path: "/audit/friend",
                })
            }
            if (key == "2-1") {
                this.$router.push({
                    path: "/account",
                })
            }
            if (key == "2-2") {
                this.$router.push({
                    path: "/accountInfo",
                })
            }

            if (key == "3-1") {
                this.$router.push({
                    path: "/article",
                })
            }
            if (key == "3-2") {
                this.$router.push({
                    path: "/edit",
                })
            }
            if (key == "3-3") {
                this.$router.push({
                    path: "/arttag",
                })
            }
            if (key == "4") {
                this.$router.push({
                    path: "/catg",
                })
            }
            if (key == "5") {
                this.$router.push({
                    path: "/tag",
                })
            }
            if (key == "6") {
                this.$router.push({
                    path: "/comment",
                })
            }
            if (key == "7") {
                this.$router.push({
                    path: "/message",
                })
            }
            if (key == "8") {
                this.$router.push({
                    path: "/friend",
                })
            }
            if (key == "9") {
                this.$router.push({
                    path: "/praise",
                })
            }
            if (key == "10") {
                this.$router.push({
                    path: "/top",
                })
            }

        },
       
    },
  

}
</script>

<style lang="less" scoped>
.box-menu {
    min-height: 610px;
    height: 610px;
}

.sidebarLogoFade-enter-active {
    transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
    opacity: 0;
}

.sidebar-logo-container {
    position: relative;
    width: 100%;
    height: 50px;
    line-height: 50px;
    background: #2b2f3a;
    text-align: center;
    overflow: hidden;

    .logo-collapse {

        & .sidebar-logo {
            width: 32px;
            height: 32px;
            vertical-align: middle;
            margin-right: 12px;
        }

        & .sidebar-title {
            margin: 0;
            color: #fff;
            font-weight: 600;
            line-height: 50px;
            font-size: 14px;
            font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
            vertical-align: middle;
        }
    }

    & .logo-expand {
        height: 100%;
        display: flex;
        margin-left: 30px;

        & .sidebar-logo {
            width: 32px;
            height: 32px;
            vertical-align: middle;
            margin-top: 10px;
        }

        & .sidebar-title {
            margin: 0;
            margin-left: 10px;
            color: #fff;
            font-weight: 600;
            line-height: 50px;
            font-size: 14px;
            font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
            vertical-align: middle;
        }
    }

    &.collapse {
        .sidebar-logo {
            margin-right: 0px;
        }
    }

}
</style>
