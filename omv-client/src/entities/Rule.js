class Rule {
    /** @type {String} */
    relation = "";
    /** @type {String} */
    direction = "";
    /** @type {String} */
    comments = "";
    /** @type {String} */
    similarity = "";
    /** @type {String} */
    simcomments = "";
    /** @type {Object} */
    directTransformation = null;
    /** @type {Object} */
    inverseTransformation = null;
    /** @type {Object} */
    entity1 = null;
    /** @type {Object} */
    entity2 = null;

    /** @param {Object} ruleobject
     * @returns {Rule}
     */
    static fromObject(ruleobject) {
        let rule = new Rule();
        rule.relation = ruleobject.relation;
        rule.direction = ruleobject.direction;
        rule.comments = ruleobject.comments;
        rule.similarity = ruleobject.similarity;
        rule.simcomments = ruleobject.simcomments;
        rule.directTransformation = ruleobject.directTransformation;
        rule.inverseTransformation = ruleobject.inverseTransformation;
        rule.entity1 = ruleobject.entity1;
        rule.entity2 = ruleobject.entity2;
        return rule;
    }

    /** @param {Object[]}
     * @return {Rule[]}
    */
    static listFromObject(rulesobject) {
        let rules = [];
        for (let ruleobject of rulesobject) {
            rules.push(Rule.fromObject(ruleobject));
        }
        return rules;
    }
}

export default Rule;