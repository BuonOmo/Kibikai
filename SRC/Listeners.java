import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import java.util.LinkedList;

public class Listeners implements  Finals {
    static boolean shiftPressed, baseSelected, init;
    static Item canSelect;
    static UnitGroup selected;
    static Player owner;
    static String typedKeys;
    
    /**
     * prend en compte la bordure de l’écran si il y en a une.
     */
    static Point2D border;
    
    //_____________CONSTRUCTEUR____________//
    
    public Listeners(Player p){
        owner = p;
        selected=new UnitGroup();
        typedKeys = "";
        init = true;
        border = new Point2D.Double();
    }
    
    //______________MÉTHODES______________//


    /**
     * @return coordonnées de la souris
     */
    public Point2D mouse(){
        
        return new Point2D.Double((double)MouseInfo.getPointerInfo().getLocation().getX()/scale - border.getX(), 
                                  (double)MouseInfo.getPointerInfo().getLocation().getY()/scale - border.getY());
    }
    
    
    
    void unSelectAll(){
        baseSelected = false;
        owner.base.setSelected(false);
        selected.setSelected(false);
        selected.clear();
    }
    
    void unSelect(Item i){
        i.setSelected(false);
        if (i.getClass().getName() == "Building"){
            baseSelected = false;
        }
        else{
            selected.remove((Unit)i);
        }
    }
    
    void select(Item i){
        if (i.selected){
            unSelect(i);
            if (!shiftPressed)
                unSelectAll();
        }
        else{
            if (!shiftPressed){
                unSelectAll();
                
                if (i != null && i.owner == owner){
                    i.setSelected(true);
                    
                    if (i.getClass().getName() == "Building"){
                        baseSelected = true;
                    }
                    else{
                        selected.add((Unit)i);
                    }
                }
            }
        }
    }
    /*  _____________________________________________À Implementer
    void select (UnitGroup group){
        if (group.selected){
            unSelect(i);
            if (!shiftPressed)
                unSelectAll();
        }
        else{
            if (!shiftPressed){
                unSelectAll();
                
                if (i != null && i.owner == owner){
                    i.setSelected(true);
                    
                    if (i.getClass().getName() == "Building"){
                        baseSelected = true;
                    }
                    else{
                        selected.add((Unit)i);
                    }
                }
            }
        }
    }
    */
    void getItemFromOwner(){
        getItem(owner.items);
    }
    
    void getItemFromAll(){
        getItem(Item.aliveItems);
    }
    
    void getItem(LinkedList<Item> list){
        canSelect = null;
        
        for (Item i : list)
            if (i.hitBox.contains(mouse())){
                canSelect = i;
                break;
            }
    }
    
    
    void setTarget(){
        getItemFromAll();
        if (canSelect == null)
            setTarget(mouse());
        else
            setTarget(canSelect);
    }
    
    void setTarget(Item i){
        selected.setTarget(i);
        if (baseSelected)
            owner.base.setTarget(i);
    }
    
    void setTarget(Point2D p){
        selected.setTarget(p);
        if (baseSelected)
            owner.base.setTarget(p);
    }
    
   
    
    //_________POUR_ADRIEN________//
    
    void cheat(int i){
        switch(i){
            case 0 :{
                owner.base.getLife(1000);
                System.out.println("____Cadeau d’Ulysse____");
                break;
            }
            case 1 :{
                System.out.println("____Adrien t’as ken_____");
                System.exit(0);
                break;
            }
        }
    }
}