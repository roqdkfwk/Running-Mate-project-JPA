import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    headers: {
      // 브라우저에게 캐시하지 말라고 명시
      'Cache-Control': 'no-store'
    },
    watch: {
      usePolling: true, // 파일 변경 감지 강화 (특히 Windows 환경에서 유용)
    }
  }
})
