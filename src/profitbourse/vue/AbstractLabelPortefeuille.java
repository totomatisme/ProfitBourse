package profitbourse.vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import profitbourse.modele.Portefeuille;

public abstract class AbstractLabelPortefeuille extends JLabel {

	private static final long serialVersionUID = -1188164499617200534L;
	protected Controleur controleur;
	protected Portefeuille portefeuille;
	protected ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
	
	public AbstractLabelPortefeuille(Controleur controleur) {
		this.controleur = controleur;
		this.portefeuille = null;
		this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
		this.controleur.getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
		this.majTextLabel();
	}
	
	protected abstract void majTextLabel();
	
	protected class ObservateurChangementDePortefeuilleCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			portefeuille = (Portefeuille)arg1;
			majTextLabel();
		}
	}
	
}
