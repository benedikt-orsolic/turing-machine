import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

@SuppressWarnings("serial")
/* TODO:    Show link lines ass they are being drawn.
 *          Add dot and arrow to link lines. 
 *          Add error check to link definition/text field.
 *          Update machine line/text on top with each iteration
 *          Change color of MachineState and Link which are being executed.
 *          
 *          Add files support. Probably use xml.
 *          Add start/stop button.
 *          Add time step adjust (delay).
 *          Make link lines join circle of MachineState, don't let them inside.
 *          Maybe add grid for drawing lines.
 *          What when link only has 2 points. GLable is set on second point.
 *          */
public class TuringMachine extends GraphicsProgram implements ProjectConstants{
	
	
	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add_button = new JButton("Add State.");
		add(add_button, SOUTH);
		
		
		add_machineStateLink_JTF = new JTextField("Add connection", 10);
		add_machineStateLink_JTF.addActionListener(this);
		add(add_machineStateLink_JTF, SOUTH);
		
		
		machineLineField = new JTextField("----------", 80);
		machineLineField.addActionListener(this);
		add( machineLineField, NORTH);
		
		machineString = "----------";
		
		
		
		addMouseListeners();
		addActionListeners();
		
		machineStateList = new ArrayList<MachineState>();
		machineStateLinkList = new ArrayList<MachineLink>();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public void run() {
	}
	
	
	
	
	
	
	
	
	
	
	
	public void mouseClicked(MouseEvent e){
		if( add_state_f ){
			add_state_f = false;
			add_state = null;
		}
		
		if( add_link_f ) {
			/* TODO get to here from JTextFrame, add lines, do a life time of debugging*/
			GObject target = getElementAt(e.getX(), e.getY());
			MachineState current_state = null;
			/*	TODO ERROR Throws scri exception	*/
			add_stateLink.addPoint( new GPoint(e.getX(), e.getY()) );
			
			if( target != null ) {
			
				/* TODO get id of first state then let user draw lines and get id of second state
				 * add start id and end id in machine state link, add a squer at end point as arow
				 * */
				/*	Target is MachineState.	*/
				Iterator<MachineState> it = machineStateList.iterator();
				while( it.hasNext() ) {
					current_state = it.next();
					if( current_state.getGCompound().equals(target) ) {
						if( add_link_start_point_f ) {
							add_stateLink.setStartID(current_state.getID());
							add_link_start_point_f = false;
						} else {
							add_stateLink.setEndID(current_state.getID());
							add_link_start_point_f = true;
							add_link_f = false;
							machineStateLinkList.add(add_stateLink);
							add_stateLink = null;
							
						}
					}
				}
			}
			
			
		}
		
		drawLines();
	}
	
	
	
	
	
	
	
	
	
	
	private void drawLines() {
		Iterator<MachineLink> it = machineStateLinkList.iterator();
		
		while( it.hasNext() ) {
			MachineLink current_stateLink = it.next();
			Iterator<GPoint> points_it = current_stateLink.getPoints();
			GPoint current = null;
			double sX, sY, eX, eY;
			if( points_it.hasNext() ) current = points_it.next();
			
			Boolean secondPoint_f = true; /*	At second point add GLable to mark connection.	*/
			while( points_it.hasNext() ) {
				
				sX = current.getX();
				sY = current.getY();
				current = points_it.next();
				eX = current.getX();
				eY = current.getY();
				if( secondPoint_f ) {
					add(new GLabel( current_stateLink.getCmd()), eX, eY);
					secondPoint_f = false;
				}
				add(new GLine(sX, sY, eX, eY));
			}
		}
		
	}
	
	
	
	
	
	
	
	
	

	public void mouseMoved(MouseEvent e) {
		if( add_state_f ) {
			add_state.getGCompound().setLocation(e.getX(), e.getY());
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		if( running ) return;
		
		if( add_button == e.getSource() && !add_state_f ) {
			/*	Initializes new MachineState object that is to be added on screen.	*/
			add_state_f = true;
			add_state = new MachineState();
			exit_stateID = add_state.getID();
			add(add_state.getGCompound(), 0, 0);
			machineStateList.add(add_state);
		}
		
		if( add_machineStateLink_JTF == e.getSource() && !add_link_f ) {
			add_link_f = true;
			add_stateLink = new MachineLink( add_machineStateLink_JTF.getText() );
		}
		
		if( machineLineField == e.getSource() ) {
			running = true;
			machineString = machineLineField.getText();
			runProgram();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		int current_ID = 0;
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
			pause(500);
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
	 * Section: Run Machine
	 * 
	 * Description: Based on lists of machine states and machine state links
	 *              runProgram and sub functions change machine state String
	 *              and update text field where initial string was entered.
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*	When machine reaches state with this ID it stops running.	*/
	int exit_stateID = 0;

	/* Defines "Add state" button and list of MachineState
	 * True until user clicks and places machine state. */
	Boolean add_state_f = false;
	JButton add_button;
	MachineState add_state;
	ArrayList<MachineState> machineStateList;
	
	/* Defines text field for MachineLink and list of them.	*/
	JTextField add_machineStateLink_JTF;
	Boolean add_link_f = false;
	Boolean add_link_start_point_f = true;
	MachineLink add_stateLink;
	ArrayList<MachineLink> machineStateLinkList;
	
	/* User enters starting characters on the line.
	 * Machine updates them. */
	JTextField machineLineField;
	Boolean running = false;
	String machineString;
}
