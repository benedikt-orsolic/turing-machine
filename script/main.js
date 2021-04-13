const canvas = document.getElementById('canvas');
const context = canvas.getContext('2d');

windowResizeEvent()


document.getElementById('canvas').addEventListener('click', function(event){

});
window.addEventListener('resize', windowResizeEvent)

function windowResizeEvent() {
    canvas.height = window.innerHeight
    canvas.width = window.innerWidth
}