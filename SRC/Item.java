import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Item {
    Point target;
    int life;
    Color color;
    Rectangle hitBox;
    Player owner;
    
    // _____________CONSTRUCTEURS______________//
    
    /**
     * @param owner Possesseur de l’objet
     * @param hitBoxToSet
     */
    public Item(Player ownerToSet, Rectangle hitBoxToSet){
        color = ownerToSet.color;
        owner = ownerToSet;
        hitBox = hitBoxToSet;
    }

    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param width largeur de la hitBox
     * @param height hauteur de la hitBox
     */
    public Item(Player owner, Point topLeftCorner,int width, int height){
        color = owner.color;
        hitBox = new Rectangle(topLeftCorner, new Dimension(Finals.SIDE*width,Finals.SIDE*height));
        target = topLeftCorner;
        
    }
    
    /**
     * Constructeur pour une hitBox carré
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Item(Player owner, Point topLeftCorner,int side){
        color = owner.color;
        hitBox = new Rectangle(topLeftCorner, new Dimension(Finals.SIDE*side,Finals.SIDE*side));
        target = topLeftCorner;
        
    }
    
    //________________MÉTHODES_______________//
    
    /**
     * Diminue la vie.
     */
    public void IsAttacked(){
        life--;
    }
    
    /**
     * @param g
     */
    public void print(Graphics g){
        g.setColor(color);
        // TODO virer ce putain de 3 et mettre un truc cohérant pour les arcs de cercle
        g.fillRoundRect(hitBox.x*Finals.scale, hitBox.y*Finals.scale, hitBox.width*Finals.scale, hitBox.height*Finals.scale,3*Finals.scale,3*Finals.scale);
    }
}
