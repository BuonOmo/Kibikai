import java.awt.geom.Point2D;

import java.util.LinkedList;

public class SoldierGroup extends UnitGroup {
    protected LinkedList<Soldier> groupUnits;
    public static LinkedList<SoldierGroup> groupSoldierList;
    public IASoldier iaSoldier; 
    protected Player owner;

    public SoldierGroup(Soldier s) {
        groupUnits = new LinkedList<Soldier>();
        groupUnits.add(s);
        iaSoldier =new IASoldier(this);
        owner = s.owner;
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSoldier,owner);
    }
    public SoldierGroup( LinkedList<Soldier> grpU ) {
        groupUnits = new LinkedList<Soldier>(grpU);
        iaSoldier =new IASoldier(this);
        owner=null;
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSoldier,owner);
    }
    public SoldierGroup ( LinkedList<Soldier> grpUnit ,Player ownerToSet) {
        owner = ownerToSet;
        groupUnits = new LinkedList<Soldier>(grpUnit);
        iaSoldier =new IASoldier(this);
        for (int i=grpUnit.size()-1 ;i >= 0;i--){
            if (grpUnit.get(i).owner!=owner)grpUnit.remove(i);
        }
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSoldier,owner);
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
    /*
     * garde la parti danse du group
     * et retourne la parti non danse
     */
    public SoldierGroup densePart() {
        LinkedList<Soldier> copactGrp = new LinkedList<Soldier> ();
        LinkedList<Soldier> rest = new LinkedList<Soldier> (groupUnits);
        densePartOfListe (copactGrp,rest,rest.get(0));
        groupUnits=copactGrp;
        return new SoldierGroup(rest,owner);
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
    public void add(SoldierGroup sg){
        if (sg.owner==this.owner){
            if (this.owner==IA.computer){
                this.groupUnits.addAll(sg.getGroup());
                this.groupSoldierList.remove(sg);
            }
            else this.groupUnits.addAll(sg.getGroup());
        }
    }
    public boolean isDense(){
        if (this.densePart().groupUnits.size()==0) return true ;
        return false ;      
    }
   /* public LinkedList<Soldier> getgroupUnits(){
        return groupUnits;
    }
   */
}
