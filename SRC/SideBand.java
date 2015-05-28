import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


public class SideBand extends JPanel implements Finals{
	JButton quitButton;
	int pX;
	int coteQuitButton;
	public SideBand(){	
		
		this.setBackground(Color.BLUE);
		this.setSize(Finals.screenWidth*(1/6), Finals.screenHeight);
		this.setBounds(Finals.screenWidth*5/6, 0, Finals.screenWidth*(1/6), Finals.screenHeight);
		this.setVisible(true);
		pX = Finals.screenWidth*5/6; 
		coteQuitButton = Finals.screenWidth/(48);
		
		
	}
	
	public void paint(Graphics g) {
    	g.setColor(Color.YELLOW);
    	g.fillRect(pX, 0, Finals.screenWidth/6, Finals.screenHeight);
    	/** //quitButton
    	g.setColor(Color.RED);
    	g.fillRect(Finals.screenWidth-coteQuitButton, 0, coteQuitButton, coteQuitButton);
    	g.setColor(Color.BLUE);
    	g.fillOval(Finals.screenWidth-coteQuitButton*2/3, coteQuitButton/3, coteQuitButton/3, coteQuitButton/3);
    	//Nom Joueur
    	g.fillRect(pX, 0, Finals.screenWidth/6-coteQuitButton, coteQuitButton);*/
    	System.out.println("paint de side band");
    	
    }
	
	
	
}