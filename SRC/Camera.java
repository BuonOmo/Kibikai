
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import java.util.Iterator;

import javax.swing.JPanel;

public class Camera extends JPanel{

    static double cameraX; //Position en x de la camera en pixel
    static double cameraY; //Position en y de la camera en pixel
    static double cameraWidth = Finals.screenWidth * 5 / 6;
    static double cameraHeight = Finals.screenHeight;
    public static int scale = 30;
    
    Mouse mouse;
    Key key;
    //_____________Constructeur_____________//

    /**
     * Le constructeur centre la camera sur le batiment du joueur au debut de partie.
     */
    public Camera() {
        setLocation(Finals.BASE_LOCATION_X - (cameraWidth / 2.0), Finals.BASE_LOCATION_Y - (cameraHeight / 2.0));
        this.setBackground(Finals.BACKGROUND_COLOR);
        mouse = new Mouse(Game.human);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
    }


    /**
     * Permet de mettre la camera a une position donnee
     * @param x position en x
     * @param y position en y
     */
    public static void setLocation(double x, double y) {
        cameraX = x;
        cameraY = y;

        //Locks the camera when it's at the right or left edge
        if (cameraX + cameraWidth > Finals.WIDTH * scale)
            cameraX = Finals.WIDTH * scale - cameraWidth;

        if (cameraX < 0)
            cameraX = 0;

        //Locks the camera when it's at the top or bottom edge
        if (cameraY + cameraHeight > Finals.HEIGHT * scale)
            cameraY = Finals.HEIGHT * scale - cameraHeight;

        if (cameraY < 0)
            cameraY = 0;

    }


    /**
     * Permet de bouger la camera selon un petit deplacement en x et/ou y
     * @param dx déplacement selon x
     * @param dy déplacement selon y
     */
    public void moveCamera(double dx, double dy) {

        setLocation(cameraX + dx, cameraY + dy);

    }

    public Point2D getPosition() {
        return new Point2D.Double(cameraX, cameraY);
    }
    
    public static int getXOnScreen(double xInGame){
        return (int) ((xInGame - cameraX)*scale);
    }
    
    public static int getYOnScreen(double yInGame){
        return (int) ((yInGame - cameraY)*scale);
    }
    public static void setScale(int i){
        scale+= i;
        if (cameraX + cameraWidth > Finals.WIDTH * scale)
            scale = (int) (cameraX + cameraWidth) / Finals.WIDTH;

        //Locks the camera when it's at the top or bottom edge
        if (cameraY + cameraHeight > Finals.HEIGHT * scale)
            scale = (int) (cameraY + cameraHeight) / Finals.HEIGHT;
        
    }

    public void paint(Graphics g) {

        // AFFICHAGE DU FOND
        g.setColor(Finals.BACKGROUND_COLOR);
        g.fillRect(0, 0, (int) cameraWidth, (int) cameraHeight);

        // AFFICHAGE DE LA SELECTION
        if (Mouse.dragging) {
            g.setColor(new Color(0, 255, 255));
            double x = Mouse.draggingSquare.getX() - cameraX;
            double y = Mouse.draggingSquare.getY() - cameraY;
            double w = Mouse.draggingSquare.getWidth();
            double h = Mouse.draggingSquare.getHeight();
            g.drawRect((int) (x * scale), (int) (y * scale), (int) (w * scale),
                       (int) (h * scale));
            g.setColor(new Color(0, 255, 255, 30));
            g.fillRect((int) (x * scale), (int) (y * scale), (int) (w * scale),
                       (int) (h * scale));
        }
        boolean tab[][] = new boolean [(int)cameraWidth][(int)cameraHeight];
        BufferedImage newImage = new BufferedImage((int)cameraWidth,(int)cameraHeight,BufferedImage.TYPE_INT_ARGB);
        tab.equals(true);
        
        for (int i = 0 ;i<cameraWidth; i++)
            for (int j = 0 ; j<cameraHeight ; j++)
                tab[i][j]=true ;
        
        for (Item i : Item.aliveItems) {
            if (i.isContained()){
                i.print(g);

            }
        }
        for (Unit u : Unit.dyingUnits){
            u.printDieAnimation(g);
        }
        
        for (Item i : Game.human.items) {
            if (i.isContained()){
                i.fog(cameraX,cameraY, tab, scale);
            }
        }
        
        for (Unit i : Unit.dyingUnits) {
            if (i.isContained() && i.owner.isHuman()){
                i.fog(cameraX,cameraY, tab, scale);
                //TODO gerer l’erreur de modification concourante de dyingUnits
            }
        }
        int RGB =Finals.FOG_COLOR.getRGB();
        for (int i = 0 ;i<cameraWidth; i++)
            for (int j = 0 ; j<cameraHeight ; j++)
                if (tab[i][j])
                    newImage.setRGB(i, j,RGB);
        g.drawImage(newImage,0,0,this); 
        IA.computer.base.print(g);
                
    }
}
