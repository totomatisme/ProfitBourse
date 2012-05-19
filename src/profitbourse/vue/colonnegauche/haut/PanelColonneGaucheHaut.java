package profitbourse.vue.colonnegauche.haut;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelColonneGaucheHaut extends JPanel {

	private static final long serialVersionUID = 800183547871066864L;
	private Controleur controleur;
	private ScrollPaneTreeProjet scrollPaneTreeProjet;
	
	public PanelColonneGaucheHaut(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.scrollPaneTreeProjet = new ScrollPaneTreeProjet(this.controleur);
		this.add(this.scrollPaneTreeProjet, BorderLayout.CENTER);
	}

}
