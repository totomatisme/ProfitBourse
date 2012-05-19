package profitbourse.vue.colonnemilieu.milieu.tableportefeuille;

import javax.swing.JScrollPane;

import profitbourse.vue.Controleur;

public class ScrollPaneTablePortefeuille extends JScrollPane {
	
	private static final long serialVersionUID = 6118785048705600464L;
	private Controleur controleur;
	private TablePortefeuille tablePortefeuille;
	
	public ScrollPaneTablePortefeuille(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.tablePortefeuille = new TablePortefeuille(this.controleur);
		this.setViewportView(this.tablePortefeuille);
	}
	
}
