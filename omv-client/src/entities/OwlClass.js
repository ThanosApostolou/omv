import Annotation from './Annotation.js';

class OwlClass {
    /** @type {String} */
    iri;
    /** @type {String} */
    name;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlClass[]} */
    children;

    // For SVG creation
    /** @type {Boolean} */
    visible;
    /** @type {Number} */
    height;
    /** @type {Number} */
    width;
    /** @type {Number} */
    r;
    /** @type {Number} */
    startx;
    /** @type {Number} */
    starty;
    /** @type {Number} */
    cx;
    /** @type {Number} */
    cy;
    /** @type {Number} */
    textx;
    /** @type {Number} */
    texty;

    /** @param {Number} x
     *  @param {Number} y
     *  @param {Number} r
     *  @returns {Number}
     */
    calcPositions(x) {
        this.r = 5;
        this.startx=x;
        this.starty = 0;
        this.cx=this.startx + this.r;
        this.cy = this.r;
        this.height = this.cy + this.r;
        this.width = this.cx + this.r;
        this.textx = 2+this.cx + this.r;
        this.texty = this.cy + this.r/2;
        let nextx = this.startx + 2*this.r;
        for (let child of this.children) {
            child.calcPositions(nextx);
        }
    }

    /** @param {object} owlclassobject
     *  @returns {OwlClass}
     */
    static fromObject(owlclassobject) {
        let owlclass = new OwlClass();
        owlclass.iri = owlclassobject.iri;
        owlclass.name = owlclassobject.inameri;
        owlclass.label = owlclassobject.label;
        owlclass.annotations = Annotation.listFromObject(owlclassobject.annotations);
        owlclass.children = [];
        for (let child of owlclassobject.children) {
            owlclass.children.push(OwlClass.fromObject(child));
        }
        return owlclass;
    }
}

export default OwlClass;