import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/views/Layout.vue'),
    meta: { title: '主页' },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '仪表盘' }
      },
      {
        path: 'user',
        name: 'User',
        meta: { title: '用户管理' },
        children: [
          {
            path: 'list',
            name: 'UserList',
            component: () => import('@/views/user/UserList.vue'),
            meta: { title: '用户列表' }
          },
          {
            path: 'add',
            name: 'UserAdd',
            component: () => import('@/views/user/UserAdd.vue'),
            meta: { title: '添加用户' }
          },
          {
            path: 'edit/:id',
            name: 'UserEdit',
            component: () => import('@/views/user/UserEdit.vue'),
            meta: { title: '编辑用户' }
          },
          {
            path: 'detail/:id',
            name: 'UserDetail',
            component: () => import('@/views/user/UserDetail.vue'),
            meta: { title: '用户详情' }
          }
        ]
      },
      {
        path: 'document',
        name: 'Document',
        meta: { title: '文档管理' },
        children: [
          {
            path: 'upload',
            name: 'DocumentUpload',
            component: () => import('@/views/document/DocumentUpload.vue'),
            meta: { title: '上传文档' }
          },
          {
            path: 'list',
            name: 'DocumentList',
            component: () => import('@/views/document/DocumentList.vue'),
            meta: { title: '文档列表' }
          },
          {
            path: 'detail/:id',
            name: 'DocumentDetail',
            component: () => import('@/views/document/DocumentDetail.vue'),
            meta: { title: '文档详情' }
          },
          {
            path: 'category',
            name: 'DocumentCategory',
            component: () => import('@/views/document/DocumentCategory.vue'),
            meta: { title: '文档分类' }
          }
        ]
      },
      {
        path: 'qa',
        name: 'QA',
        meta: { title: '智能问答' },
        children: [
          {
            path: 'ask',
            name: 'QAAsk',
            component: () => import('@/views/qa/QAAsk.vue'),
            meta: { title: '问答界面' }
          },
          {
            path: 'history',
            name: 'QAHistory',
            component: () => import('@/views/qa/QAHistory.vue'),
            meta: { title: '历史记录' }
          }
        ]
      },
      {
        path: 'knowledge',
        name: 'Knowledge',
        meta: { title: '知识库管理' },
        children: [
          {
            path: 'list',
            name: 'KnowledgeList',
            component: () => import('@/views/knowledge/KnowledgeList.vue'),
            meta: { title: '知识库列表' }
          },
          {
            path: 'create',
            name: 'KnowledgeCreate',
            component: () => import('@/views/knowledge/KnowledgeCreate.vue'),
            meta: { title: '创建知识库' }
          },
          {
            path: 'detail/:id',
            name: 'KnowledgeDetail',
            component: () => import('@/views/knowledge/KnowledgeDetail.vue'),
            meta: { title: '知识库详情' }
          }
        ]
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue'),
    meta: { title: '404' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

import { useUserStore } from '@/stores/user'

router.beforeEach((to, _from, next) => {
  document.title = `${to.meta.title || 'DocMind'} - 智能知识库管理系统`
  
  // 检查登录状态
  const userStore = useUserStore()
  const isLoggedIn = userStore.getIsLoggedIn
  
  // 登录页面不需要检查
  if (to.path === '/login') {
    next()
    return
  }
  
  // 其他页面需要检查登录状态
  if (!isLoggedIn) {
    // 未登录，跳转到登录页面
    next('/login')
  } else {
    // 已登录，继续访问
    next()
  }
})

export default router