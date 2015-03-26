import java.awt.Color;
import java.awt.Graphics;

public class Building extends Item{
    protected int level;

    /**
     * @param owner
     * @param levelToSet
     * @param locationOfTheCenter
     */
    public Building(Player owner, int levelToSet, Location locationOfTheCenter){
        super(owner,locationOfTheCenter);
        level = levelToSet;
        
    }
    
    public boolean isDestroyed(){
        return false;
        //TODO implémenter cette méthode
    }

    /**
     * @param levelToSet
     */
    public void setLevel(int levelToSet){
        level = levelToSet;
    }
    
    public int getLevel(){
        return level;
    }

    /**
     * @param g
     */
    public void print(Graphics g){
        g.setColor(color); //comment appelle-t’on la couleur du joueur qui se situe dans Item ?
        int l = (level + 1) * Finals.SIDE;
        g.fillRoundRect(location.x, location.y, l, l, l/5, l/5);
    }
}
