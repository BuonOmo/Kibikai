import java.awt.Graphics;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleUnit extends Unit {

    //_____________________ATTRIBUTS____________________//

    boolean creating;
    static SimpleUnitGroup builders;

    static LinkedList<SimpleUnit> aliveSimpleUnits = new LinkedList<SimpleUnit>();
    static LinkedList<SimpleUnit> deadSimpleUnits = new LinkedList<SimpleUnit>();

    //___________________CONSTRUCTEURS__________________//

    /**
     * @param owner
     * @param locationToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet) {
        super(owner, topLeftCorner, LIFE, 1, targetToSet);
        life = LIFE;
        viewRay =Finals.VIEW_RAY_SIMPLEUNIT;
        creating = false;
        aliveSimpleUnits.add(this);
        if (owner != null) {
            owner.simpleUnits.add(this);
            if (owner == IA.computer)
                new SimpleUnitGroup(this);
        }

    }

    /**
     * @param owner
     * @param targetToSet
     * @param topLeftCorner
     */
    public SimpleUnit(Player owner, Item targetToSet, Point2D topLeftCorner) {
        this(owner, topLeftCorner, targetToSet.getCenter());
        setTarget(targetToSet);
    }

    /**
     * @param owner
     * @param topLeftCorner
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner) {
        this(owner, topLeftCorner, topLeftCorner);
        setTarget(getCenter());
    }

    //_____________________MÉTHODES____________________//


    /**
     * @param p point ou se fait la transformation
     * @param u SU impliqu�es qui ne sont pas prises en compte dans scan des intersections
     * @return true si des SU peuvent cr�er un S sans intersections
     */
    
    public static boolean canCreate(Point2D p, SimpleUnit[] u){
    	//unit�s pr�sentes autour de p sont dans otherUnits
    	LinkedList<Unit> otherUnits = new LinkedList<Unit>();
        for (Unit i : IA.computer.units) {
            if (i.distanceTo(p) <= (i.radius + 3 * SIDE)) {
                otherUnits.add(i);
            }
        }
        for (Unit i : IA.player.units) {
            if (i.distanceTo(p) <= (i.radius + 3 * SIDE)) {
                otherUnits.add(i);
            }
        }
        for(int k=0;k<u.length;k++){
        	otherUnits.remove(u[k]);
        }
    	if(otherUnits.isEmpty()){
		return true;  
    	}else{
    		return false;
    	}
    }


    /**
     * Méthode principale pour la création de soldats.
     * @param u tableau de 3 US
     * @param p position de la création
     */
    public static void createSoldier(SimpleUnit[] u, Point2D p) {
        if (u != null) {
            if (u.length == 3 &&
                u[0] != null && u[1] != null && u[2] != null &&
                u[0].owner == u[1].owner && u[0].owner == u[2].owner &&
                u[0] != u[1] && u[0] != u[2]) {

                
                for (SimpleUnit element : u) {
                    element.creating = true;
                }
                setTriangularTarget(u, p);
                builders = new SimpleUnitGroup(u);
            }
        }
    }

    /**
     * @param p point ou sont prises les unités les plus proches
     * @param o possesseur des unités
     * @param t point ou est créé le soldat
     */
    public static void createSoldier(Point2D p, Player o, Point2D t) {
        if (o.simpleUnits.size() > 2)
            createSoldier(getNClosestSimpleUnitsFromO(3, p, o), t);
    }

    /**
     * @param p position ou sont prises les unités et ou sera créé le soldat
     * @param o
     */
    public static void createSoldier(Point2D p, Player o) {
       createSoldier(p, o, p);
    }

    public static void createSoldierOnBarycenter(SimpleUnit[] u) {
        boolean error = false;
        for (int i=0; i<u.length; i++){
            if (u[i] == null)
                error = true;
        }
        
        if (!error)
            createSoldier(u, UnitGroup.getPosition(u));
    }

    public static void createSoldierOnBarycenter(Point2D p, Player o) {
        if (o.simpleUnits.size() > 2)
            createSoldierOnBarycenter(getNClosestSimpleUnitsFromO(3, p, o));
    }
    // au dessus méthodes verifiées
    
    // appelée par ia
    public void createSoldier(SimpleUnit u1, SimpleUnit u2) {
        createSoldierOnBarycenter(new SimpleUnit[] {this, u1, u2});

    }
    // appelée par ia
    public void createSoldier() {
        createSoldierOnBarycenter(getNClosestSimpleUnitsFromO(3, getCenter(), owner));
    }

    public boolean build() {
        
        if ((builders.distanceTo(builders.getPosition()) <= CREATION_RANGE) && (builders.group.size() == 3) &&
            (owner.soldiers.size() < NUMBER_MAX_OF_SOLDIER) && canCreate(target, builders.toSimpleUnit()) && builders.isOnTarget()) {
            new Soldier(owner,
                        new Point2D.Double(builders.getPosition().getX() - 1,
                                           builders.getPosition().getY() - 1),
                        builders.getQuantityOfLife());
            for (Item i : builders.getGroup()){
                i.getLife(-i.life);
            }
            return true;
        }

        return false;
    }
    
    public static SimpleUnit[] getNClosestSimpleUnitsFromOInL(int n, Point2D p, Player o, List<SimpleUnit> l) {
        if (!(l == null)){
            SimpleUnit[] toReturn = new SimpleUnit[n];
            ArrayList<SimpleUnit> inOrder = new ArrayList<SimpleUnit>(n);
            LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(l);
            inOrder.add(toCheck.getFirst());
            int c = 0;
            for (SimpleUnit check : toCheck){
                
                for (SimpleUnit s : inOrder) {
                    if (check.distanceTo(p) < s.distanceTo(p) && check != s){
                        inOrder.add(c, check);
                        if (c>=n-1)
                            inOrder.remove(n);
                        break;
                    }
                    c++;
                }
                c=0;
                
            }
            for (SimpleUnit i : inOrder){
                toReturn[c] = i;
                c++;
                if (c == n)
                    break;
            }
            return toReturn;
        }
        return null;
    }
    
    public static SimpleUnit[] getNClosestSimpleUnitsFromO(int n, Point2D p, Player o) {
        return getNClosestSimpleUnitsFromOInL(n, p, o, o.simpleUnits);
    }
    public static SimpleUnit[] getNClosestSimpleUnitsFromList(int n, Point2D p, LinkedList<SimpleUnit> l) {

        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(l);
        if (toCheck.size() < n)
            return null;
        SimpleUnit[] toReturn = new SimpleUnit[n];
        int i = 0;
        for (SimpleUnit Su : toCheck) {
            if (i > n - 1)
                break;
            toReturn[i] = Su;
            i++;
        }
        for (SimpleUnit Su : toCheck) {
            boolean isNotInTab = true;
            for (int j = 0; j < n; j++)
                if (toReturn[j] == Su) {
                    isNotInTab = false;
                    break;
                }
            for (int j = 0; j < n - 1; j++)
                if (p.distance(Su.getCenter()) < p.distance(toReturn[j].getCenter()) && isNotInTab)
                    toReturn[j] = Su;

        }
        return toReturn;
    }

    public static SimpleUnit getClosestSimpleUnitsFromO(Point2D p, Player o) {
        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(o.simpleUnits);
        if (toCheck.size() == 0)
            return null;
        SimpleUnit toReturn;
        toReturn = toCheck.getFirst();
        for (SimpleUnit i : toCheck) {
            if (p.distance(i.getCenter()) <= p.distance(toReturn.getCenter())) {
                toReturn = i;
            }

        }
        return toReturn;
    }
    
    public static SimpleUnit getClosestSimpleUnitInT(Point2D p, SimpleUnit[] t) {
        
        if (t.length == 0)
            return null;
        SimpleUnit toReturn;
        toReturn = t[0];
        for (SimpleUnit i : t) {
            if (p.distance(i.getCenter()) <= p.distance(toReturn.getCenter())) {
                toReturn = i;
            }

        }
        return toReturn;
    }
    
    /**
     * 
     */
    public static void setTriangularTarget(SimpleUnit[] tab, Point2D p){
        if (tab.length == 3){
            
            double alpha = 0;
            Point2D targets[] = new Point2D[3];
            /*
            for (SimpleUnit s : tab){
                
                s.setTarget(p.getX() + 1 * Math.cos(alpha), p.getY() + 1 * Math.sin(alpha));
                alpha+= 2.0 * Math.PI / 3.0;
                
            }
            */
            
            for (int i=0; i<3; i++){
                targets[i]= new Point2D.Double(p.getX() + 1 * Math.cos(alpha), p.getY() + 1 * Math.sin(alpha));
                alpha+= 2.0 * Math.PI / 3.0;
            }
            setThreeTargets(tab, targets);
            
        }
    }
    
    /**
     * soin de la 'targetI'
     */
    public boolean heal() {
        if (targetI != null && targetI.getClass().getName() != "SimpleUnit" && this.hasSameOwner(targetI)) {
            if (targetI.isDead()) {

                stop();
            } else if (this.isCloseTo(targetI, HEALING_RANGE)) {
                targetI.getLife(life);
                this.getLife(-life);
                return this.isDestructed();
            }
        }
        return false;
    }

    @Override
    public boolean isDestructed() {
        //a faire au niveau Unit et Batiment ne pas oublier de traiter Plyer.Units et Plyer.deadUnits
        if (!deadItems.contains(this)) {
            owner.deadUnits.add(this);
            deadItems.add(this);
            aliveItems.remove(this);
            deadSimpleUnits.add(this);
            aliveSimpleUnits.remove(this);
            owner.items.remove(this);
            owner.units.remove(this);
            owner.simpleUnits.remove(this);
            dyingUnits.add(this);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean[][] fog ( double offsetX, double offsetY, boolean[][] tab, double Scale){
        
        // animation de destruction
        if (this.isDead()){
            if (viewRay <= 0)
                dyingUnits.remove(this);
            else
                viewRay-= VIEW_RAY_SIMPLEUNIT/6.0;
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
        return tab; // ce return ne serait-il pas inutile par hasard ?
    }
    
    public boolean execute() {
        actualiseTarget();
        move();
        if (creating)
            return build();
        return heal();
    }
    
    public boolean testSpawn() {
        LinkedList<Unit> computerOthers = this.scanPerimeter((int)(Finals.SIDE),IA.computer);
        LinkedList<Unit> playerOthers = this.scanPerimeter((int)(Finals.SIDE),IA.player);
        computerOthers.remove(this);
        playerOthers.remove(this);
        if(computerOthers.isEmpty() && playerOthers.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
        
    @Override
    public void print(Graphics g) {
        
        int x, y;
        x = (int) ((hitbox.getX() - Camera.cameraX) * Camera.scale);
        y = (int) ((hitbox.getY() - Camera.cameraY) * Camera.scale);
        
        
        if (selected)
            g.drawImage(owner.simpleUnitAliveSelected.get((int) ((3.0 * life - 0.00001) / lifeMAX)),
                        x, y, null);
        else
            g.drawImage(owner.simpleUnitAlive.get((int) ((3.0 * life - 0.00001) / lifeMAX)),
                        x, y, null);
        
    }
    
    @Override
    public void printDieAnimation(Graphics g) {
        // TODO Implement this method
    }
}
