package profitbourse.vue.table;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import profitbourse.modele.Projet;
import profitbourse.vue.Controleur;

public class ModeleTableIndices extends AbstractTableModel {

	private static final long serialVersionUID = 760234202695806892L;
	
	private Controleur controleur;
	private Projet projet;
	private ArrayList<String> entetes;
	private ObservateurAjoutIndice observateurAjoutIndice;
	private ObservateurMajIndices observateurMajIndices;
	private ObservateurSuppressionIndice observateurSuppressionIndice;
	private ObservateurModificationIndice observateurModificationIndice;
	private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;

	public ModeleTableIndices(Controleur controleur) {
		this.controleur = controleur;
		this.projet = null;
		this.entetes = new ArrayList<String>();
		this.entetes.add("Code");
		this.entetes.add("Points");
		this.entetes.add("%");
		this.observateurAjoutIndice = new ObservateurAjoutIndice();
		this.observateurMajIndices = new ObservateurMajIndices();
		this.observateurModificationIndice = new ObservateurModificationIndice();
		this.observateurSuppressionIndice = new ObservateurSuppressionIndice();
		this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
		this.controleur.getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
	}
	
	public int getColumnCount() {
		if (this.entetes == null) {
			return 0;
		} else {
			return this.entetes.size();
		}
	}

	public int getRowCount() {
		if (this.projet == null) {
			return 0;
		} else {
			return this.projet.getIndices().size();
		}
	}
	
	public String getColumnName(int column) {
		if (this.entetes == null) {
			return "";
		} else {
			return this.entetes.get(column);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (this.projet == null) {
			return null;
		} else {
			switch (columnIndex) {
			case 0:
				return this.projet.getIndices().get(rowIndex).getCode();
			case 1:
				return this.projet.getIndices().get(rowIndex).getCours();
			case 2:
				return this.projet.getIndices().get(rowIndex).getVariation();
			default:
				return null; //Ne devrait jamais arriver
			}
		}
	}
	
	private class ObservateurAjoutIndice implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationIndiceAjoute notificationIndiceAjoute = projet.getNotificationIndiceAjoute();
			if (notificationIndiceAjoute == arg0) {
				int row = notificationIndiceAjoute.getRow();
				fireTableRowsInserted(row, row);
			}
		}
	}
	
	private class ObservateurSuppressionIndice implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationIndiceSupprime notificationIndiceSupprime = projet.getNotificationIndiceSupprime();
			if (notificationIndiceSupprime == arg0) {
				int row = notificationIndiceSupprime.getRow();
				fireTableRowsDeleted(row, row);
			}
		}
	}
	
	private class ObservateurMajIndices implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationMajIndices notificationMajIndices = projet.getNotificationMajIndices();
			if (notificationMajIndices == arg0) {
				fireTableDataChanged();
			}
		}
	}
	
	private class ObservateurModificationIndice implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationModificationIndice notificationModificationIndice = projet.getNotificationModificationIndice();
			if (notificationModificationIndice == arg0) {
				int row = notificationModificationIndice.getRow();
				fireTableRowsUpdated(row, row);
			}
		}
	}
	
	private class ObservateurChangementDeProjetCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet nouveauProjet = (Projet)arg1;
			Projet projetPrecedant = projet;
			if (projetPrecedant != null) {
				projetPrecedant.getNotificationIndiceAjoute().deleteObserver(observateurAjoutIndice);
				projetPrecedant.getNotificationIndiceSupprime().deleteObserver(observateurSuppressionIndice);
				projetPrecedant.getNotificationMajIndices().deleteObserver(observateurMajIndices);
				projetPrecedant.getNotificationModificationIndice().deleteObserver(observateurModificationIndice);
			}
			projet = nouveauProjet;
			if (projet != null) {
				projet.getNotificationIndiceAjoute().addObserver(observateurAjoutIndice);
				projet.getNotificationIndiceSupprime().addObserver(observateurSuppressionIndice);
				projet.getNotificationMajIndices().addObserver(observateurMajIndices);
				projet.getNotificationModificationIndice().addObserver(observateurModificationIndice);
			}
			fireTableDataChanged();
		}
	}
}
