import java.awt.geom.Point2D;

import java.util.LinkedList;

public class SoldierGroup {
    private LinkedList<Soldier> groupUnits;
    private double compactdim;
    public static LinkedList<SoldierGroup> groupList;
    public IASoldier iaSoldieur; 

    public SoldierGroup(Soldier s) {
        groupUnits = new LinkedList<Soldier>();
        groupUnits.add(s);
        iaSoldieur =new IASoldier();
    }
    public SoldierGroup( LinkedList<Soldier> grpU ) {
        groupUnits = new LinkedList<Soldier>(grpU);
        iaSoldieur =new IASoldier();
    }

    /**
     * @return postion du Centre des masse du group
     */
    public Point2D GetPsosition(){
        double X=0;
        double Y=0;
        for (int i = 0; i<groupUnits.size();i++){
            X=+groupUnits.get(i).getCenter().getX();
            Y=+groupUnits.get(i).getCenter().getY();
        }
        X=X/this.groupUnits.size();
        Y=Y/this.groupUnits.size();
        return new Point2D.Double(X,Y);
    }
    public LinkedList<SoldierGroup> divideInDenseGroups (){
        LinkedList<SoldierGroup> toReturn = new LinkedList<SoldierGroup>();
        toReturn.add(this);
        while (toReturn.getLast().groupUnits.size() != 0){
            SoldierGroup toAdd = toReturn.getLast().densePart();
            toReturn.add(toAdd);
        }
        toReturn.remove(toReturn.size()-1);
        return toReturn ;
    }
    public SoldierGroup densePart() {
        LinkedList<Soldier> copactGrp = new LinkedList<Soldier> ();
        LinkedList<Soldier> rest = new LinkedList<Soldier> (groupUnits);
        densePartOfListe (copactGrp,rest,rest.get(0));
        groupUnits=copactGrp;
        return new SoldierGroup(rest);
    }
    private void densePartOfListe (LinkedList<Soldier> copactGrp,LinkedList<Soldier> rest ,Soldier s ){
        rest.remove(s);
        copactGrp.add(s);
        if (rest.size()!=0) {
                int restSize = rest.size();
            for (int i = restSize-1 ;i>=0;i--){
                    if (s.distanceTo(rest.get(i))< compactdim )densePartOfListe(copactGrp,rest,rest.get(i));
                }
            }
                
        
    }
    public boolean isDense(){
        if (this.densePart().groupUnits.size()==0) return true ;
        return false ;
        
        
    }
}
