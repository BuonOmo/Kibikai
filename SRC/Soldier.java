import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Soldier extends Unit {

    //_____________ATTRIBUTS____________//

    int damage;

    //__________________CONSTRUCTEURS__________________//

    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     * @param lifeToSet vie du soldat à sa création (et non sa vie max)
     */
    public Soldier(Player owner, Point2D topLeftCorner, double lifeToSet) {
        super(owner, topLeftCorner, LIFE * 3, 2);
        viewRay = VEW_RAY_SOLDIER;
        hitbox = new Ellipse2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), 2, 2);
        life = (lifeToSet < lifeMAX) ? lifeToSet : lifeMAX;
        damage = 0;
        if (owner != null) {
            owner.soldiers.add(this);
            if (owner == IA.computer)
                new SoldierGroup(this);
        }
    }

    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner) {
        this(owner, topLeftCorner, LIFE * 3);
    }

    //________________MÉTHODES_______________//

    public boolean attack() {

        Item toAttack;
        toAttack = getEnemyToAttack();

        if (toAttack != null) {

            damage += (toAttack.life < DAMAGE) ? toAttack.life : DAMAGE;
            return toAttack.getLife(-DAMAGE);

        }
        return false;
    }

    public Item getEnemyToAttack() {
        //TODO gerer la priorité entre attaquer une US, une UM ou un BA ? et gerer une liste d’ennemis ?

        if (targetI != null && targetI.isCloseTo(this, ATTACK_RANGE) && !hasSameOwner(targetI))
            return targetI;
        for (Item enemy : aliveItems)
            if (!hasSameOwner(enemy) && enemy.isCloseTo(this, ATTACK_RANGE))
                return enemy;
        return null;


    }

    @Override
    public void print(Graphics g, double offsetX, double offsetY, double Scale, double ScaleI ) {
        g.setColor(getColor());
        g.fillOval((int) ((hitbox.getCenterX() - offsetX) * Scale-hitbox.getWidth() * ScaleI/2), (int) ((hitbox.getCenterY() - offsetY) * Scale-hitbox.getHeight() * ScaleI/2),
                        (int) (hitbox.getWidth() * ScaleI), (int) (hitbox.getHeight() * ScaleI));
    }

    public boolean isDestructed() {
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)) {
            owner.deadUnits.add(this);
            deadItems.add(this);
            aliveItems.remove(this);
            owner.items.remove(this);
            owner.units.remove(this);
            owner.soldiers.remove(this);
            return true;
        }
        return false;
    }

    public boolean execute() {
        actualiseTarget();
        move();
        return attack();
    }

}
