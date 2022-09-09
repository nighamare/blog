<template>
<el-container>
    <el-header style="height: auto;">
        <div class="banner">
            <div class="article-info-container">
                <!-- 文章标题 -->
                <div class="article-title">{{ article.title }}</div>
                <div class="article-info">
                    <!-- 发表时间 -->
                    <span>
                        <i class="el-icon-time"></i>
                        发表于 {{ article.createTime | DateTimeFormat }}
                    </span>
                    <el-divider direction="vertical"></el-divider>
                    <!-- 发表时间 -->
                    <span>
                        <i class="el-icon-time" />
                        更新于
                        <template>
                            {{ article.lastUpdate | DateTimeFormat }}
                        </template>

                    </span>
                    <span class="separator">|</span>
                    <!-- 文章分类 -->
                    <!--  <span class="article-category">
                            <i class="el-icon-s-operation" />
                            <router-link :to="'/categories/' + article.categoryId">
                                {{ article.categoryName }}
                            </router-link>
                        </span> -->
                    <!-- 字数统计 -->
                    <span>
                        <i class="iconfont icon-shu" />
                        字数统计: {{ wordNum }}
                    </span>
                    <span class="separator">|</span>
                    <!-- 阅读时长 -->
                    <span>
                        <i class="iconfont icon-yuedu" />
                        阅读时长: {{ readTime }}
                    </span>
                    <span class="separator">|</span>
                    <!-- 阅读量 -->
                    <span>
                        <i class="iconfont icon-shu" /> 阅读量: {{ article.views }}
                    </span>
                    <span class="separator">|</span>
                    <!-- 评论量 -->
                    <span>
                        <i class="iconfont icon-pinglun" />评论数:
                        <template v-if="count">{{ count }}</template>
                        <template v-else>0</template>
                    </span>

                </div>
            </div>
        </div>
    </el-header>
    <el-container style="max-width: 1200px;margin: auto;">
        <el-main>
            <!-- 文章内容 -->
            <div>
                <el-card class="article-wrapper">
                    <article id="write" class="article-content markdown-body" v-html="article.content" ref="article"></article>
                    <!-- 版权声明 -->
                    <div>
                        <div class="tag-container">
                            <el-link :underline="false" v-for="item of tagList" :key="item.id" @click="toTag(item.id)">
                                {{ item.tagName }}
                            </el-link>
                        </div>
                    </div>
                    <div class="aritcle-copyright">
                        <div>
                            <span>文章作者：</span>
                            <el-link type="info" @click="toUser(authorInfo.id)" target="_blank">{{authorInfo.nickname}}</el-link>
                        </div>
                        <div>
                            <span>文章链接：</span>
                            <el-link type="info" :href="articleHref" target="_blank">{{ articleHref }} </el-link>
                        </div>
                        <div>
                            <span>版权声明：</span>本文版权归作者和本博客共有，转载前请注意查看
                            <el-popover placement="top-start" width="400" trigger="click">
                                欢迎转载，但未经作者同意，必须保留此段声明；必须在文章中给出原文连接；否则必究法律责任。
                                本博客所有文章除特别声明外，均采用
                                <el-link type="primary" href="https://creativecommons.org/licenses/by-nc-sa/4.0/" target="_blank">
                                    CC BY-NC-SA 4.0
                                </el-link>
                                许可协议。
                                <el-button style="padding: 0;" type="text" slot="reference">转载声明</el-button>
                            </el-popover>
                            ，转载时请注明文章出处。
                        </div>
                        <div>
                            <span>免责声明：</span>
                            本文中使用的部分图片来自于网络，如有侵权，请联系博主进行删除
                        </div>
                        <div style="height: 28px;">
                            <span>分 享 到 :</span>
                            <div class="share-container">
                                <el-link style="margin:0 5px ;" :href="shareUrl.qqZone.url" :underline="false" target="_blank">
                                    <i class="iconfont icon-QQkongjian"></i>
                                </el-link>
                                <!-- 分享到微信 -->
                                <el-link style="margin:0 5px ;" :href="shareUrl.wechat.url" :underline="false" target="_blank">
                                    <i class="iconfont icon-weixin"></i>
                                </el-link>
                                <!-- 分享到QQ -->
                                <el-link style="margin:0 5px ;" :href="shareUrl.qq.url" :underline="false" target="_blank">
                                    <i class="iconfont icon-QQ"></i>
                                </el-link>
                                <!-- 分享到微博 -->
                                <el-link style="margin:0 5px ;" :href="shareUrl.weibo.url" :underline="false" target="_blank">
                                    <i class="iconfont icon-weibo"></i>
                                </el-link>
                                <!-- 分享到知乎 -->
                                <el-link style="margin:0 5px ;" :href="shareUrl.zhihu.url" :underline="false" target="_blank">
                                    <i class="iconfont icon-shejiaotubiao-10"></i>
                                </el-link>

                            </div>

                        </div>
                    </div>
                    <!-- 点赞打赏等 -->
                    <div class="article-reward">
                        <!-- 点赞按钮 -->
                        <strong>
                            <el-button style="width: 50px;height: 50px;margin: 0 15px 5px;" @click="like(article.id)" plain circle>
                                <i style="font-size:20px" :class="isLike?'iconfont icon-dianzan-pink-copy':'iconfont icon-dianzan'"></i>
                            </el-button>
                            <span v-show="article.likes > 0">
                                {{ article.likes}}
                            </span>
                            <span>点赞</span>
                        </strong>
                        <strong>
                            <el-button style="width: 50px;height: 50px;margin: 0 15px 5px;" @click="collectionArticle" plain circle>
                                <i style="font-size:20px" :class="isCollect?'iconfont icon-shoucang1-copy':'iconfont icon-shoucang'"></i>
                            </el-button>
                            <span v-show="article.likeCount > 0">
                                {{ article.likeCount }}
                            </span>
                            <span>收藏</span>
                        </strong>
                        <strong>
                            <el-button style="width: 50px;height: 50px;margin: 0 15px 5px;" @click="centerDialogVisible = true" plain circle>
                                <!-- 打赏按钮 -->
                                <i style="font-size:20px" class="iconfont icon-qian2"></i>
                                <!-- 二维码 -->
                            </el-button>
                            <span>打赏</span>
                        </strong>

                        <strong>
                            <el-button style="width: 50px;height: 50px;margin: 0 15px 5px;" @click="like" plain circle>
                                <i style="font-size:20px" class="iconfont icon-jubao"></i>
                                <span v-show="article.likeCount > 0">
                                    {{ article.likeCount }}
                                </span>
                            </el-button>
                            <span>举报</span>
                        </strong>
                        <el-dialog :visible.sync="centerDialogVisible" width="900px" center>
                            <template #title>
                                <span style="color: red;font-size: 16px;font-weight: 700;">暂未开通博主打赏功能！！！</span>
                            </template>
                            <div style="text-align: center;">
                                <span style="color: red;font-size: 16px;padding: 5px;">赞助本网站</span>
                                <br>
                                <div style="height: 100%;">
                                    <div style="width: 400px;float: left;">
                                        <el-image style="width: 350px;" :src="require('@/assets/static/images/wxpay.png')"></el-image>
                                    </div>
                                    <div style="width: 400px;float: left;">
                                        <el-image style="width: 350px;" :src="require('@/assets/static/images/alipay.jpg')"></el-image>
                                    </div>
                                </div>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="centerDialogVisible = false">关 闭</el-button>
                            </span>
                        </el-dialog>

                    </div>
                    <el-divider></el-divider>
                    <!-- 上一篇，下一篇 -->
                    <!-- 推荐 -->
                    <div class="comment-title"><i class="iconfont icon-guanlian"></i>推荐</div>
                    <div class="recommend-div">
                        <div v-for="(item) in articleSimilarList" :key="item.articleId" class="recommend-item">
                            <div style="height: 24px;overflow: hidden;width: 100%;">
                                <el-link @click="toArticle(item.articleId)" style="display: block;">
                                    <strong class="recommend-strong">
                                        {{ item.title}}</strong>
                                </el-link>
                                <el-link @click="toUser(item.accountId)" style="display: block;">
                                    <span class="rec-author">
                                        {{ item.nickname}}的博客</span>
                                </el-link>
                            </div>
                            <div class="con-div">
                                <el-link :underline="false" @click="toArticle(item.articleId)" style="display: block;">
                                    <span>{{ item.summary }}</span>
                                </el-link>
                            </div>
                        </div>

                    </div>
                    <!-- 分割线 -->
                    <hr />
                    <!-- 评论 -->
                    <comment :commentList="commentList" :count="count" :pageInfoProp="pageInfo" @handleRootSizeChange="handleRootSizeChange" @handleRootCurrentChange="handleRootCurrentChange" @reloadComment="listComment" :upId="authorInfo.accountId" />
                </el-card>
            </div>
        </el-main>
        <el-aside width="340px" style="padding: 20px;">
            <!-- 作者信息 -->
            <el-card class="first-right-container" shadow="hover">
                <div slot="header" class="clearfix">
                    <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                    <span style="font-size: 20px;font-weight: 700;">博主信息</span>
                </div>
                <div>
                    <div style="text-align: center;">
                        <el-avatar :size="70" :src="authorInfo.avatar">
                        </el-avatar>
                    </div>
                    <div class="my-description">
                        <p>{{ authorInfo.nickname }}</p>
                        <p>Bug开发工程师</p>
                        <p>lycorisdeve@qq.com</p>
                    </div>

                </div>
            </el-card>
            <!-- 文章目录 -->
            <el-card class="right-container">
                <div slot="header" class="clearfix">
                    <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="el-icon-postcard"></i>
                    <span style="font-size: 20px;font-weight: 700;">目录</span>
                </div>
                <div id="toc"></div>
            </el-card>
            <!-- 最新文章 -->
            <el-card class="right-container" shadow="hover">
                <div slot="header" class="clearfix">
                    <i style="font-size: 20px;color: #ff9401;margin-right: 10px;" class="iconfont icon-fire"></i>
                    <span style="font-size: 20px;font-weight: 700;">最热文章</span>
                </div>
                <div>
                    <div class="my-description">
                        <span v-for="(article, index) in hotArticles" :key="index">
                            <el-link @click="toArticle(article.id)" :underline="false" style="height: 30px;font-weight: 550;color: black;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;text-align: left">
                                <i style="font-size: 14px;color: #437dff;font-weight: 700;padding: 0 5px 0 0;">{{
                                            index + 1
                                    }}</i>
                                {{ article.title }}
                            </el-link>
                        </span>
                    </div>
                </div>
            </el-card>
        </el-aside>
    </el-container>
</el-container>
</template>

<script>
import Comment from "./components/Comment.vue";
import Clipboard from "clipboard";
import tocbot from "tocbot";
import hljs from "highlight.js";
import "highlight.js/styles/atom-one-dark.css";
import MarkdownIt from "markdown-it";
import {
    Message
} from "element-ui";

export default {
    name: "ArticleDetails",
    components: {
        Comment
    },

    data() {
        return {
            isCollect: false,
            accountInfo: {},
            isLike: false,
            authorInfo: {},
            centerDialogVisible: false,
            tagList: [],
            imgList: [],
            articleLatestList: [],
            hotArticles: [],
            pageInfo: '',
            commentList: [],
            count: 0,
            wordNum: "",
            readTime: "",
            articleHref: window.location.href,
            clipboard: null,
            shareUrl: {
                qqZone: {
                    id: 1,
                    url: "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=" + window.location.href + "&sharesource=qzone&title=博客&summary=我的博客"
                },
                wechat: {
                    id: 2,
                    url: "http://qr.liantu.com/api.php?text=" + window.location.href
                },
                weibo: {
                    id: 3,
                    url: "http://service.weibo.com/share/share.php?url=" + window.location.href + "&title=博客&pic=&appkey="
                },
                qq: {
                    id: 4,
                    url: "http://connect.qq.com/widget/shareqq/index.html?url=" + window.location.href + "&title=博客&source=我的博客"
                },
                zhihu: {
                    id: 5,
                    url: "https://www.zhihu.com/share?url=" + window.location.href + "&title=博客&pic=&appkey="
                }
            },

            article: {
                id: 100004,
                accountId: '', //作者id
                title: '', //文章标题
                summary: '', //文章摘要
                content: '', //文章内容
                blogTypeId: '', //文章类型
                status: '', //文章状态
                published: '', //是否发布
                commentOpen: '', //是否开启评论
                appreciation: '', //是否开启赞赏
                recommend: '', //是否开启推荐
                reprint: '', //是否转载
                reprintUrl: '', //转载地址
                blogImg: '', //文章封面
                views: '', //浏览量
                commentNum: '', //评论数
                likes: '', //点赞数
                createTime: '', //创建时间
                lastUpdate: '', //最后更新时间
            },
            articleSimilarList: [],

        };
    },
    methods: {
        collectionArticle() {
            this.isCollect = !this.isCollect
            const h = this.$createElement;

            this.$notify({
                title: '消息提示',
                message: h('i', {
                    style: 'color: teal'
                }, '暂未开通此项功能！')
            });
        },
        getArticle() {
            if (this.article.id === undefined) {
                this.$router.push({
                    path: '/404'
                }) //如果文章id为空，跳转到404页面

            }
            const _this = this;
            //查询文章
            this.$get("/article/" + this.article.id).then(res => {
                document.title = res.data.title;
                //将markdown转换为Html
                this.markdownToHtml(res.data);
                this.$nextTick(() => {
                    // 统计文章字数
                    this.wordNum = this.deleteHTMLTag(this.article.content).length;
                    // 计算阅读时间
                    this.readTime = Math.round(this.wordNum / 400) + "分钟";
                    // 添加代码复制功能
                    this.clipboard = new Clipboard(".copy-btn");
                    this.clipboard.on("success", () => {
                        Message({
                            type: "success",
                            message: "复制成功",
                            showClose: true,
                            center: true
                        });
                    });
                    // 添加文章生成目录功能
                    let nodes = this.$refs.article.children;
                    if (nodes.length) {
                        for (let i = 0; i < nodes.length; i++) {
                            let node = nodes[i];
                            let reg = /^H[1-4]{1}$/;
                            if (reg.exec(node.tagName)) {
                                node.id = i;
                            }
                        }
                    }
                    tocbot.init({
                        tocSelector: "#toc", //要把目录添加元素位置，支持选择器
                        contentSelector: ".article-content", //获取html的元素
                        headingSelector: "h1, h2, h3, h4, h5, h6", //要显示的id的目录
                        hasInnerContainers: true,
                        collapseDepth: 6,
                        onClick: function (e) {
                            e.preventDefault();
                        }
                    });
                    // 添加图片预览功能
                    const imgList = this.$refs.article.getElementsByTagName("img");
                    for (var i = 0; i < imgList.length; i++) {
                        this.imgList.push(imgList[i].src);
                        imgList[i].addEventListener("click", function (e) {
                            _this.previewImg(e.toElement.src);
                        });
                    }
                });
            });
        },
        listComment() {
            if (this.article.id === undefined) {
                this.$router.push({
                    path: '/404'
                }) //如果文章id为空，跳转到404页面
            }
            let pageNum;
            let pageSize;
            if (!this.pageInfo.pageNum) {
                pageNum = 1;
            } else {
                pageNum = this.pageInfo.pageNum
            }
            if (!this.pageInfo.pageSize) {
                pageSize = 5;
            } else {
                pageSize = this.pageInfo.pageSize
            }
            //查询评论
            this.$get("/comment/list/" + this.article.id, {
                pageNum: pageNum,
                pageSize: pageSize
            }).then(res => {
                this.pageInfo = res.data;
                this.commentList = res.data.list;
                this.count = res.count;
            });
        },

        like(id) {
            this.isLike = !this.isLike;
            // 判断登录
            if (!this.accountInfo) {
                this.$router.push({
                    path: '/login'
                })
                Message.warning("请先登录！")
                return false;
            }
            //发送请求
            let params = {
                id: id,
                accountId: this.accountInfo.id,
                isLiked: this.isLike,
            }
            if (this.isLike) {
                this.article.likes++

                this.$post('/article/likes', params).then(res => {
                    Message.success('感谢你的点赞支持！')

                })
            } else {
                this.article.likes--
                this.$post('/article/likes', params).then(res => {

                })

            }
        },
        markdownToHtml(article) {
            const md = new MarkdownIt({
                html: true,
                linkify: true,
                typographer: true,
                highlight: (str, lang) => {
                    // 当前时间加随机数生成唯一的id标识
                    var d = new Date().getTime();
                    if (
                        window.performance && typeof window.performance.now === "function"
                    ) {
                        d += performance.now();
                    }
                    const codeIndex = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,
                        (c) => {
                            var r = (d + Math.random() * 16) % 16 | 0;
                            d = Math.floor(d / 16);
                            return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
                        }
                    );
                    // 复制功能主要使用的是 clipboard.js
                    let html = `<button class="copy-btn el-icon-document-copy" type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}"></button>`;
                    const linesLength = str.split(/\n/).length - 1;
                    // 生成行号
                    let linesNum = '<span aria-hidden="true" class="line-numbers-rows">';
                    for (let index = 0; index < linesLength; index++) {
                        linesNum = linesNum + "<span></span>";
                    }
                    linesNum += "</span>";
                    if (lang && hljs.getLanguage(lang)) {
                        // highlight.js 高亮代码
                        const preCode = hljs.highlight(str, {
                            language: lang,
                            ignoreIllegals: true
                        }).value;
                        html = html + preCode;
                        if (linesLength) {
                            html += '<b class="name">' + lang + "</b>";
                        }
                        // 将代码包裹在 textarea 中，由于防止textarea渲染出现问题，这里将 "<" 用 "<" 代替，不影响复制功能
                        return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(/<\/textarea>/g, "</textarea>")}</textarea>`;
                    }
                }
            });
            // 将markdown替换为html标签
            article.content = md.render(article.content);
            this.article = article;
        },
        // 获得相似文章
        getSimilarArticles() {
            if (this.article.id === undefined) {
                this.$router.push({
                    path: '/404'
                }) //如果文章id为空，跳转到404页面

            }
            this.$get("/article/similar/" + this.article.id).then(res => {
                this.articleSimilarList = res.data;
            });
        },
        previewImg(img) {
            this.$imagePreview({
                images: this.imgList,
                index: this.imgList.indexOf(img)
            });
        },
        deleteHTMLTag(content) {
            return content
                .replace(/<\/?[^>]*>/g, "")
                .replace(/[|]*\n/, "")
                .replace(/&npsp;/gi, "");
        },
        getTagList() {
            this.$get("/articleTag/list", {
                articleId: this.article.id
            }).then(res => {
                this.tagList = res.data;
            });
        },
        //获得作者信息
        getAuthorInfo() {
            this.$get("/account/author/" + this.article.id).then(res => {
                this.authorInfo = res.data;
            });
        },
        handleRootSizeChange(val) {
            this.pageInfo.pageSize = val;
            this.pageInfo.pageNum = 1;
            this.listComment();
        },
        handleRootCurrentChange(val) {
            this.pageInfo.pageNum = val;
            this.listComment();
        },
        toTag() {
            this.$router.push({
                path: '/tag',
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
        toArticle(e) {
            // window.location.href = '#/article?id=' + e
            this.$router.push({
                path: '/article',
                query: {
                    id: e
                }
            })
        },
        getHotArticles() {
            this.$get('/article/hot').then(res => {

                this.hotArticles = res.data;
            })
        },
        getPraiseInfo(){
            this.$get('/article/praise').then(res => {
                this.isLike= res.data
            })
        }

    },
    mounted() {
        this.getAuthorInfo();
        this.getArticle();
        this.getTagList();
        this.getSimilarArticles();
        this.listComment();
        this.getHotArticles();
    },
    created() {
        // 获取url中的文章ID
        this.article.id = this.$route.query.id;
        if (this.article.id === undefined) {
            this.$router.push({
                path: '/404'
            }) //如果文章id为空，跳转到404页面
        }
        this.accountInfo = JSON.parse(sessionStorage.getItem("accountInfo"))
    },
    watch: {
        '$route'(to, from) {
            location.reload();
        }
    },
    destroyed() {
        this.clipboard.destroy();
        tocbot.destroy();
    },
};
</script>

<style lang="less">
@import "@/assets/static/css/markdown.css";

.comment-title {
    display: flex;
    align-items: center;
    font-size: 1.25rem;
    font-weight: bold;
    line-height: 40px;
    margin-bottom: 10px;
}

.comment-title i {
    font-size: 1.5rem;
    margin-right: 5px;
}

pre.hljs {
    padding: 12px 2px 12px 40px !important;
    border-radius: 5px !important;
    position: relative;
    font-size: 14px !important;
    line-height: 22px !important;
    overflow: hidden !important;
    background-color: #1e1e1e !important;

    &:hover .copy-btn {
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }

    code {
        display: block !important;
        margin: 0 10px !important;
        overflow-x: auto !important;

        &::-webkit-scrollbar {
            z-index: 11;
            width: 6px;
        }

        &::-webkit-scrollbar:horizontal {
            height: 6px;
        }

        &::-webkit-scrollbar-thumb {
            border-radius: 5px;
            width: 6px;
            background: #666;
        }

        &::-webkit-scrollbar-corner &::-webkit-scrollbar-track {
            background: #1e1e1e;
        }

        &::-webkit-scrollbar-track-piece {
            background: #1e1e1e;
            width: 6px;
        }
    }

    .line-numbers-rows {
        position: absolute;
        pointer-events: none;
        top: 12px;
        bottom: 12px;
        left: 0;
        font-size: 100%;
        width: 40px;
        text-align: center;
        letter-spacing: -1px;
        border-right: 1px solid rgba(0, 0, 0, 0.66);
        user-select: none;
        counter-reset: linenumber;

        span {
            pointer-events: none;
            display: block;
            counter-increment: linenumber;

            &:before {
                content: counter(linenumber);
                color: #999;
                display: block;
                text-align: center;
            }
        }
    }

    b.name {
        position: absolute;
        top: 7px;
        right: 45px;
        z-index: 1;
        color: #999;
        pointer-events: none;
    }

    .copy-btn {
        position: absolute;
        top: 6px;
        right: 6px;
        z-index: 1;
        color: #ccc;
        background-color: #525252;
        border-radius: 6px;
        display: none;
        font-size: 14px;
        width: 32px;
        height: 24px;
        outline: none;
        cursor: pointer;
    }
}

.right-container ol {
    padding-left: 0 !important;
}

.toc-list-item {
    line-height: 2;
    list-style: none;
}

.toc-link {
    display: block;
    border-left: 3px solid transparent;
    padding-left: 6px;
    color: #666261 !important;
    transition: all .2s ease-in-out;
    // 下划线
    text-decoration: none;
}

.toc-link:hover {
    border-left: 3px solid #666261;
    padding-left: 3px;
    color: #666261 !important;

}

.is-active-link {
    border-left-color: #009d92;
    background: #00c4b6;
    color: #fff !important;
}
</style><style lang="less" scoped>
:deep(.el-header) {
    padding: 0;
}

:deep(.el-header .el-row) {
    margin: 0;
}

@media (min-width: 760px) {
    .banner {
        color: #eee !important;
        background-color: rgba(0, 0, 0, 0.5);
        height: 300px;

        .article-info-container {
            width: 70%;
            text-align: center;
            margin: auto;
            padding: 100px;

            .article-info i {
                font-size: 14px;
            }

            .article-info {
                font-size: 14px;
                line-height: 1.9;
                display: inline-block;

                .separator:first-child {
                    display: none;
                }
            }

            .article-title {
                font-size: 35px;
                font-weight: 700;
                overflow: hidden;
                height: 45px;
                display: -webkit-box;
                /*作为弹性伸缩盒子模型显示*/
                -webkit-line-clamp: 1;
                /*显示的行数；如果要设置2行加...则设置为2*/
                overflow: hidden;
                /*超出的文本隐藏*/
                text-overflow: ellipsis;
                /* 溢出用省略号*/
                -webkit-box-orient: vertical;
                /*伸缩盒子的子元素排列：从上到下*/
            }

        }

    }

    .article-info span {
        font-size: 95%;
    }

    .second-line.third-line {
        display: inline;
    }

}

.share-container {
    float: right;
    height: 28px;
    width: 300px;

}

.share-container i {
    float: right;
    font-size: 32px;
    line-height: 30px;
}

.share-container a {
    float: right;
    font-size: 32px;
    line-height: 30px;
}

.article-content {
    word-break: break-word;
    font-size: 14px;
    line-height: 2;
}

.article-category a {
    color: #fff !important;
}

.tag-container a {
    display: inline-block;
    margin: 0.5rem 0.5rem 0.5rem 0;
    padding: 0 0.75rem;
    width: fit-content;
    border: 1px solid #67c23a;
    border-radius: 1rem;
    color: #67c23a !important;
    font-size: 12px;
    line-height: 2;
}

.tag-container a:hover {
    color: #fff !important;
    background: #67c23a;
    transition: all 0.5s;
}

.aritcle-copyright {
    position: relative;
    margin-top: 40px;
    margin-bottom: 10px;
    font-size: 0.875rem;
    line-height: 2;
    padding: 0.625rem 1rem;
    border: 1px solid #eee;
}

.aritcle-copyright span {
    color: #49b1f5;
    font-weight: bold;
}

.first-right-container {
    padding: 20px 24px;
    font-size: 14px;

    :deep(.el-card__body) {
        padding: 0;
    }
}

.right-container {
    padding: 20px 24px;
    font-size: 14px;
    margin-top: 20px;

    :deep(.el-card__body) {
        padding: 0;
    }

}

.article-reward {
    text-align: center;

    strong {
        display: inline-block;
        width: 80px;
        text-align: center;
        position: relative;
        font-weight: 400;
    }
}

.recommend-div {
    background-color: #fff;
    font-style: normal;
    border-top: 1px solid #eee;
    display: block;
    align-items: center;

    .recommend-item {
        border-top: 1px solid #eee;
        padding: 8px 24px;

    }

    .con-div {
        height: 40px;
        overflow: hidden;
        text-overflow: ellipsis;

        a {
            color: #999aaa;
            font-size: 14px;
            display: block;
            height: 40px;
            overflow: hidden;
            text-overflow: clip;
            text-decoration: none;
        }

        span {
            color: #999aaa;
            font-size: 14px;
            display: block;
            height: 40px;
            overflow: hidden;
            text-overflow: clip;
        }
    }

    .recommend-strong {
        color: #333;
        display: block;
        width: 70%;
        height: 24px;
        font-size: 16px;
        line-height: 24px;
        overflow: hidden;
        float: left;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .rec-author {
        display: block;
        width: 30%;
        height: 24px;
        color: #999aaa;
        font-size: 13px;
        line-height: 24px;
        overflow: hidden;
        float: right;
        text-align: right;
    }

}

.clearfix {
    height: 30px;
    line-height: 30px;
}

.my-description {
    text-align: center;
    font-size: 16px;

    :deep(.el-link--inner) {
        height: 30px;
        width: 100%;
        line-height: 30px;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    :deep(.el-link--inner span) {
        height: 30px;
        width: 100%;
        line-height: 30px;
        overflow: hidden;
        text-overflow: ellipsis;
    }

}

.my-description p {
    height: 25px;
    margin: auto 0;
    line-height: 30px;
    padding: 3px 0;
    overflow: hidden;
    text-overflow: ellipsis;
}

.my-description span {
    overflow: hidden;
    text-overflow: ellipsis;
}

hr {
    position: relative;
    margin: 20px auto 40px;
    border: 2px dashed #d2ebfd;
    width: calc(100% - 4px);
}

:deep(.el-card__header) {

    padding: 0 20px 20px 20px;
    border-bottom: 1px solid #EBEEF5;
    box-sizing: border-box;

}
</style>
