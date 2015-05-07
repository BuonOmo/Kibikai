import java.util.LinkedList;

public class IASoldier extends IAUnite {
    public SoldierGroup soldierGroup ;
    public IASoldier(SoldierGroup soldierGroupToSet) {
        soldierGroup=soldierGroupToSet;
        super.setAll(soldierGroupToSet);
    }
    public int calculateStaite() {
        int state =0;
        /*
         * geurre zonne 1 (+0;1)
         */
        if (soldierPlyaerInZone1.size()==0) state= 1;
        else state=0;
        /*
         * superiorité numérique en zonne 2 (+0;2)
         */
        if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size());
        else state=2+state;
        /*
         * bat compiuter (+0;4)
         */
        for (Soldier i : IA.player.units){
            if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=4+state;
                break;
            }
        }
        /*
         * unité Simple plyer isoler de dans zonne 3
         */

        
        /*
         * bat player est attaquer ()
         */
        for (Soldier i : IA.computer.units){
            if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=8+state;
                break;
            }
        }
        
        
        /*
         * superiorité numérique (+casi égale ) en zonne 3 
         */
        if (soldierPlyaerInZone3.size()*0.8>soldierComputerInZone3.size())state= 2*3*1+state;
        else if (soldierPlyaerInZone3.size()*1.5>soldierComputerInZone3.size())state=2*3*2+state;
        else state = 2;//?
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
            break;
        case 2:
            /*
             * allï¿½ dï¿½fandre le qg alliï¿½ 
             */
            soldierGroup.setTarget(IA.computer.base.getCenter());
            break;
        case 3:
            /*
             * attaque timide 
             */
            if (soldierPlyaerInZone1.size()>soldierComputerInZone1.size()){
                /*
                 * ajouter soldierGroup au group alliï¿½ le plus proche 
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
                 * se rï¿½unire 
                 */
                soldierGroup.setTarget(soldierGroup.getPsosition());
                }
            }
            if (soldierPlyaerInZone1.size()== 0){
                if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size()) {
                    soldierGroup.setTarget(IA.computer.base.getCenter());
                }
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
                if (soldierPlyaerInZone2.size()== 0){
                    if (soldierPlyaerInZone2.size()== 0){
                        soldierGroup.setTarget(soldierGroup.getPsosition());
                    }
                    else {
                        SoldierGroup plyaerZone3 = new SoldierGroup(soldierPlyaerInZone3,IA.player);
                        LinkedList <SoldierGroup> plyaerZone3Groups = plyaerZone3.divideInDenseGroups();
                        /*
                         * on cherche le plus petit group de soldierPlyaerInZone3 
                         */
                        int size = plyaerZone3Groups.get(0).groupUnits.size();
                        SoldierGroup smolerSoldierGroup = plyaerZone3Groups.get(0);
                        for (int i =1 ; i > plyaerZone3Groups.size();i++){
                            if (plyaerZone3Groups.get(i).groupUnits.size()<size){
                                size = plyaerZone3Groups.get(i).groupUnits.size();
                                smolerSoldierGroup = plyaerZone3Groups.get(i);
                            }
                        }
                        if (smolerSoldierGroup.groupUnits.size()<soldierGroup.groupUnits.size()){
                            soldierGroup.setTarget(smolerSoldierGroup.getPsosition());
                        }
                        else{
                            if (soldierPlyaerInZone3.size()>soldierComputerInZone3.size()) {
                                soldierGroup.setTarget(IA.computer.base.getCenter());
                            }
                            else{
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
                        }
                        
                    }
                    }

                }
            
                break;
        case 4:

                break;
        case 5:

                break;
        case 6:

                break;
        default: 
        }
    }
}

