package profitbourse.vue.colonnemilieu.milieu.haut;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsEtLabel extends JPanel {
	
	private static final long serialVersionUID = 5501690840447161888L;
	private Controleur controleur;
	private PanelLabelTablePortefeuille panelLabelTablePortefeuille;
	private PanelBoutonsPortefeuille panelBoutonsPortefeuille;
	
	public PanelBoutonsEtLabel(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.panelLabelTablePortefeuille = new PanelLabelTablePortefeuille(this.controleur);
		this.add(this.panelLabelTablePortefeuille, BorderLayout.SOUTH);
		
		this.panelBoutonsPortefeuille = new PanelBoutonsPortefeuille(this.controleur);
		this.add(this.panelBoutonsPortefeuille, BorderLayout.CENTER);
	}

}
