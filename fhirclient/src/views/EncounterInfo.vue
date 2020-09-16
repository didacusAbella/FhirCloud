<template>
    <section class="column is-12 box" v-if="encounter && conditions && observations && procedures && devices && medications">
       <div class="columns is-multiline">
        <div class="column is-6">
            <h3 class="title is-5">Appuntamento n° {{ encounter.id }}</h3>
            <dl class="content">
                <dt class="has-text-weight-bold">Data Appuntamento</dt>
                <dd>{{'Dal ' + encounter.period.start.substring(0, 10) + ' Al ' + encounter.period.end.substring(0, 10) }}</dd>
                <dt class="has-text-weight-bold">Motivo Appuntamento</dt>
                <dd>{{encounter.reasonCode[0].coding[0].display}}</dd>
                <dt class="has-text-weight-bold">Tipo Appuntamento</dt>
                <dd>{{encounter.class.display}}</dd>
                <dt class="has-text-weight-bold">Paziente</dt>
                <dd>
                    <b-button type="is-text" tag="router-link" :to="'/patients/' + encounter.subject.reference">
                        Vai alla Cartella del paziente
                    </b-button>
                </dd>
            </dl>
        </div>
        <div class="column is-6">
            <h3 class="title is-5">Condizioni del paziente</h3>
            <ul class="content" v-if="conditions.length > 0">
                <li v-for="(condition, index) in conditions" :key="index">
                    <b-message :title="'Condizione n°' + condition.id" :closable="false" icon-pack="fas" type="is-info" size="is-small">
                        <dl class="content">
                            <dt class="has-text-weight-bold">Diagnosi</dt>
                            <dd>{{condition.code.coding[0].display}}</dd>
                            <dt class="has-text-weight-bold">Data Rilevazione Condizione</dt>
                            <dd>{{ condition.onsetDateTime}}</dd>
                            <dt class="has-text-weight-bold">Data Termine Condizione</dt>
                            <dd>{{ condition.abatementDateTime }}</dd>
                        </dl>
                    </b-message>
                </li>
            </ul>
            <p v-else>Nessuna condizione particolare</p>
        </div>
        <div class="column is-12">
            <b-tabs v-model="activeTab">
                <b-tab-item label="Attrezzatura Utilizzata" icon-pack="fas" icon="toolbox">
                    <ol class="content" v-if="devices.length > 0">
                        <li v-for="(device, index) in devices" :key="index">
                            {{ device.type.coding[0].display}}
                        </li>
                    </ol>
                    <p v-else>Nessuna Attrezzatura Utilizzata</p>
                </b-tab-item>
                <b-tab-item label="Osservazioni Effettuate" icon-pack="fas" icon="stethoscope">
                    <b-table :data="observations" :bordered="true" :hoverable="true" :paginated="true">
                        <b-table-column field="id" label="ID" v-slot="props">
                            {{props.row.id}}
                        </b-table-column>
                        <b-table-column field="effectiveDateTime" label="Effettuata il" v-slot="props">
                            <b-tag type="is-info">{{ props.row.effectiveDateTime.substring(0, 10) }}</b-tag>
                        </b-table-column>
                        <b-table-column label="Nome" v-slot="props">
                            <em>{{props.row.code.coding[0].display}}</em>
                        </b-table-column>
                        <b-table-column label="Valore" v-slot="props">
                            <strong>{{ props.row.valueString }}</strong>
                        </b-table-column>
                        <td slot="empty" colspan="2">
                            Nessuna osservazione effettuata
                        </td>
                    </b-table>
                </b-tab-item>
                <b-tab-item label="Medicine Prescritte" icon-pack="fas" icon="pills">
                    <template v-if="medications.length > 0">
                    <section class="panel">
                        <div class="panel-block" v-for="(medication, index) in medications" :key="index">
                            <em>{{ medication.code.coding[0].display }}</em>
                        </div>
                    </section>
                    </template>
                    <p v-else>Nessuna medicina prescritta</p>
                </b-tab-item>
                <b-tab-item label="Procedure Avviate" icon-pack="fas" icon="procedures">
                    <template v-if="procedures.length > 0">
                    <section class="media" v-for="(procedure, index) in procedures" :key="index">
                        <figure class="media-left">
                            <b-icon pack="fas" icon="file-medical" size="is-large" type="is-secondary"/>
                        </figure>
                        <div class="media-content">
                            <div class="content">
                                <p>{{ procedure.performedDateTime.substring(0, 10) }}</p>
                                <p><strong>{{ procedure.code.coding[0].display }}</strong> <small>{{ procedure.id }}</small> </p>

                            </div>
                        </div>
                    </section>
                    </template>
                    <p v-else>Nessuna procedura effettuata</p>
                </b-tab-item>
            </b-tabs>
        </div>
        </div>
    </section>
</template>

<script>
const base_url = 'http://localhost:4000/fhir'

function loadEncounter(id) {
    return fetch(`${base_url}/Encounter/${id}?_format=json`).then(response => response.json())
}

function loadConditions(encounter) {
    return fetch(`${base_url}/Condition?_format=json&encounter=${encounter}`)
            .then(response => response.json())
            .then(resjson => {
                return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

function loadDevices(encounter) {
    return fetch(`${base_url}/Device?_format=json&identifier=${encounter}`)
            .then(response => response.json())
            .then(resjson => {
               return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

function loadMedications(encounter) {
    return fetch(`${base_url}/Medication?_format=json&identifier=${encounter}`)
            .then(response => response.json())
            .then(resjson => {
                return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

function loadObservations(encounter) {
    return fetch(`${base_url}/Observation?_format=json&encounter=${encounter}`)
            .then(response => response.json())
            .then(resjson => {
                return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
            })
}

function loadProcedures(encounter) {
    return fetch(`${base_url}/Procedure?_format=json&encounter=${encounter}`)
        .then(response => response.json())
        .then(resjson => {
            return ('entry' in resjson) ? resjson.entry.map(en => en.resource) : []
        })
}

export default {
    props: ["id"],
    data() {
        return {
            activeTab: 0,
            encounter: null,
            conditions: null,
            devices: null,
            medications: null,
            observations: null,
            procedures: null
        }
    },
    mounted() {
        Promise.all([loadEncounter(this.id), loadConditions(this.id), loadDevices(this.id), 
                        loadObservations(this.id), loadMedications(this.id), loadProcedures(this.id)])
        .then(values => {
            this.encounter = values[0]
            this.conditions = values[1]
            this.devices = values[2]
            this.observations = values[3]
            this.medications = values[4]
            this.procedures = values[5]
        })
    }
}
</script>
