<template>
    <div class="container">
        <h2>My Page</h2>
        <div class="user-info-box">

            <div class="user-info-row">
                <label for="userId">ID:</label>
                <input id="userId" v-model="user.userId" type="text" />
            </div>

            <div class="user-info-row">
                <label for="userPwd">Password:</label>
                <input id="userPwd" v-model="user.userPwd" type="password" />
            </div>

            <div class="user-info-row">
                <label for="userName">Name:</label>
                <input id="userName" v-model="user.userName" type="text" />
            </div>

            <div class="user-info-row">
                <label for="userNick">Nickname:</label>
                <input id="userNick" v-model="user.userNick" type="text" />
            </div>

            <div class="user-info-row">
                <label for="email">Email:</label>
                <input id="email" v-model="user.email" type="email" />
            </div>

            <div class="user-info-row">
                <label for="address">Address:</label>
                <input id="address" v-model="user.address" type="text" />
            </div>

            <div class="user-info-row">
                <label for="phone">Phone:</label>
                <input id="phone" v-model="user.phone" type="text" />
            </div>

            <div class="user-info-row">
                <label for="exposure">Exposure:</label>
                <input id="exposure" v-model="user.exposure" type="checkbox" />
            </div>

            <div class="actions">
                <button @click="onSave" class="btn-save">수정</button>
                <button @click="withdraw" class="btn-delete">회원탈퇴</button>
            </div>

        </div>
    </div>
</template>

<script setup>

import { useAuthStore } from "@/stores/auth";
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';

const user = ref({})
const userStore = useUserStore()
const authStore = useAuthStore()

onMounted(() => {
    userStore.myPage()
    user.value = userStore.user
})

const withdraw = function () {
    authStore.withdraw()
}
</script>


<style scoped>
.container {
    margin: 20px auto;
    padding: 20px;
    width: 600px;
    background-color: rgba(2, 21, 30, 0.8);
    border-radius: 10px;
    color: white;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.user-info-box {
    display: flex;
    flex-direction: column;
    gap: 12px;
    margin-top: 20px;
}

/* 한 줄(row)마다 flex 컨테이너 */
.user-info-row {
    display: flex;
    align-items: center;
    gap: 12px;
}

/* 1) 레이블: 고정 폭이 필요 없으면 auto, 좌측 정렬 */
.user-info-row label {
    width: 120px;
    /* 필요에 따라 더 늘리거나 줄이세요 */
    text-align: left;
    /* ← 우측 정렬에서 좌측 정렬로 변경 */
    font-weight: 500;
}

/* 2) 입력창: flex-grow 대신 max-width로 크기 제한 */
.user-info-row input[type="text"],
.user-info-row input[type="password"],
.user-info-row input[type="email"] {
    flex: 1;
    /* 가변 너비 유지 */
    max-width: 200px;
    /* 최대 400px까지만 늘어납니다 */
    width: 100%;
    /* flex와 함께 쓰여야 동작합니다 */
    padding: 6px 8px;
    color: black;
}

/* 체크박스는 따로 조정 */
.user-info-row input[type="checkbox"] {
    margin-left: 0;
    width: auto;
}
</style>
