<template>
<section class="column is-12 box" v-if="encounters">
    <b-table :data="encounters" paginated>
        <b-table-column field="id" label="Identificativo" v-slot="props">
            {{props.row.id}}
        </b-table-column>
        <b-table-column label="Codice Incontro" v-slot="props">
            <b-tooltip :label="props.row.class.display">
            <span>{{ props.row.class.code }}</span>
            </b-tooltip>
        </b-table-column>
        <b-table-column label="Azioni" v-slot="props">
            <b-button tag="router-link" :to="'/encounters/' + props.row.id" icon-pack="fas" type="is-link" iocn-left="info">
                Maggiori Dettagli
            </b-button>
        </b-table-column>
    </b-table>
</section>
</template>

<script>
export default {
    name: 'Encounters',
    data(){
        return {
            encounters: null
        }
    },
    mounted(){
        fetch('http://localhost:4000/fhirserver-5.0.0/fhir/Encounter?_format=json')
        .then(response => response.json())
        .then(encs => encs.entry)
        .then(entries => entries.map(entry => entry.resource))
        .then(fin => this.encounters = fin)
    }
}
</script>
