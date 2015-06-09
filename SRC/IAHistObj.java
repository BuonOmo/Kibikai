
public class IAHistObj {

    public int State;
    public int Action;
    public double Reward;
    public long Time;

    //_____________CONSTRUCTORS_____________//

    public IAHistObj(int st, int ac, double re, long t) {
        State = st;
        Action = ac;
        Reward = re;
        Time = t;
    }

    public IAHistObj(int st, int ac, long t) {
        this(st, ac, 0.0, t);
    }

    //_______________METHODS_______________//

    /**
     * Donne une recompense
     * @param recompense
     */
    public void setReward(double recompense) {
        Reward = recompense;
    }
}
