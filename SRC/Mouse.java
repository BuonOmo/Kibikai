import java.awt.MouseInfo;
import java.awt.event.InputEvent;
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
    static int click = 0;

    //_____________CONSTRUCTEUR____________//

    /**
     * Constructeur.
     * @param player joueur
     */
    public Mouse(Player player) {
        super(player);
        leftPressed = false;
        dragging = false;
        draggBeginning = new Point2D.Double();
        draggingSquare = new Rectangle2D.Double();
    }


    //_____________MÉTHODES____________//

    /**
     * Methode appelee au premier tour de jeu afin de recuperer les bordures.
     * @param e premier click reçu
     */
    public static void firstClick(MouseEvent e) {
        init = false;
        border.setLocation((double) (MouseInfo.getPointerInfo().getLocation().getX() - e.getX()) / Camera.scale,
                           (double) (MouseInfo.getPointerInfo().getLocation().getY() - e.getY()) / Camera.scale);
    }


    /**
     * Methode appelee sur un clic droit.
     */
    private void rightClick() {

        if (selected.size() == 3) {
            createSoldier();
        } else {
            setTarget();
        }
    }

    /**
     * Methode appelee sur un clic gauche.
     */
    private void leftClick() {

        if (!shiftPressed) {
            unSelectAll();
        }
        getItemFromOwner();
        if (canSelect != null)
            select(canSelect);

    }

    /**
     * Rassemble 3 US pour creer un soldier.
     */
    public void createSoldier() {

        // cree parfois plus d’unités qu’il n’en faut (sauf dans le cas de 3 unités selectionnées)
        // mettre >= 3 pour avoir le cas général
        if (selected.size() == 3) {

            LinkedList<SimpleUnit> simpleUnitSelected = new LinkedList<SimpleUnit>();
            for (Item i : selected.getGroup())
                if (i.getClass().getName() == "SimpleUnit")
                    simpleUnitSelected.add((SimpleUnit) i);

            while (simpleUnitSelected.size() >= 3) {
                SimpleUnit[] getClosest;
                getClosest = SimpleUnit.getNClosestSimpleUnitsInL(3, mouseWithCameraOffset(), simpleUnitSelected);
                //System.out.println(getClosest.length);
                SimpleUnit.createSoldier(getClosest, mouseWithCameraOffset());

                for (SimpleUnit toRemove : getClosest)
                    simpleUnitSelected.remove(toRemove);
            }
        }
    }


    //_______________ÉCOUTEURS_____________//

    /**
     * Actions associees aux clics de la souris.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        switch (e.getModifiers()) {

        case InputEvent.BUTTON1_MASK:
            {
                leftClick();
                break;
            }

        case InputEvent.BUTTON3_MASK:
            {
                rightClick();
                break;
            }
        }
    }

    /**
     * Actions liees a l'appui d'un bouton de la souris.
     */
    @Override
    public void mousePressed(MouseEvent e) {

        if (init)
            firstClick(e);

        switch (e.getModifiers()) {
        case InputEvent.BUTTON1_MASK:
            {
                dragging = true;
                draggBeginning.setLocation(mouseWithCameraOffset());
                break;
            }
        }
    }

    /**
     * Actions liees au relachement d'un bouton de la souris.
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        dragging = false;
        draggBeginning.setLocation(0, 0);
        draggingSquare.setFrame(0, 0, 0, 0);
    }

    /**
     * Actions liees a l'entree de la souris dans un component.
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    /**
     * Actions liees a la sortie de la souris dans un component.
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    /**
     * Actions liees au drag de la souris
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        if (dragging) {
            draggingSquare.setRect((draggBeginning.getX() < mouseWithCameraOffset().getX()) ? draggBeginning.getX() :
                                   mouseWithCameraOffset().getX(),
                                   (draggBeginning.getY() < mouseWithCameraOffset().getY()) ? draggBeginning.getY() :
                                   mouseWithCameraOffset().getY(),
                                   Math.abs(draggBeginning.getX() - mouseWithCameraOffset().getX()),
                                   Math.abs(draggBeginning.getY() - mouseWithCameraOffset().getY()));
            selectOnly(draggingSquare);
        }
    }

    /**
     * Actions liees au mouvement de la souris.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Actions liees a de la souris.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Laisse la possibilite d'un mouvement d’echelle
    }
}
