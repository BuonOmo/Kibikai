import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class UI extends JFrame{

    Timer timer;
    static long time;
    BufferedImage ArrierePlan;
    Graphics buffer;
    Rectangle Ecran;

    GamePan gamepan;
    //SideBand panelBandeau;

    JFrame frame;

    public UI(){
        gamepan = new GamePan(Finals.screenHeight,Finals.screenWidth);
        
        frame = new JFrame();
        
        
        // Plein ecran
        /*
        frame.setUndecorated(true);
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gE.getDefaultScreenDevice().setFullScreenWindow(frame);
        */
        
        
        
        
        
        //Location and size of the frame
        frame.setSize(Finals.screenWidth, Finals.screenHeight); //Wide screen
        frame.setResizable(false);
        frame.setLocation(0,0);
        

        //Set the background on YELLOW : if you see stg yellow it means the jpanel ain't working
        //frame.getContentPane().setBackground(Color.YELLOW);
        
        //panelBandeau = new SideBand();       
        //frame.getContentPane().setLayout(null);
        frame.add(gamepan);  
        //frame.getContentPane().add(panelBandeau);
        
        //JFrame properties
        frame.setTitle("LUCA");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        timer = new Timer(60, new TimerAction());
    }
    

    private class TimerAction implements ActionListener {

    	public void actionPerformed(ActionEvent e) {
            Game.run();

            //Scrolling
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            
            if(mouse.x <= Finals.SCROLL_BORDER){
                    gamepan.canvas.cam.moveCamera(-Finals.CAMERA_SPEED, 0); //scroll à gauche
            }
            else if(mouse.x >= Finals.screenWidth - Finals.SCROLL_BORDER){
                    gamepan.canvas.cam.moveCamera(Finals.CAMERA_SPEED, 0);  //scroll à droite
            }
            if(mouse.y <= Finals.SCROLL_BORDER){
                    gamepan.canvas.cam.moveCamera(0, -Finals.CAMERA_SPEED); //scroll en haut
            }
            else if(mouse.y >= Finals.screenHeight - Finals.SCROLL_BORDER){
                    gamepan.canvas.cam.moveCamera(0, Finals.CAMERA_SPEED);  //scroll en bas
            }
            
    	    if(Key.leftKey){
    	            gamepan.canvas.cam.moveCamera(-Finals.CAMERA_SPEED/2, 0); //scroll à gauche
    	    }
    	    else if(Key.rightKey){
    	            gamepan.canvas.cam.moveCamera(Finals.CAMERA_SPEED/2, 0);  //scroll à droite
    	    }
    	    if(Key.upKey){
    	            gamepan.canvas.cam.moveCamera(0, -Finals.CAMERA_SPEED/2); //scroll en haut
    	    }
    	    else if(Key.downKey){
    	            gamepan.canvas.cam.moveCamera(0, Finals.CAMERA_SPEED/2);  //scroll en bas
    	    }
                
            gamepan.repaint();
            gamepan.UpDate();
            //panelBandeau.repaint();
            time ++;
    	}
    }

    public static void main (String[] args){
        UI gui = new UI();      
        gui.timer.start();
    }

}