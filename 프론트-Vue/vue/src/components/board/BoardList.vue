<template>
  <div class="container">
    <h4>게시글 목록</h4>
    <hr />
    <div class="table-container">
      <table>
        <tr>
          <th class="column-number">번호</th>
          <th class="column-title">제목</th>
          <th class="column-writer">작성자</th>
          <th class="column-view">조회수</th>
          <th class="column-date">등록일</th>
        </tr>
        <tr v-for="(board, index) in paginatedBoards" :key="board.id">
          
          <td class="column-number">{{ index + 1 + (currentPage - 1) * pageSize }}</td>
          <td class="column-title">
            <RouterLink :to="`/group/${groupId}/board/${board.id}`">{{ board.title }}</RouterLink>
          </td>
          <td class="column-writer">{{ board.writerNick }}</td>
          <td class="column-view">{{ board.viewCnt }}</td>
          <td class="column-date">{{ formatDate(board.createdAt) }}</td>
          <!-- <p>{{ board }}</p> -->
        </tr>
      </table>
    </div>

    <!-- 검색창 -->
    <div class="search-container">
      <select v-model="searchFilter" class="search-filter">
        <option value="title">제목</option>
        <option value="writerNick">작성자 닉네임</option>
      </select>
      <div class="search-input-container">
        <input type="text" v-model="searchQuery" placeholder="Search..." class="search-input" />
        <button @click="performSearch" class="search-button">🔍</button>
      </div>
      <button @click="createBoard" class="create-board-button">게시글 작성</button>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="goToFirstPage">&laquo;</button>
      <button @click="goToPreviousPage">&lsaquo;</button>
      <button v-for="page in visiblePages" :key="page" @click="setPage(page)" :class="{ active: currentPage === page }">
        {{ page }}
      </button>
      <button @click="goToNextPage">&rsaquo;</button>
      <button @click="goToLastPage">&raquo;</button>
    </div>
    <RouterLink :to="{name: 'groupDetail', params: {groupId: route.params.groupId }}" class="action-button">Group {{ route.params.groupId }} 으로 돌아가기</RouterLink>
  </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const groupId = route.params.groupId
const store = useBoardStore()

const createBoard = function() {
  router.push({ name: 'boardCreate', params: { groupId } })
}

const currentPage = ref(1)
const pageSize = 10
const searchQuery = ref('')
const searchFilter = ref('title')

// 전체 게시글 목록 가져오기
onMounted(() => {
  store.getBoardList(groupId)
})

// 게시글 목록을 가져오기 위해 store의 boardList를 사용
const boards = computed(() => store.boardList)

const filteredBoards = computed(() => {
  if (!searchQuery.value) {
    return boards.value
  }
  return boards.value.filter(board => {
    return board[searchFilter.value].toLowerCase().includes(searchQuery.value.toLowerCase())
  })
})

const totalPages = computed(() => {
  return Math.ceil(filteredBoards.value.length / pageSize)
})

const paginatedBoards = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  const end = start + pageSize
  return filteredBoards.value.slice(start, end)
})

const visiblePages = computed(() => {
  let pages = []
  let startPage = Math.max(1, currentPage.value - 2)
  let endPage = Math.min(totalPages.value, currentPage.value + 2)

  if (startPage === 1) {
    endPage = Math.min(5, totalPages.value)
  } else if (endPage === totalPages.value) {
    startPage = Math.max(1, totalPages.value - 4)
  }

  if (totalPages.value < 5) {
    startPage = 1
    endPage = totalPages.value
  }

  for (let i = startPage; i <= endPage; i++) {
    pages.push(i)
  }

  return pages
})

const setPage = page => {
  currentPage.value = page
}

const goToFirstPage = () => {
  currentPage.value = 1
}

const goToLastPage = () => {
  currentPage.value = totalPages.value
}

const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const performSearch = () => {
  currentPage.value = 1 // 검색 시 페이지를 첫 페이지로 이동
}

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
  return new Date(dateString).toLocaleDateString(undefined, options);
}
</script>

<style scoped>
.container {
  max-width: 800px; /* 박스의 최대 너비 */
  margin: 0 auto;
  padding: 20px;

  background-color:  rgb(2, 21, 30,0.8);
  border-radius: 10px;
  color : white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.action-button {
  padding: 10px 20px;
  border: none;

  background-color: rgba(108, 117, 125, 0.7);
  color: white;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
}

.action-button:hover {
  background-color: rgba(108, 117, 125);
}

.table-container {
  max-height: 540px; /* 표의 최대 높이 설정 */
  overflow-y: auto; /* 내용이 넘칠 경우 수직 스크롤 표시 */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
  word-wrap: break-word; /* 단어가 넘칠 경우 줄 바꿈 */
}

th {
  background-color: #f4f4f4;
}

.column-number {
  width: 10%; /* 번호 칸의 너비를 10%로 설정 */
}

.column-title {
  width: 35%; /* 제목 칸의 너비를 35%로 설정 */
}

.column-writer {
  width: 15%;
}

.column-view {
  width: 10%;
}

.column-date {
  width: 30%;
}

.search-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-filter {
  width: 20%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-input-container {
  position: relative;
  width: 60%;
}

.search-input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-button {
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.create-board-button {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  background-color: rgba(108, 117, 125, 0.7);
  color: white;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
  display: inline-block;
}

.create-board-button:hover {
  background-color:rgba(108, 117, 125);
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-bottom: 20px;
}

.pagination button {
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