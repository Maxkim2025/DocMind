<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <el-aside
      :width="sidebar.opened ? '200px' : '64px'"
      class="layout-sidebar"
      :class="{ 'sidebar-closed': !sidebar.opened }"
    >
      <div class="sidebar-logo">
        <h1 v-if="sidebar.opened" class="logo-text">DocMind</h1>
        <el-icon v-else class="logo-icon"><DataAnalysis /></el-icon>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        :collapse="!sidebar.opened"
        router
        :collapse-transition="!sidebar.withoutAnimation"
      >
        <el-menu-item index="/">
          <template #icon>
            <el-icon><DataBoard /></el-icon>
          </template>
          <span>仪表盘</span>
        </el-menu-item>
        <el-sub-menu index="/user">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/list">用户列表</el-menu-item>
          <el-menu-item index="/user/add">添加用户</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/document">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>文档管理</span>
          </template>
          <el-menu-item index="/document/upload">上传文档</el-menu-item>
          <el-menu-item index="/document/list">文档列表</el-menu-item>
          <el-menu-item index="/document/category">文档分类</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/qa">
          <template #title>
            <el-icon><ChatLineSquare /></el-icon>
            <span>智能问答</span>
          </template>
          <el-menu-item index="/qa/ask">问答界面</el-menu-item>
          <el-menu-item index="/qa/history">历史记录</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/knowledge">
          <template #title>
            <el-icon><CollectionTag /></el-icon>
            <span>知识库管理</span>
          </template>
          <el-menu-item index="/knowledge/list">知识库列表</el-menu-item>
          <el-menu-item index="/knowledge/create">创建知识库</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container class="layout-main">
      <!-- 头部导航 -->
      <el-header class="layout-header">
        <el-button
          type="text"
          class="header-toggle"
          @click="toggleSidebar"
        >
          <el-icon><Fold /></el-icon>
        </el-button>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32">
                <template #default>
                  <el-icon><User /></el-icon>
                </template>
              </el-avatar>
              <span v-if="userStore.getIsLoggedIn">管理员</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区域 -->
      <el-main class="layout-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'
import { DataAnalysis, DataBoard, User, Document, ChatLineSquare, CollectionTag, Fold } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const appStore = useAppStore()
const userStore = useUserStore()

const sidebar = computed(() => appStore.getSidebar)

const activeMenu = computed(() => {
  const path = route.path
  return path
})

const toggleSidebar = () => {
  appStore.toggleSidebar()
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}

onMounted(() => {
  // 检查登录状态
  if (!userStore.getIsLoggedIn) {
    router.push('/login')
  }
})
</script>

<style scoped>
.layout-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.layout-sidebar {
  background-color: #fff;
  border-right: 1px solid #eaeaea;
  transition: width 0.3s ease;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
}

.sidebar-logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #eaeaea;
  padding: 0 20px;
}

.logo-text {
  font-size: 18px;
  font-weight: 600;
  color: #409EFF;
  margin: 0;
}

.logo-icon {
  font-size: 24px;
  color: #409EFF;
}

.sidebar-menu {
  flex: 1;
  margin-top: 20px;
}

.layout-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.layout-header {
  height: 60px;
  background-color: #fff;
  border-bottom: 1px solid #eaeaea;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.header-toggle {
  margin-right: 20px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-info span {
  margin-left: 10px;
}

.layout-content {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f7fa;
}

.sidebar-closed .sidebar-logo {
  padding: 0;
}

/* 过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

@media (max-width: 768px) {
  .layout-sidebar {
    position: fixed;
    left: 0;
    top: 0;
    height: 100vh;
    z-index: 1000;
  }
}
</style>