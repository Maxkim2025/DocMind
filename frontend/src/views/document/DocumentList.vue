<template>
  <div class="document-list-container">
    <el-card class="document-list-card">
      <template #header>
        <div class="card-header">
          <span>文档列表</span>
          <el-button type="primary" @click="handleUpload">
            <el-icon><Upload /></el-icon>
            <span>上传文档</span>
          </el-button>
        </div>
      </template>
      <el-form
        :model="searchForm"
        class="search-form"
        label-width="80px"
      >
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="文档标题">
              <el-input v-model="searchForm.title" placeholder="请输入文档标题" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="文档分类">
              <el-select v-model="searchForm.categoryId" placeholder="请选择文档分类">
                <el-option label="技术文档" value="1" />
                <el-option label="业务文档" value="2" />
                <el-option label="其他文档" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="resetSearch">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table
        :data="documentList"
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="文档标题" />
        <el-table-column prop="categoryName" label="文档分类" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleViewDocument(scope.row.id)"
            >
              查看
            </el-button>
            <el-button
              type="success"
              size="small"
              @click="handleEditDocument(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDeleteDocument(scope.row.id)"
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
import { Upload } from '@element-plus/icons-vue'

const router = useRouter()
const appStore = useAppStore()
const documentList = ref([
  {
    id: 1,
    title: '技术架构设计文档',
    categoryName: '技术文档',
    createTime: '2026-02-14 10:00:00'
  },
  {
    id: 2,
    title: '业务需求分析',
    categoryName: '业务文档',
    createTime: '2026-02-14 09:30:00'
  }
])
const searchForm = reactive({
  title: '',
  categoryId: ''
})
const pagination = reactive({
  pageNum: 1,
  pageSize: 15,
  total: 2
})

const handleUpload = () => {
  router.push('/document/upload')
}

const handleSearch = () => {
  appStore.showMessage('文档搜索功能开发中', 'info')
}

const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key as keyof typeof searchForm] = ''
  })
}

const handleSizeChange = (size: number) => {
  pagination.pageSize = size
}

const handleCurrentChange = (current: number) => {
  pagination.pageNum = current
}

const handleViewDocument = (id: number) => {
  router.push(`/document/detail/${id}`)
}

const handleEditDocument = (_id: number) => {
  appStore.showMessage('文档编辑功能开发中', 'info')
}

const handleDeleteDocument = (_id: number) => {
  appStore.showMessage('文档删除功能开发中', 'info')
}
</script>

<style scoped>
.document-list-container {
  padding: 0;
}

.document-list-card {
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