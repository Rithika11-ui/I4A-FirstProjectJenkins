<template>
  <div class=" ps-6 flex justify-center ">
  <div class="w-130 h-85 bg-red-200 rounded-xl overflow-hidden grid grid-cols-2">
    <div class=" w-100 flex flex-col items-start justify-center ">
      <h1 class="text-3xl  text-white font-bold p-8">{{ article }}</h1>
      <Btn class="p-2 ms-5" :color="btnColor">Show More</Btn>
    </div>

    <div class="relative ">
      <img :src='images' alt="" class="absolute bottom-0 right-0 object-contain">
    </div>
  </div>
  </div>
</template>

<script lang="ts" >
import { defineComponent } from 'vue';
import Btn from './button.vue'

export default defineComponent({
  name: 'Promotion',
  components: { Btn },

  props:{
    article: {
      type: String,
      required: true
    },
    images: {
      type: String,
      required: true
    },
    btnColor: {
      type: String,
      default: 'green',
    }
  }
})
</script>

