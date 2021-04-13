document.getElementById('canvas').addEventListener(         'click', function(event){ mouseClicked(event)        });
document.getElementById('addState').addEventListener(       'click', function(){ control = 'addState';           });
document.getElementById('addLink').addEventListener(        'click', function(){ control = 'addLink';            });

window.addEventListener('resize', windowResizeEvent);