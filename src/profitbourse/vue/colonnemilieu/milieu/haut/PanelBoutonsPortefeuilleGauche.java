package profitbourse.vue.colonnemilieu.milieu.haut;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelBoutonsPortefeuilleGauche extends JPanel {

	private static final long serialVersionUID = 7276373314007501925L;
	private Controleur controleur;
	private JButton boutonAjouterAction;
	private JButton boutonSupprimerAction;
	
	public PanelBoutonsPortefeuilleGauche(Controleur controleur) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.controleur = controleur;
		
		this.boutonAjouterAction = new JButton(this.controleur.demandeAjoutAction);
		this.add(this.boutonAjouterAction);
		
		this.boutonSupprimerAction = new JButton(this.controleur.demandeSuppressionAction);
		this.add(this.boutonSupprimerAction);
	}
	
}
