package profitbourse.vue.colonnegauche.bas;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelColonneGaucheBas extends JPanel {

	private static final long serialVersionUID = 9119720249668290393L;
	private Controleur controleur;
	private ScrollPaneTableIndices scrollPaneTableIndices;
	private PanelBoutonsAjoutSuppressionIndice panelBoutonsAjoutSuppressionIndice;
	
	public PanelColonneGaucheBas(Controleur controleur) {
		super(new BorderLayout());
		this.controleur = controleur;
		
		this.scrollPaneTableIndices = new ScrollPaneTableIndices(this.controleur);
		this.add(this.scrollPaneTableIndices, BorderLayout.CENTER);
		
		this.panelBoutonsAjoutSuppressionIndice = new PanelBoutonsAjoutSuppressionIndice(this.controleur);
		this.add(this.panelBoutonsAjoutSuppressionIndice, BorderLayout.SOUTH);
	}

}
