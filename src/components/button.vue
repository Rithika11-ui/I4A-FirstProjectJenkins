<template>
  <div class="">
  <button :class="[btnColor[color] || btnColor.green, 'text-white text-xl font-semibold w-45 h-15 rounded-md cursor-pointer shadow-sm transition-transform duration-800 hover:scale-105 ']">
    <slot></slot>
    <i class="fa-solid fa-arrow-right ms-4"></i>
  </button>
  </div>
</template>

<script lang="ts">
//import type { defineComponent } from 'vue';

export default{
  name: 'Btn',

  props: {
    color: {
      type: String,
      required: true,
      default: 'green',
    },
  },
  data() {
    return {
      btnColor: {
          green: 'bg-green-400 text-white hover:bg-green-500 ',
          yellow: 'bg-yellow-300 text-white hover:bg-yellow-500 ',
        }
    }
    }
  }

</script>

<style>

</style>
