import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.LinkedList;


public abstract class Unit extends Item {

    /**
     * Liste permettant d’attribuer des récompenses aux unités de l’IA pour le Q learning.
     */
    public LinkedList<IAHistObj> histoList = new LinkedList<IAHistObj>();

    /**
     * Liste pour l’affichage d’animations des unités mourrantes.
     */
    public static LinkedList<Unit> dyingUnits = new LinkedList<Unit>();

    /**
     * Vie maximale d’une unité
     */
    double lifeMAX;

    /**
     * Temps d’arrivée sur le jeu, permet de gerer les animations
     * de création et les récompenses attribuées.
     */
    double firstAppearance;

    /**
     * Stratégie de l’unité si c’est une IA.
     */
    int strategyincurs = 0;

    /**
     * compteur pour l’animation de mort.
     */
    int dieTimer;

    /**
     * @param owner
     * @param topLeftCorner
     * @param width
     * @param height
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int width, int height) {
        super(owner, topLeftCorner, width, height);
        lifeMAX = lifeMAXToSet;
        owner.units.add(this);
        firstAppearance = UI.time;
        stop();
        dieTimer = 0;
    }

    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitbox
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int side) {
        this(owner, topLeftCorner, lifeMAXToSet, side, side);
    }


    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param lifeMAXToSet
     * @param side coté de la hitbox
     * @param targetToSet point à associer en cible
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int side, Point2D targetToSet) {
        this(owner, topLeftCorner, lifeMAXToSet, side);
        setTarget(targetToSet);
    }

    //________________MÉTHODES_______________//

    /**
     * @return vraie si très proche de l’objectif (0.2 unité de jeu)
     */
    public boolean isOnTarget() {
        if (this.isCloseTo(target, 0.2))
            return true;
        return false;
    }

    /**
     * arrête le mouvement d’une unité.
     */
    public void stop() {
        setTarget(this.getCenter());
    }

    /**
     * Gère la vie d’une unité.
     * @param amount vie ajoutée (- pour en enlever)
     * @return vraie si un élement de aliveItem à été modifié
     */
    public boolean getLife(double amount) {
        life += amount;
        if (life <= 0)
            return this.isDestructed();
        else if (life >= lifeMAX)
            life = lifeMAX;
        return false;
    }


    @Override
    public Color getColor() {

        double percent;

        percent = (life + lifeMAX / 2.0) / (1.5 * lifeMAX);
        percent = (percent > 1) ? 1 : (percent < 0) ? 0 : percent;

        if (selected)
            return new Color(0, (int) (255 * percent), (int) (255 * percent));

        return new Color((int) (owner.color.getRed() * percent), (int) (owner.color.getGreen() * percent),
                         (int) (owner.color.getBlue() * percent));
    }

    /**
     * Gère le déplacement d’une unité.
     */
    public void move() {

        Point2D d;
        d = new Point2D.Double();
        d.setLocation(setVector());
        hitbox.setFrame(hitbox.getX() + d.getX(), hitbox.getY() + d.getY(), hitbox.getWidth(), hitbox.getHeight());

    }

    /**
     * Affiche l’animation de mort d’une unité.
     * @param g
     */
    public abstract void printDieAnimation(Graphics g);

    //________MÉTHODES POUR LE DÉPLACEMENT______//

    /**
     * Permet de trouver le point d’arrivée du déplacement en fonction
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @param alphaDegre angle du déplacement par rapport à la droite Objet-Cible
     * @return point d’arrivé du déplacement
     */
    public Point2D getNewLocationFromCenter(double alphaDegre) {
        Point2D d;
        d = getVector(alphaDegre);
        return new Point2D.Double(hitbox.getCenterX() + d.getX(), hitbox.getCenterY() + d.getY());
    }

    /**
     * Permet de trouver le vecteur du déplacement en fonction
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @param alpha angle du déplacement par rapport à la droite Objet-Cible
     * @return point d’arrivé du déplacement
     */
    private Point2D getVector(double alphaDegre) {
        double alpha;
        alpha = Math.toRadians(alphaDegre);
        Point2D vector;
        vector = new Point2D.Double();
        if (distanceTo(target) > DISTANCE_TO_MOVE)
            vector.setLocation(DISTANCE_TO_MOVE * Math.cos(getAlphaOffset() - alpha),
                               DISTANCE_TO_MOVE * Math.sin(getAlphaOffset() - alpha));
        else
            vector.setLocation(target.getX() - hitbox.getCenterX(), target.getY() - hitbox.getCenterY());
        return vector;
    }

    /**
     * permet de trouver le vecteur unitaire de déplacement en fonction
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @return vecteur de déplacement unitaire
     */
    private Point2D getVector() {

        if (distanceTo(target) > DISTANCE_TO_MOVE)
            return new Point2D.Double((target.getX() - hitbox.getCenterX()) * DISTANCE_TO_MOVE /
                                      this.distanceTo(target),
                                      (target.getY() - hitbox.getCenterY()) * DISTANCE_TO_MOVE /
                                      this.distanceTo(target));
        else
            return new Point2D.Double(0, 0);

    }

    /**
     * Choisi un vecteur de déplacement possible en fonction des obstacles.
     * @return vecteur unitaire de déplacement
     */
    Point2D setVector() {
        LinkedList<Item> obstacle;
        obstacle = new LinkedList<Item>(aliveItems);
        obstacle.remove(this);
        Point2D vector, location;
        vector = getVector();
        location = new Point2D.Double(hitbox.getX() + vector.getX(), hitbox.getY() + vector.getY());

        //Gestion des bordures
        /*
        {
            if (hitbox.getX() + vector.getX() < 0) {
                setX(0);
                return new Point2D.Double(0, vector.getY());
            }
            if (hitbox.getX() + vector.getX() + hitbox.getWidth() > WIDTH) {
                setX(WIDTH - hitbox.getWidth());
                return new Point2D.Double(0, vector.getY());
            }

            if (hitbox.getY() + vector.getY() < 0) {
                setY(0);
                return new Point2D.Double(vector.getX(), 0);
            }
            if (hitbox.getY() + vector.getY() + hitbox.getHeight() > HEIGHT) {
                setY(HEIGHT - hitbox.getHeight());
                return new Point2D.Double(vector.getX(), 0);
            }
        }
        */

        // Gestion des objets
        double alpha;
        alpha = 0;
        boolean intersect;
        intersect = false;
        do {
            for (Item i : obstacle) {
                if (this.willIntersect(i, alpha) || this.willIntersect(i, -alpha)) {
                    intersect = true;
                    break;
                }
            }
            if (!intersect)
                return getVector(alpha);
            intersect = false;
            alpha += ALPHA;
        } while (alpha <= 180);

        return new Point2D.Double(0, 0);
    }

    /**
     * Calcul les intersections entre deux cercles.
     * @param other Un autre item qui est proche du premier (methode a n'utiliser que si il y a intersection,
     * sinon tableau vide renvoyé).
     * @return tableau de Point 2D contenant les intersections entres les deux cerles entourant les items.
     */
    public Point2D.Double[] getIntersect(double R0, double R1, Point2D P1, Point2D P2) {

        double x0 = P1.getX();
        double y0 = P1.getY();
        double x1 = P2.getX();
        double y1 = P2.getY();

        double deltaX = x0 - x1;
        double deltaY = y0 - y1;

        double Xa;
        double Ya;
        double Xb;
        double Yb;

        if (y1 != y0) {

            double N = (R1 * R1 - R0 * R0 - x1 * x1 + x0 * x0 - y1 * y1 + y0 * y0) / (2 * deltaY);
            double A = (deltaX / deltaY) * (deltaX / deltaY) + 1;
            double B = 2 * (y0 - N) * (deltaX / deltaY) - 2 * x0;
            double C = y0 * y0 + x0 * x0 + N * N - R0 * R0 - 2 * y0 * N;

            double DELTA = B * B - 4 * A * C;

            if (DELTA > 0) { //mettre >= si on veut qu’il retourne lorsqu’il y a une seule intersection
                DELTA = Math.sqrt(DELTA);
                Xa = (-B - DELTA) / (2 * A);
                Ya = N - Xa * (deltaX / deltaY);
                Xb = (-B + DELTA) / (2 * A);
                Yb = N - Xb * (deltaX / deltaY);
                Point2D.Double[] Intersects = { new Point2D.Double(Xa, Ya), new Point2D.Double(Xb, Yb) };
                return Intersects;
            } else {
                return null;
            }

        } else {
            Xa = (R1 * R1 - R0 * R0 - x1 * x1 + x0 * x0) / (2 * deltaX);
            double A = 1;
            double B = -2 * y1;
            double C = x1 * x1 + Xa * Xa - 2 * x1 * Xa - R1 * R1;
            double DELTA = B * B - 4 * A * C;
            if (DELTA > 0) { //mettre >= si on veut qu’il retourne lorsqu’il y a une seule intersection
                DELTA = Math.sqrt(DELTA);
                Ya = (-B - DELTA) / (2 * A);
                Yb = (-B + DELTA) / (2 * A);
                Point2D.Double[] Intersects = { new Point2D.Double(Xa, Ya), new Point2D.Double(Xa, Yb) };
                return Intersects;
            } else {
                return null;
            }
        }
    }


    /**
     * @param i objet génant
     * @param alphaDegre angle de déplacement courant de l’unité
     * @return vrai s’il y aura intersection au tour suivant
     */
    public boolean willIntersect(Item i, double alphaDegre) {
        Point2D newLocation = getNewLocationFromCenter(alphaDegre);

        if (i.hitbox.intersects(new Rectangle2D.Double(newLocation.getX() - hitbox.getWidth() / 2.0,
                                                       newLocation.getY() - hitbox.getHeight() / 2.0, hitbox.getWidth(),
                                                       hitbox.getHeight())))
            return true;
        return false;
    }

    /**
     * @return offset entre l’axe horizontal et l’axe Unité / Objectif
     */
    public double getAlphaOffset() {
        Point2D vect = getVector();
        double offset;
        offset = Math.atan(vect.getY() / vect.getX());
        if (vect.getX() < 0)
            return offset + Math.PI;
        return offset;
    }

    /**
     * Supprime definitivement l’unité après son animation de fin de vie.
     */
    public void die() {
        if (isDead() && viewRay <= 0) {
            dyingUnits.remove(this);
        }
    }
}
