import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Soldier extends Unit {
    
    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner, 2);
    }
    
    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner, Point2D targetToSet){
        super(owner, topLeftCorner, 2, targetToSet);
        
    }
    
    //________________MÉTHODES_______________//


    @Override
    public void print(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (hitBox.getX() * Finals.scale), (int) (hitBox.getY() * Finals.scale),
                   (int) (hitBox.getWidth() * Finals.scale), (int) (hitBox.getHeight() * Finals.scale));
    }
}
