import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel{
    Player P1;
    Player P2;
    Camera cam;
    
    public Canvas()  {
    	
        this.setSize(Finals.screenWidth*5/6, Finals.screenHeight);
        this.setBounds(0, 0, Finals.screenWidth*5/6, Finals.screenHeight);
        this.setVisible(true); //osef
        this.setBackground(Color.YELLOW); //marche pas!
        IA.bigining();
        P1 = new Player (Color.GREEN, Finals.BASE_LOCATION, "Player one RPZ" );
        P2 = new Player (Color.RED, new Point2D.Double(40,40), "Player two FTW" );
        IA.computer=P2;
        IA.player=P1;
        cam = new Camera();
        
        //P1.base.goAndProcreate();
        
        /* Decommenter pour voir une magnifique bataille*/
        //new Soldier(P1, new Point2D.Double(20,20));
        //P1.soldiers.get(0).setTarget(P2.base);

        for (int i =1 ; i<0; i++){
           // new Soldier(P1, new Point2D.Double(20,20+i));
           // new Soldier(P1, new Point2D.Double(15,20+i));
            new Soldier(P2, new Point2D.Double(90,40+i));
        }
        for (int i =0; i< 9;i++){
            new SimpleUnit(P1, new Point2D.Double(20,5+2*i));
            //new SimpleUnit(P1, P1.base, new Point2D.Double(22.5,5*i));
            //new Soldier(P1, new Point2D.Double(5*i,5));
        }
        
        //P1.soldiers.get(1).setTarget(P2.soldiers.get(0));
        //P2.soldiers.get(0).setTarget(Pc1.base);
        //P1.soldiers.get(0).setTarget(new Point2D.Double(100,100));
        for (int i= 0; i<0; i++){
            //new SimpleUnit(P1, P1.base, new Point2D.Double(20.0, 5.0 +2.0*(double)i*Finals.SIDE));
        }
        // new SimpleUnit(P1, P1.soldiers.get(0), new Point2D.Double(5,10));

    }
    
    public void paint(Graphics g) {
    	g.setColor(Color.YELLOW);
    	g.fillRect(Finals.screenWidth*5/6, 0, Finals.screenWidth/6, Finals.screenHeight);
    	System.out.println("paint de canvas"); //quand y'a ca tout marche wtf? ha ba non
    	cam.paint(g);
    }
	
}
