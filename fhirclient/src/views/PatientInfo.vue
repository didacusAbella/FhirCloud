<template>
<section class="column is-12 box" v-if="patient && allergies && immunizations && imagings">
    <div class="columns is-multiline">
        <div class="column is-6">
            <div class="media">
                <figure class="media-left">
                    <p class="image is-64x64">
                        <img src="../assets/avatar.png">
                    </p>
                </figure>
                <div class="media-content">
                    <dl class="content">
                        <dt class="has-text-weight-bold">Nome Completo</dt>
                        <dd>{{ fullName }}</dd>
                        <dt class="has-text-weight-bold">Codice Fiscale</dt>
                        <dd>{{ patient.identifier[0].value }}</dd>
                        <dt class="has-text-weight-bold">Sesso</dt>
                        <dd>{{ patient.gender}}</dd>
                        <dt class="has-text-weight-bold">Data di Nascita</dt>
                        <dd>{{ patient.birthDate }}</dd>
                        <dt class="has-text-weight-bold">Condizione Sociale</dt>
                        <dd>{{ patient.maritalStatus.coding[0].display }}</dd>
                        <dt class="has-text-weight-bold">Indirizzo</dt>
                        <dd>{{ completeAddress }}</dd>
                    </dl>
                </div>
            </div>
        </div>
        <div class="column is-6">
                <article class="panel" v-if="allergies.length > 0">
                    <p class="panel-heading">Allergie</p>
                    <div class="panel-block" v-for="allergy in allergies" :key="allergy.id">
                        <b-tag type="is-info">{{ allergy.code.coding[0].display }}</b-tag>
                        | <em>{{ allergy.onsetPeriod.start }}</em>
                    </div>
                </article>
                <p v-else>Il paziente non ha allergie</p>
        </div>
        <div class="column is-12">
            <b-table :data="immunizations" paginated pagination-size="is-small" per-page="10">
                <b-table-column  field="occurrenceDateTime" label="Data Somministrazione" v-slot="props">
                    {{props.row.occurrenceDateTime.substring(0,10) }}
                </b-table-column>
                <b-table-column label="Nome Vaccino" v-slot="props">
                    {{ props.row.vaccineCode.coding[0].display }}
                </b-table-column>
                <td slot="empty" colspan="2">
                    Il paziente non ha vaccinazioni
                </td>
            </b-table>
        </div>
        <div class="column is-12">
            <h3 class="is-size-5">Diagnostica</h3>
            <b-carousel-list v-model="test" :items-to-show="1" :data="imagings" icon-pack="fas" v-if="imagings.length > 0">
                <template slot="item" slot-scope="imaging">
                    <div class="level">
                            <figure class="level-item image">
                                <a @click="info(imaging.index)"><img :src="fullImage(imaging)" width="600" height="600"></a>
                            </figure>
                        <div class="level-item">
                            <dl class="content">
                                <dt class="has-text-weight-bold">Tipo di Diagnostica</dt>
                                <dd>{{ imaging.series[0].modality.display }}</dd>
                                <dt class="has-text-weight-bold">Parte del Corpo</dt>
                                <dd>{{ imaging.series[0].bodySite.display }}</dd>
                                <dt class="has-text-weight-bold">Effetuata il</dt>
                                <dd>{{ imaging.started.substring(0, 10) }}</dd>
                            </dl>
                        </div>
                    </div>
                </template>
            </b-carousel-list>
            <p v-else>Non ci sono immagini diagnostiche</p>
        </div>
    </div>
</section>
</template>

<script>
const base_url = "http://localhost:4000/fhir"

function loadPatient(identifier) {
    return fetch(`${base_url}/Patient/${identifier}?_format=json`)
        .then(response => response.json())
}

function loadAllergies(patient) {
    return fetch(`${base_url}/AllergyIntolerance?_format=json&patient=${patient}`)
        .then(response => response.json())
        .then(function(resjson){
            return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
        })
}

function loadImmunizations(patient) {
    return fetch(`${base_url}/Immunization?_format=json&patient=${patient}`)
            .then(response => response.json())
            .then(function(resjson){
                return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

function loadImagings(patient) {
    return fetch(`${base_url}/ImagingStudy?_format=json&patient=${patient}`)
            .then(response => response.json())
            .then(function(resjson){
                return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

export default {
    name: 'PatientInfo',
    props: ["id"],
    data(){
        return {
            gallery: false,
            test: 0,
            patient: null ,
            allergies: null,
            immunizations: null,
            imagings: null
    }},
    computed: {
        fullName: function(){
            let variable = ""
            if('prefix' in this.patient.name[0]) { variable += this.patient.name[0].prefix[0] }
            variable = variable + ' ' + this.patient.name[0].given[0]
            variable = variable + ' ' + this.patient.name[0].family[0]
            if('suffix' in this.patient.name[0]) { variable += this.patient.name[0].suffix[0] }
            return variable
        },
        completeAddress: function() {
            const { text, city, state, postalCode, country} = this.patient.address[0]
            return `${text} ${city} ${state}, ${postalCode} ${country}`
        },
    },
    methods: {
        info(value) {
            this.test = value
        },
        fullImage(imaging) {
            return (imaging.series[0].bodySite.code === '72696002') ? imaging.series[0].endpoint[0].reference : require('../assets/noimage.png')
        }
    },
    mounted(){
        Promise.all([loadPatient(this.id), loadAllergies(this.id), loadImmunizations(this.id),
            loadImagings(this.id) ])
        .then(values => {
            this.patient = values[0]
            this.allergies = values[1]
            this.immunizations = values[2]
            this.imagings = values[3]
        })
    }
}
</script>

