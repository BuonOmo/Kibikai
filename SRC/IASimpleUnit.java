import java.awt.geom.Point2D;

public class IASimpleUnit extends IAUnite {
    public IASoldier support;

    public IASimpleUnit(SimpleUnitGroup simpleUnitGroupToSet) {
        super(simpleUnitGroupToSet);
        support = null;
    }

    public int calculateStaite() {

        int staite = 0;
        if (support != null) {
            // strategie de groupes soutenue (0;5)
            staite = support.presentStrategy - 1;

            //superiorite numerique du groupe supporte dans la zone 1(0;6)
            if (support.soldierComputerInZone1.size() > support.soldierPlyaerInZone1.size())
                staite = staite + 6;

            //superiorite numerique du groupe supporte dans la zone 3(0;12)
            if (support.soldierComputerInZone3.size() > support.soldierPlyaerInZone3.size())
                staite = staite + 12;
        }


        //bat attaquer (0;24)
        for (Soldier i : IA.player.soldiers) {
            if (IA.computer.base.isCloseTo(i, Finals.ATTACK_RANGE)) {
                staite = 24 + staite;
                break;
            }
        }
        // pas de groupe supporte (0) et taille du groupe supportee  (48;96)
        if (support != null) {
            if (support.unitGroup.group.size() < 0.3 * Finals.NUMBER_MAX_OF_SOLDIER)
                staite = 48 + staite;
            else
                staite = 96 + staite;
        }
        //taille de this (0;144;288)
        if (unitGroup.numberUnit() < (double) Finals.NUMBER_MAX_OF_SIMPLEUNIT * 0.2)
            staite = 144 + staite;
        else if (unitGroup.numberUnit() < (double) Finals.NUMBER_MAX_OF_SIMPLEUNIT * 0.08)
            staite = 288 + staite;


        return staite;
    }

    public int chooseStrategy(int staite) {
        Double sommeR = 0.0;
        for (int i = 0; i < 5; i++) {
            sommeR = sommeR + Math.exp(IA.qIASimpleUnit[staite][i]);
        }
        Double Rdm = Math.random() * sommeR;
        for (int i = 0; i < 5; i++) {
            if (Rdm < Math.exp(IA.qIASimpleUnit[staite][i]))
                return i + 1;
            Rdm = Rdm - Math.exp(IA.qIASimpleUnit[staite][i]);
        }

        return 1;
    }

    public void applyStrategy(int strategy) {
        switch (strategy) {
        case 1:
            {
                /*
             * suivre le groupe supporeter
             */
                if (support == null)
                    unitGroup.setTarget(IA.computer.base.getCenter());
                else {
                    Point2D pts1 = support.unitGroup.getPosition();
                    Point2D pts2 = IA.computer.base.getCenter();
                    unitGroup.setTarget(new Point2D.Double(pts1.getX() +
                                                           (pts2.getX() - pts1.getX()) * 5 / pts1.distance(pts2),
                                                           pts1.getY() +
                                                           (pts2.getY() - pts1.getY()) * 5 / pts1.distance(pts2)));
                }
                break;
            }
            /*
         * Synder le group en deux
         */

        case 2:
            {
                SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit) unitGroup.group.get(0));
                unitGroup.remove(unitGroup.group.get(0));
                sicition.ia = new IASimpleUnit(sicition);
                for (int i = unitGroup.group.size() - 1; i > 0; i--) { // lesser le for sous se format la (risque d'emerde avec le remouve )
                    if ((i) % 2 == 0) {
                        sicition.add((SimpleUnit) unitGroup.group.get(i));
                        unitGroup.remove(unitGroup.group.get(i));
                    }
                }
                if (unitGroup.group.size() == 0)
                    SimpleUnitGroup.list.remove(unitGroup);
                break;
            }
        case 3:
            {
                /*
             * Soutenire un autre group d'unit�
             */

                double distence = 10000;
                SoldierGroup group = null;
                for (SoldierGroup sg : SoldierGroup.list) {

                    if (sg.distanceTo(unitGroup) < distence) {
                        group = sg;
                        distence = sg.distanceTo(unitGroup);
                    }
                }
                if (group != null) {
                    if (group.ia.support == null) {
                        SimpleUnitGroup sicition = new SimpleUnitGroup((SimpleUnit) unitGroup.group.get(0));
                        sicition.ia.support = group.ia;
                        group.ia.support = sicition.ia;
                    }
                    if (unitGroup.group.size() != 0)
                        for (int i = unitGroup.group.size() - 1; i > 0; i--) { // lesser le if sous se format la (risque d'emerde avec le remouve )
                            if ((i + 1) % 2 == 0) {
                                group.ia.support.unitGroup.group.add((SimpleUnit) unitGroup.group.get(i));
                                unitGroup.remove(unitGroup.group.get(i));
                            }
                        }
                }
                break;
            }
        case 4:
            {
                /*
                    * donn� de la vie
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
        case 5:
            {
                /*
                        * cr�� unit�s
                        */
                boolean quit = false;
                for (Unit u : unitGroup.group) {
                    if (quit)
                        break;
                    for (SimpleUnit Su : IA.computer.simpleUnits) {
                        if (quit)
                            break;
                        if (Su.strategyincurs == 5)
                            for (SimpleUnit Su2 : IA.computer.simpleUnits) {
                                if (Su.strategyincurs == 5) {
                                    SimpleUnit su = (SimpleUnit) u;
                                    su.createSoldier(Su, Su2);
                                    quit = true;
                                    break;
                                }
                            }
                    }
                }
                break;
            }
        case 11:
            {
                /*
             * Strat�gie 1 appliqu� au tours pr�c�dant
             * suivre le groupe supporeter
             */
                if (support == null)
                    unitGroup.setTarget(IA.computer.base.getCenter());
                else {
                    Point2D pts1 = support.unitGroup.getPosition();
                    Point2D pts2 = IA.computer.base.getCenter();
                    unitGroup.setTarget(new Point2D.Double(pts1.getX() +
                                                           (pts2.getX() - pts1.getX()) * 5 / pts1.distance(pts2),
                                                           pts1.getY() +
                                                           (pts2.getY() - pts1.getY()) * 5 / pts1.distance(pts2)));
                }
                break;

            }
        case 15:
            {
                /*
                        * cr�� unit�s
                        */
                for (Unit u : unitGroup.group) {

                    SimpleUnit su = (SimpleUnit) u;
                    su.setTarget(su.getCenter());
                    su.createSoldier();
                }
                break;
            }

        }
    }
}
