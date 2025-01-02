<!-- 
    findId, findPwd, SignupView, kakao & naver & google Login
 -->
<template>
  <div class="login-container">
    <h1>로그인</h1>
    <form @submit.prevent="login" class="login-form">
      <div class="input-group">
        <input type="text" id="id" v-model="user.userId" placeholder="아이디" required>
      </div>
      <div class="input-group">
        <input type="password" id="password" v-model="user.userPwd" placeholder="비밀번호" required>
      </div>
      <button type="submit" class="login-button">로그인</button>
    </form>
    <div class="links">
      <router-link to="/find-id">아이디 찾기</router-link> |
      <router-link to="/find-password">비밀번호 찾기</router-link> |
      <router-link to="/signup">회원가입</router-link>
    </div>

  </div>
</template>

<script setup>
import { useMainStore } from '@/stores/main';
import { useRouter } from 'vue-router'
import { ref } from 'vue';

const store = useMainStore();
const router = useRouter();
const user = ref({
  userId: '',
  userPwd: ''
})

const login = function () {
  store.login(user.value)
  router.push({ name: 'main' })
  console.log("로그인 됨")
}


</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background-color: rgba(255,255,255,0.7);
  text-align: center;
}

.input-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  
}

.input-group input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: rgba(255,255,255,0.8);
  color: black;
  cursor: pointer;
}

.login-button:hover {
  background-color: #6c757d;
  color : white;
}

.links {
  margin: 20px 0;
}

.links a {
  color: black;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}


</style>