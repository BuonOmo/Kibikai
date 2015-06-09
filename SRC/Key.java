import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key extends Listeners implements KeyListener {

	static boolean upKey, downKey, leftKey, rightKey;

	public Key(Player player) {
		super(player);
	}

	//_______________MÉTHODES____________//

	/**
	 * Methode appelee quand une touche est relachee.
	 */
	public void releaseKey() {
		shiftPressed = false;
		upKey = false;
		downKey = false;
		leftKey = false;
		rightKey = false;
	}

	//_______________ÉCOUTEURS____________//

	/**
	 * Effectue une action selon la touche appuyee.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		typedKeys += e.getKeyChar();

		switch (e.getKeyChar()) {
		
		//Selectionne le batiment.
		case ('b'):
		{
			unSelectAll();
			select(owner.base);
			break;
		}
		
		//Cree un soldat avec les 3 unites simples les plus proches.
		case ('c'):
		{
			SimpleUnit.createSoldier(mouseWithCameraOffset(), owner);
			break;
		}

		//Choisit une cible pour les unites selectionnees.
		case ('d'):
		{
			setTarget();
			break;
		}
		
		//Choisit un nouveau point de ralliement.
		case ('p'):
		{
			owner.base.setTarget(mouseWithCameraOffset());
			break;
		}
		
		//Selectionne les soldats
		case ('s'):
		{
			selectOnly(owner.soldiers);
			break;
		}
		
		//Selection des unités simples
		case ('u'):
		{
			selectOnly(owner.simpleUnits);
			break;
		}
		
		default:
		}
		
		if (typedKeys.endsWith("ulysse"))
			cheat(0);
		if (typedKeys.endsWith("adrien"))
			cheat(1);
		if (typedKeys.endsWith("unicorn"))
			cheat(2);
		if (typedKeys.endsWith("charles"))
			cheat(3);
	}

	/**
	 * Change les boolean des fleches directionnelles si appuyees.
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()){
		case(16):{ // touche shift
			shiftPressed = true;
		}
		case (37):{ // clavier gauche
			leftKey = true;
			break;
		}
		case(39):{ // clavier droite
			rightKey = true;
			break;
		}
		case(38):{ // clavier haut
			upKey = true;
			break;
		}
		case(40):{ // clavier bas
			downKey = true;
			break;
		}
		}


	}

	/**
	 * Utilise la methode releaseKey() si la touche est relachee.
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		releaseKey();
	}

}
