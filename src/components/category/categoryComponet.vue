<template>
  <article
    class="card flex flex-col justify-between items-center p-2 rounded-xl shadow-md transition-transform duration-200 hover:-translate-y-1 hover:shadow-lg cursor-pointer w-28 h-37"
    :style="{ backgroundColor: color || '#fff' }"
  >
    <img
      v-if="image"
      :src="image"
      :alt="name"
      class="w-24 h-24 object-contain mb-2 sm:w-20 sm:h-20"
    />
    <div class="card-body text-center ">
      <h3 class="text-base font-bold text-[#253d4e] sm:text-sm">{{ name }}</h3>
      <p class="text-xs font-normal text-gray-400 sm:text-[11px]">
        {{ productCount }} item{{ productCount !== 1 ? 's' : '' }}
      </p>
       
    </div>
  </article>
</template>

<script>
export default {
  name: "CategoryComponent",
  props: {
    name: { type: String, required: true },
    productCount: { type: Number, default: 0 },
    color: { type: String, default: "#fff" },
    image: { type: String, default: "" },
  },
};
</script>
