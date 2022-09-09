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
            <el-form-item label="文章分类">
                <el-select v-model="article.categoryId" placeholder="选择文章栏目">
                    <el-option v-for="(item, index) in categories" :key="index" :label="item.categoryName" :value="item.id">
                    </el-option>

                </el-select>
            </el-form-item>

            <el-form-item label="文章标题">
                <el-input v-model="article.title" maxlength="120" show-word-limit placeholder="请输入标题..." style="width: 950px;"></el-input>
            </el-form-item>
            <el-form-item label="文章摘要">
                <el-input v-model="article.summary" type="textarea" maxlength="500" :show-word-limit="true" :autosize="{ minRows: 2, maxRows: 5 }" placeholder="请写一下关于文章的摘要，这将让你的博客显示在首页时，帮助你吸引更多的读者" style="margin-top: 10px;width: 950px">
                </el-input>
            </el-form-item>

            <el-form-item label="文章内容" style="max-width: 1000px;">
                <!--mavon-editor  -->
                <div id="editor">
                    <mavon-editor v-model="article.content" style="height:1030px;" :ishljs="true" codeStyle="github-dark" :xssOptions="xssOptions" ref=md @imgAdd="$imgAdd" @change="onChange" @imgDel="$imgDel"></mavon-editor>
                </div>
            </el-form-item>
            <el-form-item label="文章标签" style="max-width: 1000px;">
                <div>
                    <tag-select style="height: 100%;width: 1030px;" :tagList="tags" :selectedTags="artTagList" @getTagList="getTagList">
                    </tag-select>
                </div>
            </el-form-item>
            <el-form-item label="开启功能">
                <div class="inline fields" style="margin-top: 10px;margin-left: 155px;display: none;">
                    <el-checkbox v-model="article.recommend">推荐</el-checkbox>
                    <el-checkbox v-model="article.appreciation">点赞</el-checkbox>
                    <el-checkbox v-model="article.commentOpen">评论</el-checkbox>
                </div>
                <i style="font-size: 12px;color: red;">* 功能权限请等待以后开放，默认全部开启</i>
            </el-form-item>
            <el-form-item label-width="0">
                <div class="sub-btn-div">
                    <el-button @click="cancelEdit">放弃编辑</el-button>
                    <template v-if="!article.id">
                        <el-button @click="saveBlogToDrafts">保存草稿</el-button>
                        <el-button type="primary" @click="saveBlog">发表文章</el-button>
                    </template>
                    <template v-else>
                        <el-button type="primary" @click="updateBlog">保存修改</el-button>
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
import TagSelect from './components/TagSelect.vue'
import 'mavon-editor/dist/css/index.css'
import {
    Message
} from 'element-ui'
export default {
    name: 'WriteArticle',
    components: {
        mavonEditor,
        TagSelect
    },
    data() {
        return {
            accountInfo: {},
            artTagList: [],
            articleTags: '',
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
                categoryId: '', //文章类型
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
            formdata.append('file', $file);
            this.$upload('/article/uploadImg', formdata).then(res => {
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                /**
                 * $vm 指为mavonEditor实例，可以通过如下两种方式获取
                 * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
                 * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
                 */
                this.$refs.md.$img2Url(pos, res.url);
            })
        },
        // 删除图片
        $imgDel(pos, $file) {
            console.log()
            // 获得图片地址
            let url = pos[0];

            // 删除图片
            this.$post('/article/deleteImg', {
                url
            }).then(res => {
                console.log(res.data);
                this.$refs.md.$img2Url(pos, '');
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
        saveBlogToDrafts() {
            this.loading = true
            this.article.accountId = this.accountInfo.id
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
            let article = {
                // accountId: this.$store.state.user.id,//作者id
                accountId: this.accountInfo.id, //作者id
                title: this.article.title, //文章标题
                summary: this.article.summary, //文章摘要
                content: this.content, //文章内容
                categoryId: this.article.categoryId, //文章类型
                status: 2, //文章状态
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
            this.$post('/article/write', article).then(res => {
                this.article.id = res.data.id
                let tagNames = []
                let articleTags = this.articleTags
                articleTags.forEach(articleTag => {
                    tagNames.push(articleTag.tagName);
                })
                this.$post('/articleTag/handle?' + tagNames.map(tagName => 'tagName=' + tagName).join('&'),
                    this.article.id,
                ).then(res => {
                    this.loading = false
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }).catch(err => {
                    this.$message.error('保存失败')
                    this.article.id = ''
                })

            }).catch(err => {
                this.loading = false
                this.$message.error('保存失败')
            })
        },
        saveBlog() {
            this.loading = true
            this.loading = true
            if (!this.article.title || !this.article.content || !this.article.categoryId) {
                this.$message({
                    type: 'error',
                    message: '数据不能为空!'
                });
                this.loading = false
                return;
            }
            if (!this.article.title==='') {
                this.$message({
                    type: 'error',
                    message: '标题不能为空!'
                });
                this.loading = false
                return;
            }
            if (!this.article.summary==='') {
                this.$message({
                    type: 'error',
                    message: '摘要不能为空!'
                });
                this.loading = false
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
            this.$post("/article/write", this.article).then(res => {
                this.article.id = res.data.id
                let tagNames = []
                let articleTags = this.articleTags
                articleTags.forEach(articleTag => {
                    tagNames.push(articleTag.tagName);
                })
                this.$post('/articleTag/handle?' + tagNames.map(tagName => 'tagName=' + tagName).join('&'),
                    this.article.id,
                ).then(res => {
                    this.loading = false
                    this.$message.success('保存成功')
                    this.$router.push('/userInfo')
                }).catch(err => {
                    this.$message.error('保存失败')
                    this.article.id = ''
                    _this.loading = false;
                })

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
        updateBlog() {
            this.loading = true
            if (!this.article.title || !this.article.content || !this.article.categoryId) {
                this.$message({
                    type: 'error',
                    message: '数据不能为空!'
                });
                this.loading = false
                return;
            }
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
            this.$put("/article/update", this.article).then(res => {
                let tagNames = []
                if (this.articleTags) {
                    let articleTags = this.articleTags
                    articleTags.forEach(articleTag => {
                        tagNames.push(articleTag.tagName);
                    })
                }

                this.$post('/articleTag/updateTag?' + tagNames.map(tagName => 'tagName=' + tagName).join('&'),
                    this.article.id,
                ).then(res => {
                    this.loading = false
                    this.$message.success('保存成功')
                    this.$router.push('/userInfo')
                }).catch(err => {
                    this.$message.error('保存失败')
                    this.article.id = '',
                        _this.loading = false;
                })

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
            this.articleTags = e
        },
        //  从数据库获得标签
        getTags() {
            // 发送请求获得标签
            this.$get("/tag/list10").then(resp => {
                this.tags = resp.data;
            });
        },
        getArticle(e) {
            if (e) {
                this.$get("/article/" + e).then(resp => {
                    this.article = resp.data;
                    this.content = this.article.content;
                    this.article.categoryId = this.article.categoryId;

                });
            } else {
                return;
            }
        },
        getTagsByArticleIds(e) {
            if (e) {
                this.$get('/articleTag/getTags/' + e).then(res => {
                    this.artTagList = res.data
                })
            }

        }

    },
    created() {
          this.accountInfo=JSON.parse(sessionStorage.getItem('accountInfo'))
        this.article.id = this.$route.query.id
        this.getCategories();
        this.getTags();
        this.getTagsByArticleIds(this.$route.query.id);

    },
    mounted() {
        if (this.article.id) {
            this.getArticle(this.article.id);
        }

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

:deep(.el-form--inline .el-form-item__label) {
    float: none;
    display: inline-block;
    font-size: 16px;
    font-weight: 700;
}
</style>
