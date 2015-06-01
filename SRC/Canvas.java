import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
    Player P1;
    Player P2;
    Camera cam;
    
    Mouse mouse;
    

    public Canvas() {
        
        this.setBackground(Finals.BACKGROUND_COLOR);
        IA.bigining();
        P1 = new Player("green", Finals.BASE_LOCATION, Finals.namePlayer);
        P2 = new Player("red", new Point2D.Double(40, 40), "Player two FTW");
        mouse = new Mouse(P1);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
        IA.computer = P2;
        IA.player = P1;
        cam = new Camera(this);
        new SimpleUnit(P1, new Point2D.Double(Finals.BASE_LOCATION_X + P1.base.hitbox.getHeight() / 2.0, Finals.BASE_LOCATION_Y));
        for (int i = 0; i < 9; i++) {
            new SimpleUnit(P1, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(P2, new Point2D.Double(30,42 + 2 * i));
        }

    }

    public void paint(Graphics g) {
        cam.paint(g);
    }
}
