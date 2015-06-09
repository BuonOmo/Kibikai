import java.awt.geom.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Scanner;

public class IA {

	public static double Gamma = Finals.IA_GAMMA;
	
	public static Double [][] qIASoldier ;
	public static Double [][] qIASimpleUnit ;
	public static Double [][] nbSaveSol ;
	public static Double [][] nbSaveSU ;

	public static Player computer;
	public static Player player; 	


	/**
	 * Recupere les fichiers de QLearning.
	 */
	public static void beginning () {

		try {
			nbSaveSol =load( "savenbSaveSol.txt");
			nbSaveSU =load( "savenbSaveSU.txt");
			qIASoldier=load("SaveqIASoldier.txt");
			qIASimpleUnit=load("SaveqIASimpleUnite.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Applique le comportement a faire a toutes les unites.
	 */
	public static void execut(){

		// Deplace l'endroit d'apparition des unites
		computer.base.setTarget(new Point2D.Double(computer.base.getCenter().getX(),computer.base.getCenter().getY()+computer.base.getHeight()*2));


		//Applique l'IA au Groupe de soldats 
		for (int i = 0 ; i<SoldierGroup.list.size();i++ ){
			if ((UI.time+i)%2==0)
				SoldierGroup.list.get(i).ia.execut();
		}

		//Applique l'IA au Groupe de SU
		for (int i = 0 ;  i+1 < SimpleUnitGroup.list .size();i++){
			if ((UI.time+i)%5==0)
				SimpleUnitGroup.list.get(i).ia.execut();
		}
	}


	/**
	 * Methode appelee a la fin du jeu.
	 * Met a jour les fichiers de QLearning.
	 */
	public static void end(){
                // métre a jour les historiques une derniére foie 
        for (SoldierGroup sg : SoldierGroup.list){
				sg.ia.createHisto();
		}
        for (SimpleUnitGroup sug :SimpleUnitGroup.list){
				sug.ia.createHisto();
		}
        
        


		LinkedList <Unit> AllUnit = computer.units;
		AllUnit.addAll(computer.deadUnits);
		System.out.println("IA.end : allUnit.siz AllUnit.size()"+AllUnit.size());



		for (int i = 0; i<AllUnit.size();i++){
			System.out.print("|");
			String className = AllUnit.get(i).getClass().getName();
			if (className == "Soldier"){
				LinkedList <IAHistObj> histoList = ((Soldier)AllUnit.get(i)).histoList;
				histoList.add(new IAHistObj(0,0,0));
				Double R = histoList.get(histoList.size()-2).Reward;
				for (int j = histoList.size()-3;j>=0;j--) {
					R= reinforceQ(qIASoldier,nbSaveSol,histoList,j,R);
				}
				histoList.clear();
			}

			if (className == "SimpleUnit"){
				LinkedList <IAHistObj> histoList = ((SimpleUnit)AllUnit.get(i)).histoList;
				histoList.add(new IAHistObj(0,0,0));                                
				Double R = histoList.get(histoList.size()-2).Reward;
				for (int j = histoList.size()-3;j>=0;j--) {
					R= reinforceQ(qIASimpleUnit,nbSaveSU,histoList,j,R);
				}
				histoList.clear();   
			} 

		}

		//Sauvegarde pour le Reinforcement Learning
		try {
			save ("SaveqIASoldier.txt",qIASoldier);
			save ("SaveqIASimpleUnite.txt",qIASimpleUnit);
			IA.save("savenbSaveSU.txt", nbSaveSU);
			IA.save("savenbSaveSol.txt", nbSaveSol);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Permet de calculer la nouvelle valeur de recompense d'une case du Qlearning.
	 * @param Q tableau du QLearning
	 * @param nbS nombre de fois ou la valeur Qsa a ete mise a jour
	 * @param histoList historique de la partie d'une unite
	 * @param index emplacement de la case a changer
	 * @param previousReward recompense precedente de la case a changer
	 */
	public static Double reinforceQ(Double [][] Q, Double [][] nbS, LinkedList <IAHistObj> histoList, int index, Double previousReward ){
		
		//Case a modifier dans les tableaux
		Double Qsa;

		//Calcul de la plus grosse recompense Q max a l'etat suivant 
		Double Qmax = -10.0;
		for (Double Qnext : Q[histoList.get(index+1).State]){
			if (Qnext>Qmax)Qmax=Qnext;
		}
		
		Qsa =histoList.get(index).Reward;
		Qsa += Gamma/2*(Qmax+previousReward);
		Qsa -= Q[histoList.get(index).State][histoList.get(index).Action-1];
		Qsa = Qsa/(nbS [histoList.get(index).State][histoList.get(index).Action-1]+1);
		Qsa += Q[histoList.get(index).State][histoList.get(index).Action-1];

		if (Qsa>10) Qsa =10.0;
		if (Qsa<-10) Qsa =10.0;
		nbS [histoList.get(index).State][histoList.get(index).Action-1]++;
		Q[histoList.get(index).State][histoList.get(index).Action-1]=Qsa;
		return previousReward*Gamma/2+ histoList.get(index).Reward;
	}


	/**
	 * Sauvegarde les fichiers de l'IA.
	 * @param file nom du fichier
	 * @param toSave la valeur a sauvegarder
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
	 * Permet de lire les valeurs d'un fichier sous sa forme de tableau.
	 * @param file nom du fichier
	 * @return le tableau charge
	 */
	public static Double[][] load(String file) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(new File(file)); 
		int nBLines = Integer.parseInt(scanner.nextLine());
		int nBColumns = Integer.parseInt(scanner.nextLine());
		Double[][] toReturn = new Double[nBLines][nBColumns];
		for(int i = 0; i<nBLines;i++){
			for(int k = 0; k<nBColumns;k++){
				toReturn[i][k] = Double.parseDouble(scanner.nextLine());
			}
		}
		scanner.close();
		return toReturn;

	}
}
