import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class NetSimModel {

	private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> transList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> appList = new HashMap<Integer, Node>();
	private ArrayList<Link> linkList = new ArrayList<Link>();

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

	//=============================================================== insert link
	/** insert link */
	public void insertLink(String initType, int initId, String endType, int endId){
		Link link = new Link(initType, initId, endType, endId);
		linkList.add(link);
	}

	//=============================================================== remove link
	/** remove link */
	public void removeLink(String type, int id){
		ArrayList<Link> removeList = new ArrayList<Link>();
		for(Link l : linkList){
			if (type.compareTo(l.initType)==0 && id==l.initId || type.compareTo(l.endType)==0 && id==l.endId ){
				removeList.add(l);
			}
		}
		for(Link l : removeList)
			linkList.remove(l);
	}

	//=================================================== return link Coordinates
	public int[][] linkCoordinates(){
		int[][] linkCoords = new int[linkList.size()][4];
		int i = 0;

		for(Link l: linkList){
			if (l.initType.compareTo("node")==0){
				linkCoords[i][0] = (nodeList.get(l.initId)).getX();
				linkCoords[i][1] = (nodeList.get(l.initId)).getY();
			}
			else if (l.initType.compareTo("trans")==0){
				linkCoords[i][0] = (transList.get(l.initId)).getX();
				linkCoords[i][1] = (transList.get(l.initId)).getY();
			}
			else if (l.initType.compareTo("app")==0){
				linkCoords[i][0] = (appList.get(l.initId)).getX();
				linkCoords[i][1] = (appList.get(l.initId)).getY();
			}
			if (l.endType.compareTo("node")==0){
				linkCoords[i][2] = (nodeList.get(l.endId)).getX();
				linkCoords[i][3] = (nodeList.get(l.endId)).getY();
			}
			else if (l.endType.compareTo("trans")==0){
				linkCoords[i][2] = (transList.get(l.endId)).getX();
				linkCoords[i][3] = (transList.get(l.endId)).getY();
			}
			else if (l.endType.compareTo("app")==0){
				linkCoords[i][2] = (appList.get(l.endId)).getX();
				linkCoords[i][3] = (appList.get(l.endId)).getY();
			}
			i++;
		}
		return linkCoords;
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
		linkList.clear();
	}

	//==================================================================== open
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
		f = new File("dataLink.txt");
		fis = null;
		try{
			fis = new FileInputStream(f);
		}catch(Exception e1){
			System.out.println(e1.toString());
		} 
		bis = new BufferedInputStream(fis); 
		dis = new DataInputStream(bis); 
		data = null;
		try { 
			while ( (data=dis.readLine()) != null ) { 
				StringTokenizer st = new StringTokenizer(data," | ");
				while(st.hasMoreElements()){
					String initType = st.nextToken();
					int initId = Integer.parseInt(st.nextToken());
					String endType = st.nextToken();
					int endId = Integer.parseInt(st.nextToken());
					Link l = new Link(initType,initId,endType,endId);
					linkList.add(l);
				}
			} 
		} catch (IOException e2) { 
			System.out.println(e2.toString());
		} 
		//
		// return table;
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
		try{
			// Create file 
			FileWriter fstream2 = new FileWriter("dataLink.txt");
			BufferedWriter out2 = new BufferedWriter(fstream2);
			//out.write("id | name | x | y\n");
			Link next = null;
			for (int i=0; i<=1000; i++){
				if(linkList.size() <= i)
					break;
				next = linkList.get(i);
				String nextData = next.initType + " | " + next.initId + " | " +
				next.endType+ " | "+next.endId +"\n";
				out2.write(nextData);
			}
			out2.close();
		}catch(Exception e){System.out.println(e.toString());}

	}

	//===
	public HashMap<Integer, Node> getHash(String name){
		if(name.compareTo("trans") == 0)
			return transList;
		else if (name.compareTo("node") == 0)
			return nodeList;
		else if (name.compareTo("app") == 0)
			return appList;
		return null;
	}
	public ArrayList<Link> getLinkList (){
		return linkList;
	}

	public void printList(String name,int id)
	{
		Node node = null;
		if ("node".equals(name))
		{
			if(nodeList.containsKey(id))
			{
				node = nodeList.get(id);

				System.out.println("id:" + node.getId()+"\t"+"type:"+node.getName()+ "\t"+"POX:" + node.getX()+ "\t  "+"POY:" +node.getY());
			}
			else{
				System.out.println("id does not exist");
				return;
			}
		}

		if("trans".equals(name))
		{
			if(transList.containsKey(id))
			{
				node = transList.get(id);

				System.out.println("id:" + node.getId()+"\t"+"type:"+node.getName()+ "\t"+"POX:" + node.getX()+ "\t  "+"POY:" + node.getY());
			}
			else{
				System.out.println("id does not exist");
				return;
			}
		}

		if("app".equals(name))
		{
			if(appList.containsKey(id))
			{
				node = appList.get(id);

				System.out.println("id:" + node.getId()+"\t"+"type:"+ node.getName()+ "\t"+"POX:" + node.getX()+ "\t  "+"POY:" +node.getY());
			}
			else{
				System.out.println("id does not exist");
				return;
			}
		}


	}
	public Link readLink(int id)
	{
		Link link=linkList.get(id);

		try
		{

			System.out.println("initType:" + link.initType+"\t"+"initId:"+link.initId+"\t"+"endType:"+link.endType+"\t"+"endId:"+link.endId);

		}
		catch(Exception e)

		{
			System.out.println("exception"+e.getMessage());
		}


		return link;
	}
}
