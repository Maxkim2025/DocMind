<template>
  <div class="document-category-container">
    <el-card class="document-category-card">
      <template #header>
        <div class="card-header">
          <span>文档分类</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>添加分类</span>
          </el-button>
        </div>
      </template>
      <el-table
        :data="categoryList"
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="parentName" label="父分类" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(scope.row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAppStore } from '@/stores/app'
import { Plus } from '@element-plus/icons-vue'

const appStore = useAppStore()
const categoryList = ref([
  {
    id: 1,
    name: '技术文档',
    parentName: '无',
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 2,
    name: '业务文档',
    parentName: '无',
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 3,
    name: '其他文档',
    parentName: '无',
    createTime: '2026-02-14 10:00:00'
  }
])

const handleAdd = () => {
  appStore.showMessage('分类添加功能开发中', 'info')
}

const handleEdit = (_id: number) => {
  appStore.showMessage('分类编辑功能开发中', 'info')
}

const handleDelete = (_id: number) => {
  appStore.showMessage('分类删除功能开发中', 'info')
}
</script>

<style scoped>
.document-category-container {
  padding: 0;
}

.document-category-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>