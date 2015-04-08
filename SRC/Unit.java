import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.geom.Point2D;

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
    public Point2D getShortTarget(double alpha){
        
        Point2D shortTarget;
        shortTarget = new Point2D.Double();
        
        double x, y;
        x = (double) (target.x - hitBox.getCenterX()) * (double) Finals.DISTANCE_TO_MOVE / this.distanceTo(target);
        y = (double) (target.y - hitBox.getCenterY()) * (double) Finals.DISTANCE_TO_MOVE / this.distanceTo(target);
        
        shortTarget.setLocation( Math.cos(alpha)*x+hitBox.getCenterX(), Math.sin(alpha)*y+hitBox.getCenterY());
        return shortTarget;
    }

    
    /**
     * @param shortTarget
     * @return angle du déplacement par rapport à la droite Objet-Cible
     */
    public double findAngle(Point2D shortTarget){
        return 1;
    }
    
    //public double[] angle possible..............................................
    /**
     * @param shortTarget
     * @return l’unitée peut faire un déplacement unitaire
     */
    public boolean canMove(){
        double r;
        r = this.distanceTo(new Point((int) hitBox.getX(), (int) hitBox.getY()));

        LinkedList<Item> obstacle;
        obstacle = new LinkedList<>(aliveItems);
        obstacle.remove(this);
        
        for(double i=0 ; Math.abs(i*Math.pow(-1.0,i)*Finals.ALPHA)<= 180 ; i++){
            for (int j=0; j <= obstacle.size(); j++){
                
            }
        }
        
        return true;
    }
}
