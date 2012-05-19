package profitbourse.vue.colonnemilieu.milieu.bas.tablebilan;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelTableBilanEntetesEtContenu extends JPanel {

	private static final long serialVersionUID = -4943596990437695980L;
	private Controleur controleur;
	private TableBilan tableBilan;
	
	public PanelTableBilanEntetesEtContenu(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.tableBilan = new TableBilan(this.controleur);
		this.add(this.tableBilan.getTableHeader(), BorderLayout.NORTH);
		this.add(this.tableBilan, BorderLayout.SOUTH);
	}
	
}
