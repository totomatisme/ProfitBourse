package profitbourse.vue.colonnemilieu.milieu.tableportefeuille;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import profitbourse.modele.Money;
import profitbourse.modele.preferences.GestionnairePreferences;

public class PlusValueCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 944095891770368316L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Money plusValue = (Money)value;
		setText(plusValue.toString());
		
		Color couleurCell = null;
		Color couleurTexte = null;
		if (isSelected) {
			couleurCell = GestionnairePreferences.getListSelectionBackground();
			couleurTexte = GestionnairePreferences.getListSelectionForeground();
		} else {
			couleurTexte = Color.BLACK;
			if (plusValue.isZero()) {
				couleurCell = GestionnairePreferences.getCouleurZero();
			} else {
				if (plusValue.isPlus()) {
					couleurCell = GestionnairePreferences.getCouleurPositive();
				} else {
					couleurCell = GestionnairePreferences.getCouleurNegative();
				}
			}
		}
		setBackground(couleurCell);
		setForeground(couleurTexte);
		
		return this;
	}

}
