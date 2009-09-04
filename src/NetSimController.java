import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NetSimController {
	//... The Controller interacts with both the Model and View.
	private NetSimModel m_model;
	private NetSimView  m_view;

	private boolean startDrag = false;
	private boolean drawLink = false;
	private int initMousePosX = 0;
	private int initMousePosY = 0;


	//========================================================== constructor
	/** Constructor */
	NetSimController(NetSimModel model, NetSimView view) {
		m_model = model;
		m_view  = view;		

		//... Add listeners to the view.
		view.addMultiplyListener(new MultiplyListener());
		view.addClearListener(new ClearListener());
		view.addSelectKeyListeners(new SelectKeyListeners ());
		view.addRightPaneMouseListeners(new RightPaneMouseListeners ());	
		view.addRightPaneMouseMotionListener(new RightPaneMouseMotionListener());
	}

	//======================================== inner class SelectKeyListeners
	/**  1. If selected button is selected, and the DEL key is pressed
	 *      the selected objects will be removed from the view and the model
	 */
	class SelectKeyListeners extends KeyAdapter {
		public void keyPressed(KeyEvent evt) {
			if ( evt.getKeyCode() == KeyEvent.VK_DELETE) {
				for (Object comp : m_view.selectedComponents) {
					m_view.rightPane.remove((Component) comp);
					//TODO Remove Objects from model	
					//	m_model.removeObject(id) ???get components id or x y
				}
				m_view.rightPane.repaint();
				m_view.removeSelectedComponents();
			}
		}
	}

	//================================== inner class RightPaneMouseMotionListener
	/**  1. At mouse motion 
	 *
	 *  
	 */  
	class RightPaneMouseMotionListener extends MouseAdapter {
		Component initComponent= null;
		
		public void mouseDragged(MouseEvent me){
			System.out.println("HERE");
			Graphics g = m_view.rightPane.getGraphics();
			Component iconComp = m_view.rightPane.getComponentAt(me.getX(), me.getY());		
			
			if (m_view.selectButton.isSelected()) {

				if (!startDrag) {
					initMousePosX = me.getX();
					initMousePosY = me.getY();

					if (!m_view.selectedComponents.contains(iconComp) && !me.isControlDown()) {
						for (Object comp : m_view.selectedComponents)
							((Component) comp).setForeground(java.awt.Color.BLACK);
						m_view.selectedComponents.clear();
						if (!iconComp.equals(m_view.rightPane)) {
							iconComp.setForeground(java.awt.Color.BLUE);
							m_view.selectedComponents.add(iconComp);
						}
					}
					startDrag = true;
				}
				int deltaX = me.getX() - initMousePosX;
				int deltaY = me.getY() - initMousePosY;

				if (!m_view.selectedComponents.isEmpty() && !me.isControlDown()) {
					deltaX = me.getX() - initMousePosX;
					deltaY = me.getY() - initMousePosY;
					for (Object comp : m_view.selectedComponents) {
						int X = ((Component) comp).getX() + deltaX;
						int Y = ((Component) comp).getY() + deltaY;
						((Component) comp).setLocation(X, Y);
					}
					initMousePosX = me.getX();
					initMousePosY = me.getY();
				}
				//TODO paint rectangle				
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
						drawLink = true;
					}
				}
				if (drawLink)
					g.drawLine(initMousePosX, initMousePosY, me.getX(), me.getY());
			}
			m_view.rightPane.repaint();
//TODO		drawLinks();
			
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
				name = "NODE";		
			}
			else if (m_view.appButton.isSelected()){
				name = "APP";
			}
			else if (m_view.transButton.isSelected()){
				name = "TRANS";
			}

			if(name != null){
				m_view.paintObject(posX, posY, name);
				m_model.insertObject(posX, posY, name);
			}

			//how do I access the components in the right pane?
			Component iconComp = m_view.rightPane.getComponentAt(posX, posY);
			ArrayList<Component> selComp = m_view.selectedComponents;

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
			m_view.rightPane.repaint();

			//drawNetworkConnection(me);
		}

		public void mouseReleased(MouseEvent me){
			//			Component iconComp = m_view.rightPane.getComponentAt(me.getX(), me.getY());
			if (startDrag == true && m_view.selectButton.isSelected()) {
				for (Object comp : m_view.rightPane.getComponents()) {
					int posX = ((Component) comp).getX();
					int posY = ((Component) comp).getY();
					int minX = Math.min(initMousePosX, me.getX());
					int minY = Math.min(initMousePosY, me.getY());
					int maxX = Math.max(initMousePosX, me.getX());
					int maxY = Math.max(initMousePosY, me.getY());
					if (posX >= minX && posX <= maxX && posY >= minY && posY <= maxY) {
						if (m_view.selectedComponents.contains((Component) comp)) {
							((Component) comp).setForeground(java.awt.Color.BLACK);
							m_view.selectedComponents.remove(((Component) comp));
						}
						else {
							((Component) comp).setForeground(java.awt.Color.BLUE);
							m_view.selectedComponents.add(((Component) comp));
						}
					}
				}
				m_view.rightPane.repaint();
			}
			/*			if(startDrag && m_view.linkButton.isSelected()){
				if(!iconComp.equals(m_view.rightPane)&& !iconComp.equals(initComponent)){
					Link l = new Link(initComponent, iconComp);
					linkList.add(l);
					drawLinks();

				}
				drawLink = false;
			}*/
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

	//========================================== inner class MultiplyListener
	/** When a multiplication is requested.
	 *  1. Get the user input number from the View.
	 *  2. Call the model to multiply by this number.
	 *  3. Get the result from the Model.
	 *  4. Tell the View to display the result.
	 * If there was an error, tell the View to display it.
	 */
	class MultiplyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userInput = "";
			try {
				userInput = m_view.getUserInput();
				m_model.multiplyBy(userInput);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput + "'");
			}
		}
	}//end inner class MultiplyListener

}
