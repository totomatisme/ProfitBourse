package profitbourse.vue.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

import profitbourse.vue.Controleur;

public class DialogVendreEnPartieAction extends JDialog {

	private static final long serialVersionUID = -684524263759296343L;
	private Controleur controleur;
	
	private JPanel panelLabelEtTexteQuantite;
	private JLabel labelQuantite;
	private JTextField texteQuantite;
	
	private JPanel panelMaximumVente;
	private JLabel labelMaximumVente;
	
	private JPanel panelBoutons;
	private JButton boutonVendre;
	private JButton boutonAnnuler;
	
	private DemandeAnnuler demandeAnnuler;
	private DemandeVendre demandeVendre;
	
	public DialogVendreEnPartieAction(Controleur controleur) {
		super();
		this.controleur = controleur;
		
		this.demandeAnnuler = new DemandeAnnuler();
		this.demandeVendre = new DemandeVendre();
		
		this.construireInterface();
	}
	
	public void construireInterface() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		// Label et TextField de selection de la quantité.
		this.panelLabelEtTexteQuantite = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.panelLabelEtTexteQuantite.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		this.labelQuantite = new JLabel("Quantité à vendre :  ");
		this.panelLabelEtTexteQuantite.add(this.labelQuantite);
		this.texteQuantite = new JTextField(4);
		this.panelLabelEtTexteQuantite.add(this.texteQuantite);
		this.add(this.panelLabelEtTexteQuantite);
		
		this.panelMaximumVente = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.panelMaximumVente.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		this.labelMaximumVente = new JLabel("(la quantité doit être de 0 à " + controleur.getActionActuelle().getQuantite() + ")");
		this.panelMaximumVente.add(this.labelMaximumVente);
		this.add(this.panelMaximumVente);
		
		// Boutons en bas à droite
		this.panelBoutons = new JPanel();
		this.panelBoutons.setLayout(new BoxLayout(this.panelBoutons, BoxLayout.LINE_AXIS));
		this.panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.panelBoutons.add(Box.createHorizontalGlue());
		this.boutonVendre = new JButton("Vendre");
		this.boutonVendre.addActionListener(this.demandeVendre);
		this.panelBoutons.add(this.boutonVendre);
		this.panelBoutons.add(Box.createRigidArea(new Dimension(10, 0)));
		this.boutonAnnuler = new JButton("Annuler");
		this.boutonAnnuler.addActionListener(this.demandeAnnuler);
		this.panelBoutons.add(this.boutonAnnuler);
		this.add(this.panelBoutons);
		
		this.getRootPane().setDefaultButton(this.boutonVendre);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Vendre en partie l'action");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(this.controleur.getFenetrePrincipale());
	}
	
	private class DemandeAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	private class DemandeVendre implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String quantiteString = texteQuantite.getText();
			int quantite = 0;
			try {
				quantite = Integer.parseInt(quantiteString);
			} catch (NumberFormatException e) {
				controleur.afficherUneErreur("La quantité '" + quantiteString + "' ne correspond par à un entier !");
				return;
			}
			if (quantite < 0) {
				controleur.afficherUneErreur("La quantité '" + quantiteString + "' est négative !");
				return;
			}
			if (quantite > controleur.getActionActuelle().getQuantite()) {
				controleur.afficherUneErreur("La quantité '" + quantiteString + "' est supérieure à " + controleur.getActionActuelle().getQuantite() + " !");
				return;
			}
			controleur.getActionActuelle().vendreEnPartieAction(quantite);
			dispose();
		}
	}
	
}
