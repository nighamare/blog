<template>
<div>
    <div class="comment-title"><i class="iconfont icon-liuyan1" />评论</div>
    <!-- 评论框 -->
    <div class="comment-input-wrapper">
        <div style="display:flex">
            <el-avatar :size="40">
                <!-- 从store中获得个人头像 -->
                <img v-if="this.accountInfo" :src="this.accountInfo.avatar" />
                <img v-else src="https://gravatar.loli.net/avatar/d41d8cd98f00b204e9800998ecf8427e?d=mp&v=1.4.14" />
            </el-avatar>
            <div style="width:100%" class="ml-3">
                <div class="comment-input">
                    <div style="height: 75px;display: flex;">
                        <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 3}" class="comment-textarea" v-model="commentContent" placeholder="留下点什么吧...">

                        </el-input>
                        <el-button type="primary" @click="insertComment" class="upload-btn">
                            发布
                        </el-button>
                    </div>
                    <el-popover placement="bottom" width="370" trigger="click">
                        <!-- 表情框 -->
                        <picker :i18n="I18N" style="width:370px;height: 270px;" :showSearch="true" :showPreview="false" :showCategories="true" @select="addEmoji" />
                        <el-button type="small" class="emoji-expend" slot="reference">
                            <i class="iconfont icon-biaoqing"></i>
                            表情</el-button>
                    </el-popover>

                </div>

            </div>
        </div>
    </div>
    <!-- 评论详情 -->
    <div v-if="count > 0 ">
        <!-- 评论数量 -->
        <div class="count">{{ count }} 评论</div>
        <!-- 评论列表 -->
        <div style="display:flex" class="pt-5" v-for="(item, index) of commentList" :key="item.id">
            <!-- 头像 -->
            <el-avatar :size="40" class="comment-avatar">
                <img :src="item.avatar" />
            </el-avatar>
            <div class="comment-meta">
                <!-- 用户名 -->
                <div class="comment-user">
                    <a @click="toUser(item.accountId)" target="_blank">
                        {{ item.nickname }}
                    </a>
                    <span class="blogger-tag" v-if="item.accountId == upId">博主</span>
                </div>
                <!-- 回复 -->
                <span class="reply-btn" @click="replyComment(index, item)">
                    回复
                </span>
                <!-- 评论内容 -->
                <p v-html="item.content" class="comment-content"></p>
                <!-- 信息 -->
                <div class="comment-info">
                    <!-- 楼层 -->
                    <span style="margin-right:10px">{{ count - index }}楼</span>
                    <!-- 发表时间 -->
                    <span style="margin-right:10px">{{ item.createTime | DateTimeFormat }}</span>
                    <!-- 点赞 -->
                    <span @click="like(item)">
                        <i :class="isLike(item.id)?'iconfont icon-dianzan-pink-copy':'iconfont icon-dianzan'"></i>
                    </span>
                    <span v-show="item.praiseNum > 0"> {{ item.praiseNum }}</span>

                </div>
                <!-- 回复人 -->
                <div style="display:flex" v-for="reply of item.commentReplyList" :key="reply.id">
                    <!-- 头像 -->
                    <el-avatar :size="36" class="comment-avatar">
                        <img :src="reply.avatar" />
                    </el-avatar>
                    <div class="reply-meta">
                        <!-- 用户名 -->
                        <div class="comment-user">
                            <a @click="toUser(reply.accountId)" target="_blank">
                                {{ reply.nickname }}
                            </a>
                            <span class="blogger-tag" v-if="reply.accountId == upId">博主</span>
                        </div>
                        <!-- 信息 -->
                        <div class="comment-info">

                            <!-- 回复 -->
                            <span class="reply-btn" @click="replyComment(index, reply)">
                                回复
                            </span>
                        </div>
                        <!-- 回复内容 -->
                        <p class="comment-content">
                            <!-- 回复用户名 -->
                            <template v-if="reply.commentLevel === 3">
                                <span>回复</span>
                                <a @click="toUser(reply.accountId)" target="_blank" class="comment-nickname">
                                    @{{ reply.replyNickname }}
                                </a>
                                :
                            </template>
                            <span v-html="reply.content"></span>
                        </p>
                        <div class="comment-info">
                            <!-- 发表时间 -->
                            <span style="margin-right:10px">
                                {{ reply.createTime | DateTimeFormat }}
                            </span>
                            <!-- 点赞 -->
                            <span :class="isLike(reply.id)?'iconfont icon-dianzan-pink-copy':'iconfont icon-dianzan'" @click="like(reply)"></span>
                            <span v-show="reply.praiseNum > 0"> {{ reply.praiseNum }}</span>
                        </div>
                    </div>
                </div>
                <!-- 回复数量 -->
                <div class="mb-3" style="font-size:0.75rem;color:#6d757a" v-show="item.replyCount > 3" ref="check">
                    共
                    <b>{{ item.replyCount }}</b>
                    条回复，
                    <span style="color:#00a1d6;cursor:pointer" @click="checkReplies(index, item)">
                        点击查看
                    </span>

                </div>
                <!-- 回复分页 -->
                <div class="mb-3" v-show="item.replyCount > 5 && pageShow">
                    <el-pagination @current-change="(val)=>handleCurrentChange(item,val)" :current-page="replyPageInfo.pageNum" :page-size="replyPageInfo.pageSize" layout="total, prev, pager, next" :total="replyPageInfo.total" />
                </div>
                <!-- 回复框 -->
                <Reply ref="reply" @reloadReply="reloadReply" />
            </div>
        </div>
        <!-- 一级评论分页 -->
        <div style="text-align: center;">
            <el-pagination @size-change="handleRootSizeChange" @current-change="handleRootCurrentChange" :current-page.sync="pageInfoProp.pageNum" :page-sizes="[5, 10, 15, 50]" :page-size="pageInfoProp.pageSize" layout="total, sizes, prev, pager, next" :total="pageInfoProp.total">
            </el-pagination>
        </div>
    </div>
    <!-- 没有评论提示 -->
    <div v-else style="padding:1.25rem;text-align:center">
        <el-empty :image-size="200" description="来发评论吧~"></el-empty>
    </div>
</div>
</template>

<script>
import Reply from "./Reply.vue";

import {
    Picker
} from "emoji-mart-vue";
import {
    Message
} from 'element-ui';
export default {
    components: {
        Reply,
        Picker
    },
    props: [
        'commentList',
        'count',
        'pageInfoProp',
        'upId'
    ],
    data() {
        return {
            praiseIdList: [],
            praiseList: [],
            pageInfo: {
                pageNum: 1,
                pageSize: 5,
                total: 0
            },
            replyPageInfo: {},
            pageShow: false,
            accountInfo: {},
            commentContent: "",
            chooseEmoji: false,
            current: 1,
            articleId: '',
            I18N: {
                search: '搜索',
                notfound: 'No Emoji Found',
                categories: {
                    search: '搜索结果',
                    recent: '经常使用',
                    smileys: '表情与情感',
                    people: '人物与身体',
                    nature: '动物与自然',
                    foods: '食物与饮料',
                    activity: '活动',
                    places: '旅行与地理',
                    objects: '物品',
                    symbols: '符号标志',
                    flags: '旗帜',
                    custom: 'Custom',
                },
            }
        };
    },
    methods: {
        replyComment(index, item) {
            this.$refs.reply.forEach(item => {
                item.$el.style.display = "none";
            });
            //传值给回复框
            this.$refs.reply[index].commentContent = "";
            this.$refs.reply[index].nickname = item.nickname;
            this.$refs.reply[index].replyId = item.accountId;
            this.$refs.reply[index].parentId = this.commentList[index].id;
            this.$refs.reply[index].index = index;
            this.$refs.reply[index].id = item.id;
            this.$refs.reply[index].$el.style.display = "block";
        },
        addEmoji(key) {
            this.commentContent += key.native;
        },
        checkReplies(index, item) {
            this.$get("/comment/replies/" + item.id, {
                pageNum: 1,
                pageSize: 5
            }).then(res => {
                this.pageShow = true;
                this.$refs.check[index].style.display = "none";
                item.commentReplyList = res.data.list;
                this.replyPageInfo = res.data;
            });
        },
        handleCurrentChange(item, val) {
            this.$get("/comment/replies/" + item.id, {
                pageNum: val,
                pageSize: 5
            }).then(res => {
                item.commentReplyList = res.data.list;
                this.replyPageInfo = res.data;
            });
        },
        insertComment() {
            //判断登录
            if (!this.accountInfo) {
                Message.error("请先登录后评论！")
                return false;
            }
            //判空
            if (this.commentContent.trim() === "") {
                Message({
                    type: "error",
                    message: "评论不能为空"
                });
                return false;
            }
            //判断长度
            if (this.commentContent.length > 200) {
                Message({
                    type: "error",
                    message: "评论不能超过200字"
                });
                return false;
            }
            if (!this.articleId) {
                Message({
                    type: "error",
                    message: "请重新尝试！"
                })
            }

            var comment = {
                accountId: this.accountInfo.id,
                articleId: this.articleId,
                content: this.commentContent,
            };

            this.$post("/comment/add", comment).then(() => {
                this.commentContent = "";
                this.$emit("reloadComment");
                Message({
                    type: "success",
                    message: "评论成功"
                });

            });
        },
        like(item) {
            let isliked = this.praiseIdList.indexOf(item.id) === -1

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
                id: item.id,
                accountId: this.accountInfo.id,
                isLiked: isliked,
            }

            if (isliked) {
                this.praiseIdList.push(item.id);
                item.praiseNum++
                this.$post('/comment/likes', params).then(res => {
                })
            } else {
                this.praiseIdList.splice(this.praiseIdList.indexOf(item.id), 1)
                item.praiseNum--
                this.$post('/comment/likes', params).then(res => {
                })
            }

        },

        reloadReply(index) {
            this.$emit("reloadReply", index);
            this.$get("/comment/replies/" + this.commentList[index].id, {
                    pageNum: 1,
                })
                .then(res => {
                    this.commentList[index].replyCount++;
                    this.commentList[index].commentReplyList = res.data.list;
                });
        },

        handleRootSizeChange(val) {
            this.$emit("handleRootSizeChange", val);
        },
        handleRootCurrentChange(val) {
            this.$emit("handleRootCurrentChange", val);
        },
        toUser(e) {
            this.$router.push({
                path: '/user',
                query: {
                    id: e
                }
            })
        },
        getPraiseList() {
            this.$get('/praise/getPraiseList', {
                articleId: this.articleId,
                accountId: this.accountInfo.id
            }).then(res => {
                this.praiseIdList = res.data.list;
                this.praiseList = res.data
            })

        }

    },
    computed: {
        isLike() {
            return function (commentId) {
                if(!this.praiseIdList){
                    this.praiseIdList=[]
                }
                return this.praiseIdList.indexOf(commentId) != -1 ? true : false;
            };
        }
    },
    created() {
        this.$nextTick(() => {
            this.pageInfo = this.pageInfoProp;
        });
    },
    mounted() {
        this.accountInfo = JSON.parse(sessionStorage.getItem("accountInfo"));
        this.articleId = this.$route.query.id
        this.getPraiseList();
    }
};
</script>

<style lang="less" scoped>
p {
    margin-bottom: 1.25rem !important;
}

.blogger-tag {
    background: #ffa51e;
    font-size: 12px;
    display: inline-block;
    border-radius: 2px;
    color: #fff;
    padding: 0 5px;
    margin-left: 6px;
}

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

.comment-input-wrapper {
    border: 1px solid #90939950;
    border-radius: 4px;
    padding: 10px;
    margin: 0 0 10px;
    height: 100%;

    .comment-input {
        border: none;
        outline: none;
        resize: none;
        width: 100%;
        font-size: 14px;
        line-height: 1.5;
        color: #606266;
        margin-left: 10px;

        .comment-textarea {
            position: relative;
            flex: 1;
            font-size: 14px;
            line-height: 1.5;

            &::placeholder {
                color: #909399;
            }
        }

        .upload-btn {
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            flex-basis: 75px;
            margin-left: 10px;
            margin-right: 10px;
            border-radius: 4px;
            height: 75px;
        }

        .emoji-expend {
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            margin-top: 5px;

        }
    }
}

.count {
    padding: 5px;
    line-height: 1.75;
    font-size: 1.25rem;
    font-weight: bold;
}

.comment-meta {
    margin-left: 0.8rem;
    width: 100%;
    border-bottom: 1px dashed #f5f5f5;
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

.comment-nickname {
    text-decoration: none;
    color: #1abc9c !important;
    font-weight: 500;
}

.comment-info {
    line-height: 1.75;
    font-size: 0.75rem;
    color: #b3b3b3;
}

.reply-btn {
    font-size: 12px;
    cursor: pointer;
    float: right;
    color: #ef2f11;
}

.comment-content {
    font-size: 0.875rem;
    line-height: 1.75;
    padding-top: 0.625rem;
    margin-top: 0;
}

.comment-avatar {
    transition: all 0.5s;
}

.comment-avatar:hover {
    transform: rotate(360deg);
}

.like {
    cursor: pointer;
    font-size: 0.875rem;
}

.like:hover {
    color: #eb5055;
}

.like-active {
    cursor: pointer;
    font-size: 0.875rem;
    color: #eb5055;
}
</style>
