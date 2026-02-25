<template>
  <div class="knowledge-list-container">
    <el-card class="knowledge-list-card">
      <template #header>
        <div class="card-header">
          <span>知识库列表</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            <span>创建知识库</span>
          </el-button>
        </div>
      </template>
      <el-form
        :model="searchForm"
        class="search-form"
        label-width="80px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="知识库名称">
              <el-input v-model="searchForm.name" placeholder="请输入知识库名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="resetSearch">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table
        :data="knowledgeList"
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="知识库名称" />
        <el-table-column prop="description" label="知识库描述" :show-overflow-tooltip="true" />
        <el-table-column prop="documentCount" label="文档数量" width="120" />
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
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { Plus } from '@element-plus/icons-vue'

const router = useRouter()
const appStore = useAppStore()
const knowledgeList = ref([
  {
    id: 1,
    name: '技术知识库',
    description: '包含技术相关的文档和资料',
    documentCount: 50,
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 2,
    name: '业务知识库',
    description: '包含业务相关的文档和资料',
    documentCount: 30,
    createTime: '2026-02-14 09:30:00'
  }
])
const searchForm = reactive({
  name: ''
})
const pagination = reactive({
  pageNum: 1,
  pageSize: 15,
  total: 2
})

const handleCreate = () => {
  router.push('/knowledge/create')
}

const handleSearch = () => {
  appStore.showMessage('知识库搜索功能开发中', 'info')
}

const resetSearch = () => {
  searchForm.name = ''
}

const handleSizeChange = (size: number) => {
  pagination.pageSize = size
}

const handleCurrentChange = (current: number) => {
  pagination.pageNum = current
}

const handleView = (id: number) => {
  router.push(`/knowledge/detail/${id}`)
}

const handleDelete = (_id: number) => {
  appStore.showMessage('知识库删除功能开发中', 'info')
}
</script>

<style scoped>
.knowledge-list-container {
  padding: 0;
}

.knowledge-list-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 768px) {
  .search-form {
    padding: 10px;
  }
  
  .el-col {
    width: 100%;
  }
}
</style>