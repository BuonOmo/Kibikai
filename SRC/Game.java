import java.awt.geom.Point2D;

public class Game implements Finals {

    //________________ATTRIBUTS_____________//
    static Player human, computer;
    static UI ui;
    static boolean firstRun = true;


    /**
     * méthode appelée dans UI.
     */
    public static void run() {
        /*
        System.out.println("Game.run : nb de SU de comp "+IA.computer.simpleUnits.size());
        System.out.println("Game.run : nb de SU de play "+IA.player.simpleUnits.size());
        System.out.println("Game.run : nb de So de play "+IA.player.soldiers.size());
        System.out.println("Game.run : nb de U de play "+IA.player.units.size());
        System.out.println("Game.run : nb de So de comp "+IA.computer.soldiers.size());
        System.out.println("Game.run : nb de U de comp "+IA.computer.units.size());
        System.out.println("Game.run : nb nb itme en vie "+Item.aliveItems.size());
        */
        
        if (Building.gameOver())
            end();
        else
            middle();
    }

    public static void beginning(UI gui) {
        
        setUI(gui);
        
        setHuman(new Player("pink", BASE_LOCATION, namePlayer));
        setComputer(new Player("green", new Point2D.Double(40, 40), "The Intelligence"));
        

        for (int i = 0; i < 5; i++) {
            new SimpleUnit(human, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(computer, new Point2D.Double(30,35 + 2 * i));
        }
        
        IA.computer = computer;
        IA.player = human;
        IA.bigining();                              

    }

    public static void middle() {
        /*
        Iterator it = Item.aliveItems.iterator();
        Item current;
        for (Item i : Item.aliveItems)
            i.setUnDone();

        while (it.hasNext()){
            current = (Item)it.next();

            if (!current.done){

                current.setDone();

                if (current.execute())
                    it = Item.aliveItems.iterator();

            }
        }
        */
        for (int i = 0; i < Unit.dyingUnits.size(); i++) {
            Unit.dyingUnits.get(i).die();
        }
        for (int i = 0; i < Item.aliveItems.size(); i++) {
            Item.aliveItems.get(i).execute();
        }
        IA.execut();
        /*
        if ((UI.time+1)%600==0){
            IA.end();
            System.out.println("sauvegardeIA");
        }
*/

    }

    public static void end() {
        System.out.println("Game Over");
        IA.end();
        Game.exit();
    }
    
    public static void exit(){
        Item.aliveItems.clear();
        Item.deadItems.clear();
        Building.buildings.clear();
        SimpleUnit.aliveSimpleUnits.clear();
        SimpleUnit.deadSimpleUnits.clear();
        SoldierGroup.list.clear();
        SimpleUnitGroup.list.clear();
        Unit.dyingUnits.clear();
        SimpleUnitGroup.list.clear();
        SoldierGroup.list.clear();
        ui.time = 0;
        ui.timer.stop();
        ui.dispose();
        
        // ajouter une sortie de l’ecran
    }
    
    public static void scroll(){
    }
    
    //______________ACCESSEURS__________//

    static Player getHuman() {
        return human;
    }

    static Player getComputer() {
        return computer;
    }
    
    static UI getUI() {
        return ui;
    }

    //___________MUTATEURS___________//

    static void setHuman(Player p) {
        human = p;
    }

    static void setComputer(Player p) {
        computer = p;
    }
    
    static void setUI(UI u){
        ui = u;
    }

}
