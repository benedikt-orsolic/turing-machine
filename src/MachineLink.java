import java.util.ArrayList;
import java.util.Iterator;

import acm.graphics.*;

public class MachineLink implements ProjectConstants{
	
	public MachineLink(String cmd){
		points = new ArrayList<GPoint>();
		lines = new ArrayList<GLine>();
		label = new GLabel(cmd);
		lastPoint = null;
		lastLine = null;
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
		double lastX = lastLine.getEndPoint().getX();
		double lastY = lastLine.getEndPoint().getY();
		lastLine = new GLine( lastX, lastY, x, y);
		if( !labelSet ) {
			label.setLocation(x, y);
			labelSet = true;
		}
		
		return lastLine;
	}
	
	public GLabel getLabel() {
		return label;
	}
	
	public Iterator<GLine> getLines(){
		return lines.iterator();
	}
	
	public GLine getLastLine(){
		return lastLine;
	}
	
	public Iterator<GPoint> getPoints() {
		return points.iterator();
	}
	
	public String getCmd() {
		return command;
	}
	
	public GLine setStart(int ID, double x, double y) {
		startID = ID;
		lastLine = new GLine(x, y, x, y);
		lines.add( lastLine );
		return lastLine;
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
	ArrayList<GPoint> points;
	ArrayList<GLine> lines;
	GPoint lastPoint;
	GLine lastLine;
	String command;
	Boolean fristPointAdded_f;
	GLabel label;
	Boolean labelSet = false;
	int startID;
	int endID;
}
