<template>
  <div class="document-upload-container">
    <el-card class="document-upload-card">
      <template #header>
        <div class="card-header">
          <span>上传文档</span>
        </div>
      </template>
      <el-upload
        class="upload-demo"
        drag
        action="#"
        :auto-upload="false"
        :on-change="handleFileChange"
        :file-list="fileList"
        :limit="5"
        :on-exceed="handleExceed"
      >
        <el-icon class="el-icon--upload"><Upload /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            只能上传 PDF、Word、Excel、PPT 格式的文件，单个文件不超过 10MB
          </div>
        </template>
      </el-upload>
      <el-form
        :model="form"
        class="form"
        label-width="80px"
      >
        <el-form-item label="文档标题">
          <el-input v-model="form.title" placeholder="请输入文档标题" />
        </el-form-item>
        <el-form-item label="文档分类">
          <el-select v-model="form.categoryId" placeholder="请选择文档分类">
            <el-option label="技术文档" value="1" />
            <el-option label="业务文档" value="2" />
            <el-option label="其他文档" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="文档描述">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入文档描述"
            :rows="4"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleUpload" :loading="loading">
            {{ loading ? '上传中...' : '上传文档' }}
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { Upload } from '@element-plus/icons-vue'
import { api } from '@/services/api'

const router = useRouter()
const appStore = useAppStore()
const fileList = ref([])
const loading = ref(false)
const selectedFile = ref<File | null>(null)

const form = reactive({
  title: '',
  categoryId: '',
  description: ''
})

const handleFileChange = (file: any, _fileList: any[]) => {
  selectedFile.value = file.raw
}

const handleExceed = (_files: any[], _fileList: any[]) => {
  appStore.showMessage('文件数量不能超过5个', 'warning')
}

const handleUpload = async () => {
  if (loading.value) return
  
  if (!selectedFile.value) {
    appStore.showMessage('请选择要上传的文件', 'warning')
    return
  }
  
  try {
    loading.value = true
    await api.uploadFile(selectedFile.value)
    appStore.showMessage('上传成功', 'success')
    router.push('/document/list')
  } catch (error) {
    console.error('上传失败:', error)
    appStore.showMessage('上传失败: ' + (error as Error).message, 'error')
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  router.push('/document/list')
}
</script>

<style scoped>
.document-upload-container {
  padding: 0;
}

.document-upload-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.upload-demo {
  margin-bottom: 20px;
}

.form {
  max-width: 600px;
}

@media (max-width: 768px) {
  .form {
    max-width: 100%;
  }
}
</style>