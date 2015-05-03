import java.awt.geom.Point2D;

public class SimpleUnit extends Unit {
    
    //_____________________ATTRIBUTS____________________//
    
    boolean creating;
    SimpleUnit builder1, builder2;
    SimpleUnitGroup builders;
    
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
    
    // _______________________________________________________________________ ne peut pas marcher
    public void createSoldier(Point2D t, SimpleUnit u1, SimpleUnit u2){
        if (!creating)
            setBuilders(u1, u2, t);
            
        else createSoldier();
            
    }
    
    public void createSoldier(){
        if (builders.distanceTo(target) <= CREATION_RANGE){
            builders.isDestructed();
            //TODO gerer les conflits à la création ______________________________________________?
            owner.units.add(new Soldier(owner, target));
        } 
    }
    
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
    
    public void execute(){
        setTarget(targetI);
        move();
        if (creating)
            createSoldier();
        else
            heal();
    }
}
