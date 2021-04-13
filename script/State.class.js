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

}