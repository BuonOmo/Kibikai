import java.util.LinkedList;

public abstract class IAUnite  {
    private UnitGroup unitGroup;
    public LinkedList<Soldier> soldierPlyaerInZone3;
    public LinkedList<Soldier> soldierPlyaerInZone2;
    public LinkedList<Soldier> soldierPlyaerInZone1;
    public LinkedList<Soldier> soldierComputerInZone3;
    public LinkedList<Soldier> soldierComputerInZone2;
    public LinkedList<Soldier> soldierComputerInZone1;
    public LinkedList<SimpleUnit> simpleUnitPlyaerInZone3;
    public double R3; //ajouteé a finals//
    public double R2; //ajouteé a finals//
    public double R1; //ajouteé a finals//
    public IAUnite() {
        soldierPlyaerInZone3= new LinkedList<Soldier>();
        soldierPlyaerInZone2= new LinkedList<Soldier>();
        soldierPlyaerInZone1= new LinkedList<Soldier>();
        soldierComputerInZone3= new LinkedList<Soldier>();
        soldierComputerInZone2= new LinkedList<Soldier>();
        soldierComputerInZone1= new LinkedList<Soldier>();
        simpleUnitPlyaerInZone3 = new LinkedList<SimpleUnit>();
    }
    public void setAll(UnitGroup unitGroupToSet){
        unitGroup =unitGroupToSet;
    }
    public void execut(){
        updateZone ();
        applyStrategy (chooseStrategy(calculateStaite()));
    }
    public abstract int calculateStaite ();
    public abstract int chooseStrategy (int staite);
    public abstract void applyStrategy (int strategy);
    public void updateZone (){
        simpleUnitPlyaerInZone3.clear();
        for (int i = 0; i< IA.player.units.size();i++){
            if (IA.player.units.get(i).getClass().getName()=="SimpleUnit"){
                if (unitGroup.distanceTo(IA.player.units.get(i))<R3) simpleUnitPlyaerInZone3.add((SimpleUnit)IA.player.units.get(i));
            }
        }
        soldierPlyaerInZone3.clear();
        for (int i = 0; i< IA.player.units.size();i++){
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
        for (int i = 0; i< soldierPlyaerInZone2.size();i++){
            if (soldierPlyaerInZone2.get(i).getClass().getName()=="Soldier"){
                for (int j = 0; j<unitGroup.groupUnits.size(); j++ ){
                    if (unitGroup.groupUnits.get(j).distanceTo(soldierPlyaerInZone1.get(i))<R1) { 
                        soldierPlyaerInZone1.add(soldierPlyaerInZone2.get(i));
                        break;
                    }
                }
            }
        }
        soldierComputerInZone3.clear();
        for (int i = 0; i< IA.computer.units.size();i++){
            if (IA.computer.units.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(IA.computer.units.get(i))<R3) soldierComputerInZone3.add((Soldier)IA.computer.units.get(i));
            }
        }
        soldierComputerInZone2.clear();
        for (int i = 0; i< soldierComputerInZone3.size();i++){
            if (soldierComputerInZone3.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(soldierComputerInZone3.get(i))<R2) soldierComputerInZone2.add(soldierComputerInZone3.get(i));
            }
        }
        soldierComputerInZone1.clear();
        for (int i = 0; i< soldierComputerInZone2.size();i++){
            if (soldierComputerInZone2.get(i).getClass().getName()=="Soldier"){
                for (int j = 0; j<unitGroup.groupUnits.size(); j++ ){
                    if (unitGroup.groupUnits.get(j).distanceTo(soldierComputerInZone1.get(i))<R1) { 
                        soldierComputerInZone1.add(soldierComputerInZone2.get(i));
                        break;
                    }
                }
            }
        }
    }
}
