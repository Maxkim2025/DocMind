<template>
  <div class="knowledge-detail-container">
    <el-card class="knowledge-detail-card">
      <template #header>
        <div class="card-header">
          <span>知识库详情</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>
      <el-descriptions
        :column="1"
        :border="true"
        class="knowledge-detail"
      >
        <el-descriptions-item label="知识库名称">
          {{ knowledgeDetail.name }}
        </el-descriptions-item>
        <el-descriptions-item label="知识库描述">
          {{ knowledgeDetail.description }}
        </el-descriptions-item>
        <el-descriptions-item label="文档数量">
          {{ knowledgeDetail.documentCount }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ knowledgeDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ knowledgeDetail.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="创建用户">
          {{ knowledgeDetail.username }}
        </el-descriptions-item>
      </el-descriptions>
      <div class="document-list">
        <h3>包含文档</h3>
        <el-table
          :data="documentList"
          style="width: 100%"
          border
        >
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="title" label="文档标题" />
          <el-table-column prop="createTime" label="添加时间" width="180" />
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                @click="handleViewDocument(scope.row.id)"
              >
                查看
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="action-buttons">
        <el-button type="primary" @click="handleAddDocument">
          <el-icon><Plus /></el-icon>
          <span>添加文档</span>
        </el-button>
        <el-button type="success" @click="handleEdit">
          <el-icon><Edit /></el-icon>
          <span>编辑知识库</span>
        </el-button>
        <el-button type="danger" @click="handleDelete">
          <el-icon><Delete /></el-icon>
          <span>删除知识库</span>
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'

const router = useRouter()
const appStore = useAppStore()

const knowledgeDetail = reactive({
  id: 0,
  name: '技术知识库',
  description: '包含技术相关的文档和资料',
  documentCount: 50,
  createTime: '2026-02-14 10:00:00',
  updateTime: '2026-02-14 10:00:00',
  username: '管理员'
})

const documentList = ref([
  {
    id: 1,
    title: '技术架构设计文档',
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 2,
    title: 'API接口文档',
    createTime: '2026-02-14 10:00:00'
  }
])

const handleBack = () => {
  router.push('/knowledge/list')
}

const handleAddDocument = () => {
  appStore.showMessage('添加文档功能开发中', 'info')
}

const handleEdit = () => {
  appStore.showMessage('编辑知识库功能开发中', 'info')
}

const handleDelete = () => {
  appStore.showMessage('删除知识库功能开发中', 'info')
}

const handleViewDocument = (id: number) => {
  router.push(`/document/detail/${id}`)
}
</script>

<style scoped>
.knowledge-detail-container {
  padding: 0;
}

.knowledge-detail-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.knowledge-detail {
  margin: 20px 0;
}

.document-list {
  margin: 20px 0;
}

.document-list h3 {
  margin-bottom: 10px;
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