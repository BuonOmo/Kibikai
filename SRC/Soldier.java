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
        viewRay = 0;
        hitbox = new Ellipse2D.Double(topLeftCorner.getX(), topLeftCorner.getY(), 2, 2);
        life = (lifeToSet < lifeMAX) ? lifeToSet : lifeMAX;
        damage = 0;
        if (owner != null) {
            owner.soldiers.add(this);
            if (owner == Game.computer)
                new SoldierGroup(this);
        }
        setTarget(getCenter());
    }

    /**
     * @param owner Détenteur de l’unité
     * @param topLeftCorner position de l’unité
     */
    public Soldier(Player owner, Point2D topLeftCorner) {
        this(owner, topLeftCorner, LIFE * 3);
    }
    
    public Soldier(Player owner, Point2D topLeftCorner, Item targetIToSet){
        this(owner, topLeftCorner);
        setTarget(targetIToSet);
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
    public void print(Graphics g) {
        int x, y;
        x = (int) ((hitbox.getX() - Camera.cameraX) * Camera.scale);
        y = (int) ((hitbox.getY() - Camera.cameraY) * Camera.scale);
        
        
        if (UI.time - this.firstAppearance < 8)
            g.drawImage(owner.soldierCreation.get((int) (UI.time - this.firstAppearance)),
                        Player.soldierCreationOffset + x, Player.soldierCreationOffset + y, null);
        else{
            if (selected)
                g.drawImage(owner.soldierAliveSelected.get((int) ((3.0 * life - 0.00001) / lifeMAX)),
                        x, y, null);
            else
                g.drawImage(owner.soldierAlive.get((int) ((3.0 * life - 0.00001) / lifeMAX)),
                        x, y, null);
            
        }
    }
    
    public void printDieAnimation(Graphics g){
        int x, y;
        x = (int) ((hitbox.getX() - Camera.cameraX) * Camera.scale - 10);
        y = (int) ((hitbox.getY() - Camera.cameraY) * Camera.scale - 10);
        
        if (selected)
            g.drawImage(owner.soldierDeathSelected.get(c),
                        x, y, null);
        else
            g.drawImage(owner.soldierDeath.get(c),
                        x, y, null);
        System.out.println(c);
        c++;
        
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
            
            dyingUnits.add(this);
            stop();
            
            return true;
        }
        return false;
    }

    @Override
    public boolean[][] fog ( double offsetX, double offsetY, boolean[][] tab, double Scale){
        
        // animations de creation et destruction
        if (this.isDead()){
            viewRay-= VIEW_RAY_SOLDIER/6.0;
        }
        else if (viewRay < VIEW_RAY_SOLDIER){
            viewRay+= VIEW_RAY_SOLDIER/9.0;
        }
        
        double R = (viewRay+hitbox.getWidth())*Scale;
        for (int i =(int) -R ; i <= (int) R ; i++)
            for (int j = -(int)Math.sqrt(R*R-i*i) ; j <= (int)Math.sqrt(R*R-i*i) ; j++){
                if ((int)((getCenter().getX() - offsetX) * Scale)+i>=0
                    &&(int)((getCenter().getX() - offsetX) * Scale)+i<tab.length
                    &&(int)((getCenter().getY() - offsetY) * Scale)+j>=0
                    &&(int)((getCenter().getY() - offsetY) * Scale)+j<tab[0].length
                    )
                    tab[(int)((getCenter().getX() - offsetX) * Scale)+i][(int)((getCenter().getY() - offsetY) * Scale)+j] = false;
                        //=(Math.random() > 0.5) ? false : true;
            }
        return tab;
    }
    
    public boolean execute() {
        
        actualiseTarget();
        if (targetI != null && !hasSameOwner(targetI)){
            if (!isCloseTo(targetI, ATTACK_RANGE))
                move();
        }
        else {
            move();
        }
        return attack();
    }
}
