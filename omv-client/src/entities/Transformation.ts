import { OwlEntity } from "./OwlEntity";
import { Rule } from "./Rule";

export class Argvalue {
    stringvalue: string = null;
    owlentity: OwlEntity = null;
    owlposition: string = null;
    type: string = null;

    constructor(stringvalue: string) {
        this.stringvalue = stringvalue;
    }

    addEntity(owlentityroot: OwlEntity, position: string, type: string): boolean {
        const foundentity = owlentityroot.findByIri(this.stringvalue);
        if (foundentity != null) {
            this.owlentity = foundentity;
            this.owlposition = position;
            this.type = type;
            return true;
        }
        return false;
    }
}

export class Argument {
    argname: string = "";
    argvalues: Argvalue[] = [];

    static fromObject (argumentobject: any): Argument {
        const argument = new Argument();
        argument.argname = argumentobject.argname;
        if (typeof argumentobject.argvalue == "string") {
            const stringvalue: string = argumentobject.argvalue;
            argument.argvalues.push(new Argvalue(stringvalue));
        } else {
            for (const argvaluestring of argumentobject.argvalue as string) {
                const stringvalue: string = argvaluestring;
                argument.argvalues.push(new Argvalue(stringvalue));
            }
        }
        return argument;
    }

    static listFromObject (argumentobjects: any[]): Argument[] {
        const newarguments: Argument[] = [];
        for (const argumentobject of argumentobjects) {
            const argument: Argument = Argument.fromObject(argumentobject);
            newarguments.push(argument);
        }
        return newarguments;
    }
}

export class Transformation {
    rule: Rule = null;
    type: string = null;
    uri: string = null;
    description: string = null;
    arguments: Argument[] = [];

    static fromObject(transformationobject: any, type: string, rule: Rule): Transformation {
        if (transformationobject === null || transformationobject === "") {
            return null;
        } else {
            const transformation = new Transformation();
            transformation.rule = rule;
            transformation.type = type;
            transformation.uri = transformationobject.uri;
            transformation.description = transformationobject.description;
            transformation.arguments = Argument.listFromObject(transformationobject.arguments);
            transformation.findArgvaluesEntities();
            return transformation;
        }
    }

    findArgvaluesEntities() {
        const owl1classes  = this.rule.mapping.visualization.owl1.owlclasses;
        const owl1objprops  = this.rule.mapping.visualization.owl1.owlobjprops;
        const owl1dataprops  = this.rule.mapping.visualization.owl1.owldataprops;
        const owl2classes  = this.rule.mapping.visualization.owl2.owlclasses;
        const owl2objprops  = this.rule.mapping.visualization.owl2.owlobjprops;
        const owl2dataprops  = this.rule.mapping.visualization.owl2.owldataprops;
        for (const argument of this.arguments) {
            for (const argvalue of argument.argvalues) {
                if (!argvalue.addEntity(owl1classes, "left", "class")) {
                    if (!argvalue.addEntity(owl1objprops, "left", "objprop")) {
                        if (!argvalue.addEntity(owl1dataprops, "left", "dataprop")) {
                            if (!argvalue.addEntity(owl2classes, "right", "class")) {
                                if (!argvalue.addEntity(owl2objprops, "right", "objprop")) {
                                    argvalue.addEntity(owl2dataprops, "right", "dataprop");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}