package profitbourse.vue.colonnegauche.haut.treeprojet;

import java.util.Observable;
import java.util.Observer;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

import profitbourse.modele.Projet;
import profitbourse.vue.Controleur;

public class ModeleTreeProjet extends AbstractTreeModel {

	@SuppressWarnings("unused")
	private Controleur controleur;
	private Projet projet;
	private ObservateurAjoutPortefeuille observateurAjoutPortefeuille;
	private ObservateurSuppressionPortefeuille observateurSuppressionPortefeuille;
	
	public ModeleTreeProjet(Controleur controleur, Projet projet) {
		this.controleur = controleur;
		this.projet = projet;
		this.observateurAjoutPortefeuille = new ObservateurAjoutPortefeuille();
		this.observateurSuppressionPortefeuille = new ObservateurSuppressionPortefeuille();
		if (this.projet != null) {
			this.projet.getNotificationPortefeuilleAjoute().addObserver(this.observateurAjoutPortefeuille);
			this.projet.getNotificationPortefeuilleSupprime().addObserver(this.observateurSuppressionPortefeuille);
		}
	}
	
	public Object getChild(Object arg0, int arg1) {
		if (this.projet == null) {
			return null;
		} else {
			return this.projet.getPortefeuilles().get(arg1);
		}
	}

	public int getChildCount(Object arg0) {
		if (arg0 == null) {
			return 0;
		} else if (arg0 == this.projet) {
			return this.projet.getPortefeuilles().size();
		} else {
			return 0;
		}
	}

	public int getIndexOfChild(Object arg0, Object arg1) {
		if (this.projet == null) {
			return 0;
		} else {
			return this.projet.getPortefeuilles().indexOf(arg1);
		}
	}

	public Object getRoot() {
		return this.projet;
	}

	public boolean isLeaf(Object arg0) {
		if (arg0 == projet) {
			return false;
		} else {
			return true;
		}
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
		
	}
	
	private class ObservateurAjoutPortefeuille implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationPortefeuilleAjoute notificationPortefeuilleAjoute = projet.getNotificationPortefeuilleAjoute();
			if (notificationPortefeuilleAjoute == arg0) {
				int row = notificationPortefeuilleAjoute.getRow();
				int[] childIndices = {row};
				Object[] children = {arg1};
				TreeModelEvent e = new TreeModelEvent(this, new TreePath(new Object[] {projet}), childIndices, children);
				fireTreeNodesInserted(e);
			}
		}
	}
	
	private class ObservateurSuppressionPortefeuille implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationPortefeuilleSupprime notificationPortefeuilleSupprime = projet.getNotificationPortefeuilleSupprime();
			if (notificationPortefeuilleSupprime == arg0) {
				int row = notificationPortefeuilleSupprime.getRow();
				int[] childIndices = {row};
				Object[] children = {arg1};
				TreeModelEvent e = new TreeModelEvent(this, new TreePath(new Object[] {projet}), childIndices, children);
				fireTreeNodesRemoved(e);
			}
		}
	}
	
	public void supprimerLesObservateurs() {
		if (this.projet != null) {
			this.projet.getNotificationPortefeuilleAjoute().deleteObserver(this.observateurAjoutPortefeuille);
			this.projet.getNotificationPortefeuilleSupprime().deleteObserver(this.observateurSuppressionPortefeuille);
		}
	}

	public ObservateurAjoutPortefeuille getObservateurAjoutPortefeuille() {
		return observateurAjoutPortefeuille;
	}

	public ObservateurSuppressionPortefeuille getObservateurSuppressionPortefeuille() {
		return observateurSuppressionPortefeuille;
	}

}
