import java.util.LinkedList;

public class SoldierGroup extends UnitGroup {

	//______________ATTRIBUTS__________________//

	public static LinkedList<SoldierGroup> list = new LinkedList<SoldierGroup>();
	IASoldier ia;

	//_______________CONSTRUCTEURS______________//

	/**
	 * Constructeur.
	 * @param soldier a ajouter dans la liste
	 */
	public SoldierGroup(Soldier soldier) {
		super(soldier);
		if (soldier != null) {

			if (soldier.owner == Game.computer) {
				list.add(this);
				ia = new IASoldier(this);
			}

		}
	}

	/**
	 * Constructeur.
	 * @param soldiers a ajouter dans la liste
	 */
	@SuppressWarnings("unused")
	private SoldierGroup(LinkedList<Soldier> soldiers) {
		super(soldiers);
	}

	/**
	 * Constructeur.
	 * @param soldiers a ajouter a la liste
	 * @param ownerToSet proprietaire de la liste
	 */
	public SoldierGroup(LinkedList<Soldier> soldiers, Player ownerToSet) {
		super(soldiers, ownerToSet);
		if (owner == IA.computer) {
			list.add(this);
			ia = new IASoldier(this);
		}
	}

	//_______________MÉTHODES______________//
	
	/**
	 * Divise en groupes denses
	 * @return liste de soldats
	 */
	public LinkedList<SoldierGroup> divideInDenseGroups() {
		LinkedList<SoldierGroup> toReturn = new LinkedList<SoldierGroup>();
		toReturn.add(this);
		while (toReturn.getLast().group.size() != 0) {
			SoldierGroup toAdd = toReturn.getLast().densePart();
			toReturn.add(toAdd);
		}
		toReturn.remove(toReturn.size() - 1);
		return toReturn;
	}

	/**
	 * Garde la partie dense du groupe
	 * @return partie non dense
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SoldierGroup densePart() {
		LinkedList<Soldier> copactGrp = new LinkedList<Soldier>();
		LinkedList<Soldier> rest = new LinkedList(group);
		densePartOfListe(copactGrp, rest, rest.get(0));
		group.clear();
		group.addAll(copactGrp);
		return new SoldierGroup(rest, owner);
	}

	/**
	 * Partie dense de la liste
	 * @param compactGrp groupe compact
	 * @param rest le reste de la liste
	 * @param s soldat
	 */
	private void densePartOfListe(LinkedList<Soldier> compactGrp, LinkedList<Soldier> rest, Soldier s) {
		rest.remove(s);
		compactGrp.add(s);
		if (rest.size() != 0) {
			int restSize = rest.size();
			for (int i = restSize - 1; i >= 0; i--) {
				if (s.distanceTo(rest.get(i)) < compactdim)
					densePartOfListe(compactGrp, rest, rest.get(i));
			}
		}
	}

	/**
	 * Voir si on peut ajouter un groupe d'unites 
	 * @param sg groupe d'unites
	 * @return si on peut
	 */
	public boolean add(UnitGroup sg) {
		String className = sg.getClass().getName();
		if (className == "SoldierGroup") {
			if (sg.owner == this.owner) {
				if (this.owner == IA.computer) {
					this.group.addAll(sg.getGroup());
					list.remove(sg);
				} else
					this.group.addAll(sg.getGroup());
			}
			return true;
		} else
			return false;
	}

	/**
	 * @return la quantite de dommages cumulee de toutes les unites du UnitGroup
	 */
	@Override
	public double getQuantityOfDamages() {
		double quantity = 0.0;
		//Si notation ci dessous compile c'est bon! Adrien a toi de voir. Ou alors mettre damage commun a toutes les unites, et egal a 0 a a creation.
		for (Unit s : this.getGroup()) {
			quantity += ((Soldier) s).damage;
		}
		return quantity;
	}
}
