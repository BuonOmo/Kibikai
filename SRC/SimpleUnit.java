import java.awt.Graphics;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleUnit extends Unit {

    //_____________________ATTRIBUTS____________________//

    /**
     * vrai si l’unitée est en train de créer un soldat.
     */
    boolean creating;
    
    /**
     * Unités qui construisent un soldat (groupe de 3).
     */
    SimpleUnitGroup builders;

    static LinkedList<SimpleUnit> aliveSimpleUnits = new LinkedList<SimpleUnit>();
    static LinkedList<SimpleUnit> deadSimpleUnits = new LinkedList<SimpleUnit>();

    //___________________CONSTRUCTEURS__________________//

    /**
     * @param owner
     * @param topLeftCorner
     * @param targetToSet
     */
    public SimpleUnit(Player owner, Point2D topLeftCorner, Point2D targetToSet) {
        
        super(owner, topLeftCorner, LIFE, 1, targetToSet);
        
        life = LIFE;
        viewRay =Finals.VIEW_RAY_SIMPLEUNIT;
        creating = false;
        aliveSimpleUnits.add(this);
        
        if (owner != null) {
            owner.simpleUnits.add(this);
            if (owner == Game.computer){
                new SimpleUnitGroup(this,true);
            }
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
     * Permet de déplacer une unité vers un point donné.
     * @param targetToSet Point d’arrivée de l’unité (objectif)
     */
    @Override
    public void setTarget(Point2D targetToSet) {
        targetI = null;
        target = targetToSet;
        creating = false;
    }
    
    
    /**
     * @param p point de création
     * @param u unités impliqueés
     * @return vraie si la création est possible
     */
    public static boolean canCreate(Point2D p, SimpleUnit[] u){
        
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
     * @param u tableau de 3 unités
     * @param p position de la création
     */
    public static void createSoldier(SimpleUnit[] u, Point2D p) {
        if (u != null) {
            if (u.length == 3 &&
                u[0] != null && u[1] != null && u[2] != null &&
                u[0].owner == u[1].owner && u[0].owner == u[2].owner &&
                u[0] != u[1] && u[0] != u[2]) {

                SimpleUnitGroup b = new SimpleUnitGroup(u);
                
                
                setTriangularTarget(u, p);
                for (SimpleUnit element : u) {
                    element.creating = true;
                    element.builders = b;
                }
                
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
     * @param o possesseur des unités
     */
    public static void createSoldier(Point2D p, Player o) {
       createSoldier(p, o, p);
    }


    /**
     * Vérifie si un soldat est créable (unités au bon endroit etc) et crée le soldat.
     * @return vraie si aliveItems est modifié
     */
    public boolean build() {
        
        if ((builders.distanceTo(builders.getPosition()) <= CREATION_RANGE) && (builders.group.size() == 3) &&
            (owner.soldiers.size() < NUMBER_MAX_OF_SOLDIER) && canCreate(target, builders.toSimpleUnit()) && 
            builders.isOnTarget() && !builders.hasDead()) {
            new Soldier(owner,
                        new Point2D.Double(builders.getPosition().getX() - 1,
                                           builders.getPosition().getY() - 1),
                        builders.getQuantityOfLife() * 2.0/3.0);
            for (Item i : builders.getGroup()){
                i.getLife(-i.life);
            }
            
            return true;
        }

        return false;
    }

    /**
     * Renvoi les n plus proches unités simples d’un point appartenant à une liste, ne vérifie pas le possesseur.
     * @param n nombre d’unités du tableau à renvoyer
     * @param p point ou on veut des unités proches
     * @param l liste d’unités simples à verifier
     * @return tableau d’unités simples de dimension n
     */
    public static SimpleUnit[] getNClosestSimpleUnitsInL(int n, Point2D p, List<SimpleUnit> l) {

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
        /*
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
        */
    }

     /**
      * Renvoi les n plus proches unités simples d’un point appartenant au possesseur.
      * @param n nombre d’unités du tableau à renvoyer
      * @param p point ou on veut des unités proches
      * @param o possesseur
      * @return tableau d’unités simples de dimension n
      */
    public static SimpleUnit[] getNClosestSimpleUnitsFromO(int n, Point2D p, Player o) {
        return getNClosestSimpleUnitsInL(n, p, o.simpleUnits);
    }
     
    /**
     * Renvoi l’unité simple la plus proche d’un point appartenant au possesseur.
     * @param p point de proximité
     * @param o possesseur
     * @return unité simple
     */
    public static SimpleUnit getClosestSimpleUnitsFromO(Point2D p, Player o) {
        return getNClosestSimpleUnitsFromO(1, p, o)[0];
    }
    
    /**
     * Renvoi l’unité simple la plus proche d’un point venant d’un tableau.
     * @param p point de proximité
     * @param t tableau d’unités simples
     * @return unité simple
     */
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
     * Place les unités pour créer un soldat.
     * @param tab tableau de 3 unités simples
     * @param p point autour duquel sont distribués les unités
     */
    public static void setTriangularTarget(SimpleUnit[] tab, Point2D p){
        if (tab.length == 3){
            
            double alpha = 0;
            Point2D targets[] = new Point2D[3];

            
            for (int i=0; i<3; i++){
                targets[i]= new Point2D.Double(p.getX() + 1 * Math.cos(alpha), p.getY() + 1 * Math.sin(alpha));
                alpha+= 2.0 * Math.PI / 3.0;
            }
            
            setThreeTargets(tab, targets);
            
        }
    }
    
    /**
     * Soin de la 'targetI'.
     * @return vrai si modification de aliveItems
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

    /**
     * Met à jour les listes lors de la mort.
     * @return vrai si modification de aliveItems
     */
    public boolean isDestructed() {
        
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
    
    /**
     * Crée le brouillard de guerre autour de l’objet et modifie la portée du brouillard à la mort.
     * @param offsetX positionne x par rapport à la camera
     * @param offsetY positionne y par rapport à la camera
     * @param tab tableau à modifier en fonction du brouillard
     * @param Scale echelle de la minimap ou de la camera
     */
    public void fog ( double offsetX, double offsetY, boolean[][] tab, double Scale){
        
        // animation de destruction
        if (this.isDead()){
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
    }
    
    /**
     * Execute le déplacement, le soin et la création d’une unité simple.
     * @return vrai si suppression dans aliveItems
     */
    public boolean execute() {
        actualiseTarget();
        move();
        if (creating)
            return build();
        return heal();
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
    
    
    //______________METHODES_NON_UTILISEES___________//
    
    /**
     * @param u unités créant le soldat
     */
    public static void createSoldierOnBarycenter(SimpleUnit[] u) {
        boolean error = false;
        for (int i=0; i<u.length; i++){
            if (u[i] == null)
                error = true;
        }
        
        if (!error)
            createSoldier(u, UnitGroup.getPosition(u));
    }
    
    @Override
    public void printDieAnimation(Graphics g) {
        // TODO Implement this method
    }
    
}
