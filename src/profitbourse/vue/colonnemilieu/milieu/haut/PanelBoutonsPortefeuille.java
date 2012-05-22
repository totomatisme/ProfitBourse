package profitbourse.vue.colonnemilieu.milieu.haut;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsPortefeuille extends JPanel {
	
	private static final long serialVersionUID = -5340724480065030490L;
	private Controleur controleur;
	private PanelBoutonsPortefeuilleGauche panelBoutonsPortefeuilleGauche;
	private PanelBoutonsPortefeuilleDroit panelBoutonsPortefeuilleDroit;
	
	public PanelBoutonsPortefeuille(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.panelBoutonsPortefeuilleDroit = new PanelBoutonsPortefeuilleDroit(this.controleur);
		this.add(this.panelBoutonsPortefeuilleDroit, BorderLayout.EAST);
		
		this.panelBoutonsPortefeuilleGauche = new PanelBoutonsPortefeuilleGauche(this.controleur);
		this.add(this.panelBoutonsPortefeuilleGauche, BorderLayout.CENTER);
	}

}
