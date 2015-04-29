import java.awt.geom.Point2D;

import java.util.Iterator;

public class Game implements Finals {
    
    /**
     * méthode appelée à chaque "tour de jeu".
     */
    public static void running(){
        //TODO ajouter les récuperation des Input
        for (Item element : Item.aliveItems)
            element.execute();
    }
    
    public static void test(){
        Item a = new SimpleUnit(new Player(null,null,null), new Point2D.Double());
        System.out.println(a.getClass());
    }

}
