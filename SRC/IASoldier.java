import java.util.LinkedList;

public class IASoldier extends IAUnite {

    public IASoldier(SoldierGroup soldierGroupToSet) {
       
        super(soldierGroupToSet);
    }
    public int calculateStaite() {
        /*
         * état de la carte autoure du groupe entre (0;432)
         */
        int state =0;
        /*
         * geurre zonne 1 (+0;1)
         */
        if (soldierPlyaerInZone1.size()==0) state= 1;
        else state=0;
        /*
         * superioritï¿½ numï¿½rique en zonne 2 (+0;2)
         */
        if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size());
        else state=2+state;
        /*
         * bat compiuter  est attaquer (+0;4)
         */
        for (Soldier i : IA.player.soldiers){
            if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=4+state;
                break;
            }
        }
        /*
         * unite Simple plyer isoler de dans zonne 3(+0;8)
         */
        for (SimpleUnit i : simpleUnitPlyaerInZone3){
            boolean isIsolate = true;
            for (Soldier j : soldierPlyaerInZone3){
                if (!i.isCloseTo(j, Finals.ATTACK_RANGE*2))isIsolate=false;    
            }
            if (isIsolate){
                state=8+state;
                break;
            }
        }
        
        /*
         * bat player est attaquer (+ 0;16;32)
         */
        int nbComputerSoldiersCloseToBat =0;
        int nbPlayerSoldiersCloseToBat =0;
        for (Soldier i : IA.computer.soldiers){
            if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=8+state;
                nbComputerSoldiersCloseToBat++;
            }
        }
        for (Soldier i : IA.player.soldiers){
            if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=8+state;
                nbPlayerSoldiersCloseToBat++;
            }
        }
        if (nbPlayerSoldiersCloseToBat<nbComputerSoldiersCloseToBat)state=16+state;
        
        
        /*
         * superiorite numerique (oucasi egale ) en zonne 3 (+0;48;96)
         */
        if (soldierPlyaerInZone3.size()*0.8>soldierComputerInZone3.size())state= 48+state;
        else if (soldierPlyaerInZone3.size()*1.5>soldierComputerInZone3.size())state=96+state;


        if (unitGroup.numberUnit()<(double)Finals.Nb_max_of_Soldier*0.2)state= 144+state;
        else if (unitGroup.numberUnit()<(double)Finals.Nb_max_of_Soldier*0.08)state= 288+state;
        return state;
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
             * allï¿½ dï¿½fandre le qg alliï¿½ 
             */
            unitGroup.setTarget(IA.computer.base.getCenter());
            break;
        case 3:
            /*
             * attaque timide 
             */
            if (soldierPlyaerInZone1.size()>soldierComputerInZone1.size()){
                /*
                 * ajouter soldierGroup au group alliï¿½ le plus proche 
                 */
                double distence =  SoldierGroup.list.get(0).distanceTo(unitGroup);
                SoldierGroup group = SoldierGroup.list.get(0);
                for (SoldierGroup i : SoldierGroup.list ){
                    if (distence >i.distanceTo(unitGroup)){
                        distence=i.distanceTo(unitGroup);
                        group = i;
                    }
                }
                group.add(unitGroup);
            }
            else {
                if (soldierPlyaerInZone1.size()!= 0){
                /*
                 * se rï¿½unire 
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
                                 * ajouter soldierGroup au group alliï¿½ le plus proche 
                                 */
                                double distence =  SoldierGroup.list.get(0).distanceTo(unitGroup);
                                SoldierGroup group = SoldierGroup.list.get(0);
                                for(SoldierGroup i : SoldierGroup.list){
                                    if (distence >i.distanceTo(unitGroup)){
                                        distence=i.distanceTo(unitGroup);
                                        group = i;
                                    }
                                }
                                group.add(unitGroup);
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

