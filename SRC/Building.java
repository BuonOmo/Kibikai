import java.awt.geom.Point2D;

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
     *  Cree une unitÃ© simple et la rajoute dans le tableau du joueur. Elle se dirige au target et n'est crée que si l'emplacement est vide.
     *  Dans le cas contraire, on recherche une position adaptée sur un rayon qui s'élargira si nécessaire.
     *  Si target est sur la base, elle ne se deplaceront pas à la sortie.
     */
    public void goAndProcreate() {

    	if (owner.simpleUnits.size() < NUMBER_MAX_OF_SIMPLEUNIT) {
        	//rayon du spawn
        	double R1 = this.radius + Finals.SIDE*3/2;
            //elementaires de déplacement
            double dx, dy;
            if(hitbox.contains(this.target)){
            	dx=1; dy=1;
            }else{
            	dx = (double) (target.getX() - hitbox.getCenterX());
            	dy = (double) (target.getY() - hitbox.getCenterY());
            }
            double distanceCentreTarget = Math.sqrt(dx*dx+dy*dy);
            dx = dx/distanceCentreTarget;
            dy = dy/distanceCentreTarget;
            //angle a balayer
            double alpha;
            double beta;
            double angleIncrement; 
            //premier spawnpoint et création de l'unité
            double x = hitbox.getCenterX() + (dx)*R1 - Finals.SIDE/2;
            double y =  hitbox.getCenterY() + (dy)*R1 - Finals.SIDE/2;
            Point2D.Double spawnPoint = new Point2D.Double(x, y);
            SimpleUnit bizuth = new SimpleUnit(this.owner, spawnPoint, target);
            System.out.println("PREMIERE CREE chez" + owner.name);
            boolean spawned = bizuth.testSpawn();            
            //elementaires de deplacements lors du balayage
            double ux = dx;
            double uy = dy;
            
            while(!spawned){
                R1 = R1 + (double)(Finals.SIDE)*3/2;
                angleIncrement = Math.toDegrees(Finals.SIDE/R1); 
                alpha = 0;
                beta = 0;
        		System.out.println("premier spawn pas accepté" + owner.name);
               	while(!spawned && alpha<=(180 + angleIncrement)){           
                		alpha = alpha + angleIncrement;
                		ux = Math.cos(Math.toRadians(alpha))*dx;
                		uy = Math.sin(Math.toRadians(alpha))*dy;
                		x = hitbox.getCenterX() + (ux)*R1;
                        y =  hitbox.getCenterY() + (uy)*R1;
                		bizuth.setLocationFromCenter(x, y);
                		spawned = bizuth.testSpawn();
                		System.out.println("alpha testé"+ owner.name);
                		if(!spawned){
                			beta = -alpha;
                			ux=Math.cos(Math.toRadians(beta))*dx;
                    		uy=Math.sin(Math.toRadians(beta))*dy;
                    		x = hitbox.getCenterX() + (ux)*R1;
                                y =  hitbox.getCenterY() + (uy)*R1;
                    		bizuth.setLocationFromCenter(x, y);
                    		spawned = bizuth.testSpawn();
                    		System.out.println("beta testé"+ owner.name);
                		}
                }
             }  
            	
            if(hitbox.contains(this.target)){
           		bizuth.setTarget(new Point2D.Double(x,y));
            }
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
