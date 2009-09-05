/*this is a nodeclass*/
public class Node {
	private int x, y;
	private int id;
	private String name;
	
	//======================================================= constructor
	/** Constructor */
	Node(int nodeId, int posX, int posY, String nodeName){
		id = nodeId;
		x = posX;
		y = posY;
		name = nodeName;
	}
	
	//======================================================= attributes
	public void setPosition(int posX, int posY){
		x = posX;
		y = posY;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
}
