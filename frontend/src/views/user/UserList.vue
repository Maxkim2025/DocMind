<template>
  <div class="user-list-container">
    <el-card class="user-list-card">
      <template #header>
        <div class="card-header">
          <span>用户列表</span>
          <el-button type="primary" @click="handleAddUser">
            <el-icon><Plus /></el-icon>
            <span>添加用户</span>
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <el-form
        :model="searchForm"
        class="search-form"
        label-width="80px"
      >
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="用户名">
              <el-input v-model="searchForm.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="邮箱">
              <el-input v-model="searchForm.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="手机号">
              <el-input v-model="searchForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职位">
              <el-input v-model="searchForm.position" placeholder="请输入职位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="性别">
              <el-select v-model="searchForm.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="18" class="search-buttons">
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-col>
        </el-row>
      </el-form>

      <!-- 用户表格 -->
      <el-table
        v-loading="loading"
        :data="userList"
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="position" label="职位" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="role" label="角色" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleViewUser(scope.row.id)"
            >
              查看
            </el-button>
            <el-button
              type="success"
              size="small"
              @click="handleEditUser(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDeleteUser(scope.row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
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
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { userService } from '@/services/user'
import { useAppStore } from '@/stores/app'
import { Plus } from '@element-plus/icons-vue'
import type { User } from '@/types'

const router = useRouter()
const appStore = useAppStore()
const loading = ref(false)
const userList = ref<User[]>([])

// 搜索表单
const searchForm = reactive({
  username: '',
  email: '',
  phone: '',
  position: '',
  gender: ''
})

// 分页信息
const pagination = reactive({
  pageNum: 1,
  pageSize: 15,
  total: 0
})

// 获取用户列表
const getUserList = async () => {
  loading.value = true
  try {
    const response = await userService.getUserPage(
      pagination.pageNum,
      pagination.pageSize,
      searchForm.username,
      searchForm.email,
      searchForm.phone,
      searchForm.position,
      searchForm.gender
    )
    userList.value = response.records
    pagination.total = response.total
  } catch (error: any) {
    appStore.showMessage(error.message || '获取用户列表失败', 'error')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = () => {
  pagination.pageNum = 1
  getUserList()
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key as keyof typeof searchForm] = ''
  })
  pagination.pageNum = 1
  getUserList()
}

// 处理分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  getUserList()
}

// 处理当前页变化
const handleCurrentChange = (current: number) => {
  pagination.pageNum = current
  getUserList()
}

// 处理添加用户
const handleAddUser = () => {
  router.push('/user/add')
}

// 处理查看用户
const handleViewUser = (id: number) => {
  router.push(`/user/detail/${id}`)
}

// 处理编辑用户
const handleEditUser = (id: number) => {
  router.push(`/user/edit/${id}`)
}

// 处理删除用户
const handleDeleteUser = async (id: number) => {
  try {
    await userService.deleteUser(id)
    appStore.showMessage('删除用户成功', 'success')
    getUserList()
  } catch (error: any) {
    appStore.showMessage(error.message || '删除用户失败', 'error')
  }
}

onMounted(() => {
  getUserList()
})
</script>

<style scoped>
.user-list-container {
  padding: 0;
}

.user-list-card {
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

.search-buttons {
  display: flex;
  align-items: flex-end;
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
  
  .search-buttons {
    margin-top: 10px;
  }
}
</style>