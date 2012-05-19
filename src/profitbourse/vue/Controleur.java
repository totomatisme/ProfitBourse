package profitbourse.vue;

import java.io.File;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import profitbourse.Console;
import profitbourse.modele.*;
import profitbourse.modele.Portefeuille.ActionDejaPresenteDansLePortefeuille;
import profitbourse.modele.preferences.GestionnairePreferences;
import profitbourse.modele.sauvegarde.GestionnaireSauvegarde;

public class Controleur {
	
	private Projet projetActuel;
	private Portefeuille portefeuilleActuel;
	private Action actionActuelle;
	
	private FenetrePrincipale fenetrePrincipale;
	
	private NotificationChangementDePortefeuilleCourant notificationChangementDePortefeuilleCourant;
	private NotificationChangementDeProjetCourant notificationChangementDeProjetCourant;
	private ObservateurSuppressionPortefeuille observateurSuppressionPortefeuille;
	
	public Controleur() {
		this.projetActuel = null;
		this.portefeuilleActuel = null;
		this.actionActuelle = null;
		this.notificationChangementDePortefeuilleCourant = new NotificationChangementDePortefeuilleCourant();
		this.notificationChangementDeProjetCourant = new NotificationChangementDeProjetCourant();
		this.observateurSuppressionPortefeuille = new ObservateurSuppressionPortefeuille();
		
		this.construireInterface();
		this.menuPrincipalConsole();
	}
	
	private void construireInterface() {
		Locale.setDefault(Locale.FRANCE);
		
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.fenetrePrincipale = new FenetrePrincipale(this);
		this.fenetrePrincipale.setVisible(true);
	}
	
	public void changerDePortefeuilleActuel(Portefeuille nouveauPortefeuilleActuel) {
		if (nouveauPortefeuilleActuel != this.portefeuilleActuel) {
			this.portefeuilleActuel = nouveauPortefeuilleActuel;
			this.notificationChangementDePortefeuilleCourant.notifierChangementDePortefeuilleCourant(nouveauPortefeuilleActuel);
		}
	}
	
	public void changerDeProjetActuel(Projet nouveauProjetActuel) {
		if (nouveauProjetActuel != this.projetActuel) {
			if (this.projetActuel != null) {
				this.projetActuel.getNotificationPortefeuilleSupprime().deleteObserver(this.observateurSuppressionPortefeuille);
			}
			this.projetActuel = nouveauProjetActuel;
			this.changerDePortefeuilleActuel(null);
			this.notificationChangementDeProjetCourant.notifierChangementDeProjetCourant(nouveauProjetActuel);
			if (nouveauProjetActuel != null) {
				nouveauProjetActuel.getNotificationPortefeuilleSupprime().addObserver(this.observateurSuppressionPortefeuille);
			}
		}
	}
	
	public class NotificationChangementDePortefeuilleCourant extends Observable {
		public void notifierChangementDePortefeuilleCourant(Portefeuille portefeuille) {
			this.setChanged();
			this.notifyObservers(portefeuille);
		}
	}
	
	public class NotificationChangementDeProjetCourant extends Observable {
		public void notifierChangementDeProjetCourant(Projet projet) {
			this.setChanged();
			this.notifyObservers(projet);
		}
	}
	
	private class ObservateurSuppressionPortefeuille implements Observer {
		public void update(Observable arg0, Object arg1) {
			Projet.NotificationPortefeuilleSupprime notificationPortefeuilleSupprime = projetActuel.getNotificationPortefeuilleSupprime();
			if (notificationPortefeuilleSupprime == arg0) {
				changerDePortefeuilleActuel(null);
			}
		}
	}
	
	// GETTERS et SETTERS

	public NotificationChangementDePortefeuilleCourant getNotificationChangementDePortefeuilleCourant() {
		return notificationChangementDePortefeuilleCourant;
	}
	
	public NotificationChangementDeProjetCourant getNotificationChangementDeProjetCourant() {
		return notificationChangementDeProjetCourant;
	}

	public Projet getProjetActuel() {
		return projetActuel;
	}

	public void setProjetActuel(Projet projetActuel) {
		this.projetActuel = projetActuel;
	}

	public Portefeuille getPortefeuilleActuel() {
		return portefeuilleActuel;
	}

	public Action getActionActuelle() {
		return actionActuelle;
	}

	public void setActionActuelle(Action actionActuelle) {
		this.actionActuelle = actionActuelle;
	}
	
	public void menuPrincipalConsole() {
		while (true) {
			System.out.println("");
			System.out.println("_____________________________________________________________________________________");
			System.out.println(">>>>>>>>>> ProfitBourse");
			System.out.println("Que voulez-vous faire ?");
			System.out.println("\t'n' pour créer un nouveau projet.");
			System.out.println("\t'c' pour charger un projet existant.");
			System.out.println("\t'q' pour quitter le logiciel.");
			System.out.println("");
			
			String commandeString = Console.lireLigne();
			if (commandeString.equals("")) {
				//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
				commandeString = Console.lireLigne();
			}
			char commande = commandeString.charAt(0);
			
			switch (commande) {
			case 'n':
				System.out.println("Entrez le nom du nouveau projet :");
				String nom = Console.lireLigne();
				if (nom.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					nom = Console.lireLigne();
				}
				this.changerDeProjetActuel(new Projet(nom));
				System.out.println("Le nouveau projet a été créé avec succès.");
				this.menuProjetConsole();
				break;
				
			case 'c':
				System.out.println("Entrez l'adresse du projet (par défaut à l'adresse '"
						+ GestionnairePreferences.getCheminSauvegarde().toString() + "').");
				String chemin = Console.lireLigne();
				if (chemin.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					chemin = Console.lireLigne();
				}
				if (chemin.equals("")) {
					chemin = GestionnairePreferences.getCheminSauvegarde().toString();
				}
				try {
					this.changerDeProjetActuel(GestionnaireSauvegarde.chargerProjetDepuisFichier(new File(chemin)));
					System.out.println("Le nouveau projet a été chargé avec succès.");
					this.menuProjetConsole();
				} catch (Exception e) {
					System.out.println("Erreur lors du chargement du projet à l'adresse : '" + chemin + "'.");
					e.printStackTrace();
				}
				break;
				
			case 'q':
				System.out.println("Fin du programme.");
				System.exit(0);
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}
	
	public void menuProjetConsole() {
		while (true) {
			System.out.println("");
			System.out.println("_____________________________________________________________________________________");
			System.out.println(">>>>>>>>>> " + this.projetActuel.toString());
			System.out.println("Que voulez-vous faire dans ce projet ?");
			System.out.println("\t'l' pour lister les portefeuilles et indices de référence présents dans le projet actuel.");
			System.out.println("\t'c' pour afficher le menu d'un portefeuille.");
			System.out.println("\t'a' pour ajouter un nouveau portefeuille.");
			System.out.println("\t's' pour supprimer un portefeuille existant.");
			System.out.println("\t'A' pour ajouter un indice de référence.");
			System.out.println("\t'S' pour supprimer un indice de référence.");
			System.out.println("\t'm' pour mettre à jour tous les portefeuilles et tous les indices de référence.");
			System.out.println("\t'e' pour enregistrer le projet sous...");
			System.out.println("\t'E' pour enregistrer le projet à l'emplacement actuel.");
			System.out.println("\t'f' pour fermer le projet (sans le sauvegarder).");
			System.out.println("");
			
			String commandeString = Console.lireLigne();
			if (commandeString.equals("")) {
				//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
				commandeString = Console.lireLigne();
			}
			char commande = commandeString.charAt(0);
			
			switch (commande) {
			case 'l':
				System.out.println(this.projetActuel.projetEtPortefeuillesEtIndicesToString());
				break;
				
			case 'c':
				String affichage = "Entrez le numero du portefeuille à afficher :";
				int index = 0;
				Iterator<Portefeuille> it = this.projetActuel.getPortefeuilles().iterator();
				while (it.hasNext()) {
					affichage = affichage + "\n\t'" + index + "' - " + it.next().toString();
					index++;
				}
				System.out.println(affichage);
				int choix = Console.lireInt();
				try {
					this.changerDePortefeuilleActuel(this.projetActuel.getPortefeuilles().get(choix));
					this.menuPortefeuilleConsole();
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
					e.printStackTrace();
				}
				break;
				
			case 'a':
				System.out.println("Entrez un nom pour le portefeuille :");
				String nom = Console.lireLigne();
				if (nom.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					nom = Console.lireLigne();
				}
				System.out.println("Entrez la monnaie (par defaut 'EUR') :");
				String codeDevise = Console.lireLigne();
				if (codeDevise.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					codeDevise = Console.lireLigne();
				}
				Currency devise = null;
				try {
					devise = Currency.getInstance(codeDevise);
				} catch (IllegalArgumentException e) {
					System.out.println("Monnaie non reconnue 'EUR' sera choisie à la place.");
					devise = Currency.getInstance("EUR");
				}
				Portefeuille nouveauPortefeuille = new Portefeuille(nom, devise, this.projetActuel);
				this.projetActuel.ajouterNouveauPortefeuille(nouveauPortefeuille);
				System.out.println("Le portefeuille '" + nouveauPortefeuille.getNom() + "' a été ajouté.");
				break;
				
			case 's':
				String affichage2 = "Entrez le numero du portefeuille à supprimer :";
				int index2 = 0;
				Iterator<Portefeuille> it2 = this.projetActuel.getPortefeuilles().iterator();
				while (it2.hasNext()) {
					affichage2 = affichage2 + "\n\t'" + index2 + "' - " + it2.next().toString();
					index2++;
				}
				System.out.println(affichage2);
				int choix2 = Console.lireInt();
				Portefeuille portefeuilleSupprime = null;
				try {
					portefeuilleSupprime = this.projetActuel.getPortefeuilles().get(choix2);
					this.projetActuel.supprimerPortefeuille(portefeuilleSupprime);
					System.out.println("Le portefeuille '" + portefeuilleSupprime.getNom() + "' a été supprimé du projet.");
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'A':
				System.out.println("Entrez le code de l'indice :");
				String codeIndice = Console.lireLigne();
				if (codeIndice.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					codeIndice = Console.lireLigne();
				}
				System.out.println("Entrez le nom de l'indice :");
				String nomIndice = Console.lireLigne();
				if (nomIndice.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					nomIndice = Console.lireLigne();
				}
				Indice nouvelIndice = new Indice(nomIndice, codeIndice, this.projetActuel);
				this.projetActuel.ajouterNouvelIndice(nouvelIndice);
				nouvelIndice.majWeb();
				System.out.println("L'indice '" + nouvelIndice.getNom() + "' a été ajouté.");
				break;
				
			case 'S':
				String affichage3 = "Entrez le numero de l'indice à supprimer :";
				int index3 = 0;
				Iterator<Indice> it3 = this.projetActuel.getIndices().iterator();
				while (it3.hasNext()) {
					affichage3 = affichage3 + "\n\t'" + index3 + "' - " + it3.next().toString();
					index3++;
				}
				System.out.println(affichage3);
				int choix3 = Console.lireInt();
				Indice indiceSupprime = null;
				try {
					indiceSupprime = this.projetActuel.getIndices().get(choix3);
					this.projetActuel.supprimerIndice(indiceSupprime);
					System.out.println("L'indice '" + indiceSupprime.getNom() + "' a été supprimé du projet.");
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'm':
				this.projetActuel.majTousLesPortefeuillesEtIndices();
				System.out.println("Mise à jour des portefeuilles et des indices du projet effectuée.");
				System.out.println(this.projetActuel.projetEtPortefeuillesEtIndicesToString());
				break;
				
			case 'e':
				System.out.println("Entrez le chemin de sauvegarde désiré (par défaut : '" + this.projetActuel.getCheminSauvegarde() + "'.");
				String cheminString = Console.lireLigne();
				if (cheminString.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					cheminString = Console.lireLigne();
				}
				File chemin = new File(cheminString);
				this.projetActuel.setCheminSauvegarde(chemin);
				try {
					this.projetActuel.enregistrerProjet();
					System.out.println("Projet enregistré à l'adresse '" + chemin + "' avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur lors de l'enregistrement. (Attention il faut que les dossiers existent déjà !).");
					e.printStackTrace();
				}
				break;
				
			case 'E':
				try {
					this.projetActuel.enregistrerProjet();
					System.out.println("Projet enregistré à l'adresse '" + this.projetActuel.getCheminSauvegarde() + "' avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur lors de l'enregistrement. (Attention il faut que les dossiers existent déjà !).");
					e.printStackTrace();
				}
				break;
				
			case 'f':
				System.out.println("Projet fermé.");
				this.changerDeProjetActuel(null);
				this.menuPrincipalConsole();
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}
	
	public void menuPortefeuilleConsole() {
		while (true) {
			System.out.println("");
			System.out.println("____________________________________________________________________");
			System.out.println(">>>>>>>>>> " + this.portefeuilleActuel.toString());
			System.out.println("Que voulez-vous faire dans ce portefeuille ?");
			System.out.println("\t'l' pour lister les actions présentes dans le portefeuille actuel.");
			System.out.println("\t'c' pour afficher le menu d'une action.");
			System.out.println("\t'm' pour mettre à jour toutes les actions du portefeuille.");
			System.out.println("\t'a' pour ajouter une nouvelle action.");
			System.out.println("\t's' pour vendre totalement une action existante.");
			System.out.println("\t'r' pour retourner au menu supérieur.");
			System.out.println("");
			
			String commandeString = Console.lireLigne();
			if (commandeString.equals("")) {
				//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
				commandeString = Console.lireLigne();
			}
			char commande = commandeString.charAt(0);
			
			switch (commande) {
			case 'l':
				System.out.println(this.portefeuilleActuel.portefeuilleEtActionsToString());
				break;
				
			case 'c':
				String affichage = "Entrez le numero de l'action à afficher :";
				int index = 0;
				Iterator<Action> it = this.portefeuilleActuel.getActions().iterator();
				while (it.hasNext()) {
					affichage = affichage + "\n\t'" + index + "' - " + it.next().toString();
					index++;
				}
				System.out.println(affichage);
				int choix = Console.lireInt();
				try {
					this.actionActuelle = this.portefeuilleActuel.getActions().get(choix);
					this.menuActionConsole();
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'a':
				System.out.println("Entrez le code de l'action :");
				String code = Console.lireLigne();
				if (code.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					code = Console.lireLigne();
				}
				System.out.println("Entrez le nom de l'action :");
				String nom = Console.lireLigne();
				if (nom.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					nom = Console.lireLigne();
				}
				System.out.println("Entrez la quantité achetée :");
				int quantite = Console.lireInt();
				if (quantite <= 0) {
					System.out.println("Quantité négative impossible.");
					break;
				}
				Action nouvelleAction = new Action(nom, code, quantite, this.portefeuilleActuel);
				try {
					this.portefeuilleActuel.ajouterNouvelleAction(nouvelleAction);
				} catch (ActionDejaPresenteDansLePortefeuille e1) {
					e1.printStackTrace();
				}
				nouvelleAction.premiereMajWeb();
				System.out.println("L'action '" + nouvelleAction.getNom() + "' a été ajoutée.");
				break;
				
			case 's':
				String affichage2 = "Entrez le numero de l'action à supprimer :";
				int index2 = 0;
				Iterator<Action> it2 = this.portefeuilleActuel.getActions().iterator();
				while (it2.hasNext()) {
					affichage2 = affichage2 + "\n\t'" + index2 + "' - " + it2.next().toString();
					index2++;
				}
				System.out.println(affichage2);
				int choix2 = Console.lireInt();
				Action actionSupprime = null;
				try {
					actionSupprime = this.portefeuilleActuel.getActions().get(choix2);
					this.portefeuilleActuel.supprimerTotalementAction(actionSupprime);
					System.out.println("L'action '" + actionSupprime.getNom() + "' a été supprimée du portefeuille.");
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'm':
				this.portefeuilleActuel.majToutesLesActions();
				System.out.println("Mise à jour des actions effectuée.");
				System.out.println(this.portefeuilleActuel.portefeuilleEtActionsToString());
				break;
				
			case 'r':
				System.out.println("Retour au menu supérieur.");
				this.menuProjetConsole();
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}
	
	public void menuActionConsole() {
		while (true) {
			System.out.println("");
			System.out.println("____________________________________________________________________");
			System.out.println(">>>>>>>>>> " + this.actionActuelle.toString());
			System.out.println("Que voulez-vous faire à propos de cette action ?");
			System.out.println("\t'v' pour vendre une partie de cette action.");
			System.out.println("\t'r' pour retourner au menu supérieur.");
			System.out.println("");
			
			String commandeString = Console.lireLigne();
			if (commandeString.equals("")) {
				//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
				commandeString = Console.lireLigne();
			}
			char commande = commandeString.charAt(0);
		
			switch (commande) {
			
			case 'v':
				System.out.println("Entrez la quantité à vendre :");
				int quantite = Console.lireInt();
				if (quantite <= 0 || quantite >= this.actionActuelle.getQuantite()) {
					System.out.println("Quantité négative ou supérieure à la quantité achetée impossible.");
					break;
				}
				this.actionActuelle.vendreEnPartieAction(quantite);
				System.out.println(quantite + " unités de cette action ont été vendues.");
				break;
			
			case 'r':
				System.out.println("Retour au menu supérieur.");
				menuPortefeuilleConsole();
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}

}
