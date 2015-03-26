import java.awt.Graphics;

public class Soldier extends Unit {
    
    /**
     * @param owner
     * Détenteur de l’unité
     * @param locationToSet
     * Position initiale de l’unité
     */
    public Soldier(Player owner, Location locationToSet){
        super(owner, locationToSet);
    }
    

    @Override
    public void moveTo(Location location) {
        // TODO Implement this method
    }

    @Override
    public void print(Graphics g) {
        // TODO Implement this method
    }
}
