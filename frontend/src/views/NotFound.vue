<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <el-icon class="not-found-icon"><Warning /></el-icon>
      <h1 class="not-found-title">404</h1>
      <p class="not-found-description">页面不存在</p>
      <el-button type="primary" @click="handleBack">
        <el-icon><ArrowLeft /></el-icon>
        <span>返回首页</span>
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { Warning, ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()

const handleBack = () => {
  router.push('/')
}
</script>

<style scoped>
.not-found-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.not-found-content {
  text-align: center;
  padding: 40px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.not-found-icon {
  font-size: 80px;
  color: #E6A23C;
  margin-bottom: 20px;
}

.not-found-title {
  font-size: 48px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.not-found-description {
  font-size: 18px;
  color: #666;
  margin-bottom: 30px;
}

@media (max-width: 768px) {
  .not-found-content {
    width: 90%;
    padding: 30px;
  }
  
  .not-found-icon {
    font-size: 60px;
  }
  
  .not-found-title {
    font-size: 36px;
  }
  
  .not-found-description {
    font-size: 16px;
  }
}
</style>