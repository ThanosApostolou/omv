<template>
    <div>
        <svg :x="0" :y="0" :height="owlentitysvg.height" :width="owlentitysvg.width">
            <OwlEntityNodeSVG :owlentitysvg="owlentitysvg" @show-entity="showEntity" />
        </svg>
        <v-dialog v-if="showdialog" v-model="showdialog" max-width="1400">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlEntityInfoComp :owlentity="selectedOwlEntity" />
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { OwlEntitySVG } from "./OwlEntitySVG";

import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";
import OwlEntityInfoComp from "./OwlEntityInfoComp.vue";

export default {
    name: "OwlEntityComp",
    components: {
        OwlEntityNodeSVG,
        OwlEntityInfoComp
    },
    props: {
        owlentity: {
            type: Object,
            default: null
        },
        entityType: {
            type: String,
            default: "class"
        },
        reverse: {
            type: Boolean,
            default: false
        },
    },
    data() {
        return {
            showdialog: false,
            selectedOwlEntity: null,
            owlentitysvg: null
        };
    },
    methods: {
        showEntity(owlentitysvg) {
            this.selectedOwlEntity = owlentitysvg.owlentity;
            this.showdialog = true;
        }
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.entityType, null);
        this.owlentitysvg.calcVisibility(null, "");
        this.owlentitysvg.calcWidth(1);
        this.owlentitysvg.calcPositions(0, 0);
    }
};
</script>