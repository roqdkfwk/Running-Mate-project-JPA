import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const REST_POST_API = `http://localhost:8080/api/groups`

export const usePostStore = defineStore('post', () => {

  const router = useRouter()
  const route = useRoute()
  const post = ref({})
  const postList = ref([])

  // 전체 게시글 가져오기
  const getPostList = function (groupId) {
    axios.get(`${REST_POST_API}/${groupId}/post`)
      .then((response) => {
        console.log(response)
        console.log(response.data)
        postList.value = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  }

  // 게시글 작성
  const createPost = function (form) {
    axios.post(`${REST_Post_API}/${form.groupId}/post`,
      {
        headers: {
          Authorization: `${sessionStorage.getItem('accessToken')}`,
          userId: form.writerId
        }
      },
      {
        groupId: form.groupId,
        title: form.title,
        content: form.content,
        img: form.img,
        notice: form.notice
      }
    )
      .then(response => {
        if (response.status === 201) {
          const id = response.data.id;
          const postData = {
            groupId: form.groupId,
            id: id,
            title: form.title,
            content: form.content,
            img: form.img,
            notice: form.notice
          };

          console.log("userId : ", form.writerId)
          console.log("typeof : ", typeof (form.id))

          // 게시글 상세 페이지로 GET 요청
          axios.get(`${REST_Post_API}/${form.groupId}/post/${id}`, {
            headers: {
              Authorization: `${sessionStorage.getItem('accessToken')}`,
              userId: form.writerId
            }
          })
            .then(detailResponse => {
              console.log("게시글 상세 페이지")
              console.log('Post details:', detailResponse.data);

              // 게시글 상세 페이지로 이동
              router.push({
                name: 'postDetail',
                params: {
                  groupId: form.groupId,
                  id: id
                }
              });
            })
            .catch(detailError => {
              console.log("게시글 상세 페이지 실패")
              console.error('Error fetching post details:', detailError);
            });
        } else {
          console.error('Failed to create post:', response);
        }
      })
      .catch(error => {
        console.log(error);
        console.error('Error creating post:', error);
      });
  };

  // 게시글 수정
  const updatePost = function (post, groupId) {

    console.log("post : ", post.id)
    console.log("post : ", post.writerId)
    console.log("groupId : ", groupId)

    axios.put(`${REST_POST_API}/${groupId}/post/${post.id}`, post, {
      headers: {
        Authorization: `${sessionStorage.getItem('accessToken')}`,
        userId: `${sessionStorage.getItem('userId')}`
      }
    })
      .then(() => {   // 게시글 업데이트 성공
        console.log("then으로 왔음")
        // if (sessionStorage.getItem('userid') === )
        // 작성한 게시글이 보이도록 postDetail페이지로 이동
        router.push({ name: 'postUpdate', params: { groupId: post.groupId } })
      })
      .catch((error) => { // 게시글 업데이트 실패
        alert("자신이 작성하지 않은 글은 수정할 수 없습니다.")
      })
  }

  const deletePost = function (groupId, postId, userId) {
    // setUserIdHeader(userId)
    axios.delete(`${REST_POST_API}/${groupId}/post/${postId}`)
      .then(() => {
        router.push({ name: 'postList', params: { groupId } })
      })
  }

  const detailPost = function (groupId, postId) {
    // setUserIdHeader(userId)
    axios.get(`${REST_POST_API}/${groupId}/post/${postId}`)
      .then((response) => {
        post.value = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  }

  const getPost = function (postId) {
    axios.get(`${REST_POST_API}/${postId}`)
      .then((response) => {
        post.value = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  }

  return {
    post, postList, getPostList, createPost, updatePost, deletePost,
    detailPost, getPost
  }
},
  {
    persist: true
  })
