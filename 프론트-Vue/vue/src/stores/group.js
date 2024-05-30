import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useMainStore } from './main'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useRoute } from 'vue-router'

const REST_GROUP_API = `http://localhost:8080/group`

export const useGroupStore = defineStore('group', () => {

    const group = ref({})
    const members=ref([])
    const groups = ref([])
    const mainStore = useMainStore()
    const router = useRouter()

    const getGroups = function () {
        axios.get(REST_GROUP_API)
            .then(response => {
                groups.value = response.data
            })
            .catch(error => {
                console.error('Error fetching groups:', error)
            })
    }

    const getAllGroupMember = function () {
        axios.get(`http://localhost:8080/rank/group/${route.params.groupId}` 
        )
            .then(response => {
                members.value = response.data
            })
            .catch(error => {
                console.error('Error fetching all groups:', error)
            })
    }

    const createGroup = function (group) {
        
        console.log("group : ", group)

        axios.post(`${REST_GROUP_API}`, group, {
            headers: {
                Authorization: `${sessionStorage.getItem('accessToken')}`,
                userId: mainStore.loginUser.userId
            },
        })
            .then(response => {
                if (response.status === 201) {
                    alert('그룹 생성에 성공했습니다.')
                    groups.value.push(response.data)
                } else {
                    alert('그룹 생성에 실패했습니다.')
                }
            })
            .catch(error => {
                console.log("token : ", `${sessionStorage.getItem('accessToken')}`)
                console.log("main : ", mainStore.loginUser.userId)
                console.log('그룹 생성 중 오류 발생:', error)
                alert('그룹 생성 중 오류가 발생했습니다.')
            })
    }

    const deleteGroup = function () {
        // 그룹 삭제 로직
    }

    const updateGroup = function () {
        // 그룹 업데이트 로직
    }

    const joinGroup = function (groupId) {
        if (!sessionStorage.getItem('accessToken')) {
            router.push({ name: 'loginView' })
            // router.push({ name: 'loginView', query: { redirect: `/group/join/${groupId}` } });
        } else {
            joinGroupRequest(groupId);
        }
    }

    const joinGroupRequest = function (groupId) {
        axios.get(`/group/join/${groupId}`, {
            headers: { Authorization: `Bearer ${sessionStorage.getItem('accessToken')}` }
        }).then((response) => {
            alert(`그룹 ${groupId}에 가입되었습니다.`)
            console.log(response.data);
        }).catch((error) => {
            console.error(error);
        });
    }

    return {
        group, groups, getGroups, getAllGroupMember, createGroup, deleteGroup, updateGroup,
        joinGroup, joinGroupRequest, members
    }
},
    {
        persist: true
    })
