import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { useRoute } from 'vue-router'

const REST_RANK_API = `http://localhost:8080/rank`

export const useRankStore = defineStore('rank', () => {

    const users = ref([])
    const user = ref([])
    const groups= ref([])
    const members= ref([])
    const route = useRoute()
    

    const sortGroupByHighestPace = function () {

        axios.get(`${REST_RANK_API}/group`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'highest_pace' }
        })
            .then((response) => {
                console.log(response.data)
                groups.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortGroupByFrequency = function () {

        axios.get(`${REST_RANK_API}/group`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'frequency' }
        })
            .then((response) => {
                groups.value = response.data
                console.log(response.data)
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortGroupByTotalDistance = function () {

        axios.get(`${REST_RANK_API}/group`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'total_distance' }
        })
            .then((response) => {
                groups.value = response.data
                console.log(response.data)
            })
            .catch((error) => {
                console.log(error);
            });
    }




    const sortMemByHighestPace = function () {

        axios.get(`${REST_RANK_API}/group/${route.params.groupId}`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'highest_pace' }
        })
            .then((response) => {
                members.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortMemByFrequency = function () {

        axios.get(`${REST_RANK_API}/group/${route.params.groupId}`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'frequency' }
        })
            .then((response) => {
                members.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortMemByTotalDistance = function () {

        axios.get(`${REST_RANK_API}/group/${route.params.groupId}`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'total_distance' }
        })
            .then((response) => {
                members.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }





    const sortByHighestPace = function () {

        axios.get(`${REST_RANK_API}/user`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'highest_pace' }
        })
            .then((response) => {
                users.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortByFrequency = function () {

        axios.get(`${REST_RANK_API}/user`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'frequency' }
        })
            .then((response) => {
                users.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const sortByTotalDistance = function () {

        axios.get(`${REST_RANK_API}/user`, {
            headers : {
                userId : sessionStorage.getItem('userId')
            },
            params: { con: 'total_distance' }
        })
            .then((response) => {
                users.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const myRR = function () {
        axios.get('http://localhost:8080/myRR', {
           headers : {
            Authorization : `${sessionStorage.getItem('accessToken')}`,
            userId : sessionStorage.getItem('userId')
           }
        })
            .then((response) => {
                user.value = response.data
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return { members, groups,sortGroupByFrequency, sortGroupByHighestPace, sortGroupByTotalDistance, myRR, sortByHighestPace, users, sortByFrequency, sortByTotalDistance, 
        sortMemByFrequency, sortMemByHighestPace, sortMemByTotalDistance }
},
    {
        persist: true
    })
