import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class UI extends JFrame{

    Timer timer;
    static long time;
    BufferedImage ArrierePlan;
    Graphics buffer;
    Rectangle Ecran;
    Mouse mouse;
    Key key;
    
    //component
    JButton quitButton;
	JLabel ratioLabel;
	JLabel currentLifeLabel;
	JLabel nSimpleUnitLabel;
	JLabel nSoldierLabe;
	ColoredLabel playerLabel;
	
    Canvas canvas;
    SideBand panelBandeau;

    public UI(){
        
        canvas = new Canvas();
        panelBandeau = new SideBand();
        
        mouse = new Mouse(canvas.P1);
        key = new Key(canvas.P1);
        this.addMouseListener(mouse);
        this.addKeyListener(key);
        this.addMouseMotionListener(mouse);
        

        // Plein ecran
        this.setUndecorated(true);
        this.setVisible(true); //YEAAAAAHA
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setBackground(Color.YELLOW);
        this.setLayout(null);
        this.getContentPane().add(panelBandeau); 
        this.getContentPane().add(canvas); 
        
        /*
        //Location and size of the frame
        frame.setSize(Finals.screenWidth, Finals.screenHeight); //Wide screen
        frame.setResizable(false);
        frame.setLocation(0,0);
        */
        
        // Usage paint
        this.paint(this.getGraphics()); //marche pas
    	
        // quelques variables pour simplifier
        int coteQuitButton = Finals.screenWidth/(48);
        int pX = Finals.screenWidth*5/6; 
        //tous les composants
        
    	
    	currentLifeLabel = new JLabel ("Vie : ");
    	nSimpleUnitLabel = new JLabel("5 SU");
    	nSoldierLabe = new JLabel("6 S");
    	
        //leurs positions/dimensions

    		//quitButton
    	quitButton = new JButton("X");
    	quitButton.setBounds(Finals.screenWidth-coteQuitButton, 0, coteQuitButton, coteQuitButton);
    	quitButton.setBackground(Color.RED);
    	ActionListener quit = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
    	};
        quitButton.addActionListener(quit);
        
        
        	//NomJoueur (meme hauteur que quitButton)
    	playerLabel = new ColoredLabel("NomJoueur",Color.BLUE,pX, 0, Finals.screenWidth/6-coteQuitButton-1, coteQuitButton,"rectangle");

        
        //Ratio
        ratioLabel = new JLabel("Vie");
        //Life
        currentLifeLabel.setBounds(pX+Finals.screenWidth/2,35, 50, 100);
    	
    	
        
        
        
        //les ajouter a ui
        this.add(quitButton);
        this.add(playerLabel);
 	
        this.setTitle("LUCA");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        timer = new Timer(100, new TimerAction());
    }
    
    
    //presence du paint vide semblait bloquer tout!
    private class TimerAction implements ActionListener {

    	public void actionPerformed(ActionEvent e) {
    		Game.run();

    		//Scrolling
    		Point mouse = MouseInfo.getPointerInfo().getLocation();
    		
    		if(mouse.x <= Finals.SCROLL_BORDER+50){
    			canvas.cam.moveCamera(-1, 0); //scroll à gauche
    		}
    		else if(mouse.x >= Finals.screenWidth*5/6 - Finals.SCROLL_BORDER){
    			canvas.cam.moveCamera(1, 0);  //scroll à droite
    		}
    		if(mouse.y <= Finals.SCROLL_BORDER){
    			canvas.cam.moveCamera(0, -1); //scroll en haut
    		}
    		else if(mouse.y >= Finals.screenHeight - Finals.SCROLL_BORDER){
    			canvas.cam.moveCamera(0, 1);  //scroll en bas
    		}
    		panelBandeau.repaint();
    		canvas.repaint();
    		
        	System.out.println("les repaint lances dans ui");
    		playerLabel.repaint();
    		time ++;
    	}
    }

    public static void main (String[] args){
        UI gui = new UI();      
        gui.timer.start();
        gui.repaint(); //affiche tout apr�s initiation
    }

}