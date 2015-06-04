import java.awt.geom.Point2D;

public class IASimpleUnit extends IAUnite {
    public IASoldier support;
    public IASimpleUnit(SimpleUnitGroup simpleUnitGroupToSet) {
        super(simpleUnitGroupToSet);
        support = null;
    }
    public int calculateStaite() {
        
        int staite = 0;
        if (support!=null){
            // strategie de groupes soutenue (0;5)
            staite = support.presentStrategy-1;
            
            //superiorite numerique du groupe supporte dans la zone 1(0;6)
            if (support.soldierComputerInZone1.size()>support.soldierPlyaerInZone1.size()) staite=staite+6;
            
            //superiorite numerique du groupe supporte dans la zone 3(0;12)
            if (support.soldierComputerInZone3.size()>support.soldierPlyaerInZone3.size()) staite=staite+12;
        }
        
        
        //bat attaquer (0;24)
        for (Soldier i : IA.player.soldiers){
            if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                staite=24+staite;
                break;
            }
        }
        // pas de groupe supporte (0) et taille du groupe supportee  (48;96)
        if (support !=null){
            if (support.unitGroup.group.size()<0.3*Finals.NUMBER_MAX_OF_SOLDIER)staite=48+staite;
            else staite=96+staite;
        }
        //taille de this (0;144;288)
        if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.2)staite= 144+staite;
        else if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.08)staite= 288+staite;

        
        return staite;
    }

    public int chooseStrategy(int staite) {
        Double n = 0.0;
        
        for(Double i :IA.nbSaveSU[staite]) {
            n += i;
        }
        Double  sommeR=0.0;
        for (Double x :IA.qIASimpleUnit[staite]){
            sommeR = sommeR +Math.exp((n+1)/(101+n)*x);
        }
        Double Rdm= Math.random()*sommeR;
        int i =0;
        for (Double x :IA.qIASimpleUnit[staite]){
            i++;
            if (Rdm<Math.exp((n+1)/(101+n)*x)) return i;
            Rdm=Rdm-Math.exp((n+1)/(101+n)*x);
        }
        
        return 1;
    }

    public void applyStrategy(int strategy) {
        switch (strategy){
        case 1:{
            /*
             * suivre le groupe supporeter 
             */
            if (support==null) unitGroup.setTarget(IA.computer.base.getCenter());
            else{
                Point2D pts1 = support.unitGroup.getPosition();
                Point2D pts2 = IA.computer.base.getCenter();
                unitGroup.setTarget(new Point2D.Double(pts1.getX()+(pts2.getX()-pts1.getX())*5/pts1.distance(pts2),pts1.getY()+(pts2.getY()-pts1.getY())*5/pts1.distance(pts2)));
            }
            break;
        }
        /*
         * Synder le group en deux 
         */

        case 2:{
            SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0),true);
            unitGroup.remove(unitGroup.group.get(0));
            sicition.ia= new IASimpleUnit(sicition);
            for (int i= unitGroup.group.size()-1; i>0;i--){ // lesser le for sous se format la (risque d'emerde avec le remouve )
                if ((i)%2==0) {
                    sicition.add((SimpleUnit)unitGroup.group.get(i));
                    unitGroup.remove(unitGroup.group.get(i));
                } 
            }
            if (unitGroup.group.size()==0)SimpleUnitGroup.list.remove(unitGroup);
            break;
        }
        case 3:{
            /*
             * Soutenire un autre group d'unitï¿½ 
             */ 
                   
                   
            /*
             * on cherche le groupe de Soldat le plus proche
             */
            double distence =10000;
            SoldierGroup group= null;
            for( SoldierGroup sg :SoldierGroup.list){

                    if (sg.distanceTo(unitGroup)<distence){
                        group = sg;
                        distence =sg.distanceTo(unitGroup);
                    }
            }
            
            
            
            if (group!=null){// si il exsite un groupe de soldat proche 
                if (group.ia.support == null){// si le group de soldat n'as pas de soutien on crée un groupe de soutien dans la quelle on plasse une unité simple
                    SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0),true);
                    unitGroup.remove(unitGroup.group.get(0)); 
                    sicition.ia.support=group.ia;
                    group.ia.support=sicition.ia;
                }
                else  if (unitGroup.group.size()!=0)// si il rest au moin une unité aolrs on fait motier moiter avec les unité réstants.
                for (int i= unitGroup.group.size()-1; i>0;i--){ // lesser le if sous se format la (risque d'emerde avec le remouve )
                    if ((i+1)%2==0) {
                        group.ia.support.unitGroup.group.add((SimpleUnit)unitGroup.group.get(i));
                        unitGroup.remove(unitGroup.group.get(i));
                    }
                }
            }
            if (unitGroup.group.size()==0)SimpleUnitGroup.list.remove(unitGroup);
            break;
        }
        case 4:{   
                   /*
                    * donnï¿½ de la vie 
                    */
                   unitGroup.setTarget(IA.computer.base);
                   /*
                    * if (support == null) unitGroup.setTarget(IA.computer.base);
                   else{
                       for (Unit u : support.unitGroup.group){
                           if (3*Finals.LIFE-u.life>unitGroup.group.getLast().life){
                               unitGroup.group.getLast().setTarget(u);
                                unitGroup.group.removeLast();
                                if (unitGroup.group.size()==0)break;
                           }
                       }
                   }
                    */
                   
            break;
        }
            
            case 5:{ 
                       /*
                        * crï¿½ï¿½ unitï¿½s 
                        */
                       boolean quit = false;
                       for (Unit u : unitGroup.group){
                           if (quit) break;
                           for (SimpleUnit Su: Game.computer.simpleUnits){
                                if (quit) break;
                                if (Su.strategyincurs==5)
                                    for (SimpleUnit Su2: IA.computer.simpleUnits){
                                               if (Su.strategyincurs==5){
                                               SimpleUnit su = (SimpleUnit)u;
                                               su.createSoldier(Su,Su2);
                                               quit = true;
                                               break; 
                                            }
                                    }
                           }
                       }
                break;
                } 
        case 11: {           
            /*
             * Stratï¿½gie 1 appliquï¿½ au tours prï¿½cï¿½dant
             * suivre le groupe supporeter 
             */
            if (support==null) unitGroup.setTarget(IA.computer.base.getCenter());
            else{
                Point2D pts1 = support.unitGroup.getPosition();
                Point2D pts2 = Game.computer.base.getCenter();
                unitGroup.setTarget(new Point2D.Double(pts1.getX()+(pts2.getX()-pts1.getX())*5/pts1.distance(pts2),pts1.getY()+(pts2.getY()-pts1.getY())*5/pts1.distance(pts2)));
            }
            break;
            
        }
        case 15 : { 
                       /*
                        * crï¿½ï¿½ unitï¿½s 
                        */
                       boolean quit = false;
                       for (Unit u : unitGroup.group){
                           if (quit) break;
                           for (SimpleUnit Su: Game.computer.simpleUnits){
                                if (quit) break;
                                if (Su.strategyincurs==5||Su.strategyincurs==15)
                                    for (SimpleUnit Su2: IA.computer.simpleUnits){
                                               if (Su.strategyincurs==5){
                                               SimpleUnit su = (SimpleUnit)u;
                                               su.createSoldier(Su,Su2);
                                               quit = true;
                                               break; 
                                            }
                                    }
                           }
                       }
                       break;
            }          
        }
    }
}
