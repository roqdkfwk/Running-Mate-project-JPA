import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

const REST_USER_API = `http://localhost:8080/api/users`;
const REST_RANK_API = `http://localhost:8080/api/rank`


export const useUserStore
    = defineStore('user', () => {
        const router = useRouter();
        const accessToken = ref('');
        const isIdChecked = ref(false);
        const users = ref([]);
        const user = ref({})

        /**
         * 회원가입
         */
        const signup = function (newUser) {
            axios.post(`${REST_USER_API}/signup`, newUser)
                .then((response) => {
                    if (response.status === 201) {
                        alert('회원 가입에 성공했습니다.')
                        router.push({name: 'loginView'})
                    }
                })
                .catch((error) => {
                })
        }

        /**
         * 아이디 중복 확인
         */
        const checkId = function (userId) {
            return axios.get(`${REST_USER_API}/check-id`,
                {
                    params: {userId: userId}
                })
                .then((response) => {
                    alert("사용 가능한 아이디입니다.")
                    return true
                })
                .catch((error) => {
                    alert("이미 사용 중인 아이디입니다.")
                    return false
                })
        }

        /**
         * 닉네임 중복 확인
         */
        const checkNick = function (nickname) {
            return axios.get(`${REST_USER_API}/check-nickname`,
                {
                    params: {nickname: nickname}
                })
                .then((response) => {
                    alert("사용 가능한 닉네임입니다.")
                    return true
                })
                .catch((error) => {
                    alert("이미 사용 중인 닉네임입니다.")
                    return false
                })
        }

        /**\
         * 마이페이지
         */
        const myPage = function () {
            return axios.get(`${REST_USER_API}/myPage`, {
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
                }
            })
                .then((response) => {
                    user.value = response.data
                })
                .catch((error) => {
                })
        }

        /**
         * 회원정보수정
         */
        const update = function (updateUserReq) {
            return axios.patch(`${REST_USER_API}`, updateUserReq, {
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
                }
            })
                .then((response) => {
                    // 1. 헤더에서 새로 발급된 토큰 추출
                    const authorization = response.headers['authorization'] || ''
                    const newToken = authorization.startsWith('Bearer')
                        ? authorization.slice(7) : authorization
                    
                    // 2. 세션에 토큰 저장
                    sessionStorage.setItem('accessToken', newToken)

                    // 3. 스토어에 토큰 저장
                    accessToken.value = newToken
                    user.value = {
                        userName: response.data.userName,
                        userNick: response.data.userNick,
                        email: response.data.email
                    }
                })
                .catch((error) => {
                    console.error(error)                
            })
        }
            
        const addRival = function (userId, rivalId) {
            if (!sessionStorage.getItem('accessToken')) {
                router.push({name: 'loginView'}) // 로그인 페이지로 이동
                return
            }
            axios.get(`${REST_USER_API}/add/${rivalId}`, {
                headers: {
                    Authorization: `${sessionStorage.getItem('accessToken')}`,
                    userId: userId
                }
            })
                .then((response) => {
                    if (response.status === 200) {
                        alert('라이벌로 등록되었습니다.')
                    }
                })
                .catch((error) => {
                    alert('이미 라이벌로 등록된 유저 입니다.')
                })
        }

        // url 수정
        const getAllUsers = function () {
            axios.get(`${REST_RANK_API}/user`, {
                params: {con: 'highest_pace'}
            })
                .then((response) => {
                    users.value = response.data
                })
                .catch((error) => {
                })
        }

        return {
            user, router, signup, checkId, checkNick, update, myPage, addRival, getAllUsers, users,
            isIdChecked, accessToken,
        }
    },
    {
        persist: true
    })
