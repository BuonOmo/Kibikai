import java.awt.geom.Point2D;

import java.util.Collection;
import java.util.LinkedList;

public abstract class UnitGroup {
    
    //_______________ATTRIBUTS_________________//
    
    protected LinkedList<Unit> group;
    protected double compactdim;
    protected Player owner;
    
    //_______________CONSTRUCTEURS______________//


    /**
     * Constructeur pour une seul unité.
     * @param u unité
     * @param iaToSet Intelligence artificielle du groupe
     */
    public UnitGroup (Unit u){
        group = new LinkedList<>();
        group.add(u);
        owner = u.owner;
        compactdim = Finals.Group_compactDim;
    }
    
    /**
     * Constructeur pour un groupe  d’unité.
     * @param units groupe d’unités
     * @param iaToSet Intelligence artificielle du groupe
     */
    public UnitGroup (Collection units){
        group = new LinkedList<>(units);
        owner = null;
        compactdim = Finals.Group_compactDim;
    }
    
    /**
     * Constructeur pour un groupe  d’unité ayant un possesseur attitré
     * @param units groupe d’unités
     * @param iaToSet Intelligence artificielle du groupe
     * @param ownerToSet possesseur du groupe
     */
    public UnitGroup (Collection units, Player ownerToSet){
        this(units);
        
        owner = ownerToSet;
        for (Unit i : group)
            if (i.owner!=owner)
                group.remove(i);
        
    }
    
    //_______________MÉTHODES______________//


    /**
     * @return taille du groupe
     */
    public int numberUnit(){
        return group.size();
    }

    /**
     * @return toutes les unités du groupe
     */
    public LinkedList<Unit> getGroup(){
        return group;
    }
    
    /**
     * @return postion du Centre des masse du groupe
     */
     // travailler les Update pour ameliorer la vitesse
    public Point2D getPosition(){
        double X=0;
        double Y=0;
        for (Unit i : group){
            X=+i.getCenter().getX();
            Y=+i.getCenter().getY();
        }
        X=X/this.group.size();
        Y=Y/this.group.size();
        return new Point2D.Double(X,Y);
    }


    /**
     * @param p position sur la carte
     * @return distance à une position
     */
    public double distanceTo(Point2D p){
        return p.distance(getPosition());   
    }

    /**
     * @param i objet sur la carte
     * @return distance à un objet
     */
    public double distanceTo(Item i){
        return i.getCenter().distance(getPosition());   
    }

    /**
     * @param ug groupe d’unités
     * @return distance à un autre groupe d’unités
     */
    public double distanceTo(UnitGroup ug){
        return ug.getPosition().distance(getPosition());   
    }
    
    public void isDestructed(){
        for (Unit u : group)
            u.isDestructed();
    }
    /**
     * @return possesseur
     */
    public Player getOwner(){
        return owner;
    }
    
    public void  setTarget(Point2D targetToSet){
        for(Unit i : group)
            i.setTarget(targetToSet);
    }
}
