package profitbourse.vue.colonnemilieu.milieu.bas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBilan extends JPanel {

	private static final long serialVersionUID = 4666695387067932924L;
	private Controleur controleur;
	private PanelLabelBilan panelLabelBilan;
	private PanelTableBilan panelTableBilan;
	private PanelVide panelVide;
	
	public PanelBilan(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.setPreferredSize(new Dimension(100, 70));
		
		this.panelLabelBilan = new PanelLabelBilan(this.controleur);
		this.add(this.panelLabelBilan, BorderLayout.NORTH);
		
		this.panelTableBilan = new PanelTableBilan(this.controleur);
		this.add(this.panelTableBilan, BorderLayout.CENTER);
		
		this.panelVide = new PanelVide(this.controleur);
		this.add(this.panelVide, BorderLayout.EAST);
	}
	
}
