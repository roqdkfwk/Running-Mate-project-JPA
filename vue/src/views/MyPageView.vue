<script setup>

import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';

const user = ref({})
const store = useUserStore()

onMounted(() => {
    store.myPage()
    user.value = store.user
})
</script>

<template>
    <div class="container">
        <h2>My Page</h2>
        <div class="user-info-box">
            <div class="user-info">
                <label> ID: </label>
                <label> Password: </label>
                <label> Name: </label>
                <label> Nickname: </label>
                <label> Email: </label>
                <label> Address: </label>
                <label> Phone: </label>
                <label> Exposure: </label>
            </div>

            <div class="user-info">
                <input v-model="user.userId" type="text" />
                <input v-model="user.userPwd" type="password" />
                <input v-model="user.userName" type="text" />
                <input v-model="user.userNick" type="text" />
                <input v-model="user.email" type="email" />
                <input v-model="user.address" type="text" />
                <input v-model="user.phone" type="text" />
                <input v-model="user.exposure" type="checkbox" />
            </div>
        </div>
    </div>
</template>

<style scoped>
.container {
    margin: 20px auto;
    padding: 20px;
    width: 600px;
    background-color: rgb(2, 21, 30, 0.8);
    border-radius: 10px;
    color: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.user-info-box {
    /* padding: 20px; */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    width: 70%;
    max-width: 600px;
    margin: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
}

input {
    margin: 15px;
    color: black;
}

label {
    margin: 15px;
}

.user-info {
    text-align: left;
    margin: auto;
    display: flex;
    flex-direction: column;
    align-items: flex-start;

}

.user-info p {
    margin: 5px 0;
}
</style>
