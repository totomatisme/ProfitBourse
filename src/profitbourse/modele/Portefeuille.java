package profitbourse.modele;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import profitbourse.modele.majthomas.GestionnaireMajWeb;


public class Portefeuille implements Serializable {

	private static final long serialVersionUID = -4302082586384377723L;
	private String nom;
	private ArrayList<Action> actions;
	private Currency devise;
	private Projet projet;
	private transient NotificationActionAjoutee notificationActionAjoutee;
	private transient NotificationActionSupprimee notificationActionSupprimee;
	private transient NotificationMajActions notificationMajActions;
	private transient NotificationModificationPortefeuille notificationModificationPortefeuille;
	private transient ObservateurDetectionModificationPortefeuille observateurDetectionModificationPortefeuille;
	private transient NotificationModificationAction notificationModificationAction;
	private transient ObservateurModificationAction observateurModificationAction;
	
	public Portefeuille(String nom, Currency devise, Projet projet) {
		this.nom = nom;
		this.devise = devise;
		this.projet = projet;
		this.actions = new ArrayList<Action>();
		this.notificationActionAjoutee = new NotificationActionAjoutee();
		this.notificationActionSupprimee = new NotificationActionSupprimee();
		this.notificationMajActions = new NotificationMajActions();
		this.notificationModificationPortefeuille = new NotificationModificationPortefeuille();
		this.notificationModificationAction = new NotificationModificationAction();
		this.observateurModificationAction = new ObservateurModificationAction();
		this.observateurDetectionModificationPortefeuille = new ObservateurDetectionModificationPortefeuille();
		
		this.notificationActionAjoutee.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationActionSupprimee.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationMajActions.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationModificationAction.addObserver(this.observateurDetectionModificationPortefeuille);
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributs "transient".
	 */
	public void initialisationApresChargement() {
		this.notificationActionAjoutee = new NotificationActionAjoutee();
		this.notificationActionSupprimee = new NotificationActionSupprimee();
		this.notificationMajActions = new NotificationMajActions();
		this.notificationModificationPortefeuille = new NotificationModificationPortefeuille();
		this.notificationModificationAction = new NotificationModificationAction();
		this.observateurModificationAction = new ObservateurModificationAction();
		this.observateurDetectionModificationPortefeuille = new ObservateurDetectionModificationPortefeuille();
		
		// observateurDetectionModificationPortefeuille observe toute les modifications possibles de ce portefeuille.
		this.notificationActionAjoutee.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationActionSupprimee.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationMajActions.addObserver(this.observateurDetectionModificationPortefeuille);
		this.notificationModificationAction.addObserver(this.observateurDetectionModificationPortefeuille);
		
		// On fait l'initialisation de toutes les actions de ce portefeuille.
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			it.next().initialisationApresChargement();
		}
	}
	
	public void ajouterNouvelleAction(Action action) throws ActionDejaPresenteDansLePortefeuille {
		if (this.getActions().contains(action)) {
			throw new ActionDejaPresenteDansLePortefeuille();
		}
		this.getActions().add(action);
		this.notificationActionAjoutee.notifierAjoutAction(action, this.getActions().size()-1);
	}
	
	public void supprimerTotalementAction(Action action) throws ActionNonPresenteDansLePortefeuille {
		int row = this.getActions().indexOf(action);
		if (row == -1) {
			throw new ActionNonPresenteDansLePortefeuille();
		} else {
			this.getActions().remove(row);
			this.notificationActionSupprimee.notifierSuppressionAction(action, row);
		}
	}
	
	public void majToutesLesActions() throws Exception {
		GestionnaireMajWeb.majActions(this.getActions());
		this.notificationMajActions.notifierMajActions();
	}
	
	public Money calculerTotalAchat() {
		Money somme = new Money(new BigDecimal(0.0), this.getDevise());
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			somme = somme.plus(it.next().calculerTotalAchat());
		}
		return somme;
	}
	
	public Money calculerTotalActuel() {
		Money somme = new Money(new BigDecimal(0.0), this.getDevise());
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			somme = somme.plus(it.next().calculerTotalActuel());
		}
		return somme;
	}
	
	public Money calculerPlusValue() {
		return this.calculerTotalActuel().minus(this.calculerTotalAchat());
	}
	
	public String portefeuilleEtActionsToString() {
		String affichage = this.toString();
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			affichage = affichage + "\n\t" + it.next().toString();
		}
		return affichage;
	}
	
	public String toString() {
		/*return "Portefeuille : '" + this.getNom() + "' en " + this.getDevise() + ", prix total à l'achat " 
				+ this.calculerTotalAchat() + ", prix total actuel " + this.calculerTotalActuel() + ".";
				*/
		return this.getNom();
	}
	
	public class ActionNonPresenteDansLePortefeuille extends Exception {
		private static final long serialVersionUID = -4261702974891478570L;
		public ActionNonPresenteDansLePortefeuille() {
			super("L'action n'est pas présente dans le portefeuille !");
		}
	}
	
	public class ActionDejaPresenteDansLePortefeuille extends Exception {
		private static final long serialVersionUID = 1650795751351991726L;
		public ActionDejaPresenteDansLePortefeuille() {
			super("L'action est déjà présente dans le portefeuille !");
		}
	}
	
	public class NotificationActionAjoutee extends Observable {
		private int row = 0;
		
		public void notifierAjoutAction(Action actionAjoutee, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(actionAjoutee);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationActionSupprimee extends Observable {
		private int row = 0;
		
		public void notifierSuppressionAction(Action actionSupprimee, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(actionSupprimee);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationMajActions extends Observable {
		public void notifierMajActions() {
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public class NotificationModificationAction extends Observable {
		private int row = 0;
		
		public void notifierModificationAction(Action action, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(action);
		}
		
		public int getRow() {
			return row;
		}
	}
	
	private class ObservateurModificationAction implements Observer {
		public void update(Observable arg0, Object arg1) {
			Action actionModifiee = (Action)arg1;
			int index = actions.indexOf(actionModifiee);
			if (index != -1) {
				notificationModificationAction.notifierModificationAction(actionModifiee, index);
			}
		}
	}
	
	public class NotificationModificationPortefeuille extends Observable {
		public void notifierModificationPortefeuille(Portefeuille portefeuille) {
			this.setChanged();
			this.notifyObservers(portefeuille);
		}
	}
	
	private class ObservateurDetectionModificationPortefeuille implements Observer {
		public void update(Observable arg0, Object arg1) {
			notificationModificationPortefeuille.notifierModificationPortefeuille(Portefeuille.this);
		}
	}
	
	// GETTERS et SETTERS

	public ArrayList<Action> getActions() {
		return actions;
	}

	public String getNom() {
		return nom;
	}

	public Currency getDevise() {
		return devise;
	}

	public Projet getProjet() {
		return projet;
	}

	public NotificationActionAjoutee getNotificationActionAjoutee() {
		return notificationActionAjoutee;
	}

	public NotificationActionSupprimee getNotificationActionSupprimee() {
		return notificationActionSupprimee;
	}

	public NotificationMajActions getNotificationMajActions() {
		return notificationMajActions;
	}

	public NotificationModificationPortefeuille getNotificationModificationPortefeuille() {
		return notificationModificationPortefeuille;
	}

	public ObservateurDetectionModificationPortefeuille getObservateurDetectionModificationPortefeuille() {
		return observateurDetectionModificationPortefeuille;
	}

	public ObservateurModificationAction getObservateurModificationAction() {
		return observateurModificationAction;
	}

	public NotificationModificationAction getNotificationModificationAction() {
		return notificationModificationAction;
	}
	
}
