package profitbourse.vue.tree;

import javax.swing.tree.TreeModel;

//This class takes care of the event listener lists required by TreeModel.
//It also adds "fire" methods that call the methods in TreeModelListener.
//Look in TreeModelSupport for all of the pertinent code.
abstract class AbstractTreeModel extends TreeModelSupport implements TreeModel {
	
}