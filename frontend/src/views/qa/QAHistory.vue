<template>
  <div class="qa-history-container">
    <el-card class="qa-history-card">
      <template #header>
        <div class="card-header">
          <span>问答历史</span>
          <el-button type="primary" @click="handleClearAll">
            <el-icon><Delete /></el-icon>
            <span>清空历史</span>
          </el-button>
        </div>
      </template>
      <el-table
        :data="historyList"
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="question" label="问题" />
        <el-table-column prop="answer" label="回答" :show-overflow-tooltip="true" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleView(scope.row.id)"
            >
              查看
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
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 15, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useAppStore } from '@/stores/app'
import { Delete } from '@element-plus/icons-vue'

const appStore = useAppStore()
const historyList = ref([
  {
    id: 1,
    question: '什么是DocMind系统？',
    answer: 'DocMind是一个企业级智能知识库管理与问答系统，支持文档上传、智能解析、向量存储和RAG问答功能。',
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 2,
    question: '如何上传文档？',
    answer: '您可以通过文档管理模块的上传功能，选择本地文件进行上传。系统支持PDF、Word、Excel、PPT等格式。',
    createTime: '2026-02-14 09:30:00'
  }
])
const pagination = reactive({
  pageNum: 1,
  pageSize: 15,
  total: 2
})

const handleClearAll = () => {
  appStore.showMessage('清空历史功能开发中', 'info')
}

const handleView = (_id: number) => {
  appStore.showMessage('查看历史详情功能开发中', 'info')
}

const handleDelete = (_id: number) => {
  appStore.showMessage('删除历史记录功能开发中', 'info')
}

const handleSizeChange = (size: number) => {
  pagination.pageSize = size
}

const handleCurrentChange = (current: number) => {
  pagination.pageNum = current
}
</script>

<style scoped>
.qa-history-container {
  padding: 0;
}

.qa-history-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>