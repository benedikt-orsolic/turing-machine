import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

@SuppressWarnings("serial")
/* TODO:    Add dot and arrow to link lines. 
 *          Add error check to link definition/text/cmd field.
 *          Change color of MachineState and Link which are being executed.
 *          Allow for start point to be else where, not only on index 0
 *          Allow time interval change in runProgram
 *          Allow skip to end.
 *          
 *          
 *          
 *          
 *          No clue what??
 *              Add a label to display machineLine, and some other prompt for setting
 *              initial state.
 *          
 *          
 *          
 *          Add files support. Probably use xml.
 *          Add start/stop button.
 *          Add time step adjust (delay).
 *          Make link lines join circle of MachineState, don't let them inside.
 *          Maybe add grid for drawing lines.
 *          What when link only has 2 points. GLable is set on second point.
 *          */
public class TuringMachine extends GraphicsProgram implements ProjectConstants{
	
	public static void main(String[] args) {
		new TuringMachine().start();
	}
	public void init() {
		
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		
		add_button = new JButton("Add State.");
		removeAll = new JButton("Remove all");
		
		add_machineStateLink_JTF = new JTextField("Add connection", 10);
		add_machineStateLink_JTF.addActionListener(this);
		
		
		machineLineField = new JTextField("----------", 80);
		machineLineField.addActionListener(this);
		machineString = "----------";

		
		
		add( machineLineField, NORTH);

		add(add_button, SOUTH);
		add(removeAll, SOUTH);
		add(add_machineStateLink_JTF, SOUTH);
		
		
		
		addMouseListeners();
		addActionListeners();
		
		machineStateList = new ArrayList<MachineState>();
		machineStateLinkList = new ArrayList<MachineLink>();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public void run() {
		/* Nothing to do. */
	}
	
	
	
	
	
	
	
	
	
	
	
	public void mouseClicked(MouseEvent e){
		/* I have no clue what it does. 
		 * Maybe: when button pressed move machine state on screen and when
		 * mouse clicked then just leave it there. */
		if( add_state_f ){
			add_state_f = false;
			add_state = null;
		}
		
		
		if( add_link != null ) {
			addLink( e.getX(), e.getY() );
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void mouseMoved(MouseEvent e) {
		
		/* Machine state follows the mouse movement. */
		if( add_state_f ) {
			add_state.getGCompound().setLocation(e.getX() - 
					       add_state.getGCompound().getWidth()/2, 
					       e.getY() - add_state.getGCompound().getHeight()/2);
		}
		
		/* Last link line follows the mouse. */
		if( add_link != null ) {
			GLine line = add_link.getLastLine();
			// Only sets label to second place where mouse clicked.
			add_link.moveLabel();
			if( line != null ) {
				line.setEndPoint(e.getX(), e.getY());
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		/* Disable buttons and text fields while machine is running.
		 * So user doesn't add extra states in mean time. */
		if( running ) return;
		
		/*	Initializes new MachineState object that is to be added on screen.	*/
		if( add_button == e.getSource() && !add_state_f ) {
			add_state_f = true;
			add_state = new MachineState();
			exit_stateID = add_state.getID();
			add(add_state.getGCompound(), 0, 0);
			machineStateList.add(add_state);
		}
		
		/*	Initializes new MachineLink object that is to be added on screen.	*/
		if( add_machineStateLink_JTF == e.getSource() && add_link == null ) {
			add_link = new MachineLink( add_machineStateLink_JTF.getText() );
		}
		
		/* Starts executing program drawn on screen. */
		if( machineLineField == e.getSource() ) {
			running = true;
			machineString = machineLineField.getText();
			new Thread(() -> {
			    runProgram();
			}).start();
		}
		
		/* Remove all objects from screen. */
		if( removeAll == e.getSource() ) {
			Iterator<MachineState> it = machineStateList.iterator();
			while(it.hasNext()) {
				remove( it.next().getGCompound() );
			}
			
			Iterator<MachineLink> link_it = machineStateLinkList.iterator();
			while(link_it.hasNext()){
				MachineLink link = link_it.next();
				Iterator<GLine> lines = link.getLines();
				while(lines.hasNext()) {
					remove(lines.next());
				}
				remove(link.getLabel());
			}
			
			start_stateID = exit_stateID + 1;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Drawing functions
	 * 
	 * Description: Set of functions used to handle drawing on screen.
	 *              
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	/* Function uses x and y as coordinates where is the mouse. 
	 * Adds link from user clicked on MachinState until user clicks on 
	 * object representing machine state again. */
	private void addLink(double x, double y) {

		
		if( add_link.getStartID() != -1 ){
			add( add_link.addPoint( x, y ) );
		}
		
		if( add_link != null && add_link.getLabel() != null ) {
			add( add_link.getLabel() ); 
		}
		
		// Adds end points only if on MachinState
		addEndPoints(x, y);
	}
	
	
	
	/* Receives x, y, coordinates where mouse was clicked.
	 * If any MachinState contains those it's end point. */
	private void addEndPoints(double x, double y) {
		
		Iterator<MachineState> it = machineStateList.iterator();
		MachineState state = null;
		
		while( it.hasNext() ) {
			state = it.next();
			if( state.getGCompound().contains(x,y) ) {
//				// TODO should set line on MACHINE_STATE_RADIUS from
//				// machine state center point.
//				double dX = x - (state.getGCompound().getX() + MACHINE_STATE_RADIUS);
//				double dY = y - (state.getGCompound().getY() + MACHINE_STATE_RADIUS);
//				double r = Math.sqrt(dX*dX + dY*dY);
//				
//				x = (MACHINE_STATE_RADIUS/r * dX) + (state.getGCompound().getX() + MACHINE_STATE_RADIUS);
//				//y = (MACHINE_STATE_RADIUS/r * dY) + (state.getGCompound().getY() + MACHINE_STATE_RADIUS);
//				y = Math.sqrt(MACHINE_STATE_RADIUS*MACHINE_STATE_RADIUS - dX*dX)+ (state.getGCompound().getX() + MACHINE_STATE_RADIUS);
				if( add_link != null && add_link.getStartID() == -1 ) {
					add( add_link.setStart(state.getID(), x, y));
				} else {
					add_link.setEndID(state.getID());
					//add_link.getLastLine().setEndPoint(x, y);
					machineStateLinkList.add(add_link);
					add_link = null;
					
				}
			}
		}
	}
	
	
	
//	private void drawLines() {
//		Iterator<MachineLink> it = machineStateLinkList.iterator();
//		while(it.hasNext()) {
//			
//			MachineLink link = it.next();
//			Iterator<GLine> lines = link.getLines();
//			if( lines.hasNext() ) {
//				GLine line = lines.next();
//				add(line);
//				add(new GLabel(link.getCmd()), line.getEndPoint());
//			}
//			while( lines.hasNext() ) {
//				add( lines.next() );
//			}
//		}
////		Iterator<MachineLink> it = machineStateLinkList.iterator();
////		
////		while( it.hasNext() ) {
////			MachineLink current_stateLink = it.next();
////			Iterator<GPoint> points_it = current_stateLink.getPoints();
////			GPoint current = null;
////			double sX, sY, eX, eY;
////			if( points_it.hasNext() ) current = points_it.next();
////			
////			Boolean secondPoint_f = true; /*	At second point add GLable to mark connection.	*/
////			while( points_it.hasNext() ) {
////				
////				sX = current.getX();
////				sY = current.getY();
////				current = points_it.next();
////				eX = current.getX();
////				eY = current.getY();
////				if( secondPoint_f ) {
////					add(new GLabel( current_stateLink.getCmd()), eX, eY);
////					secondPoint_f = false;
////				}
////				add(new GLine(sX, sY, eX, eY));
////			}
////		}
//		
//	}
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: End Drawing functions
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Run Machine
	 * 
	 * Description: Based on lists of machine states and machine state links
	 *              runProgram and sub functions change machine state String
	 *              and update text field where initial string was entered.
	 * 
	 **************************************************************************/
	private void runProgram() {
		
		/* Tracks what is current state
		 * That is starting ID of link to be executed. */
		int current_ID = start_stateID;
		/* Index where machine is currently looking on machine line. */
		int index = 0;
		MachineLink link = null;
		
		while( current_ID != exit_stateID ) {
			
			link = findCurrentLink(index, current_ID);
			/* TODO Maybe add fancy error for link not found. */
			if( link == null ) break; 
			
			updateMachineLine(index, link);
			index = updateMachineIndex(index, link.getDir());
			
			current_ID = link.getEndID();
//			System.out.println("DEBUG___\\__dir: " + link.getDir() + "__\\" + 
//			           machineString.substring(0, index) + "_" +
//			           link.getCmd().charAt(2) + "_" +
//			           machineString.substring(index+1, machineString.length()));
			//System.out.println(machineString);
			machineLineField.setText(machineString);
			machineLineField.validate();
			
			System.out.println(machineString);
			pause(delay);
		}
		
		
		
		running = false;
	}
	
	
	
	private int updateMachineIndex(int index, char dir) {
		if( dir == 'L' ) {
			if(index == 0) machineString = "-" + machineString;
			else index--;
		}
		
		if( dir == 'R' ) {
			index++;
			if ( index == machineString.length()) {
				machineString = machineString + "-";
			}
		}
		return index;
	}
	
	
	
	/* Return MachineLink what has ID as startID
	 * and same char at index and in cmd. */
	private MachineLink findCurrentLink(int index, int ID) {
		Iterator<MachineLink> it = machineStateLinkList.iterator();
		MachineLink link = null;
		while( it.hasNext() ) {
			link = it.next();
			if( link.getStartID() == ID && 
				link.getCmd().charAt(0) == machineString.charAt(index)) {

				return link;
			}
		}
		return null;
	}
	
	
	
	private void updateMachineLine(int index, MachineLink link) {
		
		machineString = machineString.substring(0, index) + 
		                link.getCmd().charAt(2) +
		                machineString.substring(index+1, machineString.length());
	}
	/***************************************************************************
	 * 
	 * Section: End Run Machine
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*	When machine reaches state with this ID it stops running.	*/
	int exit_stateID = 0;
	int start_stateID = 0;
	int delay = 1000; // Pause between switching states.

	/* Defines "Add state" button and list of MachineState
	 * True until user clicks and places machine state. */
	Boolean add_state_f = false;
	JButton add_button;
	MachineState add_state;
	ArrayList<MachineState> machineStateList;
	
	/* Defines text field for MachineLink and list of them.	*/
	JTextField add_machineStateLink_JTF;
	MachineLink add_link;
	ArrayList<MachineLink> machineStateLinkList;
	
	/* User enters starting characters on the line.
	 * Machine updates them. */
	JTextField machineLineField;
	Boolean running = false;
	String machineString;
	
	JButton removeAll;
	
}
