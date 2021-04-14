class MachineLink {

    constructor(startState, command) {
        this.startState = startState;
        this.pointList = Array();
        this.pointList.push({x: startState.getX(), y: startState.getY()});

        this.command = command;
    }

    addEndState(endState){
        this.endState = endState;
        this.pointList.push({x: endState.getX(), y: endState.getY()});
    }

    addPoint(pointX, pointY) {
        this.pointList.push({x: pointX, y: pointY});
    }

    draw() {
        Canvas.context.beginPath();
        Canvas.context.moveTo(this.pointList.x, this.pointList.y);
        for(let i = 0; i < this.pointList.length; i++) {
            Canvas.context.lineTo(this.pointList[i].x, this.pointList[i].y);
        }
        
        Canvas.context.stroke();
    }
}