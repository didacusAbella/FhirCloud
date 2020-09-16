<template>
<main class="container">
  <b-navbar class="box">
    <template slot="brand">
        <img src="./assets/logo.jpg" width="150" height="150" alt="Logo"/>
    </template>
    <template slot="start">
        <b-navbar-item tag="router-link" to="/">
            <b-icon pack="fas" icon="user-injured"/>
            Pazienti
        </b-navbar-item>
        <b-navbar-item tag="router-link" to="/encounters">
            <b-icon pack="fas" icon="calendar-check"/>
            Appuntamenti
        </b-navbar-item>
    </template>
  </b-navbar>
  <section class="columns">
    <router-view/>
  </section>
</main>
</template>

<script>
export default {

}
</script>
