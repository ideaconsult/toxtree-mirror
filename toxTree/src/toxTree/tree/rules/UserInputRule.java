package toxTree.tree.rules;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionInteractive;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import toxTree.ui.PropertyEditor;

public abstract class UserInputRule extends AbstractRule implements IDecisionInteractive {
	protected String message="";
	protected ApplyRuleOptions options = new ApplyRuleOptions(true,false);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 533087115478484038L;
	public UserInputRule() {
		setID("User");
		setTitle("User input");
		setExplanation("Proceed?");
	}	
	public UserInputRule(String message) {
		this();
		setMessage(message);
	}
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (getInteractive()) {
			JPanel p = new JPanel(new BorderLayout());
			p.add(optionsPanel(mol),BorderLayout.CENTER);
			p.add(new PropertyEditor(mol,null),BorderLayout.SOUTH);
			
			JOptionPane.showMessageDialog(null, p,getTitle(),JOptionPane.PLAIN_MESSAGE,null);
		}
		return isSilentvalue();
	}
	@Override
	public boolean isImplemented() {
		return !message.equals("");
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public JComponent optionsPanel(IAtomContainer atomContainer) {
		return options.optionsPanel(message, "If <No>, the category <For a better assessment a QSAR calculation could be applied> will be assigned.", getID(), atomContainer);
	}

	public boolean getInteractive() {
		return options.getInteractive();
	}
	
	public void setInteractive(boolean value) {
		options.setInteractive(value);
		
	}
	public boolean isSilentvalue() {
		return options.isAnswer();
	}
	public void setSilentvalue(boolean silentvalue) {
		options.setAnswer(silentvalue);
	}	
}


