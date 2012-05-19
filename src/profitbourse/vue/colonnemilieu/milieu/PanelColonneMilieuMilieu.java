package profitbourse.vue.colonnemilieu.milieu;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnemilieu.milieu.bas.PanelBilan;
import profitbourse.vue.colonnemilieu.milieu.haut.PanelBoutonsEtLabel;
import profitbourse.vue.colonnemilieu.milieu.tableportefeuille.ScrollPaneTablePortefeuille;

public class PanelColonneMilieuMilieu extends JPanel {
	
	private static final long serialVersionUID = 1411342010862438950L;
	private Controleur controleur;
	private PanelBoutonsEtLabel panelBoutonsEtLabel;
	private ScrollPaneTablePortefeuille scrollPaneTablePortefeuille;
	private PanelBilan panelBilan;
	
	public PanelColonneMilieuMilieu(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.panelBoutonsEtLabel = new PanelBoutonsEtLabel(this.controleur);
		this.add(this.panelBoutonsEtLabel, BorderLayout.NORTH);
		
		this.scrollPaneTablePortefeuille = new ScrollPaneTablePortefeuille(this.controleur);
		this.add(this.scrollPaneTablePortefeuille, BorderLayout.CENTER);
		
		this.panelBilan = new PanelBilan(this.controleur);
		this.add(this.panelBilan, BorderLayout.SOUTH);
	}

}
