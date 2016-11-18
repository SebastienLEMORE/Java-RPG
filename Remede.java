
public class Remede extends Capacite{
	private String nom;
	private int PUI;
	private int FAC;
	private double PBA;
	private int EFF;
	
	public Remede(String nom,int PUI, int FAC){
		this.nom=nom;
		this.setPUI(PUI);
		this.setFAC(FAC);
		this.EFF=0;
		this.PBA=0;
	}
	
	public Remede (Remede remede){
		this.nom=new String(remede.nom);
		this.setPUI(remede.PUI);
		this.setFAC(remede.FAC);
		this.EFF=remede.EFF;
		this.PBA=remede.PBA;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPUI() {
		return PUI;
	}

	public void setPUI(int pUI) {
		PUI = pUI;
	}

	public int getFAC() {
		return FAC;
	}

	public void setFAC(int fAC) {
		FAC = fAC;
	}
	
	
	public String toString(){
		String s=this.nom+"<br>";
		s=s+"Puissance : "+this.PUI+"<br>";
		s=s+"Facilite : "+this.FAC+"<br>";
		if(getPBA()!=0){
			s=s+"Probabilite de reussite : "+getPBA()+"<br>";
		}
		return s;
	}

	public int getEFF() {
		return EFF;
	}

	public void setEFF(int FOR) {
		EFF = (FOR*PUI);
		EFF=EFF/100;
	}

	public double getPBA() {
		return PBA;
	}

	public void setPBA(int DEX) {
		PBA = (DEX*FAC);
		this.PBA=this.PBA/10000;
		
	}
	
	

	
}
