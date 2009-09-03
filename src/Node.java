
public class Node {
	private int x, y;
	private int id;
	
	//======================================================= constructor
	/** Constructor */
	Node(int nodeId, int posX, int posY){
		id = nodeId;
		x = posX;
		y = posY;
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
}
