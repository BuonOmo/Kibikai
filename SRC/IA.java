import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Scanner;

public class IA {
    public static double Gamma = Finals.IA_GAMMA;
    public static double Alpa = Finals.IA_ALPHA;
    public static Double[][] qIASoldier;
    public static Double[][] qIASimpleUnit;
    public static Player computer;
    public static Player player; //le joueur est adversaire de l'IA//

    public static void bigining() {


        try {
            loadQIASoldier();
            loadQIASimpleUnite();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void execut() {
        //*****System.out.println("IA.execut : nb Sg "+SoldierGroup.list.size());
        //for (SoldierGroup element : SoldierGroup.list){
        for (int i = 0; i < SoldierGroup.list.size(); i++) {
            // element.ia.execut();
            if ((UI.time + i) % 2 == 0)
                SoldierGroup.list.get(i).ia.execut();
        }

        //*****System.out.println("IA.execut : nb Sug "+SimpleUnitGroup.list.size());
        //for (SimpleUnitGroup element : SimpleUnitGroup.list){
        for (int i = 0; i + 1 < SimpleUnitGroup.list.size(); i++) {
            if (SimpleUnitGroup.list.get(i).ia == null)
                SimpleUnitGroup.list.remove(i);
            if (SimpleUnitGroup.list.get(i).group.size() == 0)
                SimpleUnitGroup.list.remove(i);
            if ((UI.time + i) % 5 == 0)
                SimpleUnitGroup.list.get(i).ia.execut();
            //element.ia.execut();
        }
    }

    public static void end() {

        LinkedList<Unit> AllUnit = computer.units;
        AllUnit.addAll(computer.deadUnits);
        System.out.println("IA.end : allUnit.siz AllUnit.size()" + AllUnit.size());

        for (int i = 0; i < AllUnit.size(); i++) {
            System.out.print("|");
            String className = AllUnit.get(i).getClass().getName();
            if (className == "Soldier") {
                LinkedList<IAHistObj> histoList = ((Soldier) AllUnit.get(i)).histoList;
                histoList.add(new IAHistObj(0, 0, 0));
                for (int j = histoList.size() - 3; j >= 0; j--)
                    rinforceQ(qIASoldier, histoList, j);
                histoList.clear();
            }
            if (className == "SimpleUnit") {
                LinkedList<IAHistObj> histoList = ((SimpleUnit) AllUnit.get(i)).histoList;
                histoList.add(new IAHistObj(0, 0, 0));
                for (int j = histoList.size() - 3; j >= 0; j--)
                    IA.rinforceQ(qIASimpleUnit, histoList, j);
                histoList.clear();
            }

        }
        try {
            saveQIASoldierANDSimpleUnit();
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
    public static void rinforceQ(Double[][] Q, LinkedList<IAHistObj> histoList, int index) {
        Double Qsa;

        Qsa = histoList.get(index).Reward;
        Qsa += Gamma * Q[histoList.get(index + 1).Stait][histoList.get(index + 1).Action - 1];
        Qsa -= Q[histoList.get(index).Stait][histoList.get(index).Action - 1];
        Qsa = Qsa * Alpa;
        Qsa += Q[histoList.get(index).Stait][histoList.get(index).Action - 1];
        //Qsa= Q[histoList.get(index).Stait-1][histoList.get(index).Action-1]+ Alpa*(histoList.get(index).Reward+Gamma*Q[histoList.get(index+1).Stait-1][histoList.get(index+1).Action-1]-Q[histoList.get(index).Stait-1][histoList.get(index).Action-1]);

        if (Qsa > 10)
            Qsa = 10.0;
        if (Qsa < -10)
            Qsa = 10.0;
        Q[histoList.get(index).Stait][histoList.get(index).Action - 1] = Qsa;
    }


    public static void saveQIASoldierANDSimpleUnit() throws IOException {
        // Cr?e ou rase ? neuf les fichiers de sauvegarde
        File saveFileSol = new File("SaveqIASoldier.txt");
        File saveFileSU = new File("SaveqIASimpleUnite.txt");

        if (!saveFileSol.exists()) {
            saveFileSol.createNewFile();
        } else {
            saveFileSol.delete();
            saveFileSol.createNewFile();
        }
        if (!saveFileSU.exists()) {
            saveFileSU.createNewFile();
        } else {
            saveFileSU.delete();
            saveFileSU.createNewFile();
        }
        //Initialise les FileWriter
        FileWriter scribeSol = new FileWriter(saveFileSol);
        FileWriter scribeSu = new FileWriter(saveFileSU);
        //Ecrit la hauteur et la lareur du tableau sur les deux premieres lignes des fichiers
        scribeSol.write("" + qIASoldier.length + "\n" + qIASoldier[0].length + "\n");
        scribeSu.write("" + qIASimpleUnit.length + "\n" + qIASimpleUnit[0].length + "\n");
        //Copie les information en format String, a raison d'une valeur par ligne
        for (int i = 0; i < qIASoldier.length; i++) {
            for (int k = 0; k < qIASoldier[0].length; k++) {
                scribeSol.write("" + qIASoldier[i][k] + "\n");
            }
        }
        for (int i = 0; i < qIASimpleUnit.length; i++) {
            for (int k = 0; k < qIASimpleUnit[0].length; k++) {
                scribeSu.write("" + qIASimpleUnit[i][k] + "\n");
            }
        }
        //Ferme les FileWriter
        scribeSol.close();
        scribeSu.close();

    }

    public static void loadQIASoldier() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("SaveqIASoldier.txt"));
        int nBLignes = Integer.parseInt(scanner.nextLine());
        int nBColonnes = Integer.parseInt(scanner.nextLine());
        Double[][] qIASoldierLoading = new Double[nBLignes][nBColonnes];
        for (int i = 0; i < nBLignes; i++) {
            for (int k = 0; k < nBColonnes; k++) {
                qIASoldierLoading[i][k] = Double.parseDouble(scanner.nextLine());
            }
        }
        scanner.close();
        qIASoldier = qIASoldierLoading;
    }

    public static void loadQIASimpleUnite() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("SaveqIASimpleUnite.txt"));
        System.out.println("j’ai trouvé le \"ca c'est bon\"");
        int nBLignes = Integer.parseInt(scanner.nextLine());
        int nBColonnes = Integer.parseInt(scanner.nextLine());
        Double[][] qIASimpleUnitLoading = new Double[nBLignes][nBColonnes];
        for (int i = 0; i < nBLignes; i++) {
            for (int k = 0; k < nBColonnes; k++) {
                qIASimpleUnitLoading[i][k] = Double.parseDouble(scanner.nextLine());
            }
        }
        scanner.close();
        qIASimpleUnit = qIASimpleUnitLoading;
    }
}
