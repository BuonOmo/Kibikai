import java.awt.Color;
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
     * @param lifeToSet vie du soldat à sa création (et non sa vie max)
     */
    public Soldier(Player owner, Point2D topLeftCorner, double lifeToSet){
        super(owner, topLeftCorner, LIFE*3, 2);
        life = (lifeToSet < lifeMAX) ? lifeToSet : lifeMAX;
        damage = 0;
        if (owner!=null) {
            owner.soldiers.add(this);
            if (owner == IA.computer) new SoldierGroup(this);
            }
    }
    
    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner){
        this(owner, topLeftCorner, LIFE*3);
    }
    
    //________________MÉTHODES_______________//
    
    public void attack(){

        Item toAttack;
        toAttack = getEnemyToAttack();
        
        if (toAttack != null){
        
            damage+= (toAttack.life < DAMAGE) ? toAttack.life : DAMAGE;
            toAttack.getLife(- DAMAGE);
        
        }
    }
    
    public Item getEnemyToAttack(){
        //TODO gerer la priorité entre attaquer une US, une UM ou un BA ? et gerer une liste d’ennemis ?
   
        if (targetI != null && targetI.isCloseTo(this, ATTACK_RANGE) && !hasSameOwner(targetI))
            return targetI;
        for (Item enemy : aliveItems)
            if (!hasSameOwner(enemy) && enemy.isCloseTo(this, ATTACK_RANGE))
                return enemy;
        return null;
        

    }
    
    @Override
    public void print(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (hitBox.getX() * scale),
                   (int) (hitBox.getY() * scale),
                   (int) (2 * radius * scale),
                   (int) (2 * radius * scale));
    }
    public void isDestructed(){
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)){
            owner.deadUnits.add(this);
            deadItems.add(this);
            aliveItems.remove(this);
            owner.items.remove(this);
            owner.units.remove(this);
            owner.soldiers.remove(this);
        }
    }
    
    public void execute() {
        actualiseTarget();
        move();
        attack();
    }
    
}
