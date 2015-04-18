import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.LinkedList;
<<<<<<< HEAD
import java.awt.geom.*;
import java.lang.Object;
public abstract class Unit extends Item {
<<<<<<< HEAD
=======
=======


public abstract class Unit extends Item {
    public LinkedList <IAHistObj> histoList = new LinkedList <IAHistObj>();
>>>>>>> 0391b1001a27cd79afee7343d962b5114a1aee74
    
>>>>>>> origin/master
    
   
    /**
     * @param owner
     * @param topLeftCorner
     * @param width
     * @param height
     */
    public Unit(Player owner, Point2D topLeftCorner,int width, int height){
        super(owner, topLeftCorner, width, height);
    }
    
    /**
     * Constructeur pour une hitBox carré
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Unit(Player owner, Point2D topLeftCorner,int side, Point2D targetToSet){
        super(owner, topLeftCorner, side);
        target = targetToSet;
        
    }
    
    //________________MÉTHODES_______________//
    
    /**
     * Permet de déplacer une unité vers un point donné.
     *
     * @param targetToSet Point d’arrivée de l’unité (objectif)
     * 
     */
    public void setTarget(Point2D targetToSet){
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
        x = (double) (target.getX() - hitBox.getCenterX()) * (double) Finals.DISTANCE_TO_MOVE / this.distanceTo(target);
        y = (double) (target.getY() - hitBox.getCenterY()) * (double) Finals.DISTANCE_TO_MOVE / this.distanceTo(target);
        
        shortTarget.setLocation( Math.cos(alpha)*x+hitBox.getCenterX(), Math.sin(alpha)*y+hitBox.getCenterY());
        return shortTarget;
    }
    
    public Point2D getIntersect(Item other){
    	 //double x = this.getCenter().getX();
    	// double y = this.getCenter().getY();
         Circle2D zone = new Circle2D(this.getCenter(),Finals.DISTANCE_TO_MOVE);
    	
         //x = other.getCenter().getX();
    	 //y = other.getCenter().getY();
    	 Circle2D obstacle = new Circle2D(other.getCenter(),Finals.DISTANCE_TO_MOVE);
    	return circlesIntersections(zone, obstacle);   	 
    }
    
    /**
     * @param shortTarget
     * @return angle du déplacement par rapport à la droite Objet-Cible
     */
    public double findAngle(Point2D shortTarget){
        Point2D zero;
        zero = getShortTarget(0.0);
        zero.setLocation(zero.getX()-hitBox.getCenterX(), zero.getY()-hitBox.getCenterY());

        Point2D sT;
        sT = new Point2D.Double(shortTarget.getX() - hitBox.getCenterX(), shortTarget.getY() - hitBox.getCenterY());

        return Math.acos((zero.getX()*sT.getX()+ zero.getY()*sT.getY())/(Finals.DISTANCE_TO_MOVE*Finals.DISTANCE_TO_MOVE));
    }
    
        
    /**
     * @param shortTarget
     * @return l’unitée peut faire un déplacement unitaire
     */
    public boolean canMove(){
        double r;
<<<<<<< HEAD
        r = this.distanceTo(new Point2D(hitBox.getX(), hitBox.getY()));
=======
        r = this.distanceTo(new Point((int) hitBox.getX(), (int) hitBox.getY()));
        r = this.distanceTo(new Point2D.Double(hitBox.getX(), hitBox.getY()));
>>>>>>> 0391b1001a27cd79afee7343d962b5114a1aee74

        LinkedList<Item> obstacle;
        obstacle = new LinkedList<Item>(aliveItems);
        obstacle.remove(this);
        
        for(double i=0 ; Math.abs(i*Math.pow(-1.0,i)*Finals.ALPHA)<= 180 ; i++){
            for (int j=0; j <= obstacle.size(); j++){
                
            }
        }
        
        return true;
    }
}
