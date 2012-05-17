package profitbourse.modele;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import profitbourse.modele.majaleatoire.GestionnaireMajWeb;


public class Portefeuille implements Serializable {

	private static final long serialVersionUID = -4302082586384377723L;
	private String nom;
	private ArrayList<Action> actions;
	private Currency devise;
	private Projet projet;
	private transient NotificationActionAjoutee notificationActionAjoutee;
	private transient NotificationActionSupprimee notificationActionSupprimee;
	private transient NotificationMajActions notificationMajActions;
	
	public Portefeuille(String nom, Currency devise, Projet projet) {
		this.nom = nom;
		this.devise = devise;
		this.projet = projet;
		this.actions = new ArrayList<Action>();
		this.notificationActionAjoutee = new NotificationActionAjoutee();
		this.notificationActionSupprimee = new NotificationActionSupprimee();
		this.notificationMajActions = new NotificationMajActions();
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributsts "transient".
	 * @return l'objet correctement initialisé
	 * @throws ObjectStreamException
	 */
	private Object readResolve() throws ObjectStreamException {
		this.notificationActionAjoutee = new NotificationActionAjoutee();
		this.notificationActionSupprimee = new NotificationActionSupprimee();
		this.notificationMajActions = new NotificationMajActions();
		return this;
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
	
	public void majToutesLesActions() {
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			GestionnaireMajWeb.majAction(it.next());
		}
		this.notificationMajActions.notifierMajAction();
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
	
	public String portefeuilleEtActionsToString() {
		String affichage = this.toString();
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			affichage = affichage + "\n\t" + it.next().toString();
		}
		return affichage;
	}
	
	public String toString() {
		return "Portefeuille : '" + this.getNom() + "' en " + this.getDevise() + ", prix total à l'achat "
				+ this.calculerTotalAchat() + ", prix total actuel " + this.calculerTotalActuel() + ".";
	}
	
	public class ActionNonPresenteDansLePortefeuille extends Exception {
		private static final long serialVersionUID = -4261702974891478570L;
	}
	
	public class ActionDejaPresenteDansLePortefeuille extends Exception {
		private static final long serialVersionUID = 1650795751351991726L;
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
		public void notifierMajAction() {
			this.setChanged();
			this.notifyObservers();
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
	
}
