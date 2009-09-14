import java.awt.Component;

public class Link {
	public String nodeElement = "nodeElement", appLink = "appLink", transLink = "transLink";
	private String initType = null, endType = null;
	private int initId, endId;
	public Component lastComponent = null;
	
	Link(String initType, int initId, String endType, int endId){
		this.initType = initType;
		this.initId = initId;
		this.endType = endType;
		this.endId = endId;		
	}
	
}

