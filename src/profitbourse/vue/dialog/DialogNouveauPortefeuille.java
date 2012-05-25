package profitbourse.vue.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Currency;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import profitbourse.modele.Portefeuille;
import profitbourse.vue.Controleur;

public class DialogNouveauPortefeuille extends JDialog {

	private static final long serialVersionUID = 3068324676525371724L;
	private Controleur controleur;
	
	private JPanel panelLabelEtTexte;
	private JLabel labelEntrerNom;
	private JTextField texteNom;
	private JPanel panelBoutons;
	private JButton boutonAjouter;
	private JButton boutonAnnuler;
	private JPanel panelLabelEtComboBox;
	private JLabel labelEntrerDevise;
	private JComboBox comboChoixDevise;
	
	private DemandeAnnuler demandeAnnuler;
	private DemandeAjout demandeAjout;
	
	public DialogNouveauPortefeuille(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.demandeAjout = new DemandeAjout();
		this.demandeAnnuler = new DemandeAnnuler();
		
		this.construireInterface();
	}
	
	public void construireInterface() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Label et TextField
		this.panelLabelEtTexte = new JPanel();
		this.panelLabelEtTexte.setLayout(new BoxLayout(this.panelLabelEtTexte, BoxLayout.Y_AXIS));
		this.panelLabelEtTexte.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.labelEntrerNom = new JLabel("Entrez le nom du portefeuille :");
		this.panelLabelEtTexte.add(this.labelEntrerNom);
		this.panelLabelEtTexte.add(Box.createHorizontalGlue());
		this.panelLabelEtTexte.add(Box.createRigidArea(new Dimension(0,5)));
		this.texteNom = new JTextField();
		this.panelLabelEtTexte.add(this.texteNom);
		this.panelLabelEtTexte.add(Box.createHorizontalGlue());
		this.panelLabelEtTexte.add(Box.createVerticalGlue());
		this.add(this.panelLabelEtTexte);
		
		// Label et ComboBox
		this.panelLabelEtComboBox = new JPanel();
		this.panelLabelEtComboBox.setLayout(new BoxLayout(this.panelLabelEtComboBox, BoxLayout.X_AXIS));
		this.panelLabelEtComboBox.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		this.labelEntrerDevise = new JLabel("Entrez la devise :  ");
		this.panelLabelEtComboBox.add(this.labelEntrerDevise);
		String[] choixDevises = {"EUR", "USD", "GBP", "CNY", "JPY"};
		this.comboChoixDevise = new JComboBox(choixDevises);
		this.comboChoixDevise.setEditable(true);
		this.panelLabelEtComboBox.add(this.comboChoixDevise);
		this.panelLabelEtComboBox.add(Box.createHorizontalGlue());
		this.panelLabelEtComboBox.add(Box.createVerticalGlue());
		this.add(this.panelLabelEtComboBox);
		
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
		this.setTitle("Nouveau portefeuille");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(this.controleur.getFenetrePrincipale());
	}
	
	private class DemandeAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	private class DemandeAjout implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String nom = texteNom.getText();
			if (nom.equals("")) {
				controleur.afficherUneErreur("Le nom est vide !");
				return;
			}
			
			String deviseString = (String)comboChoixDevise.getSelectedItem();
			Currency devise = null;
			try {
				devise = Currency.getInstance(deviseString);
			} catch (IllegalArgumentException e) {
				controleur.afficherUneErreur("La devise '" + deviseString + "' ne correspond pas à une devise connue !");
				return;
			}
			
			Portefeuille nouveauPortefeuille = new Portefeuille(nom, devise, controleur.getProjetActuel());
			controleur.getProjetActuel().ajouterNouveauPortefeuille(nouveauPortefeuille);
			controleur.changerDePortefeuilleActuel(nouveauPortefeuille);
			dispose();
		}
	}
	
}
