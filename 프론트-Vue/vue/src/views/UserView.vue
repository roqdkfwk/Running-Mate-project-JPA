<template>
    <div class="user-profile-container">
      <h2>사용자 프로필</h2>
      <div class="profile-picture">
        <img :src="userProfile.img" alt="프로필 사진" />
      </div>
      <div class="profile-info">
        <div class="profile-item">
          <span class="label">이름:</span>
          <span class="value">{{ userProfile.userName }}</span>
        </div>
        <div class="profile-item">
          <span class="label">아이디:</span>
          <span class="value">{{ userProfile.userId }}</span>
        </div>
        <div class="profile-item">
          <span class="label">닉네임:</span>
          <span class="value">{{ userProfile.userNick }}</span>
        </div>
        <div class="profile-item">
          <span class="label">비밀번호:</span>
          <span class="value">********</span> <!-- 비밀번호는 보이지 않도록 -->
        </div>
        <div class="profile-item">
          <span class="label">이메일:</span>
          <span class="value">{{ userProfile.email }}</span>
        </div>
        <div class="profile-item">
          <span class="label">연락처:</span>
          <span class="value">{{ userProfile.phone }}</span>
        </div>
        <div class="profile-item">
          <span class="label">주소:</span>
          <span class="value">{{ userProfile.address }}</span>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useMainStore } from '@/stores/main'
  
  const userProfile = ref({})
  // const store = useUserStore()
  
  const fetchUserProfile = () => {
    axios.get('http://localhost:8080/user/profile', {
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    })
    .then((response) => {
      userProfile.value = response.data;
    })
    .catch((error) => {
      console.error('Failed to fetch user profile:', error)
    })
  }
  
  onMounted(() => {
    fetchUserProfile()
  })
  </script>
  
  <style scoped>
  .user-profile-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  .profile-picture {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .profile-picture img {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover;
  }
  
  .profile-info {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }
  
  .profile-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .label {
    font-weight: bold;
  }
  
  .value {
    margin-left: 10px;
  }
  </style>
  