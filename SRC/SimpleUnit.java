import java.awt.Graphics;

public class SimpleUnit extends Unit {

    // _____________CONSTRUCTEURS______________//
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Location locationToSet){
        super(owner, locationToSet);
    }

    /**
     * @param owner
     */
    public SimpleUnit(Player owner){
        super(owner, new Location(0,0));
    }

    //________________MÉTHODES_______________//

    @Override
    public void moveTo(Location location) {
        // TODO Implement this method
    }

    @Override
    public void print(Graphics g) {
        // TODO Implement this method
    }
}
