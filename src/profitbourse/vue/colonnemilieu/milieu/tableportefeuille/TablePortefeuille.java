package profitbourse.vue.colonnemilieu.milieu.tableportefeuille;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import profitbourse.modele.Action;
import profitbourse.vue.Controleur;

public class TablePortefeuille extends JTable {
	
	private static final long serialVersionUID = 3235265934824591898L;
	private Controleur controleur;
	private ModeleTablePortefeuille modeleTablePortefeuille;
	private SelectionActionListener selectionActionListener;
	private ObservateurChangementActionCourante observateurChangementActionCourante;
	
	public TablePortefeuille(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.modeleTablePortefeuille = new ModeleTablePortefeuille(this.controleur);
		this.setModel(this.modeleTablePortefeuille);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setFillsViewportHeight(true);
		
		this.selectionActionListener = new SelectionActionListener();
		this.getSelectionModel().addListSelectionListener(this.selectionActionListener);
		
		this.observateurChangementActionCourante = new ObservateurChangementActionCourante();
		this.controleur.getNotificationChangementActionCourante().addObserver(this.observateurChangementActionCourante);
	}
	
	private class SelectionActionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) return;
			ListSelectionModel selectionModel = (ListSelectionModel)e.getSource();
            if (!selectionModel.isSelectionEmpty()) {
                int rowSelection = selectionModel.getMinSelectionIndex();
                Action actionSelection = controleur.getPortefeuilleActuel().getActions().get(rowSelection);
                controleur.changerActionActuelle(actionSelection);
                //System.out.println("Action '" + actionSelection + "' a été selectionnée.");
            }
		}
	}
	
	private class ObservateurChangementActionCourante implements Observer {
		public void update(Observable arg0, Object arg1) {
			Controleur.NotificationChangementActionCourante notificationChangementActionCourante = controleur.getNotificationChangementActionCourante();
			if (notificationChangementActionCourante == arg0) {
				Action nouvelleAction = (Action)arg1;
				if (nouvelleAction != null) {
					ListSelectionModel selectionModel = getSelectionModel();
					int row = controleur.getProjetActuel().getIndices().indexOf(nouvelleAction);
					if (row != -1) {
						selectionModel.removeListSelectionListener(selectionActionListener); // c'est un peu dégueulasse, mais c'est la faute de Swing qui est mal fait.
						selectionModel.setSelectionInterval(row, row);
						selectionModel.addListSelectionListener(selectionActionListener);
					}
				}
			}
		}
	}

}
