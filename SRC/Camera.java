
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class Camera {

    static double cameraX; //Position en x de la camera en pixel
    static double cameraY; //Position en y de la camera en pixel
    static double cameraWidth = Finals.screenWidth * 5 / 6;
    static double cameraHeight = Finals.screenHeight;
    public static int scale = 30;

    //_____________Constructeur_____________//

    /**
     * Le constructeur centre la camera sur le batiment du joueur au debut de partie.
     */
    public Camera() {
        setLocation(Finals.BASE_LOCATION_X - (cameraWidth / 2.0), Finals.BASE_LOCATION_Y - (cameraHeight / 2.0));
    }


    /**
     * Permet de mettre la camera a une position donnee
     * @param x position en x
     * @param y position en y
     */
    public void setLocation(double x, double y) {
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

    public Point2D getLocation() {
        return new Point2D.Double(cameraX, cameraY);
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int) cameraWidth, (int) cameraHeight);

        // AFFICHAGE DE LA SELECTION
        if (Mouse.dragging) {
            g.setColor(new Color(0, 255, 255));
            double x = Mouse.draggingSquare.getX();
            double y = Mouse.draggingSquare.getY();
            double w = Mouse.draggingSquare.getWidth();
            double h = Mouse.draggingSquare.getHeight();
            g.drawRect((int) (x * scale), (int) (y * scale), (int) (w * scale),
                       (int) (h * scale));
            g.setColor(new Color(0, 255, 255, 30));
            g.fillRect((int) (x * scale), (int) (y * scale), (int) (w * scale),
                       (int) (h * scale));
        }

        for (Item i : Item.aliveItems) {
            if (i.isContained(this))
                i.print(g, cameraX, cameraY, scale);
        }
    }
}
