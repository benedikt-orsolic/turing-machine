const stateList = Array();
var control = '';
var buildingLink = {
    start: null,
    end: null
};
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
        if(selectedState == null) return;

        if(buildingLink.start == null) {
            buildingLink.start = selectedState;
            return;
        }

        if(buildingLink.end == null) {
            buildingLink.end = selectedState;
        }

        buildingLink.start.addLink(buildingLink.end);
        control = '';
    }

    console.log(stateList)
    
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