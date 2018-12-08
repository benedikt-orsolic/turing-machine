import java.util.ArrayList;
import java.util.Iterator;

import acm.graphics.GPoint;

public class MachineLink implements ProjectConstants{
	
	public MachineLink(String cmd){
		points = new ArrayList<GPoint>();
		command = cmd;
	}
	
	public void addPoint(GPoint point) {
		points.add( point );
	}
	
	public Iterator<GPoint> getPoints() {
		return points.iterator();
	}
	
	public String getCmd() {
		return command;
	}
	
	public void setStartID(int ID) {
		startID = ID;
	}
	
	public void setEndID(int ID) {
		endID = ID;
	}
	
	public int getStartID() {
		return startID;
	}
	
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
	String command;
	int startID;
	int endID;
}
