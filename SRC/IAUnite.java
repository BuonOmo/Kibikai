import java.util.LinkedList;

public abstract class IAUnite  {
    private UnitGroup unitGroup;
    public LinkedList<Unit> soldierInZone3;
    public LinkedList<Unit> soldierInZone2;
    public LinkedList<Unit> soldierInZone1;
    public double R3; //ajouteé a finals//
    public double R2; //ajouteé a finals//
    public double R1; //ajouteé a finals//
    public IAUnite() {
        soldierInZone3= new LinkedList<Unit>();
        soldierInZone2= new LinkedList<Unit>();
        soldierInZone1= new LinkedList<Unit>();
    }
    public void setAll(UnitGroup unitGroupToSet){
        unitGroup =unitGroupToSet;
    }
    public void execut(){
        applyStrategy (chooseStrategy(calculateStaite()));
    }
    public abstract int calculateStaite ();
    public abstract int chooseStrategy (int staite);
    public abstract void applyStrategy (int strategy);
    public void updateZone (){
        soldierInZone3.clear();
        for (int i = 0; i< IA.player.units.size();i++){
            if (IA.player.units.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(IA.player.units.get(i))<R3) soldierInZone3.add(IA.player.units.get(i));
            }
        }
        soldierInZone2.clear();
        for (int i = 0; i< soldierInZone3.size();i++){
            if (soldierInZone3.get(i).getClass().getName()=="Soldier"){
                if (unitGroup.distanceTo(soldierInZone3.get(i))) soldierInZone2.add(soldierInZone3.get(i));
            }
        }
        soldierInZone1.clear();
    }
}
