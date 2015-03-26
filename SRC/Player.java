import java.awt.Color;

public class Player {
    String name;
    Color color;
    Building base;
    Unit unit[];
    
    public Player(Color colorToSet, Building baseToSet, String nameToSet){
        color = colorToSet;
        base = baseToSet;
        name = nameToSet;
    }
}
