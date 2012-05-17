package profitbourse.vue;

public class LabelPortefeuilleContenu extends AbstractLabelPortefeuille {

	private static final long serialVersionUID = -4015994418525624626L;

	public LabelPortefeuilleContenu(Controleur controleur) {
		super(controleur);
	}

	protected void majTextLabel() {
		if (this.portefeuille == null) {
			this.setText("Pas de portefeuille selectionné.");
		} else {
			this.setText("Actions du portefeuille '" + this.portefeuille.getNom() + "' :");
		}
	}

}
