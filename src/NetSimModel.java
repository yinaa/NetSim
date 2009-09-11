import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

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

	//================================================================open
	/** Open saved file. */
	@SuppressWarnings("deprecation")
	public void open() {
		/*NetSimDataFileTableModel model = new NetSimDataFileTableModel("Data.dat");
		JTable table = new JTable();
		table.setModel(model);
		table.createDefaultColumnsFromModel();
		//table.*/
		reset();
		File f = new File("data.txt");
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(f);
			}catch(Exception e){
				System.out.println(e.toString());
			} 
		BufferedInputStream bis = new BufferedInputStream(fis); 
		DataInputStream dis = new DataInputStream(bis); 
		String data = null;
		try { 
			   while ( (data=dis.readLine()) != null ) { 
			       StringTokenizer st = new StringTokenizer(data," | ");
			       while(st.hasMoreElements()){
			    	   int id = Integer.parseInt(st.nextToken());
			    	   String name = st.nextToken();
			    	   int x = Integer.parseInt(st.nextToken());
			    	   int y = Integer.parseInt(st.nextToken());
			    	   Node nd = new Node(id,x,y,name);
			    	   if(name.compareTo("trans") == 0){
			    		   transList.put(id, nd);
			    	   }else if(name.compareTo("app")==0){
			    		   appList.put(id, nd);
			    	   }else if(name.compareTo("node")==0){
			    		   nodeList.put(id, nd);
			    	   }
			    	   
			       }
			   } 

			} catch (IOException e) { 
			   System.out.println(e.toString());
			} 	
		//
	//	return table;
		
	}
	//=================================================================save
	/** Save data. */
	public void save() {
		try{
		    // Create file 
		    FileWriter fstream = new FileWriter("data.txt");
		    BufferedWriter out = new BufferedWriter(fstream);
		    //out.write("id | name | x | y\n");
		    for (int id=1; id<= 1000; id++){
				if (transList.containsKey(id)){
					Node node = transList.get(id);
					String nodeData = node.getId()+ " | " + node.getName() + " | " +
									node.getX()+ " | "+node.getY() +"\n";
					out.write(nodeData);
				}
			}
		    for (int id=1; id<= 1000; id++){
				if (nodeList.containsKey(id)){
					Node node = nodeList.get(id);
					String nodeData = node.getId()+ " | " + node.getName() + " | " +
									node.getX()+ " | "+node.getY() +"\n";
					out.write(nodeData);
				}
			}	
		    for (int id=1; id<= 1000; id++){
				if (appList.containsKey(id)){
					Node node = appList.get(id);
					String nodeData = node.getId()+ " | " + node.getName() + " | " +
									node.getX()+ " | "+node.getY() +"\n";
					out.write(nodeData);
				}
			}	
		    //Close the output stream
		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	}
}
