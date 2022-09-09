<template>
<el-container>
    <el-header style="height: auto;">
        <Header></Header>
    </el-header>
    <el-main style="overflow: visible;padding: 0;">
        <router-view></router-view>
    </el-main>
    <el-footer style="height: auto;">
        <Footer></Footer>
    </el-footer>
</el-container>
</template>

<script>
export default {
    name: 'Home',

}
</script>

<style>
:deep(.el-main){
    padding: 0;
}
</style>
