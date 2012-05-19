package profitbourse.vue.colonnemilieu;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnemilieu.milieu.PanelColonneMilieuMilieu;

public class PanelColonneMilieu extends JPanel {
	
	private static final long serialVersionUID = -3907465590674328248L;
	private Controleur controleur;
	private PanelColonneMilieuMilieu panelColonneMilieuMilieu;
	
	public PanelColonneMilieu(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.panelColonneMilieuMilieu = new PanelColonneMilieuMilieu(this.controleur);
		this.add(this.panelColonneMilieuMilieu, BorderLayout.CENTER);
	}

}
