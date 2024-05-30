// import { createPinia } from 'pinia'

// import App from './App.vue'
// import router from './router'

// Bootstrap CSS와 JS 파일 import
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// const app = createApp(App)

// app.use(createPinia())
// app.use(router)

// app.mount('#app')


import { createApp } from 'vue'
import { createPinia } from "pinia";
import piniaPersisted from "pinia-plugin-persistedstate";
import App from "./App.vue";
import router from "./router";

const pinia = createPinia();
pinia.use(piniaPersisted);

createApp(App).use(router).use(pinia).mount("#app");
export default pinia;