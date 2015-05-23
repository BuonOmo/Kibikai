
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Camera extends Canvas{
	
	double cameraX; //Position en x de la camera en pixel
	double cameraY; //Position en y de la camera en pixel
    Point2D.Double cameraLocation;
	double cameraWidth = Finals.screenWidth;
	double cameraHeight = Finals.screenHeight;
	    
    //_____________Constructeur_____________//

	/**
	 * Le constructeur centre la camera sur le batiment du joueur au debut de partie.
	 */
    public Camera() {
    	
    	cameraX = Finals.BASE_LOCATION_X - cameraWidth;
    	cameraY = Finals.BASE_LOCATION_Y - cameraHeight;
    	cameraLocation = new Point2D.Double(cameraX, cameraY);
    	
    	//if the camera is at the right or left edge lock it to prevent a black bar
    	if (cameraX < 0) 
    		cameraX = 0;
    	
    	if (cameraX + cameraWidth > Finals.WIDTH) 
    		cameraX = Finals.WIDTH - cameraWidth;
    	
    	//if the camera is at the top or bottom edge lock it to prevent a black bar
    	if (cameraY < 0) 
    		cameraY = 0;
    	
    	if (cameraY + cameraHeight > Finals.HEIGTH)
    		cameraY = Finals.HEIGTH - cameraHeight;
	}
    
    public void setLocation (double x, double y){
    	cameraX = x;
    	cameraY = y;
    	cameraLocation = new Point2D.Double(cameraX, cameraY);
    }
    
    public void deplacementCamera (double dx, double dy){
    	cameraX += dx;
    	cameraY += dy;
    	cameraLocation = new Point2D.Double(cameraX, cameraY);
    }
    
    /**
     * @return la partie de la carte que la camera veut afficher
     */
    public Rectangle2D getMap(){
    	Rectangle2D partMap = new Rectangle2D.Double(cameraX, cameraY, Finals.screenWidth, Finals.screenHeight);
    	return partMap;
    }
    
    public void paint (Graphics g){
    	
    }

}
