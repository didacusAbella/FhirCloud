import Vue from 'vue'
import VueRouter from 'vue-router'
import Patients from '../views/Patients.vue'
import Encounters from '../views/Encounters.vue'
import EncounterInfo from '../views/EncounterInfo.vue'
import PatientInfo from '../views/PatientInfo.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Patients',
    component: Patients,
  },
      {
          path: '/patients/:id',
          name: 'PatientInfo',
          component: PatientInfo,
          props: true
      },
  {
    path: '/encounters',
    name: 'Encounters',
    component: Encounters,
  },
      {
          path: '/encounters/:id',
          name: 'EncounterInfo',
          component: EncounterInfo,
          props: true
      }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
