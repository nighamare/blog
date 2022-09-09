<template>
<div class="common-table">
    <el-table :data="pageInfo.list"  :loading="true" type="index" :row-class-name="tableRowClassName" @selection-change="handleSelectChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" width="50" label="序号">
        </el-table-column>
        <el-table-column v-for="(column, index) of columns" :prop="column.prop" :label="column.label" :key="index">
            <template #default="scope">
                <slot :name="column.prop" :row="scope.row">
                    {{ scope.row[column.prop] }}
                </slot>
            </template>

        </el-table-column>
        <el-table-column prop="action"  label="操作" fixed="right" width="160">
             <template #default="scope">
                <slot name="action" :row="scope.row">
                    {{ scope.row[action] }}
                </slot>
            </template>
        </el-table-column>

        <template #append>
            <el-pagination style="margin: auto; text-align: center;" :currentPage="pageInfo.pageNum" :page-sizes="[10,20,30,40,100]" :small="false" :disabled="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :page-size="pageInfo.pageSize" :total="pageInfo.total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </template>

    </el-table>

</div>
</template>

<script>
export default {
    name: 'CommonTable',
    props: {
        pageInfoProps: {},
        columnsProps: [],
        multipleSection:[]
    },
    data() {
        return {
            pageInfo: this.pageInfoProps,
            columns: this.columnsProps,
            multipleSelect:this.multipleSection

        }
    },
    emit: ['handleSizeChange', 'handleCurrentChange'],
    methods: {
        handleSizeChange(newValue) {
            this.$emit('handleSizeChange',{
                pageNum: this.pageInfoProps.pageNum,
                pageSize: newValue
            })
        },
        handleCurrentChange(newValue) {
            this.$emit('handleCurrentChange',{
                pageNum: newValue,
                pageSize: this.pageInfoProps.pageSize
            })
        },
        tableRowClassName({row, rowIndex}) {
        if (rowIndex %2== 0) {
          return 'warning-row';
        } else if (rowIndex %2== 1) {
          return 'success-row';
        }
        return '';
      },
      handleSelectChange(sels) {
     this.multipleSelect = sels;
    //  console.log("选中的值",sels.map((item) => item.id));
     this.$emit('sendSelectIds',this.multipleSelect)
   },

    },
    watch: {
        pageInfoProps() {
            this.pageInfo = this.pageInfoProps
        }
    },

}
</script>

<style scoped>
.el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
