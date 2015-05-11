import java.awt.Color;
import java.util.LinkedList;

public class Player {
    String name;
    Color color;
    Building base;
    LinkedList<Unit> units;
    LinkedList<Soldier> soldiers;
    LinkedList<SimpleUnit> simpleUnits;
    LinkedList<Unit> deadUnits; //a traiter avec la future class Unit.isDestructed()//
    public Player(Color colorToSet, Building baseToSet, String nameToSet){
        color = colorToSet;
        base = baseToSet;
        name = nameToSet;
    }
}
