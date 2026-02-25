<template>
  <div class="qa-ask-container">
    <el-card class="qa-ask-card">
      <template #header>
        <div class="card-header">
          <span>智能问答</span>
        </div>
      </template>
      <div class="chat-container">
        <div class="chat-messages" ref="chatMessagesRef">
          <div
            v-for="(message, index) in messages"
            :key="index"
            class="message"
            :class="message.type"
          >
            <div class="message-avatar">
              <el-avatar v-if="message.type === 'user'">
                <el-icon><User /></el-icon>
              </el-avatar>
              <el-avatar v-else>
                <el-icon><ChatDotRound /></el-icon>
              </el-avatar>
            </div>
            <div class="message-content">
              <div class="message-text">{{ message.content }}</div>
              <div v-if="message.references && message.references.length > 0" class="message-references">
                <h4>参考文档：</h4>
                <ul>
                  <li v-for="(ref, refIndex) in message.references" :key="refIndex">
                    {{ ref }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div v-if="loading" class="loading-message">
            <el-icon class="is-loading"><Loading /></el-icon>
            <span>AI正在思考中...</span>
          </div>
        </div>
        <div class="chat-input">
          <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="3"
            placeholder="请输入您的问题..."
            @keyup.enter.exact="handleSend"
          />
          <div class="input-actions">
            <el-button @click="handleClear">清空对话</el-button>
            <el-button type="primary" @click="handleSend" :disabled="!inputMessage.trim() || loading">
              <el-icon><ArrowRight /></el-icon>
              <span>发送</span>
            </el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick } from 'vue'
import { User, ChatDotRound, Loading, ArrowRight } from '@element-plus/icons-vue'

const loading = ref(false)
const inputMessage = ref('')
const chatMessagesRef = ref<HTMLElement>()
const messages = ref<Array<{
  type: string
  content: string
  references: string[]
}>>([
  {
    type: 'ai',
    content: '你好！我是DocMind智能助手，有什么可以帮您的吗？',
    references: []
  }
])

const handleSend = () => {
  if (!inputMessage.value.trim() || loading.value) return
  
  const userMessage = inputMessage.value.trim()
  messages.value.push({
    type: 'user',
    content: userMessage,
    references: []
  })
  inputMessage.value = ''
  
  // 滚动到底部
  nextTick(() => {
    if (chatMessagesRef.value) {
      chatMessagesRef.value.scrollTop = chatMessagesRef.value.scrollHeight
    }
  })
  
  // 模拟AI回复
  loading.value = true
  setTimeout(() => {
    messages.value.push({
      type: 'ai',
      content: '这是一个智能回答示例，实际功能开发中。',
      references: ['技术架构设计文档', '业务需求分析']
    })
    loading.value = false
    // 滚动到底部
    nextTick(() => {
      if (chatMessagesRef.value) {
        chatMessagesRef.value.scrollTop = chatMessagesRef.value.scrollHeight
      }
    })
  }, 1000)
}

const handleClear = () => {
  messages.value = [
    {
      type: 'ai',
      content: '你好！我是DocMind智能助手，有什么可以帮您的吗？',
      references: []
    }
  ]
}
</script>

<style scoped>
.qa-ask-container {
  padding: 0;
}

.qa-ask-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chat-container {
  height: 600px;
  display: flex;
  flex-direction: column;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  margin-bottom: 20px;
}

.message {
  display: flex;
  margin-bottom: 20px;
  align-items: flex-start;
}

.message.user {
  flex-direction: row-reverse;
}

.message-avatar {
  margin: 0 10px;
}

.message-content {
  flex: 1;
  max-width: 80%;
}

.message.user .message-content {
  text-align: right;
}

.message-text {
  padding: 12px 16px;
  border-radius: 18px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.message.user .message-text {
  background-color: #409EFF;
  color: #fff;
}

.message-references {
  margin-top: 10px;
  padding: 10px;
  background-color: #f0f0f0;
  border-radius: 8px;
  font-size: 12px;
}

.message-references h4 {
  margin: 0 0 5px 0;
  font-size: 12px;
  font-weight: 600;
}

.message-references ul {
  margin: 0;
  padding-left: 20px;
}

.loading-message {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  color: #666;
}

.chat-input {
  display: flex;
  flex-direction: column;
}

.input-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.input-actions .el-button {
  margin-left: 10px;
}

@media (max-width: 768px) {
  .chat-container {
    height: 500px;
  }
  
  .message-content {
    max-width: 90%;
  }
}
</style>