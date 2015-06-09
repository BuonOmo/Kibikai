import java.awt.geom.Point2D;


public class IASimpleUnit extends IAUnite {

	public IASoldier support;


	//_____________CONSTRUCTORS_____________//

	/**
	 * Constructeur.
	 * @param simpleUnitGroupToSet Groupe d'unites simples
	 */
	public IASimpleUnit(SimpleUnitGroup simpleUnitGroupToSet) {
		super(simpleUnitGroupToSet);
		support = null;
	}


	//_______________METHODS_______________//

	/**
	 * @return Etat entre 1 et 431
	 */
	public int calculateState() {

		int State = 0;
		if (support!=null){
			// strategie de groupes soutenue (0;5)
			State = support.presentStrategy-1;

			//superiorite numerique du groupe supporte dans la zone 1(0;6)
			if (support.soldierComputerInZone1.size()>support.soldierPlayerInZone1.size()) State=State+6;

			//superiorite numerique du groupe supporte dans la zone 3(0;12)
			if (support.soldierComputerInZone3.size()>support.soldierPlayerInZone3.size()) State=State+12;
		}

		//Batiment en train d'etre attaque (0;24)
		for (Soldier i : IA.player.soldiers){
			if (IA.computer.base.isCloseTo(i,Finals.ATTACK_RANGE)){
				State=24+State;
				break;
			}
		}

		// pas de groupe supporte (0) et taille du groupe supportee  (48;96)
		if (support !=null){
			if (support.unitGroup.group.size()<0.3*Finals.NUMBER_MAX_OF_SOLDIER)State=48+State;
			else State=96+State;
		}

		//taille de this (0;144;288)
		if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.2)State= 144+State;
		else if (unitGroup.numberUnit()<(double)Finals.NUMBER_MAX_OF_SIMPLEUNIT*0.08)State= 288+State;

		return State;
	}

	
	/**
	 * Choisit la strategie a adopter
	 * @param State etat dans lequel est la SU
	 */
	public int chooseStrategy(int State) {

		Double n = 0.0;
		//On calcule n, nombre de fois que State a eu lieu
		for(Double i :IA.nbSaveSU[State]) {
			n += i;
		}

		Double  sommeR=0.0;
		//On calcule sommeR, diviseur de la loi de probabilite
		for (Double x :IA.qIASimpleUnit[State]){
			sommeR = sommeR +Math.exp((n+1)/(101+n)*x);
		}

		Double Rdm= Math.random()*sommeR;
		int i =0;
		//On choisit la strategie avec une probablite de exp((n+1)/(101+n)*x)/SommeR
		for (Double x :IA.qIASimpleUnit[State]){
			i++;
			if (Rdm<Math.exp((n+1)/(101+n)*x)) return i;
			Rdm=Rdm-Math.exp((n+1)/(101+n)*x);
		}

		//en cas d'erreurs on applique la strategie 1 
		return 1;
	}


	/**
	 * @param strategy numero de la strategie a appliquer 
	 */
	public void applyStrategy(int strategy) {

		switch (strategy){

		//suivre le groupe supporte
		case 1:{ 

			if (support==null) unitGroup.setTarget(IA.computer.base.getCenter());
			else{
				Point2D pts1 = support.unitGroup.getPosition();
				Point2D pts2 = IA.computer.base.getCenter();
				unitGroup.setTarget(new Point2D.Double(pts1.getX()+(pts2.getX()-pts1.getX())*5/pts1.distance(pts2),pts1.getY()+(pts2.getY()-pts1.getY())*5/pts1.distance(pts2)));
			}
			break;
		}

		
		//Scinder le groupe en deux
		case 2:{  

			SimpleUnitGroup scission = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0),true);
			unitGroup.remove(unitGroup.group.get(0));
			scission.ia= new IASimpleUnit(scission);
			for (int i= unitGroup.group.size()-1; i>0;i--){ // lesser le for sous se format la (risque d'emerde avec le remouve )
				if ((i)%2==0) {
					scission.add((SimpleUnit)unitGroup.group.get(i));
					unitGroup.remove(unitGroup.group.get(i));
				} 
			}
			if (unitGroup.group.size()==0)SimpleUnitGroup.list.remove(unitGroup);
			break;
		}

		
		//Soutenir un autre group d'unite
		case 3:{ 

			//on cherche le groupe de Soldats le plus proche
			double distance =10000;
			SoldierGroup group= null;
			for( SoldierGroup sg :SoldierGroup.list){

				if (sg.distanceTo(unitGroup)<distance){
					group = sg;
					distance =sg.distanceTo(unitGroup);
				}
			}

			if (group!=null){// si il exsite un groupe de soldat proche 

				// si le groupe de soldat n'a pas de soutien, on cree un groupe de soutien dans lequel on place une unite simple
				if (group.ia.support == null){
					SimpleUnitGroup scission = new SimpleUnitGroup((SimpleUnit)unitGroup.group.get(0),true);
					unitGroup.remove(unitGroup.group.get(0)); 
					scission.ia.support=group.ia;
					group.ia.support=scission.ia;
				}
				// si il reste au moins une unite alors on fait motie moite avec les unites restantes.
				else  if (unitGroup.group.size()!=0)
					for (int i= unitGroup.group.size()-1; i>0;i--){ // lesser le if sous se format la (risque d'emerde avec le remouve )
						if ((i+1)%2==0) {
							group.ia.support.unitGroup.group.add((SimpleUnit)unitGroup.group.get(i));
							unitGroup.remove(unitGroup.group.get(i));
						}
					}
			}
			if (unitGroup.group.size()==0)SimpleUnitGroup.list.remove(unitGroup);
			break;
		}

		
		//Donne de la vie a la base
		case 4:{   
			unitGroup.setTarget(IA.computer.base);
			break;
		}

		
		//Creation Soldats
		case 5:{
			SimpleUnit.createSoldier(unitGroup.getPosition(), IA.computer, unitGroup.getPosition());
			break;
		} 


		//Strategie 1 appliquee au tour precedent : continue a suivre le groupe supporte 
		case 11: {           

			if (support==null) unitGroup.setTarget(IA.computer.base.getCenter());
			else{
				Point2D pts1 = support.unitGroup.getPosition();
				Point2D pts2 = Game.computer.base.getCenter();
				unitGroup.setTarget(new Point2D.Double(pts1.getX()+(pts2.getX()-pts1.getX())*5/pts1.distance(pts2),pts1.getY()+(pts2.getY()-pts1.getY())*5/pts1.distance(pts2)));
			}
			break;

		}

		
		//Starategie 5 appliquee au tour precedent : continue a creer des soldats
		case 15 : { 
			if (support==null)
				SimpleUnit.createSoldier(unitGroup.getPosition(), IA.computer, IA.computer.base.target);
			break;
		}          
		}
	}
}
