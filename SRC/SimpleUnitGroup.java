import java.util.LinkedList;


public class SimpleUnitGroup extends UnitGroup { 
    
    //_____________ATTRIBUTS____________//
    
    static LinkedList<SimpleUnitGroup> list = new LinkedList<SimpleUnitGroup>();
    IASimpleUnit ia;

    //__________________CONSTRUCTEURS__________________//
    
    public SimpleUnitGroup(SimpleUnit us) {
        super(us);
        if (us.owner == IA.computer){
            list.add(this);
            ia = new IASimpleUnit(this);
        }
    }
    
    private SimpleUnitGroup( LinkedList<SimpleUnit> grpUs ) {
        super(grpUs);
    }
    
    public SimpleUnitGroup (LinkedList<SimpleUnit> grpUs, Player o) {
        super(grpUs, o);
        if (o == IA.computer){
            list.add(this);
            ia = new IASimpleUnit(this);
        }
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
    // faire deux méthodes détachées (getDensePart part et densePart)
    public SimpleUnitGroup densePart() {
        LinkedList<SimpleUnit> copactGrp = new LinkedList<SimpleUnit> ();
        LinkedList<SimpleUnit> rest = new LinkedList(group);
        densePartOfListe (copactGrp,rest,rest.get(0));

        group.clear();
        group.addAll(copactGrp);
        return new SimpleUnitGroup(rest,owner);
    }
    
    private void densePartOfListe (LinkedList<SimpleUnit> copactGrp,LinkedList<SimpleUnit> rest ,SimpleUnit s ){
        rest.remove(s);
        copactGrp.add(s);
        // modifier le if
        if (rest.size()!=0) {
                int restSize = rest.size();
            for (int i = restSize-1 ;i>=0;i--){
                    if (s.distanceTo(rest.get(i))< compactdim )densePartOfListe(copactGrp,rest,rest.get(i));
                }
            }
                
        
    }
    
    public void add(SimpleUnitGroup sg){
        if (sg.owner==owner){
            if (owner==IA.computer){
                this.group.addAll(sg.getGroup());
                list.remove(sg);
            }
            else this.group.addAll(sg.getGroup());
        }
    }
    
    public void add(SimpleUnit us){
        if (us.owner==owner){
            if (owner==IA.computer)
                this.group.add(us);
                
            else this.group.add(us);
        }
    
    }
    
    public boolean isDense(){
        if (this.densePart().group.size()==0) return true ;
        return false ;
    }
    
}
