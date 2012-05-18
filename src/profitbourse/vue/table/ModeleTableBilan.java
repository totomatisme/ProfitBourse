package profitbourse.vue.table;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import profitbourse.modele.Portefeuille;
import profitbourse.vue.Controleur;

public class ModeleTableBilan extends AbstractTableModel {

	private static final long serialVersionUID = 8726628853425036216L;
	private Controleur controleur;
	private Portefeuille portefeuille;
	private ArrayList<String> entetes;
	private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
	private ObservateurModificationPortefeuille observateurModificationPortefeuille;
	
	public ModeleTableBilan(Controleur controleur) {
		this.controleur = controleur;
		this.portefeuille = null;
		this.entetes = new ArrayList<String>();
		this.entetes.add("Nom");
		this.entetes.add("Total achat");
		this.entetes.add("Total actuel");
		this.entetes.add("Plus-value");
		this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
		this.observateurModificationPortefeuille = new ObservateurModificationPortefeuille();
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
			return 1;
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
				return this.portefeuille.getNom();
			case 1:
				return this.portefeuille.calculerTotalAchat();
			case 2:
				return this.portefeuille.calculerTotalActuel();
			case 3:
				return this.portefeuille.calculerPlusValue();
			default:
				return null; //Ne devrait jamais arriver
			}
		}
	}
	
	private class ObservateurChangementDePortefeuilleCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
			Portefeuille portefeuillePrecedant = portefeuille;
			if (portefeuillePrecedant != null) {
				portefeuillePrecedant.getNotificationModificationPortefeuille().deleteObserver(observateurModificationPortefeuille);
			}
			portefeuille = nouveauPortefeuille;
			if (portefeuille != null) {
				portefeuille.getNotificationModificationPortefeuille().addObserver(observateurModificationPortefeuille);
			}
			fireTableDataChanged();
		}
	}

	private class ObservateurModificationPortefeuille implements Observer {
		public void update(Observable arg0, Object arg1) {
			Portefeuille.NotificationModificationPortefeuille notificationModificationPortefeuille = portefeuille.getNotificationModificationPortefeuille();
			if (notificationModificationPortefeuille == arg0) {
				fireTableRowsUpdated(0, 0);
			}
		}
	}
}
