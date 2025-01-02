import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import MainView from '@/views/MainView.vue'

const REST_API = `http://localhost:8080`

export const useMainStore = defineStore('main', () => {
  const accessToken = ref('')
  const loginUser = ref({})
  const router = useRouter()
  const userStore = useUserStore();

  const login = function (user) {
    console.log("logindata 받음")
    axios.post(`${REST_API}/login`, {
      userId: user.userId,
      userPwd: user.userPwd
    })
      .then((response) => {

        sessionStorage.setItem('accessToken', response.data.accessToken)
        sessionStorage.setItem('userId', response.data.userId)

        userStore.accessToken = response.data.accessToken//확인해보기
        console.log(userStore.accessToken)
        const token = response.data['accessToken'].split('.')
        // console.log("token[0] : " + token[0])
        let id = JSON.parse(atob(token[1]))['id']
        // console.log("id : " + id)
        loginUser.value = { ...user, name: response.data.name }
        const redirect = router.currentRoute.value.query.redirect || '/'
        router.push(redirect)
      })
      .catch((error) => {
        console.log(error)
        alert('아이디 또는 비밀번호가 틀렸습니다.')
      })
  }

  const logout = function () {
    sessionStorage.removeItem('accessToken')
    sessionStorage.removeItem('userId')
    localStorage.clear()

  }


  const loadMainPageInfo = function () {
    if (!accessToken.value) {
      return
    }

    Promise.all([
      axios.get(`${REST_API}/myLog`, {
        headers: {
          Authorization: `Bearer ${accessToken.value}`
        }
      }),
      axios.get(`${REST_API}/user`, {
        headers: {
          Authorization: `Bearer ${accessToken.value}`
        }
      })
    ])
      .then((response) => {
        // 기록을 가지고 뱃지 계산 & 프로필 사진 출력
      })
      .catch((error) => {
        console.log(error)
      })
  }




  return { accessToken, loginUser, router, login, logout, loadMainPageInfo, }
},
  {
    persist: true
  })
