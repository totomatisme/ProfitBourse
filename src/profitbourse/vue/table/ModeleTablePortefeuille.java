package profitbourse.vue.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import profitbourse.modele.Action;
import profitbourse.modele.Portefeuille;
import profitbourse.vue.Controleur;

public class ModeleTablePortefeuille extends AbstractTableModel {

	private static final long serialVersionUID = 2740040577237207035L;
	
	private Controleur controleur;
	private Portefeuille portefeuille;
	private ArrayList<String> entetes;
	private ObservateurAjoutAction observateurAjoutAction;
	private ObservateurSuppressionAction observateurSuppressionAction;
	private ObservateurMajActions observateurMajActions;
	private ObservateurModificationAction observateurModificationAction;
	private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
	
	public ModeleTablePortefeuille(Controleur controleur) {
		this.controleur = controleur;
		this.portefeuille = null;
		this.entetes = new ArrayList<String>();
		this.entetes.add("Nom");
		this.entetes.add("Code");
		this.entetes.add("Quantité");
		this.entetes.add("Date achat");
		this.entetes.add("Cours achat");
		this.entetes.add("Total achat");
		this.entetes.add("Total actuel");
		this.entetes.add("Plus-value");
		this.entetes.add("Cours actuel");
		this.entetes.add("Variation");
		this.observateurAjoutAction = new ObservateurAjoutAction();
		this.observateurSuppressionAction = new ObservateurSuppressionAction();
		this.observateurMajActions = new ObservateurMajActions();
		this.observateurModificationAction = new ObservateurModificationAction();
		this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
		this.controleur.getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
	}

	public int getColumnCount() {
		if (this.entetes == null) {
			return 0;
		} else {
			return this.entetes.size();
		}
	}

	public int getRowCount() {
		if (this.portefeuille == null) {
			return 0;
		} else {
			return this.portefeuille.getActions().size();
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
		if (this.portefeuille == null) {
			return null;
		} else {
			switch (columnIndex) {
			case 0:
				return this.portefeuille.getActions().get(rowIndex).getNom();
			case 1:
				return this.portefeuille.getActions().get(rowIndex).getCodeISIN();
			case 2:
				return this.portefeuille.getActions().get(rowIndex).getQuantite();
			case 3:
				return this.portefeuille.getActions().get(rowIndex).getDateAchat();
			case 4:
				return this.portefeuille.getActions().get(rowIndex).getCoursAchat();
			case 5:
				return this.portefeuille.getActions().get(rowIndex).calculerTotalAchat();
			case 6:
				return this.portefeuille.getActions().get(rowIndex).calculerTotalActuel();
			case 7:
				return this.portefeuille.getActions().get(rowIndex).calculerPlusValue();
			case 8:
				return this.portefeuille.getActions().get(rowIndex).getCoursActuel();
			case 9:
				return this.portefeuille.getActions().get(rowIndex).getVariation();
			default:
				return null; //Ne devrait jamais arriver
			}
		}
	}
	
	public Class getColumnClass(int columnIndex) { 
		switch (columnIndex) {
		case 3:
			return Date.class;
		default:
			return Object.class;
		}
	}
	
	private class ObservateurAjoutAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationActionAjoutee notificationActionAjoutee = portefeuille.getNotificationActionAjoutee();
			if (notificationActionAjoutee == arg0) {
				int row = notificationActionAjoutee.getRow();
				fireTableRowsInserted(row, row);
			}
		}
	}
	
	private class ObservateurSuppressionAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationActionSupprimee notificationActionSupprimee = portefeuille.getNotificationActionSupprimee();
			if (notificationActionSupprimee == arg0) {
				int row = notificationActionSupprimee.getRow();
				fireTableRowsDeleted(row, row);
			}
		}
	}
	
	private class ObservateurMajActions implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationMajActions notificationMajActions = portefeuille.getNotificationMajActions();
			if (notificationMajActions == arg0) {
				fireTableDataChanged();
			}
		}
	}
	
	private class ObservateurModificationAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			int index = portefeuille.getActions().indexOf(arg1);
			if (index != -1) {
				fireTableRowsUpdated(index, index);
			}
		}
	}
	
	private class ObservateurChangementDePortefeuilleCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
			Portefeuille portefeuillePrecedant = portefeuille;
			if (portefeuillePrecedant != null) {
				portefeuillePrecedant.getNotificationActionAjoutee().deleteObserver(observateurAjoutAction);
				portefeuillePrecedant.getNotificationActionSupprimee().deleteObserver(observateurSuppressionAction);
				portefeuillePrecedant.getNotificationMajActions().deleteObserver(observateurMajActions);
				Iterator<Action> it = portefeuillePrecedant.getActions().iterator();
				while (it.hasNext()) {
					it.next().getNotificationModificationAction().deleteObserver(observateurModificationAction);
				}
			}
			portefeuille = nouveauPortefeuille;
			if (portefeuille != null) {
				portefeuille.getNotificationActionAjoutee().addObserver(observateurAjoutAction);
				portefeuille.getNotificationActionSupprimee().addObserver(observateurSuppressionAction);
				portefeuille.getNotificationMajActions().addObserver(observateurMajActions);
				Iterator<Action> it = portefeuille.getActions().iterator();
				while (it.hasNext()) {
					it.next().getNotificationModificationAction().addObserver(observateurModificationAction);
				}
			}
			fireTableDataChanged();
		}
	}

}
