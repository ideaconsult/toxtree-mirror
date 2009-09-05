package toxTree.tree.rules;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionInteractive;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

public abstract class UserInputRule extends AbstractRule implements IDecisionInteractive {

	protected String message="";
	protected UserOptions options = UserOptions.YEStoALL;
	public UserOptions getOptions() {
		return options;
	}
	public void setOptions(UserOptions options) {
		this.options = options;
	}
	protected PropertyChangeListener listener;

	/**
	 * 
	 */
	private static final long serialVersionUID = 533087115478484038L;
	public UserInputRule() {
		setID("User");
		setTitle("User input");
		setExplanation("Proceed?");
		setListener(new ApplyRuleOptions());
		setInteractive(true);
	}	
	public UserInputRule(String message) {
		this();
		setMessage(message);
	}

	public PropertyChangeListener getListener() {
		return listener;
	}
	public void setListener(PropertyChangeListener listener) {
		this.listener = listener;
	}	
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
        if (getInteractive() && (getListener() !=null)) {
        	getListener().propertyChange(new PropertyChangeEvent(
        			this,
        			message,
        			"If <No>, the category <For a better assessment a QSAR calculation could be applied> will be assigned.",
        			mol));

        } 
        return isSilentvalue();
        
		/*
		if (getInteractive()) {
			JPanel p = new JPanel(new BorderLayout());
			p.add(optionsPanel(mol),BorderLayout.CENTER);
			p.add(new PropertyEditor(mol,null),BorderLayout.SOUTH);
			
			JOptionPane.showMessageDialog(null, p,getTitle(),JOptionPane.PLAIN_MESSAGE,null);
		}
		return isSilentvalue();
		*/
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

	public boolean isSilentvalue() {
		return options.getAnswer();
	}
	public void setSilentvalue(boolean silentvalue) {
		setOptions(options.setAnswer(silentvalue));
	}	

	public boolean getInteractive() {
		return options.isInteractive();
	}
	public void setInteractive(boolean interactive) {
		setOptions(options.setInteractive(interactive));
	}	
	
}


