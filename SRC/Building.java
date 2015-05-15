import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.security.acl.Owner;

public class Building extends Item{
	// _____________VARIABLE______________//
	
    boolean destructed; // attribut inutile, il y a déja une méthode isDestructed qui revoit vrai si il est mort
	
    // _____________CONSTRUCTEURS______________//

    /**
     * @param owner possesseur
     * @param topLeftCorner position du batiment
     * @param side taille (i.d. niveau) du batiment
     */
    public Building(Player owner, Point2D topLeftCorner, double side){
        super(owner, topLeftCorner, side);
        life = Math.pow(side, 2)*LIFE;
        destructed = false;
        //target.setLocation(topLeftCorner.getX() + 2*SIDE, topLeftCorner.getY() + 2*SIDE);
        setTarget(new Point2D.Double(30,30)); //(test)
    }
    
    /**
     * @param owner possesseur
     * @param topLeftCorner postion du batiment
     */
    public Building(Player owner, Point2D topLeftCorner){
        this(owner, topLeftCorner,2);
    }
    //________________METHODES_______________//
    
    /**
     *  Cree une unité simple et la rajoute dans le tableau du joueur. elle se dirige au target
     */
    public void goAndProcreate(){
    	
        //Choix du point de spawn adapte au point de ralliement
    	Point2D spawnPoint = new Point2D.Double();
        
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
        
        spawnPoint.setLocation(x, y);

        if (owner.simpleUnits.size()<Finals.NUMBER_MAX_OF_SIMPLEUNIT) {
            SimpleUnit su = new SimpleUnit(owner,getCenter(),new Point.Double(15,15) );
        if (owner == IA. computer )new SimpleUnitGroup(su);
        }
    }
    
    /**
     * Méthode permettant le soin et l’amélioration d’un batiment
     */
    public void increase(){
        
        //TODO gerer les intersection lors du grandissement
        double newSide = Math.sqrt( Math.pow(hitBox.getHeight(),2) + Math.pow(SIDE, 2));
        double shift = (newSide - hitBox.getHeight())/4.0;
        hitBox.setRect(hitBox.getX() - shift/2, 
                       hitBox.getY() - shift/2, 
                       newSide, 
                       newSide);
        
        life = Math.pow(newSide, 2)*LIFE;
    }
    
    /**
     * Méthode permettant la destruction et la regression d’un batiment
     */
    public void decrease(){
        
        life-= DAMAGE;
        if((life)>0){
        double newSide = Math.sqrt(life/LIFE);
        double shift = (newSide - hitBox.getHeight())/4.0;
        hitBox.setRect(hitBox.getX() - shift/2, 
                       hitBox.getY() - shift/2, 
                       newSide, 
                       newSide);
        }else{
        	destructed = true;
        	/// TODO gerer la defaite du player en arretant le jeu et en lancant une derniere fois l'execute des IA pour prendre la defaite/victoire en compte.
        }
    }
    
    /**
     * Gère la vie d’un batiment et sa taille.
     * @param amount vie ajoutée (- pour en enlever)
     */
    public void getLife(double amount){
        life+= amount;
        
        //TODO gerer les intersection lors du grandissement
        double newSide = Math.sqrt(life/LIFE);
        double shift = (newSide - hitBox.getHeight())/4.0;
        hitBox.setRect(hitBox.getX() - shift/2, 
                       hitBox.getY() - shift/2, 
                       newSide, 
                       newSide);
        if (life <=0)
            this.isDestructed();
    }
    
    public void execute(){
        
        actualiseTarget();
        
        // TODO trouver une variable time qui marche............................
        //if (life > LIFE){

            if (((UI.time)%((int) 2 /(hitBox.getHeight()*UNIT_PER_SECOND)+1) == 0)){
                goAndProcreate();
                System.out.println("coucou");
                System.out.println (Item.aliveItems+owner.toString());
                
            }
        //}
    }
}
