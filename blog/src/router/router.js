// 路由模块
import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}



const whiteList = ['login', 'home', 'index', 'tag', 'header', 'footer', 'category', '401', '404'] // 不重定向白名单


// 安装路由插件
Vue.use(VueRouter)
// 创建路由实例对象

// 路由规则
const routes = [
    { path: '/', redirect: '/index' },
    {
        path: '/index',
        alias: '/',
        component: () => import('../views/home/Layout.vue'),
        children: [
            {
                path: '',
                name: 'index',
                meta: {
                    nologin: true,
                    title: '主页',
                },
                component: () => import('../views/home/components/Index.vue')
            },
            {
                path: 'article',
                name: 'article',
                meta: { title: '文章详情' },
                component: () => import('../views/article/ArticleDetails.vue'),
            },
            {
                path: 'write',
                name: 'write',
                meta: { title: '撰写博客' },
                component: () => import('../views/article/WriteArticle.vue'),
            },

            {
                path: 'tag',
                name: 'tag',
                meta: { title: '标签信息' },
                component: () => import('../views/tag/Tag.vue'),
            },
            {
                path: 'category',
                name: 'category',
                meta: { title: '博客分类' },
                component: () => import('../views/category/Category.vue'),
            },
            {
                path: 'message',
                name: 'message',
                meta: { title: '留言信息' },
                component: () => import('../views/message/Message.vue'),
            },
            {
                path: 'friend',
                name: 'friend',
                meta: { title: '友情链接' },
                component: () => import('../views/friend/FriendLink.vue'),
            },
            {
                path: 'about',
                name: 'about',
                meta: { title: '关于' },
                component: () => import('../views/about/About.vue'),
            },
            {
                path: 'search',
                name: 'search',
                meta: { title: '搜索页面' },
                component: () => import('../views/home/components/Search.vue'),
            },
            {
                path: 'user',
                name: 'user',
                meta: { title: '博主页面' },
                component: () => import('../views/user/Blogger.vue'),
            },
            {
                path: 'userInfo',
                name: 'userInfo',
                meta: { title: '博主页面' },
                component: () => import('../views/user/UseInfo.vue'),
            },
            {
                path: 'tagdetail',
                name: 'tagdetail',
                meta: { title: '标签文章' },
                component: () => import('../views/tag/SearchByTag.vue'),
            },

        ],
        meta: {
            nologin: true,
            title: 'Home',
        }
    },
    { path: '/login', name: 'login', component: () => import('../views/login/Login.vue') },
    { path: '/401', name: '401', component: () => import('../components/error-page/401.vue') },
    { path: '/404', name: '404', component: () => import('../components/error-page/404.vue') },

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