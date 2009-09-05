import java.math.BigInteger;
import java.util.HashMap;

public class NetSimModel {
	//... Constants
	private static final String INITIAL_VALUE = "1";
	
	//... Member variable defining state of calculator.
	private BigInteger m_total;  // The total current value state.
	
	//... 
	private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> transList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> appList = new HashMap<Integer, Node>();

	
	//============================================================== constructor
	/** Constructor */
	NetSimModel() {
		reset();
	}

	//=============================================================== insert node
	/** insert node */
	public int insertObject(int posX, int posY, String name) {
		int id = -1;
		if (name == "node"){
			for (id=1; id<= 1000; id++){
				if (!nodeList.containsKey(id)){
					Node node = new Node(id, posX, posY, name);
					nodeList.put(id, node);
					break;
				}
			}			
		}
		else if (name == "trans"){
			for (id=1; id<= 1000; id++){
				if (!transList.containsKey(id)){
					Node node = new Node(id, posX, posY, name);
					transList.put(id, node);
					break;
				}
			}			
		}
		else if (name == "app"){
			for (id=1; id<= 1000; id++){
				if (!appList.containsKey(id)){
					Node node = new Node(id, posX, posY, name);
					appList.put(id, node);
					break;
				}
			}			
		}		
		return id;
	}
	
	//=============================================================== remove node
	/** remove node */
	public void removeObject(String type, int id){
		if (type.compareTo("node")==0){
			nodeList.remove(id);
		}
		else if (type.compareTo("trans")==0){
			transList.remove(id);
		}
		else if (type.compareTo("app")==0){
			appList.remove(id);
		}
	}

	//=========================================================== update position 
	public void updatePosition(String type, int id, int posX, int posY) {
		if (type.compareTo("node")==0){
			Node node = nodeList.get(id);
			node.setPosition(posX, posY);
		}
		else if (type.compareTo("trans")==0){
			Node trans = transList.get(id);
			trans.setPosition(posX, posY);
		}
		else if (type.compareTo("app")==0){
			Node app = appList.get(id);
			app.setPosition(posX, posY);
		}		
	}
	
	//==================================================================== reset
	/** Reset to initial value. */
	public void reset() {
		m_total = new BigInteger(INITIAL_VALUE);
		nodeList.clear();
		transList.clear();
		appList.clear();
	}

	//This belongs to example we use to guide our MVC
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
