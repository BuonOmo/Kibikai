import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.util.LinkedList;

public abstract class Item {
    Point target;
    int life;
    Color color;
    Rectangle hitBox;
    Player owner;
    static LinkedList<Item> aliveItems = new LinkedList<Item>();
    static LinkedList<Item> deadItems = new LinkedList<Item>();
    
    // _____________CONSTRUCTEURS______________//
    
    /**
     * @param owner Possesseur de l’objet
     * @param hitBoxToSet
     */
    public Item(Player ownerToSet, Rectangle hitBoxToSet, Point targetToSet){
        color = ownerToSet.color;
        owner = ownerToSet;
        hitBox = hitBoxToSet;
        target = targetToSet;
        aliveItems.add(this);
    }

    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param width largeur de la hitBox
     * @param height hauteur de la hitBox
     */
    public Item(Player ownerToSet, Point topLeftCorner,int width, int height){
        this(ownerToSet, new Rectangle(topLeftCorner, new Dimension(Finals.SIDE*width,Finals.SIDE*height)),topLeftCorner);
        color = ownerToSet.color;
        hitBox = new Rectangle(topLeftCorner, new Dimension(Finals.SIDE*width,Finals.SIDE*height));
        target = topLeftCorner;
    }
    
    /**
     * Constructeur pour une hitBox carré
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Item(Player owner, Point topLeftCorner,int side){
        color = owner.color;
        hitBox = new Rectangle(topLeftCorner, new Dimension(Finals.SIDE*side,Finals.SIDE*side));
        target = topLeftCorner;
        
    }
    
    //________________MÉTHODES_______________//
    
    /**
     * Diminue la vie.
     */
    public void isAttacked(){
        life--;
    }

    /**
     * @param g
     */
    /**
     * Retourne une liste des unit�s dans le perimetre entourant l'unit�.
     * @param radius : Rayon delimitant le perimetre de scan.
     * 
     */
    public LinkedList<Unit> scanPerimeter(int radius, Player player){
    	LinkedList<Unit> otherUnits = new LinkedList<Unit>();
    	for(int i=0;i<player.units.size();i++){
    		if(this.distanceTo(player.units.get(i))<=radius){
    			otherUnits.add(player.units.get(i));
    		}
    	}
    	return otherUnits;   	
    }
    
    /**
     * 
     * @param p 
     * @return distance du centre de l’unité au point p en double
     */
    public double distanceTo(Point p){
        double x = hitBox.getCenterX(), y = hitBox.getCenterY();
        
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y -y)*(p.y-y));   
    }
    
    public double distanceTo(Unit other){
    	double d;
    	double x1= this.hitBox.getX();
    	double y1=this.hitBox.getY();
    	double x2=other.hitBox.getX();
    	double y2=other.hitBox.getY();
    	d=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    	return d;
    }
    
    public Point2D getCenter(){
        return new Point2D.Double(hitBox.getCenterX(),hitBox.getCenterY());
    }
    
    public boolean isDestructed(){
        if (life <= 0){
            if (!deadItems.contains(this)){
                deadItems.add(this);
                aliveItems.remove(this);
            }
            return true;
        }
        return false;
    }
    
    public void print(Graphics g){
        g.setColor(color);
        // TODO virer ce putain de 3 et mettre un truc cohérant pour les arcs de cercle
        g.fillRoundRect(hitBox.x*Finals.scale, hitBox.y*Finals.scale, hitBox.width*Finals.scale, hitBox.height*Finals.scale,3*Finals.scale,3*Finals.scale);
    }
}
