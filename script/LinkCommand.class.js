class LinkCommand {

    constructor() {
        this.wantInCell = document.getElementById("wantInCell").value;
        this.putInCell = document.getElementById("putInCell").value;
        
        if(document.getElementById("directionLeft").checked){
            this.direction = 'L';
        }
        if(document.getElementById("directionRight").checked){
            this.direction = 'R';
        }
        
        this.direction = 'S';
    }



    getWantInCell() { return this.wantInCell; }
    getPutInCell() { return this.putInCell; }
    getDirection() { return this.direction; }
}