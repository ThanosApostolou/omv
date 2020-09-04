<template>
    <svg>
        <svg v-for="(rulesvg, index) in rulessvg" :key="index">
            <rect :x="rulesvg.startx" :y="rulesvg.starty" :width="4*rulesvg.r" :height="2*rulesvg.r" stroke="black" :stroke-width="rulesvg.stroke" :fill="rulesvg.fill" />
        </svg>
    </svg>
</template>

<script>
import { RuleSVG } from "./RuleSVG.js";

export default {
    name: "RulesSVGComp",
    props: {
        rulessvg: {
            type: [Array],
            default: null
        }
    }
};
</script>