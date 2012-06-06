package profitbourse.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import profitbourse.modele.*;
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
			nouveauProjet.setFichierSauvegarde(fichierProjet);
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
		//String classeDescription = e.getClass().toString() + " :\n";
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
			int reponse = JOptionPane.showConfirmDialog(
					fenetrePrincipale,
					"Voulez-vous vraiment quitter ProfitBourse ?",
					"Quitter",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (reponse == JOptionPane.OK_OPTION) {
				Dimension tailleFenetre = fenetrePrincipale.getSize();
				GestionnairePreferences.setHauteurFenetre(tailleFenetre.height);
				GestionnairePreferences.setLargeurFenetre(tailleFenetre.width);
				System.exit(0);
			}
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

}
