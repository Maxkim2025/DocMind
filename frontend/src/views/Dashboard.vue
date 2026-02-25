<template>
  <div class="dashboard-container">
    <el-card class="dashboard-card">
      <template #header>
        <div class="card-header">
          <span>系统概览</span>
        </div>
      </template>
      <div class="dashboard-stats">
        <el-statistic
          v-for="stat in stats"
          :key="stat.id"
          class="dashboard-stat"
          :title="stat.title"
          :value="stat.value"
          :precision="0"
        >
          <template #prefix>
            <el-icon :class="stat.iconClass">{{ stat.icon }}</el-icon>
          </template>
        </el-statistic>
      </div>
    </el-card>

    <el-row :gutter="20" class="dashboard-row">
      <el-col :span="12">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>最近活动</span>
            </div>
          </template>
          <el-timeline>
            <el-timeline-item
              v-for="activity in activities"
              :key="activity.id"
              :timestamp="activity.time"
              :type="activity.type"
              :icon="activity.icon"
            >
              <el-card>
                <h4>{{ activity.title }}</h4>
                <p>{{ activity.description }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>系统状态</span>
            </div>
          </template>
          <div class="system-status">
            <el-progress
              v-for="item in statusItems"
              :key="item.id"
              :percentage="item.percentage"
              :status="item.status"
              :format="item.format"
              :color="item.color"
              :stroke-width="15"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { User, Document, ChatLineSquare, CollectionTag, Check, InfoFilled, Warning, Close } from '@element-plus/icons-vue'

// 统计数据
const stats = ref([
  { id: 1, title: '用户总数', value: 100, icon: User, iconClass: 'stat-icon user-icon' },
  { id: 2, title: '文档总数', value: 500, icon: Document, iconClass: 'stat-icon document-icon' },
  { id: 3, title: '问答次数', value: 200, icon: ChatLineSquare, iconClass: 'stat-icon qa-icon' },
  { id: 4, title: '知识库数', value: 10, icon: CollectionTag, iconClass: 'stat-icon knowledge-icon' }
])

// 最近活动
const activities = ref([
  { id: 1, title: '用户登录', description: '管理员登录系统', time: '2026-02-14 10:00', type: 'success', icon: Check },
  { id: 2, title: '文档上传', description: '上传了新文档《系统架构设计》', time: '2026-02-14 09:30', type: 'info', icon: InfoFilled },
  { id: 3, title: '智能问答', description: '用户询问了关于API使用的问题', time: '2026-02-14 09:00', type: 'warning', icon: Warning },
  { id: 4, title: '系统更新', description: '系统完成了定期更新', time: '2026-02-14 08:00', type: 'error', icon: Close }
])

// 系统状态
const statusItems = ref([
  { id: 1, percentage: 85, status: 'success', format: () => 'CPU使用率 85%', color: '#67C23A' },
  { id: 2, percentage: 60, status: 'warning', format: () => '内存使用率 60%', color: '#E6A23C' },
  { id: 3, percentage: 45, status: 'info', format: () => '磁盘使用率 45%', color: '#409EFF' },
  { id: 4, percentage: 90, status: 'success', format: () => '系统健康度 90%', color: '#67C23A' }
])

onMounted(() => {
  // 这里可以从后端获取真实数据
  console.log('Dashboard mounted')
})
</script>

<style scoped>
.dashboard-container {
  padding: 0;
}

.dashboard-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dashboard-stats {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.dashboard-stat {
  flex: 1;
  min-width: 150px;
  margin: 10px;
  text-align: center;
}

.stat-icon {
  font-size: 24px;
  margin-right: 10px;
}

.user-icon {
  color: #409EFF;
}

.document-icon {
  color: #67C23A;
}

.qa-icon {
  color: #E6A23C;
}

.knowledge-icon {
  color: #F56C6C;
}

.dashboard-row {
  margin-bottom: 20px;
}

.system-status {
  padding: 20px 0;
}

.system-status .el-progress {
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .el-col {
    width: 100%;
  }
  
  .dashboard-stat {
    min-width: 100%;
  }
}
</style>