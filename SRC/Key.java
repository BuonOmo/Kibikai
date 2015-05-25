import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key extends Listeners implements KeyListener {
    public Key(Player player) {
        super(player);
    }
    
    //_______________MÉTHODES____________//
    
    /**
     * Méthode appelée quand une touche est relachée
     */
    public void releaseKey(){
        shiftPressed = false;
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
                SimpleUnit.createSoldier(mouse(), owner);
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
                selectOnly(owner.soldiers);
                break;
            }
            
            /**
             * Selection les unités simples
             */
            case ('u'):{
                selectOnly(owner.simpleUnits);
                break;
                
            }
                
        default:{
            
        }
        }
        
        
        
        if (typedKeys.endsWith("ulysse"))
            cheat(0);
        if (typedKeys.endsWith("adrien"))
            cheat(1);
        if (typedKeys.endsWith("hammel"))
            cheat(2);
        if (typedKeys.endsWith("charles"))
            cheat(3);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch(e.getKeyChar()){
            case ('m'):{
                shiftPressed = true;
                break;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        
        releaseKey();
        
    }

}
