<!-- 
    TheHamburgerButton
    UserRank랑 GroupRank를 둘 다 포함해야 될까?
    아니면 UserRankView랑 GroupRankView를 따로 만들어야 될까?
    전 : 햄버거, 전체 유저, 유저 디테일, 전체 그룹, 내 그룹(on/off 버튼) >> 로그인 페이지, 내 라이벌(on/off 버튼) >> 로그인 페이지, 
    후 : 햄버거, 전체 유저, 유저 디테일, 전체 그룹, 내 그룹(on/off 버튼), 내 라이벌(on/off 버튼)
 -->
<script setup>

</script>

<template>
    <div class="appear">
        <RouterView />
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
