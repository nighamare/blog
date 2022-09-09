<template>
<div class="dashboard-editor-container">

    <!-- plan-grop -->
    <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('article')">
                <div class="card-panel-icon-wrapper icon-people">
                    <i class="el-icon-s-check icon-four"></i>
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">
                        待审核文章
                    </div>
                    <span class="card-panel-num">{{count.auditArticle}}</span>

                </div>
            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('friend')">
                <div class="card-panel-icon-wrapper icon-message">
                    <i class="el-icon-connection icon-four"></i>
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">
                        待审核友链
                    </div>
                    <span class="card-panel-num">{{count.auditFriendLink}}</span>
                </div>
            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('message')">
                <div class="card-panel-icon-wrapper icon-money">
                    <i class="el-icon-chat-dot-round icon-four"></i>
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">
                        待查看留言
                    </div>
                    <span class="card-panel-num">{{count.auditMessage}}</span>
                </div>

            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('all')">
                <div class="card-panel-icon-wrapper icon-shopping">
                    <i class="el-icon-pie-chart icon-four"></i>
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">
                        总文章数量
                    </div>
                    <span class="card-panel-num">{{count.allArticle}}</span>
                </div>
            </div>
        </el-col>
    </el-row>
    <!-- end plan-grop -->
    <el-carousel indicator-position="none" arrow="never" :height="bannerHeight + 'px'" class="right-ad1" :interval="3000">
        <el-carousel-item v-for="(item, index) in imgList" :key="index">
            <el-image :src="item.slice(1)" style="width:100%" fit="cover"></el-image>
        </el-carousel-item>
    </el-carousel>

</div>
</template>

<script>
import Navbar from '../layout/components/Navbar.vue'
export default {
    name: 'DashboardAdmin',
    components: {
        Navbar,
    },
    data() {
        return {
            lineChartData: '',
            imgList: [],
            bannerHeight: 420,
            count: {
                auditArticle: 0,
                auditFriendLink: 0,
                auditMessage: 0,
                allArticle: 0,
            }
        }
    },
    methods: {
        // setSize() {
        //     this.bannerHeight = document.body.clientWidth / 4
        // }
        getCount() {
            this.$get('/common/count').then(res => {
                this.count.allArticle = res.data.allArticle
                this.count.auditArticle = res.data.auditArticle;
                this.count.auditFriendLink = res.data.auditFriendLink;
                this.count.auditMessage = res.data.auditMessage;
            })
        },
        handleSetLineChartData(val) {
            if (val === 'article') {
                this.$router.push(
                    '/audit/article'
                )
            } else if (val === 'friend') {
                this.$router.push('/audit/friend')
            } else if (val === 'message') {
                this.$router.push(
                    '/audit/message'
                )
            } else if (val === 'all') {
                this.$router.push(
                    '/article'
                )
            } else {
                Message.error("系统错误！")
            }

        },
    },
    created() {
      let accountInfo=JSON.parse(sessionStorage.getItem("adminInfo"));
      if (!accountInfo) {
            this.$router.push("/login");
      }
    },
    mounted() {
        
        let imgs = [];
        //读取文件的路径 是否遍历文件的子目录 匹配文件正则表达式
        const files = require.context("@/assets/static/images/carousel", false, /\.(png|jpg|gif|jpeg|webp|ico)$/);
        //files(key).default拿到该模块的vue对象
        files.keys().forEach(key => {
            imgs.push(require("@/assets/static/images/carousel" + key.slice(1)))
        });
        this.imgList = imgs

        /*  this.setSize();
         window.addEventListener('resize', () => {
             this.setSize();
         }, false); */
        this.getCount()

    }
}
</script>

<style lang="less" scoped>
.dashboard-editor-container {

    background-color: rgba(240, 242, 245, 0.4);
    position: relative;
    margin: 0;

}

@media (max-width:1024px) {
    .chart-wrapper {
        padding: 8px;
    }
}

.panel-group {

    .card-panel-col {
        margin-bottom: 32px;
    }

    .card-panel {
        height: 108px;
        cursor: pointer;
        font-size: 12px;
        position: relative;
        overflow: hidden;
        color: #666;
        background: #fff;
        box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
        border-color: rgba(0, 0, 0, .05);

        &:hover {
            .card-panel-icon-wrapper {
                color: #fff;
            }

            .icon-people {
                background: #40c9c6;
            }

            .icon-message {
                background: #36a3f7;
            }

            .icon-money {
                background: #f4516c;
            }

            .icon-shopping {
                background: #34bfa3
            }
        }

        .icon-people {
            color: #40c9c6;
        }

        .icon-message {
            color: #36a3f7;
        }

        .icon-money {
            color: #f4516c;
        }

        .icon-shopping {
            color: #34bfa3
        }

        .card-panel-icon-wrapper {
            float: left;
            margin: 14px 0 0 14px;
            padding: 16px;
            transition: all 0.38s ease-out;
            border-radius: 6px;
        }

        .icon-four {
            float: left;
            font-size: 48px;
        }

        .card-panel-description {
            float: right;
            font-weight: bold;
            margin: 26px;
            margin-left: 0px;

            .card-panel-text {
                line-height: 18px;
                color: rgba(0, 0, 0, 0.45);
                font-size: 16px;
                margin-bottom: 12px;
            }

            .card-panel-num {
                font-size: 20px;
            }
        }
    }
}

@media (max-width:550px) {
    .card-panel-description {
        display: none;
    }

    .card-panel-icon-wrapper {
        float: none !important;
        width: 100%;
        height: 100%;
        margin: 0 !important;

        .icon-four {
            display: block;
            margin: 14px auto !important;
            float: none !important;
        }
    }
}
</style>
