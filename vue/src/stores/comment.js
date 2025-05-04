import { ref } from 'vue';
import { defineStore } from 'pinia';

const REST_COMMENT_API = `http://localhost:8080/api/groups`

export const useCommentStore = defineStore('comment', () => {

    // 댓글들을 게시글별로 저장해놓으면 캐싱 기능을 사용할 수 있을 것 같다.
    const commentByPosts = ref({})

    const getCommentList = function (groupId, postId) {
        return axios.get(`${REST_COMMENT_API}/${groupId}/posts/${postId}/comments`
            .then(response => {
                return response
            })
            .catch(error => {
                console.error(error)
            })
        )
    }

    return {
        commentByPosts,
        getCommentList,
    }
})