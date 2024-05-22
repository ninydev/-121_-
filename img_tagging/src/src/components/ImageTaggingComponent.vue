<script setup lang="ts">

import {Cropper} from "vue-advanced-cropper";
import {ref} from "vue";

const img = ref('https://images.pexels.com/photos/4323307/pexels-photo-4323307.jpeg')

const change = ({coordinates, canvas}) => {
  console.log(coordinates, canvas);
}

</script>

<template>
  <div> Картинку сюда</div>
  <div style="width: 50%">
    <cropper
        :src="img"
        @change="change"
    />
  </div>
</template>

<style scoped>

</style>