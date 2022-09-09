<template>
<div>
    <!-- 标签列表 -->
    <div class="tag-container">
        <div class="tag-cloud">
            <el-link :underline="false" :style="{'font-size': Math.floor(Math.random() * 10) + 14 + 'px','color': '#' + Math.floor(Math.random() * 0xffffff).toString(16)+' !important'}" v-for="item of tagList" :key="item.id" @click="toTag(item)">
                {{ item.tagName }}
            </el-link>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: 'Tag',
    created() {
        // this.listTags();
    },
    data: function () {
        return {
            tagList: [{
                    id: 1,
                    tagName: "Java",
                },
                {
                    id: 2,
                    tagName: "Spring",
                },
                {
                    id: 3,
                    tagName: "SpringBoot",
                },
                {
                    id: 4,
                    tagName: "Vue",
                },
                {
                    id: 5,
                    tagName: "JavaScript",
                },
                {
                    id: 6,
                    tagName: "MySQL",
                },
                {
                    id: 7,
                    tagName: "Linux",
                },
                {
                    id: 8,
                    tagName: "Git",
                },
                {
                    id: 9,
                    tagName: "Docker",
                },
                {
                    id: 10,
                    tagName: "Redis",
                },
                {
                    id: 11,
                    tagName: "Elasticsearch",
                },
                {
                    id: 12,
                    tagName: "Kafka",
                },
                {
                    id: 13,
                    tagName: "Nginx",
                },
                {
                    id: 14,
                    tagName: "RabbitMQ",
                },
                {
                    id: 15,
                    tagName: "Zookeeper",
                },
                {
                    id: 16,
                    tagName: "SpringCloud",
                },
                {
                    id: 17,
                    tagName: "SpringCloudAlibaba",
                },
                {
                    id: 18,
                    tagName: "SpringSecurity",
                },
                {
                    id: 19,
                    tagName: "SpringDataJPA",
                },
                {
                    id: 20,
                    tagName: "SpringMVC",
                },
                {
                    id: 21,
                    tagName: "SpringBatch",
                },
                {
                    id: 22,
                    tagName: "SpringIntegration",
                },
                {
                    id: 23,
                    tagName: "SpringSession",
                },
                {
                    id: 24,
                    tagName: "SpringWebSocket",
                },
                {
                    id: 25,
                    tagName: "SpringBootAdmin",
                },
                {
                    id: 26,
                    tagName: "SpringBootActuator",
                },
                {
                    id: 27,
                    tagName: "SpringBootDevTools",
                },
                {
                    id: 28,
                    tagName: "SpringBootStarter",
                },
                {
                    id: 29,
                    tagName: "SpringBootStarterTest",
                },
                {
                    id: 30,
                    tagName: ""
                }

            ],
            count: 0
        };
    },
    methods: {
        listTags() {
            this.$get("/tag/list").then(res => {
                this.tagList = res.data;
            });
        },
        toTag(e) {
            this.$router.push({
                path: '/tagdetail',
                query: {
                    id: e.id,
                    tagName: e.tagName
                }
            })
        }

    },
    created() {
        this.listTags();
    }
};
</script>

<style scoped>
.tag-container {
    width: 50%;
    margin: 100px auto 200px;
    background-color: rgba(255, 255, 255, 0.5);
    min-height: 300px;

}

.tag-cloud {
    text-align: center;
    padding: 10px;
    min-height: 300px;
}

.tag-cloud a {
    color: #616161;
    display: inline-block;
    text-decoration: none;
    padding: 0 8px;
    line-height: 2;
    transition: all 0.3s;

}

.tag-cloud a:hover {
    color: #03a9f4 !important;
    transform: scale(1.1);
}
</style>
