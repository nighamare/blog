<template>
<div>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <p><i class="iconfont icon-jubao"></i>留言说明:</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请先登录后留言~</p>

        </div>
        <div id="editor">
            <mavon-editor :subfield="false" v-model="content" style="height:370px;" :ishljs="true" codeStyle="github-dark" :xssOptions="xssOptions" ref=md @imgAdd="$imgAdd" @change="onChange" @imgDel="$imgDel"></mavon-editor>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <el-button type="primary" @click="submit">提交</el-button>
            <!-- 重置 -->
            <el-button @click="reset">重置</el-button>
        </div>

        <div class="infinite-list-wrapper" style="overflow:auto">
            <el-scrollbar style="height:100%">
                <ul class="list" v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
                    <li v-for="(message,index) in messages" class="list-item" :key="index">
                        <!-- 头像 -->
                        <el-avatar :size="36" class="comment-avatar">
                            <img :src="message.avatar" />
                        </el-avatar>
                        <div class="reply-meta">
                            <!-- 用户名 -->
                            <div class="comment-user">
                                <a @click="toUser(message.accountId)" target="_blank">
                                    {{ message.nickname }}
                                </a>
                            </div>
                            <p class="comment-content">
                                <span v-html="message.content"></span>
                            </p>
                            <div class="comment-info">
                                <!-- 发表时间 -->
                                <span style="margin-right:10px">
                                    {{ message.createTime | DateTimeFormat }}
                                </span>
                            </div>
                        </div>
                    </li>
                </ul>
                <div style="text-align: center;">
                    <p v-if="loading">加载中...</p>
                    <p v-if="noMore">没有更多了</p>
                </div>
            </el-scrollbar>
        </div>
    </el-card>

</div>
</template>

<script>
import {
    Message
} from 'element-ui';
import {
    mavonEditor
} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
    name: 'Message',
    components: {
        mavonEditor
    },
    data() {
        return {
            xssOptions: {
                whiteList: {
                    span: ['style']
                }
            },
            content: '',
            html: '',
            accountInfo: {},
            messages: [],
            messagesList: [],
            loading: false,
            count: 10,
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
        // 提交
        submit() {

            if (this.accountInfo.id) {
                this.$post('/messages/add', {
                    accountId: this.accountInfo.id,
                    content: this.html
                }).then(res => {
                    Message.success('提交成功');
                    this.content = '';
                    this.getMessages();
                }).catch(err => {
                    Message.error('提交失败');
                })
            } else {
                Message.error('请先登录');
            }

        },
        // 获取留言
        getMessages() {
            this.$get('/messages/list').then(res => {
                this.messagesList = res.data;
                this.messages = this.messagesList.slice(0, 10);
            }).catch(err => {
                console.log(err)
            })
        },
        // 重置
        reset() {
            this.content = '';
        },
        load() {
            this.loading = true;
            // 查看20条数据
            this.count += 10;
            this.messages = this.messagesList.slice(0, this.count);
            this.loading = true
            setTimeout(() => {
                this.loading = false
            }, 1000)
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
        this.accountInfo = JSON.parse(sessionStorage.getItem('accountInfo'));
        this.getMessages()
    },
    computed: {
        noMore() {
            return this.messages.length >= this.messagesList.length
        },
        disabled() {
            return this.loading || this.noMore
        }
    }

}
</script>

<style lang="less">
.box-card {
    margin-top: 20px;
    width: 980px;
    margin: auto;

    .icon-jubao {
        color: red;
    }

}

.infinite-list-wrapper {
    width: 940px;
    margin: 10px auto 0;

    ::-webkit-scrollbar {
        width: 0 !important;
    }
}

.list {
    height: 300px;
    padding: 0;
    margin: 0;
    list-style: none;
}

.list-item+.list-item {
    margin-top: 10px;
}

.infinite-list-wrapper .list-item {
    display: flex;
    align-items: left;
    justify-content: left;
    padding: 10px;
    border: 1px solid #eee;

}

.comment-avatar {
    transition: all 0.5s;
}

.reply-meta {
    margin-left: 0.8rem;
    width: 100%;
}

.comment-user {
    font-size: 14px;
    line-height: 1.75;
}

.comment-user a {
    color: #1abc9c !important;
    font-weight: 500;
    transition: 0.3s all;
}

.comment-info {
    line-height: 1.75;
    font-size: 0.75rem;
    color: #b3b3b3;
}

.comment-content {
    font-size: 0.875rem;
    line-height: 1.75;
    padding-top: 0.625rem;
    margin-top: 0;
}
</style>
