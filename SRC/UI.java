import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class UI extends JFrame{
	
	Timer timer;
	static long time;
	BufferedImage ArrierePlan;
	Graphics buffer;
	Rectangle Ecran;
	
	Building base1;
	Rectangle hitBox;
	Unit simpleUnit;
	
	Canvas canvas;
	
	final int screenHeight;
	final int screenWidth;
	
	double tX;
	double tY;
	
	private JButton sideScreen;
	private JButton downScreen;
	private JButton map;
	
	public UI(){
		
		JFrame frame = new JFrame();

		//Size of the screen, have to go in the finals ??
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    screenHeight = screenSize.height;
	    screenWidth = screenSize.width;
	    
	    //Location and size of the frame
	    frame.setSize(screenWidth, screenHeight); //Wide screen
	    frame.setResizable(false);
	    frame.setLocation(0,0);
	    
	    //Set the background on WHITE
	    frame.getContentPane().setBackground(Color.WHITE);
	    canvas = new Canvas();
	    frame.getContentPane().add(canvas);
	    
	    /* Boutons pour tester le Layout
	    
	    //Put the components on the right place
	    frame.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
        
	    sideScreen = new JButton("There will be some informations about the game");
	    downScreen = new JButton("And there more informations");
	    map = new JButton("Espace de jeu");
	    JPanel cadre = new JPanel();
	    
	    cadre.add(sideScreen);
	    cadre.add(downScreen);
	    cadre.add(map);
	    
	    frame.add(cadre);
	    */
	    
	    tX=0;
	    tY=0;
	    
	    //JFrame properties
	    frame.setTitle("LUCA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		timer = new Timer(100, new TimerAction());
	}
	
	public class CustomMouseListener implements MouseListener{
		
		public void mousePressed(MouseEvent e) {
			
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			canvas.s1.setTarget(new Point2D.Double(e.getX(), e.getY()));
		}    
	}
	
	private class TimerAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//canvas.simpleUnit.hitBox.setRect(new Rectangle2D.Double(5,10,5,5));
			
			//canvas.s1.setTarget(new Point2D.Double(0,50));
			//canvas.s1.setTarget(new Point2D.Double(tX, tY));
			canvas.s1.move();
			canvas.repaint();
		}
	}

	
	public static void main (String[] args){
		UI gui = new UI();
		gui.timer.start();
	}

}
