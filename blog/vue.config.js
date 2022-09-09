const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    port: 80,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',// 将 '/api' 代理为 'http://localhost:12306/api'
        changeOrigin: true,
        ws: true,
        // rewrite:(path)=>path.replace(/^\/api/,'')
        pathRewrite: { "^/api": "" }, // 'http://localhost:12306/api' 重写为 'http://localhost:12306/'

      },
      'file': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: true,

      }
    }
  },
  transpileDependencies: true
})
