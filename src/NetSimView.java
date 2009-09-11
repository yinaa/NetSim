import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class NetSimView extends JFrame{
	//... Components
	private JTextField userInputTextField = new JTextField(5);
	private JTextField totalTextField     = new JTextField(5);
	private JButton    multiplyBtn        = new JButton("Multiply");
	private JButton    clearButton        = new JButton("Clear");

	//Variable declaration
	private static final long serialVersionUID = 1L;

	//View Panels
	private JSplitPane backPane   = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane leftPane   = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel  topLeftPane   = new JPanel();
	private JPanel bottomLeftPane = new JPanel();
	JPanel   rightPane    = new JPanel();
	ButtonGroup group = new ButtonGroup();

	//Buttons
	JToggleButton linkButton;
	JToggleButton nodeButton;
	JToggleButton transButton;
	JToggleButton appButton;
	JToggleButton selectButton;

	//Menus
	private JMenuBar jMenuBar;
	private JMenu fileMenu, helpMenu;// editMenu;
	private JMenuItem  jMenuItemOpen, jMenuItem3, jMenuItem2,  jMenuItem4, jMenuItem1;

	//Icons
	ImageIcon iconNode  = new ImageIcon (getClass().getClassLoader().getResource("resources/icons/node.png"));
	ImageIcon iconApp   = new ImageIcon (getClass().getClassLoader().getResource("resources/icons/app.png"));
	ImageIcon iconTrans = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/trans.png"));
	ImageIcon iconLink  = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/link.png"));
	ImageIcon iconPoint = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/point.png"));

	Graphics g = rightPane.getGraphics();

	//=============================================================== constructor
	//** Constructor *//*
	NetSimView() {
		//... Set up the logic
		try {
			//... Initialize components
			//totalTextField.setText(ns_model.getValue());
			totalTextField.setEditable(false);

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

	//=============================================================== set panes
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
		group.add(appButton);
		group.add(transButton);
		group.add(nodeButton);
		group.add(linkButton);				
		group.add(selectButton);
	}

	void setBottonLeftPane(){		
		leftPane.add(bottomLeftPane, JSplitPane.RIGHT);
		bottomLeftPane.setLayout(new FlowLayout());
		bottomLeftPane.add(clearButton);
	}

	void setRightPane(){
		rightPane.setBackground(new java.awt.Color(255,255,255));
		rightPane.setPreferredSize(new java.awt.Dimension(393, 398));
		rightPane.setVisible(true);
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
				jMenuItem1 = new JMenuItem();
				fileMenu.add(jMenuItem1);
				jMenuItem1.setText("New");
				//jMenuItem2.setAction(getAppActionMap().get("new"));
				fileMenu.addSeparator();
				jMenuItem3 = new JMenuItem();
				fileMenu.add(jMenuItem3);
				jMenuItem3.setText("Save");
				jMenuItem2 = new JMenuItem();
				jMenuItemOpen = new JMenuItem();
				fileMenu.add(jMenuItemOpen);
				jMenuItemOpen.setText("Open");
				fileMenu.add(jMenuItem2);
				jMenuItem2.setText("Quit");
			}		            	
//			//Edit Menu
//			editMenu = new JMenu();
//			jMenuBar.add(editMenu);
//			editMenu.setText("Edit");
//			editMenu.setName("editMenu");
//			{
//				jMenuItem3 = new JMenuItem();
//				editMenu.add(jMenuItem3);
//				jMenuItem3.setText("Clear");
//			}
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

	//============================================================ add listeners

	void addSelectKeyListeners(KeyAdapter e){
		selectButton.addKeyListener(e);
	}
	
	void setSelectButtonListener(KeyAdapter ka){
		selectButton.addKeyListener(ka);
	}
	
	void addRightPaneMouseListeners(MouseAdapter ma) {
		rightPane.addMouseListener(ma);
	}	
	
	void addRightPaneMouseMotionListener(MouseAdapter ma) {
		rightPane.addMouseMotionListener(ma);
	}

	void addClearListener(ActionListener cal) {
		clearButton.addActionListener(cal);
	}
	void addMenuListener(ActionListener menu){
		jMenuItem3.addActionListener(menu);
		jMenuItem2.addActionListener(menu);
		jMenuItemOpen.addActionListener(menu);
	}

	//============================================================ paint Object
	public void paintObject(int posX, int posY, String name) {
		int h =30;
		int w = 70;
		ImageIcon icon = null;
		
		String type = name.split("_")[0];
				
		if (type.compareTo("node")==0){
			icon = iconNode;
		}
		else if (type.compareTo("trans")==0){
			icon = iconTrans;
		}
		else if (type.compareTo("app")==0){
			icon = iconApp;
		}
		
		if(icon != null){
			posX=posX-15;
			posY=posY-20;			

			Image img = icon.getImage();  
			Image newimg = img.getScaledInstance(14, 14, Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);

			JLabel posIcon = new JLabel(name, newIcon,JLabel.CENTER);
			
			Font curFont = posIcon.getFont();
			posIcon.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 13));

			posIcon.setSize(w, h);
			rightPane.add(posIcon);
			posIcon.setLocation(posX, posY);
		}
	}
	
	//=================================================================== reset
	void reset() {
		totalTextField.setText("1");
		rightPane.removeAll();
		rightPane.repaint();
	}
}
