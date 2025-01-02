<template>
  <div class="comparison-container">
    <h2>기록 비교</h2>
    <div class="record-grid">
      <div class="user-column">
        <h3>나의 기록</h3>
      </div>
      <div class="user-column">
        <h3>상대방의 기록</h3>
      </div>


      <div class="record-item">
        <span class="value">{{ Math.floor(myRecord.highestPace / 60) }}' {{ Math.floor(myRecord.highestPace % 60)
          }}''</span>
      </div>
      <div class="record-item">
        <span class="value">{{ Math.floor(rivalRecord.highestPace / 60) }}' {{ Math.floor(rivalRecord.highestPace % 60)
          }}''</span>
      </div>

      <div class="record-item">
        <span class="value">{{ myRecord.frequency }}회</span>
      </div>
      <div class="record-item">
        <span class="value">{{ rivalRecord.frequency }}회</span>
      </div>

      <div class="record-item">
        <span class="value">{{ (myRecord.totalDistance / 1000).toFixed(2) }}km</span>
      </div>
      <div class="record-item">
        <span class="value">{{ (rivalRecord.totalDistance / 1000).toFixed(2) }}km</span>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const myRecord = ref({})
const rivalRecord = ref({})
const route = useRoute()
const store = useUserStore()

const fetchRecords = function () {
  axios.get(`http://localhost:8080/rank/user/${route.params.rivalId}`, {

    headers: {
      Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: sessionStorage.getItem('userId')
    },
  })
    .then((response) => {
      rivalRecord.value = response.data
      console.log(rivalRecord)
      return axios.get(`http://localhost:8080/myRR`, {
        headers: {
          Authorization: `${sessionStorage.getItem('accessToken')}`,
          userId: sessionStorage.getItem('userId')
        },
      })
    })
    .then((response) => {
      console.log(myRecord)
      myRecord.value = response.data;

    })
    .catch((error) => {
      console.error('Failed to fetch records:', error)
    })
}

onMounted(() => {
  fetchRecords()
})
</script>

<style scoped>
.comparison-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(2, 21, 30, 0.8);
  border-radius: 10px;
  color: white;
}

.record-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  margin-top: 20px;
}

.user-column {
  text-align: center;
  font-weight: bold;
}

.record-item {
  display: flex;
  justify-content: space-evenly;
  padding: 10px;
  background-color: rgb(2, 21, 30, 0.8);
  border-radius: 5px;
}

.label {
  font-weight: bold;
}

.value {
  margin-left: 10px;
}
</style>