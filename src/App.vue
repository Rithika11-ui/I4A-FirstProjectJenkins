<template>
  <div class="">
    <Header/>

    <div class="home-container flex flex-col justify-start items-center pt-14">
      <!-- Categories Section -->
      <section class="categories flex flex-wrap justify-center items-center gap-5 lg:gap-10">
        <CategoryComponent
          v-for="(category, index) in productStore.categories"
          :key="index"
          :name="category.name || 'Unknown Category'"
          :product-count="category.productCount || 0"
          :color="category.color || '#ccc'"
          :image="getImageUrl(category.image)"
          :alt="category.name || 'Category Image'"
        />
      </section>

      <!-- Promotions Section -->
      <section class="promotions flex flex-wrap justify-center items-center gap-5 mt-10  ">
        <PromotionComponent
          v-for="(promo, index) in productStore.promotions"
          :key="index"
          :title="promo.title || 'Promotion'"
          :color="promo.color || '#eee'"
          :image="getImageUrl(promo.image)"
          :button-color="promo.buttonColor || '#000'"
          :url="promo.url || '#'"
          :alt="promo.title || 'Promotion Image'"
        />
      </section>
    </div>
    <section class="pt-10">
      <div class="px-8">

        <div class="flex items-center justify-between w-full h-24">
        <h1 class="text-3xl font-bold text-gray-500 cursor-pointer">
          Popular Products
        </h1>

        <div class="flex items-center gap-8 px-12 h-full font-bold text-gray-500 cursor-pointer">
          <h2>All</h2>
          <h2 class="whitespace-nowrap">Milks & Dairies</h2>
          <h2 class="whitespace-nowrap">Coffees & Teas</h2>
          <h2 class="whitespace-nowrap">Pet Foods</h2>
          <h2>Meats</h2>
          <h2>Vegetables</h2>
          <h2>Fruits</h2>
        </div>
        </div>
      </div>
    <div class="px-8 ">
      <div class="grid grid-cols-5 gap-12 mt-5">
        <card
          v-for="(card, index) in productStore.products"
          :key="index"
          :name="card.name"
          :rating="card.rating"
          :size="Number(card.size)"
          :image="getImageUrl(card.image)"
          :price="card.price"
          :promotionAsPercentage="card.promotionAsPercentage"

        />
      </div>
    </div>
    </section>
  </div>
</template>

<script lang="ts" setup >
import { onMounted } from 'vue'
import { useProductStore } from './stores/product'
import CategoryComponent from "./components/category/categoryComponet.vue"
import PromotionComponent from "./components/promotion/promotionComponent.vue"
import Header from './components/header/header.vue'
import card from './components/card/card.vue'

const productStore = useProductStore()

onMounted(() => {
  productStore.loadData() // fetch all data from backend once
})

const getImageUrl = (imgPath) => {
  if (!imgPath) return 'https://via.placeholder.com/150'

  try {
    let imagePath = imgPath

    // Check if it's a JSON array string (starts with [ or ")
    if (typeof imgPath === 'string' && (imgPath.startsWith('[') || imgPath.startsWith('\"'))) {
      const imageArray = JSON.parse(imgPath)
      imagePath = imageArray[0]
    }

    // Replace backslashes with forward slashes and construct URL
    return `http://localhost:3000/${imagePath.replace(/\\/g, '/')}`
  } catch (error) {
    // If parsing fails, treat it as a direct path
    console.error('Error parsing image path:', error)
    return `http://localhost:3000/${imgPath.replace(/\\/g, '/')}`
  }
}


</script>
