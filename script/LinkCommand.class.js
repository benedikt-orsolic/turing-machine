class LinkCommand {

    constructor() {
        this.wantInCell = document.getElementById("wantInCell").value;
        this.putInCell = document.getElementById("putInCell").value;
        
            
        this.direction = 'S';
        if(document.getElementById("directionLeft").checked){
            this.direction = 'L';
        }
        if(document.getElementById("directionRight").checked){
            this.direction = 'R';
        }
    }



    getWantInCell() { return this.wantInCell; }
    getPutInCell() { return this.putInCell; }
    getDirection() { return this.direction; }
}