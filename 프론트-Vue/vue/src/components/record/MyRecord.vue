<template>
  <div class="my-record">
    <!-- 나의 페이스 -->
    <div class="section">
      <h3>Pace</h3>
      <canvas id="pace-graph"></canvas>
    </div>

    <!-- 빈도 -->
    <div class="section">
      <h3>Running day</h3>
      <div id="frequency-calendar"></div>
    </div>

    <!-- 누적거리 -->
    <div class="section">
      <h3>Distance</h3>
      <canvas id="distance-graph"></canvas>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { Calendar } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import Chart from 'chart.js/auto';

onMounted(() => {
  if(!localStorage.getItem('activities')) alert("strava에서 data를 불러오세요")
  const rawData = localStorage.getItem('activities');

  

  const activities = rawData ? JSON.parse(rawData) : [];

  // 페이스 그래프 데이터
  const paceData = activities.map(activity => Math.floor(activity.pace));

  const Labels = activities.map(activity => {
    const date = new Date(activity.date);
    return date.toLocaleDateString('ko-KR', { year: '2-digit', month: '2-digit', day: '2-digit' });
  });

  // 누적거리 계산
  let cumulativeDistance = 0;
  const distanceData = activities.map(activity => {
    cumulativeDistance += activity.distance / 1000; // 미터를 킬로미터로 변환
    return cumulativeDistance.toFixed(1); // 소숫점 첫째자리까지 표시
  });

  // 페이스 그래프
  const paceCtx = document.getElementById('pace-graph').getContext('2d');
  new Chart(paceCtx, {
    type: 'line',
    data: {
      labels: Labels,
      datasets: [{
        label: 'Pace',
        data: paceData,
        fill: false,
        borderColor: 'rgb(75, 192, 192)',

        tension: 0.1
      }]
    },
    options: {
      scales: {
        y: {
          ticks: {
            callback: function (value) {
              const minutes = Math.floor(value / 60);
              const seconds = value % 60;
              return `${minutes}' ${seconds}''`;
            }
          }
        },
        x: {
          ticks: {
          }
        }
      }
    }
  });

  // 누적거리 그래프
  const distanceCtx = document.getElementById('distance-graph').getContext('2d');
  new Chart(distanceCtx, {
    type: 'line',
    data: {
      labels: Labels,
      datasets: [{
        label: 'Distance',
        data: distanceData,
        backgroundColor: 'rgba(255, 255, 255, 0.8)',
        borderColor: 'rgb(75, 192, 192)',
      }]
    },
    options: {
      scales: {
        y: {
          ticks: {
            callback: function (value) {
              return `${value} km`; // km 단위 표시
            }
          }
        },
        x: {
          ticks: {
          }
        }
      }
    }
  });

  // const calendarEl = document.getElementById('frequency-calendar');
  // const events = activities.map(activity => ({
  //   title: 'run',
  //   date: new Date(activity.date).toISOString().split('T')[0],
  //   backgroundColor: '#ff9f89', 
  //   borderColor: '#ff9f89',
  //   textColor: '#ffffff', 

  // }));

  // const calendar = new Calendar(calendarEl, {
  //   plugins: [dayGridPlugin],
  //   initialView: 'dayGridMonth',
  //   events: events
  // });
  // calendar.render();

  const calendarEl = document.getElementById('frequency-calendar');
  const events = activities.map(activity => ({
    title: 'run',
    date: new Date(activity.date).toISOString().split('T')[0],
    backgroundColor: '#ff9f89', // 이벤트 배경색
    borderColor: '#ff9f89', // 이벤트 테두리색
    textColor: '#ffffff', // 이벤트 텍스트 색상
  }));

  const calendar = new Calendar(calendarEl, {
    plugins: [dayGridPlugin],
    initialView: 'dayGridMonth',
    events: events,
    eventColor: '#378006', // 기본 이벤트 색상 (일괄 적용)
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    buttonText: {
      today: '오늘',
      month: '월',
      week: '주',
      day: '일'
    }
  });
  calendar.render();

});
</script>

<style scoped>
.my-record {

  border-radius: 10px;
  color: white;
}

#pace-graph,
#distance-graph,
#frequency-calendar {
  background-color: rgba(255, 255, 255);
  border-radius: 10px;
}

.section {
  margin-bottom: 20px;
}

.graph {
  width: 100%;
  height: 300px;
  /* 그래프 컨테이너의 높이 설정 */
  background-color: #f9f9f9;
  /* 그래프 컨테이너의 배경색 설정 */
  border: 1px solid #ddd;
  /* 그래프 컨테이너의 테두리 설정 */
  border-radius: 8px;
  /* 그래프 컨테이너의 모서리 둥글게 설정 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.calendar {
  width: 100%;
  height: 300px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.fc-daygrid-event {
  background-color: black !important; /* 이벤트 배경색 */
  border-color: #ff9f89 !important; /* 이벤트 테두리색 */
  color: black !important; /* 이벤트 텍스트 색상 */
  height: 20px !important; /* 이벤트 높이 조정 */
  line-height: 20px !important; /* 텍스트 세로 정렬 조정 */
  padding: 2px !important; /* 패딩 조정 */
  font-size: 12px !important; /* 텍스트 크기 조정 */
  width: 20px !important; /* 이벤트 너비 자동 조정 */
}
</style>