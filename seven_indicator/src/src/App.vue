<script setup lang="ts">
import SevenSegmentDisplay from '@/components/SevenSegmentDisplay.vue'
import { onMounted } from 'vue'
import { initModals } from 'flowbite'

onMounted(() => {
  initModals()
})
</script>

<template>
  <div class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-50">
    <div class="bg-gray-700 rounded-lg shadow-lg p-6 w-full max-w-4xl">
      <header class="text-lg font-semibold text-white mb-4 text-center w-full">
        Seven Segment AI
      </header>
      <div class="border-t border-gray-200 dark:border-gray-700"></div>
      <SevenSegmentDisplay />
      <div class="border-t border-gray-200 dark:border-gray-700"></div>
      <footer class="text-sm text-white mb-4 text-center w-full">
        &copy;
        <a href="https://ninydev.com" target="_blank" class="text-white hover:text-gray-300 mx-2"
          >Oleksandr Nykytin</a
        >
        <a href="https://lab325.com" target="_blank" class="text-white hover:text-gray-300 mx-2"
          >Lab325.com</a
        >
        <a
          href="https://nikolaev.itstep.org/"
          target="_blank"
          class="text-white hover:text-gray-300 mx-2"
          >ItStep.org</a
        >
      </footer>
    </div>
  </div>
</template>

<style scoped></style>
