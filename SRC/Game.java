import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game implements Finals {

    //________________ATTRIBUTS_____________//
    static Player human, computer;
    static UI ui;
    static boolean firstRun = true;
    static String[] options;


    /**
     * méthode appelée dans UI.
     */
    public static void run() {

        
        if (Building.gameOver())
            end();
        else
            middle();
    }
    
    public static void beginning(UI gui) {
        options = getOptions();   
        setUI(gui);
        
        setHuman(new Player(options[0], BASE_LOCATION, namePlayer));
        setComputer(new Player(options[1], new Point2D.Double(40, 40), "The Intelligence"));
        

        for (int i = 0; i < 5; i++) {
            new SimpleUnit(human, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(computer, new Point2D.Double(30,35 + 2 * i));
        }
        
        IA.computer = computer;
        IA.player = human;
        IA.beginning();                              

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
        
        //sortie de jeu: frame et panel
        JFrame finDeJeu;
        JPanel pFinDeJeu = new JPanel(null);
        pFinDeJeu.setBackground(Color.BLUE);   
        //regarde qui a gagne la partie
        boolean victoire = true;
        if(Game.human.base.isDead()){ 
        	victoire = false;
        }      
        if(victoire){
        	finDeJeu = new JFrame("You beat the game!");
        }else{
        	finDeJeu = new JFrame("You lost the game.");
        }
        //bouttons
        JButton bExit = new JButton();
        JButton bRejouer = new JButton();
        bRejouer.setBounds(0,0,screenWidth/6,screenWidth/8-1);
        bExit.setBounds(screenWidth/6,0, screenWidth/6,screenWidth/8);       
        //taille ecriture
        Font fontLarge = new Font(Font.DIALOG, Font.ITALIC, 25);
        bExit.setFont(fontLarge);
        bRejouer.setFont(fontLarge);
        //fond et texte
        bExit.setBackground(Color.RED);
        bRejouer.setBackground(Color.BLUE);
        bExit.setText("Quit.");
        bRejouer.setText("Again!");
        //listeners des boutons
        ActionListener exit = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ui.dispose();
            	ui.setVisible(false); //pas sur que ca suffise, si dispose ne parvient pas a l'eliminer..
		finDeJeu.dispose();
            }
        };
        ActionListener playAgain = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ui.dispose();
            	ui.setVisible(false);
            	UI gui = new UI();      
                gui.timer.start(); //a voir... 
		finDeJeu.dispose();
            }
        };
        //ajout listeners
        bExit.addActionListener(exit);
        bExit.addActionListener(playAgain);
        //on les ajoute au panel
        pFinDeJeu.add(bRejouer);
        pFinDeJeu.add(bExit); 
        //frame
        finDeJeu.setBounds(screenWidth/2-screenWidth/6,screenHeight/2-screenHeight/8, screenWidth/3,screenHeight/4);
        finDeJeu.add(pFinDeJeu);
        finDeJeu.setVisible(true);
		finDeJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finDeJeu.setResizable(false);
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
    
    /**
     * renvoi un tableau de taille de 2.
     * @return [0] = couleur du joueur : [1] = couleur de l’IA
     */
    static String[] getOptions() {
        String[] toReturn = new String[2];
        int i=0;
        ProcessBuilder pb = new ProcessBuilder("./getOptions.sh");
        Process p;
        try {
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                toReturn[i] = line;
                i++;
            }
            
            return toReturn; 
            
        } catch (IOException e) {
            System.out.println("fichier manquant : getOptions.sh");
            System.exit(1);
            return null;
        }
       
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
    /**
     * recupère un tableau de taille 2.
     * @param s [0] = couleur du joueur : [1] = couleur de l’IA
     */
    static void setOptions(String[] s) {
        ProcessBuilder pb = new ProcessBuilder("./setOptions.sh", s[0], s[1]);
        Process p;
        try {
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("fichier manquant : setOptions.sh");
            System.exit(1);
        }

    }
    
}
