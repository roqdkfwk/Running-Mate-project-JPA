<script setup>
import { ref, computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
// import { storeToRefs } from 'pinia'
import { useGroupStore } from '@/stores/group'

const groupStore = useGroupStore()

const currentPage = ref(1)
const pageSize = 10
const sortBy = ref('pace')

const filteredGroups = computed(() => groupStore.groups)
// const { groups, totalPages } = storeToRefs(groupStore)
// const filteredGroups = computed(() => groups.value)

// 서버 호출: 서버-사이드 페이징 & 소팅
const loadPage = () => {
  if (sortBy.value === 'pace') {
    groupStore.sortByCondition(currentPage.value - 1, 10, 'pace,asc')
  } else if (sortBy.value === 'frequency') {
    groupStore.sortByCondition(currentPage.value - 1, 10, 'frequency,asc')
  } else if (sortBy.value === 'totalDistance') {
    groupStore.sortByCondition(currentPage.value - 1, 10, 'totalDistance,desc')
  }
}

onMounted(loadPage)

function changeSort(field) {
  sortBy.value = field
  currentPage.value = 1
  loadPage()
}

function goToPage(page) {
  if (page < 1 || page > groupStore.totalPages) return
  currentPage.value = page
  loadPage()
}

const getRecordHeader = () => {
  if (sortBy.value === 'pace') return 'Pace'
  if (sortBy.value === 'frequency') return 'Frequency'
  if (sortBy.value === 'totalDistance') return 'Total distance'
  return ''
}

const getGroupRecord = (group) => {
  if (sortBy.value === 'pace')
    return `${Math.floor(group.pace / 60)}' ${group.pace % 60}''`
  if (sortBy.value === 'frequency')
    return `${group.frequency} 회`
  if (sortBy.value === 'totalDistance')
    return `${Math.floor(group.totalDistance)}km`
  return ''
}

const joinGroup = (groupId) => {
  groupStore.joinGroup(groupId)
}
</script>

<template>
  <div class="container">
    <!-- 정렬 버튼 -->
    <div class="sort-buttons">
      <button @click="changeSort('pace')">Pace</button>
      <button @click="changeSort('frequency')">Frequency</button>
      <button @click="changeSort('totalDistance')">Total distance</button>
    </div>

    <!-- 헤더 -->
    <div class="group-item header">
      <div class="rank">랭킹</div>
      <div class="name">이름</div>
      <div class="record">{{ getRecordHeader() }}</div>
      <div></div>
    </div>

    <!-- group 출력 -->
    <div v-for="(group, idx) in filteredGroups" :key="group.groupId" class="group-item">
      <div class="rank">{{ (currentPage - 1) * pageSize + idx + 1 }}위</div>
      <RouterLink :to="{ name: 'groupMemberRank', params: { groupId: group.groupId } }" class="name">
        {{ group.groupName }}
      </RouterLink>
      <div class="record">{{ getGroupRecord(group) }}</div>
      <button class="join-button" @click="joinGroup(group.groupId)">가입하기</button>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="goToPage(1)" :disabled="currentPage === 1">«</button>
      <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 1">‹</button>
      <button v-for="page in groupStore.totalPages" :key="page" :class="{ active: page === currentPage }"
        @click="goToPage(page)">
        {{ page }}
      </button>
      <button @click="goToPage(currentPage + 1)" :disabled="currentPage === groupStore.totalPages">›</button>
      <button @click="goToPage(groupStore.totalPages)" :disabled="currentPage === groupStore.totalPages">»</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgba(2, 21, 30, 0.8);
  border-radius: 10px;
  color: white;
}

.sort-buttons {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.sort-buttons button {
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: rgba(2, 21, 30, 0.8);
  cursor: pointer;
  color: white;
}

.sort-buttons button:hover {
  background-color: #565e64;
}

.group-item {
  display: grid;
  grid-template-columns: 80px 120px 150px auto;
  align-items: center;
  column-gap: 10px;
  margin-bottom: 10px;
}

.header {
  font-weight: bold;
}

/* 컬럼별 정렬 방향 지정 */
.rank {
  justify-self: center;   /* 랭킹 칸은 가운데 */
}

.name {
  justify-self: center;    /* 이름 칸은 왼쪽 */
}

.group-item.header .name {
  justify-self: left;
  padding-left: 45px; /* 필요에 따라 여백 조정 */
}

.record {
  justify-self: center;   /* 기록 칸은 가운데로! */
}

.group-item.header .record {
  justify-self: center;
}


.join-button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: rgba(108, 117, 125, 0.5);
  color: white;
  cursor: pointer;
}

.join-button:hover {
  background-color: #565e64;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.pagination button {
  margin: 0 2px;
  padding: 5px 10px;
  border: none;
  background-color: #f4f4f4;
  cursor: pointer;
}

.pagination .active {
  background-color: #666;
  color: white;
}
</style>
