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
import java.util.List;

public class Listeners implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Finals {
    boolean hasSelected, shiftPressed, baseSelected;
    Item canSelect;
    UnitGroup selected;
    Player owner;
    String typedKeys;
    //_____________CONSTRUCTEUR____________//
    
    public Listeners(Player p){
        owner = p;
        selected=new UnitGroup();
        typedKeys = "";
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
        System.out.println("Listeners.leftClicked :"+canSelect);
        if (canSelect == null) 
            unSelectAll();
        else
            select(canSelect);
        
    }
    
    private void unSelectAll(){
        baseSelected = false;
        selected.setSelected(false);
        hasSelected = false;
        selected.clear();
    }
    private void select(Item i){
        
        if (i != null && i.owner == owner){
            
            hasSelected=true;
            i.setSelected(true);
            
            if (i.getClass().getName() == "Building"){
                baseSelected = true;
            }
            else{
                selected.add((Unit)i);
            }
        }
    }
        
    private void getItemFromOwner(){
        getItem(owner.items);
    }
    
    private void getItemFromAll(){
        getItem(Item.aliveItems);
    }
    
    private void getItem(LinkedList<Item> list){
        canSelect = null;
        
        for (Item i : list)
            if (i.hitBox.contains(mouse()))
                canSelect = i;
    }
    
    
    private void setTarget(){
        getItemFromAll();
        if (canSelect == null)
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
    
    private void createSoldier (SimpleUnit[] t){
        t[0].createSoldier(t[1], t[2]);
    }
    
    private void createSoldier (SimpleUnit[] t, Point2D p){
        t[0].createSoldier(p, t[1], t[2]);
    }
    
    public void log(String met, String msg){
        System.out.println(getClass().getName()+"."+met+" : "+msg);
    }
    //_______________ÉCOUTEURS____________//
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        typedKeys+= e.getKeyChar();
        
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
        case ('c'):{
            createSoldier(SimpleUnit.getNClosestSimpleUnitsFromO(3, mouse(), owner), mouse());
            break;
        }
        case('d'):{
            setTarget();
            break;
        }
            
        default:{
            
        }
        }
        
        if (typedKeys.endsWith("ulysse"))
            cheat(0);
        if (typedKeys.endsWith("adrien"))
            cheat(1);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        // TODO Implement this method
        //System.out.println(e.getExtendedKeyCode());
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
    
    
    //_________POUR_ADRIEN________//
    
    private void cheat(int i){
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