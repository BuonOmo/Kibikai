import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class Building extends Item {
    //______________ATTRIBUTS__________________//

    public static LinkedList<Building> buildings = new LinkedList<Building>();

    // _____________CONSTRUCTEURS______________//

    /**
     * @param owner possesseur
     * @param topLeftCorner position du batiment
     * @param side taille (i.d. niveau) du batiment
     */
    public Building(Player owner, Point2D topLeftCorner, double side) {
        super(owner, topLeftCorner, side);
        life = Math.pow(side, 2) * LIFE;
        viewRay =Finals.VEW_RAY_BUILDING;
        //target.setLocation(topLeftCorner.getX() + 2*SIDE, topLeftCorner.getY() + 2*SIDE);
        setTarget(topLeftCorner.getX(), topLeftCorner.getY() + 5 * SIDE);
        buildings.add(this);
    }

    /**
     * @param owner possesseur
     * @param topLeftCorner postion du batiment
     */
    public Building(Player owner, Point2D topLeftCorner) {
        this(owner, topLeftCorner, 3);
    }
    //________________METHODES_______________//

    /**
     *  Cree une unite simple au point de spawn de a base. Si d'autre unités y sont présentes, n'en crée pas
     *  mais leur donne une nouvelle target pour libérer l'espace
     */
    public void goAndProcreate() {
    	//Ajout dans le tableau du joueur ici ou pas ?
    	if (owner.simpleUnits.size() < NUMBER_MAX_OF_SIMPLEUNIT) {
    		if(this.spawnIsPossible()){
    			 new SimpleUnit(owner, new Point2D.Double(this.getCenter().getX() - Finals.SIDE/2.0,
                         								  this.hitbox.getMaxY() + 1));
    		}
    	}
    }
    /**
     * Controle la disponibilité de l'espace de spawn. Si espace indisponible, donne nouvelle target aux unités qui y sont.
     * @return
     */
    public boolean spawnIsPossible(){
    	boolean possible = true;
    	double largeurCarreSpawnNecessaire = 2*Finals.SIDE; // TODO A changer selon taille de l'animation?
    	double xS = this.getCenter().getX() - Finals.SIDE/2.0;
    	double yS = this.hitbox.getMaxY() + 1;
    	Rectangle2D frame = new Rectangle2D.Double( xS,
    												yS,
													largeurCarreSpawnNecessaire,
													largeurCarreSpawnNecessaire);
    	@SuppressWarnings("static-access")
		LinkedList<Item> units = this.getItemInFrame(frame);
    	if(units.isEmpty()){
    		return possible;
    	}else{
    		double alpha;
    		for(int i = 0;i<units.size();i++){
    			alpha = 180 + Math.toRadians((Math.random()*180));
    			units.get(i).setTarget(xS + (largeurCarreSpawnNecessaire*2 + units.get(i).hitbox.getMaxX())*Math.cos(alpha),
    								   yS + (largeurCarreSpawnNecessaire*2 + units.get(i).hitbox.getMaxY())*Math.sin(alpha));
    		}
    		return false;
    	}
    	
    	
    }
    /**
     * GÃ¨re la vie dâ€™un batiment et sa taille.
     * @param amount vie ajoutÃ©e (- pour en enlever)
     */

    public boolean getLife(double amount) {

        life += amount;

        //TODO gerer les intersection lors du grandissement
        double newSide = Math.sqrt(life / LIFE);
        double shift = (newSide - hitbox.getHeight()) / 4.0;
        hitbox.setFrame(getCenter().getX() - newSide / 2.0, getCenter().getY() - newSide / 2.0, newSide, newSide);
        setRadius();
        if (life <= 0)
            return this.isDestructed();

        return false;
    }

    public static boolean gameOver() {
        for (Building b : buildings)
            if (b.isDead())
                return true;
        return false;
    }

    public boolean execute() {

        actualiseTarget();

        // pourquoi Ã§a a Ã©tÃ© mis en commentaire ? Câ€™est pour Ã©viter les abus et division par 0
        if (life > LIFE) {

            if (((UI.time) % (int) (4 / (hitbox.getHeight() * UNIT_PER_SECOND) + 1) == 0)) {
                goAndProcreate();
            }
        }
        return false;
    }
}
