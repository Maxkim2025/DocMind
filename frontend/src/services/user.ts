import { api } from './api'
import type { User, LoginRequest, PageResponse, LoginResponse } from '@/types'

export const userService = {
  // 登录
  async login(data: LoginRequest): Promise<LoginResponse> {
    return api.post<LoginResponse>('/user/login', data)
  },

  // 获取用户列表
  async getUserList(): Promise<User[]> {
    return api.get<User[]>('/user')
  },

  // 分页获取用户列表
  async getUserPage(
    pageNum: number,
    pageSize: number,
    username?: string,
    email?: string,
    phone?: string,
    position?: string,
    gender?: string
  ): Promise<PageResponse<User>> {
    return api.get<PageResponse<User>>('/user/page', {
      pageNum,
      pageSize,
      username,
      email,
      phone,
      position,
      gender
    })
  },

  // 搜索用户
  async queryUser(
    username?: string,
    email?: string,
    phone?: string,
    position?: string,
    gender?: string
  ): Promise<User[]> {
    return api.get<User[]>('/user/query', {
      username,
      email,
      phone,
      position,
      gender
    })
  },

  // 获取用户详情
  async getUserById(id: number): Promise<User> {
    return api.get<User>(`/user/${id}`)
  },

  // 添加用户
  async addUser(data: User): Promise<User> {
    return api.post<User>('/user', data)
  },

  // 更新用户
  async updateUser(data: User): Promise<User> {
    return api.put<User>('/user', data)
  },

  // 删除用户
  async deleteUser(id: number): Promise<void> {
    return api.delete<void>(`/user/${id}`)
  }
}