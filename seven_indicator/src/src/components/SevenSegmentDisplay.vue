<script setup lang="ts">
import { useSevenSegmentState } from '@/stores/sevenSegmentState'
import { computed } from 'vue'
import Neuron from '@/components/Neuron.vue'

const segments = useSevenSegmentState()
segments.fillMatrix()

const currentValue = computed({
  get() {
    return segments.getCurrent
  },
  set(val) {
    segments.setCurrent(val)
  }
})
</script>

<template>
  <div class="flex">
    <div class="min-w-[200px] flex flex-col items-center justify-center bg-gray-600 p-4">
      <div class="mb-4">
        <input type="number" min="0" max="9" v-model="currentValue" class="w-full p-2" />
      </div>
      <div id="sevenSegmentDisplay">
        <div id="a" :class="[segments.getCurrentSegments[0] ? 'on' : 'off']"></div>
        <div id="b" :class="[segments.getCurrentSegments[1] ? 'on' : 'off']"></div>
        <div id="c" :class="[segments.getCurrentSegments[2] ? 'on' : 'off']"></div>
        <div id="d" :class="[segments.getCurrentSegments[3] ? 'on' : 'off']"></div>
        <div id="e" :class="[segments.getCurrentSegments[4] ? 'on' : 'off']"></div>
        <div id="f" :class="[segments.getCurrentSegments[5] ? 'on' : 'off']"></div>
        <div id="g" :class="[segments.getCurrentSegments[6] ? 'on' : 'off']"></div>
      </div>
    </div>

    <div class="flex-1 p-4">
      <div id="accordion-collapse" data-accordion="collapse">
        <Neuron :number="0"></Neuron>
        <Neuron :number="1"></Neuron>
        <Neuron :number="2"></Neuron>
        <Neuron :number="3"></Neuron>
        <Neuron :number="4"></Neuron>
        <Neuron :number="5"></Neuron>
        <Neuron :number="6"></Neuron>
        <Neuron :number="7"></Neuron>
        <Neuron :number="8"></Neuron>
        <Neuron :number="9"></Neuron>
        <div class="border-t border-gray-200 dark:border-gray-700"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.on {
  background-color: aqua;
}

#a {
  top: 0;
  left: 0;
  width: 200px;
  height: 20px;
}

#b {
  top: 20px;
  right: 0;
  width: 20px;
  height: 200px;
}

#c {
  top: 240px;
  right: 0;
  width: 20px;
  height: 200px;
}

#d {
  bottom: 0;
  left: 0;
  width: 200px;
  height: 20px;
}

#e {
  top: 240px;
  left: 0;
  width: 20px;
  height: 200px;
}

#f {
  top: 20px;
  left: 0;
  width: 20px;
  height: 200px;
}

#g {
  top: 220px;
  left: 0;
  width: 200px;
  height: 20px;
}

#sevenSegmentDisplay div {
  position: absolute;
  border: 1px solid darkslategray;
  border-radius: 5px;
}

#sevenSegmentDisplay {
  position: relative;
  height: 460px;
  width: 200px;
  overflow: hidden;
}
</style>
