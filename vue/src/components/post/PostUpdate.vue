<template>
  <div class="container">
    <h2>게시글 수정</h2>
    <form @submit.prevent="submitUpdate">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" v-model="post.title" required />
      </div>
      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" v-model="post.content" rows="10" required></textarea>
      </div>
      <button type="submit" class="btn">수정</button>
      <button type="button" class="btn" @click="cancelUpdate">취소</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const groupId = route.params.groupId;
const postId = route.params.id;
const post = ref({
  title: '',
  content: '',
  writerId: '',
  groupId: groupId,
  id: postId
});

const fetchPostDetail = () => {
  const { groupId, id } = route.params;
  axios.get(`http://localhost:8080/group/${groupId}/post/${id}`, {
    headers: {
      Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: `${sessionStorage.getItem('userId')}`
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

const submitUpdate = () => {
  axios.put(`http://localhost:8080/group/${groupId}/post/${postId}`, post.value, {
    headers: {
      Authorization: `${sessionStorage.getItem('accessToken')}`,
      userId: `${sessionStorage.getItem('userId')}`
    }
  })
    .then(() => {
      router.push({ name: 'postDetail', params: { groupId: groupId, id: postId } });
    })
    .catch(error => {
      alert("자신이 작성하지 않은 글은 수정할 수 없습니다.");
    });
};

const cancelUpdate = () => {
  router.push({ name: 'postDetail', params: { groupId: groupId, id: postId } });
};

onMounted(fetchPostDetail);
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(2, 21, 30, 0.8);
  border-radius: 10px;
  color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: rgba(108, 117, 125, 0.7);
  color: white;
  cursor: pointer;
  margin-right: 10px;
}

.btn:hover {
  background-color: rgba(108, 117, 125);
}
</style>