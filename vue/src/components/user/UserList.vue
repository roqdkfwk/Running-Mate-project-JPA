<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useMainStore } from '@/stores/main';
import { useRankStore } from '@/stores/rank';

const props = defineProps({
  users: Array,
  searchQuery: String,
  searchFilter: String,
});

const currentPage = ref(1);
const pageSize = 10;
const sortBy = ref(''); // 현재 정렬 기준
const showRivalsOnly = ref(false); // 라이벌만 보기

const totalPages = computed(() => {
  return Math.ceil(filteredUsers.value.length / pageSize);
});

const filteredUsers = computed(() => {
  let users = props.users;
  if (props.searchQuery) {
    users = users.filter(user => user[props.searchFilter].toLowerCase().includes(props.searchQuery.toLowerCase()));
  }
  if (showRivalsOnly.value) {
    users = users.filter(user => user.isMyRival); // 라이벌만 필터링
  }
  return users;
});

const sortedUsers = computed(() => {
  if (sortBy.value === 'highest_pace') {
    return [...filteredUsers.value].sort((a, b) => a.highestPace - b.highestPace); // highest_pace 기준 정렬 로직
  } else if (sortBy.value === 'frequency') {
    return [...filteredUsers.value].sort((a, b) => b.frequency - a.frequency); // frequency 기준 정렬 로직
  } else if (sortBy.value === 'total_distance') {
    return [...filteredUsers.value].sort((a, b) => b.totalDistance - a.totalDistance); // total_distance 기준 정렬 로직
  } else {
    return filteredUsers.value; // 기본 정렬
  }
});

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return sortedUsers.value.slice(start, end);
});

const store = useUserStore();
const rankStore = useRankStore();
const router = useRouter();
const users = ref([]);

const addRival = function (rivalId) {
  store.addRival(sessionStorage.getItem('userId'), rivalId);
};

// 정렬 버튼 클릭 핸들러
const sortByHighestPace = function () {
  sortBy.value = 'highest_pace';
  rankStore.sortByHighestPace();
  users.value = rankStore.users;
};

const sortByFrequency = () => {
  sortBy.value = 'frequency';
  rankStore.sortByFrequency();
  users.value = rankStore.users;
};

const sortByTotalDistance = () => {
  sortBy.value = 'total_distance';
  rankStore.sortByTotalDistance();
  users.value = rankStore.users;
};

// 라이벌만 보기 버튼 클릭 핸들러
const toggleShowRivalsOnly = () => {
  showRivalsOnly.value = !showRivalsOnly.value;
};

const getRecordHeader = () => {
  if (sortBy.value === 'highest_pace') return 'Pace';
  if (sortBy.value === 'frequency') return 'Frequency';
  if (sortBy.value === 'total_distance') return 'Total distance';
  return sortByHighestPace();
};

const getUserRecord = (user) => {
  if (sortBy.value === 'highest_pace') return `${Math.floor((user.highestPace) / 60)}' ${Math.floor((user.highestPace) % 60)}''`;
  if (sortBy.value === 'frequency') return `${user.frequency} 회`;
  if (sortBy.value === 'total_distance') return `${Math.floor(user.totalDistance)}km`;
  return '';
};

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
</script>

<template>
  <div class="container">
    <!-- 정렬 버튼 -->
    <div class="sort-buttons">
      <button class="btn btn-outline-secondary" @click="sortByHighestPace">Pace</button>
      <button class="btn btn-outline-secondary" @click="sortByFrequency">Frequency</button>
      <button class="btn btn-outline-secondary" @click="sortByTotalDistance">Total distance</button>
      <button class="btn btn-outline-secondary" @click="toggleShowRivalsOnly">
        {{ showRivalsOnly ? 'Show All' : 'Show Rivals Only' }}
      </button>
    </div>
    <!-- 사용자 목록 -->
    <div>
      <div class="user-item header">
        <div class="rank">랭킹</div>
        <div class="name">이름</div>
        <div class="nickname">닉네임</div>
        <div class="record">{{ getRecordHeader() }}</div>
      </div>
      <div v-for="(user, index) in paginatedUsers" :key="user.userId" >
        <div v-if="!showRivalsOnly || user.MyRival" class="user-item">
          <div class="rank">{{ (currentPage - 1) * pageSize + index + 1 }}위</div>
          <RouterLink :to="{ name: 'compareRank', params: { rivalId: user.userId } }" class="name">{{ user.userName }}
          </RouterLink>
          <div class="nickname">{{ user.userNick }}</div>
          <div class="record">{{ getUserRecord(user) }}</div>
          <button class="btn btn-outline-secondary" id="addrival" @click="addRival(user.userId)">라이벌 등록</button>
        </div>
      </div>
    </div>
    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="goToFirstPage">&laquo;</button>
      <button @click="goToPreviousPage">&lsaquo;</button>
      <button v-for="page in visiblePages" :key="page" @click="setPage(page)"
        :class="{ 'active': currentPage === page }">
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
  border: 1px solid #aaaaaa;
  border-radius: 4px;
  cursor: pointer;
}

.user-item {
  display: grid;
  grid-template-columns: 100px 100px 100px 100px auto;
  align-items: center;
  margin-bottom: 10px;
}

.header {
  font-weight: bold;
}

.rank {
  width: 50px;
}

.name {
  text-align: left;
}

.nickname {
  text-align: left;
}

.record {
  text-align: left;
}

#addrival {
  width: 120px;
  border-radius: 4px;
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
