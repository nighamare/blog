<template>
<div>
    <el-card class="box-card">
        <div class="avatar" style="text-align: left;margin-top: 5px;" v-if="userInfo">
            <div style="text-align: center;" class="avatar-div">
                <div style="margin: auto;height: 100px; width: 100px; line-height: 100px; cursor: pointer;" @mouseover="viewAvatarHover" @mouseleave="hideAvatarHover" @click="changeAvatar">

                    <el-avatar :size="100" :src="userInfo.avatar" style="cursor: pointer;"></el-avatar>
                    <div class="avatar-hover" ref="avatatHoverRef">
                        <img src="@/assets/static/images/pic.png" alt="">
                    </div>
                </div>
                <!-- 头像传 -->
                <avatar-cropper :dialogVisible.sync="dialogVisible" @uploadAvatarDialog="uploadAvatarDialog"></avatar-cropper>
            </div>
            <!-- 个人信息 -->
            <el-dialog title="修改资料" :visible.sync="dialogFormVisible">
                <el-descriptions size="medium" :column="1" border>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            昵称
                        </template>
                        <el-input v-model="accountInfo1.nickname "></el-input>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                        </template>
                        <el-input v-model="accountInfo1.username" disabled></el-input>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            性别
                        </template>
                        <el-select v-model="accountInfo1.sex" placeholder="请选择">
                            <el-option label="男" :value="1"></el-option>
                            <el-option label="女" :value="0"></el-option>
                            <el-option label="保密" :value="2"></el-option>
                        </el-select>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            电话
                        </template>
                        <el-input v-model="accountInfo1.phoneNum"></el-input>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            邮箱
                        </template>
                        <el-input v-model="accountInfo1.email"></el-input>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            地址
                        </template>
                        <el-input v-model="accountInfo1.address"></el-input>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            个人简介
                        </template>
                        <el-input type="textarea" v-model="accountInfo1.personalSignature "></el-input>
                    </el-descriptions-item>
                </el-descriptions>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submitUpdateUserInfo">确 定</el-button>
                </div>
            </el-dialog>
            <!-- end -->
            <div class="info">
                <el-descriptions class="margin-top" :column="2" border>

                    <template slot="extra">
                        <el-button type="text" size="small" style="font-size: 16px;" @click="viewUpdateInfo">修改个人信息</el-button>
                    </template>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                        </template>
                        <span v-html="userInfo.username"></span>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            注册时间
                        </template>
                        <span style="font-size: 12px;">{{ userInfo.createTime | DateTimeFormat }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            上次修改时间
                        </template>
                        <span style="font-size: 12px;">{{ userInfo.lastUpdate | DateTimeFormat }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            昵称
                        </template>
                        <span v-html="userInfo.nickname"></span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            性别
                        </template>
                        <el-tag size="small" v-if="userInfo.sex === 0">女</el-tag>
                        <el-tag size="small" v-else-if="userInfo.sex === 1">男</el-tag>
                        <el-tag size="small" v-else>未知</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            电话
                        </template>
                        {{ userInfo.phoneNum }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            邮箱
                        </template>
                        {{ userInfo.email }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            地址
                        </template>
                        {{ userInfo.address }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            个人简介
                        </template>
                        {{ userInfo.personalSignature }}
                    </el-descriptions-item>
                </el-descriptions>

            </div>
            <el-divider content-position="right" style="height: 2px;">博主文章</el-divider>
            <div v-if="articles.length > 0">
                <el-card class="box-card" v-for="(article, index) in articles" :key="index" style="background: rgb(255, 255, 255,0.4);margin-top: 10px;">
                    <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">
                        <h4 class="article-title" :title="article.title" v-html="article.title">
                        </h4>

                    </el-link>

                    <p style="height:70px;overflow: hidden;">
                        <el-link @click="toArticle(article.id)" :underline="false" style="width: 100%;display: block;">
                            <span v-html="article.summary"></span>
                        </el-link>
                    </p>
                    <div class="bar">
                        <i class="el-icon-time"></i><span>发表于 &nbsp;</span>
                        <span>{{ article.createTime | DateTimeFormat }}&nbsp;&nbsp;</span>
                        <span style="font-size: 12px;">修改于 {{ article.lastUpdate | DateTimeFormat }}</span>
                        <i class="el-icon-s-operation"></i><span>分类：</span>
                        <span><a @click="toCategory(article.categoryId)">{{ article.categoryName }}</a></span> |
                        <i class="el-icon-view"></i><span>阅读量：</span><span>{{ article.views }}</span>
                        |
                        <i class="el-icon-chat-dot-round"></i><span>评论数：</span><span>{{ article.commentNum }}</span>

                    </div>
                    <el-tag size="small" style="margin-right: 5px;" type="success" v-for="(item, index) in article.tags" :key="index">
                        <el-link @click="toTag(item.id)">
                            {{ item.tagName }}
                        </el-link>

                    </el-tag>

                </el-card>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.pageNum" :page-sizes="[20, 40, 60, 100]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
                </el-pagination>
            </div>
            <div v-else>
                <el-empty :image-size="200"></el-empty>
            </div>
        </div>
        <div v-else>
            <el-empty :image-size="200"></el-empty>
        </div>
    </el-card>

</div>
</template>

<script>
import {Message} from 'element-ui';
import avatarCropper from '../user/components/AvatarCorpper.vue'

export default {
    name: 'Blogger',
    components: {
        avatarCropper,
    },
    data() {
        return {
            img: '',
            accountId: 0,
            userInfo: {},
            accountInfo: {},
            accountInfo1: {
                id: 0,
                nickname: '',
                username: '',
                sex: 0,
                phoneNum: '',
                email: '',
                address: '',
                personalSignature: ''
            },
            articles: [],
            pageInfo: {},
            dialogVisible: false,
            dialogFormVisible: false

        }
    },
    methods: {
        getUserInformations() {
            if (!this.accountInfo.id) return;
            this.$get('/account/findOne', {
                id: this.accountInfo.id
            }).then(res => {
                this.userInfo = res.data;
            });
        },
        getArticles() {
            if (!this.accountInfo.id) return;
            this.$get('/article/user/' + this.accountInfo.id, {
                pageNum: this.pageInfo.pageNum ? this.pageInfo.pageNum : 1,
                pageSize: this.pageInfo.pageSize ? this.pageInfo.pageSize : 20
            }).then(res => {
                this.articles = res.data.list;
                this.pageInfo = res.data;
            }).catch(err => {
                this.articles = [];
            });
        },
        handleSizeChange(val) {
            this.pageInfo.pageSize = val;
            this.getArticles()
        },
        handleCurrentChange(val) {
            this.pageInfo.pageNum = val;
            this.getArticles()

        },
        toArticle(e) {
            this.$router.push({
                path: '/article',
                query: {
                    id: e
                }
            })
        },
        viewAvatarHover() {
            this.$refs.avatatHoverRef.style.display = 'block';
        },
        hideAvatarHover() {
            this.$refs.avatatHoverRef.style.display = 'none';
        },
        changeAvatar() {
            this.dialogVisible = true;
        },
        uploadAvatarDialog(data) {

            let str = this.base64toFile(data, 'file')
            if (!this.accountInfo.id) {
                this.$router.push({
                    path: '/login',
                })
                Message({
                    message: '请重新登录！',
                    type: 'warning'
                })
                return;
            }
            let formData = new FormData();
            formData.append('file', str)
            this.$upload('/account/updateAvatar/' + this.accountInfo.id,
                formData
            ).then(res => {
                this.$message.success('上传成功！')
                this.getUserInformations();
                this.$store.commit('login', this.userInfo)
            })
            this.dialogVisible = false
        },
        //data:base64图片格式字符串
        //filename：文件名称
        base64toFile(data, fileName) {
            const dataArr = data.split(",");
            const byteString = atob(dataArr[1]);
            const options = {
                type: "image/jpeg",
                endings: "native"
            };
            const u8Arr = new Uint8Array(byteString.length);
            for (let i = 0; i < byteString.length; i++) {
                u8Arr[i] = byteString.charCodeAt(i);
            }
            return new File([u8Arr], fileName + ".jpg", options); //返回文件流
        },
        viewUpdateInfo() {

            this.accountInfo1.id = this.userInfo.id
            this.accountInfo1.nickname = this.userInfo.nickname
            this.accountInfo1.username = this.userInfo.username
            this.accountInfo1.sex = this.userInfo.sex
            this.accountInfo1.phoneNum = this.userInfo.phoneNum
            this.accountInfo1.email = this.userInfo.email
            this.accountInfo1.address = this.userInfo.address
            this.accountInfo1.personalSignature = this.userInfo.personalSignature
            this.dialogFormVisible = true
        },
        submitUpdateUserInfo() {
            if (!this.accountInfo1.id) {
                this.dialogFormVisible = false
                Message({
                    message: '请重新登陆！',
                    type: 'warning'
                })
                return;
            } else {

                this.$put('/account/update', this.accountInfo1).then(res => {
                    
                    Message({
                        message: '保存成功！',
                        type: 'success'
                    })
                    this.getUserInformations();
                    this.$store.commit('login', this.userInfo)
                    this.dialogFormVisible = false
                })

            }

        }

    },
    created() {
        this.accountInfo = JSON.parse(sessionStorage.getItem("accountInfo"))
        this.accountId = this.accountInfo.id

    },
    mounted() {
        this.getUserInformations();
        this.getArticles();
    }

}
</script>

<style>
.el-descriptions__header {
    margin: 20px 0 0 0;
    font-size: 16px;
}

.el-descriptions__header span {
    font-size: 14px;
}

.avatar-hover {
    display: none;
    position: absolute;
    left: 710px;
    top: 106px;
    width: 100px;
    height: 100px;
    background: rgba(0, 0, 0, 0.5);
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-radius: 50%;
}

.avatar-hover img {
    width: 32px;
    height: 32px;
    margin-top: 34px;
}

.box-card {
    width: 1000px;
    margin: 20px auto;
    background: rgba(255, 255, 255, 0.5);

}

.bar {
    width: 100%;
    font-size: 12px;
    color: #999;
    padding: 0;
    margin-top: 10px;
}
</style>
