package profitbourse.vue.colonnemilieu.milieu.tableportefeuille;

import javax.swing.JTable;

import profitbourse.vue.Controleur;

public class TablePortefeuille extends JTable {
	
	private static final long serialVersionUID = 3235265934824591898L;
	private Controleur controleur;
	private ModeleTablePortefeuille modeleTablePortefeuille;
	
	public TablePortefeuille(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.modeleTablePortefeuille = new ModeleTablePortefeuille(this.controleur);
		this.setModel(this.modeleTablePortefeuille);
	}

}
