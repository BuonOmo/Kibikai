import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.LinkedList;

public class Mouse extends Listeners implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    static boolean leftPressed, dragging;
    static Point2D draggBeginning;
    static Rectangle2D draggingSquare;
    public Mouse(Player player) {
        super(player);
        leftPressed = false;
        dragging = false;
        draggBeginning = new Point2D.Double();
        draggingSquare = new Rectangle2D.Double();
        }
    
    //_____________MÉTHODES____________//
    
    /**
     * Méthode appelée au premier tour de jeu afin de récuperer les bordures.
     * @param e premier click reçu
     */
    public static void firstClick(MouseEvent e){
        init = false;
        border.setLocation((double) (MouseInfo.getPointerInfo().getLocation().getX() - e.getX())/scale, 
                           (double) (MouseInfo.getPointerInfo().getLocation().getY() - e.getY())/scale);
    }
    
    
    /**
     * Méthode appelée sur un clic droit.
     */
    private void rightClick(){
        createSoldier();
        setTarget();
    }
    
    /**
     * Méthode appelée sur un clic gauche.
     */
    private void leftClick(){
        //if (!shiftPressed){
        //    unSelectAll();
        //}
        getItemFromOwner();
        if (canSelect != null)
            select(canSelect);
        
    }
    
    public void createSoldier(){
        if (selected.size() >= 3){
            LinkedList<SimpleUnit> simpleUnitSelected = new LinkedList<SimpleUnit>();
            for (Item i : selected.getGroup()){
                if (i.getClass().getName() == "SimpleUnit"){
                    simpleUnitSelected.add((SimpleUnit)i);
                }
            }
            if (simpleUnitSelected.size() >=3){
                
                SimpleUnit.createSoldier(SimpleUnit.getNClosestSimpleUnitsFromList(3, mouse(), simpleUnitSelected),
                                         mouse());
                
            }
        }
        /*
        LinkedList<SimpleUnit> simpleUnitSelected = new LinkedList<SimpleUnit>();
        
        while (selected.size() >= 3 ){
            
            for (Item i : selected.group){
                if (i.getClass().getName() == "SimpleUnit"){
                    simpleUnitSelected.add((SimpleUnit)i);
                }
            }
            System.out.println(simpleUnitSelected.size());
            
            if (simpleUnitSelected.size() < 3)
                break;
            
            SimpleUnit.createSoldier(SimpleUnit.getNClosestSimpleUnitsFromList(3, mouse(), simpleUnitSelected),
                                     mouse());
            
            simpleUnitSelected.clear();
            
            
        }
        */
    }
    //_______________ÉCOUTEURS____________//

    @Override
    public void mouseClicked(MouseEvent e) {
        
        
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
    public void mousePressed(MouseEvent e) {
        
        if (init)
            firstClick(e);
        
        switch(e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                dragging = true;
                draggBeginning.setLocation(mouse());
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        dragging = false;
        draggBeginning.setLocation(0, 0);
        draggingSquare.setFrame(0, 0, 0, 0);
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
    public void mouseDragged(MouseEvent e) {
        
        if(dragging){
            draggingSquare.setRect((draggBeginning.getX() < mouse().getX()) ? draggBeginning.getX() : mouse().getX(),
                                   (draggBeginning.getY() < mouse().getY()) ? draggBeginning.getY() : mouse().getY(),
                                   Math.abs(draggBeginning.getX() - mouse().getX()),
                                   Math.abs(draggBeginning.getY() - mouse().getY()));
            selectOnly(draggingSquare);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Implement this method
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // TODO Implement this method
    }
    
}
