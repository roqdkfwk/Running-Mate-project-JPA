<template>
    <div id="container">
        <header>
            <nav>
                <RouterLink to="/">Running Mate</RouterLink>
                <StravaAuth id="strava" />
                <div v-if="!isLoggedIn">
                    <RouterLink id="bar" :to="{ name: 'loginView' }">Login</RouterLink> |
                    <RouterLink id="bar" :to="{ name: 'signupView' }">Signup</RouterLink>
                </div>
                <!-- 로그인 후 -->
                <div v-else>
                    <a href="#" id="bar" @click.prevent="logout">Logout</a> |
                    <RouterLink id="bar" :to="{ name: 'createGroup' }">Create Group</RouterLink>

                </div>
                <!-- <button @click="() => console.log(token)">sdf</button> -->
            </nav>
        </header>
    </div>
</template>

<script setup>

import StravaAuth from '@/views/StravaAuth.vue';
import { onMounted, ref, computed } from 'vue'
import { useMainStore } from '@/stores/main'

import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';



const store = useMainStore();
const userStore = useUserStore();
const router = useRouter();

const token = ref('');
onMounted(() => {
    token.value = sessionStorage.getItem('accessToken')
    if(!!token) {
        isLoggedIn.value=true;
        userStore.accessToken=token.value
    }
    console.log(!!token)
    console.log("헤더도 마운트 됨")
})

const isLoggedIn = computed(() => !!userStore.accessToken);
const logout = () => {
    store.logout();
    sessionStorage.removeItem('accessToken');
    userStore.accessToken = null;
    token.value = '';
    router.push({ name: 'loginView' });
};

// computed로 로그인 상태를 확인합니다.


</script>

<style scoped>
#container {
    text-align: center;
}

nav {
    margin-top: 25px;
}

nav a:first-child {
    font-weight: bold;
    font-size: 50px;
    text-decoration: none;
    color: black;
    text-shadow: 2px 2px 5px rgba(255, 255, 255, 100);
    /* Adds depth to the text */
}

nav a {
    font-weight: bold;
    font-size: 40px;
    text-decoration: none;
    color: black;
}

nav div {
    position: absolute;
    top: 50px;
    right: 80px;
}

div>a.router-link-exact-active {
    color: #6c757d;
}

#bar {
    font-size: 15px;

}

#strava {
    width: 200px;
    position: absolute;
    top: 80px;
    right: 30px;
}
</style>