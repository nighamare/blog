<template>
<div class="reply-input-wrapper" style="display: none" ref="reply">
    <div style="display: flex;">
        <el-avatar :size="40">
            <!-- 从store中获得个人头像 -->
            <img v-if="this.accountInfo.avatar" :src="this.accountInfo.avatar" />
            <img v-else src="https://gravatar.loli.net/avatar/d41d8cd98f00b204e9800998ecf8427e?d=mp&v=1.4.14" />
        </el-avatar>
        <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 3}" class="comment-textarea" :placeholder="'回复 @' + nickname + '：'" auto-grow dense v-model="commentContent">

        </el-input>
    </div>
    <div class="emoji-container">
        <el-popover placement="bottom" width="370" trigger="click">
            <!-- 表情框 -->
            <picker :i18n="I18N" style="width:370px;height: 270px;" :showSearch="true" :showPreview="false" :showCategories="true" @select="addEmoji" />
            <el-button type="small" class="emoji-expend" slot="reference">
                <i class="iconfont icon-biaoqing"></i>表情</el-button>
        </el-popover>
        <div style="position: relative;float: right;margin-top: 5px;">
            <el-button @click="cancleReply" class="cancle-btn">
                取消
            </el-button>
            <el-button @click="insertReply" type="primary" class="upload-btn">
                提交
            </el-button>
        </div>
    </div>
    <!-- 表情框 -->
</div>
</template>

<script>
import {
    Picker
} from "emoji-mart-vue";
import {
    Message
} from 'element-ui';

export default {
    components: {
        Picker
    },
    data() {
        return {
            accountInfo: {},
            index: 0,
            chooseEmoji: false,
            nickname: "",
            replyId: null,
            parentId: null,
            commentContent: "",
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
        cancleReply() {
            this.$refs.reply.style.display = "none";
        },
        insertReply() {
            //判断登录
            if (!sessionStorage.getItem("token")) {
                return false;
            }
            if (this.commentContent.trim() == "") {
                Message({
                    type: "error",
                    message: "回复不能为空"
                });
                return false;
            }
            //解析表情
            var comment = {
                accountId: this.accountInfo.id,
                articleId: this.$route.query.id,
                replyCommentAccountId: this.replyId,
                rootCommentId: this.parentId,
                content: this.commentContent,
                replyCommentId: this.id,
            };
            this.$post("/comment/add", comment).then(res => {
                    this.commentContent = "";
                this.$emit("reloadReply", this.index);
                Message({
                    type: "success",
                    message: "回复成功"
                });
            });
        },
        addEmoji(e) {
            this.commentContent += e.native;
        },

    },
    created() {
        this.accountInfo = JSON.parse(sessionStorage.getItem("accountInfo"));
    }
};
</script>

<style lang="less" scoped>
.reply-input-wrapper {
    border: 1px solid #90939950;
    border-radius: 4px;
    padding: 10px;
    margin: 0 0 10px;

    .comment-textarea {
        margin-left: 10px;
    }

    .emoji-container {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .emoji-expend {
            margin-left: 50px;
        }

        .cancle-btn {
            margin-right: 10px;
        }
    }
}
</style>
