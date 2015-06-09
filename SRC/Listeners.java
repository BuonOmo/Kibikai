import java.awt.MouseInfo;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class Listeners implements Finals {
	static boolean shiftPressed, baseSelected, init, louHammel;
	static Item canSelect;
	static UnitGroup selected;
	static Player owner;
	static String typedKeys;

	/**
	 * Prend en compte la bordure de l’écran s'il y en a une.
	 */
	static Point2D border;

	//_____________CONSTRUCTEUR____________//

	/**
	 * Constructeur.
	 * @param p le joueur
	 */
	public Listeners(Player p) {
		owner = p;
		selected = new UnitGroup();
		typedKeys = "";
		init = true;
		border = new Point2D.Double();
	}

	//______________MÉTHODES______________//


	/**
	 * @return coordonnees de la souris
	 */
	public static Point2D mouse() {

		return new Point2D.Double((double) MouseInfo.getPointerInfo().getLocation().getX() / Camera.scale - border.getX(),
				(double) MouseInfo.getPointerInfo().getLocation().getY() / Camera.scale - border.getY());
	}

	/**
	 * @return coordonnees de la souris avec l'offset de la camera
	 */
	public static Point2D mouseWithCameraOffset(){
		return new Point2D.Double((double) MouseInfo.getPointerInfo().getLocation().getX() / Camera.scale - border.getX() + Camera.cameraX,
				(double) MouseInfo.getPointerInfo().getLocation().getY() / Camera.scale - border.getY() + Camera.cameraY);
	}

	/**
	 * Deselectionne tout.
	 */
	void unSelectAll() {
		baseSelected = false;
		owner.base.setSelected(false);
		selected.setSelected(false);
		selected.clear();
	}

	/**
	 * Deselectionne un objet.
	 * @param i objet a deselectionner
	 */
	void unSelect(Item i) {
		i.setSelected(false);
		if (i.getClass().getName() == "Building") {
			baseSelected = false;
		} else {
			selected.remove((Unit) i);
		}
	}

	/**
	 * Selectionne un objet.
	 * @param i objet a selectionner
	 */
	void select(Item i) {
		if (i.selected) {
			unSelect(i);
			if (!shiftPressed)
				unSelectAll();
		} else {
			if (!shiftPressed) {
				unSelectAll();

				if (i != null && i.owner == owner) {
					i.setSelected(true);

					if (i.getClass().getName() == "Building") {
						baseSelected = true;
					} else {
						selected.add((Unit) i);
					}
				}
			}
		}
	}

	/**
	 * Selectionne un objet en deselectionnant tout les autres.
	 * @param i objets a selectionner
	 */
	void selectWithoutShift(Item i) {
		i.setSelected(true);
		if (i.getClass().getName() == "Building") {
			baseSelected = true;
		} else {
			selected.add((Unit) i);
		}
	}

	/**
	 * Selectionne uniquement un groupe.
	 * @param group groupe a selectionner
	 */
	void selectOnly(UnitGroup group) {
		unSelectAll();
		select(group);
	}

	/**
	 * Selectionne un groupe en plus.
	 * @param group groupe a selectionner
	 */
	void select(UnitGroup group) {
		for (Unit u : group.group) {
			if (u != null && u.owner == owner) {
				u.setSelected(true);
				selected.add(u);
			}
		}
	}

	/**
	 * Selectionne une liste.
	 * @param list liste a selectionner
	 */
	void select(LinkedList<Item> list) {
		for (Item i : list) {
			if (i != null && i.owner == owner) {
				i.setSelected(true);

				if (i.getClass().getName() == "Building") {
					baseSelected = true;
				} else {
					selected.add((Unit) i);
				}
			}
		}
	}

	/**
	 * Selectionne uniquement une liste.
	 * @param list liste a selectionner
	 */
	void selectOnly(LinkedList<Item> list) {
		unSelectAll();
		select(list);
	}

	/**
	 * Selectionne uniquement une liste.
	 * @param list liste a selectionner
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void selectOnly(ArrayList list) {
		unSelectAll();
		select(new LinkedList<Item>(list));
	}

	/**
	 * Selectionne les unites presentes dans un certain rectangle.
	 * @param r Rectangle de selection
	 */
	void select(Rectangle2D r) {
		for (Item i : owner.items) {
			if (r.contains(i.getCenter()))
				this.selectWithoutShift(i);
		}
	}

	/**
	 * Selectionne uniquement les unites presentes dans un certain rectangle.
	 * @param r Rectangle de selection
	 */
	void selectOnly(Rectangle2D r) {
		unSelectAll();
		select(r);
	}

	/**
	 * Recupere objet selon le proprietaire et le pointage de la souris
	 */
	void getItemFromOwner() {
		getItem(owner.items);
	}

	/**
	 * Recupere tous les objets vivants d'un proprietaire.
	 */
	void getItemFromAll() {
		getItem(Item.aliveItems);
	}

	/**
	 * Recupere un objet d'une liste.
	 * @param list
	 */
	void getItem(LinkedList<Item> list) {
		canSelect = null;

		for (Item i : list)
			if (i.hitbox.contains(mouseWithCameraOffset())) {
				canSelect = i;
				break;
			}
	}


	/**
	 * Assigne l'objectif vers un point donne par la souris
	 */
	void setTarget() {
		getItemFromAll();
		if (canSelect == null)
			setTarget(mouseWithCameraOffset());
		else{
			setTarget(canSelect);

			// cas particuliers pour faciliter le soin
			if (selected.size() == 1){
				if (selected.getGroup().getFirst().getClass().getName() == "Soldier" &&
						canSelect.getClass().getName() == "SimpleUnit" && 
						canSelect.hasSameOwner(selected.getGroup().getFirst()))
					canSelect.setTarget(selected.getGroup().getFirst());

				if (selected.isSimpleUnitGroup() && canSelect.getClass().getName() == "Soldier" && 
						canSelect.hasSameOwner(selected.getGroup().getFirst()) )
					canSelect.setTarget(selected.getGroup().getFirst());
			}
		}
	}

	/**
	 * Assigne l'objectif sur un objet
	 * @param i objet vise
	 */
	void setTarget(Item i) {
		selected.setTarget(i);
		if (baseSelected)
			owner.base.setTarget(i);
	}

	/**
	 * Assigne l'objectif
	 * @param p point vise
	 */
	void setTarget(Point2D p) {
		selected.setTarget(p);
		if (baseSelected)
			owner.base.setTarget(p);
	}



	/**
	 * Cheat codes associes a chaque nom
	 * @param i le numero de cheatcode associe a chaque action
	 */
	void cheat(int i) {
		switch (i) {
		case 0:
		{
			owner.base.getLife(1000);
			System.out.println("____Cadeau d’Ulysse____");
			break;
		}
		
		case 1:
		{
			System.out.println("____Adrien t’a ken sa mère_____");
			System.exit(0);
			break;
		}
		
		case 2:
		{
			louHammel = (louHammel) ? false : true;
			break;
		}
		case 3:
		{
			System.out.println("Charles n'a pas de cheatcode. Le pauvre.");
			break;
		}
		}
	}
}
