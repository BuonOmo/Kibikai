import java.awt.geom.Point2D;

import java.util.LinkedList;

public class SoldierGroup extends UnitGroup {
    
    //______________ATTRIBUTS__________________//
    
    public static LinkedList<SoldierGroup> list;
    
    //_______________CONSTRUCTEURS______________//

    public SoldierGroup(Soldier soldier) {
        super(soldier);
        if (soldier.owner == IA.computer);
            list.add(this);
            
    }
    public SoldierGroup( LinkedList<Soldier> soldiers ) {
        super(soldiers);
    }
    public SoldierGroup ( LinkedList<Soldier> soldiers ,Player ownerToSet) {
        super(soldiers, ownerToSet);
    }
    
    //_______________MÉTHODES______________//
    
    public LinkedList<SoldierGroup> divideInDenseGroups (){
        LinkedList<SoldierGroup> toReturn = new LinkedList<SoldierGroup>();
        toReturn.add(this);
        while (toReturn.getLast().group.size() != 0){
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
        LinkedList<Soldier> rest = new LinkedList (group);
        densePartOfListe (copactGrp,rest,rest.get(0));
        group.clear();
        group.addAll(copactGrp);
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
                this.group.addAll(sg.getGroup());
                list.remove(sg);
            }
            else this.group.addAll(sg.getGroup());
        }
    }
    
    public boolean isDense(){
        if (this.densePart().group.size()==0) return true ;
        return false ;      
    }
    
   /* public LinkedList<Soldier> getgroup(){
        return group;
    }
   */
}
