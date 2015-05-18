import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.LinkedList;

public abstract class Item implements Finals{
    
    //______________ATTRIBUTS________________//
    
    Point2D target;
    Item targetI;
    double life;
    Color color;
    Rectangle2D hitBox;
    Player owner;
    double radius;
    static LinkedList<Item> aliveItems = new LinkedList<Item>();
    static LinkedList<Item> deadItems = new LinkedList<Item>();
    
    // _____________CONSTRUCTEURS______________//
    
    /**
     * @param owner Possesseur de l’objet
     * @param hitBoxToSet
     */
    public Item(Player ownerToSet, Rectangle2D hitBoxToSet, Point2D targetToSet){
        color = ownerToSet.color;
        owner = ownerToSet;
        hitBox = hitBoxToSet;
        target = targetToSet;
        aliveItems.add(this);
        radius = this.distanceTo(new Point2D.Double(hitBox.getX(), hitBox.getY()));
    }

    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param width largeur de la hitBox
     * @param height hauteur de la hitBox
     */
    public Item(Player ownerToSet, Point2D topLeftCorner,double width, double height){
        this(ownerToSet, new Rectangle2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), width, height), topLeftCorner);
    }
    
    /**
     * Constructeur pour une hitBox carré.
     * @param ownerToSet Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Item(Player ownerToSet, Point2D topLeftCorner,double side){
        this(ownerToSet, new Rectangle2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), side, side), topLeftCorner);
    }
    
    
    //________________MÉTHODES_______________//
    
    
    /**
     * Gère la vie d’une unité (et pour un batiment sa taille).
     * @param amount vie ajoutée (- pour en enlever)
     */
    public abstract void getLife(double amount);
    
    /**
     * Permet de déplacer une unité vers un point donné.
     * @param targetToSet Point d’arrivée de l’unité (objectif)
     */
    public void setTarget(Point2D targetToSet){
        targetI = null;
        target = targetToSet;
    }

    /**
     * @param targetToSet 
     */
    public void setTarget(Item targetToSet){
        targetI = targetToSet;
        target = targetToSet.getCenter();
    }
    
    /**
     * Permet de suivre une cible mouvante.
     */
    public void actualiseTarget (){
        if (targetI != null)
            target = targetI.getCenter();
    }
    
    /**
     * Retourne une liste des unités dans le perimetre entourant l'unité.
     * @param radius : Rayon delimitant le perimetre de scan.
     * @param player
     * @return unités dans le périmetre.
     */
    public LinkedList<Unit> scanPerimeter(int radius, Player player){
    	LinkedList<Unit> otherUnits = new LinkedList<Unit>();
        for(Unit i : player.units){
    		if(this.distanceTo(i)<=radius){
    			otherUnits.add(i);
    		}
    	}
    	return otherUnits;   	
    }
    
    /**
     * 
     * @param p 
     * @return distance du centre de l’unité au point p en double
     */
    public double distanceTo(Point2D p){
        double x = hitBox.getCenterX(), y = hitBox.getCenterY();
        
        return Math.sqrt((p.getX() - x)*(p.getX() - x) + (p.getY() -y)*(p.getY()-y));   
    }
    
    public double distanceTo(Item other){
    	double d;
    	double x1= this.hitBox.getCenterX();
    	double y1=this.hitBox.getCenterY();
    	double x2=other.hitBox.getCenterX();
    	double y2=other.hitBox.getCenterY();
    	d=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    	return d;
    }

    /**
     * 
     * @param other unité proche
     * @param range portée de l’attaque ou du soin (ou autre)
     * @return vraie si la distance entre les deux extremités des objets sont plus petites que range
     */
    public boolean isCloseTo(Item other, double range){
        if (distanceTo(other) <= radius + other.radius + range)
            return true;
        return false;
    }
    
    public Point2D getCenter(){
        return new Point2D.Double(hitBox.getCenterX(),hitBox.getCenterY());
    }
    
    
    public Item[] getNClosestObject(int n, String type){
        LinkedList<Item> toCheck = new LinkedList<Item>(aliveItems);
        toCheck.remove(this);
        Item[] toReturn = new Item[n];
        toReturn[0] = toCheck.getFirst();
        for (Item i : toCheck){
            if (distanceTo(i)<= distanceTo(toReturn[0])){
                for (int j = 0; j<n-1; j++)
                    toReturn[j+1] = toReturn[j];
                toReturn[0] = i;
            }
            
        }
        return toReturn;
    }
    
    public void isDestructed(){
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)){
            deadItems.add(this);
            aliveItems.remove(this);
        }
    }

    /**
     * @return vraie si la vie est en dessous de 0
     */
    public boolean isDead(){
        return (life <= 0);
    }
    
    public abstract void execute();
    
    public Color getColor(){
        return color;
    }
    
    public void print(Graphics g){
        g.setColor(getColor());
        // TODO virer ce putain de 3 et mettre un truc cohérent pour les arcs de cercle
        g.fillRoundRect( (int)(hitBox.getX()*scale), 
                         (int)(hitBox.getY()*scale), 
                         (int)(hitBox.getWidth()*scale), 
                         (int)(hitBox.getHeight()*scale),
                         (10),
                         (10));
    }
    
    public String toString(){
        return this.getClass().getName();
    }
    /**
     * gère les problèmes rencontrés par des objets (inutile !).
     * @param type type d’erreur
     */
    public void error (int type){
        
        String msg;
        
        switch (type){
            
            // erreur dans SimpleUnit.heal()
            case 1:
                msg = "l'objet a�soigner est mort";
            break;
            
            default:
                msg = "erreur non identifiee";
        }
    }
}
