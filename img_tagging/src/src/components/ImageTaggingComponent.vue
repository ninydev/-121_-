<script setup lang="ts">

import {Cropper} from "vue-advanced-cropper";
import {ref} from "vue";

const imgOriginal = ref('https://images.pexels.com/photos/4323307/pexels-photo-4323307.jpeg')

const imgCrop = ref("")

const API_TOKEN = ref("")

const change = ({coordinates, canvas}) => {
  console.log(coordinates, canvas);
  imgCrop.value = canvas.toDataURL();
}

const sendToXimilar = () => {

  const body = {
    _base64: imgCrop.value
  }

  console.log("API " + API_TOKEN.value)

  const headers = new Headers()
  headers.append('Content-Type', 'application/json')
  headers.append('Authorization', 'Token ' + API_TOKEN.value)

  fetch('https://api.ximilar.com/photo/tags/v2/tags', {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(body)
  })
      .then(res => {
        console.log(res.status)
        return res.json()
      })
      .then(data => {
        console.log(data)
      })
      .catch(err => {
        console.error(err)
      })

}

</script>

<template>
  <div> Картинку сюда</div>

  <div>
    <input v-model="API_TOKEN">
    <button @click="sendToXimilar"> Send To Ximilar</button>
  </div>

  <div style="width: 30%; float: left">
    <cropper
        :src="imgOriginal"
        @change="change"
    />
  </div>
  <div style="width: 30%; float: left">
    <img :src="imgCrop">
  </div>
</template>

<style scoped>

</style>