import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse extends Listeners implements MouseListener, MouseMotionListener, MouseWheelListener {
    public Mouse(Player player) {
        super(player);
    }
    
    //_____________MÉTHODES____________//
    
    /**
     * Méthode appelée au premier tour de jeu afin de récuperer les bordures.
     * @param e premier click reçu
     */
    public static void firstClick(MouseEvent e){
        init = false;
        border.setLocation((double)(MouseInfo.getPointerInfo().getLocation().getX() - e.getX())/scale, 
                           (double)(MouseInfo.getPointerInfo().getLocation().getY() - e.getY())/scale);
    }
    
    /**
     * Méthode appelée sur un clic droit.
     */
    private void rightClick(){
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
    //_______________ÉCOUTEURS____________//

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
    
}
