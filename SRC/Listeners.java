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
    boolean hasSelected, shiftPressed, baseSelected, init;
    Item canSelect;
    UnitGroup selected;
    Player owner;
    String typedKeys;
    
    /**
     * prend en compte la bordure de l’écran si il y en a une.
     */
    Point2D border;
    
    //_____________CONSTRUCTEUR____________//
    
    public Listeners(Player p){
        owner = p;
        selected=new UnitGroup();
        typedKeys = "";
        init = true;
        border = new Point2D.Double();
    }
    
    //______________MÉTHODES______________//
    
    public void firstClick(MouseEvent e){
        init = false;
        border.setLocation((double)(MouseInfo.getPointerInfo().getLocation().getX() - e.getX())/scale, 
                           (double)(MouseInfo.getPointerInfo().getLocation().getY() - e.getY())/scale);
    }
    
    public void releaseKey(){
        shiftPressed = false;
    }
    
    
    public Point2D mouse(){
        
        return new Point2D.Double((double)MouseInfo.getPointerInfo().getLocation().getX()/scale - border.getX(), 
                                  (double)MouseInfo.getPointerInfo().getLocation().getY()/scale - border.getY());
    }
    
    private void rightClick(){
        if (hasSelected){
            setTarget();
        }
    }
    
    private void leftClick(){
        if (!shiftPressed){
            unSelectAll();
        }
        getItemFromOwner();
        if (canSelect != null)
            select(canSelect);
        
    }
    
    private void unSelectAll(){
        baseSelected = false;
        owner.base.setSelected(false);
        selected.setSelected(false);
        hasSelected = false;
        selected.clear();
    }
    
    private void unSelect(Item i){
        
        hasSelected = false;
        i.setSelected(false);
        if (i.getClass().getName() == "Building"){
            baseSelected = false;
        }
        else{
            selected.remove((Unit)i);
        }
    }
    private void select(Item i){
        if (i.selected){
            unSelect(i);
        }
        else if (i != null && i.owner == owner){
            
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
            if (i.hitBox.contains(mouse())){
                canSelect = i;
                break;
            }
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
    
    //_______________ÉCOUTEURS____________//
    
    @Override
    public void keyTyped(KeyEvent e) {
        typedKeys+= e.getKeyChar();
        
        switch(e.getKeyChar()){
        
            /**
             * Selectionne le batiment.
             */
            case ('b'):{
                unSelectAll();
                select(owner.base);
                break;
            }
        
            /**
             * Créé un soldat avet les 3 unités simple les plus proches.
             */
            case ('c'):{
                createSoldier(SimpleUnit.getNClosestSimpleUnitsFromO(3, mouse(), owner), mouse());
                break;
            }
            
            /**
             * choisi une cible pour les unités selectionnées.
             */
            case('d'):{
                setTarget();
                break;
            }
            
            /**
             * Chosi un nouveau point de ralliement.
             */
            case('p'):{
                owner.base.setTarget(mouse());
                break;
            }
        
            /**
             * Selectionne les soldats
             */
            case ('s'):{
                unSelectAll();
                for (Soldier s : owner.soldiers)
                    select(s);
                break;
            }
            
            /**
             * Selection les unités simples
             */
            case ('u'):{
                unSelectAll();
                for (SimpleUnit s : owner.simpleUnits)
                    select(s);
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
        switch(e.getKeyCode()){
            case (KeyEvent.VK_SHIFT) :{
                shiftPressed = true;
                break;
            }
            case (KeyEvent.VK_CONTROL):{
                System.out.println("grill d’été");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // TODO Implement this method
        releaseKey();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (init)
            firstClick(e);
        
        switch(e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
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
    public void mouseMoved(MouseEvent e) {
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