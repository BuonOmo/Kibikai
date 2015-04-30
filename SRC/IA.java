import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class IA {
    public static double Gamma = Finals.IA_GAMMA;
    public static double Alpa = Finals.IA_ALPHA;
    public static Double [][] qIASoldier;
    public static Double [][] qIASimpleUnit;
    public static Player computer;
    public static Player player;//le joueur est adversaire de l'IA//
    
    public static void bigining () throws FileNotFoundException{
        loadQIASoldier();
        loadQIASimpleUnite();
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
                    for (int j = histoList.size()-1;j>=0;j--) rinforceQ(qIASimpleUnit,histoList,j);
            } 
        }
        saveQIASoldierANDSimpleUnit();
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
    
    
    
    
    
    public static void saveQIASoldierANDSimpleUnit() throws IOException{  
    	// Crée ou rase à neuf les fichiers de sauvegarde
    	 File saveFileSol = new File("SaveqIASoldier.txt");
		 File saveFileSU = new File("SaveqIASimpleUnite.txt");
		 
		 if(!saveFileSol.exists()){
			 saveFileSol.createNewFile();
		 }else{
			 saveFileSol.delete();
			 saveFileSol.createNewFile();
		 }
		 if(!saveFileSU.exists()){
			 saveFileSU.createNewFile();
		 }else{
			 saveFileSU.delete();
			 saveFileSU.createNewFile();
		 }
		 //Initialise les FileWriter
		 FileWriter scribeSol = new FileWriter(saveFileSol);
		 FileWriter scribeSu = new FileWriter(saveFileSU);
		 //Ecrit la hauteur et la lareur du tableau sur les deux premieres lignes des fichiers
		 scribeSol.write(""+qIASoldier.length+"\n"+qIASoldier[0].length+"\n");
		 scribeSol.write(""+qIASimpleUnit.length+"\n"+qIASimpleUnit[0].length+"\n");
		 //Copie les information en format String, a raison d'une valeur par ligne
		 for(int i=0;i<qIASoldier.length;i++){
			 for(int k=0;k<qIASoldier[0].length;k++){
				 scribeSol.write(""+qIASoldier[i][k]+"\n");
			 }
		 }
		 for(int i=0;i<qIASimpleUnit.length;i++){
			 for(int k=0;k<qIASimpleUnit[0].length;k++){
				 scribeSu.write(""+qIASimpleUnit[i][k]+"\n");
			 }
		 }
		 //Ferme les FileWriter
		 scribeSol.close();
		 scribeSu.close();

    }
    private static void loadQIASoldier() throws FileNotFoundException{   	
    	 Scanner scanner = new Scanner(new File("SaveqIASoldier.txt")); 	 
		 int nBLignes = Integer.parseInt(scanner.nextLine());
		 int nBColonnes = Integer.parseInt(scanner.nextLine());
		 Double[][] qIASoldierLoading = new Double[nBLignes][nBColonnes];
		 int i = 0;
			 while(scanner.hasNextLine()){
				 for(int k = 0; k<nBLignes;k++){
					 qIASoldierLoading[i][k] = Double.parseDouble(scanner.nextLine());
				 }
				 i++;
			 }
		 scanner.close();
		 qIASoldier = qIASoldierLoading;        
    }
    private static void loadQIASimpleUnite() throws FileNotFoundException{
    	 Scanner scanner = new Scanner(new File("SaveqIASimpleUnit.txt")); 	 
		 int nBLignes = Integer.parseInt(scanner.nextLine());
		 int nBColonnes = Integer.parseInt(scanner.nextLine());
		 Double[][] qIASimpleUnitLoading = new Double[nBLignes][nBColonnes];
		 int i = 0;
			 while(scanner.hasNextLine()){
				 for(int k = 0; k<nBLignes;k++){
					 qIASimpleUnitLoading[i][k] = Double.parseDouble(scanner.nextLine());
				 }
				 i++;
			 }
		 scanner.close();
		 qIASimpleUnit = qIASimpleUnitLoading;
    }
}
