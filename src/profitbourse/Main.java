package profitbourse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import profitbourse.modele.*;
import profitbourse.modele.Portefeuille.ActionDejaPresenteDansLePortefeuille;
import profitbourse.modele.preferences.GestionnairePreferences;
import profitbourse.modele.sauvegarde.GestionnaireSauvegarde;
import profitbourse.vue.*;
import profitbourse.vue.table.DateCellRenderer;
import profitbourse.vue.table.ModeleTablePortefeuille;

public class Main {
	
	private static Controleur controleur;
	private static Projet projet;
	private static Portefeuille portefeuille;
	private static Action action;
	private static FenetrePrincipale fenetrePrincipale;
	private static ModeleTablePortefeuille modeleTablePortefeuille;
	private static JTable tablePortefeuille;
	private static JScrollPane tableScrollPane;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		Locale.setDefault(Locale.FRANCE);
		
		projet = new Projet("Projet1");
		
		portefeuille = new Portefeuille("Valeures sûres", Currency.getInstance("EUR"), projet);
		
		Action action1 = new Action("Total SA", "FP.PA", 23, portefeuille);
		action = action1;
		action1.premiereMajWeb();
		action1.majWeb();
		portefeuille.ajouterNouvelleAction(action1);
		
		Action action2 = new Action("GDF-Suez", "GSZ.PA", 9, portefeuille);
		action2.premiereMajWeb();
		action2.majWeb();
		portefeuille.ajouterNouvelleAction(action2);
		
		Action action3 = new Action("ST-Microelectronics", "STM.PA", 45, portefeuille);
		action3.premiereMajWeb();
		action3.majWeb();
		portefeuille.ajouterNouvelleAction(action3);
		
		Action action4 = new Action("Technip", "TEC.PA", 2, portefeuille);
		action4.premiereMajWeb();
		action4.majWeb();
		portefeuille.ajouterNouvelleAction(action4);
		
		Action action5 = new Action("EDF", "EDF.PA", 34, portefeuille);
		action5.premiereMajWeb();
		action5.majWeb();
		portefeuille.ajouterNouvelleAction(action5);
		
		projet.ajouterNouveauPortefeuille(portefeuille);
		
		Indice indice1 = new Indice("CAC 40", "^CAC", projet);
		indice1.majWeb();
		projet.ajouterNouvelIndice(indice1);
		
		Indice indice2 = new Indice("Dow Jones", "^DJI", projet);
		indice2.majWeb();
		projet.ajouterNouvelIndice(indice2);
		
		System.out.println("Initialisation terminée.");
		
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		fenetrePrincipale = new FenetrePrincipale();
		//modeleTablePortefeuille = new ModeleTablePortefeuille();
		tablePortefeuille = new JTable(modeleTablePortefeuille);
		tablePortefeuille.setDefaultRenderer(Date.class, new DateCellRenderer());
		tableScrollPane = new JScrollPane(tablePortefeuille);
		
		Container contentPane = fenetrePrincipale.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tableScrollPane, BorderLayout.CENTER);
		
		fenetrePrincipale.setVisible(true);
		menuPrincipalConsole();
		*/
		controleur = new Controleur();
	}
	
	public static void menuPrincipalConsole() {
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
				projet = new Projet(nom);
				System.out.println("Le nouveau projet a été créé avec succès.");
				menuProjetConsole();
				break;
				
			case 'c':
				System.out.println("Entrez l'adresse du projet (par défaut à l'adresse '"
						+ GestionnairePreferences.getCheminSauvegarde().toString() + "').");
				String chemin = Console.lireLigne();
				if (chemin.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					chemin = Console.lireLigne();
				}
				try {
					projet = GestionnaireSauvegarde.chargerProjetDepuisFichier(new File(chemin));
					System.out.println("Le nouveau projet a été chargé avec succès.");
					menuProjetConsole();
				} catch (Exception e) {
					System.out.println("Erreur lors du chargement du projet à l'adresse : '" + chemin + "'.");
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
	
	public static void menuProjetConsole() {
		while (true) {
			System.out.println("");
			System.out.println("_____________________________________________________________________________________");
			System.out.println(">>>>>>>>>> " + projet.toString());
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
				System.out.println(projet.projetEtPortefeuillesEtIndicesToString());
				break;
				
			case 'c':
				String affichage = "Entrez le numero du portefeuille à afficher :";
				int index = 0;
				Iterator<Portefeuille> it = projet.getPortefeuilles().iterator();
				while (it.hasNext()) {
					affichage = affichage + "\n\t'" + index + "' - " + it.next().toString();
					index++;
				}
				System.out.println(affichage);
				int choix = Console.lireInt();
				try {
					portefeuille = projet.getPortefeuilles().get(choix);
					//modeleTablePortefeuille.setPortefeuille(portefeuille);
					menuPortefeuilleConsole();
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
				Portefeuille nouveauPortefeuille = new Portefeuille(nom, devise, projet);
				projet.ajouterNouveauPortefeuille(nouveauPortefeuille);
				System.out.println("Le portefeuille '" + nouveauPortefeuille.getNom() + "' a été ajouté.");
				break;
				
			case 's':
				String affichage2 = "Entrez le numero du portefeuille à supprimer :";
				int index2 = 0;
				Iterator<Portefeuille> it2 = projet.getPortefeuilles().iterator();
				while (it2.hasNext()) {
					affichage2 = affichage2 + "\n\t'" + index2 + "' - " + it2.next().toString();
					index2++;
				}
				System.out.println(affichage2);
				int choix2 = Console.lireInt();
				Portefeuille portefeuilleSupprime = null;
				try {
					portefeuilleSupprime = projet.getPortefeuilles().get(choix2);
					projet.supprimerPortefeuille(portefeuilleSupprime);
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
				Indice nouvelIndice = new Indice(nomIndice, codeIndice, projet);
				projet.ajouterNouvelIndice(nouvelIndice);
				nouvelIndice.majWeb();
				System.out.println("L'indice '" + nouvelIndice.getNom() + "' a été ajouté.");
				break;
				
			case 'S':
				String affichage3 = "Entrez le numero de l'indice à supprimer :";
				int index3 = 0;
				Iterator<Indice> it3 = projet.getIndices().iterator();
				while (it3.hasNext()) {
					affichage3 = affichage3 + "\n\t'" + index3 + "' - " + it3.next().toString();
					index3++;
				}
				System.out.println(affichage3);
				int choix3 = Console.lireInt();
				Indice indiceSupprime = null;
				try {
					indiceSupprime = projet.getIndices().get(choix3);
					projet.supprimerIndice(indiceSupprime);
					System.out.println("L'indice '" + indiceSupprime.getNom() + "' a été supprimé du projet.");
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'm':
				projet.majTousLesPortefeuillesEtIndices();
				System.out.println("Mise à jour des portefeuilles et des indices du projet effectuée.");
				System.out.println(projet.projetEtPortefeuillesEtIndicesToString());
				break;
				
			case 'e':
				System.out.println("Entrez le chemin de sauvegarde désiré (par défaut : '" + projet.getCheminSauvegarde() + "'.");
				String cheminString = Console.lireLigne();
				if (cheminString.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					cheminString = Console.lireLigne();
				}
				File chemin = new File(cheminString);
				projet.setCheminSauvegarde(chemin);
				try {
					projet.enregistrerProjet();
					System.out.println("Projet enregistré à l'adresse '" + chemin + "' avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur lors de l'enregistrement. (Attention il faut que les dossiers existent déjà !).");
					e.printStackTrace();
				}
				break;
				
			case 'E':
				try {
					projet.enregistrerProjet();
					System.out.println("Projet enregistré à l'adresse '" + projet.getCheminSauvegarde() + "' avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur lors de l'enregistrement. (Attention il faut que les dossiers existent déjà !).");
					e.printStackTrace();
				}
				break;
				
			case 'f':
				System.out.println("Projet fermé.");
				menuPrincipalConsole();
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}
	
	public static void menuPortefeuilleConsole() {
		while (true) {
			System.out.println("");
			System.out.println("____________________________________________________________________");
			System.out.println(">>>>>>>>>> " + portefeuille.toString());
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
				System.out.println(portefeuille.portefeuilleEtActionsToString());
				break;
				
			case 'c':
				String affichage = "Entrez le numero de l'action à afficher :";
				int index = 0;
				Iterator<Action> it = portefeuille.getActions().iterator();
				while (it.hasNext()) {
					affichage = affichage + "\n\t'" + index + "' - " + it.next().toString();
					index++;
				}
				System.out.println(affichage);
				int choix = Console.lireInt();
				try {
					action = portefeuille.getActions().get(choix);
					menuActionConsole();
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
				Action nouvelleAction = new Action(nom, code, quantite, portefeuille);
				try {
					portefeuille.ajouterNouvelleAction(nouvelleAction);
				} catch (ActionDejaPresenteDansLePortefeuille e1) {
					e1.printStackTrace();
				}
				nouvelleAction.premiereMajWeb();
				System.out.println("L'action '" + nouvelleAction.getNom() + "' a été ajoutée.");
				break;
				
			case 's':
				String affichage2 = "Entrez le numero de l'action à supprimer :";
				int index2 = 0;
				Iterator<Action> it2 = portefeuille.getActions().iterator();
				while (it2.hasNext()) {
					affichage2 = affichage2 + "\n\t'" + index2 + "' - " + it2.next().toString();
					index2++;
				}
				System.out.println(affichage2);
				int choix2 = Console.lireInt();
				Action actionSupprime = null;
				try {
					actionSupprime = portefeuille.getActions().get(choix2);
					portefeuille.supprimerTotalementAction(actionSupprime);
					System.out.println("L'action '" + actionSupprime.getNom() + "' a été supprimée du portefeuille.");
				} catch (Exception e) {
					System.out.println("Le numero entré ne convient pas.");
				}
				break;
				
			case 'm':
				portefeuille.majToutesLesActions();
				System.out.println("Mise à jour des actions effectuée.");
				System.out.println(portefeuille.portefeuilleEtActionsToString());
				break;
				
			case 'r':
				System.out.println("Retour au menu supérieur.");
				menuProjetConsole();
				break;
				
			default:
				System.out.println("Commande non reconnue, veuillez réessayer.");
				break;
			}
		}
	}
	
	public static void menuActionConsole() {
		while (true) {
			System.out.println("");
			System.out.println("____________________________________________________________________");
			System.out.println(">>>>>>>>>> " + action.toString());
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
				if (quantite <= 0 || quantite >= action.getQuantite()) {
					System.out.println("Quantité négative ou supérieure à la quantité achetée impossible.");
					break;
				}
				action.vendreEnPartieAction(quantite);
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
