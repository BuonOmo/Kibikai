public interface Finals {
    /**
     * Taille du coté d’une unité simple en metres.
     */
    public static final double SIDE = 1;
    
    /**
     * Angle de Balayage en degrés.
     */
    public static final int ALPHA = 4;
    
    /**
     * Porté d’une attaque.
     */
    public static final double ATTACK_RANGE = 1.5;
    
    /**
     * Porté d’un soin.
     */
    public static final double HEALING_RANGE = 1;
    
    
    /**
     * 
     */
    public static final double CREATION_RANGE = 2;
    /**
     * Quantité de "vie" d’une unité simple en secondes d’attaque.
     */
    public static final double LIFE = 3;
    
    /**
     * Dégat fait par tour de jeu par une unité militaire
     */
    public static final double DAMAGE = 0.5;
    
    /**
     * Taille de la carte en metres.
     */
    public static final int WIDTH = 2000, HEIGTH = 1000;
    
    /**
     * distance unitaire d’un deplacement en metres;
     */
    public static final double DISTANCE_TO_MOVE = 1;
    
    /**
     * Echelle (distance en metre*scale = distance en pixel).
     */
    public static int scale = 15;
    
    /**
     * IA, Discount factor, aténuation. 
     */
     public static final double IA_GAMMA = 0.9;
        
     /**
      * IA, Learning rate, taux d'apparentissage.
      */
        
     public static final double IA_ALPHA = 0.9;
     
    /**
     * Group disance max entre deux unités pour qu'elles soient considérées comme compactes. 
     */
     public static final double Group_compactDim = 10;
     
     /**
      * Cinq coefficient employes dans le calcul de recompense et associes au nombre d'unites mortes, aux dommages causes, aux dommages recus par le groupe d'unite et a une victoire ou a une defaite.
      *Tous ces coefficients sont positifs exepte celui lie a la defaite.
      */
     public static final double R_DEAD = 5;
     public static final double R_GIVEN_DAMAGES = 5;
     public static final double R_RECEIVED_DAMAGES = 5;
     public static final double R_VICTORY = 10;
     public static final double R_DEFEAT = -10;
    
    /**
     * nombre de soldat max !!
     */
    public static final double Nb_max_of_Soldier = 50;
}
