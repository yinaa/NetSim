import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;

public class NetSimController {
	//... The Controller interacts with both the Model and View.
	private NetSimModel m_model;
	private NetSimView  m_view;

	private boolean startDrag = false;
	private boolean drawLink = false;
	private int initMousePosX = 0;
	private int initMousePosY = 0;
	private String initType = null;
	private String endType = null;
	private int initId = 0;
	private int endId = 0;

	Component initComponent= null;

	//Selected components array
	ArrayList<Component> selectedComponents = new ArrayList<Component>();

	//========================================================== constructor
	/** Constructor */
	NetSimController(NetSimModel model, NetSimView view) {

		m_model = model;
		m_view = view;
		//... Add listeners to the view.
		view.addSelectKeyListeners(new SelectKeyListeners());
		view.addRightPaneMouseListeners(new RightPaneMouseListeners());	
		view.addRightPaneMouseMotionListener(new RightPaneMouseMotionListener());
		view.addClearListener(new ClearListener());
		view.addMenuListener(new MenuListener());
	}

	public void drawLinks(){
		Graphics g = m_view.rightPane.getGraphics();
		m_view.rightPane.update(g);
		int[][] linkCoords = m_model.linkCoordinates();
		for (int i=0; i<linkCoords.length; i++){
			g.drawLine(linkCoords[i][0], linkCoords[i][1], linkCoords[i][2], linkCoords[i][3]);
		}
	}
	
	//======================================== inner class SelectKeyListeners
	/**  1. If selected button is selected, and the DEL key is pressed
	 *      the selected objects will be removed from the view and the model
	 */
	class SelectKeyListeners extends KeyAdapter {
		public void keyPressed(KeyEvent evt) {
			if ( evt.getKeyCode() == KeyEvent.VK_DELETE) {
				for (Object comp : selectedComponents) {
					String name = ((JLabel)comp).getText();
					String type = name.split("_")[0];
					int id = new Integer(name.split("_")[1]);
					m_view.rightPane.remove((Component) comp);
					
					System.out.println("remove "+type+id);
					m_model.removeLink(type, id);
					m_model.removeObject(type, id);					
					
					System.out.println("removed");
					drawLinks();
				}
								
				selectedComponents.clear();	
			}
		}
	}
	//================================== inner class RightPaneMouseMotionListener
	/**  1. At mouse motion 
	 *
	 *  
	 */  
	class RightPaneMouseMotionListener extends MouseAdapter {

		public void mouseDragged(MouseEvent me){
			Component iconComp = m_view.rightPane.getComponentAt(me.getX(), me.getY());		
			Graphics g = m_view.rightPane.getGraphics();
		
			if (m_view.selectButton.isSelected()) {

				if (!startDrag) {
					initMousePosX = me.getX();
					initMousePosY = me.getY();

					if (!selectedComponents.contains(iconComp) && !me.isControlDown()) {
						for (Object comp : selectedComponents)
							((Component) comp).setForeground(java.awt.Color.BLACK);
						selectedComponents.clear();
						if (!iconComp.equals(m_view.rightPane)) {
							iconComp.setForeground(java.awt.Color.BLUE);
							selectedComponents.add(iconComp);
						}
					}
					startDrag = true;
				}
				int deltaX = me.getX() - initMousePosX;
				int deltaY = me.getY() - initMousePosY;

				if (!selectedComponents.isEmpty() && !me.isControlDown()) {
					deltaX = me.getX() - initMousePosX;
					deltaY = me.getY() - initMousePosY;
					for (Object comp : selectedComponents) {
						int X = ((Component) comp).getX() + deltaX;
						int Y = ((Component) comp).getY() + deltaY;
						((Component) comp).setLocation(X, Y);
						String name = ((JLabel)comp).getText();
						String type = name.split("_")[0];
						int id = new Integer(name.split("_")[1]);
						m_model.updatePosition(type, id, X, Y);
					}
					initMousePosX = me.getX();
					initMousePosY = me.getY();
				}		
				else {
					g.drawRect(Math.min(initMousePosX, me.getX()), Math.min(initMousePosY, me.getY()),
							Math.abs(initMousePosX - me.getX()), Math.abs(initMousePosY - me.getY()));
				}
			}
			else if (m_view.linkButton.isSelected()) {
				if (!startDrag) {
					initMousePosX = me.getX();
					initMousePosY = me.getY();
					startDrag = true;
					if(!iconComp.equals(m_view.rightPane)){
						initComponent = iconComp;
						String name = ((JLabel)iconComp).getText();
						initType = name.split("_")[0];
						initId = new Integer(name.split("_")[1]);
						drawLink = true;
					}
				}
				if (drawLink)
					g.drawLine(initMousePosX, initMousePosY, me.getX(), me.getY());
			}
			drawLinks();
		}
	}

	//====================================  inner class RightPaneMouseListeners
	/**  1. If a Node, App or Trans button is selected: paints the object 
	 *      in the view.rightPane and adds it to the model
	 *   2. If Select button is selected: changes the text color in the view and 
	 *      adds the selected objects to the model
	 *   3. If Link button is selected:
	 */  
	class RightPaneMouseListeners extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			int posX = me.getX();
			int posY = me.getY();
			String name = null;
			
			if (m_view.nodeButton.isSelected()){
				name = "node";		
			}
			else if (m_view.appButton.isSelected()){
				name = "app";
			}
			else if (m_view.transButton.isSelected()){
				name = "trans";
			}

			if(name != null){
				int id = m_model.insertObject(posX, posY, name);
				m_view.paintObject(posX, posY, name + "_" + id);
				drawLinks();
			}
			Component iconComp = m_view.rightPane.getComponentAt(posX, posY);
			ArrayList<Component> selComp = selectedComponents;

			if (m_view.selectButton.isSelected()) {
				if (!iconComp.equals(m_view.rightPane)) {
					if (!me.isControlDown()){
						for (Object comp : selComp) {
							((Component) comp).setForeground(java.awt.Color.BLACK);
						}
						selComp.clear();
						iconComp.setForeground(java.awt.Color.BLUE);
						selComp.add(iconComp);
					}
					else {
						if (selComp.contains(iconComp)) {
							iconComp.setForeground(java.awt.Color.BLACK);
							selComp.remove(iconComp);
						}
						else {
							iconComp.setForeground(java.awt.Color.BLUE);
							selComp.add(iconComp);
						}
					}
				}
				else {
					for (Object comp : selComp) {
						((Component) comp).setForeground(java.awt.Color.BLACK);
					}
					selComp.clear();
				}
			}
			drawLinks();
		}

		public void mouseReleased(MouseEvent me){
			Component iconComp = m_view.rightPane.getComponentAt(me.getX(), me.getY());
			if (startDrag == true && m_view.selectButton.isSelected()) {
				for (Object comp : m_view.rightPane.getComponents()) {
					int posX = ((Component) comp).getX();
					int posY = ((Component) comp).getY();
					int minX = Math.min(initMousePosX, me.getX());
					int minY = Math.min(initMousePosY, me.getY());
					int maxX = Math.max(initMousePosX, me.getX());
					int maxY = Math.max(initMousePosY, me.getY());
					if (posX >= minX && posX <= maxX && posY >= minY && posY <= maxY) {
						if (selectedComponents.contains((Component) comp)) {
							((Component) comp).setForeground(java.awt.Color.BLACK);
							selectedComponents.remove(((Component) comp));
						}
						else {
							((Component) comp).setForeground(java.awt.Color.BLUE);
							selectedComponents.add(((Component) comp));
						}
					}
				}
				drawLinks();
			}
			if(startDrag && m_view.linkButton.isSelected()){
				if(!iconComp.equals(m_view.rightPane)&& !iconComp.equals(initComponent)){
					String name = ((JLabel)iconComp).getText();
					endType = name.split("_")[0];
					endId = new Integer(name.split("_")[1]);

					m_model.insertLink(initType, initId, endType, endId);
					drawLinks();
				}
				drawLink = false;
			}
			startDrag = false;				
		}
	}//end inner class RightPaneMouseListeners

	//==========================================  inner class ClearListener
	/**  1. Reset model.
	 *   2. Reset View.
	 */    
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_model.reset();
			m_view.reset();
		}
	}// end inner class ClearListener

	//====================================================
	class MenuListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			if(e.getActionCommand().compareTo("Save") == 0){
				m_model.save();
			}
			if(e.getActionCommand().compareTo("Open") == 0){
				m_view.reset();
				m_model.open();
				drawLinks();
				for (int i = 1; i < 100; i++) {
					if(m_model.getHash("trans").containsKey(i)){
						int posX = m_model.getHash("trans").get(i).getX();
						int posY = m_model.getHash("trans").get(i).getY();
						String name = "trans";
						int id = m_model.getHash("trans").get(i).getId();
						m_view.paintObject(posX, posY, name + "_" + id);
					}
					if(m_model.getHash("app").containsKey(i)){
						int posX = m_model.getHash("app").get(i).getX();
						int posY = m_model.getHash("app").get(i).getY();
						String name = "app";
						int id = m_model.getHash("app").get(i).getId();
						m_view.paintObject(posX, posY, name + "_" + id);					
					}
					if(m_model.getHash("node").containsKey(i)){
						int posX = m_model.getHash("node").get(i).getX();
						int posY = m_model.getHash("node").get(i).getY();
						String name = "node";
						int id = m_model.getHash("node").get(i).getId();
						m_view.paintObject(posX, posY, name + "_" + id);
					}
					drawLinks();
				}
				
			}
			
			if(e.getActionCommand().compareTo("New")==0){
				m_view.reset();
				m_model.reset();
			}
	
			if(e.getActionCommand().compareTo("Quit")==0){
				System.exit(0);
			}
		}
	}
}
