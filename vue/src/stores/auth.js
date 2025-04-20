import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useUserStore } from "./user";
import { useRouter } from 'vue-router'

const AUTH_REST_API = `http://localhost:8080/api/users`
const REST_API = `http://localhost:8080/api/email-verification`

export const useAuthStore = defineStore('auth', () => {
    const userStore = useUserStore()
    const router = useRouter()
    const isEmailVerified = ref(false)

    /**
     * 이메일 인증번호 전송
     */
    const sendVerificationCode = function (email) {
        return axios.post(`${REST_API}`, null,
            { params: { email }
            })
            .then((response) => {
                alert('인증번호가 전송되었습니다.')
            })
            .catch((error) => {
                switch (error.response.status) {
                    case 409:
                        alert(error.response.data.message)
                        break;
                    default:
                        alert('인증번호 전송 실패')
                }
            })
    }

    /**
     * 이메일 인증 번호 검증
     */
    const verifyCode = function (email, code) {
        return axios.post(`${REST_API}/verify`, {
            'email': email,
            'code': code
        })
            .then((response) => {
                isEmailVerified.value = true
                alert('이메일 인증이 완료되었습니다.')
            })
            .catch((error ) => {
                alert('인증번호가 잘못되었거나 만료되었습니다. 다시 시도해주세요.')
                isEmailVerified.value = false
            })
    }

    /**
     * 이메일 인증 번호 재전송
     */
    const resendVerificationCode = function (email) {
        return axios.post(`${REST_API}/resend`, {
            'email': email
        })
            .then((response) => {
                emailVerificationStatus.value = response.data
                alert('새 인증번호가 전송되었습니다. 이메일을 확인해주세요.')
            })
            .catch((error) => {
                alert('인증번호 재전송에 실패했습니다. 다시 시도해주세요.')
            })
    }

    /**
     * 회원탈퇴
     */
    const withdraw = function () {
        const accessToken = sessionStorage.getItem('accessToken') || ''
        console.log("accessToken:", accessToken)

        return axios.delete(`${AUTH_REST_API}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then((response) => {
                alert('회원 탈퇴가 완료되었습니다.')
                
                // 1. 탈퇴 후 로그인되어 있던 사용자 정보 삭제
                sessionStorage.removeItem('accessToken')
                sessionStorage.removeItem('userId')
                
                userStore.user = {}
                userStore.accessToken = {}

                // 2. 메인페이지로 리다이렉트
                router.push({name: 'mainView'})
            })
            .catch((error) => {
                alert('회원 탈퇴 중 오류가 발생했습니다.')
            
        })
    }

    return {
        isEmailVerified,
        sendVerificationCode,
        verifyCode,
        resendVerificationCode,
        withdraw
    }
})
