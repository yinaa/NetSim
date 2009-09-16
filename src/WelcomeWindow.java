

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class WelcomeWindow extends JWindow{
	private static final long serialVersionUID = 1L;

//	welcome window.
	public WelcomeWindow(Frame f,int waitTime){
		super(f);
		ImageIcon iconApp = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/Welcome.jpg"));

		JLabel label = new JLabel(iconApp);//image add to label l
		getContentPane().add(label, BorderLayout.CENTER);
		pack();
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = label.getPreferredSize();
		setLocation(screenSize.width/2 - (labelSize.width/2),screenSize.height/2 - (labelSize.height/2));

//		press the welcome window,it will be closed. 
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				setVisible(false);
			}
		});


		final int pause = waitTime;
		final Runnable closerRunner = new Runnable(){
			public void run(){
				setVisible(false);
			}
		};
		
		Runnable waitRunner = new Runnable(){
			public void run(){
				try{
					Thread.sleep(pause);
					//invoke closerRunner and wait for waitRunner run.
					SwingUtilities.invokeAndWait(closerRunner);
				}catch(Exception e){
					e.printStackTrace();
					// Catch InvocationTargetException
					// Catch InterruptedException
				}
			}
		};


		setVisible(true);
		Thread waitThread = new Thread(waitRunner, "SplashThread");
		waitThread.start();
	}


	

}

