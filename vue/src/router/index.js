import { createRouter, createWebHistory } from 'vue-router'
import { useMainStore } from '@/stores/main'

import BoardView from '@/views/BoardView.vue'
import MainView from '@/views/MainView.vue'
import SignupView from '@/views/SignupView.vue'
import LoginView from '@/views/LoginView.vue'
import GroupView from '@/views/GroupView.vue'
import RankView from '@/views/RankView.vue'
import RecordView from '@/views/RecordView.vue'
import UserView from '@/views/UserView.vue'
import MyLog from '@/views/MyLog.vue'
import MyPageView from '@/views/MyPageView.vue'

import UserRank from '@/components/rank/UserRank.vue'
import GroupRank from '@/components/rank/GroupRank.vue'
import GroupMemberRank from '@/components/rank/GroupMemberRank.vue'

import UserDetail from '@/components/user/UserDetail.vue'

import MyRecord from '@/components/record/MyRecord.vue'
import RecentRecord from '@/components/record/RecentRecord.vue'
import BadgeLog from '@/components/record/BadgeLog.vue'
// import BadgeLogDetail from '@/components/record/BadgeLogDetail(임시).vue'

import GroupCreate from '@/components/group/GroupCreate.vue'
import GroupDetail from '@/components/group/GroupDetail.vue'
import GroupUpdate from '@/components/group/GroupUpdate.vue'

import BoardCreate from '@/components/board/BoardCreate.vue'
import BoardUpdate from '@/components/board/BoardUpdate.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import BoardList from '@/components/board/BoardList.vue'
import StravaAuth from '@/views/StravaAuth.vue'




const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: "",
      name: "main",
      component: MainView,
      
    },
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: '/mypage',
      name: 'myPageView',
      component: MyPageView,
      meta: { requiresAuth: true }
    },
    {
      path: "/myLog",
      name: "myLog",
      component: MyLog
    },
    {
      path: '/strava-auth',
      name: 'StravaAuth',
      component: StravaAuth
    },
    {
      path: '/strava-callback',
      name: 'StravaCallback',
      component: StravaAuth // 같은 컴포넌트를 사용하여 콜백 처리
    },
    {
      path: "/signup",
      name: "signupView",
      component: SignupView,
    },
    { // LoginView
      path: "/login",
      name: "loginView",
      component: LoginView,
    },
    {
      path: "/group",
      name: "createGroup",
      component: GroupCreate,
      meta: { requiresAuth: true }
    },
    { // RecordView
      path: "/record",
      name: "recordView",
      component: RecordView,
      // 로그인이 필요한 페이지
      meta: { requiresAuth: true },
      children: [
    //     { // 없어도 될듯
    //       path: "",
    //       name: "startRecording",
    //       component: LATER
    //     },
    //     { // 없어도 될듯
    //       path: "",
    //       name: "endRecording"
    //     },
        {
          path: "recent",
          name: "recentRecord",
          component: RecentRecord,
        },
        {
          path: "my",
          name: "myRecord",
          component: MyRecord,
        },
        {
          path: "badge",
          name: "badgeLog",
          component: BadgeLog,
          // children: [
          //   { // 디테일이 왜 필요하더라?
          //     path: "detail",
          //     name: "badgeLogDetail",
          //     component: BadgeLogDetail
          //   }
          // ]
        },        
      ]
    },
    { // RankView
      path: "/rank",
      name: "rank",
      component: RankView,
      children: [
        { // totalUserRank
          path: "",
          name: "totalUserRank",
          component: UserRank
        },
        {
          path: "/user/:rivalId",
          name: "compareRank",
          component: UserDetail
        },
        //   { // 라우터가 필요한가??
        //     // 페이지 내에서 나와 관련된 사람만 필터링 할 수 있으면 필요없을듯
        //     path: "my",
        //     name: "myRivalRank",
        //     component: RivalRank
        //   },
        {
          path: "group",
          name: "groupRank",
          component: GroupRank
        },
        //   { // 마찬가지로 라우터가 필요한가?
        //     path: "group/my",
        //     name: "myGroupRank",
        //     component: MyGroupRank
        //   },
        {
          path: "group/:groupId",
          name: "groupMemberRank",
          component: GroupMemberRank
        },
      ]
    },
    { // GroupView
      path: "/group/:groupId",
      name: "groupDetail",
      component: GroupDetail,
      children: [
        {
          path: "update",
          name: "groupUpdate",
          component: GroupUpdate
        },
        {
          path: "board",
          name: "board",
          component: BoardView,
          children: [
            {
              path: "list",
              name: "boardList",
              component: BoardList
            },
            { // 게시글 작성
              path: "create",
              name: "boardCreate",
              component: BoardCreate,
              meta: { requiresAuth: true }

            },
            { // 게시글 수정
              path: ":id/update",
              name: "boardUpdate",
              component: BoardUpdate,
              meta: { requiresAuth: true }
            },
            //       { // 없어도 될듯
            //         path: "search",
            //         name: "boardSearch",
            //         component: BoardSearch
            //       },
            { // 게시글보기
              path: ":id",
              name: "boardDetail",
              component: BoardDetail
            },
            //       { // 없어도 될듯
            //         path: ":boardId/reply",
            //         name: "replyCreate",
            //         component: replyCreate
            //       },
            //       { // 없어도 될듯
            //         path: ":boardId/reply",
            //         name: "replyDetele",
            //         component: replyDelete
            //       }
          ]
        }
      ]
    },
    { // 마이페이지에는 userId가 필요없나??
      path: "/user",
      name: "user",
      component: UserView,
      meta: { requiresAuth: true },
      // children: [
      //     { // 내 정보 수정
      //       path: "update",
      //       name: "userUpdate",
      //       component: UserUpdate
      //     },
      // ]
    },
  ]
})

router.beforeEach((to, from) => {
  if (to.meta.requiresAuth && !sessionStorage.getItem('accessToken')) {
    return {
      name: 'loginView',
      query: { redirect: to.fullPath } // 원래 경로를 쿼리로 전달
    }
  } else {
    return true;
  }
})

export default router
