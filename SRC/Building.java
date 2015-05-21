import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.security.acl.Owner;

import java.util.LinkedList;

public class Building extends Item{
    //______________ATTRIBUTS__________________//
    
    public static LinkedList<Building> buildings = new LinkedList<Building>();

    // _____________CONSTRUCTEURS______________//

    /**
     * @param owner possesseur
     * @param topLeftCorner position du batiment
     * @param side taille (i.d. niveau) du batiment
     */
    public Building(Player owner, Point2D topLeftCorner, double side){
        super(owner, topLeftCorner, side);
        life = Math.pow(side, 2)*LIFE;
        //target.setLocation(topLeftCorner.getX() + 2*SIDE, topLeftCorner.getY() + 2*SIDE);
        setTarget(new Point2D.Double(30,30)); //___________________________________(test)
        buildings.add(this);
    }
    
    /**
     * @param owner possesseur
     * @param topLeftCorner postion du batiment
     */
    public Building(Player owner, Point2D topLeftCorner){
        this(owner, topLeftCorner,4);
    }
    //________________METHODES_______________//
    
    /**
     *  Cree une unité simple et la rajoute dans le tableau du joueur. elle se dirige au target
     */
    public void goAndProcreate(){
        
        
        // on laisse tout ce calcul ? parcequ’il sert à rien vu qu’on gère pas les intersections..
        double x,y;
    	if(target.getX()<=hitBox.getX()){
    	    x = hitBox.getX()-SIDE-1;
    	    if(target.getY()<=hitBox.getY()){
    	            y=hitBox.getY()-SIDE-1;
    	    }else{
    	            y=hitBox.getY()+hitBox.getHeight()+1;
    	    }
    	}else{
            x=hitBox.getX()+hitBox.getWidth()+1;
            if(target.getY()<=hitBox.getY()){
                    y=hitBox.getY()-SIDE-1;
            }else{
                    y=hitBox.getY()+hitBox.getHeight()+1;
            }
        }
        
        if (owner.simpleUnits.size()<Finals.NUMBER_MAX_OF_SIMPLEUNIT) {
            new SimpleUnit(owner,getCenter(),new Point.Double(x,y) );
        }

    }
    
    /**
     * Gère la vie d’un batiment et sa taille.
     * @param amount vie ajoutée (- pour en enlever)
     */
    public boolean getLife(double amount){
        
        life+= amount;
        
        //TODO gerer les intersection lors du grandissement
        double newSide = Math.sqrt(life/LIFE);
        double shift = (newSide - hitBox.getHeight())/4.0;
        hitBox.setRect(getCenter().getX() - newSide/2.0, 
                       getCenter().getY() - newSide/2.0, 
                       newSide, 
                       newSide);
        if (life <=0)
            return this.isDestructed();
        
        return false;
    }
    
    public static boolean gameOver(){
        for (Building b : buildings)
            if (b.isDead())
                return true;
        return false;
    }
    
    public boolean execute(){
        
        actualiseTarget();
        
        // pourquoi ça a été mis en commentaire ? C’est pour éviter les abus et division par 0
        if (life > LIFE){

            if (((UI.time)%(int) (4 /(hitBox.getHeight()*UNIT_PER_SECOND)+1) == 0)){
                goAndProcreate();                
            }
        }
        return false;
    }
}
