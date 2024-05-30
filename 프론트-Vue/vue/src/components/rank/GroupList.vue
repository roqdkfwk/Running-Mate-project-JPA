<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useGroupStore } from '@/stores/group';
import { useRankStore } from '@/stores/rank';

const groupStore = useGroupStore();
const rankStore = useRankStore();
const groups= ref([])

const currentPage = ref(1);
const pageSize = 10;
const sortBy = ref(''); // 현재 정렬 기준

// onMounted에서 그룹 데이터를 불러옵니다.
onMounted(() => {
  rankStore.sortGroupByHighestPace();
});

const totalPages = computed(() => {
  return Math.ceil(filteredGroups.value.length / pageSize);
});

const filteredGroups = computed(() => {
  return rankStore.groups; // 검색 필터가 없어서 모든 그룹을 반환
});

const sortedGroups = computed(() => {
  if (sortBy.value === 'pace') {
    return [...filteredGroups.value].sort((a, b) => a.pace - b.pace); // 페이스 기준 정렬
  } else if (sortBy.value === 'frequency') {
    return [...filteredGroups.value].sort((a, b) => b.frequency - a.frequency); // 빈도 기준 정렬
  } else if (sortBy.value === 'totalDistance') {
    return [...filteredGroups.value].sort((a, b) => b.totalDistance - a.totalDistance); // 누적거리 기준 정렬
  } else {
    return filteredGroups.value; // 기본 정렬
  }
});

const paginatedGroups = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return sortedGroups.value.slice(start, end);
});

const visiblePages = computed(() => {
  let pages = [];
  let startPage = Math.max(1, currentPage.value - 2);
  let endPage = Math.min(totalPages.value, currentPage.value + 2);

  if (startPage === 1) {
    endPage = Math.min(5, totalPages.value);
  } else if (endPage === totalPages.value) {
    startPage = Math.max(1, totalPages.value - 4);
  }

  if (totalPages.value < 5) {
    startPage = 1;
    endPage = totalPages.value;
  }

  for (let i = startPage; i <= endPage; i++) {
    pages.push(i);
  }

  return pages;
});

const setPage = (page) => {
  currentPage.value = page;
};

const goToFirstPage = () => {
  currentPage.value = 1;
};

const goToLastPage = () => {
  currentPage.value = totalPages.value;
};

const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const joinGroup = (groupId) => {
  groupStore.joinGroup(groupId);
};



// 레코드 헤더와 내용 가져오기
const getRecordHeader = () => {
  if (sortBy.value === 'pace') return 'Pace';
  if (sortBy.value === 'frequency') return 'Frequency';
  if (sortBy.value === 'totalDistance') return 'Total distance';
  return sortByPace();
};

const getGroupRecord = (group) => {
  if (sortBy.value === 'pace') return `${Math.floor((group.pace) / 60)}' ${Math.floor((group.pace) % 60)}''`;
  if (sortBy.value === 'frequency') return `${group.frequency} 회`;
  if (sortBy.value === 'totalDistance') return `${Math.floor(group.totalDistance)}km`;
  return ''
};


// 정렬 버튼 클릭 핸들러
const sortByPace = function() {
  sortBy.value='pace'
  rankStore.sortByHighestPace()
  groups.value = rankStore.groups
  console.log("페이스로드됨")
};

const sortByFrequency = () => {
  sortBy.value='frequency'
  rankStore.sortGroupByFrequency()
  groups.value = rankStore.groups
  console.log("프리퀀시 로드됨")
};

const sortByDistance = () => {
  sortBy.value='totalDistance'
  rankStore.sortGroupByTotalDistance()
  groups.value = rankStore.groups
  console.log("디스턴스 로드됨")
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
    <!-- 그룹 목록 -->
    <div>
      <div class="group-item header">
        <div class="rank">랭킹</div>
        <div class="name">이름</div>
        <div class="record">{{ getRecordHeader() }}</div>
      </div>
      <div v-for="(group, index) in paginatedGroups" :key="group.groupId" class="group-item">
        <div class="rank">{{ (currentPage - 1) * pageSize + index + 1 }}위</div>
        <RouterLink :to="{ name: 'groupMemberRank', params: { groupId: group.groupId } }" class="name">{{ group.groupName }}</RouterLink>
        <div class="record">{{ getGroupRecord(group) }}</div>
        <button @click="joinGroup(group.groupId)" class="join-button">가입하기</button>
      </div>
    </div>
    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="goToFirstPage">&laquo;</button>
      <button @click="goToPreviousPage">&lsaquo;</button>
      <button v-for="page in visiblePages" :key="page" @click="setPage(page)" :class="{ 'active': currentPage.value === page }">
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
  background-color: rgb(2, 21, 30, 0.8);
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
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: rgb(2, 21, 30, 0.8);
  cursor: pointer;
  color : white;
}

.sort-buttons button:hover {
  background-color: #565e64;
} 

.group-item {
  display: grid;
  grid-template-columns: 100px 100px 150px auto;
  align-items: center;
  margin-bottom: 10px;
}

.header {
  font-weight: bold;
}

.rank {
  width: 50px;
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
  border: 1px solid #ccc;
  background-color: #f4f4f4;
  cursor: pointer;
}

.pagination .active {
  background-color: #666;
  color: white;
}
</style>
