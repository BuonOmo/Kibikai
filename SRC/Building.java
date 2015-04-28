import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Building extends Item{
    protected int level;
    // _____________CONSTRUCTEURS______________//
    
    /**
     * @param owner possesseur
     * @param topLeftCorner situation du batiment
     */
    public Building(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner,2);
        life = Math.pow(level + 1, 2)*LIFE;      
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
    	owner.units.add(new SimpleUnit(owner,spawnPoint, target));
    }
    
    /**
     * Méthode permettant l’évolution d’un batiment.
     */
    public void grow(){
        
        level+= 1;
        //TODO gerer les intersection avant de faire la méthode grow
        hitBox.setRect(hitBox.getX() - SIDE/2.0, 
                       hitBox.getY() - SIDE/2.0, 
                       hitBox.getWidth() + SIDE, 
                       hitBox.getHeight() + SIDE);
        
        life = Math.pow(level + 1, 2)*LIFE;
    }
    
    public void execute(int time){
        
        // j’ai choisi 30 car il divisible par level (pour level <= 6)
        if ((double)time %(1/ (double)level)*30 == 0)
            goAndProcreate();
        
    }
}
