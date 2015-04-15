import java.util.LinkedList;

public abstract class IAUnite  {
    private LinkedList <IAHistObj> histoList = new LinkedList <IAHistObj>();
    public static LinkedList <IAUnite> AllIA = new LinkedList <IAUnite>();
    public IAUnite() {
        
    }
    public void execut(){
        applyStrategy (chooseStrategy(calculateStaite()));
    }
    public abstract int calculateStaite ();
    public abstract int chooseStrategy (int staite);
    public abstract void applyStrategy (int strategy);
}
