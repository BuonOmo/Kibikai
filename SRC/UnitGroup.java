import java.awt.geom.Point2D;

import java.util.LinkedList;

public abstract class UnitGroup {
    protected LinkedList<Unit> groupUnits;
    protected double compactdim;
    public IAUnite iaUnite; 
    protected Player owner;
    public UnitGroup (){
        
    }
    public void setall(LinkedList<Unit> groupUnitsToSet, IAUnite iaUniteToSet, Player ownerToSet) {
        groupUnits=groupUnitsToSet;
        iaUnite=iaUniteToSet;
        owner=ownerToSet;
        compactdim = Finals.Group_compactDim;
    }



    public int numberUnit(){
        return groupUnits.size();
    }
    public LinkedList<Unit> getGroupUnits(){
        return groupUnits;
    }
    /**
     * @return postion du Centre des masse du group
     */
    /*
     * travailler les Update pour amélioré la vitesse
     */
    public Point2D getPsosition(){
        double X=0;
        double Y=0;
        for (int i = 0; i < groupUnits.size();i++){
            X=+groupUnits.get(i).getCenter().getX();
            Y=+groupUnits.get(i).getCenter().getY();
        }
        X=X/this.groupUnits.size();
        Y=Y/this.groupUnits.size();
        return new Point2D.Double(X,Y);
    }
    public double distanceTo(Point2D p){
        Point2D p2 = this.getPsosition();
        return Math.sqrt((p.getX() - p2.getX())*(p.getX() - p2.getX()) + (p.getY() -p2.getY())*(p.getY() -p2.getY()));   
    }
    public double distanceTo(Item i){
        Point2D p = i.getCenter();
        Point2D p2 = this.getPsosition();
        return Math.sqrt((p.getX() - p2.getX())*(p.getX() - p2.getX()) + (p.getY() -p2.getY())*(p.getY() -p2.getY()));   
    }
    public double distanceTo(UnitGroup ug){
        Point2D p = ug.getPsosition();
        Point2D p2 = this.getPsosition();
        return Math.sqrt((p.getX() - p2.getX())*(p.getX() - p2.getX()) + (p.getY() -p2.getY())*(p.getY() -p2.getY()));   
    }

    public Player getOwner(){
        return owner;
    }
    public void  setTarget(Point2D targetToSet){
        for(int i= 0; i<groupUnits.size();i++){
            groupUnits.get(i).setTarget(targetToSet);
        }
        
    }
}
