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
    axios.get(`${REST_POST_API}/${groupId}/posts`)
      .then((response) => {
        console.log(response)
        console.log(response.data)
        postList.value = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  }

  // 단일 게시글 가져오기
  const getPost = function (groupId, postId) {
    axios.get(`${REST_POST_API}/${groupId}/posts/${postId}`)
      .then((response) => {
      post.value = response.data
      })
      .catch((error) => {
      console.error(error)
    })
  }

  // 게시글 작성
  const createPost = form => { 
    axios.post(`${REST_POST_API}/${form.groupId}/posts`,
      {
        title: form.title,
        content: form.content,
        img: form.img,
        notice: form.notice
      },
      {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
        }
      })
      .then(response => {
        if (response.status === 201) {
          // 1) 올바른 id 추출
          console.log("올바른 id: " + response.data)
          const id = response.data
  
          // 2) 수정된 GET 경로
          return axios.get(`${REST_POST_API}/${form.groupId}/posts/${id}`)
        }
        throw new Error('게시글 작성 실패');
      })
      .then(response => {
        console.log("res: ", response)
        console.log("응답: ", response.data)
        router.push({
          name: 'postDetail',
          params: {
            groupId: form.groupId,
            postId: response.data.postId
          }
        })
      })
      .catch(error => {
        console.error(error)
      })
  }
  
//   const createPost = form => {
//   const url = `${REST_POST_API}/${form.groupId}/posts`;
//   const body = {
//     title:   form.title,
//     content: form.content,
//     img:     form.img,
//     notice:  form.notice
//   };
//   const config = {
//     headers: {
//       Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
//     }
//   };

//   axios.post(url, body, config)
//     .then(response => {
//       if (response.status === 201) {
//         // 1) 올바른 id 추출
//         const id = response.data;

//         // 2) 수정된 GET 경로
//         return axios.get(
//           `${REST_POST_API}/${form.groupId}/posts/${id}`,
//           config
//         );
//       }
//       throw new Error('게시글 작성 실패');
//     })
//     .then(detailResponse => {
//       // detailResponse.data 에 실제 포스트 데이터가 담겨있어야 합니다.
//       router.push({
//         name: 'postDetail',
//         params: {
//           groupId: form.groupId,
//           postId:  detailResponse.data.id
//         }
//       });
//     })
//     .catch(error => {
//       console.error(error);
//     });
// };


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

  return {
    post, postList, getPostList, createPost, updatePost, deletePost,
    detailPost, getPost
  }
},
  {
    persist: true
  })
