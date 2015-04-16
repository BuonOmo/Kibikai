import java.util.LinkedList;

public class IA {
    public double Gamma = Finals.IA_GAMMA;
    public double Alpa = Finals.IA_ALPHA;
    public static double [][] qIASoldier;
    public static double [][] qIASimpelUnite;
    public static void bigining () {
        loadQIASoldier();
        loadQIASimpelUnite();
    }
    public static void execut(){
        for (int i=0; i<SoldierGroup.groupSoldierList.size();i++){
            if (SoldierGroup.groupSoldierList.get(i).getOwner().name=="IA");
            SoldierGroup.groupSoldierList.get(i).iaSoldier.execut();
        }
        for (int i=0; i<SimpleUnitGroup.groupSimpleUnitList.size();i++){
            if (SimpleUnitGroup.groupSimpleUnitList.get(i).getOwner().name=="IA");
            SimpleUnitGroup.groupSimpleUnitList.get(i).iaSimpleUnit.execut();
        }

        
    }
   
    public static void end(){
            /*
        LinkedList <Item> AllItem = Item.AllItem;
        
        for (int i = 0; i<AllItem.size();i++){
            if (AllItem.get(i).owner=="IA"){
                LinkedList <IAHistObj> histoList = AllItem.get(i).IAUnite.histoList;
                histoList.add(new IAHistObj(0,0,0));
                for (int j = histoList.size()-1;j>=0;j--) rinforceQ(qIASoldier,histoList,j);
            } 
        }
        save(qIASoldier);
        */
        }
    /**
     * @param Q
     * @param histoList
     * @param index
     */
    public void rinforceQ(double [][] Q,LinkedList <IAHistObj> histoList,int index ){
        double Qsa;
        Qsa= Q[histoList.get(index).Stait][histoList.get(index).Action]+Alpa*(histoList.get(index).Reward+Gamma*Q[histoList.get(index+1).Stait][histoList.get(index+1).Action]-Q[histoList.get(index).Stait][histoList.get(index).Action]);
        Q[histoList.get(index).Stait][histoList.get(index).Action]=Qsa;
    }
    public void save (double[][] Q){
        
    }
    private static void loadQIASoldier(){
        
    }
    private static void loadQIASimpelUnite(){
    
    }
}
