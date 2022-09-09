<template>
<div>
    <el-card class="box-card">
        <div class="avatar" style="text-align: left;margin-top: 5px;" v-if="userInfo">
            <div style="text-align: center;" class="avatar-div">
                <div style="margin: auto;height: 100px; width: 100px; line-height: 100px; ">
                    <el-avatar :size="100" :src="userInfo.avatar"></el-avatar>
                    <div class="avatar-hover" ref="avatatHoverRef">
                        <img src="@/assets/static/images/pic.png" alt="">
                    </div>
                </div>
            </div>

            <div class="info">
                <el-descriptions class="margin-top" :column="2" border>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                        </template>
                        <span v-html="userInfo.username"></span>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            注册时间
                        </template>
                        <span style="font-size: 12px;">{{ userInfo.createTime | DateTimeFormat }}</span>

                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            昵称
                        </template>
                        <span v-html="userInfo.nickname"></span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            性别
                        </template>
                        <el-tag size="small" v-if="userInfo.sex === 0">女</el-tag>
                        <el-tag size="small" v-else-if="userInfo.sex === 1">男</el-tag>
                        <el-tag size="small" v-else>未知</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            个性签名
                        </template>
                        {{ userInfo.personalSignature }}
                    </el-descriptions-item>
                </el-descriptions>

            </div>
            <el-divider content-position="right" style="height: 2px;">博主文章</el-divider>
            <div v-if="articles.length > 0">
                <el-card class="box-card" v-for="(article, index) in articles" :key="index" style="background: rgb(255, 255, 255,0.4);margin-top: 10px;">
                    <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">

                        <h4 class="article-title" :title="article.title" v-html="article.title">
                        </h4>

                    </el-link>

                    <p style="height:70px;overflow: hidden;">
                        <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">
                            <span v-html="article.summary"></span>
                        </el-link>
                    </p>
                    <div class="bar">
                        <i class="el-icon-time"></i><span>发表于 &nbsp;</span>
                        <span>{{ article.createTime | DateTimeFormat }}&nbsp;&nbsp;</span>
                        <span style="font-size: 12px;">修改于 {{ article.lastUpdate | DateTimeFormat }}</span>
                        <i class="el-icon-s-operation"></i><span>分类：</span>
                        <span><a @click="toCategory(article.categoryId)">{{ article.categoryName }}</a></span> |
                        <i class="el-icon-view"></i><span>阅读量：</span><span>{{ article.views }}</span>
                        |
                        <i class="el-icon-chat-dot-round"></i><span>评论数：</span><span>{{ article.commentNum }}</span>

                    </div>
                    <el-tag size="small" style="margin-right: 5px;" type="success" v-for="(item, index) in article.tags" :key="index">
                        <el-link @click="toTag(item.id)">
                            {{ item.tagName }}
                        </el-link>

                    </el-tag>

                </el-card>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.pageNum" :page-sizes="[20, 40, 60, 100]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
                </el-pagination>
            </div>
            <div v-else>
                <el-empty :image-size="200"></el-empty>
            </div>
        </div>
        <div v-else>
            <el-empty :image-size="200"></el-empty>
        </div>
    </el-card>

</div>
</template>

<script>
import avatarCropper from '../user/components/AvatarCorpper.vue'

export default {
    name: 'Blogger',
    components: {
        avatarCropper,
    },
    data() {
        return {
            img: '',
            accountId: 0,
            userInfo: {},
            articles: [],
            pageInfo: {},
            dialogVisible: false,

        }
    },
    methods: {
        getUserInformations() {
            if (!this.accountId) return;
            this.$get('/account/findOne', {
                id: this.accountId
            }).then(res => {
                this.userInfo = res.data;
            });
        },
        getArticles() {
            if (!this.accountId) return;
            this.$get('/article/user/' + this.accountId, {
                pageNum: this.pageInfo.pageNum ? this.pageInfo.pageNum : 1,
                pageSize: this.pageInfo.pageSize ? this.pageInfo.pageSize : 20
            }).then(res => {
                this.articles = res.data.list;
                this.pageInfo = res.data;
            }).catch(err => {
                this.articles = [];
            });
        },
        handleSizeChange(val) {
            this.pageInfo.pageSize = val;
            this.getArticles()
        },
        handleCurrentChange(val) {
            this.pageInfo.pageNum = val;
            this.getArticles()

        },
        toArticle(e) {
            this.$router.push({
                path: '/article',
                query: {
                    id: e
                }
            })
        },

    },
    created() {
        this.accountId = this.$route.query.id
    },
    mounted() {
        this.getUserInformations();
        this.getArticles();
    }

}
</script>

<style>
.box-card {
    width: 1000px;
    margin: 20px auto;
    background: rgba(255, 255, 255, 0.5);

}

.bar {
    width: 100%;
    font-size: 12px;
    color: #999;
    padding: 0;
    margin-top: 10px;
}
</style>
