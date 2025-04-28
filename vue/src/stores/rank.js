import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRoute } from 'vue-router';

const REST_RANK_API = 'http://localhost:8080/rank';

export const useRankStore = defineStore('rank', () => {
  const users   = ref([]);
  const user    = ref(null);
  const groups  = ref([]);
  const members = ref([]);
  const route   = useRoute();

  // 그룹 랭크 조회 (페이스)
  const sortGroupByHighestPace = () => {
    return axios
      .get(`${REST_RANK_API}/group`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'highest_pace' },
      })
      .then((response) => {
        groups.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 그룹 랭크 조회 (빈도)
  const sortGroupByFrequency = () => {
    return axios
      .get(`${REST_RANK_API}/group`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'frequency' },
      })
      .then((response) => {
        groups.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 그룹 랭크 조회 (총 거리)
  const sortGroupByTotalDistance = () => {
    return axios
      .get(`${REST_RANK_API}/group`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'total_distance' },
      })
      .then((response) => {
        groups.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 그룹 멤버 랭크 조회 (페이스)
  const sortMemByHighestPace = () => {
    return axios
      .get(`${REST_RANK_API}/group/${route.params.groupId}`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'highest_pace' },
      })
      .then((response) => {
        members.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 그룹 멤버 랭크 조회 (빈도)
  const sortMemByFrequency = () => {
    return axios
      .get(`${REST_RANK_API}/group/${route.params.groupId}`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'frequency' },
      })
      .then((response) => {
        members.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 그룹 멤버 랭크 조회 (총 거리)
  const sortMemByTotalDistance = () => {
    return axios
      .get(`${REST_RANK_API}/group/${route.params.groupId}`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'total_distance' },
      })
      .then((response) => {
        members.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 전체 유저 랭크 조회 (페이스)
  const sortByHighestPace = () => {
    return axios
      .get(`${REST_RANK_API}/user`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'highest_pace' },
      })
      .then((response) => {
        users.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 전체 유저 랭크 조회 (빈도)
  const sortByFrequency = () => {
    return axios
      .get(`${REST_RANK_API}/user`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'frequency' },
      })
      .then((response) => {
        users.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 전체 유저 랭크 조회 (총 거리)
  const sortByTotalDistance = () => {
    return axios
      .get(`${REST_RANK_API}/user`, {
        headers: { userId: sessionStorage.getItem('userId') },
        params: { con: 'total_distance' },
      })
      .then((response) => {
        users.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // 내 러닝 레코드 조회
  const myRR = () => {
    return axios
      .get('http://localhost:8080/myRR', {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
          userId: sessionStorage.getItem('userId'),
        },
      })
      .then((response) => {
        user.value = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return {
    users,
    user,
    groups,
    members,
    sortGroupByHighestPace,
    sortGroupByFrequency,
    sortGroupByTotalDistance,
    sortMemByHighestPace,
    sortMemByFrequency,
    sortMemByTotalDistance,
    sortByHighestPace,
    sortByFrequency,
    sortByTotalDistance,
    myRR,
  };
}, {
  persist: true,
});
