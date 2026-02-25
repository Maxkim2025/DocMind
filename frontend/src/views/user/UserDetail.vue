<template>
  <div class="user-detail-container">
    <el-card class="user-detail-card">
      <template #header>
        <div class="card-header">
          <span>用户详情</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>

      <el-descriptions
        :column="1"
        :border="true"
        class="user-detail"
      >
        <el-descriptions-item label="用户名">
          {{ userDetail.username }}
        </el-descriptions-item>
        <el-descriptions-item label="邮箱">
          {{ userDetail.email }}
        </el-descriptions-item>
        <el-descriptions-item label="手机号">
          {{ userDetail.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="职位">
          {{ userDetail.position }}
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          {{ userDetail.gender }}
        </el-descriptions-item>
        <el-descriptions-item label="角色">
          {{ userDetail.role }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ userDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ userDetail.updateTime }}
        </el-descriptions-item>
      </el-descriptions>

      <div class="action-buttons">
        <el-button
          type="primary"
          @click="handleEdit"
        >
          <el-icon><Edit /></el-icon>
          <span>编辑</span>
        </el-button>
        <el-button
          type="danger"
          @click="handleDelete"
        >
          <el-icon><Delete /></el-icon>
          <span>删除</span>
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { userService } from '@/services/user'
import { useAppStore } from '@/stores/app'
import { Edit, Delete } from '@element-plus/icons-vue'
import type { User } from '@/types'

const router = useRouter()
const route = useRoute()
const appStore = useAppStore()
const loading = ref(false)
const userId = ref(Number(route.params.id))

// 用户详情
const userDetail = reactive<User>({
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

// 获取用户详情
const getUserDetail = async () => {
  loading.value = true
  try {
    const user = await userService.getUserById(userId.value)
    Object.assign(userDetail, user)
  } catch (error: any) {
    appStore.showMessage(error.message || '获取用户详情失败', 'error')
  } finally {
    loading.value = false
  }
}

// 处理返回
const handleBack = () => {
  router.push('/user/list')
}

// 处理编辑
const handleEdit = () => {
  router.push(`/user/edit/${userId.value}`)
}

// 处理删除
const handleDelete = async () => {
  try {
    await userService.deleteUser(userId.value)
    appStore.showMessage('删除用户成功', 'success')
    router.push('/user/list')
  } catch (error: any) {
    appStore.showMessage(error.message || '删除用户失败', 'error')
  }
}

onMounted(() => {
  getUserDetail()
})
</script>

<style scoped>
.user-detail-container {
  padding: 0;
}

.user-detail-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-detail {
  margin: 20px 0;
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