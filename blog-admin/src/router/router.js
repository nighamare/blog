// 路由模块
import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}



const whiteList = ['login', '401', '404'] // 不重定向白名单


// 安装路由插件
Vue.use(VueRouter)
// 创建路由实例对象

// 路由规则
const routes = [
    { path: '/', redirect: '/home' },
    { path: '/login', name: 'login', component: () => import('../views/login/Login.vue') },
    { path: '/401', name: '401', component: () => import('../components/error-page/401.vue') },
    { path: '/404', name: '404', component: () => import('../components/error-page/404.vue') },
    {
        path: '/home',
        alias: '/',
        component: () => import('../views/layout/Layout.vue'),
        children: [
            {
                path: '',
                name: 'index',
                meta: {
                    title: '首页',
                    roles: ['admin']
                },
                component: () => import('../views/home/Index.vue')
            },
            {
                path: '/account',
                name: 'account',
                meta: {
                    title: '用户管理  /  账户列表',
                    roles: ['admin']
                },
                component: () => import('../views/user/Account.vue')
            },
            {
                path: '/accountInfo',
                name: 'accountInfo',
                meta: {
                    title: '用户管理  / 账户详情',
                    roles: ['admin']
                },
                component: () => import('../views/user/AccountInfo.vue')
            },
            {
                path: 'article',
                name: 'article',
                meta: {
                    title: '文章管理 / 文章列表',
                    roles: ['admin']
                },
                component: () => import('../views/article/Article.vue')
            },
            {
                path: '/edit',
                name: 'edit',
                meta: {
                    title: '文章管理 / 编辑文章',
                    roles: ['admin']
                },
                component: () => import('../views/article/WriteArticle.vue')
            },
            {
                path: '/arttag',
                name: 'arttag',
                meta: {
                    title: '文章管理 / 文章标签',
                    roles: ['admin']
                },
                component: () => import('../views/article/ArticleTag.vue')
            },
            {
                path: '/catg',
                name: 'catg',
                meta: {
                    title: '分类管理',
                    roles: ['admin']
                },
                component: () => import('../views/category/Category.vue')
            },
            {
                path: '/comment',
                name: 'comment',
                meta: {
                    title: '评论列表',
                    roles: ['admin']
                },
                component: () => import('../views/comment/Comment.vue')
            },
            {
                path: '/tag',
                name: 'tag',
                meta: {
                    title: '标签列表',
                    roles: ['admin']
                },
                component: () => import('../views/tag/Tag.vue')
            },
            {
                path: '/message',
                name: 'message',
                meta: {
                    title: '留言列表',
                    roles: ['admin']
                },
                component: () => import('../views/message/Message.vue')
            },
            {
                path: '/friend',
                name: 'friend',
                meta: {
                    title: '友链列表',
                    roles: ['admin']
                },
                component: () => import('../views/friend/FriendshipChain.vue')
            },
            {
                path: '/praise',
                name: 'praise',
                meta: {
                    title: '点赞列表',
                    roles: ['admin']
                },
                component: () => import('../views/praise/Praise.vue')
            },
            {
                path: '/top',
                name: 'top',
                meta: {
                    title: '置顶列表',
                    roles: ['admin']
                },
                component: () => import('../views/top/Top.vue')
            },
            {
                path: '/audit/article',
                name: 'audit/article',
                meta: {
                    title: '审核管理 / 审核文章',
                    roles: ['admin']
                },
                component: () => import('../views/audit/article/Article.vue')
            },
            {
                path: '/audit/friend',
                name: 'audit/friend',
                meta: {
                    title: '审核管理 / 审核友链',
                    roles: ['admin']
                },
                component: () => import('../views/audit/friend/FriendshipChain.vue')
            },


        ]
    }
]

const router = new VueRouter({
    routes
})
// 向外共享路由实例对象
export default router


router.beforeEach((to, from, next) => {
    if (whiteList.includes(to.name) || to.meta.nologin) {
        next()
    } else if (!sessionStorage.getItem('token')) {
        // next({ name: 'login' })
        next()
    } else {
        next()
    }
})