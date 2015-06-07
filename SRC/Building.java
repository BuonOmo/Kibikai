import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class Building extends Item {
    //______________ATTRIBUTS__________________//

    public static LinkedList<Building> buildings = new LinkedList<Building>();
    private int creationTimer;
    boolean spawnPossible;
    
    /**
     * temps de création, varie en fonction de la taille du batiment.
     */
    private int creationTime;
    
    /**
     * compteur pour l’affichage de l’animation.
     */
    private int c;
    // _____________CONSTRUCTEURS______________//

    /**
     * @param owner possesseur
     * @param topLeftCorner position du batiment
     * @param side taille (i.d. niveau) du batiment
     */
    public Building(Player owner, Point2D topLeftCorner, double side) {
        super(owner, topLeftCorner, side);
        life = Math.pow(side, 2) * LIFE;
        viewRay =VIEW_RAY_BUILDING;
        //target.setLocation(topLeftCorner.getX() + 2*SIDE, topLeftCorner.getY() + 2*SIDE);
        setTarget(topLeftCorner.getX(), topLeftCorner.getY() + 5 * SIDE);
        buildings.add(this);
        c=10;
        creationTime = CREATION_TIME;
        setCreationTimer();
    }

    /**
     * @param owner possesseur
     * @param topLeftCorner postion du batiment
     */
    public Building(Player owner, Point2D topLeftCorner) {
        this(owner, topLeftCorner, 3);
    }
    //________________METHODES_______________//

    
    public void setCreationTimer(){
        creationTimer = creationTime;
    }
    
    /**
     *  Cree une unite simple au point de spawn de a base. Si d'autre unit�s y sont pr�sentes, n'en cr�e pas
     *  mais leur donne une nouvelle target pour lib�rer l'espace
     */
    public void goAndProcreate() {
    	//Ajout dans le tableau du joueur ici ou pas ? non ça le fait tout seul
    	if (owner.simpleUnits.size() < NUMBER_MAX_OF_SIMPLEUNIT) {
            if (targetI == this)
                getLife(LIFE);
            else if(this.spawnIsPossible()){
                 SimpleUnit u = new SimpleUnit(owner, new Point2D.Double(this.getCenter().getX() - Finals.SIDE/2.0,
                                                                         this.hitbox.getMaxY() + 1),
                                               target);
                 if (targetI != null)
                    u.setTarget(targetI);
                    
            }
    	}
    }
    /**
     * Controle la disponibilit� de l'espace de spawn. Si espace indisponible, donne nouvelle target aux unit�s qui y sont.
     * @return
     */
    public boolean spawnIsPossible(){
    	boolean possible = true;
    	double largeurCarreSpawnNecessaire = 4*Finals.SIDE; // TODO A changer selon taille de l'animation?
    	double xS = this.getCenter().getX() - Finals.SIDE/2.0;
    	double yS = this.hitbox.getMaxY() + 1;
    	Rectangle2D frame = new Rectangle2D.Double( xS-largeurCarreSpawnNecessaire/2, yS, largeurCarreSpawnNecessaire, largeurCarreSpawnNecessaire);
    	
        LinkedList<Item> u = getItemInFrame(frame); //prend en compte le centre des items seulement: on scan sur une large etendue qu'on r�duit ensuite
        frame.setRect(new Rectangle2D.Double( xS-1, yS, Finals.SIDE+2, Finals.SIDE+2));
        for(int i=0;i<u.size();i++){
        	if(!u.get(i).hitbox.intersects(frame)){ u.remove(u.get(i));}
        }
    	if(u.isEmpty()){
    		return possible;
    	}else{
    		double alpha;
            for(Item i : u){
    			alpha = 180 + Math.toRadians((Math.random()*180));
    			i.setTarget(xS + (largeurCarreSpawnNecessaire*2 + i.hitbox.getMaxX())*Math.cos(alpha),
                                    yS + (largeurCarreSpawnNecessaire*2 + i.hitbox.getMaxY())*Math.sin(alpha));
    		}
    		return false;
    	}
    	
    	
    }
    
    /**
     * Gère la vie d’un batiment et sa taille.
     * @param amount vie ajoutée (- pour en enlever)
     */
    public boolean getLife(double amount) {

        life += amount;

        //TODO gerer les intersection lors du grandissement
        double newSide = Math.sqrt(life / LIFE);
        hitbox.setFrame(getCenter().getX() - newSide / 2.0, getCenter().getY() - newSide / 2.0, newSide, newSide);
        setRadius();
        setCreationTime(life);
        if (creationTime<10)
            creationTime = 10;
        if (this.isDead())
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
        creationTimer--;
        actualiseTarget();

        if (life > 2*LIFE) {
            
            if (creationTimer == 0) {
                goAndProcreate();
                setCreationTimer();
            }
            //((UI.time-8) % (int) (4 / (hitbox.getHeight() * UNIT_PER_SECOND) + 1) == 0)
            
        }
        this.spawnPossible = spawnIsPossible();
        return false;
    }
    
    public void print(Graphics g) {
        
        g.setColor(getColor());
        // TODO virer ce putain de 3 et mettre un truc cohérent pour les arcs de cercle
        g.fillRoundRect((int) ((hitbox.getX() - Camera.cameraX) * Camera.scale),
                        (int) ((hitbox.getY() - Camera.cameraY) * Camera.scale),
                        (int) (hitbox.getWidth() * Camera.scale),
                        (int) (hitbox.getHeight() * Camera.scale), (10), (10));
        
        if (life > 2*LIFE){
            g.setColor(BACKGROUND_COLOR);
            g.setFont(new Font("Impact",60,30));
            g.drawString(Integer.toString(creationTimer),
                         Camera.getXOnScreen(hitbox.getX()),
                         Camera.getYOnScreen(hitbox.getMaxY()));
            
            
            if (creationTimer < 9){
                
                g.fillRect((int)((hitbox.getCenterX() - Camera.cameraX)*Camera.scale) -20,
                           (int)((hitbox.getMaxY() - Camera.cameraY)*Camera.scale)- 32, 
                           34, 52);
                g.drawImage(owner.simpleUnitCreation.get(8 - creationTimer), 
                            (int)((hitbox.getCenterX() - Camera.cameraX)*Camera.scale) - 61, 
                            (int)((hitbox.getMaxY() - Camera.cameraY)*Camera.scale) - 40,
                            null);
                
            }
        }
    }
    void setCreationTime(double life){
        creationTime = (int) (140*Math.exp(0.02*(27 - life)) + 10);
    }
}
