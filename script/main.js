const stateList = Array();
var control = '';
var buildingLink = undefined;
windowResizeEvent();
animate();



document.getElementById('canvas').addEventListener('click', function(event){
    
    if(control == 'addState') {
        stateList.push(new State(event.clientX, event.clientY));
        control = '';
    }
    if(control == 'addLink') {
        let selectedState = getSelected(event);
        //console.log(selectedState)

        if(buildingLink == undefined && selectedState != null) {
            buildingLink = new MachineLink(selectedState);
            selectedState.addLink(buildingLink);
            return;
        }

        if(buildingLink.end == null && selectedState != null) {
            buildingLink.addEndState(selectedState);
        } else {
            buildingLink.addPoint(event.clientX, event.clientY);
            return;
        }
        control = '';
    }

    console.log(buildingLink);
    
});

document.getElementById('addState').addEventListener('click', function(){ control = 'addState'; });
document.getElementById('addLink').addEventListener('click', function(){ control = 'addLink'; });

window.addEventListener('resize', windowResizeEvent);



function getSelected(event) {

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