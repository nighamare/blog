<template>
<div>
    <div class="serach">
        <el-form v-model="formData" :inline="true">
            <el-form-item label="文章ID">
                <el-input @input="forceUpdate" type="text" v-model="formData.id" placeholder="请输入文章ID" clearable />
            </el-form-item>

            <el-form-item class="btn">
                <el-button type="primary" @click="searchSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
            <el-form-item label="选择要查看的文章状态" style="margin-left: 200px;">
                <el-select v-model="formData.status" @change="onChange" style="width: 300px" placeholder="请选择要查看文章的状态" clearable>
                    <el-option value="" label="全部" />
                    <el-option :value="0" label="已发布" />
                    <el-option :value="1" label="草稿" />
                    <el-option :value="2" label="已删除" />
                    <el-option :value="3" label="待审核" />
                    <!-- <el-option :value="4" label="审核不通过" /> -->
                </el-select>

            </el-form-item>

        </el-form>

    </div>
    <div style="float: left;">
        <el-button :multipleSection="multipleSection" @click="batchDeleteBuild(multipleSection)" plain :disabled="multipleSection.length === 0">批量审核</el-button>
    </div>
    <!-- Main -->
    <CommonTable @handleCurrentChange="selectObjectList" @handleSizeChange="selectObjectList" @sendSelectIds="getSelectIds" :pageInfoProps="pageInfo" :columnsProps="columns">
        <template #action="scope">
            <el-button-group>
                <el-tooltip class="item" :hide-after="800" effect="dark" content="预览文章" placement="top">
                    <el-button class="btn-action" type="primary" size="small" @click="previewArticle(scope.row)">
                        <i class="el-icon-edit"></i>预览
                    </el-button>
                </el-tooltip>
                <el-tooltip v-if="scope.row.status===3" class="item" :hide-after="800" effect="dark" content="审核通过" placement="bottom">
                    <el-button class="btn-action" type="success" size="small" @click="approvAudit(scope.row)">
                        <i class="el-icon-circle-check"></i>通过
                    </el-button>
                </el-tooltip>
                <el-tooltip v-if="scope.row.status===3" class="item" :hide-after="800" effect="dark" content="删除文章" placement="bottom">
                    <el-button class="btn-action" type="danger" size="small" @click="deleteObject(scope.row)">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </el-tooltip>
            </el-button-group>
        </template>
    </CommonTable>
    <el-dialog :visible.sync="dialogFormVisible">
        <template #title>
            <!-- 预览文章 -->
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
                    推荐阅读时长: {{ readTime }}
                </span>
                <span class="separator">|</span>
            </div>
        </template>
        <div>
            <div class="article-container">
                <article id="write" class="article-content markdown-body" v-html="article.content" ref="article"></article>
                <!-- 版权声明 -->
                <div>
                    <div class="tag-container">
                        <el-tag style="margin-right: 10px" type="success" v-for="item of tagList" :key="item.id">
                            {{ item.tagName }}
                        </el-tag>  
                    </div>
                </div>
            </div>
            <!-- title End -->
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">返 回</el-button>
                <el-button type="success" @click="passAudit">审核通过</el-button>
            </div>
        </div>
    </el-dialog>

</div>
</template>

<script>
import {
    Message,
    Loading,
    MessageBox
} from 'element-ui'
import hljs from "highlight.js";
import "highlight.js/styles/atom-one-dark.css";
import MarkdownIt from "markdown-it";

export default {

    name: "ArticleList",
    components: {},
    data() {
        return {
            tagList: [],
            imgList: [],
            count: 0,
            wordNum: "",
            readTime: "",
            dialogFormVisible: false,
            multipleSection: [],
            deleteUsernames: [],
            //删除记录的code
            deleteIds: [],
            formData: {
                id: '',
                status: 3,
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
            objectInfo: {
                id: 0,
                accountId: '',
                title: '',
                summary: '',
                content: '',
                categoryId: '',
                status: '',
                commentOpen: '',
                appreciation: '',
                recommend: '',
                reprint: '',
                reprintUrl: '',
                blogImg: '',
                views: '',
                commentNum: '',
                likes: '',
                createTime: '',
                lastUpdate: '',

            },
            // 获取每一行的数据
            index_x: [],
            pageInfo: [],
            columns: [{
                    prop: 'accountId',
                    label: '博主ID'
                },
                {
                    prop: 'id',
                    label: '博客ID'
                },

                {
                    prop: 'title',
                    label: '博客标题'
                },
                {
                    prop: 'summary',
                    label: '博客摘要'
                },
                {
                    prop: 'content',
                    label: '博客内容'
                },
                {
                    prop: 'categoryId',
                    label: '博客类型ID'
                },
                {
                    prop: 'status',
                    label: '状态'
                },
                {
                    prop: 'commentOpen',
                    label: '是否开启评论'
                },
                {
                    prop: 'appreciation',
                    label: '是否开启收藏'
                },

                {
                    prop: 'recommend',
                    label: '是否开启推荐'
                },
                {
                    prop: 'reprint',
                    label: '是否转载'
                },
                {
                    prop: 'reprintUrl',
                    label: '转载地址'
                },
                // {
                //     prop: 'blogImg',
                //     label: '博客封面图片路径'
                // },
                {
                    prop: 'views',
                    label: '点击数'
                },
                {
                    prop: 'commentNum',
                    label: '评论数'
                },
                {
                    prop: 'likes',
                    label: '点赞数'
                },
                {
                    prop: 'createTime',
                    label: '发表时间'
                },
                {
                    prop: 'lastUpdate',
                    label: '最后修改时间'
                },

            ]

        }
    },
    methods: {
        deleteObject(objectInfo) {
            MessageBox.confirm(`是否确认删除${objectInfo.title}?`, '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(() => {
                const service = Loading.service({
                    text: `正在删除文章...`,
                    target: '.el-main'
                })
                this.$delete('/article/delete/' + objectInfo.id).then(rs => {
                    Message.success(`删除${objectInfo.title}成功！`)
                    this.searchSubmit()
                }).finally(() => service.close())
            })

        },
        passAudit(){
            this.approvAudit(this.article)
            dialogFormVisible = false
        },
        approvAudit(e){
            this.$put('/article/passaudit/'+e.id).then(res=>{
                this.selectObjectList();
                Message({
                    type: "success",
                    message: '审核通过'
                })
            })
        },
        previewArticle(e) {
            if (!e.id) {
                this.selectObjectList()
                Message.error('系统出错，请重新尝试！')
                return;
            }
            this.dialogFormVisible = true
            this.article.id = e.id
            // test
            this.getArticle();
            this.getTagList();
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
                this.article = res.data
                //将markdown转换为Html
                this.markdownToHtml(res.data);
                this.$nextTick(() => {
                    // 统计文章字数
                    this.wordNum = this.deleteHTMLTag(this.article.content).length;
                    // 计算阅读时间
                    this.readTime = Math.round(this.wordNum / 400) + "分钟";
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
        onChange() {
            this.selectObjectList()
        },

        selectObjectList(e) {
            var _this = this;
            if (e) {
                if (e.pageNum) {
                    _this.pageInfo.pageNum = e.pageNum
                }
                if (e.pageSize) {
                    this.pageInfo.pageSize = e.pageSize
                }
            }
            let formData = _this.formData
            let pageNum = _this.pageInfo.pageNum
            let pageSize = _this.pageInfo.pageSize
            this.$get('/article/audit', {
                ...formData,
                pageNum: pageNum,
                pageSize: pageSize
            }).then(rs => {
                _this.pageInfo = rs.data

            })
        },

        searchSubmit() {
            this.selectObjectList()
        },

        getSelectIds(val) {

            this.multipleSection = val
        },
        //批量删除选中数据方法
        async batchDeleteBuild() {
            //判断是否选择了数据

            if (this.multipleSection.length === 0) {
                alert("请选择要审核的数据");
                return;
            }
            //如果有选中的数据，那么弹出消息框
            const confirmDelete = await this.$confirm(
                "此操作会审核通过文章信息，是否通过？",
                "提示", {
                    confimrButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
            ).catch(err => err);
            //如果用户确认删除，则返回字符串confirm
            //如果用户取消删除，则返回字符串cancel
            if (confirmDelete != "confirm") {
                return Message.info("已取消");
            }
            //将选中的数据推到deleteIds数组中
            for (var i = 0; i < this.multipleSection.length; i++) {
                var j = i;
                var id = this.multipleSection[j].id;
                var name = this.multipleSection[j].id;
                this.deleteIds.push(id);
                this.deleteUsernames.push(name);
            }
            //删除deleteIds中的数据
            let ids = this.deleteIds
            this.$put('/article/allpass?' + ids.map(id => 'ids=' + id).join('&')).then(rs => {
                Message.success(`批量审核${this.deleteUsernames}成功！`)
                this.searchSubmit()
            }).then(rs => {
                this.deleteIds = [];
            })

        },
        forceUpdate() {
            this.$forceUpdate()
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
                    let html = ''
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

    },
    mounted() {
        this.selectObjectList(this.pageInfo)

    },
    // 数字中间加*号 value是传的参数值(后端返回的，需要你处理的数据)
    filters: {
        changePwd(value) {
            const start = value.slice(0, 3)
            const end = value.slice(-3)
            return `${start}***${end}`
        }
    },
}
</script>

<style lang="less" scoped>
:deep(.btn-action) {
    padding: 9px 10px;
}

.item {
    margin: 4px;
}

.article-title {
    font-size: 35px;
    font-weight: 700;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: inherit;
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

.article-info i {
    font-size: 14px;
}

.article-info {
    font-size: 12px;
    line-height: 1.9;
    text-align: center;
    margin: auto;

    .separator:first-child {
        display: none;
    }
}

.article-info span {
    font-size: 12px;
    color: #999
}
.tag-container{
    margin-bottom: 10px;
}
.dialog-footer{
    text-align: right;
}
</style>
