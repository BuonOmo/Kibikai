import java.awt.geom.Point2D;

public class SimpleUnit extends Unit {
    
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
     * soin de la 'targetI'
     */
    public void heal(){
        
        if (targetI.isDead()){
            
            setTarget();
            error(1);
        }
        else if (this.isCloseTo(targetI, HEALING_RANGE)) {
                
            if (targetI.getClass().getName() == "Building"){
                ((Building)targetI).increase();
            }
            
            else{
            // on peut soigner plus que la vie original de chaque Item, met-on un max ?
            targetI.life += life;
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
