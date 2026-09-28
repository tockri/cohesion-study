import { defineConfig } from 'vite'

export default defineConfig({
  server: {
    proxy: {
      '/todos': 'http://localhost:8080',
    },
  },
})
