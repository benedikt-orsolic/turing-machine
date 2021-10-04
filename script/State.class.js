class State {

    static radius = 15;
    static startAngel = 0;
    static endAngle = 2*Math.PI;
    static id = 0;


    constructor (centerX, centerY) {
        this.machineLinks = Array();
        this.centerX = centerX;
        this.centerY = centerY;
        this.label = 'new Machine State';
        this.id = State.id++;
    }



    move(x, y) {
        if(this.machineLinks[0] !== undefined) return;
        this.centerX = x;
        this.centerY = y;
    }



    draw(){

        Canvas.context.beginPath();
        Canvas.context.arc(this.centerX, this.centerY, State.radius, State.startAngel, State.endAngle);

        Canvas.context.strokeStyle = '#000000';
        Canvas.context.stroke();

        this.machineLinks.forEach(function(link){
            link.draw();
        });
    }

    addLink(link) {
        this.machineLinks.push(link);
    }

    getActivatedLink(currentCharInCell) {

        for(let i=0; i < this.machineLinks.length; i++) {
            let link = this.machineLinks[i];

            if( link.getCommand().getWantInCell() == currentCharInCell) return link;
        }
        
        return null;
    }

    getX() {
        return this.centerX;
    }

    getY() {
        return this.centerY;
    }

}