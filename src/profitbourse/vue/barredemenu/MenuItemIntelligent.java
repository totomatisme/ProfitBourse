package profitbourse.vue.barredemenu;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

public class MenuItemIntelligent extends JMenuItem {

	private static final long serialVersionUID = 2971052108371463477L;
	
	public MenuItemIntelligent(Action action) {
		super(action);
		this.setText((String)action.getValue(AbstractAction.SHORT_DESCRIPTION));
	}

}
