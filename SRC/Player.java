import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Player implements Finals {
    String name;
    Color color;
    Building base;
    LinkedList<Item> items = new LinkedList<Item>();
    LinkedList<Unit> units = new LinkedList<Unit>();
    ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
    ArrayList<SimpleUnit> simpleUnits = new ArrayList<SimpleUnit>();
    LinkedList<Unit> deadUnits = new LinkedList<Unit>(); //a traiter avec la future class Unit.isDestructed()//
    
    
    ArrayList<BufferedImage> soldierAlive = new ArrayList<>(3);
    ArrayList<BufferedImage> soldierAliveSelected = new ArrayList<>(3);
    ArrayList<BufferedImage> soldierCreation = new ArrayList<>(8);
    
    ArrayList<BufferedImage> simpleUnitAlive = new ArrayList<>(3);
    ArrayList<BufferedImage> simpleUnitAliveSelected = new ArrayList<>(3);
    
    static int soldierCreationOffset = -19;

    @Deprecated
    public Player(Color c, Building baseToSet, String nameToSet) {
        color = new Color(c.getRed(), c.getGreen(), c.getBlue());
        base = baseToSet;
        name = nameToSet;
    }

    /**
     * @param colorToSet green / blue / orange / pink
     * @param baseLocation
     * @param nameToSet
     */
    public Player(String colorToSet, Point2D baseLocation, String nameToSet) {
        base = new Building(this, baseLocation);
        name = nameToSet;
        soldiers = new ArrayList<Soldier>(NUMBER_MAX_OF_SOLDIER);
        simpleUnits = new ArrayList<SimpleUnit>(NUMBER_MAX_OF_SIMPLEUNIT);
        setColor(colorToSet);
    }
    
    private void setImages(String c){
        try {
            for (int i=0; i<3; i++){
                soldierAlive.add(ImageIO.read(new File("IMG/"+c+"/Soldier/"+i+".png")));
                soldierAliveSelected.add(ImageIO.read(new File("IMG/"+c+"/Soldier_selected/"+i+".png")));
                simpleUnitAlive.add(ImageIO.read(new File("IMG/"+c+"/SimpleUnit/"+i+".png")));
                simpleUnitAliveSelected.add(ImageIO.read(new File("IMG/"+c+"/SimpleUnit_selected/"+i+".png")));
            }
            for (int i=1;i<9;i++)
                soldierCreation.add(ImageIO.read(new File("IMG/"+c+"/Soldier_creation/"+i+".png")));
        }catch (IOException e) {
           System.out.println("erreur d’écriture");
        }

    }
    
    void setColor(String c){
        setImages(c);
        switch (c){
            case ("green"):{
                color = GREEN;
                break;
            }
            case ("blue"):{
                color = BLUE;
                break;
            }
            case ("orange"):{
                color = ORANGE;
                break;                
            }
            case ("pink"):{
                color = PINK;
                break;                                
            }
        }
    }

    void setColor(){
        
        
    }
}