import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Scanner;

public class IA {
    public static double Gamma = Finals.IA_GAMMA;
    public static double Alpa = Finals.IA_ALPHA;
    public static Double [][] qIASoldier ;
    public static Double [][] qIASimpleUnit ;
    public static Double [][] nbSaveSol ;
    public static Double [][] nbSaveSU ;
    
    public static Player computer;
    public static Player player;
    static Object tab; //le joueur est adversaire de l'IA//
    
    public static void bigining () {
        

        try {
                            nbSaveSol =load( "savenbSaveSol.txt");
                            nbSaveSU =load( "savenbSaveSU.txt");
                            qIASoldier=load("SaveqIASoldier.txt");
                            qIASimpleUnit=load("SaveqIASimpleUnite.txt");
                            //loadQIASoldier();
                            //loadQIASimpleUnite();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
    }
    public static void execut(){
        //aplliquer l'IA au Groupe de soldat 
        for (int i = 0 ; i<SoldierGroup.list.size();i++ ){
            if ((UI.time+i)%2==0)
            SoldierGroup.list.get(i).ia.execut();
        }
        
        //aplliquer l'IA au Groupe de soldat 
        for (int i = 0 ;  i+1 < SimpleUnitGroup.list .size();i++){
            // petits verification 
            /*if (SimpleUnitGroup.list.get(i).ia==null)
                SimpleUnitGroup.list.remove(i);
            if (SimpleUnitGroup.list.get(i).group.size()==0)
                SimpleUnitGroup.list.remove(i);*/
            if ((UI.time+i)%5==0)
            SimpleUnitGroup.list.get(i).ia.execut();
        }
    }
   
    public static void end(){
    
        LinkedList <Unit> AllUnit = computer.units;
        AllUnit.addAll(computer.deadUnits);
        System.out.println("IA.end : allUnit.siz AllUnit.size()"+AllUnit.size());
        
        for (int i = 0; i<AllUnit.size();i++){
                System.out.print("|");
                String className = AllUnit.get(i).getClass().getName();
                if (className == "Soldier"){
                        LinkedList <IAHistObj> histoList = ((Soldier)AllUnit.get(i)).histoList;
                        histoList.add(new IAHistObj(0,0,0));
                        for (int j = histoList.size()-3;j>=0;j--) rinforceQ(qIASoldier,nbSaveSol,histoList,j);
                        histoList.clear();
                }
                if (className == "SimpleUnit"){
                    LinkedList <IAHistObj> histoList = ((SimpleUnit)AllUnit.get(i)).histoList;
                    histoList.add(new IAHistObj(0,0,0));
                    for (int j = histoList.size()-3;j>=0;j--)IA.rinforceQ(qIASimpleUnit,nbSaveSU, histoList, j);
                    histoList.clear();   
            } 

        }
        
        //sovgarder pour le ranforcement; 
        try {
                            save ("SaveqIASoldier.txt",qIASoldier);
                            save ("SaveqIASimpleUnite.txt",qIASimpleUnit);
                            IA.save("savenbSaveSU.txt", nbSaveSU);
                            IA.save("savenbSaveSol.txt", nbSaveSol);
                            //saveQIASoldierANDSimpleUnit();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
    }
    /**
     * @param Q
     * @param histoList
     * @param index
     */
    public static void rinforceQ(Double [][] Q,Double [][] nbS, LinkedList <IAHistObj> histoList,int index ){
        Double Qsa;

        Qsa =histoList.get(index).Reward;
        Qsa += Gamma*Q[histoList.get(index+1).Stait][histoList.get(index+1).Action-1];
        Qsa -= Q[histoList.get(index).Stait][histoList.get(index).Action-1];
        Qsa = Qsa/(nbS [histoList.get(index).Stait][histoList.get(index).Action-1]+1);
        Qsa += Q[histoList.get(index).Stait][histoList.get(index).Action-1];
        //Qsa= Q[histoList.get(index).Stait][histoList.get(index).Action-1]+ (1/(nbS [histoList.get(index).Stait][histoList.get(index).Action-1]+1))*(histoList.get(index).Reward+Gamma*Q[histoList.get(index+1).Stait][histoList.get(index+1).Action-1]-Q[histoList.get(index).Stait][histoList.get(index).Action-1]);
        
        if (Qsa>10) Qsa =10.0;
        if (Qsa<-10) Qsa =10.0;
        nbS [histoList.get(index).Stait][histoList.get(index).Action-1]++;
        Q[histoList.get(index).Stait][histoList.get(index).Action-1]=Qsa;
    }


    /**
     * @param fileName
     * @param tabtoSave
     * @throws IOException
     */
    public static void save (String file, Double[][] toSave)throws IOException{
        File saveFile = new File(file);
        if(!saveFile.exists()){
                saveFile.createNewFile();
        }else{
                saveFile.delete();
                saveFile.createNewFile();
        }
        FileWriter scribe = new FileWriter(saveFile);
        scribe.write(""+toSave.length+"\n"+toSave[0].length+"\n");
        for(int i=0;i<toSave.length;i++){
                for(int k=0;k<toSave[0].length;k++){
                        scribe.write(""+toSave[i][k]+"\n");
                }
        }
        scribe.close();
    }


    /**
     * @param file
     * @return Tab loded
     * @throws FileNotFoundException
     */
    public static Double[][] load(String file ) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(file)); 
                int nBLignes = Integer.parseInt(scanner.nextLine());
                int nBColonnes = Integer.parseInt(scanner.nextLine());
                Double[][] toRetrne = new Double[nBLignes][nBColonnes];
                       for(int i = 0; i<nBLignes;i++){
                                for(int k = 0; k<nBColonnes;k++){
                                        toRetrne[i][k] = Double.parseDouble(scanner.nextLine());
                                }
                        }
                scanner.close();
                return toRetrne;
        
    }
}
