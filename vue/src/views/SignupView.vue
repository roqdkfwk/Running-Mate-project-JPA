<template>
  <div class="signup-container gowun-dodum-regular">
    <h1>회 원 가 입</h1>
    <form @submit.prevent="submitForm" class="signup-form">
      <!-- 아이디 입력란과 중복 확인 버튼 -->
      <div class="input-group with-button">
        <input type="text" id="username" v-model="form.userId" placeholder="아이디" required>
        <button @click.prevent="checkId" class="check-button">중복확인</button>
      </div>
      <!-- 비밀번호 입력란 -->
      <div class="input-group long-input">
        <input type="password" id="password" v-model="form.password" placeholder="비밀번호" required>
        <div v-if="passwordWarning" class="warning-text">비밀번호는 8~16자리로 입력해야 합니다.</div>
      </div>
      <!-- 비밀번호 확인 입력란 -->
      <div class="input-group long-input">
        <input type="password" id="confirmPassword" v-model="form.confirmPassword" placeholder="비밀번호 확인"
          :class="{ 'input-error': showPasswordMismatchWarning }" required>
      </div>
      <!-- 비밀번호 불일치 경고 문구 -->
      <div v-if="showPasswordMismatchWarning" class="warning-text">비밀번호가 일치하지 않습니다</div>
      <!-- 이메일 입력란 -->
      <div class="input-group with-button">
        <input type="email" id="email" v-model="form.email" placeholder="이메일" required>
        <button @click.prevent="sendVerificationCode" class="check-button">인증번호 발송</button>
      </div>
      <!-- 이메일 인증 번호 입력란 -->
      <div class="input-group with-button">
        <input type="text" id="emailCode" v-model="form.verificationCode" placeholder="인증번호 입력" required>
        <button @click.prevent="verifyCode" class="check-button">인증번호 확인</button>
      </div>
      <!-- 닉네임 입력란과 중복 확인 버튼 -->
      <div class="input-group with-button">
        <input type="text" id="nickname" v-model="form.nickname" placeholder="닉네임" required
               @input="form.nickname = form.nickname.replace(/[^a-zA-Z0-9가-힣]/g, '')">
        <button @click.prevent="checkNick" class="check-button">중복확인</button>
      </div>
      <!-- 이름과 성별 선택 -->
      <div class="input-group name-gender">
        <input type="text" id="name" v-model="form.name" placeholder="이름" required>
      </div>
      <!-- 휴대전화 번호 입력란 -->
      <div class="input-group long-input">
        <input type="text" id="phone" v-model="form.phone" placeholder="휴대전화번호" required>
      </div>
  
      <button type="submit" class="signup-button">회원가입</button>
    </form> 

  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useAuthStore } from '@/stores/auth'
import { ref, computed } from 'vue'

const store = useUserStore()
const authStore = useAuthStore()

const form = ref({
  userId: '',
  nickname: '',
  password: '',
  confirmPassword: '',
  email: '',
  name: '',
  gender: '',
  phone: '',
  postalCode: '',
  address: '',
  detailedAddress: ''
})

/**
 * 인증번호 전송
 */
const sendVerificationCode = function () {
  if (!form.value.email) {
    alert('이메일을 입력해주세요')
    return
  }
  authStore.sendVerificationCode(form.value.email)
}

/**
 * 인증번호 확인
 */
const verifyCode = function () {
  if (!form.value.verificationCode) {
    alert('인증번호를 입력해주세요.')
    return
  }
  authStore.verifyCode(form.value.email, form.value.verificationCode)
}

const idChecked = ref(false) // 아이디 중복확인 여부를 저장
const nickChecked = ref(false) // 아이디 중복확인 여부를 저장

// store에 있는 checkId 함수를 실행
const checkId = function () {
  // 아이디 입력값 검증: 알파벳과 숫자만 허용
  if (!/^[a-zA-Z0-9]+$/.test(form.value.userId)) {
    alert("아이디는 알파벳과 숫자만 입력 가능합니다.");
    idChecked.value = false; // 중복 확인 상태 초기화
    return; // 서버 요청 중단
  }

  // 유효한 경우에만 서버로 요청 전송
  store.checkId(form.value.userId)
    .then((response) => {
      alert("사용 가능한 아이디입니다.")
      idChecked.value = true
    })
    .catch((error) => {
      alert("이미 사용 중인 아이디입니다.")
      idChecked.value = false
    })
}

const passwordWarning = computed(() => {
  const length = form.value.password.length;
  return length > 0 && (length < 8 || length > 16);
})

const checkNick = function () {
  store.checkNick(form.value.nickname)
    .then((response) => {
      alert("사용 가능한 닉네임입니다.")
      nickChecked.value = true
    })
    .catch((error) => {
      alert("이미 사용 중인 닉네임입니다.")
      nickChecked.value = false
    })
}

const submitForm = function () {
  // 아이디 중복확인 메시지 출력
  if (!idChecked.value) {
    alert('사용 불가능한 아이디입니다.')
    return
  }

  // 닉네임 중복확인 메시지 출력
  if (!nickChecked.value) {
    alert('사용 불가능한 닉네임입니다.')
    return
  }

  if (isPasswordMatch.value) {
    console.log('Form submitted:', form.value)

    const newUser = {
      userId: form.value.userId,
      userPwd: form.value.password,
      userName: form.value.name,
      userNick: form.value.nickname,
      email: form.value.email,
      createdAt: new Date(),
      updatedAt: new Date(),
      address: `${form.value.address} ${form.value.detailedAddress}`,
      img: '', // 사용자가 업로드한 이미지가 있을 경우 여기에 설정
      phone: form.value.phone,
      exposure: true // 기본값 설정, 필요에 따라 수정
    }
    store.signup(newUser) // 회원가입 요청
  } else {
    console.log('Passwords do not match')
  }
}

const mockSearchPostalCode = function (postalCode) {
  // 주소 검색 API 활용
  return `Sample Address for postal code ${postalCode}`
}

const postalCodeSearchQuery = ref('')
const isPostalCodeSearchOpen = ref(false)

const setGender = function (gender) {
  form.gender = gender
}

const isPasswordMatch = computed(() => form.value.password === form.value.confirmPassword)
const isConfirmPasswordFilled = computed(() => form.value.confirmPassword !== '')
const showPasswordMismatchWarning = computed(() => isConfirmPasswordFilled.value && !isPasswordMatch.value)

const openPostalCodeSearch = function () {
  isPostalCodeSearchOpen.value = true
}

const closePostalCodeSearch = function () {
  isPostalCodeSearchOpen.value = false
}

const searchPostalCode = function () {
  const postalCode = postalCodeSearchQuery.value
  if (postalCode) {
    form.value.postalCode = postalCode;
    form.value.address = mockSearchPostalCode(postalCode)
    closePostalCodeSearch()
  } else {
    alert('우편번호를 입력해주세요.')
  }
}

</script>

<style scoped>
.signup-container {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.7);
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal;
}

.input-group.with-button {
  justify-content: flex-start; /* 입력란과 버튼을 왼쪽 정렬 */
}

.input-group.with-button input {
  flex: 1 1 65%; /* 입력란의 너비 비율 */
  border-radius: 10px; /* 입력란 둥글게 */
  margin-right: 10px; /* 버튼과 간격 유지 */
  border: 1px solid #ccc;
  padding: 8px;
  box-sizing: border-box;
}

.input-group.with-button .check-button {
  flex: 1 1 25%; /* 버튼의 너비 비율 */
  border-radius: 10px; /* 버튼도 둥글게 */
  padding: 8px 15px;
  background-color: rgba(255, 255, 255, 0.8);
  color: darkslategray;
  border: 1px solid #ccc;
  cursor: pointer;
}

.input-group.with-button .check-button:hover {
  background-color: #fadb74; /* 버튼 호버 시 색상 */
}

.long-input input {
  width: 100%;
  border-radius: 10px; /* 일반 입력란 둥글게 */
  border: 1px solid #ccc;
  padding: 8px;
}

.name-gender {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.name-gender input[type="text"] {
  width: 70%;
  border-radius: 10px; /* 이름 입력란 둥글게 */
}

input[type="text"],
input[type="password"],
input[type="email"],
.signup-button {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 10px; /* 기본 입력란도 둥글게 */
  font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal;
}

.input-error {
  border-color: red;
}

.signup-button {
  width: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  color: darkslategray;
  cursor: pointer;
  border-radius: 10px; /* 버튼도 둥글게 */
}

.signup-button:hover {
  background-color: #fadb74;
}

.warning-text {
  color: red;
  text-align: left;
  font-size: 12px;
  margin-left: 2px;
  margin-bottom: 20px;
}

input::placeholder {
  color: #aaa;
}

/* 모달 스타일 */
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 500px;
  border-radius: 10px; /* 모달도 둥글게 */
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.tip {
  margin-top: 20px;
}

.tip p {
  font-weight: bold;
}

.tip ul {
  list-style: none;
  padding-left: 0;
}

.tip li {
  margin-bottom: 10px;
}

.tip span {
  color: #555;
  font-style: italic;
}
</style>