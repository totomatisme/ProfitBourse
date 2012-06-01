package profitbourse.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import profitbourse.Console;
import profitbourse.modele.*;
import profitbourse.modele.Portefeuille.ActionDejaPresenteDansLePortefeuille;
import profitbourse.modele.Portefeuille.ActionNonPresenteDansLePortefeuille;
import profitbourse.modele.Projet.IndiceNonPresentDansLeProjet;
import profitbourse.modele.Projet.PortefeuilleNonPresentDansLeProjet;
import profitbourse.modele.preferences.GestionnairePreferences;
import profitbourse.modele.sauvegarde.GestionnaireSauvegarde;
import profitbourse.vue.barredemenu.BarreDeMenu;
import profitbourse.vue.dialog.DialogNouveauPortefeuille;
import profitbourse.vue.dialog.DialogNouveauProjet;
import profitbourse.vue.dialog.DialogNouvelIndice;
import profitbourse.vue.dialog.DialogNouvelleAction;
import profitbourse.vue.dialog.DialogVendreEnPartieAction;

public class Controleur {
	
	private Projet projetActuel;
	private Portefeuille portefeuilleActuel;
	private Action actionActuelle;
	private Indice indiceActuel;
	
	private FenetrePrincipale fenetrePrincipale;
	private BarreDeMenu barreDeMenu;
	
	private NotificationChangementDePortefeuilleCourant notificationChangementDePortefeuilleCourant;
	private NotificationChangementDeProjetCourant notificationChangementDeProjetCourant;
	private NotificationChangementIndiceCourant notificationChangementIndiceCourant;
	private NotificationChangementActionCourante notificationChangementActionCourante;
	
	public DemandeAjoutPortefeuille demandeAjoutPortefeuille;
	public DemandeSuppressionPortefeuille demandeSuppressionPortefeuille;
	public DemandeMajIndices demandeMajIndices;
	public DemandeAjoutIndice demandeAjoutIndice;
	public DemandeSuppressionIndice demandeSuppressionIndice;
	public DemandeAjoutAction demandeAjoutAction;
	public DemandeSuppressionAction demandeSuppressionAction;
	public DemandeVendreEnPartieAction demandeVendreEnPartieAction;
	public DemandeMajPortefeuille demandeMajPortefeuille;
	public DemandeNouveauProjet demandeNouveauProjet;
	public DemandeChargerProjet demandeChargerProjet;
	public DemandeChargerDernierProjet demandeChargerDernierProjet;
	public DemandeEnregistrerProjet demandeEnregistrerProjet;
	public DemandeEnregistrerProjetSous demandeEnregistrerProjetSous;
	public DemandeFermerProjet demandeFermerProjet;
	public DemandeQuitterApplication demandeQuitterApplication;
	
	public Controleur() {
		this.projetActuel = null;
		this.portefeuilleActuel = null;
		this.actionActuelle = null;
		this.notificationChangementDePortefeuilleCourant = new NotificationChangementDePortefeuilleCourant();
		this.notificationChangementDeProjetCourant = new NotificationChangementDeProjetCourant();
		this.notificationChangementIndiceCourant = new NotificationChangementIndiceCourant();
		this.notificationChangementActionCourante = new NotificationChangementActionCourante();
		
		// Il faut créer les objets demandes (AbstractAction) avant l'interface.
		this.creerDemandes();
		// On crée l'interface.
		this.construireInterface();
		// On lance le mode console.
		this.menuPrincipalConsole();
	}
	
	public void creerDemandes() {
		this.demandeAjoutPortefeuille = new DemandeAjoutPortefeuille();
		this.demandeSuppressionPortefeuille = new DemandeSuppressionPortefeuille();
		this.demandeSuppressionIndice = new DemandeSuppressionIndice();
		this.demandeMajIndices = new DemandeMajIndices();
		this.demandeAjoutIndice = new DemandeAjoutIndice();
		this.demandeAjoutAction = new DemandeAjoutAction();
		this.demandeSuppressionAction = new DemandeSuppressionAction();
		this.demandeVendreEnPartieAction = new DemandeVendreEnPartieAction();
		this.demandeMajPortefeuille = new DemandeMajPortefeuille();
		this.demandeNouveauProjet = new DemandeNouveauProjet();
		this.demandeChargerProjet = new DemandeChargerProjet();
		this.demandeChargerDernierProjet = new DemandeChargerDernierProjet();
		this.demandeEnregistrerProjet = new DemandeEnregistrerProjet();
		this.demandeEnregistrerProjetSous = new DemandeEnregistrerProjetSous();
		this.demandeFermerProjet = new DemandeFermerProjet();
		this.demandeQuitterApplication = new DemandeQuitterApplication();
	}
	
	private void construireInterface() {
		Locale.setDefault(Locale.FRANCE);
		
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		
		this.fenetrePrincipale = new FenetrePrincipale(this);
		
		this.barreDeMenu = new BarreDeMenu(this);
		this.fenetrePrincipale.setJMenuBar(this.barreDeMenu);
		
		this.fenetrePrincipale.setVisible(true);
	}
	
	public void changerDePortefeuilleActuel(Portefeuille nouveauPortefeuilleActuel) {
		if (nouveauPortefeuilleActuel != this.portefeuilleActuel) {
			this.portefeuilleActuel = nouveauPortefeuilleActuel;
			this.changerActionActuelle(null);
			this.notificationChangementDePortefeuilleCourant.notifierChangementDePortefeuilleCourant(nouveauPortefeuilleActuel);
		}
	}
	
	public void changerDeProjetActuel(Projet nouveauProjetActuel) {
		if (nouveauProjetActuel != this.projetActuel) {
			this.projetActuel = nouveauProjetActuel;
			this.changerDePortefeuilleActuel(null);
			this.changerIndiceActuel(null);
			this.notificationChangementDeProjetCourant.notifierChangementDeProjetCourant(nouveauProjetActuel);
		}
	}
	
	public void changerActionActuelle(Action nouvelleAction) {
		if (nouvelleAction != this.actionActuelle) {
			this.actionActuelle = nouvelleAction;
			this.notificationChangementActionCourante.notifierChangementActionCourante(nouvelleAction);
		}
	}
	
	public void changerIndiceActuel(Indice nouveauIndice) {
		if (nouveauIndice != this.indiceActuel) {
			this.indiceActuel = nouveauIndice;
			this.notificationChangementIndiceCourant.notifierChangementIndiceCourant(nouveauIndice);
		}
	}
	
	public boolean enregistrerProjetActuel() {
		try {
			GestionnaireSauvegarde.enregistrerProjet(this.projetActuel, this.projetActuel.getFichierSauvegarde());
			GestionnairePreferences.setFichierSauvegarde(this.projetActuel.getFichierSauvegarde());
			GestionnairePreferences.setDernierProjetExiste(true);
			JOptionPane.showMessageDialog(
					fenetrePrincipale, 
					"Le projet a bien été enregistré à l'adresse : '" + this.projetActuel.getFichierSauvegarde() + "'.",
					"Projet enregistré",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.afficherUneErreur("Erreur lors de l'enregistrement du projet à l'adresse : '" + this.projetActuel.getFichierSauvegarde() + "'.");
		return false;
	}
	
	public boolean chargerUnAutreProjet(File fichierProjet) {
		try {
			Projet nouveauProjet = GestionnaireSauvegarde.chargerProjetDepuisFichier(fichierProjet);
			this.changerDeProjetActuel(nouveauProjet);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.afficherUneErreur("Erreur lors du chargement du projet à l'adresse : '" + fichierProjet + "'.");
		return false;
	}
	
	public void afficherUneErreur(String texteErreur) {
		JOptionPane.showMessageDialog(
				this.fenetrePrincipale, 
				texteErreur,
				"Erreur !",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public void afficherUneErreur(Exception e) {
		String message = e.getMessage();
		if (message == null) {
			this.afficherUneErreur("Une erreur s'est produite !");
		} else {
			this.afficherUneErreur(message);
		}
	}
	
	// DEMANDES
	
	public class DemandeAjoutPortefeuille extends AbstractAction {
		private static final long serialVersionUID = 5344664511671140567L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeAjoutPortefeuille() {
			super("Nouveau");
			this.putValue(SHORT_DESCRIPTION, "Ajouter un portfeuille");
			this.setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			DialogNouveauPortefeuille dialogNouveauPortefeuille = new DialogNouveauPortefeuille(Controleur.this);
			dialogNouveauPortefeuille.setVisible(true);
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeSuppressionPortefeuille extends AbstractAction {
		private static final long serialVersionUID = 8277988356034862586L;
		private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
		public DemandeSuppressionPortefeuille() {
			super("Supprimer");
			this.putValue(SHORT_DESCRIPTION, "Supprimer le portfeuille");
			this.setEnabled(false);
			this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
			getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			int reponse = JOptionPane.showConfirmDialog(
					fenetrePrincipale,
					"Voulez-vous vraiment supprimer le portefeuille '" + portefeuilleActuel + "' ?",
					"Suppression du portefeuille",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (reponse == JOptionPane.OK_OPTION) {
				try {
					projetActuel.supprimerPortefeuille(portefeuilleActuel);
					changerDePortefeuilleActuel(null);
				} catch (PortefeuilleNonPresentDansLeProjet e) {
					e.printStackTrace();
				}
			}
		}
		private class ObservateurChangementDePortefeuilleCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
				if (nouveauPortefeuille == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeMajIndices extends AbstractAction {
		private static final long serialVersionUID = 3051413672367470666L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeMajIndices() {
			super("⟲");
			this.putValue(SHORT_DESCRIPTION, "Mettre à jour les indices");
			this.setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			try {
				projetActuel.majTousLesIndices();
			} catch (Exception e) {
				e.printStackTrace();
				afficherUneErreur(e);
			}
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeAjoutIndice extends AbstractAction {
		private static final long serialVersionUID = 5797334510689354761L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeAjoutIndice() {
			super("Ajouter");
			this.putValue(SHORT_DESCRIPTION, "Ajouter un indice");
			this.setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			DialogNouvelIndice dialogNouvelIndice = new DialogNouvelIndice(Controleur.this);
			dialogNouvelIndice.setVisible(true);
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeSuppressionIndice extends AbstractAction {
		private static final long serialVersionUID = 3819324356745444614L;
		private ObservateurChangementIndiceCourant observateurChangementIndiceCourant;
		public DemandeSuppressionIndice() {
			super("Supprimer");
			this.putValue(SHORT_DESCRIPTION, "Supprimer l'indice");
			this.setEnabled(false);
			this.observateurChangementIndiceCourant = new ObservateurChangementIndiceCourant();
			getNotificationChangementIndiceCourant().addObserver(this.observateurChangementIndiceCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			int reponse = JOptionPane.showConfirmDialog(
					fenetrePrincipale,
					"Voulez-vous vraiment supprimer l'indice '" + indiceActuel + "' ?",
					"Suppression de l'indice",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (reponse == JOptionPane.OK_OPTION) {
				try {
					projetActuel.supprimerIndice(indiceActuel);
					changerIndiceActuel(null);
				} catch (IndiceNonPresentDansLeProjet e) {
					e.printStackTrace();
				}
			}
		}
		private class ObservateurChangementIndiceCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Indice nouvelIndice = (Indice)arg1;
				if (nouvelIndice == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeAjoutAction extends AbstractAction {
		private static final long serialVersionUID = -3297108071447422004L;
		private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
		public DemandeAjoutAction() {
			super("Ajouter une action");
			this.putValue(SHORT_DESCRIPTION, "Ajouter une action");
			this.setEnabled(false);
			this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
			getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			DialogNouvelleAction dialogNouvelleAction = new DialogNouvelleAction(Controleur.this);
			dialogNouvelleAction.setVisible(true);
		}
		private class ObservateurChangementDePortefeuilleCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
				if (nouveauPortefeuille == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeSuppressionAction extends AbstractAction {
		private static final long serialVersionUID = 5101785260195517184L;
		private ObservateurChangementActionCourante observateurChangementActionCourante;
		public DemandeSuppressionAction() {
			super("Supprimer l'action");
			this.putValue(SHORT_DESCRIPTION, "Supprimer l'action");
			this.setEnabled(false);
			this.observateurChangementActionCourante = new ObservateurChangementActionCourante();
			getNotificationChangementActionCourante().addObserver(this.observateurChangementActionCourante);
		}
		public void actionPerformed(ActionEvent arg0) {
			int reponse = JOptionPane.showConfirmDialog(
					fenetrePrincipale,
					"Voulez-vous vraiment supprimer l'action '" + actionActuelle + "' ?",
					"Suppression de l'action",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (reponse == JOptionPane.OK_OPTION) {
				try {
					portefeuilleActuel.supprimerTotalementAction(actionActuelle);
					changerActionActuelle(null);
				} catch (ActionNonPresenteDansLePortefeuille e) {
					e.printStackTrace();
				}
			}
		}
		private class ObservateurChangementActionCourante implements Observer {
			public void update(Observable arg0, Object arg1) {
				Action nouvelleAction = (Action)arg1;
				if (nouvelleAction == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeVendreEnPartieAction extends AbstractAction {
		private static final long serialVersionUID = 5101785260195517184L;
		private ObservateurChangementActionCourante observateurChangementActionCourante;
		public DemandeVendreEnPartieAction() {
			super("Vendre");
			this.putValue(SHORT_DESCRIPTION, "Vendre en partie l'action");
			this.setEnabled(false);
			this.observateurChangementActionCourante = new ObservateurChangementActionCourante();
			getNotificationChangementActionCourante().addObserver(this.observateurChangementActionCourante);
		}
		public void actionPerformed(ActionEvent arg0) {
			DialogVendreEnPartieAction dialogVendreEnPartieAction = new DialogVendreEnPartieAction(Controleur.this);
			dialogVendreEnPartieAction.setVisible(true);
		}
		private class ObservateurChangementActionCourante implements Observer {
			public void update(Observable arg0, Object arg1) {
				Action nouvelleAction = (Action)arg1;
				if (nouvelleAction == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeMajPortefeuille extends AbstractAction {
		private static final long serialVersionUID = 8981374659617983314L;
		private ObservateurChangementDePortefeuilleCourant observateurChangementDePortefeuilleCourant;
		public DemandeMajPortefeuille() {
			super("Mise à jour Web");
			this.putValue(SHORT_DESCRIPTION, "Mettre à jour le portefeuille");
			this.setEnabled(false);
			this.observateurChangementDePortefeuilleCourant = new ObservateurChangementDePortefeuilleCourant();
			getNotificationChangementDePortefeuilleCourant().addObserver(this.observateurChangementDePortefeuilleCourant);
		}
		public void actionPerformed(ActionEvent arg0) {
			try {
				portefeuilleActuel.majToutesLesActions();
			} catch (Exception e) {
				e.printStackTrace();
				afficherUneErreur(e);
			}
		}
		private class ObservateurChangementDePortefeuilleCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Portefeuille nouveauPortefeuille = (Portefeuille)arg1;
				if (nouveauPortefeuille == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeNouveauProjet extends AbstractAction {
		private static final long serialVersionUID = 4003769082949761808L;
		public DemandeNouveauProjet() {
			super("Nouveau projet");
			this.putValue(SHORT_DESCRIPTION, "Nouveau projet");
		}
		public void actionPerformed(ActionEvent e) {
			DialogNouveauProjet dialogNouveauProjet = new DialogNouveauProjet(Controleur.this);
			dialogNouveauProjet.setVisible(true);
		}
	}
	
	public class DemandeChargerProjet extends AbstractAction {
		private static final long serialVersionUID = -6466999554938192061L;
		public DemandeChargerProjet() {
			super("Ouvrir...");
			this.putValue(SHORT_DESCRIPTION, "Ouvrir...");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser(GestionnairePreferences.getDossierSauvegarde());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fileChooser.showOpenDialog(fenetrePrincipale);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            chargerUnAutreProjet(file);
	        }
		}
	}
	
	public class DemandeChargerDernierProjet extends AbstractAction {
		private static final long serialVersionUID = -4360693064103143497L;
		private ObservateurChangementDeFichierSauvegarde observateurChangementDeFichierSauvegarde;
		public DemandeChargerDernierProjet() {
			super();
			this.majDemande();
			this.observateurChangementDeFichierSauvegarde = new ObservateurChangementDeFichierSauvegarde();
			GestionnairePreferences.getNotificationChangementDeFichierSauvegarde().addObserver(this.observateurChangementDeFichierSauvegarde);
		}
		public void actionPerformed(ActionEvent e) {
			chargerUnAutreProjet(GestionnairePreferences.getFichierSauvegarde());
		}
		private void majDemande() {
			if (GestionnairePreferences.getDernierProjetExiste()) {
				putValue(NAME, GestionnairePreferences.getFichierSauvegarde().toString());
				setEnabled(true);
			} else {
				putValue(NAME, "Aucun projet");
				setEnabled(false);
			}
		}
		private class ObservateurChangementDeFichierSauvegarde implements Observer {
			public void update(Observable arg0, Object arg1) {
				majDemande();
			}
		}
	}
	
	public class DemandeEnregistrerProjet extends AbstractAction {
		private static final long serialVersionUID = 2589316086835317136L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeEnregistrerProjet() {
			super("Enregistrer");
			this.putValue(SHORT_DESCRIPTION, "Enregistrer");
			setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent e) {
			enregistrerProjetActuel();
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeEnregistrerProjetSous extends AbstractAction {
		private static final long serialVersionUID = 4210244625944799282L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeEnregistrerProjetSous() {
			super("Enregistrer sous...");
			this.putValue(SHORT_DESCRIPTION, "Enregistrer sous...");
			setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(projetActuel.getFichierSauvegarde());
			int returnVal = fileChooser.showSaveDialog(getFenetrePrincipale());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            projetActuel.setFichierSauvegarde(file);
	            enregistrerProjetActuel();
	        }
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeFermerProjet extends AbstractAction {
		private static final long serialVersionUID = -2412248913315236718L;
		private ObservateurChangementDeProjetCourant observateurChangementDeProjetCourant;
		public DemandeFermerProjet() {
			super("Fermer le projet");
			this.putValue(SHORT_DESCRIPTION, "Fermer le projet");
			setEnabled(false);
			this.observateurChangementDeProjetCourant = new ObservateurChangementDeProjetCourant();
			getNotificationChangementDeProjetCourant().addObserver(this.observateurChangementDeProjetCourant);
		}
		public void actionPerformed(ActionEvent e) {
			changerDeProjetActuel(null);
		}
		private class ObservateurChangementDeProjetCourant implements Observer {
			public void update(Observable arg0, Object arg1) {
				Projet projet = (Projet)arg1;
				if (projet == null) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
	}
	
	public class DemandeQuitterApplication extends AbstractAction {
		private static final long serialVersionUID = -2412248913315236718L;
		public DemandeQuitterApplication() {
			super("Quitter ProfitBourse");
			this.putValue(SHORT_DESCRIPTION, "Quitter ProfitBourse");
		}
		public void actionPerformed(ActionEvent e) {
			Dimension tailleFenetre = fenetrePrincipale.getSize();
			GestionnairePreferences.setHauteurFenetre(tailleFenetre.height);
			GestionnairePreferences.setLargeurFenetre(tailleFenetre.width);
			//System.out.println(tailleFenetre.toString());
			System.exit(0);
		}
	}
	
	// OBSERVEURS ET NOTIFIEURS
	
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
	
	public class NotificationChangementIndiceCourant extends Observable {
		public void notifierChangementIndiceCourant(Indice indice) {
			this.setChanged();
			this.notifyObservers(indice);
		}
	}
	
	public class NotificationChangementActionCourante extends Observable {
		public void notifierChangementActionCourante(Action action) {
			this.setChanged();
			this.notifyObservers(action);
		}
	}
	
	// GETTERS et SETTERS

	public NotificationChangementDePortefeuilleCourant getNotificationChangementDePortefeuilleCourant() {
		return notificationChangementDePortefeuilleCourant;
	}
	
	public NotificationChangementDeProjetCourant getNotificationChangementDeProjetCourant() {
		return notificationChangementDeProjetCourant;
	}

	public NotificationChangementIndiceCourant getNotificationChangementIndiceCourant() {
		return notificationChangementIndiceCourant;
	}

	public NotificationChangementActionCourante getNotificationChangementActionCourante() {
		return notificationChangementActionCourante;
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
	
	public FenetrePrincipale getFenetrePrincipale() {
		return fenetrePrincipale;
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
						+ GestionnairePreferences.getFichierSauvegarde().toString() + "').");
				String chemin = Console.lireLigne();
				if (chemin.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					chemin = Console.lireLigne();
				}
				if (chemin.equals("")) {
					chemin = GestionnairePreferences.getFichierSauvegarde().toString();
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
				try {
					nouvelIndice.majWeb();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					this.projetActuel.majTousLesPortefeuillesEtIndices();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Mise à jour des portefeuilles et des indices du projet effectuée.");
				System.out.println(this.projetActuel.projetEtPortefeuillesEtIndicesToString());
				break;
				
			case 'e':
				System.out.println("Entrez le chemin de sauvegarde désiré (par défaut : '" + this.projetActuel.getFichierSauvegarde() + "'.");
				String cheminString = Console.lireLigne();
				if (cheminString.equals("")) {
					//System.out.println("BUG DE LA CLASSE Console.java FOURNIE PAR SUPELEC !");
					cheminString = Console.lireLigne();
				}
				File chemin = new File(cheminString);
				this.projetActuel.setFichierSauvegarde(chemin);
				try {
					this.enregistrerProjetActuel();
					System.out.println("Projet enregistré à l'adresse '" + chemin + "' avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur lors de l'enregistrement. (Attention il faut que les dossiers existent déjà !).");
					e.printStackTrace();
				}
				break;
				
			case 'E':
				try {
					this.enregistrerProjetActuel();
					System.out.println("Projet enregistré à l'adresse '" + this.projetActuel.getFichierSauvegarde() + "' avec succès.");
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
				try {
					nouvelleAction.premiereMajWeb();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					this.portefeuilleActuel.majToutesLesActions();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
