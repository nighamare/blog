<template>
<div class="footer">
    <!-- 版权 -->
    <span>Copyright © 2022-{{year}}&nbsp;&nbsp;Mr. Lycoris&nbsp;&nbsp;</span>
    <!-- 备案号 -->
    <span>ICP备案号：粤ICP备{{2022}}000xxx号</span>
    <div style="font-size: 34px;">
        <span style="font-size: 20px;margin-right: 20px;">联系我们:</span>
        <el-popover placement="top" width="200" trigger="click">
            <img src="@/assets/static/images/wb.png" alt="GG" style="width:200px;height:200px;" />
            <el-link slot="reference" class="link-icon" :underline="false">
                <i class="iconfont icon-weibo"></i>
            </el-link>
        </el-popover>
        <el-popover placement="top" width="200" trigger="click">
            <img src="@/assets/static/images/wx.jpg" alt="GG" style="width:200px;height:200px;" />
            <el-link slot="reference" class="link-icon" :underline="false">
                <i class="iconfont icon-weixin"></i>
            </el-link>
        </el-popover>
        <el-popover placement="top" width="200" trigger="click">
            <img src="@/assets/static/images/qq.jpg" alt="GG" style="width:200px;height:200px;" />
            <el-link slot="reference" class="link-icon" :underline="false"><i class="iconfont icon-QQ"></i>
            </el-link>
        </el-popover>
        <el-link target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=SycyKCQ5IjgvLj0uCzo6ZSgkJg" class="link-icon" :underline="false"><i class="iconfont icon-youxiang"></i>
        </el-link>
    </div>

</div>
</template>

<script>
export default {
    name: 'Footer',
    data() {
        return {
            year: new Date().getFullYear()
        }
    },
    methods: {

    },

}
</script>

<style lang="less" scoped>
.iconfont {
    font-size: 34px;
}

.footer {
    text-align: center;
    color: #b3b0af;
    z-index: 999;
}

.link-icon {
    margin: 0 10px;
}
</style>
