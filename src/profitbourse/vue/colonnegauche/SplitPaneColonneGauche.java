package profitbourse.vue.colonnegauche;

import javax.swing.JSplitPane;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnegauche.bas.PanelColonneGaucheBas;
import profitbourse.vue.colonnegauche.haut.PanelColonneGaucheHaut;

public class SplitPaneColonneGauche extends JSplitPane {

	private static final long serialVersionUID = -1760745785472907036L;
	private Controleur controleur;
	private PanelColonneGaucheBas panelColonneGaucheBas;
	private PanelColonneGaucheHaut panelColonneGaucheHaut;

	public SplitPaneColonneGauche(Controleur controleur) {
		super(JSplitPane.VERTICAL_SPLIT);
		this.controleur = controleur;
		this.setResizeWeight(0.5);
		this.setOneTouchExpandable(true);
		
		this.panelColonneGaucheBas = new PanelColonneGaucheBas(controleur);
		this.panelColonneGaucheHaut = new PanelColonneGaucheHaut(controleur);
		
		this.setTopComponent(this.panelColonneGaucheHaut);
		this.setBottomComponent(this.panelColonneGaucheBas);
	}

}
