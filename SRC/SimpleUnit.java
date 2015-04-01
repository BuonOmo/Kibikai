import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class SimpleUnit extends Unit {

    // _____________CONSTRUCTEURS______________//
    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point topLeftCorner, Point targetToSet){
        super(owner, topLeftCorner, 1);
        this.target=targetToSet;
    }

    //________________MÉTHODES_______________//

    @Override
    public void moveTo(Point location) {
        // TODO Implement this method
    }
}
