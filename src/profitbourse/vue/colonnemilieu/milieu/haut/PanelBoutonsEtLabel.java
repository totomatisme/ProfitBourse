package profitbourse.vue.colonnemilieu.milieu.haut;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsEtLabel extends JPanel {

	private static final long serialVersionUID = 5501690840447161888L;
	private Controleur controleur;
	private LabelPortefeuilleContenu labelPortefeuilleContenu;
	
	public PanelBoutonsEtLabel(Controleur controleur) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.controleur = controleur;
		
		this.labelPortefeuilleContenu = new LabelPortefeuilleContenu(this.controleur);
		this.add(this.labelPortefeuilleContenu);
	}
	
}
