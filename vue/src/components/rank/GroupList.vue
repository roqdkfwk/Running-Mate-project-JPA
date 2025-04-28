<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useGroupStore } from '@/stores/group';
import { useRankStore } from '@/stores/rank';

const groupStore = useGroupStore();
const rankStore = useRankStore();

// 페이징 & 정렬 상태
const currentPage = ref(1);
const pageSize = 10;
const sortBy = ref('pace');  // 기본 정렬 기준을 pace 로 설정

// 페이지 진입 시 자동으로 pace 기준 정렬 호출
onMounted(() => {
  sortByPace();
});

// 1) 전체 그룹 리스트(필터링 없음)
const filteredGroups = computed(() => rankStore.groups);

// 2) 클라이언트 측 정렬 (서버 정렬과 동기화)
const sortedGroups = computed(() => {
  if (sortBy.value === 'pace') {
    return [...filteredGroups.value].sort((a, b) => a.pace - b.pace);
  }
  if (sortBy.value === 'frequency') {
    return [...filteredGroups.value].sort((a, b) => b.frequency - a.frequency);
  }
  if (sortBy.value === 'totalDistance') {
    return [...filteredGroups.value].sort((a, b) => b.totalDistance - a.totalDistance);
  }
  return filteredGroups.value;
});

// 3) 페이징 슬라이스 계산
const paginatedGroups = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  return sortedGroups.value.slice(start, start + pageSize);
});

// 4) 전체 페이지 수
const totalPages = computed(() =>
  Math.ceil(filteredGroups.value.length / pageSize)
);

// 페이징 핸들러
const setPage = (page) => { currentPage.value = page; };
const goToFirstPage = () => { currentPage.value = 1; };
const goToLastPage = () => { currentPage.value = totalPages.value; };
const goToPreviousPage = () => { if (currentPage.value > 1) currentPage.value--; };
const goToNextPage = () => { if (currentPage.value < totalPages.value) currentPage.value++; };

// 레코드 헤더
const getRecordHeader = () => {
  if (sortBy.value === 'pace') return 'Pace';
  if (sortBy.value === 'frequency') return 'Frequency';
  if (sortBy.value === 'totalDistance') return 'Total distance';
  return '';
};

// 레코드 표시
const getGroupRecord = (group) => {
  if (sortBy.value === 'pace') return `${Math.floor(group.pace / 60)}' ${group.pace % 60}''`;
  if (sortBy.value === 'frequency') return `${group.frequency} 회`;
  if (sortBy.value === 'totalDistance') return `${Math.floor(group.totalDistance)}km`;
  return '';
};

// 정렬 버튼 핸들러
function sortByPace() {
  sortBy.value = 'pace';
  rankStore.sortGroupByHighestPace();
}

function sortByFrequency() {
  sortBy.value = 'frequency';
  rankStore.sortGroupByFrequency();
}

function sortByDistance() {
  sortBy.value = 'totalDistance';
  rankStore.sortGroupByTotalDistance();
}

// 그룹 가입
const joinGroup = (groupId) => {
  groupStore.joinGroup(groupId);
};
</script>

<template>
  <div class="container">
    <!-- 정렬 버튼 -->
    <div class="sort-buttons">
      <button @click="sortByPace">Pace</button>
      <button @click="sortByFrequency">Frequency</button>
      <button @click="sortByDistance">Total distance</button>
    </div>

    <!-- 그룹 목록 헤더 -->
    <div class="group-item header">
      <div class="rank">랭킹</div>
      <div class="name">이름</div>
      <div class="record">{{ getRecordHeader() }}</div>
    </div>

    <!-- 그룹 아이템 -->
    <div v-for="(group, index) in paginatedGroups" :key="group.groupId" class="group-item">
      <div class="rank">{{ (currentPage - 1) * pageSize + index + 1 }}위</div>
      <RouterLink :to="{ name: 'groupMemberRank', params: { groupId: group.groupId } }" class="name">
        {{ group.groupName }}
      </RouterLink>
      <div class="record">{{ getGroupRecord(group) }}</div>
      <button @click="joinGroup(group.groupId)" class="join-button">
        가입하기
      </button>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="goToFirstPage">&laquo;</button>
      <button @click="goToPreviousPage">&lsaquo;</button>
      <button v-for="page in totalPages" :key="page" @click="setPage(page)" :class="{ active: currentPage === page }">
        {{ page }}
      </button>
      <button @click="goToNextPage">&rsaquo;</button>
      <button @click="goToLastPage">&raquo;</button>
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
  grid-template-columns: 80px 1fr 150px auto;
  align-items: center;
  margin-bottom: 10px;
}

.header {
  font-weight: bold;
}

.rank {
  text-align: center;
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
