<template>
<div>
    <div class="tag-container">
        <el-tag class="tag-con">{{tag.tagName}}</el-tag>
    </div>
    <div class="modal-body" :loading="loading">

        <div type="border-card" style="background: rgb(255, 255, 255,0.4);" v-if="articles.length!=0">
            <div v-if="articles.length!=0">
                <el-card class="box-card" v-for="(article,index) in articles" :key="index" style="background: rgb(255, 255, 255,0.4);margin-top: 10px;">
                    <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">

                        <h4 class="article-title" :title="article.title" v-html="article.title">
                        </h4>

                    </el-link>
                    <div class="bar">
                        <i class="el-icon-time"></i><span>发表于 &nbsp;</span>
                        <span>{{article.createTime |DateTimeFormat}}&nbsp;&nbsp;</span>
                        <i class="el-icon-s-operation"></i><span>分类：</span>
                        <span><a @click="toCategory(article.categoryId)">{{article.categoryName}}</a></span> |
                        <i class="el-icon-view"></i><span>阅读量：</span><span>{{article.views}}</span>
                        |
                        <i class="el-icon-chat-dot-round"></i><span>评论数：</span><span>{{article.commentNum}}</span>
                    </div>
                    <p style="height:70px;overflow: hidden;">
                        <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">
                            <span v-html="article.summary"></span>
                        </el-link>
                    </p>
                    <el-tag size="small" style="margin-right: 5px;" type="success" v-for="(item,index) in article.tags" :key="index">
                        <el-link @click="toTag(item.id)">
                            {{item.tagName}}
                        </el-link>

                    </el-tag>
                    <div class="avatar" style="text-align: left;line-height: 10px;margin-top: 5px;">
                        <el-link @click="toUser(article.accountId)" :underline="false" style="height: 50px;">
                            <el-avatar :size="35" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
                            </el-avatar>
                            <span style="margin-left: 10px;">
                                {{article.nickname}}
                                <span style="font-size: 12px;">修改于 {{article.lastUpdate|DateTimeFormat }}</span>
                            </span>
                        </el-link>
                    </div>
                </el-card>

            </div>

        </div>
        <div v-else>
            <el-empty :image-size="200" description="暂时没有相关文章哦~~"></el-empty>
        </div>

    </div>
</div>
</template>

<script>
import {
    Message
} from 'element-ui'
export default {
    name: 'TagDetail',
    data() {
        return {
            loading: false,
            articles: [],
            tag: {},
            pageInfo: {}
        }
    },
    methods: {

        onSearch() {
            this.loading = true;
            if (!this.tag.id) {
                this.$router.push({
                    path: '/tag'
                })
                return;
            }
            let id = this.tag.id;
            this.$get('/article/tag', {
                tagId: id,
                pageNum: 1,
                pageSize: 10,
            }).then(res => {
                this.articles = res.data.list
                this.pageInfo = res.data;
                this.loading = false
            })

        },
        toArticle(e) {
            this.$router.push({
                path: '/article',
                query: {
                    id: e
                }
            })
        },
        toUser(e) {
            this.$router.push({
                path: '/user',
                query: {
                    id: e
                }
            })
        },
        toCategory(e) {
            this.$router.push({
                path: '/category',
                query: {
                    id: e
                }
            })
        },
        toTag(e) {
            this.$router.push({
                path: '/tag',
                query: {
                    id: e.id,
                    tagName: e.tagName
                }
            })

        }

    },
    created() {
        this.tag.id = this.$route.query.id
        this.tag.tagName = this.$route.query.tagName
    },
    mounted() {

        this.onSearch()

    }

}
</script>

<style scoped>
:deep(.el-link--inner ){
    height: 30px;
    font-weight: 550;
    /* color: black; */
    width: 100%;
    /* overflow: hidden; */
    text-overflow: ellipsis;
    white-space: nowrap;
    text-align: left;
}
.avatar{
    height: 50px;
}
.tag-container {
    margin-top: 20px;
    text-align: center;
    margin: 20px auto 10px;
    width: 1000px;
}

.tag-con {
    font-size: 40px;
    font-weight: 500;
    height: 60px;
    line-height: 60px;
}

.modal-body {
    width: 1000px;
    margin: auto;
    margin-top: 10px;
    background: rgb(255, 255, 255, 0.4);
}

.box-card {
    background: rgb(255, 255, 255, 0.4);
}

.article-title {
    font-size: 20px;
    font-weight: 700;
    color: #333;
    margin: 0;
    padding: 0;
    line-height: 30px;
    max-width: 730px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.bar {
    width: 100%;
    font-size: 12px;
    color: #999;
    padding: 0;
    margin-top: 10px;
}

.avatar {
    display: flex;
    position: relative;

}

.info {
    display: block;

}

.info span {
    margin-top: 5px;

}
</style>
