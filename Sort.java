
public class Sort extends Capacite{
	private String nom;
	private int PUI;
	private int FAC;
	private double PBA;
	private int EFF;
	
	
	public Sort(String nom, int PUI, int FAC){
		this.nom=nom;
		this.PUI=PUI;
		this.FAC=FAC;
		this.PBA=0;
		this.EFF=0;
		
	}
	
	public Sort (Sort sort){
		this.nom=new String(sort.nom);
		this.PUI=sort.PUI;
		this.FAC=sort.FAC;
		this.EFF=sort.EFF;
		this.PBA=sort.PBA;
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
	public void setPBA(int INT){
		this.PBA=(INT*FAC);
		this.PBA=this.PBA/10000;
	}
	public void setEFF(int CON){
		this.EFF=(CON*PUI);
		this.EFF=this.EFF/100;
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

	public double getPBA(){
		return PBA;
	}

	public int getEFF(){
		return this.EFF;
	}
}
