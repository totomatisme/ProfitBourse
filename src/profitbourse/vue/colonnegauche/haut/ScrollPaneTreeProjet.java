package profitbourse.vue.colonnegauche.haut;

import javax.swing.JScrollPane;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnegauche.haut.treeprojet.TreeProjet;

public class ScrollPaneTreeProjet extends JScrollPane {
	
	private static final long serialVersionUID = 8027869211792372270L;
	private Controleur controleur;
	private TreeProjet treeProjet;
	
	public ScrollPaneTreeProjet(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.treeProjet = new TreeProjet(this.controleur);
		this.setViewportView(this.treeProjet);
	}

}
