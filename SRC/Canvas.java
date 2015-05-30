import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
    Player P1;
    Player P2;
    Camera cam;
    Key key;
    Mouse mouse;

    public Canvas() {
        this.setSize(Finals.screenWidth * 5 / 6, Finals.screenHeight);
        this.setBounds(0, 0, Finals.screenWidth * 5 / 6, Finals.screenHeight);
        this.setBackground(Color.WHITE);
        IA.bigining();
        P1 = new Player(Finals.colorPlayer, Finals.BASE_LOCATION, Finals.namePlayer);
        P2 = new Player(Finals.colorIA, new Point2D.Double(40, 40), "Player two FTW");
        key = new Key(P1);
        mouse = new Mouse(P1);
        this.addKeyListener(key);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
        IA.computer = P2;
        IA.player = P1;
        cam = new Camera();
        
        for (int i = 0; i < 9; i++) {
            new SimpleUnit(P1, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(P2, new Point2D.Double(30,42 + 2 * i));
        }

    }

    public void paint(Graphics g) {
        cam.paint(g);
    }

}
