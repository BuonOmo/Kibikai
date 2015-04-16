import java.awt.Color;
import java.util.LinkedList;

public class Player {
    String name;
    Color color;
    Building base;
    LinkedList<Unit> units;
    
    public Player(Color colorToSet, Building baseToSet, String nameToSet){
        color = colorToSet;
        base = baseToSet;
        name = nameToSet;
    }
}
