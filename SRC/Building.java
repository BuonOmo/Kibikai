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
    public Building(Player owner, Point2D topLeftCorner){
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
        double x,y;
    	if(target.getX()<=hitBox.getX()){
    	    x = hitBox.getX()-Finals.SIDE-1;
    	    if(target.getY()<=hitBox.getY()){
    	            y=hitBox.getY()-Finals.SIDE-1;
    	    }else{
    	            y=hitBox.getY()+hitBox.getHeight()+1;
    	    }
    	}else{
            x=hitBox.getX()+hitBox.getWidth()+1;
            if(target.getY()<=hitBox.getY()){
                    y=hitBox.getY()-Finals.SIDE-1;
            }else{
                    y=hitBox.getY()+hitBox.getHeight()+1;
            }
        }
        
        spawnPoint.setLocation(x, y);
    	owner.units.add(new SimpleUnit(owner,spawnPoint, target));
    }
}
