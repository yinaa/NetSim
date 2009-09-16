import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashMap;

public class NetSimModelTest {

	NetSimModel model= new NetSimModel();
	private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> transList = new HashMap<Integer, Node>();
	private HashMap<Integer, Node> appList = new HashMap<Integer, Node>();	

	@Test
	public void testObject() {
		nodeList = model.getHash("node");
		transList = model.getHash("trans");
		appList = model.getHash("app");

		/*insert 4 objects to the HashMap, 2 nodes, 1 trans and 1 app for testing*/
		model.insertObject(200, 500, "node"); 
		model.insertObject(200, 390, "node");
		model.insertObject(111, 222, "trans");
		model.insertObject(210, 310, "app");
		model.insertLink("node", 1, "node", 2);

		assertNotNull(nodeList.get(2));
		assertNotNull(transList.get(1));
		assertNotNull(nodeList.get(1));

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

		model.open();

	}


	@Test
	public void testInsertLink() {
		//fail("Not yet implemented");
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
