public class IAHistObj {
    public int Stait;
    public int Action; 
    public double Reward;
    public IAHistObj(int st, int ac, double re) {
        Stait=st;
        Action=ac;
        Reward=re;
    }
    public IAHistObj(int st, int ac) {
        this(st,ac,0.0);
    }
    public void setReward(double recompense){
    	Reward = recompense;
    }
}
