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
             * attaquer le qg addversaire
             */
            soldierGroup.setTarget(IA.player.base.getCenter());
        case 2:
            /*
             * allé défandre le qg allié 
             */
            soldierGroup.setTarget(IA.computer.base.getCenter());
        case 3:
            /*
             * attaque timide 
             */
            if (soldierPlyaerInZone1.size()>soldierComputerInZone1.size()){
                /*
                 * ajouter soldierGroup au group allié le plus proche 
                 */
                double distence =  SoldierGroup.groupSoldierList.get(0).distanceTo(soldierGroup);
                SoldierGroup group = SoldierGroup.groupSoldierList.get(0);
                for(int i =1 ;i < SoldierGroup.groupSoldierList.size();i++){
                    if (distence >SoldierGroup.groupSoldierList.get(i).distanceTo(soldierGroup)){
                        distence=SoldierGroup.groupSoldierList.get(i).distanceTo(soldierGroup);
                        group = SoldierGroup.groupSoldierList.get(i);
                    }
                }
                group.add(soldierGroup);
            }
            else {
                if (soldierPlyaerInZone1.size()!= 0){
                /*
                 * se réunire 
                 */
                soldierGroup.setTarget(soldierGroup.getPsosition());
                }
                else {
                    if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size())soldierGroup.setTarget(IA.computer.base.getCenter());
                    else{
                        if (soldierPlyaerInZone2.size()!= 0){
                            double distence =  soldierPlyaerInZone2.get(0).distanceTo(soldierGroup.getPsosition());
                            Soldier soldier =  soldierPlyaerInZone2.get(0);
                            for (int i = 1; i>soldierPlyaerInZone2.size();i++ ){
                                if (soldierPlyaerInZone2.get(i).distanceTo(soldierGroup.getPsosition())<distence){
                                    distence = soldierPlyaerInZone2.get(i).distanceTo(soldierGroup.getPsosition());
                                    soldier =soldierPlyaerInZone2.get(i);
                                }
                            
                            }
                        soldierGroup.setTarget(soldier.getCenter());
                        }
                    
                }
                    
            }
        case 4:
        case 5:
        case 6:
        default: 
        }
    }
}
