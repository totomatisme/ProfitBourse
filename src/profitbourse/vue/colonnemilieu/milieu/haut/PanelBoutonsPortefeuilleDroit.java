package profitbourse.vue.colonnemilieu.milieu.haut;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsPortefeuilleDroit extends JPanel {
	
	private static final long serialVersionUID = -6223620730307649203L;
	private Controleur controleur;
	private JButton boutonMajPortefeuille;
	
	public PanelBoutonsPortefeuilleDroit(Controleur controleur) {
		super(new FlowLayout(FlowLayout.RIGHT));
		this.controleur = controleur;
		
		this.boutonMajPortefeuille = new JButton(this.controleur.demandeMajPortefeuille);
		this.add(this.boutonMajPortefeuille);
	}

}
