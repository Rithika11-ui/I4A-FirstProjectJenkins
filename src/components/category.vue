<template>
  <div class="w-45 h-55 rounded-xl flex flex-col justify-center items-center ">
    <div class="-mt-10">
      <img :src='image' alt="">
    </div>
    <div class=" justify-center items-center ">
      <h1 class="text-xl font-bold">{{ name }}</h1>
      <h1 class="text-grey-500 font-light text-center"><span>{{ qty }}</span> items</h1>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'CategoryItem',
  props: {
    image: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    qty: {
      type: Number,
      required: true
    }
  }
})
</script>


