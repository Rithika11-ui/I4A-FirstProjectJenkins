<template>
  <div>
    <Promotion class=""
    article= "Everyday Fresh & Clean with Our Products"
    :images="union"


    />

    <Promotion
    article= "Make your Breakfast Healthy and Easy"
    :images="Strawberry"

    />

    <Promotion
    article= "The best Organic Products Online"
    :images="Vegestable"
    btnColor= 'yellow'

    />


  </div>
</template>

<script lang="ts" >
import Promotion from './promotion.vue';
import union from '../assets/images/unions.png';
import Strawberry from '../assets/images/strawberry.png';
import Vegestable from '../assets/images/vegestables.png';
import { defineComponent } from 'vue';



export default defineComponent({
  name: 'promotionCard',
  components: {
    Promotion
  },
  data() {
    return {
      union,
      Strawberry,
      Vegestable,

    }
  }
})
</script>
