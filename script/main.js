const machine = new Machine();
var control = '';
windowResizeEvent();
animate();



function mouseClickedOnCanvas(event) {
     
    if(control == 'addState') {
        mouseClickedForAddState(event);
    }
    if(control == 'addLink') {
        mouseClickedForAddLInk(event);
    }
}



function mouseMovedOnCanvas(event) {

    if( mouseClickedForAddLInk.buildingLink !== undefined && control == 'addLink' ) {
        mouseClickedForAddLInk.buildingLink.moveLastPoint(event.clientX, event.clientY);
    }

    
    if( mouseClickedForAddState.state !== undefined && control == 'addState' ) {
        mouseClickedForAddState.state.move(event.clientX, event.clientY);
    }

}



function mouseClickedForAddState(event) {
    if( mouseClickedForAddState.state !== undefined ) {
        mouseClickedForAddState.state = undefined;
        control = '';
        return;
    }

    mouseClickedForAddState.state = new State(event.clientX, event.clientY);
    machine.addState(mouseClickedForAddState.state);

}



function mouseClickedForAddLInk(event){

    let selectedState = machine.getSelectedState(event.clientX, event.clientY);

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







function animate() {
    //if(control != ''){
    Canvas.context.clearRect(0, 0, Canvas.canvas.width, Canvas.canvas.height);
    //}
    machine.draw();
    requestAnimationFrame(animate);
}



function windowResizeEvent() {
    Canvas.canvas.height = window.innerHeight
    Canvas.canvas.width = window.innerWidth
}