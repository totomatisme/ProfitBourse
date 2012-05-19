package profitbourse.vue.colonnegauche.bas;

import javax.swing.JScrollPane;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnegauche.bas.tableindices.TableIndices;

public class ScrollPaneTableIndices extends JScrollPane {

	private static final long serialVersionUID = 8613960081039391709L;
	private Controleur controleur;
	private TableIndices tableIndices;
	
	public ScrollPaneTableIndices(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.tableIndices = new TableIndices(this.controleur);
		this.setViewportView(this.tableIndices);
	}

}
