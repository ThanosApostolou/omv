<template>
    <svg>
        <svg v-for="(rulesvg, index) in rulessvg" :key="index">
            <template v-if="rulesvg.isVisible">
                <rect v-if="rulesvg.showBox" :x="rulesvg.startx" :y="rulesvg.starty" :width="rulesvg.width" :height="2*rulesvg.r" stroke="black" :stroke-width="rulesvg.stroke" :fill="rulesvg.fill" />
                <text v-if="rulesvg.showBox" :x="rulesvg.cx" :y="rulesvg.cy" :font-size="rulesvg.fontSize" dominant-baseline="middle" text-anchor="middle"> {{ rulesvg.rule.label }}</text>
                <line v-for="(line, indexx) in rulesvg.lines" :key="indexx" :x1="line.x1" :y1="line.y1" :x2="line.x2" :y2="line.y2" stroke="black" :stroke-width="lineWidth">
                    <title v-if="line.title != null">{{ line.title }}</title>
                </line>
            </template>
        </svg>
    </svg>
</template>

<script>

export default {
    name: "RulesSVGComp",
    props: {
        rulessvg: {
            type: Array,
            default: null
        }
    },
    computed: {
        lineWidth() {
            if (this.rulessvg.length > 1) {
                return "1px";
            } else {
                return "3px";
            }
        }
    }
};
</script>