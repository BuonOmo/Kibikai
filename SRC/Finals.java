public interface Finals {
    /**
     * Taille du coté d’une unité simple en metres.
     */
    public final static double SIDE = 1;
    
    /**
     * Angle de Balayage en degrés.
     */
    public final static int ALPHA = 4;
    
    /**
     * Quantité de "vie" d’une unité simple en secondes d’attaque.
     */
    public final static double LIFE = 3;
    
    /**
     * Taille de la carte en metres.
     */
    public final static int WIDTH = 2000, HEIGTH = 1000;
    
    /**
     * distance unitaire d’un deplacement en metres;
     */
    public final static double DISTANCE_TO_MOVE = 0.1;
    
    /**
     * Echelle (distance en metre*scale = distance en pixel).
     */
    public static int scale = 5;
    /**
     * IA, Discount factor, at?snuation 
     */
     public final static double IA_GAMMA = 0.9;
        
     /**
      * IA, Learning rate, taut d'apparentissage
      */
        
     public final static double IA_ALPHA = 0.9;
     
    /**
     * Group disance max entre deux unti� pour qu'elle soit consid�rer comme compactes 
     */
     public final static double Group_compactDim = 10;
    
}
