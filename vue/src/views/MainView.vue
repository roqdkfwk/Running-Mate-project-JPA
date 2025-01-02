<template>
  <div class="outer-container">
    <div class="profile-container">
      <ProfilePicture />
    </div>
    <RouterView/>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import MyLog from './MyLog.vue';
import ProfilePicture from '@/components/user/ProfilePicture.vue';
import { useMainStore } from '@/stores/main';
import { useUserStore } from '@/stores/user';
import RecordView from './RecordView.vue';
import MyRecord from '@/components/record/MyRecord.vue';
import { useRouter } from 'vue-router';

const store = useMainStore()
const userStore = useUserStore()
const token = ref('')
const isLoggedIn = ref(false)
const router = useRouter()

onMounted(() => {
  token.value = sessionStorage.getItem('accessToken')
  userStore.accessToken = token.value
  if (!!token) {
    isLoggedIn.value = true;
    router.push({ name: 'recordView' })
    console.log("로그인은 되어있다")
  }
  else {
    router.push({ name: 'totalUserRank' })
    console.log("토탈유저랭크로 가고 싶음")
  }
})


const logout = function () {
  store.logout()
  token.value = ''

}
</script>

<style scoped>
.outer-container {
  display: flex;
  justify-content: center;
}

.container {
  margin: 20px auto;
  padding: 20px;
  width: 600px;
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}



.profile-container {
  margin-left: 20px;
  margin-right: 50px;
  margin-top: 20px;
  padding: 10px;
  width: 150px;
  /* 박스의 너비 */
  height: 150px;
  /* 박스의 높이 */
  background-color: rgba(240, 240, 240, 1);
  /* 박스의 배경색 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  /* 박스의 모서리 둥글게 */
}
</style>