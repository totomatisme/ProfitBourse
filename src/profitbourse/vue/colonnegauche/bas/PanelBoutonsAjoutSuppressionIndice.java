package profitbourse.vue.colonnegauche.bas;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsAjoutSuppressionIndice extends JPanel {
	
	private static final long serialVersionUID = -9173510864640532701L;
	private Controleur controleur;
	private JButton boutonAjouterIndice;
	private JButton boutonSupprimerIndice;
	private JButton boutonMajIndices;
	
	public PanelBoutonsAjoutSuppressionIndice(Controleur controleur) {
		super(new FlowLayout(FlowLayout.RIGHT));
		this.controleur = controleur;
		
		this.boutonMajIndices = new JButton(this.controleur.demandeMajIndices);
		this.add(this.boutonMajIndices);
		
		this.boutonAjouterIndice = new JButton(this.controleur.demandeAjoutIndice);
		this.add(this.boutonAjouterIndice);
		
		this.boutonSupprimerIndice = new JButton(this.controleur.demandeSuppressionIndice);
		this.add(this.boutonSupprimerIndice);
	}

}
