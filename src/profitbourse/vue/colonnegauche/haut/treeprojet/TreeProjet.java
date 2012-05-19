package profitbourse.vue.colonnegauche.haut.treeprojet;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;

import profitbourse.modele.Projet;
import profitbourse.vue.Controleur;

public class TreeProjet extends JTree {

	private static final long serialVersionUID = -5524306832695783707L;
	private Controleur controleur;
	private ModeleTreeProjet modeleTreeProjet;
	private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
	
	public TreeProjet(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		setModeleTreeProjet(new ModeleTreeProjet(this.controleur, null));
		
		this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
		this.controleur.getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
	}
	
	private class ObservateurChangementDeProjetCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Controleur.NotificationChangementDeProjetCourant notificationChangementDeProjetCourant = controleur.getNotificationChangementDeProjetCourant();
			if (notificationChangementDeProjetCourant == arg0) {
				// On enlève les observateurs qui observent l'ancien projet car ça peut faire une fuite de mémoire.
				modeleTreeProjet.supprimerLesObservateurs();
				// On change le modèle du JTree.
				setModeleTreeProjet(new ModeleTreeProjet(controleur, (Projet)arg1));
			}
		}
	}

	public void setModeleTreeProjet(ModeleTreeProjet modeleTreeProjet) {
		this.modeleTreeProjet = modeleTreeProjet;
		setModel(modeleTreeProjet);
	}
	
}
