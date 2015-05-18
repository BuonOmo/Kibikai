import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
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
	
	//essais pour les setTarget
	double tX;
	double tY;
	
	private JButton sideScreen;
	private JButton downScreen;
	private JButton map;
	
	JFrame frame;
	
	public UI(){
		
		frame = new JFrame();
		frame.addMouseListener(new CustomMouseListener());
		
		//Size of the screen, have to go in the finals ??
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    screenHeight = screenSize.height;
	    screenWidth = screenSize.width;
	    
	    
	    // Plein ecran
	    frame.setUndecorated(true);
	    frame.setExtendedState(frame.MAXIMIZED_BOTH);
	    
	    
	    /*
	    //Location and size of the frame
	    frame.setSize(screenWidth, screenHeight); //Wide screen
	    frame.setResizable(false);
	    frame.setLocation(0,0);
	    */
	    
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
	    
	    tX=20;
	    tY=20;
	    
	    //JFrame properties
	    frame.setTitle("LUCA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		timer = new Timer(100, new TimerAction());
	}
	
	public class CustomMouseListener implements MouseListener{
	
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                tX=e.getX();
                tY=e.getY();
                System.out.println("C'est bon, ça marche "+ tX + ", " + tY);
                for (Item element : Item.aliveItems){
                    if (element.hitBox.contains(tX, tY)){
                        System.out.println(element+" devrait être selectionné");
                        break;
                    }
                }
            }    
	}
	
	private class TimerAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//canvas.simpleUnit.hitBox.setRect(new Rectangle2D.Double(5,10,5,5));
			
			//canvas.s1.setTarget(new Point2D.Double(0,50));
			//canvas.s1.setTarget(new Point2D.Double(tX, tY));
                        //canvas.s1.execute();
                        //canvas.b1.getLife(0.4);
			Game.run();
				
			canvas.repaint();
			time ++;
			//*****System.out.println ("time : "+time);
		}
	}

	
	public static void main (String[] args){
		UI gui = new UI();
		gui.timer.start();
	}

}
