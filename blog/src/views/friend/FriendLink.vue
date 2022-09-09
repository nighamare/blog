<template>
<div>
    <!-- 链接列表 -->
    <el-card class="blog-container">
        <div class="link-title mb-1">
            <i class="el-icon-link"></i> 大佬链接
        </div>
        <div class="link-container">
            <div class="link-wrapper" v-for="item of friendLinkList" :key="item.id">
                <a :href="item.linkAddress" target="_blank">
                    <el-avatar fit="scale-down" :size="65" :src="item.linkAvatar" class="link-avatar">
                        <div slot="error" class="image-slot">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                    </el-avatar>
                    <div class="link-div">
                        <span class="link-name">{{ item.linkName }}</span>
                        <span >{{ item.email }}</span>
                        <p class="link-intro">{{ item.linkIntro }}</p>
                    </div>
                </a>
            </div>
        </div>
        <div style="text-align: center;">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.pageNum" :page-sizes="[12, 24, 36, 128]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
            </el-pagination>
        </div>

        <!-- 说明 -->
        <div class="link-title">
            <i class="el-icon-link"></i>友链说明
        </div>
        <blockquote>
            <div>名称：枫雪的个人博客</div>
            <div>邮箱：lycorisdeve@qq.com</div>
            <div>简介：只要我摆的够烂 我就是无敌的</div>
            <div>头像：https://www.xxxx.com/image/avatar.jpg</div>
        </blockquote>
        <el-divider></el-divider>
        <div style="margin-bottom: 10px;">
            <span style="color: #409eff;font-weight: 700;">友链申请</span>
        </div>
        <div style="width: 500px;">
            <el-form label-position="right" label-width="80px" :model="formData" ref="formData">
            <el-form-item label="站点名称" prop="linkName">
                <el-input v-model="formData.linkName" placeholder="请输入站点名称"></el-input>
            </el-form-item>
            <el-form-item label="邮箱地址" prop="email">
                <el-input v-model="formData.email" placeholder="请输入有效的邮箱地址"></el-input>
            </el-form-item>
            <el-form-item label="站点地址" prop="linkAddress">
                <el-input v-model="formData.linkAddress" placeholder="https://或者http://"></el-input>
            </el-form-item>
            <el-form-item label="站点图片" prop="linkAvatar">
                <el-input v-model="formData.linkAvatar" placeholder="https://或者http://+你的站点头像图片地址"></el-input>
            </el-form-item>
            <el-form-item label="站点描述" prop="linkIntro">
                <el-input v-model="formData.linkIntro" placeholder="请一句话描述一下您的站点"></el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" @click="submitForm">提交申请</el-button>
                <el-button @click="resetForm('formData')">重置</el-button>
            </el-form-item>
        </el-form>
        </div>
        
    </el-card>
   
</div>
</template>

<script>
export default {

    created() {
        this.getFriendLinks();
    },
    data: function () {
        return {
            friendLinkList: [],
            commentList: [],
            count: 0,
            pageInfo: {},
            formData: {
                linkName: '',
                email: '',
                linkAddress: '',
                linkAvatar: '',
                linkIntro: ''
            }
        };
    },
    methods: {
        getFriendLinks() {
            this.$get("/friend/list", {
                pageNum: this.pageInfo.pageNum,
                pageSize: this.pageInfo.pageSize
            }).then(res => {
                this.friendLinkList = res.data.list;
                this.pageInfo = res.data;
            });
        },
        handleSizeChange(val) {
            this.pageInfo.pageSize = val;
            this.getFriendLinks();
        },
        handleCurrentChange(val) {
            this.pageInfo.pageNum = val;
            this.getFriendLinks();
        },
        submitForm() {
            // 验证表单是否有空值
            if (this.formData.name == '' || this.formData.linkAddress == '' || this.formData.linkAvatar == '' || this.formData.linkIntro == '' || this.formData.email == '') {
                this.$message({
                    message: '请填写完整信息',
                    type: 'warning'
                });
                return;
            }
            // 邮箱验证
            let reg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/
            if (!reg.test(this.formData.email)) {
                this.$message({
                    message: '邮箱格式不正确',
                    type: 'warning'
                });
                return;
            }
            this.$post("/friend/apply", this.formData).then(res => {
                this.$message({
                    message: res.msg,
                    type: 'success'
                });
                this.resetForm('formData');
            });
        },
        resetForm(formName) {
        this.$refs[formName].resetFields();
      }

    }
};
</script>

<style scoped>
blockquote {
    line-height: 2;
    margin: 0;
    font-size: 15px;
    border-left: 0.2rem solid #49b1f5;
    padding: 10px 1rem !important;
    background-color: #ecf7fe;
    border-radius: 4px;
}

.blog-container {
    width: 1000px;
    margin: auto;

}

.link-title {
    color: #344c67;
    font-size: 21px;
    font-weight: bold;
    line-height: 2;
}

.link-container {
    display: flex;
    flex-wrap: wrap;
}

.link-wrapper {
    width: 215px;
    height: 140px;
    margin: 10px;
    border: 2px solid #eee;

}

.link-div {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin: auto;

}

.link-avatar {
    margin: auto 5px;
    transition: all 0.5s;
}

.link-name {
    text-align: center;
    font-size: 16px;
    font-weight: 700;

}

.link-intro {
    text-align: center;
    height: 50px;
    font-size: 13px;
    color: #1f2d3d;
    width: 100%;
}

.link-wrapper:hover .link-avatar {
    transform: rotate(360deg);
}

.link-wrapper a {
    color: #333;
    text-decoration: none;
    display: flex;
    height: 100%;
    width: 100%;
}
</style>
