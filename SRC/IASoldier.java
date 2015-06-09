import java.awt.geom.Point2D;
import java.util.LinkedList;


public class IASoldier extends IAUnite {

	public IASimpleUnit support = null;


	//_____________CONSTRUCTORS_____________//

	/**
	 * Constructeur.
	 * @param soldierGroupToSet Groupe de soldats
	 */
	public IASoldier(SoldierGroup soldierGroupToSet) {
		super(soldierGroupToSet);
	}

	//_______________METHODS_______________//

	/**
	 * Calcule l'etat du Soldat.
	 */
	public int calculateState() {
		/*
		 * Etat de la carte autour du groupe entre (0;432)
		 */
		int state =0;
		/*
		 * guerre zone 1 (+0;1)
		 */
		if (soldierPlayerInZone1.size()==0) state= 1;
		else state=0;
		/*
		 * superiorite numerique en zone 2 (+0;2)
		 */
		if (soldierPlayerInZone2.size()>soldierComputerInZone2.size());
		else state=2+state;
		/*
		 * batiment du computer est attaque (+0;4)
		 */
		for (Soldier i : IA.player.soldiers){
			if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
				state=4+state;
				break;
			}
		}


		//Unite Simple player isole dans zone 3(+0;8)
		for (SimpleUnit i : simpleUnitPlayerInZone3){
			boolean isIsolate = true;
			for (Soldier j : soldierPlayerInZone3){
				if (!i.isCloseTo(j, Finals.ATTACK_RANGE*2))isIsolate=false;    
			}
			if (isIsolate){
				state=8+state;
				break;
			}
		}


		// batiment du player est attaque (+ 0;16;32)
		int nbComputerSoldiersCloseToBat =0;
		int nbPlayerSoldiersCloseToBat =0;
		for (Soldier i : IA.computer.soldiers){
			if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
				nbComputerSoldiersCloseToBat++;
			}
		}
		for (Soldier i : IA.player.soldiers){
			if (IA.player.base.isCloseTo(i,Finals.ATTACK_RANGE)){
				nbPlayerSoldiersCloseToBat++;
			}
		}
		if (nbComputerSoldiersCloseToBat>0){
			if (nbPlayerSoldiersCloseToBat<nbComputerSoldiersCloseToBat)state=16+state;
			else state=32+state;
		}


		//superiorite numerique (ou casi egale) en zone 3 (+0;48;96)
		if (soldierPlayerInZone3.size()*0.8>soldierComputerInZone3.size())state= 48+state;
		else if (soldierPlayerInZone3.size()*1.5>soldierComputerInZone3.size())state=96+state;

		if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SOLDIER*0.2)state= 144+state;
		else if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SOLDIER*0.08)state= 288+state;
		return state;
	}

	/**
	 * Choisit la strategie a adopter.
	 */
	public int chooseStrategy(int State) {

		//precaution prise puisqu'il y a eu des soucis de depassement d'indice mais il devrait etre a present resolu
		if (State>431)  {
			return (int)Math.random()*6+1;
		}

		Double n = 0.0;
		// on calcule n , nombre de foi que State a ue lieu
		for(Double i :IA.nbSaveSol[State]) {
			n += i;
		}

		Double  sommeR=0.0;
		// on calcule sommeR , diviseur de la loi de probabilite
		for (Double x :IA.qIASoldier[State]){
			sommeR = sommeR +Math.exp((n+1)/(101+n)*x);
		}

		Double Rdm= Math.random()*sommeR;
		// on choisit la stategie avec une probabilite de exp((n+1)/(101+n)*x)/SommeR
		int i =0;
		for (Double x :IA.qIASoldier[State]){
			i++;
			if (Rdm<Math.exp((n+1)/(101+n)*x)) return i;
			Rdm=Rdm-Math.exp((n+1)/(101+n)*x);
		}

		//en cas d'erreurs on applique la strategie 1 
		return 1;
	}

	/**
	 * Applique la strategie au Soldier.
	 */
	public void applyStrategy(int strategy) {
		
		switch (strategy){
		
		//Attaque la base adverse
		case 1:
			
			unitGroup.setTarget(IA.player.base.getCenter());
			break;
			
		
		//Va defendre la base alliee
		case 2 :
			
			unitGroup.setTarget(IA.computer.base.getCenter());
			break;

			
		//Attaque timide
		case 3 :
		{
			Unit target = null; 
			Point2D cdm = unitGroup.getPosition();
			double distance1 = IA.player.base.distanceTo(cdm);
			for (Unit u : simpleUnitPlayerInZone3){
				boolean notIsolait = true;
				if (u.distanceTo(cdm)<distance1){
					for (Unit su : IA.player.units)
						if (u.distanceTo(su)<Finals.RAYON_ZONE_2){
							notIsolait = false;
							break;
						}
					if (notIsolait){
						distance1=u.distanceTo(cdm);
						target = u;
					}
				}
			}
			if (target != null)
				unitGroup.setTarget(target);
			else{
				if (target == null)
					unitGroup.setTarget(IA.player.base.getCenter());
				else 
					unitGroup.setTarget(target);
				
				if (soldierPlayerInZone1.size()>soldierComputerInZone1.size()){
					//ajoute soldierGroup au group allie le plus proche 
					double distance =  SoldierGroup.list.get(0).distanceTo(unitGroup);
					SoldierGroup group = SoldierGroup.list.get(0);
					for (SoldierGroup i : SoldierGroup.list ){
						if (distance >i.distanceTo(unitGroup)){
							distance=i.distanceTo(unitGroup);
							group = i;
						}
					}
					group.add(unitGroup);
				}
				else {
					if (soldierPlayerInZone1.size()!= 0){
						//se reunir
						unitGroup.setTarget(unitGroup.getPosition());
					}
				}
				if (soldierPlayerInZone1.size()== 0){
					if (soldierPlayerInZone2.size()>soldierComputerInZone2.size())
						unitGroup.setTarget(IA.computer.base.getCenter());
					else{
						if (soldierPlayerInZone2.size()!= 0){
							double distance =  soldierPlayerInZone2.get(0).distanceTo(unitGroup.getPosition());
							Soldier soldier =  soldierPlayerInZone2.get(0);
							for (int i = 1; i>soldierPlayerInZone2.size();i++ ){
								if (soldierPlayerInZone2.get(i).distanceTo(unitGroup.getPosition())<distance){
									distance = soldierPlayerInZone2.get(i).distanceTo(unitGroup.getPosition());
									soldier =soldierPlayerInZone2.get(i);
								}

							}
							unitGroup.setTarget(soldier);
						}
					}
					if (soldierPlayerInZone2.size()== 0){
						if (soldierPlayerInZone2.size()== 0)
							unitGroup.setTarget(unitGroup.getPosition());
						else {
							SoldierGroup PlayerZone3 = new SoldierGroup(soldierPlayerInZone3,IA.player);
							LinkedList <SoldierGroup> PlayerZone3Groups = PlayerZone3.divideInDenseGroups();
							/*
							 * on cherche le plus petit group de soldierPlayerInZone3 
							 */
							int size = PlayerZone3Groups.get(0).group.size();
							SoldierGroup smolerSoldierGroup = PlayerZone3Groups.get(0);
							for (int i =1 ; i > PlayerZone3Groups.size();i++){
								if (PlayerZone3Groups.get(i).group.size()<size){
									size = PlayerZone3Groups.get(i).group.size();
									smolerSoldierGroup = PlayerZone3Groups.get(i);
								}
							}
							if (smolerSoldierGroup.group.size()<unitGroup.group.size())
								unitGroup.setTarget(smolerSoldierGroup.getPosition());
								
							else{
								if (soldierPlayerInZone3.size()>soldierComputerInZone3.size())
									unitGroup.setTarget(IA.computer.base.getCenter());
								else{
									//Ajoute soldierGroup au groupe allie le plus proche
									double distance =  SoldierGroup.list.get(0).distanceTo(unitGroup);
									SoldierGroup group = SoldierGroup.list.get(0);
									for(SoldierGroup i : SoldierGroup.list){
										if (distance >i.distanceTo(unitGroup)){
											distance=i.distanceTo(unitGroup);
											group = i;
										}
									}
									group.add(unitGroup);
								}
							}
						}
					}
				}
			}
			break;
		}


		//Attaque a vue !
		case 4:{
			Unit target = null; 
			Point2D cdm = unitGroup.getPosition();
			double distance = IA.player.base.distanceTo(cdm);
			LinkedList <Unit> unitPlayerInZone3 = new LinkedList <Unit>();
			unitPlayerInZone3.addAll(this.simpleUnitPlayerInZone3);
			unitPlayerInZone3.addAll(this.soldierPlayerInZone3);
			for (Unit u : unitPlayerInZone3){
				if (u.distanceTo(cdm)<distance){
					distance=u.distanceTo(cdm);
					target = u;
				}
			}
			if (target == null)
				unitGroup.setTarget(IA.player.base.getCenter());
			else 
				unitGroup.setTarget(target);
			break;
		}

		
		//Rejoint un autre groupe
		case 5:{

			SoldierGroup allier =null;
			double distance = 10000000;
			for (SoldierGroup sg : SoldierGroup.list ){
				if ( unitGroup.distanceTo(sg)<distance & unitGroup!=sg){
					distance = unitGroup.distanceTo(sg);
					allier=sg;
				}
			}
			if (allier != null) {
				unitGroup.setTarget(allier.getPosition());
				allier.group.addAll(unitGroup.group);
				SoldierGroup.list.remove(unitGroup);
			}
			break;
		}


		//Scinde le groupe
		case 6:{
			if (!unitGroup.group.isEmpty()) {
				SoldierGroup newsg = new SoldierGroup((Soldier)unitGroup.group.getFirst());
				unitGroup.group.removeFirst();
				int i = 1;
				for (Unit u :unitGroup.group){
					if (i%2 ==0){
						newsg.group.add(u);
						unitGroup.group.remove(u);
					}
				}
				if (unitGroup.group.size()==0)
					SoldierGroup.list.remove(unitGroup);
			}
			break;
		}
		
		default: 
		}
	}
}

