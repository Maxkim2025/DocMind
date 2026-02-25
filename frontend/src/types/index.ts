// 用户类型
export interface User {
  id: number
  username: string
  password: string
  email: string
  phone: string
  position: string
  gender: string
  role: string
  createTime: string
  updateTime: string
}

// 登录请求类型
export interface LoginRequest {
  username: string
  password: string
}

// 登录响应类型
export interface LoginResponse {
  token: string
  role: string
}

// 响应结果类型
export interface Result<T> {
  code: number
  message: string
  data: T
}

// 分页请求类型
export interface PageRequest {
  pageNum: number
  pageSize: number
}

// 分页响应类型
export interface PageResponse<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

// 文档类型
export interface Document {
  id: number
  title: string
  content: string
  categoryId: number
  categoryName: string
  createTime: string
  updateTime: string
  userId: number
  username: string
}

// 文档分类类型
export interface DocumentCategory {
  id: number
  name: string
  parentId: number
  createTime: string
  updateTime: string
}

// 问答类型
export interface QARecord {
  id: number
  question: string
  answer: string
  createTime: string
  userId: number
  username: string
  references: string[]
}

// 知识库类型
export interface KnowledgeBase {
  id: number
  name: string
  description: string
  createTime: string
  updateTime: string
  userId: number
  username: string
  documentCount: number
}