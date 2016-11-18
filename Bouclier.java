
public class Bouclier extends Capacite{
	private String nom;
	private int PRO;
	private int MAN;
	private double PBAdefense;
	private int EFFdefense;
	private double PBA;
	private int EFF;
	
	private int brise=0;
	
	

	public Bouclier(String nom, int PRO,int MAN){
		this.nom=nom;
		this.PRO=PRO;
		this.MAN=MAN;
		this.setEFF(0);
		this.setPBA(0);
	}
	
	public Bouclier(Bouclier bou){
		this.nom=new String(bou.nom);
		this.PRO=bou.PRO;
		this.MAN=bou.MAN;
		this.EFF=bou.EFF;
		this.PBA=bou.PBA;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPRO() {
		return PRO;
	}

	public void setPRO(int pRO) {
		PRO = pRO;
	}

	public int getMAN() {
		return MAN;
	}

	public void setMAN(int mAN) {
		MAN = mAN;
	}
	
	
	
	public double getPBAdefense() {
		return PBAdefense;
	}

	public void setPBAdefense(int DEX) {
		PBAdefense = (DEX*MAN);
		this.PBAdefense=this.PBAdefense/10000;
	}

	public int getEFFdefense() {
		return EFFdefense;
	}

	public void setEFFdefense(int FOR) {
		
		EFFdefense = (FOR*PRO);
		EFFdefense=EFFdefense/100;
		if(brise>0){
			EFFdefense=EFFdefense/(brise*2);
		}
		
	}

	public void setPBA(int DEX){
		this.PBA=(DEX*MAN);
		this.PBA=this.PBA/5000;
	}
	
	public void setEFF(int FOR){
		this.EFF=(FOR*PRO);
		this.EFF=this.EFF/50;
		if(brise>0){
			EFF=EFF/(brise*2);
		}
	}
	public void setBrise(){
		brise++;
	}
	
	public String toString(){
		String s=this.nom+"<br>";
		s=s+"Protection : "+this.PRO+"<br>";
		s=s+"Maniabilite : "+this.MAN+"<br>";
		if(getPBA()!=0){
		s=s+"Probabilite de reussite d'attaque : "+getPBA()+"<br>";
		s=s+"Probabilite de reussite de defense : "+getPBAdefense()+"<br>";
		}
		return s;
	}

	public double getPBA() {
		return PBA;
	}


	public int getEFF() {
		return EFF;
	}

}

