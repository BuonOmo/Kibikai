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
        
       //______________________terrain______________________//
           
            /**
             * Taille de la carte en metres.
             */
            public static final int WIDTH = 2000, HEIGTH = 1000;
            
            /**
             * Echelle (distance en metre*scale = distance en pixel).
             */
            public static int scale = 15;
    
    //__________________ITEM_________________//
        
        /**
         * Taille du coté d’une unité simple en metres.
         */
        public static final double SIDE = 1;

        /**
         * Quantité de vie unitaire en secondes d’attaque. (celle d’une US)
         */
        public static final double LIFE = 3;
        
        //___________________BUILDING_____________________//
        
            /**
             * Nombre d’US par secondes crées à l’origine par BA.
             */
            public static final double UNIT_PER_SECOND = 0.2;
        
        //______________________UNIT______________________//
           
            /**
             * distance unitaire d’un deplacement en metres.
             */
            public static final double DISTANCE_TO_MOVE = 1;
            
            @Deprecated
            /**
             * Angle de Balayage en degrés.
             */
            public static final int ALPHA = 4;
        
            //____________________SOLDIER________________//
                
                /**
                 * Porté d’une attaque.
                 */
                public static final double ATTACK_RANGE = 1.5;
                
                /**
                 * Dégat fait par tour de jeu par une UM.
                 */
                public static final double DAMAGE = 0.5;
            //_________________SIMPLEUNIT_______________//
                
                /**
                 * Porté d’un soin.
                 */
                public static final double HEALING_RANGE = 1;
                
                
                /**
                 * Porté pour la création d’une UM.
                 */
                public static final double CREATION_RANGE = 2;
        
     
     //______________ia__________________//
        
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
          * taille des des rayons de Zone (IA UNIT).
          */
         public static final double RAYON_ZONNE_1 = 5;
         public static final double RAYON_ZONNE_2 = 10;
         public static final double RAYON_ZONNE_3 = 100;
         
    //____________UI______________________//
         public static final int SCROLL_BORDER = 50;
    
}
