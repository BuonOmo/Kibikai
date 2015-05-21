import java.awt.geom.Point2D;

import java.util.LinkedList;

public class SimpleUnit extends Unit {
    
    //_____________________ATTRIBUTS____________________//
    
    boolean creating;
    SimpleUnit builder1, builder2;
    SimpleUnitGroup builders;
    
    static LinkedList<SimpleUnit> aliveSimpleUnits = new LinkedList<SimpleUnit>();
    static LinkedList<SimpleUnit> deadSimpleUnits = new LinkedList<SimpleUnit>();
    
    //___________________CONSTRUCTEURS__________________//
    
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet){
        super(owner, topLeftCorner, LIFE, 1, targetToSet);
        life = LIFE;
        creating = false;
        builder1 = null;
        builder2 = null;
        aliveSimpleUnits.add(this);
        if (owner!=null) {
            owner.simpleUnits.add(this);
            if (owner == IA.computer) new SimpleUnitGroup(this);
            }
                
    }

    /**
     * @param owner
     * @param targetToSet
     * @param topLeftCorner
     */
    public SimpleUnit(Player owner, Item targetToSet, Point2D topLeftCorner){
        this(owner, topLeftCorner, targetToSet.getCenter());
        setTarget(targetToSet);
    }
    
    /**
     * @param owner
     * @param topLeftCorner
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner){
        this(owner, topLeftCorner, topLeftCorner);
    }

    //_____________________MÉTHODES____________________//
    
    public void createSoldier(Point2D t, SimpleUnit u1, SimpleUnit u2){
        if (!creating)
            setBuilders(u1, u2, t);
            
        build();
            
    }
    
    public void createSoldier(SimpleUnit u1, SimpleUnit u2){
        if (!creating)
            setBuilders(u1, u2);
            
        build();
            
    }
    
    public void createSoldier(){

        if(this.owner.simpleUnits.size()>2){
        if (!creating){
            SimpleUnit theTwo[] = getTwoClosestSimpleUnits();
            setBuilders(theTwo[0], theTwo[1]);
        }
        build();
            
    }
        else this.error("createSoldier");
        //TODO add to erreurs 
    }
    public boolean build(){
        
        if ((builders.distanceTo(target) <= CREATION_RANGE)&(builders.group.size()==3)){
            new Soldier(owner, target, builders.getQuantityOfLife());
            return builders.isDestructed();
        }
        
        return false;
    }

    /**
     * @param u1
     * @param u2
     * @param t position du soldat à créer
     */
    private void setBuilders(SimpleUnit u1,SimpleUnit u2, Point2D t){
        if (hasSameOwner(u1) && hasSameOwner(u2)){
            creating = true;
            builders = new SimpleUnitGroup(this);
            builders.add(u1);
            builders.add(u2);
            builder1 = u1;
            builder2 = u2;
            builders.setTarget(t);
        }
        else error("SimpleUnit.setBuilders");
    }
    
    /**
     * crée un soldat au niveau du barycentre des trois unités.
     * @param u1
     * @param u2
     */
    private void setBuilders(SimpleUnit u1,SimpleUnit u2){
        if (hasSameOwner(u1) && hasSameOwner(u2)){
            creating = true;
            builders = new SimpleUnitGroup(this);
            builders.add(u1);
            builders.add(u2);
            builder1 = u1;
            builder2 = u2;
            builders.setTarget(builders.getPosition());
            //builders.setTarget(IA.computer.base.getCenter());
        }
        else error("SimpleUnit.setBuilders");
    }
    
    private SimpleUnit[] getTwoClosestSimpleUnits(){
        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(owner.simpleUnits);
        toCheck.remove(this);
        SimpleUnit[] toReturn = {toCheck.getFirst(),toCheck.getFirst()};
        for (SimpleUnit i : toCheck){
            if (distanceTo(i)<= distanceTo(toReturn[0])){
                toReturn[1] = toReturn[0];
                toReturn[0] = i;
            }
            
        }
        return toReturn;
    }
    
    /**
     * soin de la 'targetI'
     */
    public boolean heal(){
        if (targetI != null && targetI.getClass().getName() != "SimpleUnit" && this.hasSameOwner(targetI)) {
            if (targetI.isDead()) {

                stop();
                error("SimpleUnit.heal");
            } else if (this.isCloseTo(targetI, HEALING_RANGE)) {

                targetI.getLife(life);
                return this.isDestructed();
            }
        }
        return false;
    }
    
    @Override
    public boolean isDestructed(){
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)){
            owner.deadUnits.add(this);
            deadItems.add(this);
            aliveItems.remove(this);
            deadSimpleUnits.add(this);
            aliveSimpleUnits.remove(this);
            owner.items.remove(this);
            owner.units.remove(this);
            owner.simpleUnits.remove(this);
            return true;
        }
        return false;
    }
    
    public boolean execute(){
        actualiseTarget();
        move();
        if (creating)
            return build();
        return heal();
    }
}
