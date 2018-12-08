import acm.graphics.*;

/** Defines GCompund to be drawn on canvas.
 * 	Stores ID of state. */
public class MachineState implements ProjectConstants{
	private static int NEXT_ID = 0;
	
	public MachineState(){
		my_id = NEXT_ID;
		NEXT_ID++;
		
		/* Create GCompund*/
		visual = new GCompound();
		GOval circle = new GOval(MACHINE_STATE_RADIUS, MACHINE_STATE_RADIUS);
		visual.add(circle, 0, 0);
		
		GLabel label = new GLabel(Integer.toString(my_id));
		double labelY = ( MACHINE_STATE_RADIUS*2 - label.getHeight()) / 2;
		double labelX = MACHINE_STATE_RADIUS/2 - label.getWidth()/2;
		visual.add(label, labelX, labelY );
		
		
	}
	public int getID() {
		return my_id;
	}
	
	public GCompound getGCompound() {
		return visual;
	}

	private GCompound visual;
	private int my_id;
}
