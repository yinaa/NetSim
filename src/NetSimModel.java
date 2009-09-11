import java.util.HashMap;

public class NetSimModel {

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
		nodeList.clear();
		transList.clear();
		appList.clear();
	}
}
