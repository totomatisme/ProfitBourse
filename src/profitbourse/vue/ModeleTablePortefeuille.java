package profitbourse.vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import profitbourse.modele.Portefeuille;

public class ModeleTablePortefeuille extends AbstractTableModel {

	private static final long serialVersionUID = 2740040577237207035L;
	
	private Portefeuille portefeuille;
	private ArrayList<String> entetes;
	private ObservateurAjoutAction observateurAjoutAction;
	private ObservateurSuppressionAction observateurSuppressionAction;
	private ObservateurMajActions observateurMajActions;
	
	public ModeleTablePortefeuille() {
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
	
	private class ObservateurAjoutAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationActionAjoutee notificationActionAjoutee = 
					ModeleTablePortefeuille.this.getPortefeuille().getNotificationActionAjoutee();
			if (notificationActionAjoutee == arg0) {
				int row = notificationActionAjoutee.getRow();
				ModeleTablePortefeuille.this.fireTableRowsInserted(row, row);
			}
		}
	}
	
	private class ObservateurSuppressionAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationActionSupprimee notificationActionSupprimee = 
					ModeleTablePortefeuille.this.getPortefeuille().getNotificationActionSupprimee();
			if (notificationActionSupprimee == arg0) {
				int row = notificationActionSupprimee.getRow();
				ModeleTablePortefeuille.this.fireTableRowsDeleted(row, row);
			}
		}
	}
	
	private class ObservateurMajActions implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationMajActions notificationMajActions = 
					ModeleTablePortefeuille.this.getPortefeuille().getNotificationMajActions();
			if (notificationMajActions == arg0) {
				ModeleTablePortefeuille.this.fireTableDataChanged();
			}
		}
	}

	// GETTERS et SETTERS
	
	public Portefeuille getPortefeuille() {
		return portefeuille;
	}

	public void setPortefeuille(Portefeuille portefeuille) {
		Portefeuille portefeuillePrecedant = this.getPortefeuille();
		if (portefeuillePrecedant != null) {
			portefeuillePrecedant.getNotificationActionAjoutee().deleteObserver(this.observateurAjoutAction);
			portefeuillePrecedant.getNotificationActionSupprimee().deleteObserver(this.observateurSuppressionAction);
			portefeuillePrecedant.getNotificationMajActions().deleteObserver(this.observateurMajActions);
		}
		this.portefeuille = portefeuille;
		if (portefeuille != null) {
			portefeuille.getNotificationActionAjoutee().addObserver(this.observateurAjoutAction);
			portefeuille.getNotificationActionSupprimee().addObserver(this.observateurSuppressionAction);
			portefeuille.getNotificationMajActions().addObserver(this.observateurMajActions);
		}
		this.fireTableDataChanged();
	}

}
