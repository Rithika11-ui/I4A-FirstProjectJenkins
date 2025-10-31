<template>
  <div>
    <CategoryItem class="bg-[#F2FCE4] border border-[#81B13D]"
      :image="cat1"
      name="cucumber"
      :qty="14"
    />

    <CategoryItem class="bg-[#FFFCEB] border border-[#fdf8d7]"
      :image="cat2"
      name="cucumber"
      :qty="17"
    />
    <CategoryItem class="bg-[#ECFFEC] border border-[#d6f6d6]"
      :image="cat3"
      name="cucumber"
      :qty="21"
    />
    <CategoryItem class="bg-red-100 border border-red-200"
      :image="cat4"
      name="cucumber"
      :qty="68"
    />
    <CategoryItem class="bg-pink-100 border border-pink-200"
      :image="cat5"
      name="cucumber"
      :qty="34"
    />
    <CategoryItem class="bg-[#F2FCE4] border border-[#ecfcd6]"
      :image="cat6"
      name="cucumber"
      :qty="25"
    />
    <CategoryItem class="bg-[#FFF3EB] border border-[#fae6d8]"
      :image="cat7"
      name="cucumber"
      :qty="65"
    />
    <CategoryItem class="bg-[#FFFCEB] border border-[#fdf7d1]"
      :image="cat8"
      name="cucumber"
      :qty="54"
    />
    <CategoryItem class="bg-green-200 border border-green-300"
      :image="cat9"
      name="cucumber"
      :qty="33"
    />
    <CategoryItem class="bg-[#FFF3FF] border border-[#fbdffb]"
      :image="cat10"
      name="cucumber"
      :qty="63"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import CategoryItem from './category.vue'
import cat1 from '../../src/assets/images/cat-1.png'
import cat2 from '../../src/assets/images/cat-2.png'
import cat3 from '../../src/assets/images/cat-3.png'
import cat4 from '../../src/assets/images/cat-4.png'
import cat5 from '../../src/assets/images/cat-5.png'
import cat6 from '../../src/assets/images/cat-6.png'
import cat7 from '../../src/assets/images/cat-7.png'
import cat8 from '../../src/assets/images/cat-8.png'
import cat9 from '../../src/assets/images/cat-9.png'
import cat10 from '../../src/assets/images/cat-10.png'

export default defineComponent({
  name: 'ItemCard',
  components: {
    CategoryItem
  },
  data() {
    return {
      cat1,
      cat2,
      cat3,
      cat4,
      cat5,
      cat6,
      cat7,
      cat8,
      cat9,
      cat10,

    }
  }
})
</script>

<style scoped>
</style>
