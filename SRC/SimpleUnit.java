import java.awt.geom.Point2D;

public class SimpleUnit extends Unit {

    /**
     * objet à construire, null s’il n’y en a pas
     */
    Item toBuild;
    
    /**
     * objet à soigner, null s’il n’y en a pas
     */
    Item toHeal;
    
    //___________________CONSTRUCTEURS__________________//
    
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet){
        super(owner, topLeftCorner, 1, targetToSet);
    }
    
    public SimpleUnit(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner, 1);
    }

    //_____________________MÉTHODES____________________//
    
    public void createSoldier(){
        // TODO implémenter cette méthode
    }

    /**
     * @param toHeal Objet à soigner
     */
    public void heal(Item toHeal){
        
        if (toHeal.isDead()){
            
            setTarget();
            error(1);
        }
        
        else { 
            if (this.isCloseTo(toHeal, HEALING_RANGE)) {
                // on peut soigner plus que la vie original de chaque Item, met-on un max ?
                toHeal.life += life;
                this.isDestructed();
            } else {
                setTarget(toHeal);
            }
        }
    }
}
