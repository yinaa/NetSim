import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import java.util.ArrayList;

class NetSimView extends JFrame {
	//... Constants
	private static final String INITIAL_VALUE = "1";

	//... Components
	private JTextField m_userInputTf = new JTextField(5);
	private JTextField m_totalTf     = new JTextField(5);
	private JButton    m_multiplyBtn = new JButton("Multiply");
	private JButton    m_clearBtn    = new JButton("Clear");

	///////////////////////////
	private NetSimModel ns_model;

	//Variable declaration
	private static final long serialVersionUID = 1L;
	
	//View Panels
	private JSplitPane backPane   = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane leftPane   = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel  topLeftPane   = new JPanel();
	private JPanel bottomLeftPane = new JPanel();
	private JPanel   rightPane    = new JPanel();
	
	//Buttons
	private JToggleButton linkButton, nodeButton, transButton, appButton, selectButton;
	
	//Menus
	private JMenuBar jMenuBar;
	private JMenu fileMenu, helpMenu; //editMenu,
	private JMenuItem  jMenuItem2,  jMenuItem4; //jMenuItem1, jMenuItem3,
	
/*	private JLabel posIcon;
	private ArrayList<Component> selectedComponents = new ArrayList<Component>();
	private ArrayList<Link> linkList = new ArrayList<Link>();
	private boolean startDrag = false, drawLink = false;
	private int initMousePosX, initMousePosY;
	private Component initComponent= null;*/

	//Icons
	ImageIcon iconNode  = new ImageIcon (getClass().getClassLoader().getResource("resources/icons/node.png"));
	ImageIcon iconApp   = new ImageIcon (getClass().getClassLoader().getResource("resources/icons/app.png"));
	ImageIcon iconTrans = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/trans.png"));
	ImageIcon iconLink  = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/link.png"));
	ImageIcon iconPoint = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/point.png"));


	//======================================================= constructor
	/** Constructor */
	NetSimView(NetSimModel model) {
		//... Set up the logic
		ns_model = model;
		ns_model.setValue(INITIAL_VALUE);
		try {
			//... Initialize components
			m_totalTf.setText(ns_model.getValue());
			m_totalTf.setEditable(false);

			//////////////////////////////////////////////////////
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//Back Pane setup
			setBackPane();
			setMenuBar();
			this.setTitle("Simple Netsim - MVC");
			this.setContentPane(backPane);
			pack();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	void setBackPane(){
		backPane.setDividerSize(2);
		backPane.setDividerLocation(150);
		backPane.setPreferredSize(new Dimension(600, 600));
		backPane.add(leftPane, JSplitPane.LEFT);
		backPane.add(rightPane, JSplitPane.RIGHT);
		getContentPane().add(backPane, BorderLayout.CENTER);		
		setLeftPane();
		setRightPane();		
	}

	void setLeftPane(){	
		leftPane.setDividerSize(2);
		leftPane.setDividerLocation(400);
		setTopLeftPane();
		setBottonLeftPane();
	}

	void setTopLeftPane(){
		leftPane.add(topLeftPane, JSplitPane.LEFT);
		topLeftPane.setBorder(BorderFactory.createTitledBorder("SELECT"));

		//Adds the components buttons
		nodeButton = new JToggleButton();
		nodeButton.setText("  Node ");
		nodeButton.setIcon(iconNode);
		linkButton = new JToggleButton();
		linkButton.setText("Link  ");
		linkButton.setIcon(iconLink);
		appButton = new JToggleButton();
		appButton.setText("  App  ");
		appButton.setIcon(iconApp);
		transButton = new JToggleButton();
		transButton.setText("  Trans ");
		transButton.setIcon(iconTrans);
		selectButton = new JToggleButton();
		selectButton.setText("     Select ");
		selectButton.setIcon(iconPoint);

		topLeftPane.add(transButton);
		topLeftPane.add(appButton);
		topLeftPane.add(nodeButton);
		topLeftPane.add(linkButton);
		topLeftPane.add(selectButton);

		//Group Buttons
		ButtonGroup group = new ButtonGroup();
		group.add(appButton);
		group.add(transButton);
		group.add(nodeButton);
		group.add(linkButton);				
		group.add(selectButton);

		selectButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				//rightPaneKeyPressed(evt);
			}
		});
	}
	
	void setBottonLeftPane(){		
		leftPane.add(bottomLeftPane, JSplitPane.RIGHT);
		bottomLeftPane.setLayout(new FlowLayout());
		bottomLeftPane.add(new JLabel("Input"));
		bottomLeftPane.add(m_userInputTf);
		bottomLeftPane.add(m_multiplyBtn);
		bottomLeftPane.add(new JLabel("Total"));
		bottomLeftPane.add(m_totalTf);
		bottomLeftPane.add(m_clearBtn);
	}

	void setRightPane(){
		rightPane.setBackground(new java.awt.Color(255,255,255));
		rightPane.setPreferredSize(new java.awt.Dimension(393, 398));
	}

	void setMenuBar(){
		jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
		{
			//File Menu
			fileMenu = new JMenu();
			jMenuBar.add(fileMenu);
			fileMenu.setText("File");
			fileMenu.setName("fileMenu");
			{
				/*jMenuItem1 = new JMenuItem();
				fileMenu.add(jMenuItem1);
				jMenuItem1.setText("New");
				//jMenuItem2.setAction(getAppActionMap().get("new"));
				fileMenu.addSeparator();*/
				jMenuItem2 = new JMenuItem();
				fileMenu.add(jMenuItem2);
				jMenuItem2.setText("Quit");
				//jMenuItem2.addActionListener(SimpleListener);
				//jMenuItem2.setAction(getAppActionMap().get("quit"));
			}		            	
			/*//Edit Menu
			editMenu = new JMenu();
			jMenuBar.add(editMenu);
			editMenu.setText("Edit");
			editMenu.setName("editMenu");
			{
				jMenuItem3 = new JMenuItem();
				editMenu.add(jMenuItem3);
				jMenuItem3.setText("Clear");
				jMenuItem3.addActionListener(SimpleListener2);
				//jMenuItem3.setAction(getAppActionMap().get("clear"));
			}*/
			//Help Menu
			helpMenu = new JMenu();
			jMenuBar.add(helpMenu);
			helpMenu.setText("Help");
			helpMenu.setName("helpMenu");
			{
				jMenuItem4 = new JMenuItem();
				helpMenu.add(jMenuItem4);
				jMenuItem4.setText("About");	
				//jMenuItem4.addActionListener(SimpleListener1);
				//jMenuItem4.setAction(getAppActionMap().get("about"));
			}									
		}
	}

	void addRightPaneMouseListeners(MouseAdapter me) {
		rightPane.addMouseListener(me);
	}
/*	void addRightPaneMouseMotionListeners(MouseAdapter me) {
		rightPane.addMouseMotionListener(me);
	}*/
	
	void reset() {
		m_totalTf.setText(INITIAL_VALUE);
	}

	String getUserInput() {
		return m_userInputTf.getText();
	}

	void setTotal(String newTotal) {
		m_totalTf.setText(newTotal);
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	void addMultiplyListener(ActionListener mal) {
		m_multiplyBtn.addActionListener(mal);
	}

	void addClearListener(ActionListener cal) {
		m_clearBtn.addActionListener(cal);
	}
}
