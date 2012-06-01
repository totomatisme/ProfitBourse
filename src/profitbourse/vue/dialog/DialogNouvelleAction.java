package profitbourse.vue.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import profitbourse.modele.Action;
import profitbourse.modele.majweb.GestionnaireMajWeb;
import profitbourse.vue.Controleur;

public class DialogNouvelleAction extends JDialog {
	
	private static final long serialVersionUID = 6587731911164687336L;
	private Controleur controleur;
	
	private JPanel panelLabelEtTexte;
	private JLabel labelEntrerCode;
	private JTextField texteCode;
	
	private JPanel panelCheckBoxEtTexteNom;
	private JCheckBox checkEntrerNomManuellement;
	private JTextField texteNom;
	
	private JPanel panelLabelEtTexteQuantite;
	private JLabel labelQuantite;
	private JTextField texteQuantite;
	
	private JPanel panelBoutons;
	private JButton boutonAjouter;
	private JButton boutonAnnuler;
	
	private DemandeAnnuler demandeAnnuler;
	private DemandeAjout demandeAjout;
	private ChangementEtatCheckBox changementEtatCheckBox;
	
	public DialogNouvelleAction(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.demandeAjout = new DemandeAjout();
		this.demandeAnnuler = new DemandeAnnuler();
		this.changementEtatCheckBox = new ChangementEtatCheckBox();
		
		this.construireInterface();
	}
	
	public void construireInterface() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		// Label et TextField
		this.panelLabelEtTexte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//this.panelLabelEtTexte.setLayout(new BoxLayout(this.panelLabelEtTexte, BoxLayout.LINE_AXIS));
		this.panelLabelEtTexte.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		this.labelEntrerCode = new JLabel("Entrez le code de l'action :  ");
		this.panelLabelEtTexte.add(this.labelEntrerCode);
		this.texteCode = new JTextField(3);
		this.panelLabelEtTexte.add(this.texteCode);
		//this.panelLabelEtTexte.add(Box.createHorizontalGlue());
		//this.panelLabelEtTexte.add(Box.createVerticalGlue());
		this.add(this.panelLabelEtTexte);
		
		// CheckBox et Label
		this.panelCheckBoxEtTexteNom = new JPanel();
		this.panelCheckBoxEtTexteNom.setLayout(new BoxLayout(this.panelCheckBoxEtTexteNom, BoxLayout.Y_AXIS));
		this.panelCheckBoxEtTexteNom.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		this.checkEntrerNomManuellement = new JCheckBox("Entrer manuellement le nom de l'action :");
		this.checkEntrerNomManuellement.setSelected(false);
		this.checkEntrerNomManuellement.addActionListener(this.changementEtatCheckBox);
		this.panelCheckBoxEtTexteNom.add(this.checkEntrerNomManuellement);
		this.texteNom = new JTextField();
		this.texteNom.setEnabled(false);
		this.panelCheckBoxEtTexteNom.add(this.texteNom);
		this.add(this.panelCheckBoxEtTexteNom);
		
		// Label et TextField de selection de la quantité.
		this.panelLabelEtTexteQuantite = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.panelLabelEtTexteQuantite.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		this.labelQuantite = new JLabel("Quantité achetée :  ");
		this.panelLabelEtTexteQuantite.add(this.labelQuantite);
		this.texteQuantite = new JTextField(4);
		this.panelLabelEtTexteQuantite.add(this.texteQuantite);
		this.add(this.panelLabelEtTexteQuantite);
		
		// Boutons en bas à droite
		this.panelBoutons = new JPanel();
		this.panelBoutons.setLayout(new BoxLayout(this.panelBoutons, BoxLayout.LINE_AXIS));
		this.panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.panelBoutons.add(Box.createHorizontalGlue());
		this.boutonAjouter = new JButton("Ajouter");
		this.boutonAjouter.addActionListener(this.demandeAjout);
		this.panelBoutons.add(this.boutonAjouter);
		this.panelBoutons.add(Box.createRigidArea(new Dimension(10, 0)));
		this.boutonAnnuler = new JButton("Annuler");
		this.boutonAnnuler.addActionListener(this.demandeAnnuler);
		this.panelBoutons.add(this.boutonAnnuler);
		this.add(this.panelBoutons);
		
		this.getRootPane().setDefaultButton(this.boutonAjouter);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Ajouter une action");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(this.controleur.getFenetrePrincipale());
	}
	
	private class ChangementEtatCheckBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			texteNom.setEnabled(checkEntrerNomManuellement.isSelected());
		}
	}
	
	private class DemandeAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	private class DemandeAjout implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				String code = texteCode.getText();
				if (code.equals("")) throw new ErreurCodeVide();
				
				String nom = "";
				if (checkEntrerNomManuellement.isSelected()) {
					nom = texteNom.getText();
					if (nom.equals("")) throw new ErreurNomVide();
				} else {
					try {
						nom = GestionnaireMajWeb.obtenirNomActionPourLeCode(code);
						if (nom == null) throw new ErreurCodeInconnu();
					} catch (Exception e) {
						e.printStackTrace();
						throw new ErreurCodeInconnu();
					}
				}
				
				String quantiteString = texteQuantite.getText();
				int quantite = 0;
				try {
					quantite = Integer.parseInt(quantiteString);
				} catch (NumberFormatException e) {
					throw new Exception("La quantité '" + quantiteString + "' ne correspond par à un entier !");
				}
				if (quantite < 0) throw new Exception("La quantité '" + quantiteString + "' est négative !");
				
				Action nouvelleAction = new Action(nom, code, quantite, controleur.getPortefeuilleActuel());
			
				controleur.getPortefeuilleActuel().ajouterNouvelleAction(nouvelleAction);
				controleur.changerActionActuelle(nouvelleAction);
				nouvelleAction.premiereMajWeb();
				
				dispose();
			} catch (Exception e) {
				controleur.afficherUneErreur(e);
				e.printStackTrace();
				return;
			}
		}
	}

}
