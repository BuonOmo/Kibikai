
import java.awt.geom.Point2D;

import java.util.LinkedList;

public class IASoldier extends IAUnite {

    public IASimpleUnit support = null;

    public IASoldier(SoldierGroup soldierGroupToSet) {
       
        super(soldierGroupToSet);
    }
    public int calculateState() {
        /*
         * Etat de la carte autour du groupe entre (0;432)
         */
        int state =0;
        /*
         * geurre zone 1 (+0;1)
         */
        if (soldierPlyaerInZone1.size()==0) state= 1;
        else state=0;
        /*
         * superiorite numerique en zone 2 (+0;2)
         */
        if (soldierPlyaerInZone2.size()>soldierComputerInZone2.size());
        else state=2+state;
        /*
         * bat computer est attaque (+0;4)
         */
        for (Soldier i : IA.player.soldiers){
            if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                state=4+state;
                break;
            }
        }
        /*
         * unite Simple player isoler de dans zone 3(+0;8)
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
                nbComputerSoldiersCloseToBat++;
            }
        }
        for (Soldier i : IA.player.soldiers){
            if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                nbPlayerSoldiersCloseToBat++;
            }
        }
        if (nbComputerSoldiersCloseToBat>0){
            if (nbPlayerSoldiersCloseToBat<nbComputerSoldiersCloseToBat)state=16+state;
            else state=32+state;
        }
        
        /*
         * superiorite numerique (ou casi egale ) en zone 3 (+0;48;96)
         */
        if (soldierPlyaerInZone3.size()*0.8>soldierComputerInZone3.size())state= 48+state;
        else if (soldierPlyaerInZone3.size()*1.5>soldierComputerInZone3.size())state=96+state;


        if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SOLDIER*0.2)state= 144+state;
        else if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SOLDIER*0.08)state= 288+state;
        return state;
    }

    public int chooseStrategy(int State) {
        
        /*
         * precotion pris puisqu'il y a ue des soucie a de depassement d'indice mais il dvrais etre appresent resolsuts 
         */
        if (State>431)  {
            return (int)Math.random()*6+1;
        }
        
        
        
        Double n = 0.0;
        // on calcule n , nombre de foi que State a ue lieu
        for(Double i :IA.nbSaveSol[State]) {
            n += i;
        }
        
        
        
        Double  sommeR=0.0;
        // on calcule sommeR , diviseur de la loi de probabliit� 
        for (Double x :IA.qIASoldier[State]){
            sommeR = sommeR +Math.exp((n+1)/(101+n)*x);
        }
        
        
        Double Rdm= Math.random()*sommeR;
        // on choisie la stat�gie avec une probablit� de exp((n+1)/(101+n)*x)/SommeR
        int i =0;
        for (Double x :IA.qIASoldier[State]){
            i++;
            if (Rdm<Math.exp((n+1)/(101+n)*x)) return i;
            Rdm=Rdm-Math.exp((n+1)/(101+n)*x);
        }
        
        
        //en cas d'�rreurs on apllique la strat�gie 1 
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
             * aller defandre le qg allie 
             */
            unitGroup.setTarget(IA.computer.base.getCenter());
            break;
        
        
        
        
        case 3:
            /*
             * attaque timide 
             */
            {
                Unit target = null; 
                Point2D cdm = unitGroup.getPosition();
                double distence1 = IA.player.base.distanceTo(cdm);
                for (Unit u : simpleUnitPlyaerInZone3){
                    boolean notIsolait = true;
                    if (u.distanceTo(cdm)<distence1){
                        for (Unit su : IA.player.units)
                            if (u.distanceTo(su)<Finals.RAYON_ZONE_2){
                                notIsolait = false;
                                break;
                            }
                        if (notIsolait){
                            distence1=u.distanceTo(cdm);
                            target = u;
                        }
                    }
                }
            if (target != null)
                unitGroup.setTarget(target);
            else{
               if (target == null)
                   unitGroup.setTarget(IA.player.base.getCenter());
               else 
                    unitGroup.setTarget(target);
            if (soldierPlyaerInZone1.size()>soldierComputerInZone1.size()){
                /*
                 * ajouter soldierGroup au group alli� le plus proche 
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
                    unitGroup.setTarget(soldier);
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
            }
            
                break;
            }
        
        
        
        case 4:{
                   /*
                    * attaque a tout pris !!
                    */
                    Unit target = null; 
                    Point2D cdm = unitGroup.getPosition();
                    double distence = IA.player.base.distanceTo(cdm);
                    LinkedList <Unit> unitPlyaerInZone3 = new LinkedList <Unit>();
                    unitPlyaerInZone3.addAll(this.simpleUnitPlyaerInZone3);
                    unitPlyaerInZone3.addAll(this.soldierPlyaerInZone3);
                   for (Unit u : unitPlyaerInZone3){
                       if (u.distanceTo(cdm)<distence){
                           distence=u.distanceTo(cdm);
                           target = u;
                       }
                   }
                   if (target == null)
                       unitGroup.setTarget(IA.player.base.getCenter());
                   else 
                       unitGroup.setTarget(target);
            break;
        }
        
        
        


        case 5:{
                /*
                 * rejoindre un autre group
                 */
            SoldierGroup allier =null;
            double distence = 10000000;
            for (SoldierGroup sg : SoldierGroup.list ){
                if ( unitGroup.distanceTo(sg)<distence & unitGroup!=sg){
                    distence = unitGroup.distanceTo(sg);
                    allier=sg;
                }
            }
            if (allier != null) {
                unitGroup.setTarget(allier.getPosition());
                allier.group.addAll(unitGroup.group);
                SoldierGroup.list.remove(unitGroup);
            }
            

                break;
        }
        
        
        
        case 6:{
                  /*
                   * synder le group; 
                   */
                   if (!unitGroup.group.isEmpty()) {
                       SoldierGroup newsg = new SoldierGroup((Soldier)unitGroup.group.getFirst());
                       unitGroup.group.removeFirst();
                   int i = 1;
                   for (Unit u :unitGroup.group){
                       if (i%2 ==0){
                           newsg.group.add(u);
                           unitGroup.group.remove(u);
                       }
                   }
                   if (unitGroup.group.size()==0)
                       SoldierGroup.list.remove(unitGroup);
                   }

                break;
        }
        default: 
        }
    }
}

