<template>
    <div class="create-comment">
      <form @submit.prevent="submitComment">
        <textarea v-model="form.content" placeholder="댓글을 입력하세요" required></textarea>
        <button type="submit">댓글 작성</button>
      </form>
    </div>
</template>
  
<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { useCommentStore } from '@/stores/comment'

const REST_COMMENT_API = 'http://localhost:8080/api/groups'

const route = useRoute()
const commentStore = useCommentStore()

const groupId = route.params.groupId
const postId = route.params.postId

const form = ref({ content: '' })

function submitComment() {
    axios.post(`${REST_COMMENT_API}/${groupId}/posts/${postId}/comments`,
        { content: form.value.content },
        {
            headers: {
            Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
            }
        })
        .then(response => {
        // 201 Created 인 경우만 처리
        if (response.status !== 201) {
            throw new Error('댓글 작성 실패: 상태 코드 ' + response.status)
        }
        // 작성 완료 후 입력창 비우기
        form.value.content = ''
        // 댓글 목록 새로고침
        return commentStore.getCommentList(groupId, postId)
        })
        .catch(error => {
        console.error('댓글 작성 중 에러:', error)
        })
}
</script>

<style scoped>
.create-comment {
margin-top: 20px;
}

.create-comment form {
display: flex;
flex-direction: column;
gap: 8px;
}

.create-comment textarea {
width: 100%;
min-height: 80px;
padding: 8px;
border: 1px solid #ccc;
border-radius: 4px;
resize: vertical;
}

.create-comment button {
align-self: flex-end;
padding: 6px 16px;
background-color: rgba(108, 117, 125, 0.7);
color: white;
border: none;
border-radius: 4px;
cursor: pointer;
}

.create-comment button:hover {
background-color: rgba(108, 117, 125);
}
</style>