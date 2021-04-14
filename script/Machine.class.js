class Machine{
    stateList = Array();
    machineTapeStrLeft = '';
    machineTapeStrRight = '';
    currentTapeCell = 'a';

    addState(newState) {
        this.stateList.push(newState);
    }

    draw() {
        this.stateList.forEach(function(state){
            state.draw();
        });
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