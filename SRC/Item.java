import java.awt.Color;
import java.awt.Graphics;

public abstract class Item {
    Color color;
    Location location;

    /**
     * @param owner
     * @param locationToSet
     */
    public Item(Player owner, Location locationToSet){
        color = owner.color;
        location = locationToSet;
    }

    /**
     * @param g
     */
    public abstract void print(Graphics g);
}
