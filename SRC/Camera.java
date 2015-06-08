
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Camera extends JPanel{

    public static int scale = 30;
    static double cameraX; //Position en x de la camera
    static double cameraY; //Position en y de la camera
    static double cameraWidth = Finals.screenWidth *4/5;
    static double cameraHeight = Finals.screenHeight;
    
    Mouse mouse;    
    
    //_____________CONSTRUCTOR_____________//

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

    
	//______________METHODS______________//
    
    /**
     * Permet de mettre la camera a une position donnee
     * @param x position en x
     * @param y position en y
     */
    public static void setLocation(double x, double y) {
        cameraX = x;
        cameraY = y;

        //Locks the camera when it's at the right or left edge
        if (cameraX + cameraWidth/scale > Finals.WIDTH )
            cameraX = Finals.WIDTH  - cameraWidth/scale;

        if (cameraX < 0)
            cameraX = 0;

        //Locks the camera when it's at the top or bottom edge
        if (cameraY + cameraHeight/scale> Finals.HEIGHT)
            cameraY = Finals.HEIGHT - cameraHeight/scale;

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

    
    /**
     * @return La position de la camera
     */
    public Point2D getPosition() {
        return new Point2D.Double(cameraX, cameraY);
    }
    
    
    /**
     * @param xInGame position en x dans la carte
     * @return position en x a l'ecran
     */
    public static int getXOnScreen(double xInGame){
        return (int) (getLengthOnScreen(xInGame) - cameraX*scale);
    }
    
    
    /**
     * @param yInGame position en y dans la carte
     * @return position en y a l'ecran
     */
    public static int getYOnScreen(double yInGame){
        return (int) (getLengthOnScreen(yInGame) - cameraY*scale);
    }
    
    
    /**
     * @param lengthInGame largeur dans la carte
     * @return largeur reelle
     */
    public static int getLengthOnScreen(double lengthInGame){
        return (int) (lengthInGame*scale);
    }
    
    
    /**
     * @param XOnScreen position en x a l'ecran
     * @return position en x dans la carte
     */
    public static double getXInGame(int XOnScreen){
        return getLengthInGame(XOnScreen) + cameraX;
    }
    
    
    /**
     * @param YOnScreen position en y a l'ecran
     * @return position en y dans la carte
     */
    public static double getYInGame(int YOnScreen){
        return getLengthInGame(YOnScreen) + cameraY;
    }
    
    
    /**
     * @param lengthOnScreen largeur reelle
     * @return largeur dans la carte
     */
    public static double getLengthInGame(int lengthOnScreen){
        return lengthOnScreen / scale;
    }
    

    public void paint(Graphics g) {

        // AFFICHAGE DU FOND
        g.setColor((Listeners.louHammel) ? new Color((int)(Math.random()*255),
                                                     (int)(Math.random()*255),
                                                     (int)(Math.random()*255)) : 
                                            Finals.BACKGROUND_COLOR);
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
            else if (Building.buildings.contains(i))
                i.print(g);
        }
        
        for (Unit u : Unit.dyingUnits){
            u.printDieAnimation(g);
        }
        if (Game.fog){ // ___________________________________________________________________//        
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
            
            Game.computer.base.printOverFog(g);
        } //________________________________________________________//
        
        if (Game.over){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Impact",100,200));
            g.drawString("YOU "+Game.whoWin,(int) cameraWidth/2 - 700,(int) cameraHeight/2 + 100);
        }    
    }
}
