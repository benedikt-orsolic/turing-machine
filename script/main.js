const canvas = document.getElementById('canvas');
const context = canvas.getContext('2d');

const stateList = Array();

windowResizeEvent()


document.getElementById('canvas').addEventListener('click', function(event){
    stateList.push(new State(event.clientX, event.clientY));
    console.log(stateList)
});
window.addEventListener('resize', windowResizeEvent)

function windowResizeEvent() {
    canvas.height = window.innerHeight
    canvas.width = window.innerWidth
}