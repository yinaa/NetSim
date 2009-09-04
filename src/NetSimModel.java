import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NetSimModel {
	//... Constants
	private static final String INITIAL_VALUE = "1";
	
	//... Member variable defining state of calculator.
	private BigInteger m_total;  // The total current value state.
	
	//... 
	private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();

	
	//============================================================== constructor
	/** Constructor */
	NetSimModel() {
		reset();
	}

	//=============================================================== insert node
	/** insert node */
	public void insertObject(int posX, int posY, String name) {
		for (int id=1; id<= 1000; id++){
			if (!nodeList.containsKey(id)){
				Node node = new Node(id, posX, posY, name);
				nodeList.put(id, node);
			}
		}
	}
	
	//=============================================================== remove node
	/** remove node */
	public void removeObject(int posX, int posY){
		int id = 0;
		for (Map.Entry<Integer, Node> entry : nodeList.entrySet()) {
		    Node node = entry.getValue();
			if (node.getX() == posX && node.getY() == posY)
		    	id = node.getId();
		    	nodeList.remove(id);
		    }
	}
	
	public void removeObject(int id){
		nodeList.remove(id);
	}
	
	//==================================================================== reset
	/** Reset to initial value. */
	public void reset() {
		m_total = new BigInteger(INITIAL_VALUE);
	}

	//=============================================================== multiplyBy
	/** Multiply current total by a number.
	 *@param operand Number (as string) to multiply total by.
	 */
	public void multiplyBy(String operand) {
		m_total = m_total.multiply(new BigInteger(operand));
	}

	//================================================================= setValue
	/** Set the total value. 
	 *@param value New value that should be used for the calculator total. 
	 */
	public void setValue(String value) {
		m_total = new BigInteger(value);
	}

	//================================================================= getValue
	/** Return current calculator total. */
	public String getValue() {
		return m_total.toString();
	}
}
