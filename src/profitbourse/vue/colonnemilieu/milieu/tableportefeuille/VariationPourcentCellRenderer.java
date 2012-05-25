package profitbourse.vue.colonnemilieu.milieu.tableportefeuille;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import profitbourse.modele.VariationPourcent;
import profitbourse.modele.preferences.GestionnairePreferences;

public class VariationPourcentCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -2982154961436013309L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		VariationPourcent variationPourcent = (VariationPourcent)value;
		setText(variationPourcent.toString());
		
		Color couleurCell = null;
		Color couleurTexte = null;
		if (isSelected) {
			couleurCell = GestionnairePreferences.getListSelectionBackground();
			couleurTexte = GestionnairePreferences.getListSelectionForeground();
		} else {
			couleurTexte = Color.BLACK;
			if (variationPourcent.isZero()) {
				couleurCell = GestionnairePreferences.getCouleurZero();
			} else {
				if (variationPourcent.isPositif()) {
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
