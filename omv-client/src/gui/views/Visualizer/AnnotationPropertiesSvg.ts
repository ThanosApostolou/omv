import { AnnotationProperty } from "../../../entities/AnnotationProperty";
interface Line {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}
interface Rect {
    x: number;
    y: number;
}
class Text {
    x: number;
    y: number;
    textLength: number;
}

export class AnnotationPropertySvg {
    annotationpropertiessvg: AnnotationPropertiesSvg = null;
    annotationproperty: AnnotationProperty = null;
    lines: Line[] = [];
    rect: Rect = null;
    text: Text = null;
    width: number = 0;

    create(annotationpropertiessvg: AnnotationPropertiesSvg, annotationproperty: AnnotationProperty, x: number, y: number, trailLine: boolean) {
        this.annotationpropertiessvg = annotationpropertiessvg;
        this.annotationproperty = annotationproperty;
        const line: Line = {
            x1: x,
            y1: y+annotationpropertiessvg.r,
            x2: x+annotationpropertiessvg.r,
            y2: y+annotationpropertiessvg.r
        };
        this.lines.push(line);
        this.rect = {
            x: x+annotationpropertiessvg.r,
            y: y
        };
        this.text = {
            x: x+3*annotationpropertiessvg.r + 2,
            y: y+annotationpropertiessvg.r + annotationpropertiessvg.r/2,
            textLength: this.annotationpropertiessvg.fontSize/1.7 * this.annotationproperty.label.length
        };
        if (trailLine) {
            const line: Line = {
                x1: x,
                y1: y+annotationpropertiessvg.r,
                x2: x,
                y2: y+3*annotationpropertiessvg.r
            };
            this.lines.push(line);
        }
        this.width = x + this.text.x + this.text.textLength + 2;
    }
}

export class AnnotationPropertiesSvg {
    color: string = "brown";
    r: number = 8;
    stroke: number = 1;
    fontSize: number = 12;

    list: AnnotationPropertySvg[] = [];
    totalheight: number;
    totalwidth: number;

    create(annotationproperties: AnnotationProperty[]): void{
        const x: number = 0;
        let y: number = 0;
        let count=0;
        this.totalwidth = 0;
        for (const annotationproperty of annotationproperties) {
            count++;
            let trailLine: boolean = false;
            if (count < annotationproperties.length) {
                trailLine = true;
            }
            const annotationpropertysvg: AnnotationPropertySvg = new AnnotationPropertySvg();
            annotationpropertysvg.create(this, annotationproperty, x, y, trailLine);
            this.list.push(annotationpropertysvg);
            y += 2*this.r;
            if (this.totalwidth < annotationpropertysvg.width) {
                this.totalwidth = annotationpropertysvg.width;
            }
        }
        this.totalheight = y;
    }
}