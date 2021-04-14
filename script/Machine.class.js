class Machine{
    stateList = Array();
    machineTapeStrLeft = '';
    machineTapeStrRight = '';
    currentTapeCell = 'a';

    addState(newState) {
        this.stateList.push(newState);
    }

    resolveLink() {
        let activatedLink = this.currentState.getActivatedLink(this.currentTapeCell);
        let command = activatedLink.getCommand();

        this.currentTapeCell = command.getPutInCell();
        if(command.getDirection() == 'L'){

            if(this.machineTapeStrLeft == '') this.machineTapeStrLeft = 'a';
            this.machineTapeStrRight = this.currentTapeCell + this.machineTapeStrRight;
            this.currentTapeCell = this.machineTapeStrLeft.substring(this.machineTapeStrLeft.length - 1, this.machineTapeStrLeft.length);
            this.machineTapeStrLeft = this.machineTapeStrLeft.substring(this.machineTapeStrLeft.length - 1);
        }

        if(command.getDirection() == 'R'){
            
            if(this.machineTapeStrRight == '') this.machineTapeStrRight = 'a';
            this.machineTapeStrLeft = this.machineTapeStrLeft + this.currentTapeCell;
            this.currentTapeCell = this.machineTapeStrRight.substring(0, 1);
            this.machineTapeStrRight = this.machineTapeStrRight.substring(1, this.machineTapeStrLeft.length);
        }

        this.currentState = activatedLink.getEndState();
        this.updateMachineTape();
    }

    draw() {

        

        this.stateList.forEach(function(state){
            state.draw();
        });
    }

    updateMachineTape() {
        document.getElementById('machineTapeLeft').value = this.machineTapeStrLeft;
        document.getElementById('machineTapeRight').value = this.machineTapeStrRight;
        document.getElementById('machineTapeCurrentCell').value = this.currentTapeCell;
    }

    getSelectedState(x, y) {
    
        for(let i = 0; i < this.stateList.length; i++){
    
            let state = this.stateList[i];
    
            if( Math.abs(state.getY() - y) <= State.radius &&
                Math.abs(state.getX() - x) <= State.radius) {
                return state;
            }
        }
    }
}   