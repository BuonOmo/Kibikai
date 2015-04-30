import java.awt.geom.Point2D;

public class SimpleUnit extends Unit {
    
    //___________________CONSTRUCTEURS__________________//
    
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet){
        super(owner, topLeftCorner, 1, targetToSet);
        life = LIFE;
    }
    
    public SimpleUnit(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner, 1);
        life = LIFE;
    }

    //_____________________MÉTHODES____________________//
    
    public void createSoldier(){
        // TODO implémenter cette méthode
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
