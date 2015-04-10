import java.util.LinkedList;

public class SoldierGroup {
    private LinkedList<Soldier> groupUnits;
    private double compactdim;
    public static LinkedList<SoldierGroup> groupList;
    public IASoldier; 
    public 

    public SoldierGroup(Soldier s) {
        groupUnits = new LinkedList<Soldier>();
        groupUnits.add(s);
    }
    public SoldierGroup( LinkedList<Soldier> grpU ) {
        groupUnits = new LinkedList<Soldier>(grpU);
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
        int restSize = rest.size()
            if (rest.size()!=0) for (int i = restSize-1 ;i>=0;i--){
                if (s.distanceTo(rest.get(i))< compactdim )densePartOfListe(copactGrp,rest,rest.get(i));
            }
        
    }
}
