<template>
  <div class="user-edit-container">
    <el-card class="user-edit-card">
      <template #header>
        <div class="card-header">
          <span>编辑用户</span>
          <el-button @click="handleCancel">取消</el-button>
        </div>
      </template>

      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userRules"
        label-width="100px"
        class="user-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="userForm.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="userForm.position" placeholder="请输入职位" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="userForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" placeholder="请选择角色">
            <el-option label="普通用户" value="USER" />
            <el-option label="管理员" value="ADMIN" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            class="submit-button"
            :loading="loading"
            @click="handleSubmit"
          >
            提交
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { userService } from '@/services/user'
import { useAppStore } from '@/stores/app'
import type { FormInstance, FormRules } from 'element-plus'
import type { User } from '@/types'

const router = useRouter()
const route = useRoute()
const appStore = useAppStore()
const userFormRef = ref<FormInstance>()
const loading = ref(false)
const userId = ref(Number(route.params.id))

// 用户表单
const userForm = reactive<User>({
  id: 0,
  username: '',
  password: '',
  email: '',
  phone: '',
  position: '',
  gender: '',
  role: '',
  createTime: '',
  updateTime: ''
})

// 表单验证规则
const userRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少 6 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  position: [
    { required: true, message: '请输入职位', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ]
})

// 获取用户详情
const getUserDetail = async () => {
  loading.value = true
  try {
    const user = await userService.getUserById(userId.value)
    Object.assign(userForm, user)
  } catch (error: any) {
    appStore.showMessage(error.message || '获取用户详情失败', 'error')
  } finally {
    loading.value = false
  }
}

// 处理提交
const handleSubmit = async () => {
  if (!userFormRef.value) return
  
  await userFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      try {
        await userService.updateUser(userForm)
        appStore.showMessage('更新用户成功', 'success')
        router.push('/user/list')
      } catch (error: any) {
        appStore.showMessage(error.message || '更新用户失败', 'error')
      } finally {
        loading.value = false
      }
    } else {
      console.log('验证失败', fields)
    }
  })
}

// 处理取消
const handleCancel = () => {
  router.push('/user/list')
}

onMounted(() => {
  getUserDetail()
})
</script>

<style scoped>
.user-edit-container {
  padding: 0;
}

.user-edit-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-form {
  max-width: 600px;
}

.submit-button {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .user-form {
    max-width: 100%;
  }
  
  .el-form-item {
    margin-bottom: 15px;
  }
}
</style>