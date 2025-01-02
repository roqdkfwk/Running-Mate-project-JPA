<!-- 
    전 : 그룹 디테일, 내 그룹(on/off 버튼) >> 로그인 페이지 , 그룹 가입하기 >> 로그인 페이지
    후 : 그룹 디테일, 내 그룹(on/off 버튼), 그룹 가입하기
 -->
<!-- 
    전 : 유저 디테일, 내 라이벌(on/off 버튼) >> 로그인 페이지, 라이벌 등록 >> 로그인 페이지
    후 : 유저 디테일, 내 라이벌(on/off 버튼), 라이벌 등록
 -->
<script setup>
import { ref, onMounted } from 'vue'
import { useGroupStore } from '@/stores/group'
import GroupList from './GroupList.vue'
import { useRankStore } from '@/stores/rank';

const store = useGroupStore()
const rankStore = useRankStore();
const searchQuery = ref('')
const searchFilter = ref('id') // 기본 필터는 'id'

// onMounted에서 그룹 데이터를 불러옵니다.
onMounted(() => {
  rankStore.sortGroupByHighestPace()
})

const performSearch = function () {
  // 검색 로직을 여기에 구현
  console.log(`검색 실행 - 필터: ${searchFilter.value}, 쿼리: ${searchQuery.value}`);
}
</script>

<template>

  <div class="group-list-container">
    <h3>GroupRank</h3>
    <GroupList :groups="store.groups" :search-query="searchQuery" :search-filter="searchFilter" />
  </div>
  <div class="search-container">
    <!-- 검색 입력란 -->
    <input type="text" v-model="searchQuery" placeholder="Search groups..." />
    <!-- 돋보기 아이콘 버튼 -->
    <button @click="performSearch" class="search-button">🔍</button>
  </div>
</template>

<style scoped>
h3 {
  text-shadow: 2px 2px 5px rgba(100, 100, 100, 5);
}

.group-list-container {
  margin: 20px auto;
  padding: 20px;
  width: 550px;
  background-color:  rgb(2, 21, 30,0.8);
  border-radius: 10px;
  color : white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;

  /* border: 1px solid #ccc; */
  border-radius: 10px;

}

.search-container {
  margin: 10px auto;
  width: 550px;
  position: relative;
  display: flex;
  align-items: center;
  /* 중앙 정렬 */
}

.search-filter {
  margin-right: 5px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 5px 5px;
  height: 37px;
}

input[type='text'] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-button {
  position: absolute;
  right: 10px;
  top: 46%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
}
</style>