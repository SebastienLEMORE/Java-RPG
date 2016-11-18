import java.awt.*;

import java.awt.event.*;
import java.util.Random;

import javax.swing.*;



public class FenJeu extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Personnage J1=new Personnage();
	private Personnage J2;
	
	private JTextArea consigne=new JTextArea(1,50);;
	
	private boolean newPerso;
	private boolean up;
	private JTextField pointattribue=new JTextField(5);
	{
		pointattribue.setText("");
		pointattribue.setEnabled(false);
	}
	private JTextField nom;
	private JLabel vit=new JLabel("");
	private JLabel force=new JLabel("");
	private JLabel dex=new JLabel("");
	private JLabel con=new JLabel("");
	private JLabel intel=new JLabel("");
	
	private Tour Jeu;
	private JLabel nbtour;
	private Capacite capacitechoix;
	private JTextField tPVJ1;
	private JTextField tPVJ2;
	private int tournum=0;
	
	private Sauvegarde save;
	
	private JPanel panCentre=new JPanel();
	private JPanel panNord=new JPanel();
	private JPanel panSud=new JPanel();
	/**
	 * INITIALISATION DE LA FENETRE
	 */
	public FenJeu(String titre,int w,int h){
		super(titre);
		this.initialise();
		this.Jeu=new Tour();
		this.save=new Sauvegarde();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.dimensionne(w,h);
		this.setVisible(true);		
	}
	public void dimensionne(int w,int h){
		Toolkit aTK= Toolkit.getDefaultToolkit();
		Dimension dim=aTK.getScreenSize();
		int x=(dim.width-w)/2;
		int y=(dim.height-h)/2;
		this.setBounds(x, y, w, h);
	}
	public void initialise(){
		
		this.add(this.getPanelNord(),BorderLayout.NORTH);
		this.add(this.getPanelCentre(),BorderLayout.CENTER);
		this.add(this.getPanelSud(),BorderLayout.SOUTH);
	}
	public JPanel getPanelNord(){
		
		
		panNord.setBorder(BorderFactory.createCompoundBorder(
	       		 BorderFactory.createTitledBorder("Joueur"),
	       		 BorderFactory.createEmptyBorder(20,10,3,2)
	       		 ));
		return panNord;
	}
	public void menudebut(){
		JButton nouvelle=new JButton("Nouvelle Partie");
		panCentre.add(nouvelle);
		nouvelle.addActionListener(new EcouteurDebut());
		JButton charger=new JButton("Charger Partie");
		panCentre.add(charger);
		charger.addActionListener(new EcouteurDebut());
		JButton quitter=new JButton("Quitter");
		quitter.addActionListener(new EcouteurFin());
		panCentre.add(quitter);
		
	}
	public JPanel getPanelCentre(){
		menudebut();
		panCentre.setBorder(BorderFactory.createCompoundBorder(
	       		 BorderFactory.createTitledBorder("Jeu"),
	       		 BorderFactory.createEmptyBorder(20,10,3,2)
	       		 ));
		return panCentre;
	}
	public JPanel getPanelSud(){
		consigne.setEditable(false);
		consigne.setBackground(Color.BLACK);
		consigne.setForeground(Color.WHITE);
		panSud.setBorder(BorderFactory.createCompoundBorder(
	       		 BorderFactory.createTitledBorder("Instruction"),
	       		 BorderFactory.createEmptyBorder(20,10,3,2)
	       		 ));
		panSud.add(consigne);
		consigne.setText("Que voulez-vous faire?");
		return panSud;
	}
	/**
	 * CALCUL POINT RESTANT
	 */
	public int pointrestant(){
		int point=100+J1.getEXP();
		point=point-J1.getCON()-J1.getDEX()-J1.getFOR()-J1.getINT();
		point=(int) (point+(100+J1.getEXP()*3)-J1.getVIT());
		return point;
	}
	/**
	 * CONDITION CARAC
	 */
	public void caracguerrier(){
		if(J1.getFOR()+J1.getDEX()+J1.getINT()+J1.getCON()>100+J1.getEXP() || J1.getFOR()<J1.getDEX()+10 || J1.getDEX()+10<J1.getINT()+10 || J1.getINT()+10<J1.getCON())
			consigne.setForeground(Color.RED);
		else{
			if(pointrestant()==0){
				
				
				panNord.removeAll();
				panNord.repaint();
				panNord.revalidate();
				
				consigne.setText("");
				
				if(newPerso){
					newPerso=false;
					capaciteguerrier();
				}
				else if(J1.getCapacite().length<J1.getEXP()/2 && (J1.getEXP()/2)%2==0){
					tournum=J1.getCapacite().length-1;
					initboutoncapaciteguerrier();
				}
				else if(Jeu.isCapituler()){
					if(J1.getCapacite().length>J1.getEXP()/2)
						supprcapacite();
				}
				else{
					menu();
				}
			}
			else
				consigne.setForeground(Color.GREEN);
		}
	}
	
	
	public void caracchasseur(){
		if(J1.getFOR()+J1.getDEX()+J1.getINT()+J1.getCON()>100+J1.getEXP() || J1.getFOR()<20 || J1.getDEX()<20 || J1.getINT()<20 || J1.getCON()<20){
			consigne.setForeground(Color.RED);
		}
		else{
			if(pointrestant()==0){
				
				
				panNord.removeAll();
				panNord.repaint();
				panNord.revalidate();
				
				consigne.setText("");
				
				if(newPerso){
					newPerso=false;
					capacitechasseur();
				}
				else if(J1.getCapacite().length<J1.getEXP()/2 && (J1.getEXP()/2)%2==0){
					tournum=J1.getCapacite().length-1;
					initboutoncapacitechasseur();
				}
				else if(Jeu.isCapituler()){
					if(J1.getCapacite().length>J1.getEXP()/2)
						supprcapacite();
				}
				else{
					menu();
				}
				
			}
			else
				consigne.setForeground(Color.GREEN);
		}
	}
	
	
	public void caracmage(){
		
		if(J1.getFOR()+J1.getDEX()+J1.getINT()+J1.getCON()>100+J1.getEXP() || J1.getINT()<J1.max()+15 || J1.getCON()<J1.max()+15){
			consigne.setForeground(Color.RED);
		}
		else{
			if(pointrestant()==0){		
				panNord.removeAll();
				panNord.repaint();
				panNord.revalidate();
				
				consigne.setText("");
					
				if(newPerso){
					newPerso=false;
					capacitemage();
				}
				else if(J1.getCapacite().length<J1.getEXP()/2 && (J1.getEXP()/2)%2==0){
					tournum=J1.getCapacite().length-1;
					initboutoncapacitemage();
				}
				else if(Jeu.isCapituler()){
					if(J1.getCapacite().length>J1.getEXP()/2)
						supprcapacite();
				}
					
				else{
					menu();
				}
			}

			else
				consigne.setForeground(Color.GREEN);
	
		}
	}
	/**
	 * BOUTON CHOIX CAPACITE 
	 */
	public void initboutoncapaciteguerrier(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		
		JButton arme=new JButton("Arme");
		JButton bouclier=new JButton("Bouclier");
		JButton remede=new JButton("Remede");
		
		arme.addActionListener(new EcouteurCapacite());
		bouclier.addActionListener(new EcouteurCapacite());
		remede.addActionListener(new EcouteurCapacite());
		
		panCentre.add(arme);
		panCentre.add(bouclier);
		panCentre.add(remede);
		
		consigne.setText("Capacite "+tournum);
	}
	public void initboutoncapacitechasseur(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		
		JButton arme=new JButton("Arme");
		JButton bouclier=new JButton("Bouclier");
		JButton remede=new JButton("Remede");
		
		arme.addActionListener(new EcouteurCapacite());
		bouclier.addActionListener(new EcouteurCapacite());
		remede.addActionListener(new EcouteurCapacite());
		
		panCentre.add(arme);
		panCentre.add(bouclier);
		panCentre.add(remede);
		
		
		JButton sortattaque=new JButton("SortAttaque");
		JButton sortdefense=new JButton("SortDefense");
		JButton sortsoin=new JButton("SortSoin");
		
		sortattaque.addActionListener(new EcouteurCapacite());
		sortdefense.addActionListener(new EcouteurCapacite());
		sortsoin.addActionListener(new EcouteurCapacite());
		
		panCentre.add(sortattaque);
		panCentre.add(sortdefense);
		panCentre.add(sortsoin);
		
		consigne.setText("Capacite "+tournum);
	}
	public void initboutoncapacitemage(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		
		JButton sortattaque=new JButton("SortAttaque");
		JButton sortdefense=new JButton("SortDefense");
		JButton sortsoin=new JButton("SortSoin");
		
		sortattaque.addActionListener(new EcouteurCapacite());
		sortdefense.addActionListener(new EcouteurCapacite());
		sortsoin.addActionListener(new EcouteurCapacite());
		
		panCentre.add(sortattaque);
		panCentre.add(sortdefense);
		panCentre.add(sortsoin);
		
		consigne.setText("Capacite "+tournum);
	}
	public void capaciteguerrier(){
		if(tournum<2){
			initboutoncapaciteguerrier();
		}
		else{
			menu();
		}
	}
	public void capacitechasseur(){
		if(tournum<2){
			initboutoncapacitechasseur();
		}
		else{
			menu();
		}
	}
	
	public void capacitemage(){
		if(tournum<2){
			initboutoncapacitemage();
		}
		else{
			menu();
		}
	}
	public void supprcapacite(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		consigne.setText("Quelle Capacite voulez vous retirer");
		for(int i=0;i<J1.getCapacite().length;i++)
			switch(i){
				default : JButton capj1=new JButton("<html>"+J1.getCapacite()[i].toString()+"</html>");
				capj1.addActionListener(new EcouteurSupprCapacite());
				panCentre.add(capj1);
			}
	}
	/**
	 * BOUTON VIT,FOR,DEX,CON,INT
	 */
	public void boutonCarac(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		
		panNord.removeAll();
		panNord.repaint();
		panNord.revalidate();
		JPanel panPoint=new JPanel(new GridLayout(1,2,2,2));
		JLabel point=new JLabel("Points restant :");
		pointattribue.setText(Integer.toString(pointrestant()));
		panPoint.add(point);
		panPoint.add(pointattribue);
		panNord.add(panPoint);
		
		JPanel panVit=new JPanel(new GridLayout(3,1,2,2)); 
		vit.setText("Vitalite : "+Double.toString(J1.getVIT()));
		panVit.add(vit);
		JButton plusvit=new JButton("+");
		plusvit.addActionListener(new EcouteurVIT());
		JButton moinsvit=new JButton("-");
		moinsvit.addActionListener(new EcouteurVIT());
		panVit.add(vit);
		panVit.add(plusvit);
		panVit.add(moinsvit);
		panCentre.add(panVit);
		
		JPanel panFor=new JPanel(new GridLayout(3,1,2,2)); 
		force.setText("Force : "+Integer.toString(J1.getFOR()));
		panFor.add(force);
		JButton plusfor=new JButton("+");
		plusfor.addActionListener(new EcouteurFOR());
		JButton moinsfor=new JButton("-");
		moinsfor.addActionListener(new EcouteurFOR());
		panFor.add(force);
		panFor.add(plusfor);
		panFor.add(moinsfor);
		panCentre.add(panFor);
		
		JPanel panDex=new JPanel(new GridLayout(3,1,2,2)); 
		dex.setText("Dexterite : "+Integer.toString(J1.getDEX()));
		panDex.add(dex);
		JButton plusdex=new JButton("+");
		plusdex.addActionListener(new EcouteurDEX());
		JButton moinsdex=new JButton("-");
		moinsdex.addActionListener(new EcouteurDEX());
		panDex.add(dex);
		panDex.add(plusdex);
		panDex.add(moinsdex);
		panCentre.add(panDex);
		
		JPanel panCon=new JPanel(new GridLayout(3,1,2,2)); 
		con.setText("Concentration : "+Integer.toString(J1.getCON()));
		panCon.add(con);
		JButton pluscon=new JButton("+");
		pluscon.addActionListener(new EcouteurCON());
		JButton moinscon=new JButton("-");
		moinscon.addActionListener(new EcouteurCON());
		panCon.add(con);
		panCon.add(pluscon);
		panCon.add(moinscon);
		panCentre.add(panCon);
		
		JPanel panInt=new JPanel(new GridLayout(3,1,2,2)); 
		intel.setText("Intelligence : "+Integer.toString(J1.getINT()));
		panInt.add(intel);
		JButton plusint=new JButton("+");
		plusint.addActionListener(new EcouteurINT());
		JButton moinsint=new JButton("-");
		moinsint.addActionListener(new EcouteurINT());
		panInt.add(intel);
		panInt.add(plusint);
		panInt.add(moinsint);
		panCentre.add(panInt);
		if(Jeu.isCapituler()){
			plusvit.setEnabled(false);
			plusfor.setEnabled(false);
			plusdex.setEnabled(false);
			plusint.setEnabled(false);
			pluscon.setEnabled(false);
		}
		else if(up){
			moinsvit.setEnabled(false);
			moinsfor.setEnabled(false);
			moinsdex.setEnabled(false);
			moinsint.setEnabled(false);
			moinscon.setEnabled(false);
		}
	}
	/**
	 * SETUP COMBAT
	 */
	public void setupcombat(){
		tournum=0;
		up=false;
		Jeu.setCapituler(false);
		Random rand=new Random();
		
		J2=new Personnage(Personnage.listPersonnage[rand.nextInt(6)]);
		
		JPanel panj2=new JPanel(new GridLayout(2,1,2,2));
		panj2.add(new JLabel(J2.getNom()+" nv."+J2.getEXP()));
		this.tPVJ2=new JTextField(10);
		this.tPVJ2.setEditable(false);
		this.tPVJ2.setText("PV : "+J2.getVIT());
		panj2.add(tPVJ2);

		panNord.add(panj2);
		
		nbtour=new JLabel("Tour "+tournum);
		nbtour.setLayout(new FlowLayout());
		consigne.setText("Choissisez votre action");
		
		panSud.removeAll();
		panSud.repaint();
		panSud.revalidate();
		panSud.add(nbtour);
		panSud.add(consigne);
		
		J1.setupPBA();
		J2.setupPBA();
		
		J1.checkcapacite();
		J2.checkcapacite();
		consigne.setForeground(Color.WHITE);
		combat();
	}
	
	/**
	 * COMBAT
	 */
	
	public void combat(){
		this.tPVJ1.setText("PV : "+J1.getVIT());
		
		this.tPVJ2.setText("PV : "+J2.getVIT());
		if(J1.getVIT()>0 && J2.getVIT()>0 && !(Jeu.isCapituler())){
			nbtour.setText("Tour "+tournum);
			if(tournum%2==0){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				
				JButton attaque=new JButton("Attaque");
				attaque.addActionListener(new EcouteurCombat());
				JButton soin=new JButton("Soin");
				soin.addActionListener(new EcouteurCombat());
				JButton capituler=new JButton("Capituler");
				capituler.addActionListener(new EcouteurCombat());
				
				if(!(J1.isPossedeSoin()) || J1.getVIT()==J1.getMaxVIT())
						soin.setEnabled(false);
				else
					soin.setEnabled(true);
				
				panCentre.add(attaque);
				panCentre.add(soin);
				panCentre.add(capituler);
			}
			else{	
				if(Jeu.tourIA(J2,J1)){
					tournum++;
					if(Jeu.getChoix()==1)
						consigne.setText(consigne.getText()+"   J2 : Soin Reussi");
					else if(Jeu.getChoix()==2)
						consigne.setText(consigne.getText()+"   J2 : Soin Rate");
					else
						consigne.setText(consigne.getText()+"   J2 : Attaque Ratee");
					combat();
				}
				else{
					consigne.setText(consigne.getText()+"   J2 :Attaque Reussie");
					if(J1.isPossedeDefense()){
						Jeu.setEvaluer(false);
						panCentre.removeAll();
						panCentre.repaint();
						panCentre.revalidate();
					
						JButton defendre=new JButton("Defendre");
						JButton evaluer=new JButton("Evaluer l'attaque");
						consigne.setText("L'Evaluation reduira votre defense de 1/4");
						JButton encaisser=new JButton("Encaisser");
						defendre.addActionListener(new EcouteurDefendre());
						evaluer.addActionListener(new EcouteurDefendre());
						encaisser.addActionListener(new EcouteurDefendre());
					
						panCentre.add(defendre);
						panCentre.add(evaluer);
						panCentre.add(encaisser);
					}
					else{
						tournum++;
						Jeu.degatsansdefense(J1);
						combat();
					}
						
				}
			}
		}
		else{
			if(Jeu.isCapituler()){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				
				if(J1.getEXP()>1){
					if(J1 instanceof Guerrier){
						
						consigne.setText("FOR>=(DEX+10)>=(INT+10)>=CON");
						caracguerrier();
						boutonCarac();
						
							
					}
					else if(J1 instanceof Chasseur){
						consigne.setText("FOR>=20 et DEX>= 20 et INT>=20 et CON>=20");
						caracchasseur();
						boutonCarac();
					}
					else{
						consigne.setText("INT >= max(FOR, DEX) + 15 et CON >= max(FOR, DEX) + 15");
						caracmage();
						boutonCarac();
					}
				}
				else{
					menu();
				}
			}	
			else{
				if(J1.getVIT()==0){
					panCentre.removeAll();
					panCentre.repaint();
					panCentre.revalidate();
					consigne.setText("Game Over");
					menudebut();
				}
				else{
					consigne.setText("Victoire");
					panCentre.removeAll();
					panCentre.repaint();
					panCentre.revalidate();
				
					JButton niv=new JButton("Passer Niveau");
					niv.addActionListener(new EcouteurNiv());
					panCentre.add(niv);
				
					JButton quitter=new JButton("Quitter");
					quitter.addActionListener(new EcouteurFin());
					panCentre.add(quitter);
				}
			}
		}
	}
	
	public void menu(){
		panCentre.removeAll();
		panCentre.repaint();
		panCentre.revalidate();
		
		panNord.removeAll();
		panNord.repaint();
		panNord.revalidate();
		
		JPanel panj1=new JPanel(new GridLayout(2,1,2,2));
		panj1.add(new JLabel(J1.getNom()+" nv."+J1.getEXP()));
		this.tPVJ1=new JTextField(4);
		this.tPVJ1.setEditable(false);
		this.tPVJ1.setText("PV : "+J1.getVIT());
		panj1.add(tPVJ1);
		panNord.add(panj1);
		consigne.setText(consigne.getText()+"     Que voulez-vous faire ?");
		JButton continuer=new JButton("Lancer Combat");
		continuer.addActionListener(new EcouteurFin());
		panCentre.add(continuer);
		if(J1 instanceof Guerrier){
			System.out.println("Tes");
		}
		JButton sauvegarder=new JButton("Sauvegarder");
		sauvegarder.addActionListener(new EcouteurFin());
		panCentre.add(sauvegarder);
		
		JButton quitter=new JButton("Quitter");
		quitter.addActionListener(new EcouteurFin());
		panCentre.add(quitter);
	}
	/**
	 * 
	 * CHOIX DE PERSONNAGE DEJA CREER OU CREATION D'UN NOUVEAU
	 *
	 */
	class EcouteurDebut implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Nouvelle Partie")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				JButton cree=new JButton("Cree nouveau personnage");
				panCentre.add(cree);
				cree.addActionListener(new EcouteurPersonnage());
				JButton charger=new JButton("Charger un personnage deja cree");
				panCentre.add(charger);
				charger.addActionListener(new EcouteurPersonnage());
			}
			else{
				J1=save.charger();
				menu();
			}
		}
	}
	/**
	 * 
	 *CREATION DE PERSONNAGE(NOM) OU CHOIX DE PERSONNAGE DEJA CREER
	 *
	 */
	class EcouteurPersonnage implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Cree nouveau personnage")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				consigne.setText("Saisir le NOM de votre Personnage");
				JLabel nomperso=new JLabel("Nom :");
				panCentre.add(nomperso);
				
				nom=new JTextField(10);
				nom.setEditable(true);
				panCentre.add(nom);
				
				JButton ok=new JButton("OK");
				ok.addActionListener(new EcouteurChoixClasse());
				panCentre.add(ok);
				
			}
			else{
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				
				consigne.setText("Choississez votre Personnage");
				for(int i=0;i<Personnage.listPersonnage.length;i++){
					switch(i){
						default : JButton perso=new JButton(Personnage.listPersonnage[i].toString());
						perso.addActionListener(new EcouteurChargerPersonnage());
						panCentre.add(perso);
					}
				}
			}
		}
	}
	/**
	 * 
	 * CHOIX DE LA CLASSE
	 *
	 */
	class EcouteurChoixClasse implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			panCentre.removeAll();
			panCentre.repaint();
			panCentre.revalidate();
			consigne.setText("");
			JButton guerrier=new JButton("Guerrier");
			guerrier.addActionListener(new EcouteurClasse());
			panCentre.add(guerrier);
			JButton chasseur=new JButton("Chasseur");
			chasseur.addActionListener(new EcouteurClasse());
			panCentre.add(chasseur);
			JButton mage=new JButton("Mage");
			mage.addActionListener(new EcouteurClasse());
			panCentre.add(mage);
		}
	}
	/**
	 * 
	 * MIS EN PLACE DES CONDITIONS POUR LES CARACTERISTIQUE
	 *
	 */
	class EcouteurClasse implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Guerrier")){
				
				J1=new Guerrier();
				newPerso=true;
				consigne.setText("FOR>=(DEX+10)>=(INT+10)>=CON");
				J1.setNom(nom.getText());
				caracguerrier();
				boutonCarac();
				
					
			}
			else if(s.equals("Chasseur")){
				J1=new Chasseur();
				newPerso=true;
				consigne.setText("FOR>=20 et DEX>= 20 et INT>=20 et CON>=20");
				J1.setNom(nom.getText());
				caracchasseur();
				boutonCarac();
			}
			else{
				J1=new Mage();
				newPerso=true;
				consigne.setText("INT >= max(FOR, DEX) + 15 et CON >= max(FOR, DEX) + 15");
				J1.setNom(nom.getText());
				caracmage();
				boutonCarac();
			}
		}

	}
	/**
	 * 
	 * BOUTON CARACTERISTIQUE
	 *
	 */
	class EcouteurVIT implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("+")){
				J1.setMaxVIT(J1.getMaxVIT()+1);
				J1.setVIT(J1.getVIT()+1);
			}
			else{
				if(J1.getVIT()>100+(J1.getEXP()*3)){
					J1.setVIT(J1.getVIT()-1);
					J1.setMaxVIT(J1.getMaxVIT()-1);
					
				}
			}
			vit.setText("Vitalite : "+Double.toString(J1.getVIT()));
			pointattribue.setText(Integer.toString(pointrestant()));
			if(J1 instanceof Guerrier)
				caracguerrier();
			else if(J1 instanceof Chasseur)
				caracchasseur();
			else
				caracmage();
		}
	}
	
	class EcouteurFOR implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("+")){
				J1.setFOR(J1.getFOR()+1);
			}
			else{
				if(J1.getFOR()>0){
					J1.setFOR(J1.getFOR()-1);
				}
			}
			force.setText("Force : "+Integer.toString(J1.getFOR()));
			pointattribue.setText(Integer.toString(pointrestant()));
			if(J1 instanceof Guerrier)
				caracguerrier();
			else if(J1 instanceof Chasseur)
				caracchasseur();
			else
				caracmage();
		}
	}
	
	class EcouteurDEX implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("+")){
				J1.setDEX(J1.getDEX()+1);
			}
			else{
				if(J1.getDEX()>0){
					J1.setDEX(J1.getDEX()-1);
				}
			}
			dex.setText("Dexterite : "+Integer.toString(J1.getDEX()));
			pointattribue.setText(Integer.toString(pointrestant()));
			if(J1 instanceof Guerrier)
				caracguerrier();
			else if(J1 instanceof Chasseur)
				caracchasseur();
			else
				caracmage();
		}
	}
	
	class EcouteurCON implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("+")){
				J1.setCON(J1.getCON()+1);
			}
			else{
				if(J1.getCON()>0){
					J1.setCON(J1.getCON()-1);
				}
			}
			con.setText("Concentration : "+Integer.toString(J1.getCON()));
			pointattribue.setText(Integer.toString(pointrestant()));
			if(J1 instanceof Guerrier)
				caracguerrier();
			else if(J1 instanceof Chasseur)
				caracchasseur();
			else
				caracmage();
		}
	}
	
	class EcouteurINT implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("+")){
				J1.setINT(J1.getINT()+1);
			}
			else{
				if(J1.getINT()>0){
					J1.setINT(J1.getINT()-1);
				}
			}
			intel.setText("Intelligence : "+Integer.toString(J1.getINT()));
			pointattribue.setText(Integer.toString(pointrestant()));
			if(J1 instanceof Guerrier)
				caracguerrier();
			else if(J1 instanceof Chasseur)
				caracchasseur();
			else
				caracmage();
		}
	}
	/**
	 * 
	 * CHOIX DU TYPE DE CAPACITE
	 *
	 */
	class EcouteurCapacite implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Arme")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listArme.length;i++){
					switch(i){
						default : JButton arme=new JButton("<html>"+Personnage.listArme[i].toString()+"</html>");
						arme.addActionListener(new EcouteurArme());
						panCentre.add(arme);
					}
				}
			}
			else if(s.equals("Bouclier")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listBouclier.length;i++){
					switch(i){
						default : JButton bouclier=new JButton("<html>"+Personnage.listBouclier[i].toString()+"</html>");
						bouclier.addActionListener(new EcouteurBouclier());
						panCentre.add(bouclier);
					}
				}
			}
			else if(s.equals("Remede")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listRemede.length;i++){
					switch(i){
						default : JButton remede=new JButton("<html>"+Personnage.listRemede[i].toString()+"</html>");
						remede.addActionListener(new EcouteurRemede());
						panCentre.add(remede);
					}
				}
			}
			else if(s.equals("SortAttaque")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listAttaque.length;i++){
					switch(i){
						default : JButton attaque=new JButton("<html>"+Personnage.listAttaque[i].toString()+"</html>");
						attaque.addActionListener(new EcouteurAttaque());
						panCentre.add(attaque);
					}
				}
			}
			else if(s.equals("SortDefense")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listDefense.length;i++){
					switch(i){
						default : JButton defense=new JButton("<html>"+Personnage.listDefense[i].toString()+"</html>");
						defense.addActionListener(new EcouteurDefense());
						panCentre.add(defense);
					}
				}
			}
			else if(s.equals("SortSoin")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<Personnage.listSoin.length;i++){
					switch(i){
						default : JButton soin=new JButton("<html>"+Personnage.listSoin[i].toString()+"</html>");
						soin.addActionListener(new EcouteurSoin());
						panCentre.add(soin);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * CHOIX DE LA CAPACITE EN FONCTION DU TYPE CHOISI
	 *
	 */
	class EcouteurArme implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listArme.length;i++){
				if(s.equals("<html>"+Personnage.listArme[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listArme[i],tournum);
					break;
				}
			}
			
			tournum++;
			if(J1 instanceof Guerrier)
				capaciteguerrier();
			else
				capacitechasseur();
			
		}
	}
	class EcouteurBouclier implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listBouclier.length;i++){
				if(s.equals("<html>"+Personnage.listBouclier[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listBouclier[i],tournum);
					break;
				}
			}
			
			tournum++;
			if(J1 instanceof Guerrier)
				capaciteguerrier();
			else
				capacitechasseur();
		}
	}
	class EcouteurRemede implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listRemede.length;i++){
				if(s.equals("<html>"+Personnage.listRemede[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listRemede[i],tournum);
					break;
				}
			}
			tournum++;
			
			if(J1 instanceof Guerrier)
				capaciteguerrier();
			else
				capacitechasseur();
		}
	}
	class EcouteurAttaque implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listAttaque.length;i++){
				if(s.equals("<html>"+Personnage.listAttaque[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listAttaque[i],tournum);
					break;
				}
			}
			tournum++;
			
			if(J1 instanceof Mage)
				capacitemage();
			else
				capacitechasseur();
			
		}
	}
	class EcouteurDefense implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listDefense.length;i++){
				if(s.equals("<html>"+Personnage.listDefense[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listDefense[i],tournum);
					break;
				}
			}
			tournum++;
			
			if(J1 instanceof Mage)
				capacitemage();
			else
				capacitechasseur();
		}
	}
	class EcouteurSoin implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listSoin.length;i++){
				if(s.equals("<html>"+Personnage.listSoin[i].toString()+"</html>")){
					J1.setCapacite(Personnage.listSoin[i],tournum);
					break;
				}
			}
			
			tournum++;
			
			if(J1 instanceof Mage)
				capacitemage();
			else
				capacitechasseur();
		}
	}
	class EcouteurSupprCapacite implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			Capacite [] cap=new Capacite[J1.getCapacite().length-1];
			
			for(int i=0;i<J1.getCapacite().length;i++){
				if(!s.equals("<html>"+J1.getCapacite()[i].toString()+"</html>"))
					cap[i]=J1.getCapacite()[i];
			}
			J1.setCapacite(cap);
			menu();
		}
	}
	/**
	 * 
	 * CHOIX D'UN PERSONNAGE DEJA CREER
	 *
	 */
	class EcouteurChargerPersonnage implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<Personnage.listPersonnage.length;i++){
				if(s.equals(Personnage.listPersonnage[i].toString())){
					J1=new Personnage(Personnage.listPersonnage[i]);
					break;
				}
			}
			consigne.setText("");
			menu();

		}
	}
	
	/**
	 * 
	 * COMBAT CHOIX DE L'ACTION
	 *
	 */
	class EcouteurCombat implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Attaque")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<J1.getCapacite().length;i++){
					if(J1.getCapacite()[i] instanceof Arme || J1.getCapacite()[i] instanceof Bouclier || J1.getCapacite()[i] instanceof SortAttaque){
						switch(i){
							default : JButton cap=new JButton("<html>"+J1.getCapacite()[i].toString()+"</html>");
							cap.addActionListener(new EcouteurAttaqueCapacite());
							panCentre.add(cap);
						}
					}
				}
				
			}
			else if(s.equals("Soin")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				
				for(int i=0;i<J1.getCapacite().length;i++){
					if(J1.getCapacite()[i] instanceof Remede || J1.getCapacite()[i] instanceof SortSoin){
						switch(i){
							default : JButton cap=new JButton("<html>"+J1.getCapacite()[i].toString()+"</html>");
							cap.addActionListener(new EcouteurSoinCapacite());
							panCentre.add(cap);
						}
					}
				}
				
			}
			else{
				Jeu.setCapituler(true);
				combat();
			}
		}
	}
	/**
	 * 
	 * CHOIX DE LA CAPACITE A UTILISER POUR L'ACTION
	 *
	 */
	class EcouteurAttaqueCapacite implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<J1.getCapacite().length;i++){
				if(s.equals("<html>"+J1.getCapacite()[i].toString()+"</html>"))
					capacitechoix=J1.getCapacite()[i];
			}
			if(Jeu.attaquer(J1, J2,capacitechoix)){
				consigne.setText("J1 : Attaque Reussie");
				if(!(J1.isPossedeDefense())){
						Jeu.defendreIA(J1, J2, capacitechoix);
						consigne.setText("Encaisse l'attaque");
				}
				else{
					if(J2.isPossedeDefense()){
						if(!(Jeu.defendreIA(J1, J2, capacitechoix)))
							consigne.setText(consigne.getText()+"   J2 : Defense Reussie");
						else
							consigne.setText(consigne.getText()+"   J2 : Defense Echoue");
					}
					else{
						Jeu.degatsansdefense(J2,capacitechoix);
						tournum++;
					}
				}
			}
			else{
				tournum++;
				consigne.setText("J1 : Attaque Ratee");
			}
			combat();
		}
	}
	
	class EcouteurSoinCapacite implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			for(int i=0;i<J1.getCapacite().length;i++){
				if(s.equals("<html>"+J1.getCapacite()[i].toString()+"</html>"))
					capacitechoix=J1.getCapacite()[i];
			}
			if(Jeu.soigner(J1,capacitechoix))
				consigne.setText("Soin reussie");
			else
				consigne.setText("Soin ratee");
			tournum++;
			combat();
		}
	}
	
	class EcouteurDefendreCapacite implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			
			for(int i=0;i<J1.getCapacite().length;i++){
				if(s.equals("<html>"+J1.getCapacite()[i].toString()+"</html>"))
					capacitechoix=J1.getCapacite()[i];
			}
			if(Jeu.defendre(J1,J2,capacitechoix))
				consigne.setText("J1 : Defense Reussie");
			else
				consigne.setText("J1 : Defense Ratee");
					
			combat();
		}
	}
	class EcouteurDefendre implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Defendre")){
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
				for(int i=0;i<J1.getCapacite().length;i++){
					if(J1.getCapacite()[i] instanceof Arme || J1.getCapacite()[i] instanceof Bouclier || J1.getCapacite()[i] instanceof SortDefense){
						switch(i){
							default : JButton defense=new JButton("<html>"+J1.getCapacite()[i].toString()+"</html>");
							defense.addActionListener(new EcouteurDefendreCapacite());
							panCentre.add(defense);
						}
					}
				}
				consigne.setText("Si vous utilisez une Arme ou un Bouclier contre un sort il sera brise et reduira son efficacit� de 1/3");
			}
			else if(s.equals("Evaluer l'attaque")){
				Jeu.setEvaluer(true);
				panCentre.removeAll();
				panCentre.repaint();
				panCentre.revalidate();
			
				JButton defendre=new JButton("Defendre");
				consigne.setText("Efficacité de l'attaque : "+Jeu.getEFF());
				JButton encaisser=new JButton("Encaisser");
				defendre.addActionListener(new EcouteurDefendre());
				encaisser.addActionListener(new EcouteurDefendre());
			
				panCentre.add(defendre);
				panCentre.add(encaisser);
			}
			else{
				consigne.setText("J1 : Encaisse    J2:Attaque Reussie");
				Jeu.degatsansdefense(J1);
				tournum++;
				combat();
			}
		}
	}
	class EcouteurNiv implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			up=true;
			if(J1.getEXP()<20){
				J1.setEXP(J1.getEXP()+1);
				int vit=100+J1.getEXP()*3;
				J1.setMaxVIT(vit);
			}
			J1.setVIT(J1.getMaxVIT());
			if(J1 instanceof Guerrier){
				consigne.setText("FOR>=(DEX+10)>=(INT+10)>=CON");
				caracguerrier();
				boutonCarac();
			}
			else if(J1 instanceof Chasseur){
				consigne.setText("FOR>=20 et DEX>= 20 et INT>=20 et CON>=20");
				caracchasseur();
				boutonCarac();
			}
			else{
				consigne.setText("INT >= max(FOR, DEX) + 15 et CON >= max(FOR, DEX) + 15");
				caracmage();
				boutonCarac();
			}
		}
	}
	class EcouteurFin implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			String s=ev.getActionCommand();
			if(s.equals("Lancer Combat")){
				setupcombat();					
			}
			else if(s.equals("Sauvegarder")){
				save.sauvegarde(J1);
				consigne.setText("Sauvegarde effectue");
				menu();
			}
			else{
				System.exit(0);;
			}
		}
	}
	public static void main(String[]args){	
		new FenJeu("Jeu",1200,720);
	}
}
