<template>
  <!-- 全屏加载状态 -->
  <div v-if="fullScreen" class="loading-overlay">
    <div class="loading-content">
      <div class="loading-spinner" :class="sizeClass"></div>
      <p v-if="message" class="loading-message">{{ message }}</p>
    </div>
  </div>

  <!-- 内联加载状态 -->
  <div v-else class="loading-inline" :class="sizeClass">
    <div class="loading-spinner" :class="sizeClass"></div>
    <span v-if="message" class="loading-message-inline">{{ message }}</span>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  // 是否全屏显示
  fullScreen?: boolean
  // 加载消息
  message?: string
  // 加载图标大小
  size?: 'small' | 'medium' | 'large'
}

const props = withDefaults(defineProps<Props>(), {
  fullScreen: false,
  message: '',
  size: 'medium'
})

// 根据大小计算类名
const sizeClass = computed(() => {
  return `loading-${props.size}`
})
</script>

<style scoped>
/* 全屏加载状态 */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  backdrop-filter: blur(2px);
}

.loading-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

/* 内联加载状态 */
.loading-inline {
  display: flex;
  align-items: center;
  gap: 8px;
}

/* 加载动画 */
.loading-spinner {
  border: 3px solid #f3f3f3;
  border-top: 3px solid #409EFF;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* 不同尺寸 */
.loading-small {
  width: 20px;
  height: 20px;
  border-width: 2px;
}

.loading-medium {
  width: 40px;
  height: 40px;
  border-width: 3px;
}

.loading-large {
  width: 60px;
  height: 60px;
  border-width: 4px;
}

/* 加载消息 */
.loading-message {
  font-size: 16px;
  color: #606266;
  margin: 0;
}

.loading-message-inline {
  font-size: 14px;
  color: #606266;
}

/* 动画定义 */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .loading-large {
    width: 40px;
    height: 40px;
  }
  
  .loading-message {
    font-size: 14px;
  }
}
</style>