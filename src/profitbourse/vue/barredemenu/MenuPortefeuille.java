package profitbourse.vue.barredemenu;

import javax.swing.JMenu;

import profitbourse.vue.Controleur;

public class MenuPortefeuille extends JMenu {

	private static final long serialVersionUID = -4707197263310130343L;
	
	private Controleur controleur;
	
	private MenuItemIntelligent itemNouvelleAction;
	private MenuItemIntelligent itemSupprimerAction;
	
	private MenuItemIntelligent itemMajPortefeuille;
	
	public MenuPortefeuille(Controleur controleur) {
		super("Portefeuille");
		this.controleur = controleur;
		
		this.itemNouvelleAction = new MenuItemIntelligent(this.controleur.demandeAjoutAction);
		this.add(this.itemNouvelleAction);
		
		this.itemSupprimerAction = new MenuItemIntelligent(this.controleur.demandeSuppressionAction);
		this.add(this.itemSupprimerAction);
		
		this.addSeparator();
		
		this.itemMajPortefeuille = new MenuItemIntelligent(this.controleur.demandeMajPortefeuille);
		this.add(this.itemMajPortefeuille);
	}

}
