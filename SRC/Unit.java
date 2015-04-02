import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import java.util.LinkedList;

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
     * @param targetToSet Point d’arrivée de l’unité (objectif)
     * 
     */
    public void setTarget(Point targetToSet){
        target = targetToSet;
    }
    
    /**
     *
     * @return vecteur de déplacement unitaire
     */
    public Point getShortTarget(){
        
        Point shortTarget;
        shortTarget = new Point();

        int i = 0;
        int x, y;
        x = 
            (int) ((double) (target.x - hitBox.getCenterX()) * (double) Finals.DISTANCE_TO_MOVE /
                   this.distanceTo(target) + hitBox.getCenterX());
        y =
            (int) ((double) (target.y - hitBox.getCenterY()) * (double) Finals.DISTANCE_TO_MOVE /
                    this.distanceTo(target) + hitBox.getCenterY());
        double alpha;

        do{
            alpha = (double)Finals.ALPHA*Math.pow(-1.0, i);
            shortTarget.setLocation( Math.cos(alpha)*x, Math.sin(alpha)*y);
            i++;
        }while(!canMoveTo(shortTarget));
        return shortTarget;
    }
    
    public void moveTo(Point location){
        
    }
    
    public boolean canMoveTo(Point shortTarget){
        double r;
        r = this.distanceTo(new Point((int) hitBox.getX(), (int) hitBox.getY()));

        LinkedList<Item> obstacle;
        obstacle = new LinkedList<>(aliveItems);
        obstacle.remove(this);
        for(int i=0 ; i <= obstacle.size() ; i++)
            if (true) //*************************************************** à finir de coder en gérant l’intersection *******************************//
                return false;
        
        return true;
    }
}
