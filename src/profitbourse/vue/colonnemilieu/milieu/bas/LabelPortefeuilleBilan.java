package profitbourse.vue.colonnemilieu.milieu.bas;

import profitbourse.vue.Controleur;
import profitbourse.vue.colonnemilieu.milieu.AbstractLabelPortefeuille;

public class LabelPortefeuilleBilan extends AbstractLabelPortefeuille {
	
	private static final long serialVersionUID = -1878846526174537760L;

	public LabelPortefeuilleBilan(Controleur controleur) {
		super(controleur);
	}

	protected void majTextLabel() {
		if (this.portefeuille == null) {
			this.setText("Pas de portefeuille selectionné.");
		} else {
			this.setText("Bilan du portefeuille '" + this.portefeuille.getNom() + "' :");
		}
	}

}
