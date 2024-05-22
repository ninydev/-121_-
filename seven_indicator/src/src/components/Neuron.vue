<script setup lang="ts">
// За какую цифру отвечает этот нейрон
import { onMounted, type Ref, ref, watch } from 'vue'

const props = defineProps({
  number: Number
})

import { useSevenSegmentState } from '@/stores/sevenSegmentState'
import { initAccordions } from 'flowbite'

const segments = useSevenSegmentState()

const weights = Array.from({ length: 8 }, () => Math.random())

const neuronActivation: Ref<Number> = ref(0)

const calcNeuronActivation = (): number => {
  if (weights.length !== segments.getCurrentSegments.length) {
    throw new Error('The length of weights array must be equal to the length of segments array')
  }

  let activation = 0
  for (let i = 0; i < weights.length; i++) {
    activation += weights[i] * (segments.getCurrentSegments[i] ? 1 : 0)
  }

  neuronActivation.value = activation

  return activation
}

watch(
  () => segments.getCurrentSegments,
  () => {
    calcNeuronActivation()
  }
)

const calcSum = (line: number): number => {
  let sum = 0
  for (let i = 0; i < weights.length; i++) {
    sum += weights[i] * (segments.getMatrix[line][i] ? 1 : 0)
  }
  return sum
}

const calcSigmoid = (sum: number) => {
  return 1 / (1 + Math.exp(-sum))
}

const educationFor = () => {
  for (let i = 0; i < 1000; i++) {
    educationStep()
  }
  calcNeuronActivation()
}

onMounted(() => {
  educationFor()
  initAccordions()
})

const educationStep = () => {
  for (let i = 0; i < 10; i++) {
    let sum = calcSum(i)
    let sigmoid = calcSigmoid(sum)

    let expected = 0
    if (i == props.number) expected = 1

    let error = sigmoid - expected
    let correct = error * sigmoid * (1 - sigmoid)

    let rate = 0.1

    for (let j = 0; j < 8; j++) {
      weights[j] = weights[j] - (segments.getMatrix[i][j] ? 1 : 0) * correct * rate
    }
  }
}
</script>

<template>
  <h2 :id="`accordion-collapse-heading-${props.number}`">
    <button
      type="button"
      class="flex items-center justify-between w-full p-3 text-sm rtl:text-right text-gray-50 border border-b-0 border-gray-200 rounded-t-xl focus:ring-2 focus:ring-gray-200 hover:bg-gray-600 gap-3"
      :class="{ 'bg-sky-500': neuronActivation.valueOf() > 0.5 }"
      :data-accordion-target="`#accordion-collapse-body-${props.number}`"
      aria-expanded="false"
      :aria-controls="`accordion-collapse-body-${props.number}`"
    >
      <span
        ><strong>{{ props.number }}</strong> <em>NeuronActivation</em> =
        {{ neuronActivation.toFixed(4) }}
      </span>
      <svg
        data-accordion-icon
        class="w-3 h-3 rotate-180 shrink-0"
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 10 6"
      >
        <path
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="1"
          d="M9 5 5 1 1 5"
        />
      </svg>
    </button>
  </h2>
  <div
    :id="`accordion-collapse-body-${props.number}`"
    class="hidden"
    :aria-labelledby="`accordion-collapse-heading-${props.number}`"
  >
    <div class="p-4 border border-b-0 border-gray-200 dark:border-gray-700 dark:bg-gray-900">
      <div class="flex">
        <div class="w-1/2">
          <ol type="a" class="text-gray-500 list-outside list-lower-alpha">
            <li v-for="(weight, index) in weights" :key="index">
              {{ weight.toFixed(4) }}
            </li>
          </ol>
        </div>
        <div class="w-1/2 pl-4">
          <p class="text-sky-400/100">{{ props.number }} ==> {{ neuronActivation }}</p>
          <button class="bg-sky-500 text-white px-4 py-2 mt-2" @click="educationFor">Edu</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
