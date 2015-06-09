import java.util.LinkedList;


public class SimpleUnitGroup extends UnitGroup {

    //_____________ATTRIBUTS____________//

    static LinkedList<SimpleUnitGroup> list = new LinkedList<SimpleUnitGroup>();
    IASimpleUnit ia;


    //__________________CONSTRUCTEURS__________________//

    /**
     * Constructeur.
     * @param us unite simple
     * @param isIA true si appartient a l'IA
     */
    public SimpleUnitGroup(SimpleUnit us, boolean isIA) {
        super(us);
        if (us.owner == Game.computer && isIA) {
            ia = new IASimpleUnit(this);
            list.add(this);

        }
    }

    /**
     * Constructeur.
     * @param grpUs groupe de US
     */
    @SuppressWarnings("unused")
    private SimpleUnitGroup(LinkedList<SimpleUnit> grpUs) {
        super(grpUs);
    }

    /**
     * Constructeur.
     * @param u tableau d'unites
     */
    public SimpleUnitGroup(SimpleUnit[] u) {
        this(u[0], false);
        for (int i = 1; i < u.length; i++) {
            if (u[i].owner == owner)
                add(u[i]);
        }
    }

    /**
     * Constructeur.
     * @param grpUs groupe de US
     * @param o joueur
     */
    public SimpleUnitGroup(LinkedList<SimpleUnit> grpUs, Player o) {
        super(grpUs, o);
        if (o == IA.computer) {
            list.add(this);
            ia = new IASimpleUnit(this);
        }
    }


    //_________________METHODES___________________//

    /**
     * Ajoute un groupe.
     * @param sg
     */
    public void add(SimpleUnitGroup sg) {
        if (sg.owner == owner) {
            if (owner == IA.computer) {
                this.group.addAll(sg.getGroup());
                list.remove(sg);
            } else
                this.group.addAll(sg.getGroup());
        }
    }

    /**
     * Ajoute une unite simple.
     * @param us
     */
    public void add(SimpleUnit us) {
        if (us.owner == owner) {
            if (owner == IA.computer)
                this.group.add(us);

            else
                this.group.add(us);
        }

    }

    /**
     * @return tableau d'unite simple
     */
    SimpleUnit[] toSimpleUnit() {
        SimpleUnit[] tab = new SimpleUnit[size()];
        int c = 0;
        for (Unit i : group) {

            tab[c] = (SimpleUnit) i;
            c++;
        }
        return tab;
    }

    /**
     * @return oui si en ciblage
     */
    boolean isOnTarget() {
        for (Unit u : group) {
            if (!u.isOnTarget())
                return false;
        }
        return true;
    }

    //______________METHODES_NOT_USED_________________//

    private void densePartOfListe(LinkedList<SimpleUnit> copactGrp, LinkedList<SimpleUnit> rest, SimpleUnit s) {
        rest.remove(s);
        copactGrp.add(s);
        // modifier le if
        if (rest.size() != 0) {
            int restSize = rest.size();
            for (int i = restSize - 1; i >= 0; i--) {
                if (s.distanceTo(rest.get(i)) < compactdim)
                    densePartOfListe(copactGrp, rest, rest.get(i));
            }
        }


    }

    public LinkedList<SimpleUnitGroup> divideInDenseGroups() {
        LinkedList<SimpleUnitGroup> toReturn = new LinkedList<SimpleUnitGroup>();
        toReturn.add(this);
        while (toReturn.getLast().group.size() != 0) {
            SimpleUnitGroup toAdd = toReturn.getLast().densePart();
            toReturn.add(toAdd);
        }
        toReturn.remove(toReturn.size() - 1);
        return toReturn;
    }

    // quel est l’objectif (précis) de cette méthode
    // faire deux méthodes détachées (getDensePart part et densePart)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public SimpleUnitGroup densePart() {
        LinkedList<SimpleUnit> copactGrp = new LinkedList<SimpleUnit>();
        LinkedList<SimpleUnit> rest = new LinkedList(group);
        densePartOfListe(copactGrp, rest, rest.get(0));

        group.clear();
        group.addAll(copactGrp);
        return new SimpleUnitGroup(rest, owner);
    }

}
