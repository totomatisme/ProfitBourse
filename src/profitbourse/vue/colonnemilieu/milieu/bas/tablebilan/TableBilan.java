package profitbourse.vue.colonnemilieu.milieu.bas.tablebilan;

import javax.swing.JTable;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnemilieu.milieu.tableportefeuille.PlusValueCellRenderer;

public class TableBilan extends JTable {
	
	private static final long serialVersionUID = 3151072281732545752L;
	private Controleur controleur;
	private ModeleTableBilan modeleTableBilan;
	
	public TableBilan(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.modeleTableBilan = new ModeleTableBilan(this.controleur);
		this.setModel(this.modeleTableBilan);
		
		this.getColumnModel().getColumn(3).setCellRenderer(new PlusValueCellRenderer());
	}

}
