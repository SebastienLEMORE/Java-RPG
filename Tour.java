
import java.util.Random;


public class Tour {
	private int choix;
	private boolean evaluer=false;
	private boolean capituler;
	private Capacite capaciteIA;
	public Tour(){
		choix=5;
	}
	
	int prob=51;
	
	
	
	/**
	 * Attaquer
	 */
	public  boolean attaquer(Personnage j,Personnage jadverse,Capacite capacite){
		
		Random rand=new Random();
		int random;
		/**
		 * Attaque Reussie
		 */
			random=rand.nextInt(prob);
			if(random<=capacite.getPBA()*100){
				
				if(capacite instanceof Sort){
					capacite.setEFF(j.getCON());
				}
				else{
					capacite.setEFF(j.getFOR());
				}
				
				/**
				 * En cas devaluation au tour precedent
				 */
				if(evaluer){
					int EFF=capacite.getEFF();
					EFF=EFF-(EFF/4);
				}
				/**
				 * Choix du joueur adverse (Defense ou non)
				 */
				return true;
			}
		/**
		 * Attaque Ratee
		 */
			else{
				return false;
			}
	}
	/**
	 * Defense IA
	 */
	public boolean defendreIA(Personnage j, Personnage jadverse,Capacite capacite) {
			if(jadverse.isPossedeDefense()){
					for(int i=0;i<jadverse.getCapacite().length;i++)
						if(jadverse.getCapacite()[i] instanceof Bouclier || jadverse.getCapacite()[i] instanceof SortDefense || jadverse.getCapacite()[i] instanceof Arme)
							capaciteIA=jadverse.getCapacite()[i];
						
						Random rand=new Random();
					
						/**
					 	* Defense Reussie sans evalue
					 	*/
						double VIT;
						int eff;
						int random=rand.nextInt(prob);
						if(random<=capaciteIA.getPBA()*100){
							if(capaciteIA instanceof Sort){
								capaciteIA.setEFF(jadverse.getCON());
								eff=capaciteIA.getEFF();
							}
							else{
								capaciteIA.setEFFdefense(jadverse.getFOR());
								eff=capaciteIA.getEFFdefense();
							}
							/**
							 * Regarde si l'efficacite defense > efficacite attaque
						 	*/
							if(eff>capacite.getEFF())
								VIT=jadverse.getVIT();
							else
								VIT=jadverse.getVIT()+eff-capacite.getEFF();
						
							jadverse.setVIT(VIT);
						
							if(capaciteIA instanceof Bouclier && capacite instanceof Sort)
								capaciteIA.setBrise();
							else if(capaciteIA instanceof Arme && capacite instanceof Sort)
								capaciteIA.setBrise();
							return false;
						
						}
						/**
					 	* Defense Ratee sans evalue
					 	*/
						else{
							VIT=jadverse.getVIT()-capacite.getEFF();
							jadverse.setVIT(VIT);
							return true;
						}
			}
			else{
				double VIT=jadverse.getVIT()-capacite.getEFF();
				jadverse.setVIT(VIT);
			}
			return true;
		}
		

	/**
	 * Soigner
	 * 
	 */
	
	public boolean soigner(Personnage j,Capacite capacite){
		double VIT;
		int random;
		Random rand=new Random();
		random=rand.nextInt(prob);
		/**
		 * Soin reussi
		 */
		if(random<=capacite.getPBA()*100){
			if(capacite instanceof Sort){
				capacite.setEFF(j.getCON());
			}
			else{
				capacite.setEFF(j.getFOR());
			}
			VIT=j.getVIT()+capacite.getEFF();
			j.setVIT(VIT);
			choix=1;
			return true;
		}
		/**
		 * Soin rate
		 */
		else{
			choix=2;
			return false;
		}
	
	}
	/**
	 * Defendre
	 */
	
	public boolean defendre(Personnage j,Personnage jadverse,Capacite capacite){
		double VIT;	
		Random rand=new Random();
		int eff;
				/**
				 * Defense Reussie sans evalue
				 */
		if(!evaluer){
			int random=rand.nextInt(prob);
			if(random<=capacite.getPBA()*100){

				if(capacite instanceof Sort){
					capacite.setEFF(j.getCON());
					eff=capacite.getEFF();
				}
				else{
					capacite.setEFFdefense(j.getFOR());
					eff=capacite.getEFFdefense();
				}
				/**
				 * Regarde si l'efficacite defense > efficacite attaque
				 */
				if(eff>capaciteIA.getEFF())
					VIT=j.getVIT();
				else
					VIT=j.getVIT()+eff-capaciteIA.getEFF();
				j.setVIT(VIT);
				/**
				 * Arme Brise
				 */
					
				if(capacite instanceof Arme && capaciteIA instanceof Sort){
					capacite.setBrise();
				}
				else if(capacite instanceof Bouclier && capaciteIA instanceof Sort){
					capacite.setBrise();
				}
				return true;
			}
				/**
				 * Defense Ratee sans evalue
				 */
			else{
				VIT=j.getVIT()-capaciteIA.getEFF();
				j.setVIT(VIT);
				return false;
			}
		}
		/**
		 * evalue l'attaque
		 */
		else{
			int random=rand.nextInt(prob);
			/**
			* Defense Reussie
			*/
				if(random<=capacite.getPBA()*100){
					int penalite;
					if(capacite instanceof Sort){
						capacite.setEFF(j.getCON());
						penalite= capacite.getEFF()/4;
						eff=capacite.getEFF();
			
					}
					else{
						capacite.setEFFdefense(j.getFOR());
						penalite= capacite.getEFFdefense()/4;
						eff=capacite.getEFFdefense();
					}
					
					/**
					 * Regarde si l'efficacite defense - penalite > efficacite attaque
					 */
					if((eff-penalite)>capaciteIA.getEFF())
						VIT=j.getVIT();
					else
						VIT=j.getVIT()+(eff-penalite)-capaciteIA.getEFF();
					
					j.setVIT(VIT);
					/**
					* Arme Brise
					 */
					if(capacite instanceof Arme && capaciteIA instanceof Sort){
						capacite.setBrise();
					}
					else if(capacite instanceof Bouclier && capaciteIA instanceof Sort){
						capacite.setBrise();
					}
					return true;
				}
					/**
				 	* Rate Defense
				 	*/
					else{
						VIT=j.getVIT()-capaciteIA.getEFF();
						j.setVIT(VIT);
						return false;
					}
				}
	}
	
	
	public boolean tourIA(Personnage jadverse,Personnage j){
		double proba=(jadverse.getVIT()/jadverse.getMaxVIT())*100;
		choix=0;
		if(jadverse.isPossedeSoin()){
			if(proba>80){
				
				for(int i=0;i<jadverse.getCapacite().length;i++){
					if(jadverse.getCapacite()[i] instanceof Arme || jadverse.getCapacite()[i] instanceof SortAttaque){
						capaciteIA=jadverse.getCapacite()[i];
						break;
					}
				}
				if(attaquer(jadverse,j,capaciteIA)){
					return false;
				}
				else{
					return true;
				}		
			}
			else if(proba<80 && proba>50){
				Random rand=new Random();
				int random=rand.nextInt(prob);
				if(random<70){
					for(int i=0;i<jadverse.getCapacite().length;i++){
						if(jadverse.getCapacite()[i] instanceof Arme || jadverse.getCapacite()[i] instanceof SortAttaque){
							capaciteIA=jadverse.getCapacite()[i];
							break;
						}
					}
					if(attaquer(jadverse,j,capaciteIA)){
						return false;
					}
					else{
						return true;
					}	
				}
				else{
					if(jadverse.getCapacite()[0] instanceof Remede || jadverse.getCapacite()[0] instanceof SortSoin)
						soigner(jadverse,jadverse.getCapacite()[0]);
					else
						soigner(jadverse,jadverse.getCapacite()[1]);
					return true;
				}		
				
			}
			else if(proba<50 && proba>20){
				Random rand=new Random();
				int random=rand.nextInt(prob);
				if(random<40){
					for(int i=0;i<jadverse.getCapacite().length;i++){
						if(jadverse.getCapacite()[i] instanceof Arme || jadverse.getCapacite()[i] instanceof SortAttaque){
							capaciteIA=jadverse.getCapacite()[i];
							break;
						}
					}
					if(attaquer(jadverse,j,capaciteIA)){
						return false;
					}
					else{
						return true;
					}	
				}
				else{
					if(jadverse.getCapacite()[0] instanceof Remede || jadverse.getCapacite()[0] instanceof SortSoin)
						soigner(jadverse,jadverse.getCapacite()[0]);
					else
						soigner(jadverse,jadverse.getCapacite()[1]);
					return true;
				}
			}
			else{
				if(jadverse.getCapacite()[0] instanceof Remede || jadverse.getCapacite()[0] instanceof SortSoin)
					soigner(jadverse,jadverse.getCapacite()[0]);
				else
					soigner(jadverse,jadverse.getCapacite()[1]);
				return true;
			}
		}
		else{
			for(int i=0;i<jadverse.getCapacite().length;i++){
				if(jadverse.getCapacite()[i] instanceof Arme || jadverse.getCapacite()[i] instanceof SortAttaque){
					if(attaquer(jadverse,j,jadverse.getCapacite()[i])){
						capaciteIA=jadverse.getCapacite()[i];
						return false;
					}
					else{
						return true;
					}
				}	
			}
		}
		return true;
	}
	public void degatsansdefense(Personnage J1){
		double VIT;
		VIT=J1.getVIT()-capaciteIA.getEFF();
		J1.setVIT(VIT);
	}
	public void degatsansdefense(Personnage IA,Capacite capacite){
		double VIT;
		VIT=IA.getVIT()-capacite.getEFF();
		IA.setVIT(VIT);
	}



	public boolean isCapituler() {
		return capituler;
	}
	public void setEvaluer(boolean evaluer){
		this.evaluer=evaluer;
	}
	public void setCapituler(boolean capituler){
		this.capituler=capituler;
	}
	public int getChoix(){
		return choix;
	}
	
	public int getEFF(){
		return capaciteIA.getEFF();
	}
}

	
