import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Building extends Item{
    protected int level;
    // _____________CONSTRUCTEURS______________//
    
    /** @param owner 
     * @param levelToSet Choix du level de base
     */
    public Building(Player owner, Point topLeftCorner){
        super(owner, topLeftCorner,2);
        life = hitBox.height*hitBox.height*Finals.LIFE;      
    }
    //________________METHODES_______________//
    
    public boolean isDestroyed(){
        if(life<=0){
		return true;
        }else{
        	return false;
        }
    }


    /**
     *  Cree une unité simple et la rajoute dans le tableau du joueur. elle se dirige au target
     */
    public void GoAndProcreate(){
    	//Choix du point de spawn adapte au point de ralliement
    	Point spawnPoint = new Point();
    	if(target.getX()<=hitBox.x){
    		spawnPoint.x=hitBox.x-Finals.SIDE-1;
    	}else{
    		spawnPoint.x=hitBox.x+hitBox.width+1;
    	}
    	if(target.getY()<=hitBox.y){
    		spawnPoint.y=hitBox.y-Finals.SIDE-1;
    	}else{
    		spawnPoint.y=hitBox.y+hitBox.height+1;
    	}
    	owner.units.add(new SimpleUnit(owner,spawnPoint, target));
    }
}
