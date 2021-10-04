document.getElementById('canvas').addEventListener(         'click', function(event){ mouseClickedOnCanvas(event)        });
document.getElementById('canvas').addEventListener(         'mousemove', function(event){ mouseMovedOnCanvas(event)        });
document.getElementById('addState').addEventListener(       'click', function(){ control = 'addState';           });
document.getElementById('addLink').addEventListener(        'click', function(){ control = 'addLink';            });
document.getElementById('nextStep').addEventListener(       'click', function(){ machine.resolveLink();           });

window.addEventListener('resize', windowResizeEvent);