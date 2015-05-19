import java.awt.Graphics;
import java.awt.geom.Point2D;

import java.util.Iterator;
import java.util.ListIterator;

public class Game implements Finals {
    
    /**
     * méthode appelée à chaque "tour de jeu".
     */
    public static void run(){
        /*
        System.out.println("Game.run : nb de SU de comp "+IA.computer.simpleUnits.size());
        System.out.println("Game.run : nb de SU de play "+IA.player.simpleUnits.size());
        System.out.println("Game.run : nb de So de play "+IA.player.soldiers.size());
        System.out.println("Game.run : nb de U de play "+IA.player.units.size());        
        System.out.println("Game.run : nb de So de comp "+IA.computer.soldiers.size());
        System.out.println("Game.run : nb de U de comp "+IA.computer.units.size());  
        System.out.println("Game.run : nb nb itme en vie "+Item.aliveItems.size());
        */
        
        if (UI.time == 0)
            beginning();
            
        else if (Building.buildings.get(1).isDead() || Building.buildings.get(0).isDead())
            end();
        else
            middle();
    }
    
    public static void beginning(){
        
    }
    
    public static void middle(){
        for(int i=0; i< Item.aliveItems.size(); i++)
            Item.aliveItems.get(i).execute();
        IA.execut();
    }
    
    public static void end(){
        System.out.println("Game Over");
        System.exit(0);
    }
    
    public static void print(Graphics g){
        for (Item i : Item.aliveItems)
            i.print(g);
    }

}
