import java.sql.Time;

public class IAHistObj {
    public int Stait;
    public int Action; 
    public double Reward;
    public long Time; 
    public IAHistObj(int st, int ac, double re,long t) {
        Stait=st;
        Action=ac;
        Reward=re;
        Time = t;
    }
    public IAHistObj(int st, int ac,long t) {
        this(st,ac,0.0,t);
    }
    public void setReward(double recompense){
    	Reward = recompense;
    }
}
