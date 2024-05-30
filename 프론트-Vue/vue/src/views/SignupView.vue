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
      </div>
      <!-- 비밀번호 확인 입력란 -->
      <div class="input-group long-input">
        <input type="password" id="confirmPassword" v-model="form.confirmPassword" placeholder="비밀번호 확인"
          :class="{ 'input-error': showPasswordMismatchWarning }" required>
      </div>
      <!-- 비밀번호 불일치 경고 문구 -->
      <div v-if="showPasswordMismatchWarning" class="warning-text">비밀번호가 일치하지 않습니다</div>
      <!-- 이메일 입력란 -->
      <div class="input-group long-input">
        <input type="email" id="email" v-model="form.email" placeholder="이메일" required>
      </div>
      <!-- 닉네임 입력란과 중복 확인 버튼 -->
      <div class="input-group with-button">
        <input type="text" id="nickname" v-model="form.nickname" placeholder="닉네임" required>
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
import { ref, computed } from 'vue'

const store = useUserStore()

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

const idChecked = ref(false) // 아이디 중복확인 여부를 저장
const nickChecked = ref(false) // 아이디 중복확인 여부를 저장

// store에 있는 checkId 함수를 실행
const checkId = function () {
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
  if (!idChecked.value) {
    alert('사용 불가능한 아이디입니다.') // 아이디 중복확인 메시지 출력
    return
  }

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

  background-color: rgba(255,255,255,0.7);
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
  justify-content: space-between;
}

.input-group.with-button input {
  flex: 1 1 65%;
  /* Adjusted this percentage to make the input slightly narrower */
  margin-right: 10px;
  /* Added margin-right to create space between input and button */
}

.check-button {
  flex: 1 1 25%;
  /* Adjusted this percentage to make the button slightly wider */
  padding: 8px 15px;
  background-color: rgba(255,255,255,0.8);
  color: darkslategray;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal;
  
}

.check-button:hover {
  background-color:  #fadb74;
}

.long-input input {
  width: 100%;
}

.name-gender {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.name-gender input[type="text"] {
  width: 70%;
}


input[type="text"],
input[type="password"],
input[type="email"],
.signup-button {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
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
  border-radius: 10px;
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
}</style>