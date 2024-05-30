<script setup>
import { ref } from 'vue'
import { useGroupStore } from '@/stores/group'
import { useMainStore } from '@/stores/main'

const store = useGroupStore()
const mainStore = useMainStore()

const group = ref({
  groupName: '',
  goalPace: '',
  goalFrequency: '',
  goalTotalDistance: '',
  conPace: '',
  conFrequency: '',
  conTotalDistance: '',
})

const submitForm = function () {
  console.log("GROUPCREATE main : ", mainStore.loginUser.userId)
  store.createGroup(group.value)
}
</script>

<template>
  <div class="container">
    <h2>그룹 생성하기</h2>
    <br>
    <form @submit.prevent="submitForm">
      <div class="content">
        <label for="groupName">그룹명 : </label>
        <input id="groupName" v-model="group.groupName" required />
      </div>
      <div class="content">
        <label for="goalPace">목표 Pace : </label>
        <input id="goalPace" v-model="group.goalPace" />
      </div>
      <div class="content">
        <label for="goalFrequency">목표 Frequency</label>
        <input id="goalFrequency" v-model="group.goalFrequency" />
      </div>
      <div class="content">
        <label for="goalTotalDistance">목표 Total distance</label>
        <input id="goalTotalDistance" v-model="group.goalTotalDistance" />
      </div>
      <div class="content">
        <label for="conPace">최소 Pace : </label>
        <input id="conPace" v-model="group.conPace" />
      </div>
      <div class="content">
        <label for="conFrequency">최소 Frequency : </label>
        <input id="conFrequency" v-model="group.conFrequency" />
      </div>
      <div class="content">
        <label for="conTotalDistance">최소 Total Distance : </label>
        <input id="conTotalDistance" v-model="group.conTotalDistance" />
      </div>
      <div class="content">
        <button type="submit">Create</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.container {
  width: 80%;
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: rgb(2, 21, 30, 0.8);
  border-radius: 10px;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  /* 세로 중간 정렬 */
}

form {
  display: flex;
  flex-direction: column;
  width: 100%;
  /* 폼의 너비를 부모 컨테이너에 맞춤 */
  height: 100%;
  /* 폼의 높이를 부모 컨테이너에 맞춤 */
}

div {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
}

label {
  font-weight: bold;
  margin-right: 10px;
  /* 레이블과 입력 필드 간의 여백 추가 */
}

input {
  width: calc(100% - 20px);
  /* 부모 컨테이너의 너비에서 20px을 뺀 값으로 설정 */
  max-width: 300px;
  /* 모든 입력 필드의 고정된 최대 너비 설정 */
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  justify-content: center;
}

button {
  width: 100px;
  margin: 5% 45%;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: #ccc;
  color: black;
  cursor: pointer;
}

button:hover {
  background-color: #ccc;
}
</style>
