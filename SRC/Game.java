import java.awt.Graphics;
import java.awt.geom.Point2D;

import java.util.Iterator;

public class Game implements Finals {
    
    /**
     * méthode appelée à chaque "tour de jeu".
     */
    public static void run(){
        
        //TODO ajouter les récuperation des Input
        for (Item element : Item.aliveItems)
            element.execute();
    }
    
    public static void print(Graphics g){
        for (Item i : Item.aliveItems)
            i.print(g);
    }

}
