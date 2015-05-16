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
        super(owner, topLeftCorner, 1, targetToSet);
        life = LIFE;
        creating = false;
        builder1 = null;
        builder2 = null;
        aliveSimpleUnits.add(this);
        if (owner!=null) {
            owner.simpleUnits.add(this);
            owner.units.add(this);
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
        this(owner, topLeftCorner, null);
    }

    //_____________________MÉTHODES____________________//
    
    public void createSoldier(Point2D t, SimpleUnit u1, SimpleUnit u2){
        if (!creating)
            setBuilders(u1, u2, t);
            
        else build();
            
    }
    
    public void createSoldier(SimpleUnit u1, SimpleUnit u2){
        if (!creating)
            setBuilders(u1, u2);
            
        else build();
            
    }
    
    public void createSoldier(){
        if (!creating){
            SimpleUnit theTwo[] = getTwoClosestSimpleUnits();
            setBuilders(theTwo[0], theTwo[1]);
        }
        else build();
            
    }
    
    public void build(){
        if (builders.distanceTo(target) <= CREATION_RANGE){
            builders.isDestructed();
            //TODO gerer les conflits à la création ______________________________________________?
            new Soldier(owner, target, builders.getQuantityOfLife());
        } 
    }

    /**
     * @param u1
     * @param u2
     * @param t position du soldat à créer
     */
    public void setBuilders(SimpleUnit u1,SimpleUnit u2, Point2D t){
        creating = true;
        builders = new SimpleUnitGroup(this);
        builders.add(u1);
        builders.add(u2);
        builder1 = u1;
        builder2 = u2;
        builders.setTarget(t);
    }
    
    /**
     * @param u1
     * @param u2
     * @param t position du soldat à créer
     */
    public void setBuilders(SimpleUnit u1,SimpleUnit u2){
        creating = true;
        builders = new SimpleUnitGroup(this);
        builders.add(u1);
        builders.add(u2);
        builder1 = u1;
        builder2 = u2;
        builders.setTarget(builders.getPosition());
    }
    
    public SimpleUnit[] getTwoClosestSimpleUnits(){
        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(aliveSimpleUnits);
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
     * Gère la vie d’une US, son max étant LIFE.
     * @param amount vie ajoutée (- pour en enlever)
     */
    public void getLife(double amount){
        life+= amount;
        if (life <=0)
            this.isDestructed();
        else  if (life >= LIFE)
            life = LIFE;
    }
    
    /**
     * soin de la 'targetI'
     */
    public void heal(){
        if (targetI != null) {
            if (targetI.isDead()) {

                stop();
                error(1);
            } else if (this.isCloseTo(targetI, HEALING_RANGE)) {

                targetI.getLife(life);
                this.isDestructed();
            }
        }
    }
    
    public void isDestructed(){
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)){
            deadItems.add(this);
            aliveItems.remove(this);
            deadSimpleUnits.add(this);
            aliveSimpleUnits.remove(this);
            owner.units.remove(this);
            owner.simpleUnits.remove(this);
        }
    }
    
    public void execute(){
        actualiseTarget();
        move();
        if (creating)
            build();
        else
            heal();
    }
}
