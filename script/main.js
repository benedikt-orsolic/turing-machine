const stateList = Array();
var control = '';
windowResizeEvent();
animate();



document.getElementById('canvas').addEventListener('click', function(event){
    
    if(control == 'addState') {
        ist.push(new State(event.clientX, event.clientY));
        control = '';
    }
    
});

document.getElementById('addState').addEventListener('click', function(){ control = 'addState'; });

window.addEventListener('resize', windowResizeEvent);



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