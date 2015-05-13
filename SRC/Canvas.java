import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Canvas extends JComponent{
    Rectangle2D hitBox;
    Unit simpleUnit;
    Building b1;
    Building b2;
    Soldier s1;
    
    
    public Canvas(){
        
        Player P1 = new Player (Color.GREEN, new Point2D.Double(10,10), "Player one RPZ" );
        Player P2 = new Player (Color.RED, new Point2D.Double(100,50), "Player two FTW" );
        /*
        hitBox = new Rectangle2D.Double (0,0, 10, 10);
        simpleUnit = new SimpleUnit(P1, new Point2D.Double(50,50), null);
        s1 = new Soldier(P2, new Point2D.Double(50,20));
        b1 = new Building(P1, new Point2D.Double(10, 20));
        //s1.setTarget(u1);
        */
        
        
    }
    
    public void paintComponent(Graphics g) {
        /*
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRoundRect((int)hitBox.getX()*Finals.scale, (int)hitBox.getY()*Finals.scale, 
                        (int)hitBox.getWidth()*Finals.scale, (int)hitBox.getHeight()*Finals.scale,3*Finals.scale,3*Finals.scale);
        simpleUnit.print(g);
        s1.print(g);
        b1.print(g);
        */
        Game.print(g);
    }
	
}
