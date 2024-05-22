import { defineStore } from 'pinia'

export const useSevenSegmentState = defineStore('states', {
  state: () => ({
    matrix: Array.from({ length: 10 }, () => Array.from({ length: 7 }, () => false)),
    current: 0
  }),
  getters: {
    getMatrix(state): boolean[][] {
      return state.matrix
    },
    get(state): (num: number) => boolean[] {
      return (num: number) => state.matrix[num]
    },
    getCurrent(state): number {
      return state.current
    },
    getCurrentSegments(state): boolean[] {
      return state.matrix[state.current]
    }
  },
  actions: {
    setCurrent(num: number): void {
      if (num < 0) num = 0
      if (num > 9) num = 9
      this.current = num
    },
    fillMatrix(): void {
      // a     b      c      d      e      f      g
      this.matrix[0] = [true, true, true, true, true, true, false, true]
      this.matrix[1] = [false, true, true, false, false, false, false, true]
      this.matrix[2] = [true, true, false, true, true, false, true, true]
      this.matrix[3] = [true, true, true, true, false, false, true, true]
      this.matrix[4] = [false, true, true, false, false, true, true, true]
      this.matrix[5] = [true, false, true, true, false, true, true, true]
      this.matrix[6] = [true, false, true, true, true, true, true, true]
      this.matrix[7] = [true, true, true, false, false, false, false, true]
      this.matrix[8] = [true, true, true, true, true, true, true, true]
      this.matrix[9] = [true, true, true, true, false, true, true, true]
    }
  }
})
