package profitbourse.vue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;


public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = -3744789466688086804L;

	public FenetrePrincipale() {
		super("ProfitBourse");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,170);
	}
	
}
