import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.util.LinkedList;

public class Soldier extends Unit {
    
    //_____________ATTRIBUTS____________//
    
    int damage;

    //__________________CONSTRUCTEURS__________________//
    
    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner){
        super(owner, topLeftCorner, 2);
        life = LIFE*2;
        damage = 0;
    }
    
    //________________MÉTHODES_______________//

    /**
     * Gère la vie d’une unité (et pour un batiment sa taille).
     * @param amount vie ajoutée (- pour en enlever)
     */
    public void getLife(double amount){
        life+= amount;
        if (life <=0)
            this.isDestructed();
        else  if (life >= LIFE*3)
            life = LIFE*3;
    }
    
    public void attack(){

        Item toAttack;
        toAttack = getEnemyToAttack();
        
        toAttack.getLife(- DAMAGE);
        damage+= DAMAGE;
        // on peut infliger plus de dégats qu’il ne reste de vie à une unité
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
                   (int) (2 * radius * scale),
                   (int) (2 * radius * scale));
    }
    
    public void execute() {
        setTarget(targetI);
        move();
        attack();
    }
    
}
