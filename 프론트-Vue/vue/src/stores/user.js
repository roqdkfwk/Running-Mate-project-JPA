import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

const REST_API = `http://localhost:8080/user`;
const REST_RANK_API = `http://localhost:8080/rank`

export const useUserStore = defineStore('user', () => {
  const router = useRouter();
  const accessToken = ref('');
  const isIdChecked = ref(false);
  const users = ref([]); // Array to store user information
  const user = ref({})

  const signup = function (newUser) {
    axios.post(`${REST_API}/signup`, newUser)
      .then((response) => {
        if (response.status === 201) {
          alert('회원 가입에 성공했습니다.') // 성공 메시지 출력
          router.push({ name: 'loginView' })
        }
      })
      .catch((error) => {
        console.log(error)
      })
  }

  const checkId = function (userId) {
    return axios.get(`${REST_API}/signup/ci/${userId}`)
  }

  const myPage = function () {
    console.log("마이페이지는 불렸다")
    return axios.get(`${REST_API}/myPage`, {
      headers: {
        Authorization: `${sessionStorage.getItem('accessToken')}`,
        userId: sessionStorage.getItem('userId')
      }
    })
    .then((response) => {
      console.log(response)
      user.value = response.data
    })
    .catch((error) =>{
      console.log("에러발생")
      console.log(error)
    }
  )
  }

  const checkNick = function (nickname) {
    return axios.get(`${REST_API}/signup/cn/${nickname}`)
  }

  const addRival = function (userId, rivalId) {
    console.log(userId, rivalId)
    console.log(sessionStorage.getItem('accessToken'))
    if (!sessionStorage.getItem('accessToken')) {
      router.push({ name: 'loginView' }) // 로그인 페이지로 이동
      return
    }
    axios.get(`${REST_API}/add/${rivalId}`, {
      headers: {
        Authorization: `${sessionStorage.getItem('accessToken')}`,
        userId: userId
      }
    })
      .then((response) => {
        if (response.status === 200) {
          console.log(response.status)
          alert('라이벌로 등록되었습니다.')
        }
      })
      .catch((error) => {
        console.log(error);
        alert('이미 라이벌로 등록된 유저 입니다.')
      })
  }


  // url 수정
  const getAllUsers = function () {
    axios.get(`${REST_RANK_API}/user`, {
      params: { con: 'highest_pace' }
    })
      .then((response) => {
        users.value = response.data
        console.log(users.value)
        console.log("유저 데이터는 왔음")
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return {
    user, router, signup, checkId, checkNick, myPage, addRival, getAllUsers, users,
    isIdChecked, accessToken,
  }
},
{
  persist: true
}
)
