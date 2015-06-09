import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

public interface Finals {

    //________________parametres généraux_______________//

    /**
     * nombre de soldats max
     */
    public static final int NUMBER_MAX_OF_SOLDIER = 20;

    /**
     * nombre d'unites simples max
     */
    public static final int NUMBER_MAX_OF_SIMPLEUNIT = 20;
    

    //______________________terrain______________________//

    /**
     * Taille de la carte en metres.
     */
    public static final int WIDTH = 60, HEIGHT = 60;


    //__________________ITEM_________________//

    /**
     * Taille du cote d'une unite simple en metres.
     */
    public static final double SIDE = 1;

    /**
     * Quantite de vie unitaire en secondes d'attaque. (celle d’une US)
     */
    public static final double LIFE = 3;


    //___________________BUILDING_____________________//

    /**
     * Nombre d’US par secondes creees à l’origine par BA.
     */
    public static final double UNIT_PER_SECOND = 0.005;
    
    /**
     * 
     */
    public static final double CREATION_INCREMENT = 3;
    
    /**
     * 
     */
    public static final int CREATION_TIME = 200;
    
    /**
     * Rayon de vision (pour la brouillard) pour Building
     */
    public static final int VIEW_RAY_BUILDING= 5;


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
     * Portee d’une attaque
     */
    public static final double ATTACK_RANGE = 1.3;

    /**
     * Degats infliges par tour de jeu par un S
     */
    public static final double DAMAGE = 0.05;
    
    /**
     * Rayon de vision dans le brouillard pour soldier
     */
    public static final int VIEW_RAY_SOLDIER= 9;


    //_________________SIMPLEUNIT_______________//

    /**
     * Portee d’un soin.
     */
    public static final double HEALING_RANGE = 1;


    /**
     * Portee pour la création d’une UM.
     */
    public static final double CREATION_RANGE = 3;
    
    /**
     * Rayon de vision dans le brouillard pour simple unit
     */
    public static final int VIEW_RAY_SIMPLEUNIT= 3;


    //__________________IA____________________//

    /**
     * Attenuation des experiences de l'IA dans son comportement
     */
    public static final double IA_GAMMA = 0.9;

    /**
     * Distance max entre deux unites pour qu'elles soient considerees comme compactes.
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
    public static final int SCROLL_BORDER = 10;

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
    
    
    //_____________GRAPHISM____________//
    
    public static final Color BACKGROUND_COLOR = new Color(74, 72, 76);
    public static final Color FOG_COLOR = new Color(67, 64, 69);

    public static final Color BLUE = new Color(147, 162, 247);
    public static final Color ORANGE = new Color(255, 160, 90);
    public static final Color PINK = new Color(247, 147, 223);    
    public static final Color GREEN = new Color(182, 235, 135);
    
    
    //______________MUSIC_____________//
    
    public static final String[] SONGS = {"SONGS/Royksopp- What Else Is There.wav",
    									  "SONGS/Pretty Lights - Future Blind.wav",
    									  "SONGS/Daft Punk - Motherboard.wav"};
}
