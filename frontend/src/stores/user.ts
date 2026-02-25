import { defineStore } from 'pinia'
import { userService } from '@/services/user'
import type { User, LoginRequest, LoginResponse } from '@/types'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null as User | null,
    token: localStorage.getItem('token') || '',
    role: localStorage.getItem('role') || '',
    isLoggedIn: !!localStorage.getItem('token'),
    loading: false
  }),

  getters: {
    getUserInfo: (state) => state.userInfo,
    getToken: (state) => state.token,
    getRole: (state) => state.role,
    getIsLoggedIn: (state) => state.isLoggedIn
  },

  actions: {
    // 登录
    async login(data: LoginRequest) {
      this.loading = true
      try {
        const response = await userService.login(data)
        this.token = response.token
        this.role = response.role
        this.isLoggedIn = true
        localStorage.setItem('token', response.token)
        localStorage.setItem('role', response.role)
        return response
      } catch (error) {
        throw error
      } finally {
        this.loading = false
      }
    },

    // 登出
    logout() {
      this.userInfo = null
      this.token = ''
      this.role = ''
      this.isLoggedIn = false
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    },

    // 获取用户信息
    async getUserInfo() {
      // 这里可以根据实际情况从后端获取用户信息
      // 暂时返回null
      return this.userInfo
    },

    // 重置状态
    resetState() {
      this.userInfo = null
      this.token = ''
      this.role = ''
      this.isLoggedIn = false
    }
  }
})