public class Link {
	public String initType = null, endType = null;
	public int initId, endId;
	
	//========================================================== constructor
	/** Constructor */
	Link(String initType, int initId, String endType, int endId){
		this.initType = initType;
		this.initId = initId;
		this.endType = endType;
		this.endId = endId;		
	}
}


