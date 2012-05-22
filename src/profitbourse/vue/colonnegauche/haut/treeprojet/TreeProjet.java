package profitbourse.vue.colonnegauche.haut.treeprojet;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import profitbourse.modele.Portefeuille;
import profitbourse.modele.Projet;
import profitbourse.vue.Controleur;

public class TreeProjet extends JTree {

	private static final long serialVersionUID = -5524306832695783707L;
	private Controleur controleur;
	private ModeleTreeProjet modeleTreeProjet;
	private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
	private SelectionDansLeTreeListener selectionDansLeTreeListener;
	private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
	
	public TreeProjet(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		setModeleTreeProjet(new ModeleTreeProjet(this.controleur, null));
		
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.selectionDansLeTreeListener = new SelectionDansLeTreeListener();
		this.addTreeSelectionListener(this.selectionDansLeTreeListener);
		
		this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
		this.controleur.getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
		
		this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
		this.controleur.getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
	}
	
	public void setModeleTreeProjet(ModeleTreeProjet modeleTreeProjet) {
		this.modeleTreeProjet = modeleTreeProjet;
		setModel(modeleTreeProjet);
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
	
	private class SelectionDansLeTreeListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent arg0) {
			Object nodeObject = getLastSelectedPathComponent();
			if (nodeObject == null) {
				return;
			} else {
				if (nodeObject instanceof Portefeuille) {
					Portefeuille portefeuilleSelection = (Portefeuille)nodeObject;
					controleur.changerDePortefeuilleActuel(portefeuilleSelection);
				}
			}
		}
	}
	
	private class ObservateurChangementDePortefeuilleCourant implements Observer {
		public void update(Observable arg0, Object arg1) {
			Controleur.NotificationChangementDePortefeuilleCourant notificationChangementDePortefeuilleCourant = controleur.getNotificationChangementDePortefeuilleCourant();
			if (notificationChangementDePortefeuilleCourant == arg0) {
				Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
				if (nouveauPortefeuille != null) {
					removeTreeSelectionListener(selectionDansLeTreeListener); // c'est un peu dégueulasse, mais c'est la faute de Swing qui est mal fait.
					getSelectionModel().setSelectionPath(new TreePath(new Object[] {controleur.getProjetActuel(), nouveauPortefeuille}));
					addTreeSelectionListener(selectionDansLeTreeListener);
				}
			}
		}
	}
	
}
