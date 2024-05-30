<template>
    <div id="app">

        <TheHeaderNav />
        <div>
        <TheHamburgerButton />
        <br>
        <RouterView class="appear" id="content" />
  </div>
    </div>

</template>
  
<script setup>
import TheHamburgerButton from './components/common/TheHamburgerButton.vue';
import TheHeaderNav from './components/common/TheHeaderNav.vue';



</script>

<style>
.gowun-dodum-regular {
    font-family: "Gowun Dodum", sans-serif;
    font-weight: 400;
    font-style: normal;
}


#app {
    /* font-family: Avenir, Helvetica, Arial, sans-serif; */
    font-family: "Gowun Dodum", sans-serif;
    font-weight: 400;
    font-style: normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}


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
