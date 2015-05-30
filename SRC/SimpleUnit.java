import java.awt.geom.Point2D;
import java.util.LinkedList;

public class SimpleUnit extends Unit {

    //_____________________ATTRIBUTS____________________//

    boolean creating;
    SimpleUnit builder1, builder2;
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
        creating = false;
        builder1 = null;
        builder2 = null;
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
    }

    //_____________________MÃ‰THODES____________________//


    /**
     * @param p point ou se fait la transformation
     * @param u SU impliquées qui ne sont pas prises en compte dans scan des intersections
     * @return true si des SU peuvent créer un S sans intersections
     */
    
    public static boolean canCreate(Point2D p, SimpleUnit[] u){
    	//unités présentes autour de p sont dans otherUnits
    	LinkedList<Unit> otherUnits = new LinkedList<Unit>();
        for (Unit i : IA.computer.units) {
            if (i.distanceTo(p) <= (i.radius + Finals.SIDESOLDIER)) {
                otherUnits.add(i);
            }
        }
        for (Unit i : IA.player.units) {
            if (i.distanceTo(p) <= (i.radius + Finals.SIDESOLDIER)) {
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
    
    
    public static void createSoldier(SimpleUnit[] u, Point2D p) {
        if (u != null) {
            if (u.length == 3 && u[0] != null && u[1] != null && u[2] != null && u[0].owner == u[1].owner &&
                u[0].owner == u[2].owner) {

                for (SimpleUnit element : u) {
                    element.creating = true;
                    element.setTarget(p);
                }
                builders = new SimpleUnitGroup(u);
            }
        }
    }

    public static void createSoldier(Point2D p, Player o, Point2D t) {
        createSoldier(getNClosestSimpleUnitsFromO(3, p, o), t);
    }

    public static void createSoldier(Point2D p, Player o) {
        if (o.simpleUnits.size() > 2)
            createSoldier(getNClosestSimpleUnitsFromO(3, p, o), p);
    }

    public static void createSoldierOnBaryCenter(SimpleUnit[] u) {
        createSoldier(u, UnitGroup.getPosition(u));
    }

    public static void createSoldierOnBarycenter(Point2D p, Player o) {
        if (o.simpleUnits.size() > 2)
            createSoldierOnBaryCenter(getNClosestSimpleUnitsFromO(3, p, o));
    }

    public void createSoldier(Point2D t, SimpleUnit u1, SimpleUnit u2) {
        if (!creating)
            setBuilders(u1, u2, t);

    }

    public void createSoldier(SimpleUnit u1, SimpleUnit u2) {
        if (!creating)
            setBuilders(u1, u2);

    }

    public void createSoldier() {

        if (this.owner.simpleUnits.size() > 2) {
            if (!creating) {
                SimpleUnit theTwo[] = getTwoClosestSimpleUnits();
                setBuilders(theTwo[0], theTwo[1]);
            }

        } else
            this.error("createSoldier");
        //TODO add to erreurs
    }

    public boolean build() {

        if ((builders.distanceTo(target) <= CREATION_RANGE) && (builders.group.size() == 3) &&
            (owner.soldiers.size() < NUMBER_MAX_OF_SOLDIER)) {
            new Soldier(owner, target, builders.getQuantityOfLife());
            return builders.isDestructed();
        }

        return false;
    }

    /**
     * @param u1
     * @param u2
     * @param t position du soldat Ã  crÃ©er
     */
    private void setBuilders(SimpleUnit u1, SimpleUnit u2, Point2D t) {
        if (hasSameOwner(u1) && hasSameOwner(u2)) {
            creating = true;
            builders = new SimpleUnitGroup(this);
            builders.add(u1);
            builders.add(u2);
            builder1 = u1;
            builder2 = u2;
            builders.setTarget(t);
        } else
            error("SimpleUnit.setBuilders");
    }

    /**
     * crÃ©e un soldat au niveau du barycentre des trois unitÃ©s.
     * @param u1
     * @param u2
     */
    private void setBuilders(SimpleUnit u1, SimpleUnit u2) {
        if (hasSameOwner(u1) && hasSameOwner(u2)) {
            creating = true;
            builders = new SimpleUnitGroup(this);
            builders.add(u1);
            builders.add(u2);
            builder1 = u1;
            builder2 = u2;
            builders.setTarget(builders.getPosition());
            //builders.setTarget(IA.computer.base.getCenter());
        } else
            error("SimpleUnit.setBuilders");
    }

    private SimpleUnit[] getTwoClosestSimpleUnits() {
        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(owner.simpleUnits);
        toCheck.remove(this);
        if (toCheck.size() < 1)
            return null;
        SimpleUnit[] toReturn = { toCheck.getFirst(), toCheck.get(1) };
        for (SimpleUnit i : toCheck) {
            if (distanceTo(i) <= distanceTo(toReturn[0])) {
                toReturn[1] = toReturn[0];
                toReturn[0] = i;
            }

        }
        return toReturn;
    }

    public static SimpleUnit[] getNClosestSimpleUnitsFromO(int n, Point2D p, Player o) {

        LinkedList<SimpleUnit> toCheck = new LinkedList<SimpleUnit>(o.simpleUnits);
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

    /**
     * soin de la 'targetI'
     */
    public boolean heal() {
        if (targetI != null && targetI.getClass().getName() != "SimpleUnit" && this.hasSameOwner(targetI)) {
            if (targetI.isDead()) {

                stop();
                error("SimpleUnit.heal");
            } else if (this.isCloseTo(targetI, HEALING_RANGE)) {

                targetI.getLife(life);
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
            return true;
        }
        return false;
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
}
