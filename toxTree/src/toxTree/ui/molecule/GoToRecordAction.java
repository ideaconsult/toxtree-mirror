package toxTree.ui.molecule;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.data.DataModule;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.ui.actions.DataModuleAction;
import toxTree.ui.tree.images.ImageTools;

public class GoToRecordAction  extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4508406135625635445L;

	public GoToRecordAction(DataModule module) {
		this(module,"Go to record");
	}

	public GoToRecordAction(DataModule module, String name) {
		this(module, name,ImageTools.getImage("page_go.png"));

	}

	public GoToRecordAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Go to record compound No.");
	}

	@Override
	public void run() {
    	module.getActions().allActionsEnable(false);

    	
    	String value = JOptionPane.showInputDialog(frame,"Enter No.","Go to record number",JOptionPane.PLAIN_MESSAGE);
    	try {
    		if (value != null)
    			((DecisionMethodsDataModule)module).gotoRecord(value);
    	} catch (Exception x) {
    		JOptionPane.showMessageDialog(frame, x.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
    	}
    	module.getActions().allActionsEnable(true);

	}

}
