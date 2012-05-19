package profitbourse.vue.colonnemilieu.milieu.bas;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelLabelBilan extends JPanel {

	private static final long serialVersionUID = -4739303095459785176L;
	private Controleur controleur;
	private LabelPortefeuilleBilan labelPortefeuilleBilan;
	
	public PanelLabelBilan(Controleur controleur) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.controleur = controleur;
		
		this.labelPortefeuilleBilan = new LabelPortefeuilleBilan(this.controleur);
		this.add(this.labelPortefeuilleBilan);
	}
	
}
