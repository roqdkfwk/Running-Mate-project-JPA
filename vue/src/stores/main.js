import {ref} from 'vue'
import {defineStore} from 'pinia'
import axios from 'axios'
import {useRouter} from 'vue-router'
import {useUserStore} from '@/stores/user'

const REST_API = `http://localhost:8080/api/auth`

export const useMainStore
    = defineStore('main', () => {
        const accessToken = ref('')
        const loginUser = ref({})
        const router = useRouter()
        const userStore = useUserStore();

        const login = function (user) {
            axios.post(`${REST_API}/login`, {
                userId: user.userId,
                userPw: user.userPw
            })
                .then((response) => {
                    // 1. 헤더에서 토큰 추출
                    const authorization = response.headers['authorization'] || ''
                    const accessToken = authorization.startsWith('Bearer')
                        ? authorization.slice(7) : authorization
                    
                    // 2. 세션에 토큰 저장
                    sessionStorage.setItem('accessToken', accessToken)
                    sessionStorage.setItem('userId', response.data.userId)

                    // 3. 스토어에 토큰 저장
                    userStore.accessToken = accessToken
                    userStore.user = {
                        userSeq: response.data.userSeq,
                        userName: response.data.userName,
                        userNick: response.data.userNick
                    }

                    alert(JSON.stringify(response.data, null, 2))

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


        return {accessToken, loginUser, router, login, logout, loadMainPageInfo,}
    },
    {
        persist: true
    })
