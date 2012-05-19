package profitbourse.vue.colonnemilieu.milieu.bas;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnemilieu.milieu.bas.tablebilan.PanelTableBilanEntetesEtContenu;

public class PanelTableBilan extends JPanel {
	
	private static final long serialVersionUID = 1381541524870424743L;
	private Controleur controleur;
	private PanelTableBilanEntetesEtContenu panelTableBilanEntetesEtContenu;
	
	public PanelTableBilan(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.panelTableBilanEntetesEtContenu = new PanelTableBilanEntetesEtContenu(this.controleur);
		this.add(this.panelTableBilanEntetesEtContenu, BorderLayout.NORTH);
	}

}
