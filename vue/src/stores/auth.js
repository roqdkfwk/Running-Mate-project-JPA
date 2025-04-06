import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API = `http://localhost:8080/api/email-verification`

export const useAuthStore = defineStore('auth', () => {
    // 이메일 인증 여부
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

    return {
        isEmailVerified,
        sendVerificationCode,
        verifyCode,
        resendVerificationCode
    }
})
