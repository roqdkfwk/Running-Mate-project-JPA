<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRankStore } from '@/stores/rank';
import { useGroupStore } from '@/stores/group';
const route = useRoute();
const rankStore = useRankStore();
const groupStore = useGroupStore();
const currentPage = ref(1);
const pageSize = 10;
const sortBy = ref('');

const members = ref([]);


onMounted(() => {
  rankStore.sortMemByHighestPace();
});

const totalPages = computed(() => {
  return Math.ceil(filteredMembers.value.length / pageSize);
});

const filteredMembers = computed(() => {
  return members.value;
});

const sortedMembers = computed(() => {
  if (sortBy.value === 'pace') {
    return [...filteredMembers.value].sort((a, b) => a.pace - b.pace);
  } else if (sortBy.value === 'frequency') {
    return [...filteredMembers.value].sort((a, b) => b.frequency - a.frequency);
  } else if (sortBy.value === 'totalDistance') {
    return [...filteredMembers.value].sort((a, b) => b.totalDistance - a.totalDistance);
  } else {
    return filteredMembers.value;
  }
});

const paginatedMembers = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return sortedMembers.value.slice(start, end);
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



const getRecordHeader = () => {
  if (sortBy.value === 'pace') return 'Pace';
  if (sortBy.value === 'frequency') return 'Frequency';
  if (sortBy.value === 'totalDistance') return 'Total distance';
  return sortByPace();
};

const getMemberRecord = (member) => {
  if (sortBy.value === 'pace') return `${Math.floor(member.highestPace / 60)}' ${Math.floor(member.highestPace % 60)}''`;
  if (sortBy.value === 'frequency') return `${member.frequency} 회`;
  if (sortBy.value === 'totalDistance') return `${Math.floor(member.totalDistance)}km`;
  return '';
};

// 정렬 버튼 클릭 핸들러
const sortByPace = function() {
  sortBy.value='pace'
  rankStore.sortMemByHighestPace()
  members.value = rankStore.members
};

const sortByFrequency = () => {
  sortBy.value='frequency'
  rankStore.sortMemByFrequency()
  members.value = rankStore.members

};

const sortByDistance = () => {
  sortBy.value='totalDistance'
  rankStore.sortMemByTotalDistance()
  members.value = rankStore.members
};


const joinGroup = (groupId) => {
  groupStore.joinGroup(groupId);
};


</script>

<template>
  <div class="container">
    <div class="group-actions">
      <RouterLink :to="{ name: 'groupRank'}" class="action-button">Group home</RouterLink>
      <RouterLink :to="{ name: 'boardList', params: { groupId: route.params.groupId } }" class="action-button">게시판</RouterLink>
      <button @click="joinGroup(route.params.groupId)" class="action-button join-group-button">그룹 {{ route.params.groupId}} 가입하기</button>
    </div>
    <div>
      <div class="sort-buttons">
        <button @click="sortByPace">Pace</button>
        <button @click="sortByFrequency">Frequency</button>
        <button @click="sortByDistance">Total distance</button>
      </div>
      <div class="user-item header">
        <div class="rank">랭킹</div>
        <div class="name">이름</div>
        <div class="nickname">닉네임</div>
        <div class="record">{{ getRecordHeader() }}</div>
      </div>
      <div v-for="(member, index) in paginatedMembers" :key="member.userId" class="user-item">
        <div class="rank">{{ (currentPage - 1) * pageSize + index + 1 }}위</div>
        <RouterLink :to="{ name: 'compareRank', params: { rivalId: member.userId } }" class="name">{{ member.userName }}</RouterLink>
        <div class="nickname">{{ member.userNick }}</div>
        <div class="record">{{ getMemberRecord(member) }}</div>
        <button @click="addRival(member.userId)" class="btn btn-outline-secondary">라이벌 등록</button>
      </div>
    </div>
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

.group-actions {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  gap: 20px;
}

.action-button {
  padding: 10px 20px;
  border: none;
  background-color: rgba(108, 117, 125, 0.5);
  color: white;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
}

.action-button:hover {
  background-color: #565e64;
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
  color : white;
  cursor: pointer;
}

.sort-buttons button:hover {
  background-color: #565e64;
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
