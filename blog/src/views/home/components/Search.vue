<template>
<div>
    <div class="modal-body" :loading="loading">
        <div class="search-form">
            <el-input placeholder="请输入内容" v-model="keywords" class="input-with-select" @keyup.native.enter="onSearch">
                <template slot="prepend" style="background: #fc5531;">搜索博客：</template>
                <el-button slot="append" icon="el-icon-search" @click="onSearch">立即搜索</el-button>
            </el-input>
        </div>
        <el-tabs type="border-card" style="background: rgb(255, 255, 255,0.4);">
            <el-tab-pane label="文章" v-if="articles.length!=0">
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
                        <el-link @click="toUser(article.accountId)" :underline="false">
                            <el-avatar :size="35" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
                            </el-avatar>
                            <span style="margin-left: 10px;">
                                {{article.nickname}}
                                <span style="font-size: 12px;">修改于 {{article.lastUpdate|DateTimeFormat }}</span>
                            </span>
                        </el-link>
                    </div>
                </el-card>

            </el-tab-pane>
            <el-tab-pane label="用户" v-else-if="users.length!=0">

                <el-card class="box-card" v-for="(user,index) in users" :key="index">
                    <el-link @click="toUser(user.accountId)" :underline="false">
                        <div class="avatar" style="text-align: left;margin-top: 5px;">
                            <el-avatar :size="100" :src="user.avatar" style="margin-top: 20px;">
                            </el-avatar>
                            <div class="info">
                                <el-descriptions class="margin-top" :column="2" :size="size" border>
                                    <el-descriptions-item>
                                        <template slot="label">
                                            <i class="el-icon-user"></i>
                                            用户名
                                        </template>
                                        <span v-html="user.username"></span>
                                    </el-descriptions-item>

                                    <el-descriptions-item>
                                        <template slot="label">
                                            <i class="el-icon-user"></i>
                                            注册时间
                                        </template>
                                        <span style="font-size: 12px;">{{user.createTime|DateTimeFormat }}</span>

                                    </el-descriptions-item>
                                    <el-descriptions-item>
                                        <template slot="label">
                                            <i class="el-icon-user"></i>
                                            昵称
                                        </template>
                                        <span v-html="user.nickname"></span>
                                    </el-descriptions-item>
                                    <el-descriptions-item>
                                        <template slot="label">
                                            <i class="el-icon-tickets"></i>
                                            性别
                                        </template>
                                        <el-tag size="small" v-if="user.sex===0">女</el-tag>
                                        <el-tag size="small" v-else-if="user.sex===1">男</el-tag>
                                        <el-tag size="small" v-else>未知</el-tag>
                                    </el-descriptions-item>

                                    <el-descriptions-item>
                                        <template slot="label">
                                            <i class="el-icon-tickets"></i>
                                            个性签名
                                        </template>
                                        {{user.personalSignature}}

                                    </el-descriptions-item>

                                </el-descriptions>

                            </div>
                        </div>
                    </el-link>
                </el-card>
            </el-tab-pane>
            <el-tab-pane v-else>
                <el-empty :image-size="200"></el-empty>
            </el-tab-pane>

        </el-tabs>

    </div>
</div>
</template>

<script>
import {
    Message
} from 'element-ui'
export default {
    name: 'Search',
    data() {
        return {
            loading: false,
            articles: [],
            users: [],
            keywords: '',
        }
    },
    methods: {
        changeArticleColor(result) {
            //result为接口返回的数据
            result.map((item, index) => {
                if (this.keywords) {
                    /**
                     * 使用正则表达式进行全文匹配关键词
                     * ig : 表示 全文查找 ,忽略大小写
                     *  i : 忽略大小写
                     *  g : 全文查找
                     *
                     * 使用字符串的replace方法进行替换
                     * stringObject.replace('被替换的值',替换的值)
                     */
                    let replaceReg = new RegExp(this.keywords, 'ig')
                    let replaceString = `<span style="color: #ed4014">${this.keywords}</span>`
                    result[index].title = item.title.replace(replaceReg, replaceString)
                    result[index].summary = item.summary.replace(replaceReg, replaceString)
                }
            })
            return result
        },
        changeUserColor(result) {
            //result为接口返回的数据
            result.map((item, index) => {
                if (this.keywords) {
                    /**
                     * 使用正则表达式进行全文匹配关键词
                     * ig : 表示 全文查找 ,忽略大小写
                     *  i : 忽略大小写
                     *  g : 全文查找
                     *
                     * 使用字符串的replace方法进行替换
                     * stringObject.replace('被替换的值',替换的值)
                     */
                    let replaceReg = new RegExp(this.keywords, 'ig')
                    let replaceString = `<span style="color: #ed4014">${this.keywords}</span>`
                    result[index].nickname = item.nickname.replace(replaceReg, replaceString)
                    result[index].username = item.username.replace(replaceReg, replaceString)
                }
            })
            return result
        },
        onSearch() {
            this.loading = true;
            if (this.keywords === '') {
                Message({
                    type: 'error',
                    message: '请输入搜索关键字;Please enter a keywords!'
                })

                return;
            }
            let keywords = this.keywords;
            this.$get('/article/search', {
                keywords: keywords,
                pageNum: 1,
                pageSize: 10,
            }).then(res => {
                this.articles = this.changeArticleColor(res.data.articles.list)
                this.users = this.changeUserColor(res.data.users.list)
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
        toTag(){
            this.$router.push({
                path: '/tag',
                query: {
                    id: e
                }
            })

        }

    },
    created() {
        this.keywords = this.$route.query.search
    },
    mounted() {

        this.onSearch()

    }

}
</script>

<style>
.search-form {
    margin: 30px 0 10px 0;

}

.el-input-group__append,
.el-input-group__prepend {
    background-color: #fc5531;
    color: #333;
    vertical-align: middle;
    display: table-cell;
    position: relative;
    border: 1px solid #DCDFE6;
    padding: 0 20px;
    width: 1px;
    white-space: nowrap;
}

.el-input-group__append {
    border-radius: 0 4px 4px 0;
}

.el-input-group__prepend {
    border-radius: 4px 0 0 4px;
}

.margin-top {
    margin-left: 20px;
}

.modal-body {
    width: 1000px;
    margin: auto;
    margin-top: 10px;
    background: rgba(255, 255, 255, 0.4);
}

.box-card {
    background: rgba(255, 255, 255, 0.4);
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
