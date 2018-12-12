import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

@SuppressWarnings("serial")
/* BUG:     Something strange with machine line on top
 * 
 * TODO:    Add dot and arrow to link lines. 
 *          Add error check to link definition/text/cmd field.
 *          
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
 *          Make link lines join circle of MachineState, don't let them inside.
 *          Maybe add grid for drawing lines.
 *          What when link only has 2 points. GLable is set on second point.
 *          
 *          Add multiple end states?
 *          
 *          */
public class TuringMachine extends GraphicsProgram implements ProjectConstants, Runnable{
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Program initialization
	 * 
	 * Description: \
	 * 
	 * Note: Program is all event driven 
	 *       except init() so run stays here.
	 *              
	 * 
	 **************************************************************************/
	
	
	
	public static void main(String[] args) {
		new TuringMachine().start();
	}
	
	
	
	public void init() {
		
		
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		
		
		add_button = new JButton("Add State.");
		removeAll_button = new JButton("Remove all");
		start_button = new JButton("START");
		pause_button = new JButton("Pause");
		goToEnd_button= new JButton("Finish");
		nextStep_button = new JButton("Next step");
		
		add_machineStateLink_JTF = new JTextField("a,b,R", 10);
		add_machineStateLink_JTF.addActionListener(this);
		
		increment_input_JTF = new JTextField(4);
		increment_input_JTF.addActionListener(this);
		
		machineLineLeft = new JTextField("", 40);
		machineLineLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		machineLineCurrent = new JTextField("", 1);
		machineLineRight = new JTextField("", 40);
		
		

		add( add_button, SOUTH);
		add( removeAll_button, SOUTH);
		add( start_button, SOUTH );
		add( pause_button, SOUTH );
		add( goToEnd_button, SOUTH );
		add( nextStep_button, SOUTH );
		
		add( add_machineStateLink_JTF, SOUTH);
		
		add( new JLabel("Time step:"), SOUTH);
		add( increment_input_JTF, SOUTH);
		add( new JLabel("ms"), SOUTH);
		
		add( machineLineLeft, NORTH);
		add( machineLineCurrent, NORTH);
		add( machineLineRight, NORTH);
		
		
		
		setRunning( false );
		
		
		
		
		/* Limit JTF for current character to be 1 long. */
		machineLineCurrent.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (machineLineCurrent.getText().length() >= 1 )
		            e.consume(); 
		    }  
		});
		
		
		
		
		addMouseListeners();
		addActionListeners();
		
		
		
		machineString = "";
		
		
		
		machineStateList = new ArrayList<MachineState>();
		machineStateLinkList = new ArrayList<MachineLink>();
		
		
	}
	
	
	
	public void run() {
		/* Nothing to do. */
	}
	
	
	
	/***************************************************************************
	 * 
	 * Section: END Program initialization
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Interrupt functions
	 * 
	 * Description: \
	 *              
	 * 
	 **************************************************************************/
	
	
	
	
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
			add_state.getGCompound().setLocation(e.getX() - add_state.getGCompound().getWidth()/2, 
					                             e.getY() - add_state.getGCompound().getHeight()/2);
		}
		
		
		/* Last link line follows the mouse. */
		if( add_link != null ) {
			add_link.moveLabel();	// Only sets label to second place where mouse clicked.

			GLine line = add_link.getLastLine();
			if( line != null ) line.setEndPoint(e.getX(), e.getY());
		}
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		if( nextStep_button == e.getSource() ) nextStep_b = true;
		
		if( pause_button == e.getSource() && running ) pause_Button();
		
		/* Starts/Stops executing program drawn on screen. */
		if( start_button == e.getSource() ) 
			setRunning( !running && start_button.getActionCommand().equals("START"));
		
		
		if( goToEnd_button == e.getSource() ) delay = 0;
		
		if( increment_input_JTF == e.getSource() ) incrementInput();
		
		
		/*	Initializes new MachineState object that is to be added on screen.	*/
		if( add_button == e.getSource() && !add_state_f ) add_button_pressed();
		
		/*	Initializes new MachineLink object that is to be added on screen.	*/
		if( add_machineStateLink_JTF == e.getSource() && add_link == null ) {
			add_link = new MachineLink( add_machineStateLink_JTF.getText() );
		}
		
		
		
		/* Remove all objects from screen. */
		if( removeAll_button == e.getSource() ) removeAll_button_pressed();
	}
	
	
	
	/***************************************************************************
	 * 
	 * Section: END Interrupt functions
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Button interaction functions
	 * 
	 * Description: \
	 *              
	 * 
	 **************************************************************************/
	

	
	private void pause_Button() {
		
		if( runProgram_paused ) {
			runProgram_paused = false;
			pause_button.setText("Pause");
		} else {
			runProgram_paused = true;
			pause_button.setText("Resume");
		}
	}
	
	
	
	private void incrementInput() {
		String str = increment_input_JTF.getText();
		for(int i = 0; i < str.length(); i++ ) {
			if( str.charAt(i) < '0' || str.charAt(i) > '9' ) {
				increment_input_JTF.setText("\\");
				return;
			}
		}
		delay = 0;
		for(int i = 0; i < str.length(); i++ ) {
			delay *= 10;
			delay += str.charAt(i)-'0';
		}
		System.out.println(delay);
	}
	
	
	
	private void add_button_pressed() {
			add_state_f = true;
			add_state = new MachineState();
			exit_stateID = add_state.getID();
			add(add_state.getGCompound(), 0, 0);
			machineStateList.add(add_state);
	}
	
	
	
	private void removeAll_button_pressed() {
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
	
	
	
	/* If true changes Start to stop and other way around. */
	private void setRunning(boolean b) {
		
		running = b;
		
		/* Machine running interaction. */
		pause_button.setEnabled(b);
		goToEnd_button.setEnabled(b);
		nextStep_button.setEnabled(b);
		
		/* Machine is being edited if b is false. */
		add_button.setEnabled(!b);
		add_button.setEnabled(!b);
		add_machineStateLink_JTF.setEnabled(!b);
		removeAll_button.setEnabled(!b);
		
		
		if(b) {
			start_button.setText("Stop");
			
			current_stateID = start_stateID;
			
			machineString = machineLineLeft.getText() + 
					        machineLineCurrent.getText() +
					        machineLineRight.getText();
			
			
			T_thread = new Thread()   {
				public void run() {
					runProgram( machineLineLeft.getText().length() );
				}
			};
			T_thread.start();
			
		}else {
			start_button.setText("START");
			while( T_thread != null && T_thread.isAlive() ) ;
			T_thread = null;
		}
		
	}
	
	
	
	/***************************************************************************
	 * 
	 * Section: END Button interaction functions
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	
	
	private void runProgram( int index) {

		running = true;
		
		/* Tracks what is current state
		 * That is starting ID of link to be executed. */
		MachineLink link = null;
		
		while( current_stateID != exit_stateID ) {
			
			link = findCurrentLink(index, current_stateID);
			/* TODO Maybe add fancy error for link not found. */
			if( link == null ) {
				System.out.println("DEBUG");
				break;
				
			}
			
			
			current_stateID = link.getEndID();
			
			
			/* Highlight current step. */
			setLinkColor(Color.RED, link);
			setStateColor(Color.RED, link.getStartID());
			
			
			
			if( delay != 0 ) pause(delay);
			else while( !nextStep_b ) pause(10);
			nextStep_b = false;
			
			
			updateMachineString(index, link);
			updateMachineLine(index);
			index = updateMachineIndex(index, link.getDir());
			
			setLinkColor(Color.BLACK, link);
			setStateColor(Color.BLACK, link.getStartID());
			
			
			/* Hang until resumed. */
			while( runProgram_paused && running ) pause(10);
			if( !running ) break;
			
			
		}
		
		
		
		running = false;
	}
	
	
	
	/* Sets color for current MachineState. */
	private void setStateColor(Color color, int current_stateID) {
		Iterator<MachineState> it = machineStateList.iterator();
		while( it.hasNext() ) {
			MachineState state = it.next();
			if( state.getID() == current_stateID ) {
				state.getGCompound().setColor( color );
				return;
			}
		}
	
	}
	
	
	
	
	
	
	/* Sets color for current MachineLink. */
	private void setLinkColor(Color color, MachineLink link) {
		Iterator<GLine> lines = link.getLines();
		while( lines.hasNext() ) {
			lines.next().setColor(color);
		}
		
	}
	
	
	
	/* */
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
	
	
	
	private void updateMachineLine(int index) {
		
		
		machineLineLeft.setText( machineString.substring(0, index) );
		machineLineCurrent.setText( machineString.substring(index, index+1) );
		machineLineRight.setText( machineString.substring(index+1, machineString.length()) );
	}
	
	
	
	private void updateMachineString(int index, MachineLink link) {

		machineString = machineString.substring(0, index) + 
		                link.getCmd().charAt(2) +
		                machineString.substring(index+1, machineString.length());
	}
	
	
	
	/***************************************************************************
	 * 
	 * Section: End Run Machine
	 * 
	 **************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Section: Instance variables
	 * 
	 * Description: /
	 * 
	 **************************************************************************/
	
	
	
	/*	When machine reaches state with this ID it stops running.	*/
	int exit_stateID = 0;
	int start_stateID = 0;
	int current_stateID;
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
	
	
	
	/* Input for machine */
	JTextField machineLineLeft;
	JTextField machineLineCurrent;
	JTextField machineLineRight;
	
	
	
	/* Disables adding new states and links. */
	Boolean running = false;
	Boolean runProgram_paused = false;
	
	
	
	/* Tracks machine input. */
	String machineString;
	
	
	
	/* For GUI. */
	JButton removeAll_button;
	JButton start_button;
	JButton pause_button;
	JButton goToEnd_button;
	JButton nextStep_button;
	JTextField increment_input_JTF;
	
	
	
	boolean nextStep_b;
	
	Thread T_thread;
	
	
	
	/***************************************************************************
	 * 
	 * Section: END Instance variables
	 * 
	 **************************************************************************/
	
}
