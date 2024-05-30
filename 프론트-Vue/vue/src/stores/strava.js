import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';

export const useStravaStore = defineStore('strava', () => {

  const accessToken = ref('');
  const activities = ref([]); // activities 상태 추가

  const setAccessToken = (token) => {
    accessToken.value = token;
  };

  const fetchAccessToken = (code) => {
    const clientId = 127006;
    const clientSecret = import.meta.env.VITE_STRAVA_API_SECRET
    const url = 'https://www.strava.com/oauth/token';

    const data = {
      client_id: clientId,
      client_secret: clientSecret,
      code: code,
      grant_type: 'authorization_code',
    };

    return axios.post(url, data)
      .then(response => {
        setAccessToken(response.data.access_token);
        console.log("Access token fetched successfully:", response);
        // 액세스 토큰을 가져온 후 런닝 활동 데이터를 가져옴
        return fetchRunningActivities(response.data.access_token);
      })
      .then(() => {
        // 요약 정보를 서버로 전송
        return sendSummaryToServer();
      })
      .catch(error => {
        console.error('Error fetching access token or activities:', error.response ? error.response.data : error.message);
      });
  };

  const fetchRunningActivities = (token, perPage = 10) => {
    console.log(token);
    return axios.get('https://www.strava.com/api/v3/athlete/activities', {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        per_page: perPage
      }
    })
      .then(response => {
        // 런닝 활동만 필터링
        console.log("런닝활동", response);
        const runningActivities = response.data.filter(activity => activity.sport_type === 'Run');
        console.log(runningActivities); // 일단 런닝활동 다 받아 왔음
        activities.value = runningActivities.map(activity => ({
          date: activity.start_date, // date
          distance: activity.distance, // 거리 (m)
          runTime: activity.elapsed_time, // 시간(s)
          pace: (activity.elapsed_time / activity.distance) * 1000, // 평균 pace (s/km)
          userSeq: null, // 실제 userSeq 값으로 변경
        }));
        saveToLocalStorage();
      })
      .catch(error => {
        console.error('Error fetching activities:', error.response ? error.response.data : error.message);
      });
  };

  const saveToLocalStorage = () => {
    localStorage.setItem('activities', JSON.stringify(activities.value));
    console.log("로컬에 저장된 데이터:", localStorage.getItem('activities'));
  };

  const loadFromLocalStorage = () => {
    const data = localStorage.getItem('activities');
    if (data) {
      activities.value = JSON.parse(data);
    }
  };

  const sendSummaryToServer = () => {
    const totalDistance = activities.value.reduce((sum, lap) => sum + lap.distance, 0);
    const totalRunTime = activities.value.reduce((sum, lap) => sum + lap.runTime, 0);
    const frequency = activities.value.length;
    const highestPace = totalDistance > 0 ? (totalRunTime / totalDistance) * 1000 : 0;

   
    const userSeq = 1; // 실제 userSeq 값을 사용
    const record = {
      userSeq: userSeq,
      frequency: frequency,
      totalDistance: totalDistance,
      highestPace: highestPace,
    };
    console.log("레코드 : " ,record)

    const userId = sessionStorage.getItem('userId'); // sessionStorage에서 userId 가져오기

    return axios.post('http://localhost:8080/records', record, {
      headers: {
        'Authorization': `Bearer ${accessToken.value}`,
        'userId': userId
      }
    })
      .then(() => {
        console.log('Data sent to server successfully');
        console.log(localStorage.getItem('activities'))
      })
      .catch(error => {
        console.error('Error sending data to server:', error.response ? error.response.data : error.message);
      });
  };

  return { 
    sendSummaryToServer, 
    loadFromLocalStorage, 
    saveToLocalStorage, 
    fetchRunningActivities, 
    fetchAccessToken, 
    setAccessToken, 
    accessToken, 
    activities 
  };
},
{
  persist: true
});
