import java.awt.Graphics;

import java.awt.geom.RectangularShape;

import java.util.LinkedList;

public class Obstacle {
    
    boolean canMove, canBePrinted;
    RectangularShape frame;
    Item associedItem;
    Player associedOwner;
    static LinkedList<Obstacle> all = new LinkedList<Obstacle>();
    
    public Obstacle(RectangularShape f, Item i, Player p, boolean move, boolean print){
        all.add(this);
        frame = f;
        canMove = move;
        canBePrinted = print;
        associedItem = i;
        associedOwner = p;
    }
    
    public Obstacle(RectangularShape frameToSet){
        this(frameToSet, null, null, false, false);
    }
    
    
    //___________MÉTHODES__________//
    
    void move(){
        
    }
    
    void execute(){
        if (canMove){
            move();
        }
    }
    
    void print(Graphics g){
        if(canBePrinted){
            /*switch(frame.getClass().getName()){
                case ("Rectangle2D"):{
                    break;
                }
                case ("Ellipse2D"):{
                    
                    break;
                }
                case ("Arc2D"):{
                    
                    break;
                }
            }
        */}
    }
}
