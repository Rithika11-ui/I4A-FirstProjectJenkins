<template>
  <div
    class="promo flex justify-between items-center p-4 rounded-xl transition-transform duration-200 hover:-translate-y-1 max-w-lg w-[450px] h-[270px]"
    :style="{ backgroundColor: color }"
  >
    <div class="promo-content flex flex-col justify-center gap-7 ">
      <h3 class="promo-title text-2xl font-bold text-[#253d4e] ps-5">{{ title }}</h3>
      <a :href="url" target="_blank" rel="noopener">
        <button
          class="shopbtn w-[140px] h-12 rounded text-white transition-colors duration-200 hover:brightness-120 ms-5 font-semibold"
          :style="{ backgroundColor: buttonColor }"
          @click.prevent="shopNow"
        >
          Shop Now
          <i class="fa fa-arrow-right ps-3" aria-hidden="true"></i>
        </button>
      </a>
    </div>

    <img
      class="promo-image w-[215px] object-contain rounded mt-8 "
      :src="image"
      :alt="title"
    />

  </div>
</template>

<script >
export default {
  name: "PromotionComponent",
  props: {
    title: { type: String, required: true },
    color: { type: String, default: "#fff" },
    image: { type: String, required: true },
    buttonColor: { type: String, default: "#3bb77e" },
    url: { type: String, default: "#" },
  },
  methods: {
    shopNow() {
      alert("Let's shop: " + this.title);
    },
  },
};
</script>
