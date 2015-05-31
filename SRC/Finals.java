import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

public interface Finals {

    //________________parametres généraux_______________//

    /**
     * nombre de soldat max !!
     */
    public static final int NUMBER_MAX_OF_SOLDIER = 50;

    /**
     * nombre de US max !!
     */
    public static final int NUMBER_MAX_OF_SIMPLEUNIT = 20;


    /**
     * transparence des unités (entre 0 et 255).
     */
    public static final int TRANSPARANCY = 80;


    //______________________terrain______________________//

    /**
     * Taille de la carte en metres.
     */
    public static final int WIDTH = 200, HEIGHT = 200;

    /**
     * Echelle (distance en metre*scale = distance en pixel).
     */
    //public static int scale = 30;


    //__________________ITEM_________________//

    /**
     * Taille du cote dune unite simple en metres.
     */
    public static final double SIDE = 1;

    /**
     * Quantite de vie unitaire en secondes d'attaque. (celle d’une US)
     */
    public static final double LIFE = 3;


    //___________________BUILDING_____________________//

    /**
     * Nombre d’US par secondes crées à l’origine par BA.
     */
    public static final double UNIT_PER_SECOND = 0.005;
    /**
     * Rayon de vision (pour la brouillard) pour Building
     */
    public static final int VEW_RAY_BUILDING= 5;


    //_____________________UNIT______________________//

    /**
     * distance unitaire d’un deplacement en metres.
     */
    public static final double DISTANCE_TO_MOVE = 0.5;

    /**
     * Angle de Balayage en degrés.
     */
    public static final int ALPHA = 4;


    //__________________SOLDIER__________________//

    /**
     * Portee d’une attaque.
     */
    public static final double ATTACK_RANGE = 1.3;

    /**
     * Degats infliges par tour de jeu par une UM.
     */
    public static final double DAMAGE = 0.05;
    
    /**
     * Rayon de vision (pour la brouillard) pour soldier
     */
    public static final int VEW_RAY_SOLDIER= 2;


    //_________________SIMPLEUNIT_______________//

    /**
     * Portee d’un soin.
     */
    public static final double HEALING_RANGE = 1;


    /**
     * Portee pour la création d’une UM.
     */
    public static final double CREATION_RANGE = 1;
    
    /**
     * Rayon de vision (pour la brouillard) pour simple unit
     */
    public static final int VEW_RAY_SIMPLEUNIT= 1;


    //__________________IA____________________//

    /**
     * IA, Discount factor, attenuation.
     */
    public static final double IA_GAMMA = 0.9;

    /**
     * IA, Learning rate, taux d'apprentissage.
     */
    public static final double IA_ALPHA = 0.9;

    /**
     * Group distance max entre deux unites pour qu'elles soient considerees comme compactes.
     */
    public static final double Group_compactDim = 10;

    /**
     * Cinq coefficients employes dans le calcul de recompense et associes au nombre d'unites mortes,
     * aux dommages causes, aux dommages recus par le groupe d'unite et a une victoire ou a une defaite.
     * Tous ces coefficients sont positifs exepte celui lie a la defaite.
     */
    public static final double R_DEAD = 1;
    public static final double R_GIVEN_DAMAGES = 0.05;
    public static final double R_RECEIVED_DAMAGES = 0.05;
    public static final double R_VICTORY = 10;
    public static final double R_DEFEAT = -10;

    /**
     * taille des rayons de Zone (IA UNIT).
     */
    public static final double RAYON_ZONE_1 = 5;
    public static final double RAYON_ZONE_2 = 10;
    public static final double RAYON_ZONE_3 = 100;


    //_________________UI__________________//

    /**
     * Bordure qui sert de limite au scrolling.
     */
    public static final int SCROLL_BORDER = 100;

    /**
     * Taille de l'ecran
     */
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    public static final int screenHeight = screenSize.height;
    public static final int screenWidth = screenSize.width;

    /**
     * Position du building humain
     */
    public static final int BASE_LOCATION_X = 10;
    public static final int BASE_LOCATION_Y = 10;
    public static final Point2D.Double BASE_LOCATION = new Point2D.Double(BASE_LOCATION_X, BASE_LOCATION_Y);

    /**
     * Vitesse du scrolling (deplacement de la camera)
     */
    public static final int CAMERA_SPEED = 3;


    //____________OPTIONS_____________//

    public static Color colorPlayer = Color.green;
    public static Color colorIA = Color.red;
    public static String namePlayer = "The Human";

}
