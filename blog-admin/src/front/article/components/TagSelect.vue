<template>
    <div>
        <div class="tranfer">
            <!-- 已选标签 -->
            <div class="selected-tag">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>已选标签</span>
                        <el-button @click="deleteAllSelectedTag" class="text-all" style="float: right; padding: 3px 0"
                            type="text">删除全部</el-button>
                    </div>
                    <el-tag v-for="(tag, index) in conditions" closable @close="handleClose(tag)" type="success"
                        :key="index">{{ tag }}</el-tag>
                    <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput"
                        size="small" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm">
                    </el-input>
                    <el-button v-else class="button-new-tag" type="primary" size="small" @click="showInput">自定义标签
                    </el-button>
                </el-card>
            </div>
            <div style="text-align: center;height: 40px;"><i class="iconfont icon-link1"
                    style="font-size: 55px;color: blueviolet;"></i></div>
            <!-- 待选标签 -->
            <div class="select-tag">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>热门标签</span>
                        <el-button @click="addAllSelectedTag" class="text-all" style="float: right; padding: 3px 0;"
                            type="text">添加全部</el-button>
                    </div>
                    <el-tag style="cursor: pointer;" v-for="(tag, index) in categories" type="success" :key="index"
                        @click="selectTag(tag)">{{ tag }}</el-tag>

                </el-card>
            </div>

        </div>
    </div>
</template>

<script>
import { Message } from 'element-ui'
export default {
    name: 'TagSelect',
    props: ['tagList'],
    $emit: ['getTagList'],
    data() {
        return {
            categories: this.tagList,
            conditions: [],
            inputVisible: false,
            inputValue: ''
        }
    },
    methods: {
        // 处理标签点击事件，未选中则选中，已选中则取消选中
        selectTag(item) {
            // 最多只能有10个标签
            if (this.conditions.length <= 10) {
                if (this.conditions.indexOf(item) == -1) {
                    this.conditions.push(item)
                    // 移除已选标签
                    this.categories.splice(this.categories.indexOf(item), 1)
                } else {
                    this.conditions.splice(this.conditions.indexOf(item), 1)
                }
            } else {
                Message({
                    message: '最多只能有10个标签',
                    type: 'warning'
                });
            }

        },
        handleClose(tag) {
            this.conditions.splice(this.conditions.indexOf(tag), 1);
            // 添加到待选标签
            this.categories.push(tag)
        },
        showInput() {
            // 最多只能有10个标签
            if (this.conditions.length <= 10) {
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            } else {
                Message({
                    message: '最多只能有10个标签',
                    type: 'warning'
                });
            }
        },

        handleInputConfirm() {
            let inputValue = this.inputValue;
            // 判断是否存在
            if (this.conditions.indexOf(inputValue) == -1) {
                if (inputValue) {
                    this.conditions.push(inputValue);
                    if (this.categories.indexOf(inputValue) != -1) {
                        // 删除待选标签
                        this.categories.splice(this.categories.indexOf(inputValue), 1)
                    }
                }
                this.inputVisible = false;
                this.inputValue = '';
            } else {
                Message({
                    message: '标签已存在',
                    type: 'warning'
                });
            }

        },
        // 删除全部已选标签
        deleteAllSelectedTag() {
            this.conditions.forEach(item => {
                this.categories.push(item)
            })
            this.conditions = []
        },
        // 添加全部待选标签
        addAllSelectedTag() {
            // 最多只能有10个标签
            if (this.conditions.length === 0) {
                this.categories.forEach(item => {
                    // 未选中则选中
                    if (this.conditions.indexOf(item) == -1) {
                        this.conditions.push(item)
                    }
                })
                this.categories = []
            } else {
                Message({
                    message: '最多只能有10个标签',
                    type: 'warning'
                });
            }
        }
    },
    watch: {
        conditions: {
            handler() {
                this.$emit('getTagList', this.conditions)
            },
            deep: true
        }
    }

}
</script>

<style lang="less" scoped>
.box-card {
    width: 100%;
    box-shadow: none;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    text-align: center;

}

.selected-tag {
    width: 100%;
    border-radius: 4px;


    .selected-tag-item {
        display: inline-block;
        margin-right: 10px;
        margin-bottom: 10px;

        .el-tag {
            cursor: pointer;
        }
    }
}

.select-tag {
    width: 100%;
    border-radius: 4px;


    .selected-tag-item {
        display: inline-block;
        margin-right: 10px;
        margin-bottom: 10px;

        .el-tag {
            cursor: pointer;
        }
    }

}

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

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.clearfix {
    padding: 0;
    line-height: 40px;
    text-align: left;

}

:deep(.el-card__header)  {
    height: 40px;
    line-height: 40px;
    background: #f5f7fa;
    margin: 0;
    padding: 0 0 0 15px;
    border-bottom: 1px solid #ebeef5;
    box-sizing: border-box;
    color: #000;

    .text-all {
        color: #409EFF;
        margin: 12px 10px 12px 0;
    }

}

:deep(.el-card__body)  {
    padding: 20px !important;
    height: 52px;
}
</style>
