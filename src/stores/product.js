import axios from 'axios'
import { defineStore } from 'pinia'

export const useProductStore = defineStore('product', {
  state: () => ({
    groups: [],
    promotions: [],
    categories: [],
    products: []
  }),

  getters: {
    // 1. Get categories by group name
    // Note: categories have a 'group' field that matches the group name
    getCategoriesByGroup: (state) => {
      return (groupName) => {
        return state.categories.filter((category) => category.group === groupName)
      }
    },

    // 2. Get products by group name
    getProductsByGroup: (state) => {
      return (groupName) => {
        // Products can have a 'group' field directly
        // or we can filter by categories that belong to the group
        const directGroupProducts = state.products.filter(
          (product) => product.group === groupName.toLowerCase()
        )

        if (directGroupProducts.length > 0) {
          return directGroupProducts
        }

        // Fallback: get through categories
        const categoryIds = state.categories
          .filter((category) => category.group === groupName)
          .map(cat => cat.id)

        return state.products.filter((product) =>
          categoryIds.includes(product.categoryId)
        )
      }
    },

    // 3. Get products by category ID
    getProductsByCategory: (state) => {
      return (categoryId) => {
        return state.products.filter((product) => product.categoryId === categoryId)
      }
    },

    // 4. Get popular products (countSold > 10)
    getPopularProducts: (state) => {
      return state.products.filter((product) => (product.countSold || 0) > 10)
    }
  },

  actions: {
    async loadData() {
      try {
        const [groups, promotions, categories, products] = await Promise.all([
          axios.get('http://localhost:3000/api/groups'),
          axios.get('http://localhost:3000/api/promotions'),
          axios.get('http://localhost:3000/api/categories'),
          axios.get('http://localhost:3000/api/products'),
        ])

        this.groups = groups.data
        this.promotions = promotions.data
        this.categories = categories.data
        this.products = products.data
      } catch (error) {
        console.error('❌ Failed to load product data:', error)
        console.error('Make sure your backend server is running on http://localhost:3000')
      }
    }
  }
})
