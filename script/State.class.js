class State {

    static radius = 15;
    static startAngel = 0;
    static endAngle = 2*Math.PI;


    constructor (centerX, centerY) {
        this.machineLinks = Array();
        this.centerX = centerX;
        this.centerY = centerY;
        this.label = 'new Machine State';
    }



    draw(){

        Canvas.context.beginPath();
        Canvas.context.arc(this.centerX, this.centerY, State.radius, State.startAngel, State.endAngle);
        Canvas.context.stroke();
    }

    addLink(link) {
        this.machineLinks.push(link);
    }

    getX() {
        return this.centerX;
    }

    getY() {
        return this.centerY;
    }

}