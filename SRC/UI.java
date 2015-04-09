import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.*;



@SuppressWarnings("serial")
public class UI extends JFrame{
	
	/*Timer timer;
	long time;*/
        BufferedImage ArrierePlan;
	Graphics buffer;
	Rectangle Ecran;
	
	Building base1;
	
	final int screenHeight;
	final int screenWidth;
	
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
	    Point2D origine = new Point2D.Double(0,0);
	    Player p1 = new Player (Color.GREEN, base1, "Player one RPZ" );
	    Unit simpleUnit = new SimpleUnit(p1, origine, null) ;
	    
	    //JFrame properties
	    frame.setTitle("LUCA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void paint(Graphics g) {
			
			simpleUnit.print();
			g.drawImage(ArrierePlan,0,0,this);
	}
	
	
	
	public static void main (String[] args){
		UI gui = new UI();
	}

}