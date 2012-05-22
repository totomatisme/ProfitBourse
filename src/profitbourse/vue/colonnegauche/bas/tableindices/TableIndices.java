package profitbourse.vue.colonnegauche.bas.tableindices;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import profitbourse.modele.Indice;
import profitbourse.vue.Controleur;

public class TableIndices extends JTable {
	
	private static final long serialVersionUID = 766986177488126708L;
	private Controleur controleur;
	private ModeleTableIndices modeleTableIndices;
	private SelectionIndiceListener selectionIndiceListener;
	private ObservateurChangementIndiceCourant observateurChangementIndiceCourant;
	
	public TableIndices(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.modeleTableIndices = new ModeleTableIndices(this.controleur);
		this.setModel(this.modeleTableIndices);
		
		this.setFillsViewportHeight(true);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.selectionIndiceListener = new SelectionIndiceListener();
		this.getSelectionModel().addListSelectionListener(this.selectionIndiceListener);
		
		this.observateurChangementIndiceCourant = new ObservateurChangementIndiceCourant();
		this.controleur.getNotificationChangementIndiceCourant().addObserver(this.observateurChangementIndiceCourant);
	}
	
	private class SelectionIndiceListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) return;
			ListSelectionModel selectionModel = (ListSelectionModel)e.getSource();
            if (!selectionModel.isSelectionEmpty()) {
                int rowSelection = selectionModel.getMinSelectionIndex();
                Indice indiceSelection = controleur.getProjetActuel().getIndices().get(rowSelection);
                controleur.changerIndiceActuel(indiceSelection);
                //System.out.println("Indice '" + indiceSelection + "' a été selectionné.");
            }
		}
	}
	
	private class ObservateurChangementIndiceCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Controleur.NotificationChangementIndiceCourant notificationChangementIndiceCourant = controleur.getNotificationChangementIndiceCourant();
			if (notificationChangementIndiceCourant == arg0) {
				Indice nouvelIndice = (Indice)arg1;
				if (nouvelIndice != null) {
					ListSelectionModel selectionModel = getSelectionModel();
					int row = controleur.getProjetActuel().getIndices().indexOf(nouvelIndice);
					if (row != -1) {
						selectionModel.removeListSelectionListener(selectionIndiceListener); // c'est un peu dégueulasse, mais c'est la faute de Swing qui est mal fait.
						selectionModel.setSelectionInterval(row, row);
						selectionModel.addListSelectionListener(selectionIndiceListener);
					}
				}
			}
		}
	}

}
