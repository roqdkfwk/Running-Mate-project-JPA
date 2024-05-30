<template>
  <div>
    <button type="button" class="btn btn-secondary" @click.prevent="authorize">strava에서 data 불러오기</button>
  </div>
</template>

<script setup>
import { useStravaStore } from '@/stores/strava';
import { useRoute, useRouter } from 'vue-router';


const stravaStore = useStravaStore();
const route = useRoute();
const router = useRouter();

const clientId = 127006;
const redirectUri = 'http://localhost:5173/strava-callback'; // 이 URL은 리디렉션 URI로 설정해야 합니다.

const authorize = () => {
  if (sessionStorage.getItem('accessToken') != null) {
    const authorizationUrl = `https://www.strava.com/oauth/authorize?client_id=${clientId}&response_type=code&redirect_uri=${redirectUri}&scope=read,activity:read_all,activity:write`;
    window.location.href = authorizationUrl;
  }
  else alert("로그인 후 이용해주세요!")

};
const code = route.query.code;
if (code) {
  stravaStore.fetchAccessToken(code).then(() => {
    router.push('/'); // 인증 후 메인 페이지로 리디렉션
  });
}



</script>

<style scoped>

/* .container {
  position: relative;
}

button {
  position: absolute;
  top: 10px;
  right: 0px;
} */

</style>