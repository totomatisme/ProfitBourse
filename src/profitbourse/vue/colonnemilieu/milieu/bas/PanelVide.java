package profitbourse.vue.colonnemilieu.milieu.bas;

import java.awt.Dimension;

import javax.swing.JPanel;

import profitbourse.vue.Controleur;

public class PanelVide extends JPanel {
	
	private static final long serialVersionUID = -3257806341026785837L;
	//private Controleur controleur;
	
	public PanelVide(Controleur controleur) {
		super();
		//this.controleur = controleur;
		
		this.setPreferredSize(new Dimension(200,2));
	}

}
