<script setup>
import GroupDetail from '@/components/group/GroupDetail.vue';

</script>

<template>
    <div>
        <RouterView class="appear"/>
</div>
</template>

<style scoped>
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translate(-5px, -5px);
    }

    to {
        opacity: 1;
        transform: translate(0, 0);
    }
}

.appear {
    animation: fadeIn 0.4s ease-in-out;
}


</style>