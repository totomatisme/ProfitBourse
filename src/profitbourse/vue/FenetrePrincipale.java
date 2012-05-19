package profitbourse.vue;

import javax.swing.JFrame;


public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = -3744789466688086804L;
	private Controleur controleur;
	private SplitPanePrincipal splitPanePrincipal;

	public FenetrePrincipale(Controleur controleur) {
		super("ProfitBourse");
		this.controleur = controleur;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,300);
		
		this.splitPanePrincipal = new SplitPanePrincipal(this.controleur);
		this.getContentPane().add(this.splitPanePrincipal);
	}
	
}
