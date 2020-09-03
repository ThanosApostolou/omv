<template>
    <div>
        <svg :x="0" :y="0" :height="this.height" :width="this.width">
            <OwlEntityTreeSVG :owlentity="owlentity" :type="type" @show-entity="showEntity" ref="owlentitysvgref" />
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
    name: "OwlEntityComp",
    components: {
        OwlEntityTreeSVG
    },
    props: {
        owlentity: {
            type: Object,
            default: null
        },
        type: {
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
    mounted() {
        console.log("width ", this.$refs.owlentitysvgref.owlentitysvg.width);
        this.width = this.$refs.owlentitysvgref.owlentitysvg.width;
        this.height = this.$refs.owlentitysvgref.owlentitysvg.height;
    }
};
</script>