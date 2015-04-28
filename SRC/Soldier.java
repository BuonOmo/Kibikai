import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.util.LinkedList;

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


    public void attack(){

        Item toAttack;
        toAttack = getEnemyToAttack();

        toAttack.life-= DAMAGE;
        
        if (toAttack.life <= 0)
            toAttack.isDestructed();
    }
    
    public Item getEnemyToAttack(){
        //TODO gerer la priorité entre attaquer une US, une UM ou un BA ? et gerer une liste d’ennemis ?
        //LinkedList<Item> enemies;
        //enemies = new LinkedList();
        //enemies.addAll(aliveItems);
        if (targetI != null && targetI.isCloseTo(this, ATTACK_RANGE))
            return targetI;
        for (Item enemy : aliveItems)
            if (enemy.owner != owner && enemy.isCloseTo(this, ATTACK_RANGE))
                return enemy;
        return null;
        

    }
    
    @Override
    public void print(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (hitBox.getX() * scale),
                   (int) (hitBox.getY() * scale),
                   (int) (hitBox.getWidth() * scale),
                   (int) (hitBox.getHeight() * scale));
    }
    
    public void execute() {
        setTarget(targetI);
        move();
        attack();
    }
    
}
