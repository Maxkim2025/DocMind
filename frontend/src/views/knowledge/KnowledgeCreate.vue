<template>
  <div class="knowledge-create-container">
    <el-card class="knowledge-create-card">
      <template #header>
        <div class="card-header">
          <span>创建知识库</span>
          <el-button @click="handleCancel">取消</el-button>
        </div>
      </template>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        class="form"
      >
        <el-form-item label="知识库名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入知识库名称" />
        </el-form-item>
        <el-form-item label="知识库描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入知识库描述"
            :rows="4"
          />
        </el-form-item>
        <el-form-item label="选择文档">
          <el-upload
            class="upload-demo"
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
            :file-list="fileList"
            :limit="10"
            :on-exceed="handleExceed"
          >
            <el-button type="primary">
              <el-icon><Upload /></el-icon>
              <span>选择文档</span>
            </el-button>
            <template #tip>
              <div class="el-upload__tip">
                只能上传 PDF、Word、Excel、PPT 格式的文件，单个文件不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">创建知识库</el-button>
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
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const appStore = useAppStore()
const formRef = ref<FormInstance>()
const fileList = ref([])

const form = reactive({
  name: '',
  description: ''
})

const rules = reactive<FormRules>({
  name: [
    { required: true, message: '请输入知识库名称', trigger: 'blur' },
    { min: 2, max: 50, message: '知识库名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入知识库描述', trigger: 'blur' },
    { min: 5, max: 200, message: '知识库描述长度在 5 到 200 个字符', trigger: 'blur' }
  ]
})

const handleFileChange = (_file: any, _fileList: any[]) => {
  console.log('File changed')
}

const handleExceed = (_files: any[], _fileList: any[]) => {
  appStore.showMessage('文件数量不能超过10个', 'warning')
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid, fields) => {
    if (valid) {
      appStore.showMessage('知识库创建功能开发中', 'info')
    } else {
      console.log('验证失败', fields)
    }
  })
}

const handleCancel = () => {
  router.push('/knowledge/list')
}
</script>

<style scoped>
.knowledge-create-container {
  padding: 0;
}

.knowledge-create-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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