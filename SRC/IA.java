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
        for (SoldierGroup element : SoldierGroup.list){
            element.ia.execut();
        }
        
        for (SimpleUnitGroup element : SimpleUnitGroup.list){
            element.ia.execut();
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
                    for (int j = histoList.size()-1;j>=0;j--)IA.rinforceQ(qIASimpleUnit, histoList, j);
                    
            } 
        }
        saveQIASoldierANDSimpleUnit();
    }
    /**
     * @param Q
     * @param histoList
     * @param index
     */
    public static void rinforceQ(Double [][] Q,LinkedList <IAHistObj> histoList,int index ){
        Double Qsa;
        Qsa= Q[histoList.get(index).Stait][histoList.get(index).Action]+Alpa*(histoList.get(index).Reward+Gamma*Q[histoList.get(index+1).Stait][histoList.get(index+1).Action]-Q[histoList.get(index).Stait][histoList.get(index).Action]);
        Q[histoList.get(index).Stait][histoList.get(index).Action]=Qsa;
    }
    
    
    
    
    
    public static void saveQIASoldierANDSimpleUnit(){  
    	// Cr�e ou rase � neuf les fichiers de sauvegarde
    	 File saveFileSol = new File("SaveqIASoldier.txt");
		 File saveFileSU = new File("SaveqIASimpleUnite.txt");
		 
		 if(!saveFileSol.exists()){
			 try {
				saveFileSol.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
			 saveFileSol.delete();
			 try {
				saveFileSol.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(!saveFileSU.exists()){
			 try {
				saveFileSU.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
			 saveFileSU.delete();
			 try {
				saveFileSU.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 //Initialise les FileWriter
		 FileWriter scribeSol;
		try {
			scribeSol = new FileWriter(saveFileSol);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 FileWriter scribeSu;
		try {
			scribeSu = new FileWriter(saveFileSU);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //Ecrit la hauteur et la lareur du tableau sur les deux premieres lignes des fichiers
		 try {
			scribeSol.write(""+qIASoldier.length+"\n"+qIASoldier[0].length+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			scribeSol.write(""+qIASimpleUnit.length+"\n"+qIASimpleUnit[0].length+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //Copie les information en format String, a raison d'une valeur par ligne
		 for(int i=0;i<qIASoldier.length;i++){
			 for(int k=0;k<qIASoldier[0].length;k++){
				 try {
					scribeSol.write(""+qIASoldier[i][k]+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		 for(int i=0;i<qIASimpleUnit.length;i++){
			 for(int k=0;k<qIASimpleUnit[0].length;k++){
				 try {
					scribeSu.write(""+qIASimpleUnit[i][k]+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		 //Ferme les FileWriter
		 try {
			scribeSol.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			scribeSu.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
