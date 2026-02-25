import { defineStore } from 'pinia'
import { ElMessage } from 'element-plus'

export const useAppStore = defineStore('app', {
  state: () => ({
    // 全局加载状态
    loading: false,
    // 侧边栏状态
    sidebar: {
      opened: true,
      withoutAnimation: false
    },
    // 主题设置
    theme: {
      color: '#409EFF',
      mode: 'light' as 'light' | 'dark'
    }
  }),

  getters: {
    getLoading: (state) => state.loading,
    getSidebar: (state) => state.sidebar,
    getTheme: (state) => state.theme
  },

  actions: {
    // 设置加载状态
    setLoading(loading: boolean) {
      this.loading = loading
    },

    // 显示消息
    showMessage(content: string, type: 'info' | 'success' | 'warning' | 'error' = 'info') {
      ElMessage({
        message: content,
        type: type,
        duration: 3000
      })
    },

    // 切换侧边栏
    toggleSidebar() {
      this.sidebar.opened = !this.sidebar.opened
      this.sidebar.withoutAnimation = false
    },

    // 关闭侧边栏
    closeSidebar(withoutAnimation: boolean) {
      this.sidebar.opened = false
      this.sidebar.withoutAnimation = withoutAnimation
    },

    // 切换主题
    toggleTheme() {
      this.theme.mode = this.theme.mode === 'light' ? 'dark' : 'light'
    },

    // 设置主题颜色
    setThemeColor(color: string) {
      this.theme.color = color
    }
  }
})