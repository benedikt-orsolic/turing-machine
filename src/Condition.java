
public class Condition {
	
	/**@param d where to go; -1 left, 0 stop. 1 right
	 * @param c what character is currently on line
	 * @param p what to put on line
	 */
	public Condition(int d, char c, char p) {
		direction = d;
		current = c;
		put = p;
	}
	
	public int getDireciton() {
		return direction;
	}
	
	public char getCurrent() {
		return current;
	}
	
	public char getPut() {
		return put;
	}
	
	int direction;
	char current;
	char put;
}
