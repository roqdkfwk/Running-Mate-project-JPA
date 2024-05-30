<script setup>
import BadgeLog from '@/components/record/BadgeLog.vue';
import RecentRecord from '@/components/record/RecentRecord.vue';
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'


const router = useRouter()

const my = function() {
    router.push({name: 'myRecord'})
}
const recent = function() {
    router.push({name: 'recentRecord'})
}
const badge = function() {
    router.push({name: 'badgeLog'})
}

onMounted(()=>{
    my()
})

</script>

<template>
    <div class="container">
        <div>
            <h2>My Record</h2>
            <button class="btn" @click="my">나의 로그</button>
            <button class="btn" @click="recent">최근 활동</button>
        </div>
        <RouterView />
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
    animation: fadeIn 0.4s ease-in-out;
}

.btn {
    margin: 10px 20px;

    padding: 10px 15px;
    font-size: 18px; /* 버튼 텍스트 크기 */

    color : white;
    background-color: rgba(80, 126, 148, 0.8);
    border-radius: 10px;
    color: white;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translate(-5px, -5px);
    }

    to {
        opacity: 1;
        transform: translate(0, 0);
    }
}

.appear {
    animation: fadeIn 0.4s ease-in-out;
}
</style>
