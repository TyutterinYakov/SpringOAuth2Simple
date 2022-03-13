<template>
  <div>
    <h1>{{access}}</h1>
    <div>
      Регистрация:
      <form @submit="submitRegistration">
        <input type="text" placeholder="name" v-model="registrationForm.userName">
        <input type="password" placeholder="password" v-model="registrationForm.password">
        <input type="submit" value="Регистрация"/>
      </form>
      Авторизация:
      <form @submit="submitLogin">
        <input type="text" placeholder="name" v-model="loginForm.username">
        <input type="password" placeholder="password" v-model="loginForm.password">
        <input type="submit" value="Войти"/>
      </form>
      Выход:
        <input type="button" value="Выйти" @click="submitLogout"/>
      

    </div>
  </div>

</template>

<script>
import {mapGetters} from "vuex"
import "@/assets/main.css"
export default {
  computed: mapGetters({
    access: "user/getAccess"
  }),
  fetch(){
    this.$store.dispatch("user/fetchAccess")
  },
  data(){
    return {
      registrationForm: {
        userName: '',
        password: ''
      },
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods:{
    submitLogout(){
      this.$store.dispatch("user/logout")
    },
    submitLogin(event){
      event.preventDefault();
      this.$store.dispatch("user/login", this.loginForm)
    },
    submitRegistration(event){
      event.preventDefault();
      this.$store.dispatch("user/registration", this.registrationForm)
    }
  }
}
</script>
