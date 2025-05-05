<template>
    <div class="comment-list">
      <div v-if="Array.isArray(comments) && comments.length === 0">아직 댓글이 없습니다.</div>
      <ul v-else>
        <li v-for="comment in comments" :key="comment.commentId" class="comment-item">
          <div class="comment-author">{{ comment.author }}</div>
          <div class="comment-content">{{ comment.content }}</div>
          <div class="comment-date">{{ formatDate(comment.createdAt) }}</div>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup>
  import { defineProps } from 'vue'
  
  const props = defineProps({
    comments: {
      type: Array,
      required: true,
      default: () => []
    }
  })

  console.log('received comments:', props.comments)

  function formatDate(dateString) {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  }
  </script>
  
  <style scoped>
  .comment-list {
    margin-top: 20px;
    padding: 10px;
    background-color: #1f2a38;
    border-radius: 8px;
  }
  
  .comment-item {
    border-bottom: 1px solid #ccc;
    padding: 8px 0;
  }
  
  .comment-author {
    font-weight: bold;
    margin-bottom: 4px;
    color: #a3d5ff;
  }
  
  .comment-content {
    margin-bottom: 4px;
    color: #fff;
  }
  
  .comment-date {
    font-size: 12px;
    color: #bbb;
  }
  </style>
  