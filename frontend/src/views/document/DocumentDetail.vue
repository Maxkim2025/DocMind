<template>
  <div class="document-detail-container">
    <el-card class="document-detail-card">
      <template #header>
        <div class="card-header">
          <span>文档详情</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>
      <el-descriptions
        :column="1"
        :border="true"
        class="document-detail"
      >
        <el-descriptions-item label="文档标题">
          {{ documentDetail.title }}
        </el-descriptions-item>
        <el-descriptions-item label="文档分类">
          {{ documentDetail.categoryName }}
        </el-descriptions-item>
        <el-descriptions-item label="文档描述">
          {{ documentDetail.description }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ documentDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ documentDetail.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="上传用户">
          {{ documentDetail.username }}
        </el-descriptions-item>
      </el-descriptions>
      <div class="document-content">
        <h3>文档内容</h3>
        <div class="content-placeholder">
          <el-empty description="文档内容预览功能开发中" />
        </div>
      </div>
      <div class="action-buttons">
        <el-button type="primary" @click="handleDownload" :loading="loading">
          <el-icon><Download /></el-icon>
          <span>{{ loading ? '下载中...' : '下载文档' }}</span>
        </el-button>
        <el-button type="success" @click="handleEdit">
          <el-icon><Edit /></el-icon>
          <span>编辑文档</span>
        </el-button>
        <el-button type="danger" @click="handleDelete">
          <el-icon><Delete /></el-icon>
          <span>删除文档</span>
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { Download, Edit, Delete } from '@element-plus/icons-vue'
import { api } from '@/services/api'

const router = useRouter()
const route = useRoute()
const appStore = useAppStore()
const loading = ref(false)

const documentDetail = reactive({
  id: 0,
  title: '技术架构设计文档',
  categoryName: '技术文档',
  description: '系统技术架构设计文档，包含系统架构、模块划分、技术选型等内容。',
  createTime: '2026-02-14 10:00:00',
  updateTime: '2026-02-14 10:00:00',
  username: '管理员'
})

onMounted(() => {
  const id = Number(route.params.id)
  if (id) {
    documentDetail.id = id
  }
})

const handleBack = () => {
  router.push('/document/list')
}

const handleDownload = async () => {
  if (loading.value) return
  
  try {
    loading.value = true
    await api.downloadFile(documentDetail.id)
    appStore.showMessage('下载成功', 'success')
  } catch (error) {
    console.error('下载失败:', error)
    appStore.showMessage('下载失败: ' + (error as Error).message, 'error')
  } finally {
    loading.value = false
  }
}

const handleEdit = () => {
  appStore.showMessage('文档编辑功能开发中', 'info')
}

const handleDelete = () => {
  appStore.showMessage('文档删除功能开发中', 'info')
}
</script>

<style scoped>
.document-detail-container {
  padding: 0;
}

.document-detail-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.document-detail {
  margin: 20px 0;
}

.document-content {
  margin: 20px 0;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.content-placeholder {
  margin-top: 20px;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
}

.action-buttons .el-button {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .action-buttons {
    flex-direction: column;
  }
  
  .action-buttons .el-button {
    margin-right: 0;
    margin-bottom: 10px;
    width: 100%;
  }
}
</style>