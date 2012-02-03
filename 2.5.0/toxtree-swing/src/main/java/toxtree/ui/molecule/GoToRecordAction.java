package toxtree.ui.molecule;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.io.Tools;
import toxtree.data.DataModule;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.actions.DataModuleAction;

public class GoToRecordAction  extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4508406135625635445L;

	public GoToRecordAction(DataModule module) {
		this(module,"Go to record");
	}

	public GoToRecordAction(DataModule module, String name) {
		this(module, name,Tools.getImage("page_go.png"));

	}

	public GoToRecordAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Go to record compound No.");
	}

	@Override
	public void run() throws Exception {
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
