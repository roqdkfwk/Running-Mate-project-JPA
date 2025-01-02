<template>
    <div class="recent-activities">
        <h2>나의 최근 활동</h2>
        <div class="activity-container">
            <div class="activity-box">
            <div class="activity-title">
                <span><strong>날짜</strong></span>
                <span><strong>거리</strong></span>
                <span><strong>시간</strong></span>
                <span><strong>페이스</strong></span>
            </div>
            </div>
        </div>
        <div class="activity-container" v-for="activity in recentActivities" :key="activity.date">
            <div class="activity-box">
                <div class="activity-details">
                    <span>{{ activity.date }}</span>
                    <span>{{ activity.distance }}</span>
                    <span>{{ activity.time }}</span>
                    <span>{{ activity.pace }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 최근 활동 데이터
const recentActivities = ref([]);

// 로컬 스토리지에서 데이터를 가져와서 변환
onMounted(() => {
    const rawData = localStorage.getItem('activities');
    const activities = rawData ? JSON.parse(rawData) : [];

    // 데이터를 변환하여 recentActivities에 저장
    recentActivities.value = activities.map(activity => {
        const date = new Date(activity.date).toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
        const distance = (activity.distance / 1000).toFixed(1) + 'km'; // m를 km로 변환
        const time = (activity.runTime / 60).toFixed(0) + ' minutes'; // 초를 분으로 변환
        const paceMinutes = Math.floor(activity.pace / 60);
        const paceSeconds = Math.floor(activity.pace % 60);
        const pace = `${paceMinutes}' ${paceSeconds.toString().padStart(2, '0')}''`; // 페이스 형식 지정

        return { date, distance, time, pace };
    });
});


</script>

<style scoped>
.recent-activities {
    border-radius: 10px;
    color: white;
    margin-top: 20px;

}

.activity-container {
    display: flex;
    justify-content: center;
    /* 가운데 정렬을 위한 설정 */
    margin-bottom: 20px;
}

.activity-box {
    width: 75%;
    display: flex;
    flex-direction: column;
}

.activity-title {
    width: 90%;
    display: flex;
    justify-content: space-between;
    margin-left : 30px;
    /* margin-bottom: 10px; */
    margin-top: 20px;
}

.activity-details {

    display: flex;
    justify-content: space-between;
    /* margin-bottom: 10px; */
    margin-top: 20px;
}

.activity-details span {
    margin-right: 10px;
}
</style>