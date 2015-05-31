import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;

import java.awt.image.BufferedImage;

import java.awt.image.ImageObserver;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements ImageObserver {
    Player P1;
    Player P2;
    Camera cam;
    Key key;
    Mouse mouse;
    /*
    BufferedImage image = null;
    File f;
    List<BufferedImage> images = new ArrayList<>(8);
    String path;
    static int c = 0;
    */
    

    public Canvas() {
        /*
        for (int i=1; i<9; i++){
            path = "IMG/animation_soldier/"+i+".png";
            f = new File(path);
            try{
                image = ImageIO.read(f);
                //images.add(ImageIO.read(f));
            }catch (IOException e) {
           System.out.println("Ça marche pas");
            }
            images.add(image);
            
        }
        */
        this.setBackground(Finals.BACKGROUND_COLOR);
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
        cam = new Camera(this);
        new SimpleUnit(P1, new Point2D.Double(Finals.BASE_LOCATION_X + P1.base.hitbox.getHeight() / 2.0, Finals.BASE_LOCATION_Y));
        for (int i = 0; i < 9; i++) {
            new SimpleUnit(P1, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(P2, new Point2D.Double(30,42 + 2 * i));
        }

    }

    public void paint(Graphics g) {
        cam.paint(g);
        /*
        if (c<images.size())
        g.drawImage(images.get(c), 0, 0, null);
        else if (c>15)
            c=-1;
        c++;
        */
    }
}
