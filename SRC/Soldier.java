import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Soldier extends Unit {
    
    /**
     * @param owner
     * Détenteur de l’unité
     * @param locationToSet
     * Position initiale de l’unité
     */
    public Soldier(Player owner, Point topLeftCorner){
        super(owner, topLeftCorner, 2);
    }
    
    //________________MÉTHODES_______________//


    @Override
    public void print(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (hitBox.getX() * Finals.scale), (int) (hitBox.getY() * Finals.scale),
                   (int) (hitBox.getWidth() * Finals.scale), (int) (hitBox.getHeight() * Finals.scale));
    }
}
