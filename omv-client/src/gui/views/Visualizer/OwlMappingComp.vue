<template>
    <div>
        <svg :x="0" :y="0" :height="this.height" :width="this.width">
            <OwlEntityNodeSVG v-bind="owl1classes" key="1" @show-entity="showEntity" />
            <OwlEntityNodeSVG v-bind="owl1objprops" key="2" @show-entity="showEntity" />
            <OwlEntityNodeSVG v-bind="owl1dataprops" key="3" @show-entity="showEntity" />
            <!--<OwlEntityNodeSVG v-if="owl1objprops.ready" v-bind="owl1objprops" key="2" @show-entity="showEntity" />
            <OwlEntityNodeSVG v-if="owl1dataprops.ready" v-bind="owl1dataprops" key="3" @show-entity="showEntity" />-->
        </svg>
        <v-dialog v-if="show" v-model="show">
            <v-card>
                <span> {{ selectedOwlEntity.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import OwlEntitySVG from "./OwlEntitySVG.js";
import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";

export default {
    name: "OwlMappingComp",
    components: {
        OwlEntityNodeSVG
    },
    props: {
        owl1: {
            type: Object,
            default: null
        },
        owl2: {
            type: Object,
            default: null
        },
        mapping: {
            type: Object,
            default: null
        },
        visibilityType: {
            type: String,
            default: "AllRules"
        }
    },
    data() {
        return {
            show: false,
            selectedOwlEntity: null,
            width: 0,
            height: 0,
            owl1classes: {
                owlentitysvg: null
            },
            owl1objprops: {
                owlentitysvg: null
            },
            owl1dataprops: {
                owlentitysvg: null
            }
        };
    },
    methods: {
        showEntity(owlentity) {
            this.selectedOwlEntity = owlentity;
            this.show = true;
        }
    },
    created() {
        this.owl1classes.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owlclasses, "class", null);
        this.owl1classes.owlentitysvg.init(0, 0, this.visibilityType, false);
        this.width = this.owl1classes.owlentitysvg.width;
        this.height = this.owl1classes.owlentitysvg.height;

        this.owl1objprops.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owlobjprops, "objprop", null);
        this.owl1objprops.owlentitysvg.init(0, this.height, this.visibilityType, false);
        this.width += this.owl1objprops.owlentitysvg.width;
        this.height += this.owl1objprops.owlentitysvg.height;

        this.owl1dataprops.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owldataprops, "dataprop", null);
        this.owl1dataprops.owlentitysvg.init(0, this.height, this.visibilityType, false);
        this.width += this.owl1dataprops.owlentitysvg.width;
        this.height += this.owl1dataprops.owlentitysvg.height;
    }
};
</script>