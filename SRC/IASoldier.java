import java.util.LinkedList;

public class IASoldier extends IAUnite {

    public IASoldier(SoldierGroup soldierGroupToSet) {
       
        super(soldierGroupToSet);
    }
    public int calculateStaite() {
        int state =0;
        /*
         * geurre zonne 1 (+0;1)
         */
        if (soldierPlyaerInZone1.size()==0) state= 1;
        else state=0;
        /*
         * superiorit� num�rique en zonne 2 (+0;2)
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
         * unit� Simple plyer isoler de dans zonne 3
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
         * superiorit� num�rique (+casi �gale ) en zonne 3 
         */
        if (soldierPlyaerInZone3.size()*0.8>soldierComputerInZone3.size())state= 2*3*1+state;
        else if (soldierPlyaerInZone3.size()*1.5>soldierComputerInZone3.size())state=2*3*2+state;
        else state = 2;//?
        return 0;
    }

    public int chooseStrategy(int staite) {
        return 1;
    }

    public void applyStrategy(int strategy) {
        switch (strategy){
        case 1:
            /*
             * attaquer le qg addversaire
             */
            unitGroup.setTarget(IA.player.base.getCenter());
            break;
        case 2:
            /*
             * all� d�fandre le qg alli� 
             */
            unitGroup.setTarget(IA.computer.base.getCenter());
            break;
        case 3:
            /*
             * attaque timide 
             */
            if (soldierPlyaerInZone1.size()>soldierComputerInZone1.size()){
                /*
                 * ajouter soldierGroup au group alli� le plus proche 
                 */
                double distence =  SoldierGroup.list.get(0).distanceTo(unitGroup);
                SoldierGroup group = SoldierGroup.list.get(0);
                for(int i =1 ;i < SoldierGroup.list.size();i++){
                    if (distence >SoldierGroup.list.get(i).distanceTo(unitGroup)){
                        distence=SoldierGroup.list.get(i).distanceTo(unitGroup);
                        group = SoldierGroup.list.get(i);
                    }
                }
                group.add(unitGroup);//c'est la merde !!
            }
            else {
                if (soldierPlyaerInZone1.size()!= 0){
                /*
                 * se r�unire 
                 */
                unitGroup.setTarget(unitGroup.getPosition());
                }
            }
            if (soldierPlyaerInZone1.size()== 0){
                if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size()) {
                    unitGroup.setTarget(IA.computer.base.getCenter());
                }
                else{
                    if (soldierPlyaerInZone2.size()!= 0){
                        double distence =  soldierPlyaerInZone2.get(0).distanceTo(unitGroup.getPosition());
                        Soldier soldier =  soldierPlyaerInZone2.get(0);
                        for (int i = 1; i>soldierPlyaerInZone2.size();i++ ){
                            if (soldierPlyaerInZone2.get(i).distanceTo(unitGroup.getPosition())<distence){
                                distence = soldierPlyaerInZone2.get(i).distanceTo(unitGroup.getPosition());
                                soldier =soldierPlyaerInZone2.get(i);
                            }
                            
                        }
                    unitGroup.setTarget(soldier.getCenter());
                    }
                }
                if (soldierPlyaerInZone2.size()== 0){
                    if (soldierPlyaerInZone2.size()== 0){
                        unitGroup.setTarget(unitGroup.getPosition());
                    }
                    else {
                        SoldierGroup plyaerZone3 = new SoldierGroup(soldierPlyaerInZone3,IA.player);
                        LinkedList <SoldierGroup> plyaerZone3Groups = plyaerZone3.divideInDenseGroups();
                        /*
                         * on cherche le plus petit group de soldierPlyaerInZone3 
                         */
                        int size = plyaerZone3Groups.get(0).group.size();
                        SoldierGroup smolerSoldierGroup = plyaerZone3Groups.get(0);
                        for (int i =1 ; i > plyaerZone3Groups.size();i++){
                            if (plyaerZone3Groups.get(i).group.size()<size){
                                size = plyaerZone3Groups.get(i).group.size();
                                smolerSoldierGroup = plyaerZone3Groups.get(i);
                            }
                        }
                        if (smolerSoldierGroup.group.size()<unitGroup.group.size()){
                            unitGroup.setTarget(smolerSoldierGroup.getPosition());
                        }
                        else{
                            if (soldierPlyaerInZone3.size()>soldierComputerInZone3.size()) {
                                unitGroup.setTarget(IA.computer.base.getCenter());
                            }
                            else{
                                /*
                                 * ajouter soldierGroup au group alli� le plus proche 
                                 */
                                double distence =  SoldierGroup.list.get(0).distanceTo(unitGroup);
                                SoldierGroup group = SoldierGroup.list.get(0);
                                for(int i =1 ;i < SoldierGroup.list.size();i++){
                                    if (distence >SoldierGroup.list.get(i).distanceTo(unitGroup)){
                                        distence=SoldierGroup.list.get(i).distanceTo(unitGroup);
                                        group = SoldierGroup.list.get(i);
                                    }
                                }
                                group.add(unitGroup);//c'est la merde ici aussi !!
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

