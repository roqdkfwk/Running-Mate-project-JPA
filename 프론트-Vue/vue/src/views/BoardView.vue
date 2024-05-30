<script setup>
import BoardList from '@/components/board/BoardList.vue';
import BoardUpdate from '@/components/board/BoardUpdate.vue';
import { RouterView, useRoute } from 'vue-router'

const route = useRoute()
// const isBoardPage = computed(() => {
//   return route.name
// })
</script>

<template>
    <!-- <BoardList />
    <BoardUpdate /> -->
    <RouterView />
</template>

<style scoped>
</style>