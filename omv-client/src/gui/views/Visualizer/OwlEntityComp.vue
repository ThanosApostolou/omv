<template>
    <div>
        <svg :x="0" :y="0" :height="this.height" :width="this.width">
            <OwlEntityNodeSVG :owlentitysvg="owlentitysvg" @show-entity="showEntity" />
        </svg>
        <v-dialog v-if="show" v-model="show">
            <v-card>
                <span> {{ selectedOwlEntity.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";
import { OwlEntitySVG } from "./OwlEntitySVG";

export default {
    name: "OwlEntityComp",
    components: {
        OwlEntityNodeSVG
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
            show: false,
            selectedOwlEntity: null,
            owlentitysvg: null,
            width: 0,
            height: 0
        };
    },
    methods: {
        showEntity(owlentity) {
            this.selectedOwlEntity = owlentity;
            this.show = true;
        }
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.entityType, null);
        this.owlentitysvg.calcVisibility(null, "");
        this.owlentitysvg.calcWidth(1);
        this.owlentitysvg.calcPositions(0, 0);
        this.width = this.owlentitysvg.width;
        this.height = this.owlentitysvg.height;
    }
};
</script>