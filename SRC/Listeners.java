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
    Point2D mouse;
    boolean hasSelected, shiftPressed, baseSelected;
    Item canSelect;
    UnitGroup selected;
    Player owner;
    //_____________CONSTRUCTEUR____________//
    
    public Listeners(Player p){
        owner = p;
        mouse = new Point2D.Double(20, 20);
        selected=new UnitGroup();
    }
    
    //______________MÉTHODES______________//
    
    public void releaseKey(){
        shiftPressed = false;
    }
    
    public void setMouseLocation(MouseEvent e){
        mouse.setLocation(new Point2D.Double (e.getX()/scale, e.getY()/scale));
    }
    
    private void rightClick(){
        if (hasSelected){
            getItem();
            if (canSelect != null)
                setTarget(canSelect);
            else
                setTarget(mouse);
        }
    }
    
    private void leftClick(){
        getItemFromOwner();
        System.out.println("Listeners.mouseClicked :"+canSelect);
        select(canSelect);
        
    }
    
    private void unSelectAll(){
        baseSelected = false;
        selected.setSelected(false);
        hasSelected = false;
    }
    private void select(Item i){
        if (i != null){
            if (!shiftPressed)
                unSelectAll();
            
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
        
        if (owner.base.hitBox.contains(mouse))
            canSelect = owner.base;
        for (Unit u : owner.units)
            if (u.hitBox.contains(mouse))
                canSelect = u;
    }
    
    private void getItem(){
        canSelect = null;
        
        for (Item i : Item.aliveItems)
            if (i.hitBox.contains(mouse))
                canSelect = i;
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
    public void keyTyped(KeyEvent keyEvent) {
        // TODO Implement this method
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
        setMouseLocation(mouseEvent);
        System.out.println("Listeners.mouseClicked :"+mouse);
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