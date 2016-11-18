
public class Arme extends Capacite{
	private String nom;
	private int IMP;
	private int MAN;
	private double PBAdefense;
	private int EFFdefense;
	private double PBA;
	private int EFF;
	
	private int brise=0;
	
	public Arme(String nom, int IMP,int MAN){
		this.nom=nom;
		this.IMP=IMP;
		this.MAN=MAN;
		this.setEFF(0);
		this.setPBA(0);
	}
	
	
	
	public Arme(Arme a){
		this.nom=new String(a.nom);
		this.setIMP(a.getIMP());
		this.setMAN(a.getMAN());
		this.EFF=a.EFF;
		this.PBA=a.PBA;
	}
	
	public int getIMP() {
		return IMP;
	}

	public void setIMP(int iMP) {
		IMP = iMP;
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
		PBAdefense=PBAdefense/5000;
	}

	public int getEFFdefense() {
		return EFFdefense;
	}

	public void setEFFdefense(int FOR) {
		EFFdefense = (FOR*IMP);
		EFFdefense=EFFdefense/50;
		if(brise>0){
			EFFdefense=EFFdefense/(brise*3);
		}
	}

	public void setPBA(int DEX){
		this.PBA=(DEX*MAN);
		this.PBA=this.PBA/10000;
	}
	
	public void setEFF(int FOR){
		this.EFF=(FOR*IMP);
		this.EFF=this.EFF/100;
		if(brise>0){
			EFF=EFF/(brise*3);
		}
	}
	public  void setBrise(){
		brise++;
	}
	
	public String toString(){
		String s=this.nom+"<br>";
		s=s+"Impact : "+this.IMP+"<br>";
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
