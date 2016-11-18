import java.io.Serializable;

public class Personnage implements Serializable{
	 private String nom;
	 private double VIT=103;
	 private int FOR;
	 private int INT;
	 private int DEX;
	 private int CON;
	 private int EXP;
	 private Capacite [] capacite;
	 private  double maxVIT;
	 private boolean possedeAttaque;
	 private boolean possedeDefense;
	 private boolean possedeSoin;
	 
	 public static final Arme [] listArme=getlistArme();
	 public static final Bouclier [] listBouclier=getlistBouclier();
	 public static final Remede [] listRemede=getlistRemede();
	 public static final SortAttaque[] listAttaque=getlistAttaque();
	 public static final SortDefense[] listDefense=getlistDefense();
	 public static final SortSoin[] listSoin=getlistSoin();
	 public static final Personnage[] listPersonnage=getlistPersonnage();

	 private static Arme[] getlistArme() {
			Arme [] listArme;
			listArme=new Arme[8];
			listArme[0]=new Arme("Hallebarde",60,40);
			listArme[1]=new Arme("Dague",20,80);
			listArme[2]=new Arme("Espadon",60,40);
			listArme[3]=new Arme("Hache",50,50);
			listArme[4]=new Arme("epee",50,50);
			listArme[5]=new Arme("Katana",40,60);
			listArme[6]=new Arme("Arbalete",50,50);
			listArme[7]=new Arme("Arc",40,60);
			return listArme;
		}
	
	 private static Personnage[] getlistPersonnage() {
		 Personnage [] listPersonnage=new Personnage[6];
		
		 Capacite [] cap1=new Capacite[2];
		 cap1[0]=listArme[0];
		 cap1[1]=listBouclier[0];
		 listPersonnage[0]=new Guerrier("Rage",61,40,0,0,cap1);
		 
		 Capacite [] cap2=new Capacite[2];
		 cap2[0]=listArme[1];
		 cap2[1]=listRemede[0];
		 listPersonnage[1]=new Guerrier("Rog",56,45,0,0,cap2);
		 
		 Capacite [] cap3=new Capacite[2];
		 cap3[0]=listAttaque[0];
		 cap3[1]=listDefense[0];
		 listPersonnage[2]=new Mage("Antonidas",0,0,51,50,cap3);
		 
		 Capacite [] cap4=new Capacite[2];
		 cap4[0]=listAttaque[2];
		 cap4[1]=listSoin[0];
		 listPersonnage[3]=new Mage("Rhonin",0,0,40,61,cap4);
		 
		 Capacite [] cap5=new Capacite[2];
		 cap5[0]=listArme[6];
		 cap5[1]=listSoin[0];
		 listPersonnage[4]=new Chasseur("Sylvanas",31,30,20,20,cap5);
		
		 Capacite [] cap6=new Capacite[2];
		 cap6[0]=listBouclier[2];
		 cap6[1]=listAttaque[1];
		 listPersonnage[5]=new Chasseur("Vol'jin",20,20,30,31,cap6);
		 
		return listPersonnage;
	}

	public static Bouclier[] getlistBouclier(){
		 Bouclier [] listBouclier;
		 listBouclier=new Bouclier[4];
		 listBouclier[0]=new Bouclier("Pavoi",30,70);
		 listBouclier[1]=new Bouclier("ecu",60,40);
		 listBouclier[2]=new Bouclier("Targe en cuir",20,80);
		 listBouclier[3]=new Bouclier("Bouclier du roi",50,50);
		 
		 return listBouclier;	 
	 }
		
	 private static Remede [] getlistRemede(){
			Remede [] listRemede;
			listRemede=new Remede[3];
			listRemede[0]=new Remede("Petite Potion",20,80);
			listRemede[1]=new Remede("Moyenne Potion",40,60);
			listRemede[2]=new Remede("Grande Potion",50,50);
			
			return listRemede;			
		}
		
		private static SortAttaque[] getlistAttaque() {
			SortAttaque [] listAttaque;
			listAttaque=new SortAttaque[6];
			listAttaque[0]=new SortAttaque("Boule de feu",60,40);
			listAttaque[1]=new SortAttaque("eclair de givre",40,60);
			listAttaque[2]=new SortAttaque("Projectile des Arcanes",20,80);
			listAttaque[3]=new SortAttaque("eclat stellaire",70,30);
			listAttaque[4]=new SortAttaque("eclair de givrefeu",80,20);
			listAttaque[5]=new SortAttaque("Colere",30,70);
			
			return listAttaque;
		}
		
		private static SortDefense[] getlistDefense() {
			SortDefense [] listDefense;
			listDefense=new SortDefense[3];
			listDefense[0]=new SortDefense("Barriere de Glace",50,50);
			listDefense[1]=new SortDefense("ecorcefer",40,60);
			listDefense[2]=new SortDefense("Mot de pouvoir : Bouclier",60,40);
			
			return listDefense;
		}
		
		private static SortSoin[] getlistSoin(){
			SortSoin [] listSoin;
			listSoin=new SortSoin[3];
			listSoin[0]=new SortSoin("Soin rapide",30,70);
			listSoin[1]=new SortSoin("Soin",50,50);
			listSoin[2]=new SortSoin("Soin Majeur",60,40);
			return listSoin;
		}
	 
	 
	 public Personnage(){
		 this.setNom("James");
		 this.setFOR(0);
		 this.setDEX(0);
		 this.setINT(0);
		 this.setCON(0);
		 this.capacite=new Capacite[2];
		 this.capacite[0]=listAttaque[0];
		 this.capacite[1]=listArme[0];
		 this.setEXP(1);
		 VIT=103;
		 setMaxVIT(VIT);
	 }
	 public Personnage(String nom,int FOR,int DEX,int INT,int CON,Capacite [] capacite){
		 this.setNom(nom);
		 
		 this.setFOR(FOR);
		 this.setDEX(DEX);
		 this.setINT(INT);
		 this.setCON(CON);
		 this.setCapacite(capacite);
		 this.setEXP(1);
		 this.maxVIT=200-(getFOR()+getDEX()+getINT()+getCON())+(getEXP()*3);
		 this.VIT=this.maxVIT;
		 this.checkcapacite();
	 }
	public Personnage (Personnage pers){
		this.nom=new String(pers.getNom());
		this.FOR=pers.getFOR();
		this.DEX=pers.getDEX();
		this.INT=pers.getINT();
		this.CON=pers.getCON();
		this.EXP=pers.getEXP();
		this.possedeAttaque=pers.isPossedeAttaque();
		this.possedeDefense=pers.isPossedeDefense();
		this.possedeSoin=pers.isPossedeSoin();
		this.maxVIT=pers.getMaxVIT();
		this.VIT=pers.getVIT();
		this.capacite=new Capacite[pers.getCapacite().length];
		for(int i=0;i<pers.getCapacite().length;i++){
			if(pers.getCapacite()[i] instanceof Arme){
				this.capacite[i]=new Arme((Arme) pers.getCapacite()[i]);
			}
			else if(pers.getCapacite()[i] instanceof Bouclier){
				this.capacite[i]=new Bouclier((Bouclier) pers.getCapacite()[i]);
			}
			else if(pers.getCapacite()[i] instanceof Remede){
				this.capacite[i]=new Remede((Remede) pers.getCapacite()[i]);
			}
			else if(pers.getCapacite()[i] instanceof SortAttaque){
				this.capacite[i]=new SortAttaque((SortAttaque) pers.getCapacite()[i]);
			}
			else if(pers.getCapacite()[i] instanceof SortDefense){
				this.capacite[i]=new SortDefense((SortDefense) pers.getCapacite()[i]);
			}
			else if(pers.getCapacite()[i] instanceof SortSoin){
				this.capacite[i]=new SortSoin((SortSoin) pers.getCapacite()[i]);
			}
		}
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getVIT() {
		return VIT;
	}
	public void setVIT(double vIT) {
		VIT = vIT;
		if(VIT>maxVIT)
			this.VIT=maxVIT;
		else if(VIT<0)
			this.VIT=0;
	}
	public int getFOR() {
		return FOR;
	}
	public void setFOR(int fOR) {
		FOR = fOR;
	}
	public int getINT() {
		return INT;
	}
	public void setINT(int iNT) {
		INT = iNT;
	}
	public int getDEX() {
		return DEX;
	}
	public void setDEX(int dEX) {
		DEX = dEX;
	}
	public int getCON() {
		return CON;
	}
	public void setCON(int cON) {
		CON = cON;
	}
	public int getEXP() {
		return EXP;
	}
	public void setEXP(int eXP) {
		EXP = eXP;
	}
	
	public Capacite [] getCapacite() {
		return capacite;
	}
	public void setCapacite(Capacite[] capacite){
		this.capacite=capacite;
	}
	public void setCapacite(Capacite capacite,int i) {		
		this.capacite[i] = capacite;
	}
	
	public int sum(int i) {
		return i;
	}
	public int max(){
		int max;
		if(getFOR()>getDEX()){
			max=getFOR();
		}
		else{
			max=getDEX();
		}
		return max;
			
	}
	
	public String toString(){
		String s="<html>Nom : "+this.nom+" Nv."+this.EXP;
		s=s+"<br>Vitalite : "+this.VIT;
		s=s+"<br>Force : "+this.FOR;
		s=s+"<br>Dexterite : "+this.DEX;
		s=s+"<br>Intelligence : "+this.INT;
		s=s+"<br>Concentration :"+this.CON+"<br>";
		for(int i=0;i<capacite.length;i++)
			s=s+"<br>"+this.capacite[i];
		
		s=s+"</html>";
		return s;
	}

	public double getMaxVIT() {
		return maxVIT;
	}

	public void setMaxVIT(double maxVIT) {
		this.maxVIT = maxVIT;
	}
	public void checkcapacite(){
		for(int i=0;i<capacite.length;i++){
			if(capacite[i] instanceof Arme || capacite[i] instanceof Bouclier || capacite[i] instanceof SortAttaque)
				possedeAttaque=true;
			if(capacite[i] instanceof Arme || capacite[i] instanceof Bouclier || capacite[i] instanceof SortDefense)
				possedeDefense=true;
			if(capacite[i] instanceof Remede || capacite[i] instanceof SortSoin)
				possedeSoin=true;
		}
	}
	public void setupPBA(){
		for(int i=0;i<getCapacite().length;i++){
			if(getCapacite()[i] instanceof Arme || getCapacite()[i] instanceof Bouclier){
				getCapacite()[i].setPBA(getDEX());
				getCapacite()[i].setPBAdefense(getDEX());
			}
			else if(getCapacite()[i] instanceof Sort)
				getCapacite()[i].setPBA(getINT());
			else
				getCapacite()[i].setPBA(getDEX());
		}
	}
	
	public boolean isPossedeAttaque() {
		return possedeAttaque;
	}



	public boolean isPossedeDefense() {
		return possedeDefense;
	}



	public boolean isPossedeSoin() {
		return possedeSoin;
	}
}
