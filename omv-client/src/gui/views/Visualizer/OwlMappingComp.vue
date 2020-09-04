<template>
    <div>
        <svg :x="0" :y="0" :height="this.height" :width="this.width">
            <OwlEntityTreeSVG v-if="owl1classes.ready" v-bind="owl1classes" key="1" @show-entity="showEntity" ref="owl1classesref" />
            <OwlEntityTreeSVG v-if="owl1objprops.ready" v-bind="owl1objprops" key="2" @show-entity="showEntity" ref="owl1objpropsref" />
            <OwlEntityTreeSVG v-if="owl1dataprops.ready" v-bind="owl1dataprops" key="3" @show-entity="showEntity" ref="owl1datapropsref" />
        </svg>
        <v-dialog v-if="show" v-model="show">
            <v-card>
                <span> {{ selectedOwlEntity.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import OwlEntityTreeSVG from "./OwlEntityTreeSVG.vue";

export default {
    name: "OwlMappingComp",
    components: {
        OwlEntityTreeSVG
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
        type: {
            type: String,
            default: "class"
        },
        visibilityType: {
            type: String,
            default: "AllRules"
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
            width: 0,
            height: 0,
            owl1classes: {
                ready: true,
                owlentity: this.owl1.owlclasses,
                type: "class",
                visibilityType: this.visibilityType,
                startx: 0,
                starty: 0
            },
            owl1objprops: {
                ready: true,
                owlentity: this.owl1.owlobjprops,
                type: "objprop",
                visibilityType: this.visibilityType,
                startx: 0,
                starty: 0
            },
            owl1dataprops: {
                ready: true,
                owlentity: this.owl1.owldataprops,
                type: "dataprop",
                visibilityType: this.visibilityType,
                startx: 0,
                starty: 0
            }
        };
    },
    methods: {
        showEntity(owlentity) {
            this.selectedOwlEntity = owlentity;
            this.show = true;
        }
    },
    mounted() {
        console.log("width ", this.$refs.owl1classesref.owlentitysvg.width);
        this.width = this.$refs.owl1classesref.owlentitysvg.width;
        this.height = this.$refs.owl1classesref.owlentitysvg.height;

        this.owl1objprops.starty = this.height;
        this.owl1objprops.ready = true;
        this.$forceUpdate();
        this.width += this.$refs.owl1objpropsref.owlentitysvg.width;
        this.height += this.$refs.owl1objpropsref.owlentitysvg.height;
        console.log("heihgt", this.$refs.owl1objpropsref.owlentitysvg.height);
        this.owl1dataprops.starty = this.height;
        this.owl1dataprops.ready = true;
        this.width += this.$refs.owl1datapropsref.owlentitysvg.width;
        this.height += this.$refs.owl1datapropsref.owlentitysvg.height;
    }
};
</script>