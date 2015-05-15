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
            // strat�gie de groups soutenue (0;5)
            staite = support.presentStrategy-1;
            
            //superoriter num�rique du groupe suport� dans la zonne 1(0;6)
            if (support.soldierComputerInZone1.size()>support.soldierPlyaerInZone1.size()) staite=staite+6;
            
            //superoriter num�rique du groupe suport� dans la zonne 3(0;12)
            if (support.soldierComputerInZone3.size()>support.soldierPlyaerInZone3.size()) staite=staite+12;
        }
        
        
        //bat attaquer (0;24)
        for (Soldier i : IA.player.soldiers){
            if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
                staite=24+staite;
                break;
            }
        }
        // pas de group supporter (0) et taille du groupe suppoerter  (48;96)
        if (support !=null){
            if (support.unitGroup.group.size()<0.3*Finals.NUMBER_MAX_OF_SOLDIER)staite=48+staite;
            else staite=96+staite;
        }
        //taill de this (0;144;288
        if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.2)staite= 144+staite;
        else if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.08)staite= 288+staite;

        
        return staite;
    }

    public int chooseStrategy(int staite) {
        Double  sommeR=0.0;
        for (int i = 0; i<5;i++){
            sommeR = sommeR +Math.exp(IA.qIASimpleUnit[staite][i]);
        }
        Double Rdm= Math.random()*sommeR;
        for (int i = 0; i<5;i++){
            if (Rdm<Math.exp(IA.qIASimpleUnit[staite][i]))return i+1;
            Rdm=Rdm-Math.exp(IA.qIASimpleUnit[staite][i]);
        }
        
        return 0;
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
                unitGroup.setTarget(new Point2D.Double(pts1.getX()+(pts2.getX()-pts1.getX())*40/pts1.distance(pts2),pts1.getY()+(pts2.getY()-pts1.getY())*40/pts1.distance(pts2)));
            }
            break;
        }
        case 2:{
            SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0));
            sicition.ia= new IASimpleUnit(sicition);
            for (int i= unitGroup.group.size()-1; i>=0;i--){ // lesser le if sous se format la (risque d'emerde avec le remouve )
                if ((i+1)%2==0) {
                    unitGroup.remouve(unitGroup.group.get(i));
                    sicition.add((SimpleUnit)unitGroup.group.get(i));
                } 
            }
            break;
        }
        case 3:{
            SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0));
            sicition.ia= new IASimpleUnit(sicition);
            double distence =SoldierGroup.list.get(1).distanceTo(unitGroup);
            SoldierGroup group= new SoldierGroup(null);
            for( SoldierGroup sg :SoldierGroup.list){
                if (sg.distanceTo(unitGroup)<distence){
                    group = sg;
                    distence =sg.distanceTo(unitGroup);
                }
            }
            sicition.ia.support=group.ia;
            for (int i= unitGroup.group.size()-1; i>0;i--){ // lesser le if sous se format la (risque d'emerde avec le remouve )
                if ((i+1)%2==0) {
                    unitGroup.remouve(unitGroup.group.get(i));
                    sicition.add((SimpleUnit)unitGroup.group.get(i));
                }
            }
            break;
        }
        case 4:{
                   
            break;
        }
            
                
        }
    }
}
