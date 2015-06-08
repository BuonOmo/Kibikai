import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.geom.Point2D;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game implements Finals {

    //________________ATTRIBUTS_____________//
	
    static Player human, computer;
    static UI ui;
    static boolean firstRun = true;
    static String[] options;
    static String whoWin;
    static boolean over, fog;
    static PlayWave musicPlayer;


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
        
        options = getOptions();   
        
        musicPlayer = (PlayWave.alreadyExists) ? PlayWave.firstOne : new PlayWave(options[2].equals("MusicOn"));
                
        over = false;

        setUI(gui);
        
        fog = options[3].equals("FogOn");
        
        setHuman(new Player(options[0], BASE_LOCATION, "The Human"));
        setComputer(new Player(options[1], new Point2D.Double(40, 40), "The Intelligence"));

        for (int i = 0; i < 5; i++) {
            new SimpleUnit(human, new Point2D.Double(20, 5 + 2 * i));
            new SimpleUnit(computer, new Point2D.Double(30,35 + 2 * i));
        }
        
        IA.computer = computer;
        IA.player = human;
        IA.beginning();     
        
        startMusic();
    }

    public static void middle() {
        
        for (int i = 0; i < Unit.dyingUnits.size(); i++) {
            Unit.dyingUnits.get(i).die();
        }
        for (int i = 0; i < Item.aliveItems.size(); i++) {
            Item.aliveItems.get(i).execute();
        }
        IA.execut();
    }

    public static void end() {
        whoWin = (human.base.isDead()) ? "LOOSE" : "WIN" ;
        over = true;
        ui.timer.stop();
        IA.end();
        stopMusic();
    }
    
    static void startMusic(){
        if (!musicPlayer.isAlive())
            musicPlayer.start();
    }
    static void stopMusic(){
        musicPlayer.stop();
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
        String[] toReturn = new String[4];
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
        ProcessBuilder pb = new ProcessBuilder("./setOptions.sh", s[0], s[1], s[2], s[3]);
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
