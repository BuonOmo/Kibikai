import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.JPanel;
public class Game implements Finals {
    
    //________________ATTRIBUTS_____________//
    static Player human, computer;
    
    
    /**
     * méthode appelée dans UI.
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
        
        if (UI.time == 0){
            //beginning();
        }
        else if (Building.gameOver())
            end();
        else
            middle();
    }
    
    public static void beginning(Player humanToSet, Player computerToSet){

        setHuman(humanToSet);
        setComputer(computerToSet);

    }
    
    public static void middle(){
        /*
        Iterator it = Item.aliveItems.iterator();
        Item current;
        for (Item i : Item.aliveItems)
            i.setUnDone();
        
        while (it.hasNext()){
            current = (Item)it.next();
           
            if (!current.done){
                
                current.setDone();
                
                if (current.execute())
                    it = Item.aliveItems.iterator();
                
            }
        }
        */
        
        for(int i=0; i< Item.aliveItems.size(); i++){
            Item.aliveItems.get(i).execute();
        }
        IA.execut();
/*
        if ((UI.time+1)%600==0){
            IA.end();
            System.out.println("sovgardeIA");
        }
*/

    }
    
    public static void end(){
        System.out.println("Game Over");
        IA.execut();
        IA.end();
        System.exit(0);
    }
    
    public static void print(Graphics g){
    	
        System.out.println("game print fonctionne");

        if (Mouse.dragging){
            g.setColor(new Color(0,255,255));
            g.drawRect((int) (Mouse.draggingSquare.getX() * scale),
                       (int) (Mouse.draggingSquare.getY() * scale), 
                       (int) (Mouse.draggingSquare.getWidth() * scale),
                       (int) (Mouse.draggingSquare.getHeight() * scale));
            g.setColor(new Color(0,255,255,30));
            g.fillRect((int) (Mouse.draggingSquare.getX() * scale),
                       (int) (Mouse.draggingSquare.getY() * scale), 
                       (int) (Mouse.draggingSquare.getWidth() * scale), 
                       (int) (Mouse.draggingSquare.getHeight()) * scale);
        }
            
        for (Item i : Item.aliveItems)
            i.print(g);
    }
    
    
    //______________ACCESSEURS__________//
    
    static Player getHuman(){
        return human;
    }
    
    static Player getComputer(){
        return computer;
    }
    
    
    //___________MUTATEURS___________//
    
    static void setHuman(Player p){
        human = p;
    }
    
    static void setComputer(Player p){
        computer = p;
    }

}