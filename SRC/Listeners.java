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
    boolean lClick, rClick;
    Point2D mouse;
    //______________MÉTHODES______________//
    
    public static void setMouseLocation(){
        mouse.setLocation(MouseInfo.getPointerInfo().getLocation());
    }
    
    private void rightClick(){
        
    }
    
    private void leftClick(){
        
    }
    
    private Item getItem(LinkedList<Item> list, Point2D p){
        for (Item i : list){
            if (i.hitBox.contains(p))
                return i;
        }
        return null;
    }
    //_______________ÉCOUTEURS____________//
    
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // TODO Implement this method
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // TODO Implement this method
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // TODO Implement this method
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        setMouseLocation();

        switch(mouseEvent.getModifiers()) {
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
        if (leftClicked){
            for (Item element : Item.aliveItems){
                if (element.hitBox.contains(tX, tY)){
                    System.out.println("UI.CustomMouseListener.mouseClicked : "+element+" devrait être selectionné");
                    hasSelected = true;
                    element.setSelected(true);
                    selected = element;
                    break;
                }
            }
            if (hasSelected){
                for (Item element : Item.aliveItems){
                    if (element.hitBox.contains(tX, tY)){
                        element.setSelected(true);
                        break;
                    }
                }
                selected.setTarget(tX, tY);
            }
        }
        if (rightClicked){
            if (hasSelected){
                for (Item element : Item.aliveItems){
                    if (element.hitBox.contains(tX, tY)){
                        selected.setTarget(element);
                        break;
                    }
                }
                selected.setTarget(tX, tY);
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
