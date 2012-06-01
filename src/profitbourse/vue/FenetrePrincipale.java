package profitbourse.vue;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import profitbourse.modele.preferences.GestionnairePreferences;


public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = -3744789466688086804L;
	private Controleur controleur;
	private SplitPanePrincipal splitPanePrincipal;
	
	private FenetreListener fenetreListener;

	public FenetrePrincipale(Controleur controleur) {
		super("ProfitBourse");
		this.controleur = controleur;
		
		this.fenetreListener = new FenetreListener();
		this.addWindowListener(this.fenetreListener);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		int hauteurFenetre = GestionnairePreferences.getHauteurFenetre();
		int largeurFenetre = GestionnairePreferences.getLargeurFenetre();
		this.setSize(largeurFenetre,hauteurFenetre);
		
		this.splitPanePrincipal = new SplitPanePrincipal(this.controleur);
		this.getContentPane().add(this.splitPanePrincipal);
	}
	
	private class FenetreListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			controleur.demandeQuitterApplication.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	}
	
}
