
<template>

      <div class=" h-100 w-75 border-[1.5px] border-gray-300 hover:border-[rgba(59,183,126,1)] rounded-md ">
        <div class="relative h-33">
         <div v-if="promotionAsPercentage "
           :class="[
             'absolute top-3 left-0 w-15 h-9 text-center text-white pt-1 rounded-r-full z-10',
             promotionAsPercentage.toString()=== 'Hot' ? 'bg-red-400' :
             promotionAsPercentage.toString() === 'Sale' ? 'bg-yellow-400' :
             'bg-green-500'
           ]">
        {{ isNaN(Number(promotionAsPercentage)) ? promotionAsPercentage : promotionAsPercentage + '%' }}
      </div>

          <div class="flex justify-center pt-13">
            <img :src="image" alt="Mango"/>
          </div>
        </div>


        <div class="p-4 mt-13 ">
          <ul>
            <li class="text-gray-500 ">Hodo Foods</li>
            <li class="font-semibold ">{{ name }}</li>
            <li class="py-1 text-xl font-semibold gap-2 ">
              <span class="flex gap-1.5">
                <template v-for="star in 5" :key="star">

                  <i v-if="rating >= star" class="pi pi-star-fill text-yellow-400 text-lg"></i>

                  <i v-else-if="rating >= star - 0.5" class="pi pi-star-half-fill text-yellow-400 text-lg"></i>

                  <i v-else class="pi pi-star text-gray-300 text-lg"></i>
                </template>
                ({{ rating.toFixed(1) }})
              </span>
            </li>
            <li class="text-gray-500 font-semibold">{{size}}gram</li>
            <ul class="flex pt-2 items-center">
              <li class="text-[rgba(59,183,126,1)] text-3xl ">${{ price.toFixed(2) }}</li>
              <li class=" text-gray-500 font-semibold ps-3 line-through mt-2 ">$2.00</li>

              <div :class="['flex justify-center items-center ms-10 rounded-sm w-25 h-10 ml-auto overflow-hidden', isAdded ? 'border border-[rgba(59,183,126,1)]' : '']">

                  <div v-if="!isAdded"
                      class="h-full flex items-center gap-4 px-3 cursor-pointer bg-green-200 transition-colors w-full justify-center"
                      @click="addToCart">
                    <span class="text-[rgba(59,183,126,1)] font-semibold text-sm">Add</span>
                    <i class="fa fa-plus text-[rgba(59,183,126,1)]" aria-hidden="true"></i>
                  </div>

                  <div v-else class="flex items-center gap-5 w-full h-full justify-center">
                    <h1 class="text-2xl text-[rgba(59,183,126,1)]">{{ num }}</h1>

                    <div class="flex flex-col -space-y-1.5 ">

                      <button @click="upIcons()">
                        <i class=" upIcons pi pi-chevron-up text-green-500 hover:text-green-300 text-sm "></i>
                      </button>

                      <button @click="downIcons()">
                        <i class="downIcons pi pi-chevron-down text-green-500 hover:text-green-300 text-sm"></i>
                      </button>

                    </div>
                </div>
              </div>
            </ul>
          </ul>

        </div>
      </div>

</template>

<script lang="ts" setup>
import { ref } from "vue";
defineProps<{
  name: string;
  rating: number;
  size: number;
  image: string;
  price: number;
  promotionAsPercentage: number | string;
}>();

const num = ref(1);
const isAdded = ref(false);

const addToCart = () => { isAdded.value = true };

  const upIcons =() => {
      num.value +=1;
};
const downIcons = () => {
  if (num.value > 1) {
    num.value -= 1;
  } else {
    return isAdded.value = false;
  }

    }

</script>

<style>

</style>
