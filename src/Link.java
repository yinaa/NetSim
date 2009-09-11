import java.awt.Component;

public class Link {
	public Component initComponent = null;
	public Component lastComponent = null;
	
	Link(Component o, Component d){
		this.initComponent = o;
		this.lastComponent = d;		
	}
}

