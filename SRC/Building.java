import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.LinkedList;

public class Building extends Item {


    //______________ATTRIBUTS__________________//

    public static LinkedList<Building> buildings = new LinkedList<Building>();

    /**
     * timer gerant la création : celle-ci est lancée quand il vaut 0.
     */
    private int creationTimer;

    /**
     * temps de création, varie en fonction de la taille du batiment.
     */
    private int creationTime;

    /**
     * compteur pour l’affichage de l’animation.
     */
    private int animationTimer;

    /**
     * permet de savoir s’il a déjà été affiché ce tour et de l’afficher sinon.
     */
    boolean hasBeenPrinted;


    // _____________CONSTRUCTEURS______________//

    /**
     * @param owner possesseur
     * @param topLeftCorner position du batiment
     * @param side taille (i.d. niveau) du batiment
     */
    public Building(Player owner, Point2D topLeftCorner, double side) {
        super(owner, topLeftCorner, side);
        life = Math.pow(side, 2) * LIFE;
        viewRay = VIEW_RAY_BUILDING;
        setTarget(topLeftCorner.getX(), topLeftCorner.getY() + 5 * SIDE);
        buildings.add(this);
        animationTimer = 10;
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

    /**
     * Commence des le debut du jeu a creer une unite
     */
    public void setCreationTimer() {
        creationTimer = creationTime;
    }


    /**
     *  Cree une unite simple au point de spawn de a base. Si d'autre unites y sont presentes, n'en cree pas
     *  mais leur donne une nouvelle target pour liberer l'espace
     */
    public void goAndProcreate() {
        if (owner.simpleUnits.size() < NUMBER_MAX_OF_SIMPLEUNIT) {
            if (targetI == this)
                getLife(LIFE);
            else if (this.spawnIsPossible()) {
                SimpleUnit u =
                    new SimpleUnit(owner,
                                   new Point2D.Double(this.getCenter().getX() - Finals.SIDE / 2.0,
                                                      this.hitbox.getMaxY() + 1), target);
                if (targetI != null)
                    u.setTarget(targetI);
            }
        }
    }


    /**
     * Controle la disponibilite de l'espace de spawn. Si espace indisponible, donne nouvelle target aux unites qui y sont.
     * @return true si l'apparition d'une unite est possible a cet endroit
     */
    public boolean spawnIsPossible() {

        boolean possible = true;
        double largeurCarreSpawnNecessaire = 2 * Finals.SIDE; // TODO A changer selon taille de l'animation?
        double xS = this.getCenter().getX() - Finals.SIDE / 2.0;
        double yS = this.hitbox.getMaxY() + 1;
        Rectangle2D frame = new Rectangle2D.Double(xS, yS, largeurCarreSpawnNecessaire, largeurCarreSpawnNecessaire);

        LinkedList<Item> u = getItemInFrame(frame);

        if (u.isEmpty())

            return possible;

        else {

            double alpha;
            for (Item i : u) {
                alpha = 180 + Math.toRadians((Math.random() * 180));
                i.setTarget(xS + (largeurCarreSpawnNecessaire * 2 + i.hitbox.getMaxX()) * Math.cos(alpha),
                            yS + (largeurCarreSpawnNecessaire * 2 + i.hitbox.getMaxY()) * Math.sin(alpha));
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

        double newSide = Math.sqrt(life / LIFE);
        hitbox.setFrame(getCenter().getX() - newSide / 2.0, getCenter().getY() - newSide / 2.0, newSide, newSide);
        setRadius();
        setCreationTime(life);
        if (creationTime < 10)
            creationTime = 10;
        if (this.isDead())
            return this.isDestructed();

        return false;
    }


    /**
     * @return true si un des batiments est detruit
     */
    public static boolean gameOver() {

        for (Building b : buildings)
            if (b.isDead())
                return true;
        return false;
    }

    /**
     * Execute la création d’unité simples par tour.
     * @return vrai si suppression dans aliveItems
     */
    public boolean execute() {

        creationTimer--;
        actualiseTarget();

        if (life > 2 * LIFE) {
            if (animationTimer == 9) {
                goAndProcreate();
                setCreationTimer();
                animationTimer++;
            }
            if (creationTimer == 0) {
                animationTimer = 0;
            }
        } else
            setCreationTimer();
        return false;
    }


    public void print(Graphics g) {

        g.setColor(getColor());
        g.fillRoundRect((int) ((hitbox.getX() - Camera.cameraX) * Camera.scale),
                        (int) ((hitbox.getY() - Camera.cameraY) * Camera.scale),
                        (int) (hitbox.getWidth() * Camera.scale), (int) (hitbox.getHeight() * Camera.scale), (10),
                        (10));

        if (life > 2 * LIFE) {

            g.setColor(BACKGROUND_COLOR);

            if (animationTimer < 9) {

                g.fillRect((int) ((hitbox.getCenterX() - Camera.cameraX) * Camera.scale) - 20,
                           (int) ((hitbox.getMaxY() - Camera.cameraY) * Camera.scale) - 32, 34, 52);
                g.drawImage(owner.simpleUnitCreation.get(animationTimer),
                            (int) ((hitbox.getCenterX() - Camera.cameraX) * Camera.scale) - 61,
                            (int) ((hitbox.getMaxY() - Camera.cameraY) * Camera.scale) - 40, null);
                animationTimer++;

            } else {
                g.setFont(new Font("Impact", 60, 30));
                g.drawString(Integer.toString(creationTimer), Camera.getXOnScreen(hitbox.getX()),
                             Camera.getYOnScreen(hitbox.getMaxY()));
            }
        }

        hasBeenPrinted = true;
    }


    public void printOverFog(Graphics g) {
        g.setColor(getColor());
        g.drawRoundRect(Camera.getXOnScreen(hitbox.getX()), Camera.getYOnScreen(hitbox.getY()),
                        Camera.getLengthOnScreen(hitbox.getWidth()), Camera.getLengthOnScreen(hitbox.getHeight()), 10,
                        10);
    }


    void setCreationTime(double life) {
        creationTime = (int) ((CREATION_TIME - 10) * Math.exp(0.02 * (27 - life)) + 10);
    }
}
