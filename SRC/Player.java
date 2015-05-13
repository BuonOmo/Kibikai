import java.awt.Color;
import java.awt.geom.Point2D;

import java.util.LinkedList;

public class Player {
    String name;
    Color color;
    Building base;
    LinkedList<Unit> units;
    LinkedList<Soldier> soldiers;
    LinkedList<SimpleUnit> simpleUnits;
    LinkedList<Unit> deadUnits; //a traiter avec la future class Unit.isDestructed()//
    
    public Player(Color c, Building baseToSet, String nameToSet){
        color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 125);
        base = baseToSet;
        name = nameToSet;
    }
    
    public Player(Color c, Point2D baseLocation, String nameToSet){
        color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 125);
        base = new Building(this, baseLocation);
        name = nameToSet;
    }
}
