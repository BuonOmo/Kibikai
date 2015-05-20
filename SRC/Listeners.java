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

public class Listeners implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Finals {
    boolean hasSelected, shiftPressed, baseSelected;
    Item canSelect;
    UnitGroup selected;
    Player owner;
    char lastTypedKey;
    //_____________CONSTRUCTEUR____________//
    
    public Listeners(Player p){
        owner = p;
        selected=new UnitGroup();
    }
    
    //______________MÉTHODES______________//
    
    public void releaseKey(){
        shiftPressed = false;
    }
    
    public Point2D mouse(){
        return new Point2D.Double((double)MouseInfo.getPointerInfo().getLocation().getX()/scale, 
                                  (double)MouseInfo.getPointerInfo().getLocation().getY()/scale);
    }
    
    private void rightClick(){
        if (hasSelected){
            setTarget();
        }
    }
    
    private void leftClick(){
        getItemFromOwner();
        System.out.println("Listeners.mouseClicked :"+canSelect);
        select(canSelect);
        if (canSelect.isNull()){
            unSelectAll();
        }
        
    }
    
    private void unSelectAll(){
        baseSelected = false;
        selected.setSelected(false);
        hasSelected = false;
        selected.clear();
    }
    private void select(Item i){
        
        if (!i.isNull() && i.owner == owner){
            
            hasSelected=true;
            
            if (i.getClass().getName() == "Building"){
                owner.base.setSelected(true);
                baseSelected = true;
                
            }
            else{
                i.setSelected(true);
                selected.add((Unit)i);
            }
        }
    }
        
    private void getItemFromOwner(){
        canSelect = null;
        
        if (owner.base.hitBox.contains(mouse()))
            canSelect = owner.base;
        for (Unit u : owner.units)
            if (u.hitBox.contains(mouse()))
                canSelect = u;
    }
    
    private void getItem(){
        canSelect = null;
        
        for (Item i : Item.aliveItems)
            if (i.hitBox.contains(mouse()))
                canSelect = i;
    }
    
    
    private void setTarget(){
        getItem();
        if (canSelect.isNull())
            setTarget(mouse());
        else
            setTarget(canSelect);
    }
    private void setTarget(Item i){
        selected.setTarget(i);
        if (baseSelected)
            owner.base.setTarget(i);
    }
    
    private void setTarget(Point2D p){
        selected.setTarget(p);
        if (baseSelected)
            owner.base.setTarget(p);
    }
    
    
    public void log(String met, String msg){
        System.out.println(getClass().getName()+"."+met+" : "+msg);
    }
    //_______________ÉCOUTEURS____________//
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        switch(e.getKeyChar()){
        
        case ('s'):{
            unSelectAll();
            for (Soldier s : owner.soldiers)
                select(s);
            break;
        }
            
        case ('u'):{
            unSelectAll();
            for (SimpleUnit s : owner.simpleUnits)
                select(s);
            break;
            
        }
        case('c'):{
            setTarget();
        }
        }
        
        lastTypedKey = e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        // TODO Implement this method
        System.out.println(e.getExtendedKeyCode());
        switch(e.getKeyCode()){
            case (16) :{
                log("keyPressed","pressed");
                shiftPressed = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // TODO Implement this method
        releaseKey();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        
        System.out.println("Listeners.mouseClicked :"+mouse());
        switch(mouseEvent.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                log("mouseClicked","left");
                leftClick();
                break;
            }
            /**
             * Pour le bouton du milieu :
            case InputEvent.BUTTON2_MASK: {
                System.out.println("That's the MIDDLE button");     
                break;
            }
             */
            case InputEvent.BUTTON3_MASK: {
                log("mouseClicked","right");
                rightClick();
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // TODO Implement this method
    }
}