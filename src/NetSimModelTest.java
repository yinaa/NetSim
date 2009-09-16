import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class NetSimModelTest {

NetSimModel model= new NetSimModel();
private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
private HashMap<Integer, Node> transList = new HashMap<Integer, Node>();
private HashMap<Integer, Node> appList = new HashMap<Integer, Node>();	
private ArrayList<Link> linkList = new ArrayList<Link>();
	@Test
	public void testObject() {
		nodeList = model.getHash("node");
		transList = model.getHash("trans");
		appList = model.getHash("app");
		
		
		/*insert 4 objects to the HashMap, 2 nodes, 1 trans and 1 app for testing*/
		System.out.println("Testing HashMap....");
		model.insertObject(200, 500, "node"); 
		model.insertObject(200, 390, "node");
		model.insertObject(111, 222, "trans");
		model.insertObject(210, 310, "app");
	
		
		
		assertNotNull(nodeList.get(1));
		assertNotNull(nodeList.get(2));
		assertNotNull(transList.get(1));
	
		
		
		
		assertEquals(nodeList.get(2).getX(),200); 
		assertEquals(transList.get(1).getX(),111);
		assertEquals(appList.get(1).getX(),210);
		assertEquals(appList.get(1).getY(),310);
		
		
	
		model.printList("trans", 1);
		model.printList("app", 1);
		
		model.removeObject("node", 1);
		model.printList("node", 1);
		
		

		
		model.printList("node", 2);
		model.updatePosition("node", 2, 130, 135);
		assertEquals(nodeList.get(2).getX(),130); 
		assertEquals(nodeList.get(2).getY(),135); 
		
		model.printList("node", 2);
		

		
	}

	
	@Test
	public void testInsertLink() {
		System.out.println("Testing arrayList");
		linkList= model.getLinkList();
		model.insertLink("node", 1, "node", 2);
		model.insertLink("node", 3, "app", 4);
		assertNotNull(linkList.get(0));
		assertNotNull(linkList.get(1));
		assertEquals(linkList.get(0).initId,1);
		assertEquals(linkList.get(1).initId,3);
		model.readLink(0);
		model.readLink(1);
		
	}

	@Test
	public void testRemoveLink() {
		//fail("Not yet implemented");
	}

	@Test
	public void testLinkCoordinates() {
		//fail("Not yet implemented");
	}


	@Test
	public void testReset() {
//		fail("Not yet implemented");
	}

	@Test
	public void testOpen() {
//		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetHash() {
//		fail("Not yet implemented");
	}


}
