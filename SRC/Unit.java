import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Unit extends Item {
    
    
    
    /**
     * @param owner
     * @param topLeftCorner
     * @param width
     * @param height
     */
    public Unit(Player owner, Point topLeftCorner,int width, int height){
        super(owner, topLeftCorner, width, height);
    }
    
    /**
     * Constructeur pour une hitBox carré
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Unit(Player owner, Point topLeftCorner,int side){
        super(owner, topLeftCorner, side);
        
    }
    
    //________________MÉTHODES_______________//
    
    /**
     * Permet de déplacer une unité vers un point donné.
     *
     * @param Point
     * Point d’arrivée de l’unité
     */
    public abstract void moveTo(Point location);
}
