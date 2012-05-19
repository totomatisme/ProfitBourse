package profitbourse.vue;

import javax.swing.JSplitPane;

import profitbourse.vue.colonnegauche.SplitPaneColonneGauche;
import profitbourse.vue.colonnemilieu.PanelColonneMilieu;

public class SplitPanePrincipal extends JSplitPane {

	private static final long serialVersionUID = -1760745785472907036L;
	private Controleur controleur;
	private SplitPaneColonneGauche splitPaneColonneGauche;
	private PanelColonneMilieu panelColonneMilieu;
	
	public SplitPanePrincipal(Controleur controleur) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.controleur = controleur;
		
		this.splitPaneColonneGauche = new SplitPaneColonneGauche(this.controleur);
		this.panelColonneMilieu = new PanelColonneMilieu(this.controleur);
		
		this.setLeftComponent(this.splitPaneColonneGauche);
		this.setRightComponent(this.panelColonneMilieu);
	}
	
}
