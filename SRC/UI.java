import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
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
    Mouse mouse;
    Key key;

    Canvas canvas;

    //essais pour les setTarget
    double tX;
    double tY;

    private JButton sideScreen;
    private JButton downScreen;
    private JButton map;

    JFrame frame;

    public UI(){
        canvas = new Canvas();
        mouse = new Mouse(canvas.P1);
        key = new Key(canvas.P1);
        frame = new JFrame();
        frame.addMouseListener(mouse);
        frame.addKeyListener(key);
        frame.addMouseMotionListener(mouse);

        
        // Plein ecran
        frame.setUndecorated(true);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);


        /*
        //Location and size of the frame
        frame.setSize(Finals.screenWidth, Finals.screenHeight); //Wide screen
        frame.setResizable(false);
        frame.setLocation(0,0);
        */

        //Set the background on WHITE
        frame.getContentPane().setBackground(Color.WHITE);
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

        boolean hasSelected = false;
        Item selected;
        boolean rightClicked, leftClicked;

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {
            tX=e.getX()/Finals.scale;
            tY=e.getY()/Finals.scale;

            switch(e.getModifiers()) {
                case InputEvent.BUTTON1_MASK: {
                    leftClicked = true;
                    break;
                }
                /**
                 * Pour le bouton du milieu :
                case InputEvent.BUTTON2_MASK: {
                    System.out.println("That's the MIDDLE button");     
                    break;
                }
                 */
                case InputEvent.BUTTON3_MASK: {
                    rightClicked = true;     
                    break;
                }
            }
            if (leftClicked){
                for (Item element : Item.aliveItems){
                    if (element.hitBox.contains(tX, tY)){
                        System.out.println("UI.CustomMouseListener.mouseClicked : "+element+" devrait être selectionné");
                        hasSelected = true;
                        element.setSelected(true);
                        selected = element;
                        break;
                    }
                }
                if (hasSelected){
                    for (Item element : Item.aliveItems){
                        if (element.hitBox.contains(tX, tY)){
                            element.setSelected(true);
                            break;
                        }
                    }
                    selected.setTarget(tX, tY);
                }
            }
            if (rightClicked){
                if (hasSelected){
                    for (Item element : Item.aliveItems){
                        if (element.hitBox.contains(tX, tY)){
                            selected.setTarget(element);
                            break;
                        }
                    }
                    selected.setTarget(tX, tY);
                }
            }
        }    
    }

    private class TimerAction implements ActionListener {

    	public void actionPerformed(ActionEvent e) {
    		Game.run();

    		//Scrolling
    		Point mouse = MouseInfo.getPointerInfo().getLocation();
    		if(mouse.x <= Finals.SCROLL_BORDER){
    			//scroll à gauche
    			//System.out.println("Scroll à gauche");
    		}
    		else if(mouse.x >= Finals.screenWidth - Finals.SCROLL_BORDER){
    			//scroll à droite
    			//System.out.println("Scroll à droite");
    		}
    		if(mouse.y <= Finals.SCROLL_BORDER){
    			//scroll en haut
    			//System.out.println("Scroll en haut");
    		}
    		else if(mouse.y >= Finals.screenHeight - Finals.SCROLL_BORDER){
    			//scroll en bas
    			//System.out.println("Scroll en bas");
    		}

    		canvas.repaint();
    		time ++;
    	}
    }

/*
    public static void main (String[] args){
        UI gui = new UI();
        gui.timer.start();
    }
*/
}