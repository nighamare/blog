<template>
    <div v-loading="loading" class="post-article">
        <div style="width: 1100px;margin: auto;">
            <el-form :inline="true" style="text-align: left;" label-position="left" label-width="80px" :model="article">
                <el-form-item label="文章版权">
                    <el-select v-model="article.reprint" placeholder="选择版权">
                        <el-option v-for="item in copyrightList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="转载地址" v-if="article.reprint === 1">
                    <el-input v-model="article.reprintUrl" placeholder="请输入转载地址..." style="width: 320px;"></el-input>
                </el-form-item>
                <el-form-item label="原文地址" v-if="article.reprint === 2">
                    <el-input v-model="article.reprintUrl" placeholder="请输入原文地址..." style="width: 320px;"></el-input>
                </el-form-item>
                <el-form-item label="文章栏目">
                    <el-select v-model="article.blogTypeId" placeholder="选择文章栏目">
                        <el-option v-for="item in 4" :key="item" :label="item" :value="item">
                        </el-option>
                        <!-- <el-option v-for="item in 4" :key="item.cid" :label="item.cateName" :value="item.cid">
                </el-option> -->
                    </el-select>
                </el-form-item>

                <el-form-item label="文章标题">
                    <el-input v-model="article.title" maxlength="10" show-word-limit placeholder="请输入标题..."
                        style="width: 950px;"></el-input>
                </el-form-item>
                <el-form-item label="文章摘要">
                    <el-input v-model="article.summary" type="textarea" maxlength="500" :show-word-limit="true"
                        :autosize="{ minRows: 2, maxRows: 5 }" placeholder="请写一下关于文章的摘要，这将让你的博客显示在首页时，帮助你吸引更多的读者"
                        style="margin-top: 10px;width: 950px">
                    </el-input>
                </el-form-item>

                <el-form-item label="文章内容" style="max-width: 1000px;">
                    <!--mavon-editor  -->
                    <div id="editor">
                        <mavon-editor v-model="article.content" style="height:700px;" :ishljs="true"
                            codeStyle="github-dark" :xssOptions="xssOptions" ref=md @imgAdd="$imgAdd" @change="onChange"
                            @imgDel="$imgDel"></mavon-editor>
                    </div>
                </el-form-item>
                <el-form-item label="文章标签" style="max-width: 1000px;">
                    <div>
                        <tag-select style="height: 100%;width: 1030px;" :tagList="tags" @getTagList="getTagList">
                        </tag-select>
                    </div>
                </el-form-item>
                <el-form-item label="开启功能">
                    <div class="inline fields" style="margin-top: 10px;margin-left: 155px">
                        <el-checkbox v-model="article.recommend">推荐</el-checkbox>
                        <el-checkbox v-model="article.appreciation">点赞</el-checkbox>
                        <el-checkbox v-model="article.commentOpen">评论</el-checkbox>
                    </div>

                </el-form-item>
                <el-form-item label-width="0">
                    <div class="sub-btn-div">
                        <el-button @click="cancelEdit" v-if="from != ''">放弃修改</el-button>
                        <template v-if="from === '' || from === 'draft'">
                            <el-button @click="saveBlog(0)">保存草稿</el-button>
                            <el-button type="primary" @click="saveBlog(1)">发表文章</el-button>
                        </template>
                        <template v-else>
                            <el-button type="primary" @click="saveBlog(1)">保存修改</el-button>
                        </template>
                    </div>
                </el-form-item>

            </el-form>
        </div>

    </div>
</template>

<script>
// 导入组件 及 组件样式
import {
    mavonEditor
} from 'mavon-editor'
import TagSelect from '../article/components/TagSelect.vue'
import 'mavon-editor/dist/css/index.css'
export default {
    name: 'WriteArticle',
    components: {
        mavonEditor,
        TagSelect
    },
    data() {
        return {
            tags: [],
            content: '',
            html: '',
            configs: {},
            categories: [],
            tagInputVisible: false,
            tagValue: '',
            loading: false,
            from: '',
            copyrightList: [{
                id: 0,
                name: '原创'
            },
            {
                id: 1,
                name: '转载'
            },
            {
                id: 2,
                name: '翻译'
            }
            ],
            xssOptions: {
                whiteList: {
                    span: ['style']
                }
            },

            article: {
                id: '',
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
            }
        }
    },
    methods: {
        // 将图片上传到服务器，返回地址替换到md中
        $imgAdd(pos, $file) {
            // 第一步.将图片上传到服务器.
            var formdata = new FormData();
            formdata.append('image', $file);
            this.$post('/url', formdata).then((url) => {
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                /**
                 * $vm 指为mavonEditor实例，可以通过如下两种方式获取
                 * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
                 * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
                 */
                $vm.$img2Url(pos, url);
            })
        },
        // 删除图片
        $imgDel(pos, $file) {
            // 获得图片地址
            let url = $file.url;
            // 删除图片
            this.$post('/删除接口地址', {
                url: url
            }).then(res => {
                console.log(res.data);
                $vm.$img2Url(pos, '');
            }).catch(err => {
                console.log(err)
            })
        },
        // 所有操作都会被解析重新渲染
        onChange(value, render) {
            // render 为 markdown 解析后的结果[html]
            this.html = render;
        },
   

        // 取消编辑
        cancelEdit() {
            this.$router.go(-1)
        },
        // 保存到草稿箱
        saveBlogToDrafts(status) {
            this.loading = true
            let article = {
                // accountId: this.$store.state.user.id,//作者id
                accountId: 100000, //作者id
                title: this.article.title, //文章标题
                summary: this.article.summary, //文章摘要
                content: this.content, //文章内容
                blogTypeId: this.article.blogTypeId, //文章类型
                status: status, //文章状态
                published: this.article.published, //是否发布
                commentOpen: this.article.commentOpen, //是否开启评论
                appreciation: this.article.appreciation, //是否开启赞赏
                recommend: this.article.recommend, //是否开启推荐
                reprint: this.article.reprint, //是否转载
                reprintUrl: this.article.reprintUrl, //转载地址
                blogImg: this.article.blogImg, //文章封面
                views: this.article.views, //浏览量
                commentNum: this.article.commentNum, //评论数
                likes: this.article.likes, //点赞数
                createTime: this.article.createTime, //创建时间
                lastUpdate: this.article.lastUpdate, //最后更新时间
            }
            this.$post('/article/save', article).then(res => {
                this.loading = false
                this.$message.success('保存成功')
                this.$router.go(-1)
            }).catch(err => {
                this.loading = false
                this.$message.error('保存失败')
            })
        },
        saveBlog(state) {
            this.loading = true
            if (!(this.article.title, this.article.content, this.article.blogTypeId)) {
                this.$message({
                    type: 'error',
                    message: '数据不能为空!'
                });
                return;
            }

            this.article.accountId = 100000

            if (this.article.recommend === true) {
                this.article.recommend = 0
            } else {
                this.article.recommend = 1
            }
            if (this.article.appreciation === true) {
                this.article.appreciation = 0

            } else {
                this.article.appreciation = 1
            }
            if (this.article.commentOpen === true) {
                this.article.commentOpen = 0
            } else {
                this.article.commentOpen = 1
            }
            this.article.createTime = undefined
            this.article.lastUpdate = undefined
            var _this = this;
            _this.loading = true;
            this.$post("/article/write", this.article).then(resp => {
                _this.loading = false;
                this.article.id = resp.data.id;
                _this.$message({
                    type: 'success',
                    message: '发布成功!'
                });
                _this.$router.push({
                    path: '/home'
                });

            }, error => {
                _this.loading = false;
                _this.$message({
                    type: 'error',
                    message: '博客发布失败!'
                });
                _this.$message({
                    type: 'error',
                    message: error.message
                });
            })
        },
        //   获得分类
        getCategories() {
            let _this = this;
            // 发送请求获得分类
            this.$get("/category/list").then(resp => {
                _this.categories = resp.data;
            });
        },
        //   获得标签
        getTagList(e) {
            console.log(e)
        },
        //  从数据库获得标签
        getTags() {
            let _this = this;
            // 发送请求获得标签
            this.$get("/tag/list").then(resp => {
                _this.tags = resp.data;
            });
        },
        getArticle() {
            if (sessionStorage.getItem("articleInfo") != null) {
                let accountInfo = JSON.parse(sessionStorage.getItem("articleInfo"));
                this.$get("/article/get/" + accountInfo.id).then(resp => {
                    this.article = resp.data;
                    this.content = this.article.content;
                    this.article.blogTypeId = this.article.blogType.id;
                    this.article.tagIds = this.article.tags.map(item => item.id);
                });
            }
        }

    },
    mounted() {
        this.getCategories();
        this.getTags();
        this.getArticle();

    }

}
</script>

<style lang="less" scoped>
.sub-btn-div {
    align-items: center;
    margin-top: 15px;
    justify-content: flex-end;
    width: 1030px;
    text-align: right;
}

// 行号样式
:deep(code) {
    &:before {
        content: counter(linenumber);
        color: #999;
        display: block;
        text-align: center;
        counter-increment: linenumber;
    }
}

.post-article {
    width: 1210px;
    text-align: center;
    margin: 0 auto;

    .main {
        /*justify-content: flex-start;*/
        display: flex;
        flex-direction: column;
        padding-left: 5px;
        background-color: #ececec;
        padding-top: 0px;

        #editor {
            margin-top: 20px;
            width: 100%;
            height: 450px;
            text-align: left;
        }
    }

    .header {
        background-color: #ececec;
        margin-top: 10px;
        padding-left: 5px;
        display: flex;
        justify-content: flex-start;
        height: auto;

        .el-tag+.el-tag {
            margin-left: 10px;
        }

        .button-new-tag {
            margin-left: 10px;
            height: 32px;
            line-height: 30px;
            padding-top: 0;
            padding-bottom: 0;
        }

        .input-new-tag {
            width: 90px;
            margin-left: 10px;
            vertical-align: bottom;
        }
    }
}

:deep(.el-form--inline .el-form-item__label)  {
    float: none;
    display: inline-block;
    font-size: 16px;
    font-weight: 700;
}
</style>
