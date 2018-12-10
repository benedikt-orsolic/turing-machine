import java.util.ArrayList;
import java.util.Iterator;

import acm.graphics.*;

public class MachineLink implements ProjectConstants{
	
	public MachineLink(String cmd){
		lines = new ArrayList<GLine>();
		label = new GLabel(cmd);
		command = cmd;
		startID = -1;
		endID = -1;
	}
	
	public void moveLabel() {
		if(lines.size() >= 1) {
			label.setLocation( lines.get(0).getEndPoint() );
		}
	}
	
	public GLine addPoint(double x, double y) {
		double lastX = lines.get( lines.size()-1 ).getEndPoint().getX();
		double lastY = lines.get( lines.size()-1 ).getEndPoint().getY();
		lines.add(new GLine( lastX, lastY, x, y) );
		
		if( !labelSet ) {
			label.setLocation(x, y);
			labelSet = true;
		}

		if( lines.size() == 0 ) return null;
		return lines.get( lines.size()-1 );
	}
	
	public GLabel getLabel() {
		return label;
	}
	
	public Iterator<GLine> getLines(){
		return lines.iterator();
	}
	
	public GLine getLastLine(){

		if( lines.size() == 0 ) return null;
		return lines.get( lines.size()-1 );
	}
	
	public String getCmd() {
		return command;
	}
	
	public GLine setStart(int ID, double x, double y) {
		startID = ID;
		lines.add( new GLine(x, y, x, y) );
		
		if( lines.size() == 0 ) return null;
		return lines.get( lines.size()-1 );
	}
	
	public void setEndID(int ID) {
		endID = ID;
	}
	
	/** If returns -1 mean it's not set. */
	public int getStartID() {
		return startID;
	}
	
	/** If returns -1 mean it's not set. */
	public int getEndID() {
		return endID;
	}
	
	public char getDir() {
		//TODO should store these in separate vars and error check on input
		return command.charAt(4);
	}
	
	/* List of GPoint objects that make connection. 
	 * First and last one need to bin in MachineState.	*/
	ArrayList<GLine> lines;
	String command;
	Boolean fristPointAdded_f;
	GLabel label;
	Boolean labelSet = false;
	int startID;
	int endID;
}
