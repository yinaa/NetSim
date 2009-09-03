import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//import java.awt.event.MouseListener;

//import NetSimController.ClearListener;
//import NetSimController.MultiplyListener;

public class NetSimController {
	//... The Controller needs to interact with both the Model and View.
	private NetSimModel m_model;
	private NetSimView  m_view;

	//========================================================== constructor
	/** Constructor */
	NetSimController(NetSimModel model, NetSimView view) {
		m_model = model;
		m_view  = view;

		//... Add listeners to the view.
		view.addMultiplyListener(new MultiplyListener());
		view.addClearListener(new ClearListener());
		view.addRightPaneMouseListeners(new RightPaneListeners ());
	}

	//==========================================  inner class ClearListener
	/**  1. If a Node, App or Trans button is selected: paints the object 
	 *      in the view.rightPane and adds it to the model
	 *   2. If Select button is selected:
	 *   3. If Link button is selected:
	 */    
	class RightPaneListeners extends MouseAdapter {
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
			ArrayList<Component> selComp = m_model.selectedComponents;

//not working on deselecting object
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
	}//end inner class RightPaneListeners

	
	/* rightPane.addMouseListener(new MouseAdapter(){		
    		 public void mouseReleased(MouseEvent me){
    			 //rightPaneMouseReleased(me);
    		 }							
    	 });
    	 rightPane.addMouseMotionListener(new MouseAdapter(){
    		 public void mouseDragged(MouseEvent me){
    			 //rightPaneMouseDragged(me);
    		 }
    	 });*/

	/*	selectButton.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent evt) {
			//rightPaneKeyPressed(evt);
		}
	});*/



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
