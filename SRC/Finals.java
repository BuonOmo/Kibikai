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
     * Porté d’un soin.
     */
    public static final double HEALING_RANGE = 1;
    
    /**
     * Quantité de "vie" d’une unité simple en secondes d’attaque.
     */
    public static final double LIFE = 3;
    
    /**
     * Taille de la carte en metres.
     */
    public static final int WIDTH = 2000, HEIGTH = 1000;
    
    /**
     * distance unitaire d’un deplacement en metres;
     */
    public static final double DISTANCE_TO_MOVE = 0.1;
    
    /**
     * Echelle (distance en metre*scale = distance en pixel).
     */
    public static int scale = 5;
    
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
    
}
