<template>
  <!-- 模态对话框错误提示 -->
  <el-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    width="400px"
    :close-on-click-modal="false"
  >
    <div class="error-dialog-content">
      <el-icon :class="`error-icon error-icon-${dialogType}`">
        <component :is="getIconComponent(dialogType)" />
      </el-icon>
      <div class="error-message">
        <p class="error-description">{{ dialogMessage }}</p>
        <p v-if="dialogAction" class="error-action">{{ dialogAction }}</p>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 顶部通知条错误提示 -->
  <div
    v-for="(toast, index) in toasts"
    :key="index"
    :class="`error-toast error-toast-${toast.type}`"
    :style="toastStyle"
  >
    <el-icon :class="`toast-icon toast-icon-${toast.type}`">
      <component :is="getIconComponent(toast.type)" />
    </el-icon>
    <div class="toast-content">
      <p class="toast-message">{{ toast.message }}</p>
      <p v-if="toast.action" class="toast-action">{{ toast.action }}</p>
    </div>
    <el-button
      type="text"
      size="small"
      class="toast-close"
      @click="removeToast(index)"
    >
      <el-icon><Close /></el-icon>
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { 
  Close, 
  Warning, 
  Error, 
  InfoFilled, 
  CheckCircle 
} from '@element-plus/icons-vue'

type MessageType = 'success' | 'warning' | 'error' | 'info'

interface Toast {
  message: string
  type: MessageType
  action?: string
  duration?: number
}

// 对话框属性
const dialogVisible = ref(false)
const dialogTitle = ref('错误提示')
const dialogMessage = ref('')
const dialogType = ref<MessageType>('error')
const dialogAction = ref('')

// 通知条属性
const toasts = ref<Toast[]>([])

// 计算通知条样式
const toastStyle = computed(() => {
  return {
    position: 'fixed',
    top: '20px',
    right: '20px',
    zIndex: '9999'
  }
})

// 根据类型获取图标组件
const getIconComponent = (type: MessageType) => {
  switch (type) {
    case 'success':
      return CheckCircle
    case 'warning':
      return Warning
    case 'error':
      return Error
    case 'info':
      return InfoFilled
    default:
      return InfoFilled
  }
}

// 显示模态对话框错误提示
const showDialog = (message: string, type: MessageType = 'error', action?: string) => {
  dialogVisible.value = true
  dialogMessage.value = message
  dialogType.value = type
  dialogAction.value = action || ''
  
  // 设置对话框标题
  switch (type) {
    case 'success':
      dialogTitle.value = '成功提示'
      break
    case 'warning':
      dialogTitle.value = '警告提示'
      break
    case 'error':
      dialogTitle.value = '错误提示'
      break
    case 'info':
      dialogTitle.value = '信息提示'
      break
  }
}

// 显示顶部通知条错误提示
const showToast = (message: string, type: MessageType = 'info', action?: string, duration: number = 3000) => {
  const toast: Toast = {
    message,
    type,
    action,
    duration
  }
  
  toasts.value.push(toast)
  
  // 自动移除通知条
  if (duration > 0) {
    setTimeout(() => {
      const index = toasts.value.indexOf(toast)
      if (index > -1) {
        toasts.value.splice(index, 1)
      }
    }, duration)
  }
}

// 移除通知条
const removeToast = (index: number) => {
  toasts.value.splice(index, 1)
}

// 导出方法
defineExpose({
  showDialog,
  showToast,
  removeToast
})
</script>

<style scoped>
/* 对话框错误提示样式 */
.error-dialog-content {
  display: flex;
  align-items: flex-start;
  padding: 20px 0;
}

.error-icon {
  font-size: 24px;
  margin-right: 16px;
  flex-shrink: 0;
  margin-top: 2px;
}

.error-icon-success {
  color: #67c23a;
}

.error-icon-warning {
  color: #e6a23c;
}

.error-icon-error {
  color: #f56c6c;
}

.error-icon-info {
  color: #409EFF;
}

.error-message {
  flex: 1;
}

.error-description {
  margin: 0 0 8px 0;
  font-size: 14px;
  line-height: 1.5;
  color: #303133;
}

.error-action {
  margin: 0;
  font-size: 12px;
  line-height: 1.5;
  color: #606266;
}

/* 顶部通知条样式 */
.error-toast {
  margin-bottom: 12px;
  animation: slideInRight 0.3s ease-out;
}

.toast-icon {
  font-size: 16px;
  margin-right: 12px;
  flex-shrink: 0;
}

.toast-icon-success {
  color: #67c23a;
}

.toast-icon-warning {
  color: #e6a23c;
}

.toast-icon-error {
  color: #f56c6c;
}

.toast-icon-info {
  color: #409EFF;
}

.toast-content {
  flex: 1;
}

.toast-message {
  margin: 0 0 4px 0;
  font-size: 14px;
  line-height: 1.4;
  color: #303133;
}

.toast-action {
  margin: 0;
  font-size: 12px;
  line-height: 1.4;
  color: #606266;
}

.toast-close {
  margin-left: 12px;
  padding: 0;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 动画效果 */
@keyframes slideInRight {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .error-dialog-content {
    flex-direction: column;
  }
  
  .error-icon {
    margin-right: 0;
    margin-bottom: 12px;
  }
}
</style>