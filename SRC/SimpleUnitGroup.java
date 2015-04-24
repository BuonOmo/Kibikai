import java.awt.geom.Point2D;

import java.util.LinkedList;

public class SimpleUnitGroup extends UnitGroup {
    protected LinkedList<SimpleUnit> groupUnits;
    public static LinkedList<SimpleUnitGroup> groupSimpleUnitList;
    public IASimpleUnit iaSimpleUnit; 
    protected Player owner;

    public SimpleUnitGroup(SimpleUnit s) {
        groupUnits = new LinkedList<SimpleUnit>();
        groupUnits.add(s);
        iaSimpleUnit =new IASimpleUnit(this);
        owner = s.owner;
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSimpleUnit,owner);
    }
    public SimpleUnitGroup( LinkedList<SimpleUnit> grpU ) {
        groupUnits = new LinkedList<SimpleUnit>(grpU);
        iaSimpleUnit =new IASimpleUnit(this);
        owner=null;
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSimpleUnit,owner);
    }
    public SimpleUnitGroup (LinkedList<SimpleUnit> grpUnit ,Player ownerToSet) {
    owner = ownerToSet;
    groupUnits = new LinkedList<SimpleUnit>(grpUnit);
    iaSimpleUnit =new IASimpleUnit(this);
    for (int i=grpUnit.size()-1 ;i >= 0;i--){
        if (grpUnit.get(i).owner!=owner)grpUnit.remove(i);
        }
        LinkedList<Unit> toSuper = new LinkedList<Unit>();
        toSuper.addAll(groupUnits);
        super.setall(toSuper,iaSimpleUnit,owner);
    }

    public LinkedList<SimpleUnitGroup> divideInDenseGroups (){
        LinkedList<SimpleUnitGroup> toReturn = new LinkedList<SimpleUnitGroup>();
        toReturn.add(this);
        while (toReturn.getLast().groupUnits.size() != 0){
            SimpleUnitGroup toAdd = toReturn.getLast().densePart();
            toReturn.add(toAdd);
        }
        toReturn.remove(toReturn.size()-1);
        return toReturn ;
    }
    public SimpleUnitGroup densePart() {
        LinkedList<SimpleUnit> copactGrp = new LinkedList<SimpleUnit> ();
        LinkedList<SimpleUnit> rest = new LinkedList<SimpleUnit> (groupUnits);
        densePartOfListe (copactGrp,rest,rest.get(0));
        groupUnits=copactGrp;
        return new SimpleUnitGroup(rest,owner);
    }
    private void densePartOfListe (LinkedList<SimpleUnit> copactGrp,LinkedList<SimpleUnit> rest ,SimpleUnit s ){
        rest.remove(s);
        copactGrp.add(s);
        if (rest.size()!=0) {
                int restSize = rest.size();
            for (int i = restSize-1 ;i>=0;i--){
                    if (s.distanceTo(rest.get(i))< compactdim )densePartOfListe(copactGrp,rest,rest.get(i));
                }
            }
                
        
    }
    public void add(SimpleUnitGroup sg){
        if (sg.owner==this.owner){
            if (this.owner==IA.computer){
                this.groupUnits.addAll(sg.getgroupUnits());
                this.groupSimpleUnitList.remove(sg);
            }
            else this.groupUnits.addAll(sg.getgroupUnits());
        }
    }
    public boolean isDense(){
        if (this.densePart().groupUnits.size()==0) return true ;
        return false ;
    }
    public LinkedList<SimpleUnit> getgroupUnits(){
        return groupUnits;
    }

}
