<template>
    <div>
        <el-container>
            <el-header style="height: auto;">
                <div class="header-container">
                    <div class="img1">
                        <img src="@/assets/static/img/logo.png" style="height: 70px;" />
                    </div>
                    <div class="img2">
                        <img src="@/assets/static/img/logo2.png" style="height: 70px;" />
                    </div>
                    <div class="img3">
                        <img src="@/assets/static/img/logo1.png" style="height: 70px;" />
                    </div>

                </div>
            </el-header>
            <el-container style="height: auto;">
                <el-aside :width="collapse ? '64px' : '200px'" style="overflow: hidden;max-width: 200px;">
                    <Aside :collapse="collapse"></Aside>
                </el-aside>
                <el-container style="height: auto;">
                    <el-header style="height: 50px;padding: 0;">
                        <navbar class="navbar" @sendIsCollapse="getIsCollapse" />
                    </el-header>
                    <el-main style="height: 610px;">
                        <el-scrollbar wrap-class="scrollbar-wrapper">
                            <router-view></router-view>
                        </el-scrollbar>
                    </el-main>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import Aside from '../layout/components/Aside.vue';
import Navbar from '../layout/components/Navbar.vue'
export default {
    name: 'Layout',
    components: {
        Aside,
        Navbar
    },
    data() {
        return {
            collapse: false
        }
    },
    methods: {
        getIsCollapse(e) {
            this.collapse = e
        }
    },

}
</script>

<style lang="less" scoped>
.narbar {
    width: 100%;
    flex-wrap: wrap;
}

.header-container {
    display: flex;
    width: 1200px;
    margin: auto;
    height: 60px;

    .img1 {
        text-align: left;
        width: 33%;
        height: 60px;

        img {
            position: absolute;
            top: -10px;
            height: 60px;
        }
    }

    .img2 {
        text-align: center;
        width: 33%;
        height: 60px;
    }

    .img3 {
        text-align: right;
        width: 33%;
        height: 60px;
    }
}
</style>
