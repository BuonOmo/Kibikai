import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.awt.geom.RectangularShape;

import java.util.LinkedList;

public abstract class Item implements Finals{
    
    //______________ATTRIBUTS________________//
    
    Point2D target;
    Item targetI;
    double life;
    Color color;
    RectangularShape hitbox;
    Player owner;
    double radius;
    static LinkedList<Item> aliveItems = new LinkedList<Item>();
    static LinkedList<Item> deadItems = new LinkedList<Item>();    
    boolean done;
    boolean selected;
    
    // _____________CONSTRUCTEURS______________//
    
    
    /**
     * @param owner Possesseur de l’objet
     * @param hitboxToSet
     */
    public Item(Player ownerToSet, RectangularShape hitboxToSet, Point2D targetToSet){
        color = ownerToSet.color;
        owner = ownerToSet;
        hitbox = hitboxToSet;
        target = targetToSet;
        owner.items.add(this);
        aliveItems.add(this);
        radius = this.distanceTo(new Point2D.Double(hitbox.getX(), hitbox.getY()));
        selected = false;
    }

    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param width largeur de la hitbox
     * @param height hauteur de la hitbox
     */
    public Item(Player ownerToSet, Point2D topLeftCorner,double width, double height){
        this(ownerToSet, new Rectangle2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), width, height), topLeftCorner);
    }
    
    /**
     * Constructeur pour une hitbox carree.
     * @param ownerToSet Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitbox
     */
    public Item(Player ownerToSet, Point2D topLeftCorner,double side){
        this(ownerToSet, new Rectangle2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), side, side), topLeftCorner);
    }
    
    
    //________________MÉTHODES_______________//
    
    
    /**
     * Gère la vie d’une unité (et pour un batiment sa taille).
     * @param amount vie ajoutée (- pour en enlever)
     * @return vraie si l’unité meurt
     */
    public abstract boolean getLife(double amount);
    
    /**
     * Permet de déplacer une unité vers un point donné.
     * @param targetToSet Point d’arrivée de l’unité (objectif)
     */
    public void setTarget(Point2D targetToSet){
        targetI = null;
        target = targetToSet;
    }
    
    public void setTarget(double x, double y){
        setTarget( new Point2D.Double(x,y));
    }
    
    public void setTarget(int x, int y){
        setTarget( new Point2D.Double(x, y));
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
        double x = hitbox.getCenterX(), y = hitbox.getCenterY();
        
        return Math.sqrt((p.getX() - x)*(p.getX() - x) + (p.getY() -y)*(p.getY()-y));   
    }
    
    public double distanceTo(Item other){
    	double d;
    	double x1= this.hitbox.getCenterX();
    	double y1=this.hitbox.getCenterY();
    	double x2=other.hitbox.getCenterX();
    	double y2=other.hitbox.getCenterY();
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
        return new Point2D.Double(hitbox.getCenterX(),hitbox.getCenterY());
    }
    
    public static LinkedList<Item> getItemInFrame(Rectangle2D frame){
        LinkedList<Item> toReturn = new LinkedList<Item>();
        for (Item i : aliveItems){
            if (frame.contains(i.getCenter()))
                toReturn.add(i);
        }
        return toReturn;
    }
    
    public Item[] getNClosestItem(int n){
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
    
    public boolean isDestructed(){
        //TODO au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)){
            deadItems.add(this);
            aliveItems.remove(this);
            owner.items.remove(this);
            return true;
        }
        return false;
    }

    /**
     * @return vraie si la vie est en dessous de 0
     */
    public boolean isDead(){
        return (life <= 0);
    }
    
    public boolean hasSameOwner(Item i){
        return (i.owner == owner);
    }

    /**
     * @return vraie si il y a des remove dans aliveItem
     */
    public abstract boolean execute();
    
    public Color getColor(){
        if (selected)
            return new Color(0,255,255,100);
        if (Listeners.louHammel)
            return new Color((int) (255.0 * Math.random()),
                             (int) (255.0 * Math.random()),
                             (int) (255.0 * Math.random()));
        return color;
    }
    
    public void print(Graphics g, double offsetX, double offsetY){
        /*if (selected){
            
            double newSide = hitbox.getHeight()*1.2 + SIDE/4.0;
            
            g.fillRoundRect( (int)((getCenter().getX() - newSide/2.0)*scale), 
                             (int)((getCenter().getY() - newSide/2.0)*scale), 
                             (int)newSide*scale, 
                             (int)newSide*scale,
                             (12),
                             (12));
        }*/
        
        g.setColor(getColor());
        // TODO virer ce putain de 3 et mettre un truc cohérent pour les arcs de cercle
        g.fillRoundRect( (int)((hitbox.getX()-offsetX)*scale), 
                         (int)((hitbox.getY()-offsetY)*scale), 
                         (int)(hitbox.getWidth()*scale), 
                         (int)(hitbox.getHeight()*scale),
                         (10),
                         (10));
    }
    
    public String toString(){
        return this.getClass().getName()+" at ["+getCenter().getX()+", "+getCenter().getY()+"]";
    }
    
    public void setDone(){
        done = true;
    }
    
    public void setUnDone(){
        done = false;
    }
    
    /**
     * gère les problèmes rencontrés par des objets (inutile !).
     * @param type type d’erreur
     */
     public void error (String type){
        
        /*String msg;
        
       switch (type){
            
            // erreur dans SimpleUnit.heal()
            case "SimpleUnit.heal":
                msg = "l'objet à soigner est mort";
                break;
            
            case "SimpleUnit.setBuilders":
                msg = "les UM appartiennent a� l'adversaire";
                break;
            
            default:
                msg = "erreur non identifiee";
        }
        
        System.out.println(msg);
*/
    }

    public void setSelected(boolean b) {
        selected = b;
    }

    /**
     * @param x position x de l’angle en haut à gauche de la hitbox
     * @param y position y de l’angle en haut à gauche de la hitbox
     */
    public void setLocation(double x,double y){
        hitbox.setFrame(x, y, hitbox.getWidth(), hitbox.getHeight());
    }

    /**
     * @param x position x de l’angle en haut à gauche de la hitbox
     */
    public void setX(double x){
        setLocation(x, hitbox.getY());
    }
    
    /**
     * @param y position y de l’angle en haut à gauche de la hitbox
     */
    public void setY(double y){
        setLocation(hitbox.getX(), y);
    }
    
    /**
     * Regarde si l'Item est contenu dans la vue de la camera
     * @param c la camera utilisee
     * @return vrai si contenu dans la camera
     */
    public boolean isContained(Camera c){
    	double xI = hitbox.getX();
    	double yI = hitbox.getY();
    	double wI = hitbox.getWidth();
    	double hI = hitbox.getHeight();
    	
    	if((xI+wI)>c.cameraX && (xI)<(c.cameraX+c.cameraWidth)){
    		if ((yI+hI)>c.cameraY && (yI)<(c.cameraY+c.cameraHeight)){
    			return true;
    		}
    	}
    	return false;
    }
}
