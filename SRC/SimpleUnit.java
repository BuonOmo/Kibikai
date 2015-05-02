import java.awt.geom.Point2D;

public class SimpleUnit extends Unit {
    
    //_____________________ATTRIBUTS____________________//
    
    boolean creating;
    
    //___________________CONSTRUCTEURS__________________//
    
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet){
        super(owner, topLeftCorner, 1, targetToSet);
        life = LIFE;
        creating = false;
    }
    
    public SimpleUnit(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner, 1);
        life = LIFE;
        creating = false;
    }

    //_____________________MÉTHODES____________________//
    
    // _______________________________________________________________________ ne peut pas marcher
    public void createSoldier(Point2D t, SimpleUnit u1, SimpleUnit u2){
        creating = true;
        SimpleUnitGroup toCreate = new SimpleUnitGroup(this);
        toCreate.add(u1);
        toCreate.add(u2);
        toCreate.setTarget(t);
        if (toCreate.distanceTo(target) <= CREATION_RANGE){
            this.isDestructed();
            u1.isDestructed();
            u2.isDestructed();
            //TODO gerer les conflits à la création ______________________________________________?
            owner.units.add(new Soldier(owner, target));
        }
            
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
        heal();
    }
}
