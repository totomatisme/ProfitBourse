package profitbourse.vue.barredemenu;

import javax.swing.JMenuBar;

import profitbourse.vue.Controleur;

public class BarreDeMenu extends JMenuBar {
	
	private static final long serialVersionUID = 2397891326165184962L;
	private Controleur controleur;
	private MenuFichier menuFichier;
	private MenuProjet menuProjet;
	private MenuPortefeuille menuPortefeuille;
	private MenuAction menuAction;
	
	public BarreDeMenu(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.menuFichier = new MenuFichier(this.controleur);
		this.add(this.menuFichier);
		
		this.menuProjet = new MenuProjet(this.controleur);
		this.add(this.menuProjet);
		
		this.menuPortefeuille = new MenuPortefeuille(this.controleur);
		this.add(this.menuPortefeuille);
		
		this.menuAction = new MenuAction(this.controleur);
		this.add(this.menuAction);
	}

}
