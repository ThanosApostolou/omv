<template>
    <div>
        <svg width='100' height='100'>
            <circle cx='11' cy='11' r='10' stroke='black' stroke-width='1' fill='yellow' />
            <svg width='100' height='100'>
                <circle cx='21' cy='21' r='10' stroke='black' stroke-width='1' fill='yellow' />
            </svg>
        </svg>
    </div>
</template>

<script>

export default {
    name: 'VisualizationSVG',
    props: {
        receivedResult: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            result: this.receivedResult,
        };
    }
};
</script>