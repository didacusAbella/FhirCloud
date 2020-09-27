<template>
    <section v-if="patients" id="patients" class="box column is-12">
    <b-table :data="patients" paginated>
        <b-table-column field="id" label="Identificativo" v-slot="props">
            {{ props.row.id }}
        </b-table-column>
        <b-table-column field="fullName" label="Nome" v-slot="props">
            {{ props.row.fullName }}
        </b-table-column>
        <b-table-column label="Azioni" v-slot="props">
        <b-button tag="router-link" :to="'/patients/' + props.row.id" icon-left="info-circle" icon-pack="fas" type="is-link">
            Visualizza Cartella Clinica
        </b-button>
        </b-table-column>
    </b-table>
    </section>
</template>

<script>
export default {
  name: 'Patients',
  data(){
      return {
          patients: null,
     }
    },
    mounted(){
      fetch('http://localhost:4000/fhirserver-5.0.0/fhir/Patient?_format=json')
        .then(response => response.json())
        .then(objects => objects.entry)
        .then(list => list.map(el => el.resource))
        .then(resources => resources.map(function(element){
            element.fullName = `${element.name[0].family} ${element.name[0].given[0]}`
            return element;
        }))
        .then(fin => this.patients = fin)
    }
}
</script>

<style>
#patients {
margin-top: 50px;
}
</style>
