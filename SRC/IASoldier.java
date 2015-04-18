public class  IASoldier extends IAUnite {
    public SoldierGroup soldierGroup ;
    public IASoldier(SoldierGroup soldierGroupToSet) {
        soldierGroup=soldierGroupToSet;
        super.setAll(soldierGroupToSet);
    }
    public int calculateStaite() {
        return 0;
    }

    public int chooseStrategy(int staite) {
        return 0;
    }

    public void applyStrategy(int strategy) {
        switch (strategy){
        case 1:
            /*
             * attaquer le qg addversaire;
             */
            soldierGroup.setTarget(IA.player.base.getCenter());
        case 2:
            /*
             * allé défandre le qg allié ;
             */
            soldierGroup.setTarget(IA.computer.base.getCenter());
        case 3:
        case 4:
        case 5:
        case 6:
        default: 
        }
    }
}
