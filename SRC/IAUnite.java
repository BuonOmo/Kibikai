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
    public LinkedList<Soldier> soldierPlyaerInZone3;
    public LinkedList<Soldier> soldierPlyaerInZone2;
    public LinkedList<Soldier> soldierPlyaerInZone1;
    public LinkedList<Soldier> soldierComputerInZone3;
    public LinkedList<Soldier> soldierComputerInZone2;
    public LinkedList<Soldier> soldierComputerInZone1;
    public LinkedList<SimpleUnit> simpleUnitPlyaerInZone3;
    public double R3 = Finals.RAYON_ZONE_3; //ajoute� a finals//
    public double R2 = Finals.RAYON_ZONE_2; //ajoute� a finals//
    public double R1 = Finals.RAYON_ZONE_1; //ajoute� a finals//

    public IAUnite(UnitGroup unitGroupToSet) {
        unitGroup = unitGroupToSet;
        soldierPlyaerInZone3 = new LinkedList<Soldier>();
        soldierPlyaerInZone2 = new LinkedList<Soldier>();
        soldierPlyaerInZone1 = new LinkedList<Soldier>();
        soldierComputerInZone3 = new LinkedList<Soldier>();
        soldierComputerInZone2 = new LinkedList<Soldier>();
        soldierComputerInZone1 = new LinkedList<Soldier>();
        simpleUnitPlyaerInZone3 = new LinkedList<SimpleUnit>();
        //Pour comparaison avec tour N-1

        previousUnitGroup = (UnitGroup) unitGroup.clone();
        previousUnitGroup.group = (LinkedList<Unit>) unitGroup.group.clone();

        previousLife = unitGroup.getQuantityOfLife();
        previousDamages = unitGroup.getQuantityOfDamages();
    }

    public void execut() {
        updateZone();
        int state = calculateState();
        int Strategy;
        //si l'�tat a changer (�tat� di�f�rant ou �tat idantique depuit longtemps)
        if (previousState != state || nbQualSrtategy >30) {
            nbQualSrtategy = 0;
            //chois de la stat�gie 
            Strategy = chooseStrategy(state);
            presentStrategy = Strategy;
            previousStrategy = Strategy;
            // ajout a l'historique de chaque unit�s
            createHisto(state, Strategy);
            updateyStrategy(Strategy);
        } else{
            // (Strategy = previousStrategy + 10) permet d'appliquer les acction que n�s�site les strat�gie durables dans le temps 
            Strategy = previousStrategy + 10;
            nbQualSrtategy++;
            }
        // application de la strat�gie 
        
        applyStrategy(Strategy);

        previousState = state;


    }

    public abstract int calculateState();

    public abstract int chooseStrategy(int State);

    public abstract void applyStrategy(int strategy);

    public void updateyStrategy(int strategy) {
        for (Unit u : this.unitGroup.group)
            u.strategyincurs = strategy;
    }


    public void updateZone() {
        //update simpleUnitPlyaerInZone3
        simpleUnitPlyaerInZone3.clear();
        for (int i = 0; i < IA.player.simpleUnits.size(); i++) {
            if (IA.player.units.get(i).getClass().getName() == "SimpleUnit") {
                if (unitGroup.distanceTo(IA.player.units.get(i)) < R3)
                    simpleUnitPlyaerInZone3.add((SimpleUnit) IA.player.units.get(i));
            }
        }
        
        
        //update soldierPlyaerInZone3
        soldierPlyaerInZone3.clear();
        for (int i = 0; i < IA.player.soldiers.size(); i++) {
            if (IA.player.units.get(i).getClass().getName() == "Soldier") {
                if (unitGroup.distanceTo(IA.player.units.get(i)) < R3)
                    soldierPlyaerInZone3.add((Soldier) IA.player.units.get(i));
            }
        }
        
        
        
        //Update soldierPlyaerInZone2
        soldierPlyaerInZone2.clear();
        for (int i = 0; i < soldierPlyaerInZone3.size(); i++) {
            if (soldierPlyaerInZone3.get(i).getClass().getName() == "Soldier") {
                if (unitGroup.distanceTo(soldierPlyaerInZone3.get(i)) < R2)
                    soldierPlyaerInZone2.add(soldierPlyaerInZone3.get(i));
            }
        }
        
        
        //Update soldierPlyaerInZone1
        soldierPlyaerInZone1.clear();
        for (Soldier s : soldierPlyaerInZone1) {
            for (Unit u2 : unitGroup.group) {
                if (s.distanceTo(u2) < R1) {
                    soldierPlyaerInZone1.add(s);
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
     * @param state
     * @param strategy
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
        //on nettoie et enregistre a nouvau l'unit group pour le calcul de recompense qui suivra
        this.previousUnitGroup.getGroup().clear();
        for (Unit u : unitGroup.group)
            previousUnitGroup.group.add(u);
        previousLife = this.previousUnitGroup.getQuantityOfLife();
        previousDamages = this.previousUnitGroup.getQuantityOfDamages();


    }
}
