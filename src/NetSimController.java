import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
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

	class RightPaneListeners extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			System.out.println(me.getX());
//			ImageIcon icon = null;

			int posX = me.getX();
			int posY = me.getY();
			
//how do I access the components in the right pane?
//			Component iconComp = m_view.rightPane.getComponentAt(posX, posY);
			
//			if(icon!=null){
				m_view.paintObject(posX, posY);
//			}
			//rightPaneClicked(me);
			//drawNetworkConnection(me);
		}
	}

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

	////////////////////////////////////////// inner class MultiplyListener
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


	//////////////////////////////////////////// inner class ClearListener
	/**  1. Reset model.
	 *   2. Reset View.
	 */    
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_model.reset();
			m_view.reset();
		}
	}// end inner class ClearListener

}
