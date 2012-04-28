package profitbourse;

import java.io.File;
import java.util.Currency;
import java.util.Locale;

import profitbourse.modele.*;
import profitbourse.modele.Portefeuille.ActionNonPresenteDansLePortefeuille;


public class Main {
	
	private static Projet projet;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Locale.setDefault(Locale.FRANCE);
		
		File cheminProjet = new File(".");
		projet = new Projet("Projet1", cheminProjet);
		
		Portefeuille portefeuille = new Portefeuille("Valeures sûres", Currency.getInstance("EUR"), projet);
		
		Action action1 = new Action("Total", "FR0000234", 23, portefeuille);
		action1.premiereMajWeb();
		action1.majWeb();
		portefeuille.ajouterNouvelleAction(action1);
		
		Action action2 = new Action("GDF-Suez", "FR0000974", 9, portefeuille);
		action2.premiereMajWeb();
		action2.majWeb();
		portefeuille.ajouterNouvelleAction(action2);
		
		Action action3 = new Action("ST-Microelectronics", "FR0000874", 45, portefeuille);
		action3.premiereMajWeb();
		action3.majWeb();
		portefeuille.ajouterNouvelleAction(action3);
		
		Action action4 = new Action("Technip", "FR0000065", 2, portefeuille);
		action4.premiereMajWeb();
		action4.majWeb();
		portefeuille.ajouterNouvelleAction(action4);
		
		Action action5 = new Action("EDF", "FR0000904", 34, portefeuille);
		action5.premiereMajWeb();
		action5.majWeb();
		portefeuille.ajouterNouvelleAction(action5);
		
		projet.ajouterNouveauPortefeuille(portefeuille);
		
		System.out.println(portefeuille.portefeuilleEtActionsToString());
		
		try {
			portefeuille.supprimerTotalementAction(action1);
		} catch (ActionNonPresenteDansLePortefeuille e) {
			e.printStackTrace();
		}
		
		System.out.println(portefeuille.portefeuilleEtActionsToString());
		
		//menuPrincipalConsole();
	}
	
	public static void menuPrincipalConsole() {
		while (true) {
			System.out.println("");
			System.out.println("Que voulez-vous faire ?");
			System.out.println("\t'l' pour lister les portefeuilles présents dans le projet actuel.");
			System.out.println("\t'a' pour ajouter un nouveau portefeuille.");
			System.out.println("\t's' pour supprimer un portefeuille existant.");
			System.out.println("\t'q' pour quitter le logiciel.");
			System.out.println("");
			
			char action = Console.lireChar();
			
			switch (action) {
			case 'l':
				System.out.println(projet.projetEtPortefeuillesToString());
				break;
				
			case 'a':
				System.out.println("Entrez un nom pour le portefeuille :");
				String nom = Console.lireLigne();
				System.out.println("Entrez la monnaie (par defaut 'EUR') :");
				String codeDevise = Console.lireLigne();
				Currency devise = null;
				try {
					devise = Currency.getInstance(codeDevise);
				} catch (IllegalArgumentException e) {
					devise = Currency.getInstance("EUR");
				}
				Portefeuille nouveauPortefeuille = new Portefeuille(nom, devise, projet);
				projet.ajouterNouveauPortefeuille(nouveauPortefeuille);
				System.out.println("Le portefeuille '" + nouveauPortefeuille.getNom() + "'");
				break;
				
			case 'q':
				System.exit(0);
				break;
				
			default:
				break;
			}
		}
	}
	

}
