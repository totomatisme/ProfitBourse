package profitbourse.vue.barredemenu;

import javax.swing.JMenuBar;

import profitbourse.vue.Controleur;

public class BarreDeMenu extends JMenuBar {
	
	private static final long serialVersionUID = 2397891326165184962L;
	private Controleur controleur;
	private MenuFichier menuFichier;
	
	public BarreDeMenu(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.menuFichier = new MenuFichier(this.controleur);
		this.add(this.menuFichier);
	}

}
