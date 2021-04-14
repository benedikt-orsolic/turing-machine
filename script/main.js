const stateList = Array();
var control = '';
windowResizeEvent();
animate();



function mouseClicked(event) {
    if(control == 'addState') {
        stateList.push(new State(event.clientX, event.clientY));
        control = '';
    }
    if(control == 'addLink') {
        mouseClickedForAddLInk(event);
    }
}



function mouseClickedForAddLInk(event){

    
    

    let selectedState = getSelectedState(event);

    if(mouseClickedForAddLInk.buildingLink == undefined && selectedState != null) {
        mouseClickedForAddLInk.buildingLink = new MachineLink(selectedState, new LinkCommand());
        selectedState.addLink(mouseClickedForAddLInk.buildingLink);
        return;
    }

    if(mouseClickedForAddLInk.buildingLink.end == null && selectedState != null) {
        mouseClickedForAddLInk.buildingLink.addEndState(selectedState);
    } else {
        mouseClickedForAddLInk.buildingLink.addPoint(event.clientX, event.clientY);
        return;
    }
    
    mouseClickedForAddLInk.buildingLink = undefined;
    control = '';
}



function getSelectedState(event) {

    let x = event.clientX;
    let y = event.clientY;

    for(let i = 0; i < stateList.length; i++){

        state = stateList[i];

        if( Math.abs(state.getY() - y) <= State.radius &&
            Math.abs(state.getX() - x) <= State.radius) {
            return state;
        }
    }
}



function animate() {

    stateList.forEach(function(state){
        state.draw();
    });


    requestAnimationFrame(animate);
}



function windowResizeEvent() {
    Canvas.canvas.height = window.innerHeight
    Canvas.canvas.width = window.innerWidth
}