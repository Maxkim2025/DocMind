import { defineStore } from 'pinia'
import { ElMessage, ElMessageBox } from 'element-plus'

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

    // 显示消息通知
    showMessage(content: string, type: 'info' | 'success' | 'warning' | 'error' = 'info') {
      ElMessage({
        message: content,
        type: type,
        duration: 3000
      })
    },

    // 显示错误提示
    showError(error: any) {
      let message = '操作失败'
      let action = ''

      // 网络错误
      if (error.message && (error.message.includes('Network Error') || error.message.includes('网络'))) {
        message = '网络连接失败'
        action = '请检查网络连接后重试'
      }
      // 权限不足
      else if (error.response && error.response.status === 403) {
        message = '权限不足'
        action = '您没有权限执行此操作'
      }
      // 未授权
      else if (error.response && error.response.status === 401) {
        message = '登录已过期'
        action = '请重新登录'
      }
      // 服务器错误
      else if (error.response && error.response.status === 500) {
        message = '服务器错误'
        action = '请稍后再试'
      }
      // 数据验证失败
      else if (error.response && error.response.status === 400) {
        message = '数据验证失败'
        action = error.response.data?.message || '请检查输入数据'
      }
      // 其他错误
      else if (error.message) {
        message = error.message
      }

      // 显示错误提示
      ElMessage({
        message: `${message}${action ? ' - ' + action : ''}`,
        type: 'error',
        duration: 4000
      })
    },

    // 显示重要错误对话框
    showErrorDialog(title: string, message: string, action?: string) {
      ElMessageBox.alert(
        `<div><p>${message}</p>${action ? `<p style="font-size: 12px; color: #606266; margin-top: 8px;">${action}</p>` : ''}</div>`,
        title,
        {
          confirmButtonText: '确定',
          dangerouslyUseHTMLString: true,
          type: 'error'
        }
      )
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