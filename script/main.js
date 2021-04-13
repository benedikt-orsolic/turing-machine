const stateList = Array();
windowResizeEvent();
animate();



document.getElementById('canvas').addEventListener('click', function(event){
    
    stateList.push(new State(event.clientX, event.clientY));
    
});
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