import java.util.LinkedList;

public abstract class IAUnite {

    private int nbQualSrtategy;
    protected UnitGroup unitGroup;
    public int presentStrategy;
    public int previousState;
    public int previousStrategy;
    public UnitGroup previousUnitGroup;
    public double previousLife;
    public double previousDamages;

    public LinkedList<Soldier> soldierPlayerInZone3;
    public LinkedList<Soldier> soldierPlayerInZone2;
    public LinkedList<Soldier> soldierPlayerInZone1;
    public LinkedList<Soldier> soldierComputerInZone3;
    public LinkedList<Soldier> soldierComputerInZone2;
    public LinkedList<Soldier> soldierComputerInZone1;
    public LinkedList<SimpleUnit> simpleUnitPlayerInZone3;

    public double R3 = Finals.RAYON_ZONE_3;
    public double R2 = Finals.RAYON_ZONE_2;
    public double R1 = Finals.RAYON_ZONE_1;

    public abstract int calculateState();

    public abstract int chooseStrategy(int State);


    //_____________CONSTRUCTOR_____________//

    /**
     * Constructeur.
     * @param unitGroupToSet Groupe des unites
     */
    @SuppressWarnings("unchecked")
    public IAUnite(UnitGroup unitGroupToSet) {
        unitGroup = unitGroupToSet;
        soldierPlayerInZone3 = new LinkedList<Soldier>();
        soldierPlayerInZone2 = new LinkedList<Soldier>();
        soldierPlayerInZone1 = new LinkedList<Soldier>();
        soldierComputerInZone3 = new LinkedList<Soldier>();
        soldierComputerInZone2 = new LinkedList<Soldier>();
        soldierComputerInZone1 = new LinkedList<Soldier>();
        simpleUnitPlayerInZone3 = new LinkedList<SimpleUnit>();

        //Pour comparaison avec tour N-1
        previousUnitGroup = (UnitGroup) unitGroup.clone();
        previousUnitGroup.group = (LinkedList<Unit>) unitGroup.group.clone();

        previousLife = unitGroup.getQuantityOfLife();
        previousDamages = unitGroup.getQuantityOfDamages();
    }

    /**
     * Methode appelee pour etablir les stratgies et gerer l'historique a chaque instant
     */
    public void execut() {
        updateZone();
        int state = calculateState();
        int Strategy;
        //si l'etat a change (etat different ou etat identique depuis longtemps)
        if (previousState != state || nbQualSrtategy > 30) {
            nbQualSrtategy = 0;
            //choix de la strategie
            Strategy = chooseStrategy(state);
            presentStrategy = Strategy;
            previousStrategy = Strategy;
            // ajout a l'historique de chaque unite
            createHisto(state, Strategy);
            upDateStrategy(Strategy);
        } else {
            //Permet d'appliquer les actions que necessitent les strategies durables dans le temps
            Strategy = previousStrategy + 10;
            nbQualSrtategy++;
        }

        // application de la strategie
        applyStrategy(Strategy);

        previousState = state;
    }

    /**
     * Applique la strategie a l'unite
     * @param strategy strategie a appliquer
     */
    public abstract void applyStrategy(int strategy);


    /**
     * Met a jour la strategie
     * @param strategy strategie a appliquer
     */
    public void upDateStrategy(int strategy) {
        for (Unit u : this.unitGroup.group)
            u.strategyincurs = strategy;
    }


    /**
     * Met a jour la zone
     */
    public void updateZone() {
        //update simpleUnitPlayerInZone3
        simpleUnitPlayerInZone3.clear();
        for (int i = 0; i < IA.player.simpleUnits.size(); i++) {
            if (IA.player.units.get(i).getClass().getName() == "SimpleUnit") {
                if (unitGroup.distanceTo(IA.player.units.get(i)) < R3)
                    simpleUnitPlayerInZone3.add((SimpleUnit) IA.player.units.get(i));
            }
        }


        //update soldierPlayerInZone3
        soldierPlayerInZone3.clear();
        for (int i = 0; i < IA.player.soldiers.size(); i++) {
            if (IA.player.units.get(i).getClass().getName() == "Soldier") {
                if (unitGroup.distanceTo(IA.player.units.get(i)) < R3)
                    soldierPlayerInZone3.add((Soldier) IA.player.units.get(i));
            }
        }


        //Update soldierPlayerInZone2
        soldierPlayerInZone2.clear();
        for (int i = 0; i < soldierPlayerInZone3.size(); i++) {
            if (soldierPlayerInZone3.get(i).getClass().getName() == "Soldier") {
                if (unitGroup.distanceTo(soldierPlayerInZone3.get(i)) < R2)
                    soldierPlayerInZone2.add(soldierPlayerInZone3.get(i));
            }
        }


        //Update soldierPlayerInZone1
        soldierPlayerInZone1.clear();
        for (Soldier s : soldierPlayerInZone1) {
            for (Unit u2 : unitGroup.group) {
                if (s.distanceTo(u2) < R1) {
                    soldierPlayerInZone1.add(s);
                    break;
                }
            }
        }


        // Update soldierComputerInZone3
        soldierComputerInZone3.clear();
        for (int i = 0; i < IA.computer.soldiers.size(); i++) {
            if (IA.computer.units.get(i).getClass().getName() == "Soldier") {
                if (unitGroup.distanceTo(IA.computer.units.get(i)) < R3)
                    soldierComputerInZone3.add((Soldier) IA.computer.units.get(i));
            }
        }


        // Update soldierComputerInZone2
        soldierComputerInZone2.clear();
        for (int i = 0; i < soldierComputerInZone3.size(); i++) {

            if (unitGroup.distanceTo(soldierComputerInZone3.get(i)) < R2)
                soldierComputerInZone2.add(soldierComputerInZone3.get(i));

        }

        //Update soldierComputerInZone1
        soldierComputerInZone1.clear();
        for (Soldier s : soldierComputerInZone2) {
            for (Unit u2 : unitGroup.group) {
                if (s.distanceTo(u2) < R1) {
                    soldierComputerInZone1.add(s);
                    break;
                }
            }
        }
    }


    /**
     * Cree l'historique en calculant les recompenses pour chaque strategie.
     * @param state etat de l'unite
     * @param strategy strategie appliquee
     */
    public void createHisto(int state, int strategy) {

        //calcul de la recompense en comparant previousUnitGroup et UnitGroup   	
        int deadUnits = this.previousUnitGroup.areDeadNow();
        double lifeVariation = this.previousUnitGroup.getQuantityOfLife() - previousLife;
        double damagesVariation = this.previousUnitGroup.getQuantityOfDamages() - previousDamages;
        double recompense =
            damagesVariation * Finals.R_GIVEN_DAMAGES + lifeVariation * Finals.R_RECEIVED_DAMAGES -
            deadUnits * Finals.R_DEAD;

        // Changer AUTRE_Joueur en le joueur adverse du possesseur du groupe d'unites
        if (IA.player.base.isDead())
            recompense = +Finals.R_VICTORY;

        if (IA.computer.base.isDead())
            recompense = +Finals.R_DEFEAT; //R_DEFEAT<0


        for (Unit unite : unitGroup.group) {
            //On modifie le parametre recompense du dernier IAHistObj
            if (!unite.histoList.isEmpty())
                unite.histoList.getLast().setReward(recompense);
            //On cree le nouveau IAHistObj pour l'etat actuel
            unite.histoList.add(new IAHistObj(state, strategy, UI.time));

        }
        //on nettoie et enregistre a nouveau l'unit group pour le calcul de recompense qui suivra
        this.previousUnitGroup.getGroup().clear();
        for (Unit u : unitGroup.group)
            previousUnitGroup.group.add(u);
        previousLife = this.previousUnitGroup.getQuantityOfLife();
        previousDamages = this.previousUnitGroup.getQuantityOfDamages();


    }

    public void createHisto() {
        createHisto(previousState, presentStrategy);
    }
}
