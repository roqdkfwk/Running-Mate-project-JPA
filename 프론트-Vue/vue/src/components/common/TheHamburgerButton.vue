<!-- 
    전 : 유저, 그룹은 상관없음. 내 로그 >> 로그인 페이지
    후 : 유저, 그룹, 내 로그
    사이드바 디자인은 천천히 
 -->
<!-- 
    전 : 유저, 그룹은 상관없음. 내 로그 >> 로그인 페이지
    후 : 유저, 그룹, 내 로그
    사이드바 디자인은 천천히 
 -->
<template>
    <div>
        <button class="hamburger" @click="toggleSidebar">☰</button>
        <div :class="['sidebar', { 'is-open': isSidebarOpen }]">
            <br>
            <ul>
                <li>
                    <RouterLink :to="{ name: 'myPageView' }">My Page</RouterLink>
                </li>
                <li>
                    <RouterLink :to="{ name: 'myRecord' }">My Record</RouterLink>
                    <!-- <a href="#">여기는 나의 로그</a> -->
                </li>
                <li>
                    <RouterLink :to="{ name: 'totalUserRank' }">User Rank</RouterLink>
                    <!-- <a href="#">여기는 유저 보기</a> -->
                </li>
                <li>
                    <RouterLink :to="{ name: 'groupRank' }">Group Rank</RouterLink>
                    <!-- <a href="#">여기는 그룹 보기</a> -->
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const isSidebarOpen = ref(false)
const router = useRouter()

const toggleSidebar = function () {
    isSidebarOpen.value = !isSidebarOpen.value
}

// 라우터 이벤트를 감지하여 사이드바 상태를 업데이트
// 사이드바를 통해 페이지를 이동한 후 사이드바를 자동으로 닫는 로직
const closeSidebarOnRouteChange = function () {
    if (isSidebarOpen.value) {
        isSidebarOpen.value = false
    }
}

onMounted(() => {
    router.afterEach(closeSidebarOnRouteChange)
})

onUnmounted(() => {
    router.afterEach(closeSidebarOnRouteChange)
})
</script>

<style scoped>
.hamburger {
    font-size: 40px;
    border: none;
    background: none;
    cursor: pointer;
    position: fixed;
    top: 20px;
    left: 20px;
    z-index: 1001;
    /* 사이드바보다 z-index 높게 설정 */
    color : white;
}

.sidebar {
    position: fixed;
    top: 0;
    left: 0;
    width: 0;
    height: 100%;
    background-color: rgb(2, 21, 30, 0.8);
    border-radius: 10px;
    color: white;
    overflow-x: hidden;
    transition: 0.5s;
    padding-top: 0;
    padding-left: 0;
    border-radius: 0;
    z-index: 1000;
    /* 그림자를 위한 기본 z-index 설정 */
}

.sidebar.is-open {
    width: 250px;
    padding-top: 60px;
    padding-left: 50px;
    border-radius: 10px;
    /* 그림자 추가 */
    box-shadow: 2px 0 8px rgba(0.6, 0.6, 0.6, 0.6);
}

.sidebar ul {
    list-style-type: none;
    padding: 0;
}

.sidebar ul li {
    padding: 8px 16px;
    text-align: left;
}

.sidebar ul li a {
    color: #ecf0f1;
    text-decoration: none;
    font-size: 25px;
    display: block;
    transition: 0.3s;
}

.sidebar ul li a {
    color: white;
}

.sidebar ul li a:hover {
    color: rgb(2, 21, 30, 0.8);
    /* 호버 시 색상 변경 */
}

.hamburger:hover {
    color: rgb(2, 21, 30, 0.8);
}
</style>