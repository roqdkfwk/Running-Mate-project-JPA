<template>
  <div class="post-detail-container">
    <h2 class="post-title">{{ post.title }}</h2>
    <div v-if="post.img" class="post-image-container">
      <img :src="post.img" alt="Post Image" class="post-image" />
    </div>
    <div class="post-meta">
      <span class="post-writer">작성자: {{ post.writerNick }}</span>
      <span class="post-date">작성일: {{ formatDate(post.createdAt) }}</span>
      <span class="post-views">조회수: {{ post.viewCnt }}</span>
    </div>
    <div class="post-content-box">
      <p>{{ post.content }}</p>
    </div>
    <div class="post-actions">
      <button @click="updatePost" class="edit-button">수정</button>
      <button @click="deletePost" class="delete-button">삭제</button>
    </div>
    <p v-if="post.notice" class="post-notice">Notice: This is an important post.</p>
    <button @click="goBack" class="back-button">목록</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { usePostStore } from '@/stores/post'

const route = useRoute();
const router = useRouter();
const post = ref({});
const store = usePostStore()

const fetchPostDetail = () => {
  const { groupId, id } = route.params;
  console.log("group : ", groupId)
  console.log("post : ", id)
  axios.get(`http://localhost:8080/group/${groupId}/post/${id}`, {
    headers: {
      // Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: sessionStorage.getItem('userId')
    }
  })
  .then(response => {
    post.value = response.data;
    console.log(post.value)
  })
  .catch(error => {
    console.error('Error fetching post details:', error);
  });
};

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

// 게시글 수정
const updatePost = function() {
  store.updatePost(post.value, route.params.groupId)
}

// 게시글 삭제
const deletePost = () => {
  const { groupId, id } = route.params;
  console.log(groupId, id)
  axios.delete(`http://localhost:8080/group/${groupId}/post/${id}`, {
    headers: {
      Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: sessionStorage.getItem('userId')
    }
  })
  .then(() => {
    router.push({ name: 'postList', params: { groupId } });
  })
  .catch(error => {
    console.error('Error deleting post:', error);
  });
};

const goBack = () => {
  router.push({ name: 'postList', params: { groupId: route.params.groupId } });
}

onMounted(fetchPostDetail);
</script>

<style scoped>
.post-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color:  rgb(2, 21, 30,0.8);
  border-radius: 10px;
  color : white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  
}

.post-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.post-image-container {
  text-align: center;
  margin-bottom: 20px;
}

.post-image {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.post-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 14px;
  color: #888;
}

.post-content-box {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.post-content-box p {
  font-size: 16px;
  line-height: 1.6;
  color: #555;
}

.post-actions {
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

.post-notice {
  padding: 10px;
  background-color: #ffefc1;
  border: 1px solid #ffd700;
  border-radius: 4px;
  font-weight: bold;
  color: #a67c00;
}
</style>