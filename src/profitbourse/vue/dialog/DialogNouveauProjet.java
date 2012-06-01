package profitbourse.vue.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import profitbourse.modele.Projet;
import profitbourse.vue.Controleur;

public class DialogNouveauProjet extends JDialog {

	private static final long serialVersionUID = -8690191138872618949L;
	private Controleur controleur;
	private JPanel panelLabelEtTexte;
	private JLabel labelEntrerNom;
	private JTextField texteNom;
	private JPanel panelBoutons;
	private JButton boutonCreer;
	private JButton boutonAnnuler;
	
	private DemandeAnnuler demandeAnnuler;
	private DemandeCreation demandeCreation;
	
	public DialogNouveauProjet(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.demandeAnnuler = new DemandeAnnuler();
		this.demandeCreation = new DemandeCreation();
		
		this.construireInterface();
	}
	
	public void construireInterface() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		// Label et TextField
		this.panelLabelEtTexte = new JPanel();
		this.panelLabelEtTexte.setLayout(new BoxLayout(this.panelLabelEtTexte, BoxLayout.Y_AXIS));
		this.panelLabelEtTexte.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.labelEntrerNom = new JLabel("Entrez le nom du projet :");
		this.panelLabelEtTexte.add(this.labelEntrerNom);
		this.panelLabelEtTexte.add(Box.createRigidArea(new Dimension(0,5)));
		this.texteNom = new JTextField();
		this.panelLabelEtTexte.add(this.texteNom);
		this.panelLabelEtTexte.add(Box.createVerticalGlue());
		this.add(this.panelLabelEtTexte);
		
		// Boutons en bas à droite
		this.panelBoutons = new JPanel();
		this.panelBoutons.setLayout(new BoxLayout(this.panelBoutons, BoxLayout.LINE_AXIS));
		this.panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.panelBoutons.add(Box.createHorizontalGlue());
		this.boutonCreer = new JButton("Créer");
		this.boutonCreer.addActionListener(this.demandeCreation);
		this.panelBoutons.add(this.boutonCreer);
		this.panelBoutons.add(Box.createRigidArea(new Dimension(10, 0)));
		this.boutonAnnuler = new JButton("Annuler");
		this.boutonAnnuler.addActionListener(this.demandeAnnuler);
		this.panelBoutons.add(this.boutonAnnuler);
		this.add(this.panelBoutons);
		
		this.getRootPane().setDefaultButton(this.boutonCreer);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Nouveau projet");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(this.controleur.getFenetrePrincipale());
	}
	
	private class DemandeAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	private class DemandeCreation implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				String nom = texteNom.getText();
				if (nom.equals("")) throw new ErreurNomVide();
				
				Projet nouveauProjet = new Projet(nom);
				controleur.changerDeProjetActuel(nouveauProjet);
				dispose();
			} catch (Exception e) {
				controleur.afficherUneErreur(e);
				e.printStackTrace();
			}
		}
	}
	
}

class ErreurNomVide extends Exception {
	private static final long serialVersionUID = 8447309210674959240L;
	public ErreurNomVide() {
		super("Le nom est vide !");
	}
}
