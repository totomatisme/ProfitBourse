package profitbourse.vue.colonnegauche.bas.tableindices;

import javax.swing.JTable;

import profitbourse.vue.Controleur;

public class TableIndices extends JTable {
	
	private static final long serialVersionUID = 766986177488126708L;
	private Controleur controleur;
	private ModeleTableIndices modeleTableIndices;
	
	public TableIndices(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.modeleTableIndices = new ModeleTableIndices(this.controleur);
		this.setModel(this.modeleTableIndices);
	}

}
