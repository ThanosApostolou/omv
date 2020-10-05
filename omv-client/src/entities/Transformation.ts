import { OwlEntity } from "./OwlEntity";

export class Argument {
    argname: string = "";
    argvalues: string[] = [];
    argentity: OwlEntity = null;

    static fromObject (argumentobject: any): Argument {
        const argument = new Argument();
        argument.argname = argumentobject.argname;
        if (typeof argumentobject.argvalue == "string") {
            argument.argvalues.push(argumentobject.argvalue);
        } else {
            argument.argvalues = argumentobject.argvalue;
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
    type: string = null;
    uri: string = null;
    description: string = null;
    arguments: any[] = [];

    static fromObject(transformationobject: any, type: string): Transformation {
        if (transformationobject === null || transformationobject === "") {
            return null;
        } else {
            const transformation = new Transformation();
            transformation.type = type;
            transformation.uri = transformationobject.uri;
            transformation.description = transformationobject.description;
            transformation.arguments = Argument.listFromObject(transformationobject.arguments);
            return transformation;
        }
    }

}