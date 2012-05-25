package profitbourse.vue.barredemenu;

import javax.swing.JMenu;

import profitbourse.vue.Controleur;

public class MenuAction extends JMenu {

	private static final long serialVersionUID = 4507048536261894949L;
	
	private Controleur controleur;
	
	private MenuItemIntelligent itemVendreEnPartieAction;
	
	public MenuAction(Controleur controleur) {
		super("Action");
		this.controleur = controleur;
		
		this.itemVendreEnPartieAction = new MenuItemIntelligent(this.controleur.demandeVendreEnPartieAction);
		this.add(this.itemVendreEnPartieAction);
	}

}
