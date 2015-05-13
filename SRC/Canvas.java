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
    Player P1;
    Player P2;
    
    
    public Canvas(){
        
        P1 = new Player (Color.GREEN, new Point2D.Double(10,10), "Player one RPZ" );
        P2 = new Player (Color.RED, new Point2D.Double(100,50), "Player two FTW" );
        /*
        hitBox = new Rectangle2D.Double (0,0, 10, 10);
        simpleUnit = new SimpleUnit(P1, new Point2D.Double(50,50), null);
        s1 = new Soldier(P2, new Point2D.Double(50,20));
        b1 = new Building(P1, new Point2D.Double(10, 20));
        //s1.setTarget(u1);
        */
        
        //P1.base.goAndProcreate();
        
        /* ____________________________________________________   Decomenter pour voir une magnifique bataille
        new Soldier(P1, new Point2D.Double(20,20));
        P1.soldiers.get(0).setTarget(P2.base);
        */
        
        new Soldier(P1, new Point2D.Double(20,20));
        new Soldier(P1, new Point2D.Double(15,20));
        new Soldier(P2, new Point2D.Double(90,40));
        P1.soldiers.get(1).setTarget(P2.soldiers.get(0));
        P2.soldiers.get(0).setTarget(P1.base);
        for (int i= 0; i<1; i++){
            new SimpleUnit(P1, P1.base, new Point2D.Double(20.0, 5.0 +2.0*(double)i*Finals.SIDE));
        }
        new SimpleUnit(P1, P1.soldiers.get(1), new Point2D.Double(5,10));
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
