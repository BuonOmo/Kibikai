import java.util.LinkedList;

public class IA {
    public static double Gamma = Finals.IA_GAMMA;
    public static double Alpa = Finals.IA_ALPHA;
    public static double [][] qIASoldier;
    public static double [][] qIASimpelUnite;
    public static Player computer;
    public static Player player;//le joureur est addversaire de l'IA//
    
    public static void bigining () {
        loadQIASoldier();
        loadQIASimpelUnite();
    }
    public static void execut(){
        for (int i=0; i<SoldierGroup.groupSoldierList.size();i++){
            if (SoldierGroup.groupSoldierList.get(i).getOwner()==computer);
            SoldierGroup.groupSoldierList.get(i).iaSoldier.execut();
        }
        for (int i=0; i<SimpleUnitGroup.groupSimpleUnitList.size();i++){
            if (SimpleUnitGroup.groupSimpleUnitList.get(i).getOwner()==computer);
            SimpleUnitGroup.groupSimpleUnitList.get(i).iaSimpleUnit.execut();
        }

        
    }
   
    public static void end(){
    
        LinkedList <Unit> AllUnit = computer.units;
        AllUnit.addAll(computer.deadUnits);
        
        for (int i = 0; i<AllUnit.size();i++){
 
                String className = AllUnit.get(i).getClass().getName();
                if (className == "Soldier"){
                        LinkedList <IAHistObj> histoList = ((Soldier)AllUnit.get(i)).histoList;
                        histoList.add(new IAHistObj(0,0,0));
                        for (int j = histoList.size()-1;j>=0;j--) rinforceQ(qIASoldier,histoList,j);
                }
                if (className == "SimpleUnit"){
                    LinkedList <IAHistObj> histoList = ((Soldier)AllUnit.get(i)).histoList;
                    histoList.add(new IAHistObj(0,0,0));
                    for (int j = histoList.size()-1;j>=0;j--) rinforceQ(qIASimpelUnite,histoList,j);
            } 
        }
        saveQIASoldier();
        saveQIASimpelUnite();
    }
    /**
     * @param Q
     * @param histoList
     * @param index
     */
    public static void rinforceQ(double [][] Q,LinkedList <IAHistObj> histoList,int index ){
        double Qsa;
        Qsa= Q[histoList.get(index).Stait][histoList.get(index).Action]+Alpa*(histoList.get(index).Reward+Gamma*Q[histoList.get(index+1).Stait][histoList.get(index+1).Action]-Q[histoList.get(index).Stait][histoList.get(index).Action]);
        Q[histoList.get(index).Stait][histoList.get(index).Action]=Qsa;
    }
    public static void saveQIASoldier(){  
    	
    	
    	
    }
    public static void saveQIASimpelUnite(){
    }
    private static void loadQIASoldier(){
        
    }
    private static void loadQIASimpelUnite(){
    
    }
}
