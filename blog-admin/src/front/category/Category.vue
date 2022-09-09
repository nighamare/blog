<template>
<div>
    <el-tabs v-model="activeName" tab-position="left" style="width: 1000px;margin: auto;" @tab-click="handleClick">
        <el-tab-pane v-for="(item,index) in categoryList" :key="index" :label="item.categoryName" :name="item.id+''">
            <el-timeline>
                <el-timeline-item v-for="(article,index) of articles" :key="index" color="#0bbd87" :timestamp="article.createTime |DateTimeFormat" :hide-timestamp="true" placement="bottom">
                    <el-card >
                        <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">

                            <h4 class="article-title" :title="article.title">{{article.title}}
                            </h4>

                        </el-link>
                        <div class="bar">
                            <i class="el-icon-time"></i><span>发表于 &nbsp;</span>
                            <span>{{article.createTime |DateTimeFormat}}&nbsp;&nbsp;</span>
                            <i class="el-icon-s-operation"></i><span>分类：</span>
                            <span><a href="#">{{article.typeName}}</a></span> |
                            <i class="el-icon-view"></i><span>阅读量：</span><span>{{article.views}}</span>
                            |
                            <i class="el-icon-chat-dot-round"></i><span>评论数：</span><span>{{article.commentNum}}</span>
                        </div>
                        <p style="height:70px;overflow: hidden;">
                            <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">
                                {{article.summary}}
                            </el-link>
                        </p>
                        <el-tag size="small" style="margin-right: 5px;" type="success" v-for="(item,index) in article.tags" :key="index">
                            {{item.tagName}}
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
                </el-timeline-item>
                <div style="text-align: center;">
                    <el-pagination @current-change="handleCurrentChange" :current-page="pageInfo.pageNum" background layout="total,prev, pager, next" :total="pageInfo.total">
                    </el-pagination>
                </div>

            </el-timeline>
        </el-tab-pane>

    </el-tabs>

</div>
</template>

<script>
export default {
    name: 'Category',
    data() {
        return {
            activeName: '1',
            pageInfo: {},
            categoryList: [],
            articles: [],
        }
    },
    methods: {
        listCategories() {
            this.$get('/category/list').then(res => {
                this.categoryList = res.data;
            })

            let pageNum;
            let pageSize;
            if (this.pageInfo.pageNum) {
                pageNum = 1;
            }else{
                pageNum = this.pageInfo.pageNum;
            }
            if (this.pageInfo.pageSize) {
                pageSize = 10;
            }else{
                pageSize = this.pageInfo.pageSize;
            }
            this.$get("/article/category/1", {
                pageNum: pageNum,
                pageSize: pageSize
            }).then(res => {
                this.articles = res.data.list;
                this.pageInfo = res.data;
            })
        },
        handleClick(tab) {
            let categoryId = tab.name;
            let pageNum;
            let pageSize;
            if (this.pageInfo.pageNum) {
                pageNum = 1;
            }else{
                pageNum = this.pageInfo.pageNum;
            }
            if (this.pageInfo.pageSize) {
                pageSize = 10;
            }else{
                pageSize = this.pageInfo.pageSize;
            }
            this.$get("/article/category/" + categoryId, {
                pageNum: pageNum,
                pageSize: 1
            }).then(res => {
                this.articles = res.data.list;
                this.pageInfo = res.data;
            })

        },
        handleCurrentChange() {

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
    },
    created() {
        this.listCategories();
    },

}
</script>

<style lang="less" scoped>
.bar {
    width: 100%;
    font-size: 12px;
    color: #999;
    padding: 0;
    margin-top: 10px;
}

</style>
