import java.util.LinkedList;

public abstract class IAUnite  {
    protected UnitGroup unitGroup;
    public int previousStait;
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
    public double R3 = Finals.RAYON_ZONNE_3; //ajoute� a finals//
    public double R2= Finals.RAYON_ZONNE_2; //ajoute� a finals//
    public double R1= Finals.RAYON_ZONNE_1; //ajoute� a finals//
    public IAUnite(UnitGroup unitGroupToSet) {
        unitGroup =unitGroupToSet;  
        soldierPlyaerInZone3= new LinkedList<Soldier>();
        soldierPlyaerInZone2= new LinkedList<Soldier>();
        soldierPlyaerInZone1= new LinkedList<Soldier>();
        soldierComputerInZone3= new LinkedList<Soldier>();
        soldierComputerInZone2= new LinkedList<Soldier>();
        soldierComputerInZone1= new LinkedList<Soldier>();
        simpleUnitPlyaerInZone3 = new LinkedList<SimpleUnit>();
        //Pour comparaison avec tour N-1
    
        previousUnitGroup = (UnitGroup)unitGroup.clone();
        previousUnitGroup.group = (LinkedList<Unit>)unitGroup.group.clone();

        previousLife = unitGroup.getQuantityOfLife();
        previousDamages =unitGroup.getQuantityOfDamages();
    }
    public void execut(){
        updateZone ();
        int state =calculateStaite();
        int Strategy= chooseStrategy(state);
        applyStrategy (Strategy);
        createHisto(state,Strategy);
        previousStait = state;

    }
    public abstract int calculateStaite ();
    public abstract int chooseStrategy (int staite);
    public abstract void applyStrategy (int strategy);
    public void updateZone (){

        simpleUnitPlyaerInZone3.clear();
        for (int i = 0; i< IA.player.simpleUnits.size();i++){
            if (IA.player.units.get(i).getClass().getName()=="SimpleUnit"){
                if (unitGroup.distanceTo(IA.player.units.get(i))<R3) simpleUnitPlyaerInZone3.add((SimpleUnit)IA.player.units.get(i));
            }
        }
        soldierPlyaerInZone3.clear();
        for (int i = 0; i< IA.player.soldiers.size();i++){
            if (IA.player.units.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(IA.player.units.get(i))<R3) soldierPlyaerInZone3.add((Soldier)IA.player.units.get(i));
            }
        }
        soldierPlyaerInZone2.clear();
        for (int i = 0; i< soldierPlyaerInZone3.size();i++){
            if (soldierPlyaerInZone3.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(soldierPlyaerInZone3.get(i))<R2) soldierPlyaerInZone2.add(soldierPlyaerInZone3.get(i));
            }
        }
        soldierPlyaerInZone1.clear();
        for (Soldier s : soldierPlyaerInZone1){
                for(Unit u2 : unitGroup.group){
                    if (s.distanceTo(u2)<R1){
                        soldierPlyaerInZone1.add(s);
                        break;
                    }
                }
        }
        /*
        for (int i = 0; i< soldierPlyaerInZone2.size();i++){
            if (soldierPlyaerInZone2.get(i).getClass().getName()=="Soldier"){
                for (int j = 0; j<unitGroup.group.size(); j++ ){
                    if (unitGroup.group.get(j).distanceTo(soldierPlyaerInZone1.get(i))<R1) { 
                        soldierPlyaerInZone1.add(soldierPlyaerInZone2.get(i));
                        break;
                    }
                }
            }
        }
        */
        soldierComputerInZone3.clear();
        for (int i = 0; i< IA.computer.soldiers.size();i++){
            if (IA.computer.units.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(IA.computer.units.get(i))<R3) soldierComputerInZone3.add((Soldier)IA.computer.units.get(i));
            }
        }
        soldierComputerInZone2.clear();
        for (int i = 0; i< soldierComputerInZone3.size();i++){

                if (unitGroup.distanceTo(soldierComputerInZone3.get(i))<R2) soldierComputerInZone2.add(soldierComputerInZone3.get(i));

        }
        soldierComputerInZone1.clear();
        for (Soldier s : soldierComputerInZone2){
                for(Unit u2 : unitGroup.group){
                    if (s.distanceTo(u2)<R1){
                        soldierComputerInZone1.add(s);
                        break;
                    }
                }
        }
        /*for (int i = 0; i< soldierComputerInZone2.size();i++){
            if (soldierComputerInZone2.get(i).getClass().getName()=="Soldier"){
                for (int j = 0; j<unitGroup.group.size(); j++ ){
                    if (unitGroup.group.get(j).distanceTo(soldierComputerInZone1.get(i))<R1) { 
                        soldierComputerInZone1.add(soldierComputerInZone2.get(i));
                        break;
                    }
                }
            }
        }
        */
        //System.out.println("==> IAUnit.updateZone : nd soldier player in z3"+soldierPlyaerInZone3.size());
    }
    
    public void createHisto(int state, int strategy){

    	
    	//calcul de la recompense en comparant previousUnitGroup et UnitGroup   	
		int deadUnits = this.previousUnitGroup.areDeadNow();
		double lifeVariation = this.previousUnitGroup.getQuantityOfLife() - previousLife;
		double damagesVariation = this.previousUnitGroup.getQuantityOfDamages() - previousDamages;
		double recompense = damagesVariation*Finals.R_GIVEN_DAMAGES + lifeVariation*Finals.R_RECEIVED_DAMAGES - deadUnits*Finals.R_DEAD;
		
		// Changer AUTRE_Joueur en le joueur adverse du possesseur du groupe d'unites
		if( IA.player.base.isDead())recompense =+ Finals.R_VICTORY;
		
		if(IA.computer.base.isDead())recompense =+ Finals.R_DEFEAT;  //R_DEFEAT<0
		

            for (Unit unite:unitGroup.group ){
    		//On modifie le parametre recompense du dernier IAHistObj
    		if (!unite.histoList.isEmpty())unite.histoList.getLast().setReward(recompense);
    		//On cree le nouveau IAHistObj pour l'etat actuel
    		unite.histoList.add(new IAHistObj(state, strategy,UI.time));

    	}
    		//on nettoie et enregistre a nouvau l'unit group pour le calcul de recompense qui suivra
    		this.previousUnitGroup.getGroup().clear();
            for (Unit u :unitGroup.group) previousUnitGroup.group.add(u);
    		previousLife = this.previousUnitGroup.getQuantityOfLife();
    		previousDamages = this.previousUnitGroup.getQuantityOfDamages();

    	
    }


}
