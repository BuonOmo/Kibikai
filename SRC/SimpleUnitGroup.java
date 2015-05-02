import java.util.LinkedList;


public class SimpleUnitGroup extends UnitGroup { 

    //__________________CONSTRUCTEURS__________________//
    
    public SimpleUnitGroup(SimpleUnit us) {
        super(us);
    }
    
    public SimpleUnitGroup( LinkedList<SimpleUnit> grpUs ) {
        super(grpUs);
    }
    
    public SimpleUnitGroup (LinkedList<SimpleUnit> grpUs, Player o) {
        super(grpUs, o);
    }
    
    //_________________________METHODES_______________________//
    
    public LinkedList<SimpleUnitGroup> divideInDenseGroups (){
        LinkedList<SimpleUnitGroup> toReturn = new LinkedList<SimpleUnitGroup>();
        toReturn.add(this);
        while (toReturn.getLast().group.size() != 0){
            SimpleUnitGroup toAdd = toReturn.getLast().densePart();
            toReturn.add(toAdd);
        }
        toReturn.remove(toReturn.size()-1);
        return toReturn ;
    }
    
    // quel est l’objectif (précis) de cette méthode
    public SimpleUnitGroup densePart() {
        LinkedList<SimpleUnit> copactGrp = new LinkedList<SimpleUnit> ();
        LinkedList<SimpleUnit> rest = new LinkedList(group);
        densePartOfListe (copactGrp,rest,rest.get(0));
        
        // inutile ? 
        /*group.clear();
        group.addAll(copactGrp);
        groupU = copactGrp;*/
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
    
    // quel est l’interet de groupSimpleUnitList ?
    public void add(SimpleUnitGroup sg){
        if (sg.owner==owner){
            if (owner==IA.computer){
                this.group.addAll(sg.getGroup());
                // inutile ?_________________________________________________??
                //this.groupSimpleUnitList.remove(sg);
            }
            else this.group.addAll(sg.getGroup());
        }
    }
    
    public void add(SimpleUnit us){
        if (us.owner==owner){
            if (owner==IA.computer){
                this.group.add(us);
                // inutile ?_________________________________________________??
                //this.groupSimpleUnitList.remove(sg);
            }
            else this.group.add(us);
        }
    
    }
    public boolean isDense(){
        if (this.densePart().group.size()==0) return true ;
        return false ;
    }
    
}
