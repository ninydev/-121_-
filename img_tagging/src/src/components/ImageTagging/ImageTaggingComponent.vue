<script setup lang="ts">

import {Cropper} from "vue-advanced-cropper";
import {ref} from "vue";
import {default as createClient} from '@azure-rest/ai-vision-image-analysis';
import {AzureKeyCredential} from '@azure/core-auth';

// import { ImageAnalysisClient, default as createClient } from '@azure-rest/ai-vision-image-analysis';
// import { AzureKeyCredential } from '@azure/core-auth';

const imgOriginal = ref('https://images.pexels.com/photos/4323307/pexels-photo-4323307.jpeg')

const imgCrop = ref("")

const XIMILAR_TOKEN = ref("")

const AZURE_COMPUTER_VISION_END_POINT = ref("https://itsteppv121cv.cognitiveservices.azure.com/")
const AZURE_COMPUTER_VISION_KEY = ref("")
const AZURE_COMPUTER_VISION_REGION = ref("eastus")

const change = ({coordinates, canvas}) => {
  console.log(coordinates, canvas);
  imgCrop.value = canvas.toDataURL('image/webp');
}

const dataURLtoBlob = (dataurl: string) => {
  const arr = dataurl.split(',');
  const mime = arr[0].match(/:(.*?);/)[1];
  const bstr = atob(arr[1]);
  let n = bstr.length;
  const u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new Blob([u8arr], {type: mime});
}

const sendToAzure = async () => {
  const credential = new AzureKeyCredential(AZURE_COMPUTER_VISION_KEY.value);
  const client = createClient(AZURE_COMPUTER_VISION_END_POINT.value, credential);

  const features = [
    // 'Caption',
    // 'DenseCaptions',
    // 'Objects',
    // 'People',
    // 'Read',
    // 'SmartCrops',
    'Tags'
  ];

  const blob = dataURLtoBlob(imgCrop.value);
  const arrayBuffer = await blob.arrayBuffer();

  console.log(blob.type)
  console.log(imgCrop.value)

  client.path('/imageanalysis:analyze').post({
    body: arrayBuffer,
    queryParameters: {
      features: features,
      language: 'en',
      'gender-neutral-captions': 'true',
      'smartCrops-aspect-ratios': [0.9, 1.33]
    },
    contentType: 'application/octet-stream'
  }).then(result => {
    const iaResult = result.body;
    console.log(iaResult)
  })


  // console.log(`Model Version: ${iaResult.modelVersion}`);
  // console.log(`Image Metadata: ${JSON.stringify(iaResult.metadata)}`);
  // if (iaResult.captionResult) {
  //   console.log(`Caption: ${iaResult.captionResult.text} (confidence: ${iaResult.captionResult.confidence})`);
  // }
  // if (iaResult.denseCaptionsResult) {
  //   iaResult.denseCaptionsResult.values.forEach(denseCaption =>
  //       console.log(`Dense Caption: ${JSON.stringify(denseCaption)}`));
  // }
  // if (iaResult.objectsResult) {
  //   iaResult.objectsResult.values.forEach(object =>
  //       console.dir(object, {depth: null, colors: true}));
  // }
  // if (iaResult.peopleResult) {
  //   iaResult.peopleResult.values.forEach(person =>
  //       console.dir(person, {depth: null, colors: true}));
  // }
  // if (iaResult.readResult) {
  //   iaResult.readResult.blocks.forEach(block =>
  //       console.dir(block, {depth: null, colors: true}));
  // }
  // if (iaResult.smartCropsResult) {
  //   iaResult.smartCropsResult.values.forEach(smartCrop =>
  //       console.dir(smartCrop, {depth: null, colors: true}));
  // }
  //
  // console.log("------------------------- TAGS ------------------------- ")
  // if (iaResult.tagsResult) {
  //   iaResult.tagsResult.values.forEach(tag =>
  //       console.dir(tag, {depth: null, colors: true}));
  // }
}

const sendToXimilar = () => {

  const body = {
    _base64: imgCrop.value
  }

  const headers = new Headers()
  headers.append('Content-Type', 'application/json')
  headers.append('Authorization', 'Token ' + XIMILAR_TOKEN.value)

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
    <input v-model="XIMILAR_TOKEN">
    <button @click="sendToXimilar"> Send To Ximilar</button>
  </div>

  <div>
    <input v-model="AZURE_COMPUTER_VISION_END_POINT">
    <input v-model="AZURE_COMPUTER_VISION_KEY">
    <input v-model="AZURE_COMPUTER_VISION_REGION">
    <button @click="sendToAzure"> Send To Azure</button>
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