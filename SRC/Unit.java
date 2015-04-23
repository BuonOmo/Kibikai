import java.awt.geom.Point2D;

import java.util.LinkedList;



import java.util.Collection;

//import math.geom2d.conic.Circle2D;

public abstract class Unit extends Item implements Finals {
    public LinkedList <IAHistObj> histoList = new LinkedList <IAHistObj>();
   
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
        x = (double) (target.getX() - hitBox.getCenterX()) * (double) DISTANCE_TO_MOVE / this.distanceTo(target);
        y = (double) (target.getY() - hitBox.getCenterY()) * (double) DISTANCE_TO_MOVE / this.distanceTo(target);
        
        shortTarget.setLocation( Math.cos(alpha)*x+hitBox.getCenterX(), Math.sin(alpha)*y+hitBox.getCenterY());
        return shortTarget;
    }
    
    /**
         * Donne les points intersection entre deux items.
         * @param other Un autre item qui est proche du premier (methode a n'utiliser que si il y a intersection, sinon tableau vide renvoyé).
         * @return tableau de Point 2D contenant les intersections entres les deux cerles entourant les items.
         */
    public Point2D.Double[] getIntersect(Item other){
                        
        double x0 = this.getCenter().getX();
        double y0 = this.getCenter().getY();
        double x1 = other.getCenter().getX();
        double y1 = other.getCenter().getY();
        
        double x0Coin = this.hitBox.getX();
        double y0Coin = this.hitBox.getY();
        double x1Coin = other.hitBox.getX();
        double y1Coin = other.hitBox.getY();
        
        double R0 = Math.sqrt((x0Coin-x0)*(x0Coin-x0)+(y0Coin-y0)*(y0Coin-y0));
        double R1 = Math.sqrt((x1Coin-x1)*(x1Coin-x1)+(y1Coin-y1)*(y1Coin-y1));
        
        double deltaX = x0-x1;
        double deltaY = y0-y1;
             
        double Xa;
        double Ya;
        double Xb;
        double Yb;
        
        if(y1!=y0){
             
            double N = (R1*R1-R0*R0-x1*x1+x0*x0-y1*y1+y0*y0)/(2*deltaY);
            double A = (deltaX/deltaY)*(deltaX/deltaY)+1;
            double B = 2*(y0-N)*(deltaX/deltaY)-2*x0;
            double C = y0*y0+x0*x0+N*N-R0*R0-2*y0*N;
             
            double DELTA = B*B-4*A*C;
             
            if(DELTA>=0){
                DELTA = Math.sqrt(DELTA);
                Xa=(-B-DELTA)/(2*A);
                Ya=N-Xa*(deltaX/deltaY);
                Xb=(-B+DELTA)/(2*A);
                Yb=N-Xb*(deltaX/deltaY);
                Point2D.Double[] Intersects = {new Point2D.Double(Xa,Ya) , new Point2D.Double(Xb,Yb)};
                return Intersects;
            }
            else{
                return null;
            }
             
        }
        else{
            Xa=(R1*R1-R0*R0-x1*x1+x0*x0)/(2*deltaX);        
            double A = 1;
            double B = -2*y1;
            double C = x1*x1+Xa*Xa-2*x1*Xa-R1*R1;
            double DELTA = B*B - 4*A*C;
            if(DELTA>=0){
                DELTA = Math.sqrt(DELTA);
                Ya=(-B-DELTA)/(2*A);
                Yb=(-B+DELTA)/(2*A);
                Point2D.Double[] Intersects = {new Point2D.Double(Xa,Ya) , new Point2D.Double(Xa,Yb)};
                return Intersects;
            }
            else{
                return null;
            } 
        }       
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

        return Math.acos((zero.getX()*sT.getX()+ zero.getY()*sT.getY())/(DISTANCE_TO_MOVE*DISTANCE_TO_MOVE));
    }
    
        
    /**
     * @param shortTarget
     * @return l’unitée peut faire un déplacement unitaire
     */
    public boolean canMove(){
        double r;
        r = this.distanceTo(new Point2D.Double(hitBox.getX(), hitBox.getY()));

        LinkedList<Item> obstacle;
        obstacle = new LinkedList<Item>(aliveItems);
        obstacle.remove(this);
        
        for(double i=0 ; Math.abs(i*Math.pow(-1.0,i)*ALPHA)<= 180 ; i++){
            for (int j=0; j <= obstacle.size(); j++){
                
            }
        }
        
        return true;
    }
}
