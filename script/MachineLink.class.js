class MachineLink {

    constructor(startState, command) {
        this.startState = startState;
        this.pointList = Array();
        this.pointList.push({x: startState.getX(), y: startState.getY()});
        // Last point that will follow mouse
        this.pointList.push({x: this.pointList[0].x, y: this.pointList[0].y});

        this.command = command;
    }

    getEndState() { return this.endState; }
    getCommand() { return this.command; }

    addEndState(endState){
        this.endState = endState;
        this.pointList.push({x: endState.getX(), y: endState.getY()});
        this.movingPoint = undefined;
    }

    addPoint(pointX, pointY) {
        this.pointList.push({x: pointX, y: pointY});
    }

    moveLastPoint(x, y){
        if(this.endState !== undefined) return;
        this.pointList[this.pointList.length - 1].x = x;
        this.pointList[this.pointList.length - 1].y = y;
    }

    draw() {
        
        this.drawExceptLastLine();
        this.drawLastLine();
        this.drawLinkLabel();
        
    }

    drawExceptLastLine() {

        Canvas.context.beginPath();
        Canvas.context.strokeStyle = '#000000';

        Canvas.context.moveTo(this.pointList.x, this.pointList.y);

        for(let i = 0; i < this.pointList.length - 1; i++) {
            Canvas.context.lineTo(this.pointList[i].x, this.pointList[i].y);
        }
        
        Canvas.context.stroke();
    }

    drawLastLine() {
        if(this.pointList.length < 2 ) return;

        let i = this.pointList.length - 2;

        Canvas.context.beginPath();


        Canvas.context.moveTo(this.pointList[i].x, this.pointList[i].y);
        i++;
        Canvas.context.lineTo(this.pointList[i].x, this.pointList[i].y);
        

        Canvas.context.strokeStyle = '#ff0000';
        Canvas.context.stroke();
    }

    drawLinkLabel() {
        if(this.pointList.length < 2) return;
        
        Canvas.context.font = "30px Arial";
        Canvas.context.strokeStyle = '#000000';
        Canvas.context.strokeText(this.command.getWantInCell() + '/' + this.command.getPutInCell() + ' ' + this.command.getDirection(),
                                    this.pointList[1].x, this.pointList[1].y);
        
    }
}