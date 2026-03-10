import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import type { Result } from '@/types'

class ApiService {
  private instance: AxiosInstance

  constructor() {
    this.instance = axios.create({
      baseURL: '/api',
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json'
      }
    })

    // 请求拦截器
    this.instance.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token')
        if (token) {
          config.headers.Authorization = `Bearer ${token}`
        }
        return config
      },
      (error) => {
        return Promise.reject(error)
      }
    )

    // 响应拦截器
    this.instance.interceptors.response.use(
      (response: AxiosResponse<Result<any>>) => {
        if (response.data.code !== 200) {
          // 处理业务错误
          const errorMessage = response.data.message || '未知错误'
          if (errorMessage === '用户未登录' || errorMessage === 'Token无效') {
            // 清除登录状态
            localStorage.removeItem('token')
            localStorage.removeItem('role')
            // 跳转到登录页面
            window.location.href = '/login'
          }
          return Promise.reject(new Error(errorMessage))
        }
        return response
      },
      (error) => {
        // 处理网络错误
        if (error.response) {
          // 服务器返回错误状态码
          if (error.response.status === 401) {
            // 未授权，清除登录状态并跳转到登录页面
            localStorage.removeItem('token')
            localStorage.removeItem('role')
            window.location.href = '/login'
          }
        }
        return Promise.reject(error)
      }
    )
  }

  // 通用请求方法
  async request<T>(config: AxiosRequestConfig): Promise<T> {
    console.log('开始发送API请求：', config)
    try {
      const response = await this.instance.request<Result<T>>(config)
      console.log('API请求成功，响应：', response)
      return response.data.data
    } catch (error) {
      console.log('API请求失败，错误：', error)
      throw error
    }
  }

  // GET请求
  async get<T>(url: string, params?: any): Promise<T> {
    return this.request<T>({
      method: 'GET',
      url,
      params
    })
  }

  // POST请求
  async post<T>(url: string, data?: any): Promise<T> {
    return this.request<T>({
      method: 'POST',
      url,
      data
    })
  }

  // PUT请求
  async put<T>(url: string, data?: any): Promise<T> {
    return this.request<T>({
      method: 'PUT',
      url,
      data
    })
  }

  // DELETE请求
  async delete<T>(url: string, params?: any): Promise<T> {
    return this.request<T>({
      method: 'DELETE',
      url,
      params
    })
  }
  
  // 文件下载
  async downloadFile(id: number): Promise<void> {
    try {
      // 使用axios实例发送请求，确保baseURL和拦截器生效
      const response = await this.instance.request({
        method: 'GET',
        url: `/file/download/${id}`,
        responseType: 'blob' // 重要：设置响应类型为blob
      })
      
      // 获取文件名
      const contentDisposition = response.headers['content-disposition']
      let fileName = 'download.bin'
      if (contentDisposition) {
        const matches = /filename="([^"]+)"/.exec(contentDisposition)
        if (matches && matches[1]) {
          fileName = matches[1]
        }
      }
      
      // 处理文件下载
      const blob = response.data
      const url = window.URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = fileName
      document.body.appendChild(a)
      a.click()
      window.URL.revokeObjectURL(url)
      document.body.removeChild(a)
    } catch (error) {
      console.error('文件下载失败:', error)
      throw error
    }
  }
  
  // 文件上传
  async uploadFile(file: File): Promise<any> {
    try {
      const formData = new FormData()
      formData.append('file', file)
      
      // 使用axios实例发送请求，确保baseURL和拦截器生效
      // 注意：对于FormData，Content-Type会自动设置为multipart/form-data
      const response = await this.instance.request({
        method: 'POST',
        url: '/file/upload',
        data: formData,
        headers: {
          // 不手动设置Content-Type，让axios自动处理
        }
      })
      
      return response.data.data
    } catch (error) {
      console.error('文件上传失败:', error)
      throw error
    }
  }
}

export const api = new ApiService()