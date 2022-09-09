<template>
<div>
    <div class="main-container">
        <el-container>
            <el-header style="height: auto;">
                <div class="container-ad">
                    <el-carousel height="340px" class="right-ad1" :interval="5000" arrow="always">
                        <el-carousel-item v-for="(item, index) in imgUrls" :key="index">
                            <el-image :src="item.url" style="height: 100%; width: 800px" fit="cover"></el-image>
                        </el-carousel-item>
                    </el-carousel>
                    <el-card class="box-card" shadow="hover">
                        <div slot="header" class="clearfix">
                            <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                            <span style="font-size: 20px;font-weight: 700;">个人信息</span>
                        </div>
                        <div>
                            <div style="text-align: center;">
                                <el-avatar :size="70" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
                                </el-avatar>
                            </div>
                            <div class="my-description">
                                <p>Lycoris</p>
                                <p>Bug开发工程师</p>
                                <p>lycorisdeve@qq.com</p>
                            </div>
                            <div style="text-align: center;margin-top: 7px;">
                                <el-popover placement="left" width="200" trigger="click">
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
                    </el-card>

                </div>
            </el-header>
            <el-container style="margin-top: 20px;">
                <el-main style="padding: 0 0 0 20px;width: 800px;overflow: visible;">
                    <div class="article-list">
                        <div style="width: 1200px;border-bottom: 2px solid #f6f6f6;">
                            <p style="font-size: 20px;font-weight: 800;margin: 10px auto;">最新博客</p>
                        </div>
                        <div class="block">
                            <el-timeline style="padding-left: 20px;margin-top: 10px;" color="#0bbd87">
                                <el-timeline-item v-for="(article,index) of articles" :key="index" :timestamp="article.createTime |DateTimeFormat" placement="top">
                                    <el-card>
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
                                            <el-avatar :size="35" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
                                            </el-avatar>
                                            <span style="margin-left: 10px;">
                                                {{article.nickname}}
                                                <span style="font-size: 12px;">修改于 {{article.lastUpdate
                                                            |DateTimeFormat }}</span>
                                            </span>
                                        </div>
                                    </el-card>
                                </el-timeline-item>
                            </el-timeline>
                        </div>
                        <el-pagination style="margin: auto; text-align: center;" :currentPage="pageInfo.pageNum" :page-sizes="[10, 20, 30, 40, 100]" :small="false" :disabled="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :page-size="pageInfo.pageSize" :total="pageInfo.total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />

                    </div>
                    <el-backtop>
                        <i class="el-icon-caret-top"></i>
                    </el-backtop>
                </el-main>
                <el-aside width="352px" style="padding-right: 18px;margin-top: 60px;">
                    <div class="aside-div">
                        <el-card class="aside-box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="iconfont icon-fire"></i>
                                <span style="font-size: 20px;font-weight: 700;">最热文章</span>
                            </div>
                            <div>

                                <div class="my-description">
                                    <span v-for="(article,index) in hotArticles" :key="index">
                                        <el-link @click="toArticle(article.id)" :underline="false" style="height: 30px;font-weight: 550;color: black;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;text-align: left">
                                            <i style="font-size: 14px;color: #437dff;font-weight: 700;padding: 0 5px 0 0;">{{index+1}}</i>
                                            {{article.title}}
                                        </el-link>
                                    </span>
                                </div>

                            </div>
                        </el-card>
                        <el-card class="aside-box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                                <span style="font-size: 20px;font-weight: 700;">标签</span>
                            </div>
                            <div>
                                <div class="tagBall" ref="tagBall" @mouseover.self.stop="mouseOver" @mouseleave.self.stop="mouseLeave">
                                    <span v-for="(tag, index) of tagList" :key="index" @click="toTag(tag)" class="tag">
                                        {{ tag.tagName }}
                                    </span>
                                </div>
                            </div>
                        </el-card>
                        <el-card class="aside-box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                                <span style="font-size: 20px;font-weight: 700;">站长推荐</span>
                            </div>
                            <div>
                                <div class="my-description">
                                    <span v-for="(article,index) in recommendArticles" :key="index">
                                        <el-link @click="toArticle(article.id)" :underline="false" style="height: 30px;font-weight: 550;color: black;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;text-align: left">
                                            <i style="font-size: 14px;color: #437dff;font-weight: 700;padding: 0 5px 0 0;">{{index+1}}</i>
                                            {{article.title}}
                                        </el-link>
                                    </span>
                                </div>

                            </div>
                        </el-card>
                        <el-card class="aside-box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                                <span style="font-size: 20px;font-weight: 700;">猜你喜欢</span>
                            </div>
                            <div>
                                <div class="my-description">
                                    <span v-for="(article,index) in likeArticles" :key="index">
                                        <el-link @click="toArticle(article.id)" :underline="false" style="height: 30px;font-weight: 550;color: black;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;text-align: left">
                                            <i style="font-size: 14px;color: #437dff;font-weight: 700;padding: 0 5px 0 0;">{{index+1}}</i>
                                            {{article.title}}
                                        </el-link>
                                    </span>
                                </div>

                            </div>
                        </el-card>
                        <el-card class="aside-box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                                <span style="font-size: 20px;font-weight: 700;">站点信息</span>
                            </div>
                            <div>

                                <div class="my-description">
                                    <p>Lycoris</p>
                                    <p>Bug开发工程师</p>
                                    <p>m.yong@foxmail.com</p>
                                </div>

                            </div>
                        </el-card>

                    </div>
                </el-aside>
            </el-container>

        </el-container>
    </div>
</div>
</template>

<script>
export default {
    name: 'Index',
    data() {
        return {
            accountInfo: {},
            articles: [],
            hotArticles: [],
            likeArticles: [],
            recommendArticles: [],
            tagList: [],
            pageInfo: {},
            imgUrls: [{
                    url: require('@/assets/static/images/carousel/80537.jpg'),
                    herfTo: '#'
                },
                {
                    url: require('@/assets/static/images/carousel/1.jpg'),
                    herfTo: '#'
                },
                {
                    url: require('@/assets/static/images/carousel/123.jpg'),
                    herfTo: '#'
                },
                {
                    url: require('@/assets/static/images/carousel/323021.jpg'),
                    herfTo: '#'
                },
                {
                    url: require('@/assets/static/images/carousel/1001427.jpg'),
                    herfTo: '#'
                },
                {
                    url: require('@/assets/static/images/carousel/316558.jpg'),
                    herfTo: '#'
                },
            ],
            tagEle: "",
            paper: "",
            RADIUS: 150,
            fallLength: 500,
            tags: [],
            angleX: Math.PI / 500,
            angleY: Math.PI / 500,
            CX: "",
            CY: "",
            EX: "",
            EY: "",
            timing: null,

        }
    },
    methods: {

        /* STRAT */
        innit() {
            for (var i = 0; i < this.tagEle.length; i++) {
                var a, b;
                var k = (2 * (i + 1) - 1) / this.tagEle.length - 1;
                var a = Math.acos(k);
                var b = a * Math.sqrt(this.tagEle.length * Math.PI);
                var x = this.RADIUS * Math.sin(a) * Math.cos(b);
                var y = this.RADIUS * Math.sin(a) * Math.sin(b);
                var z = this.RADIUS * Math.cos(a);
                // var t = this.tag({ ele: this.tagEle[i], x, y, z });
                this.tagEle[i].style.color = "rgb(" +
                    parseInt(Math.random() * 255) +
                    "," +
                    parseInt(Math.random() * 255) +
                    "," +
                    parseInt(Math.random() * 255) +
                    ")";

                this.tags.push({
                    ele: this.tagEle[i],
                    x,
                    y,
                    z
                });
            }
        },
        tag({
            ele,
            x,
            y,
            z
        }) {
            var scale = this.fallLength / (this.fallLength - z);
            var alpha = (z + this.RADIUS) / (2 * this.RADIUS);
            ele.style.fontSize = 15 * scale + "px";
            ele.style.opacity = alpha + 0.5;
            ele.style.filter = "alpha(opacity = " + (alpha + 0.5) * 100 + ")";
            ele.style.zIndex = parseInt(scale * 100);
            ele.style.left = x + this.CX - ele.offsetWidth / 2 + "px";
            ele.style.top = y + this.CY - ele.offsetHeight / 2 + "px";
        },
        animate() {
            this.timing = setInterval(() => {
                this.rotateX();
                this.rotateY();
                this.tags.forEach((item) => {
                    this.tag(item);
                });
            }, 200);
        },
        rotateX() {
            var cos = Math.cos(this.angleX);
            var sin = Math.sin(this.angleX);
            this.tags.forEach((item) => {
                var y1 = item.y * cos - item.z * sin;
                var z1 = item.z * cos + item.y * sin;
                item.y = y1;
                item.z = z1;
            });
        },
        rotateY() {
            var cos = Math.cos(this.angleY);
            var sin = Math.sin(this.angleY);
            this.tags.forEach((item) => {
                var x1 = item.x * cos - item.z * sin;
                var z1 = item.z * cos + item.x * sin;
                item.x = x1;
                item.z = z1;
            });
        },
        mouseOver(event) {
            var x = event.clientX - this.EX - this.CX;
            var y = event.clientY - this.EY - this.CY;
            this.angleY = x * 0.000001;
            this.angleX = y * 0.000001;
            if (!this.timing) this.animate();
        },
        mouseLeave(event) {
            var x = event.clientX - this.EX - this.CX;
            var y = event.clientY - this.EY - this.CY;
            this.angleY = x * 0.00001;
            this.angleX = y * 0.00001;
        },
        cheMouseOver() {
            clearInterval(this.timing);
            this.timing = null;
        },
        /* END */
        handleSizeChange(val) {
            this.pageInfo.pageSize = val
            this.getArticleList()

        },
        handleCurrentChange(val) {
            this.pageInfo.pageNum = val;
            this.getArticleList()
        },
        getArticleList() {
            this.$get('/article/list', {
                pageNum: this.pageInfo.pageNum,
                pageSize: this.pageInfo.pageSize
            }).then(res => {
                this.pageInfo = res.data;
                this.articles = res.data.list;
            })
        },
        toTag(e) {
            this.$router.push({
                path: '/tagdetail',
                query: {
                    id: e.id,
                    tagName: e.tagName
                }
            })
        },
        getHotArticles() {
            this.$get('/article/hot').then(res => {

                this.hotArticles = res.data;
            })
        },
        getLikeArticles() {
            this.$get('/article/asLike').then(res => {

                this.likeArticles = res.data;
            })
        },
        getRecommendArticles() {
            this.$get('/article/recommend').then(res => {

                this.recommendArticles = res.data;
            })
        },
        listTags() {
            this.$get("/tag/list").then(res => {
                this.tagList = res.data;
            });
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
    computed: {
        maxum() {
            return this.wordList.sort()[0].value;
        },
    },
    watch: {},
    created() {
        this.getArticleList();
        this.getHotArticles();
        this.getLikeArticles();
        this.getRecommendArticles();
        this.listTags();
    },
    mounted() {
        this.accountInfo = JSON.parse(sessionStorage.getItem('accountInfo'));

    },
    beforeUpdate() {
        this.tagEle = this.$refs.tagBall.children;
        this.paper = this.$refs.tagBall;
        this.CX = this.paper.offsetWidth / 2;
        this.CY = this.paper.offsetHeight / 2;
        this.EX =
            this.paper.offsetLeft +
            document.body.scrollLeft +
            document.documentElement.scrollLeft;
        this.EY =
            this.paper.offsetTop +
            document.body.scrollTop +
            document.documentElement.scrollTop;
        this.innit();
        this.animate();
    }

}
</script>
<style lang="less" scoped>
:deep(.el-link--inner) {
    height: 30px;
    font-weight: 550;
    color: black;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    text-align: left
}
.main-container {

    width: 1200px;
    height: 900px;
    margin: 0 auto;
    margin-top: 30px;
    min-width: 1000px;
    min-height: 3500px;

    .container-ad {
        width: 100%;
        height: 340px;
        margin: 0 auto;
        float: left;

        .box-card {
            width: 330px;
            height: 340px;
            margin: 0 auto;
            float: right;
            opacity: 0.7;

            .my-description {
                text-align: center;
                font-size: 16px;

            }

            .my-description p {
                height: 25px;
                margin: auto 0;
                line-height: 30px;
                padding: 3px 0;
            }

            .link-icon {
                margin: 0 10px;

                .iconfont {
                    font-size: 30px;
                }
            }

            .clearfix {
                height: 30px;
                line-height: 30px;
            }
        }

        .right-ad1 {
            width: 800px;
            margin-right: 20px;
            float: left;
        }

    }

    .article-list {
        width: 800px;
        margin-right: 10px;

        .bar {
            width: 100%;
            font-size: 12px;
            color: #999;
            padding: 0;
            margin-top: 10px;
        }

        .avatar:after {
            content: "";
            position: absolute;
            background: url(@/assets/static/images/vip.png) no-repeat;
            background-size: 12px;
            width: 12px;
            height: 12px;
            left: 50px;
            bottom: 20px;
        }

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

    .block h4 {
        font-size: 20px;
        font-weight: 700;
        margin-bottom: 0;
        margin-top: 0;
    }

    .aside-div {

        .aside-box-card {
            margin-top: 22px;
            opacity: 0.7;

            .tagBall {
                width: 300px;
                height: 300px;
                margin: 0;
                position: relative;

                .tag {
                    display: block;
                    position: absolute;
                    left: 0px;
                    top: 0px;
                    color: #000;
                    text-decoration: none;
                    font-size: 15px;
                    font-weight: bold;
                    background-clip: text;
                    cursor: pointer;
                }
            }

        }

    }

}
</style>
