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