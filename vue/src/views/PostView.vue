<script setup>
import PostList from '@/components/post/PostList.vue';
import PostUpdate from '@/components/post/PostUpdate.vue';
import { RouterView, useRoute } from 'vue-router'

const route = useRoute()
// const isPostPage = computed(() => {
//   return route.name
// })
</script>

<template>
    <!-- <PostList />
    <PostUpdate /> -->
    <RouterView />
</template>

<style scoped>
</style>