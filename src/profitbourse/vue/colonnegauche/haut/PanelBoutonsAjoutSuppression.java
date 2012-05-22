package profitbourse.vue.colonnegauche.haut;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsAjoutSuppression extends JPanel {

	private static final long serialVersionUID = 3816089536233239499L;
	private Controleur controleur;
	private JButton boutonAjoutPortefeuille;
	private JButton boutonSuppressionPortefeuille;
	
	public PanelBoutonsAjoutSuppression(Controleur controleur) {
		super(new FlowLayout(FlowLayout.RIGHT));
		this.controleur = controleur;
		
		this.boutonAjoutPortefeuille = new JButton(this.controleur.demandeAjoutPortefeuille);
		this.add(this.boutonAjoutPortefeuille);
		
		this.boutonSuppressionPortefeuille = new JButton(this.controleur.demandeSuppressionPortefeuille);
		this.add(this.boutonSuppressionPortefeuille);
	}
	
}
