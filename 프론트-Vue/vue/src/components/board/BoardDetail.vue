<template>
  <div class="board-detail-container">
    <h2 class="board-title">{{ board.title }}</h2>
    <div v-if="board.img" class="board-image-container">
      <img :src="board.img" alt="Board Image" class="board-image" />
    </div>
    <div class="board-meta">
      <span class="board-writer">작성자: {{ board.writerNick }}</span>
      <span class="board-date">작성일: {{ formatDate(board.createdAt) }}</span>
      <span class="board-views">조회수: {{ board.viewCnt }}</span>
    </div>
    <div class="board-content-box">
      <p>{{ board.content }}</p>
    </div>
    <div class="board-actions">
      <button @click="updateBoard" class="edit-button">수정</button>
      <button @click="deleteBoard" class="delete-button">삭제</button>
    </div>
    <p v-if="board.notice" class="board-notice">Notice: This is an important board.</p>
    <button @click="goBack" class="back-button">목록</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useBoardStore } from '@/stores/board'

const route = useRoute();
const router = useRouter();
const board = ref({});
const store = useBoardStore()

const fetchBoardDetail = () => {
  const { groupId, id } = route.params;
  console.log("group : ", groupId)
  console.log("board : ", id)
  axios.get(`http://localhost:8080/group/${groupId}/board/${id}`, {
    headers: {
      // Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: sessionStorage.getItem('userId')
    }
  })
  .then(response => {
    board.value = response.data;
    console.log(board.value)
  })
  .catch(error => {
    console.error('Error fetching board details:', error);
  });
};

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

const updateBoard = function() {
  store.updateBoard(board.value, route.params.groupId)
}

const deleteBoard = () => {
  const { groupId, id } = route.params;
  console.log(groupId, id)
  axios.delete(`http://localhost:8080/group/${groupId}/board/${id}`, {
    headers: {
      Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: sessionStorage.getItem('userId')
    }
  })
  .then(() => {
    router.push({ name: 'boardList', params: { groupId } });
  })
  .catch(error => {
    console.error('Error deleting board:', error);
  });
};

const goBack = () => {
  router.push({ name: 'boardList', params: { groupId: route.params.groupId } });
}

onMounted(fetchBoardDetail);
</script>

<style scoped>
.board-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color:  rgb(2, 21, 30,0.8);
  border-radius: 10px;
  color : white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  
}

.board-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.board-image-container {
  text-align: center;
  margin-bottom: 20px;
}

.board-image {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.board-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 14px;
  color: #888;
}

.board-content-box {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.board-content-box p {
  font-size: 16px;
  line-height: 1.6;
  color: #555;
}

.board-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-bottom: 20px;
}

.edit-button,
.delete-button,
.back-button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.edit-button {
  background-color:rgba(108, 117, 125, 0.7);
  color: white;
}

.delete-button {
  background-color: #f44336;
  color: white;
}

.back-button {
  background-color: #ddd;
  color: #333;
}

.edit-button:hover {
  background-color:rgba(108, 117, 125, 0.7);
}

.delete-button:hover {
  background-color: #e53935;
}

.back-button:hover {
  background-color: #ccc;
}

.board-notice {
  padding: 10px;
  background-color: #ffefc1;
  border: 1px solid #ffd700;
  border-radius: 4px;
  font-weight: bold;
  color: #a67c00;
}
</style>