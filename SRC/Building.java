import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Building extends Item{
    protected int level;
    // _____________CONSTRUCTEURS______________//
    
    /** @param owner 
     * @param levelToSet Choix du level de base
     */
    public Building(Player owner, Point topLeftCorner){
        super(owner, topLeftCorner,2);
        life = hitBox.getHeight()*hitBox.getHeight()*Finals.LIFE;      
    }
    //________________METHODES_______________//
    
    /**
     *  Cree une unité simple et la rajoute dans le tableau du joueur. elle se dirige au target
     */
    public void GoAndProcreate(){
    	//Choix du point de spawn adapte au point de ralliement
    	Point2D spawnPoint = new Point2D.Double();      
    	if(target.getX()<=hitBox.getX()){
    		spawnPoint.x=hitBox.getX()-Finals.SIDE-1;
    	}else{
    		spawnPoint.x=hitBox.getX()+hitBox.getWidth()+1;
    	}
    	if(target.getY()<=hitBox.getY()){
    		spawnPoint.y=hitBox.getY()-Finals.SIDE-1;
    	}else{
    		spawnPoint.y=hitBox.getY()+hitBox.getHeight()+1;
    	}
    	owner.units.add(new SimpleUnit(owner,spawnPoint, target));
    }
}
