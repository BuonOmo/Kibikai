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
                //________________________________________________À Implementer
                //select(owner.soldiers);
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

}
