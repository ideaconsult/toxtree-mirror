package toxTree.ui;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionCategoryEditor;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IToxTreeEditor;
import toxTree.qsar.AbstractQSARModel;

public class EditorFactory implements IEditorFactory {
	
	protected static IEditorFactory factory;
	
	public static IEditorFactory getInstance() {
		if (factory==null) factory = new EditorFactory();
		return factory;
	}
	
	public static void setInstance(IEditorFactory newfactory) {
		factory = newfactory;
	}	
	public IDecisionRuleEditor createEditor(IDecisionRule rule) {
		return null;
	}
	@Override
	public JComponent optionsPanel(IDecisionMethod method,
			IAtomContainer atomContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IDecisionMethodEditor createTreeEditor(IDecisionMethod method) {
	    // return new TreeEditorPanel(method);
			//return new EditTreeFrame(this);
		return null;
	}
	@Override
	public IToxTreeEditor createModelEditor(AbstractQSARModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IDecisionCategoryEditor createCategoryEditor(
			IDecisionCategory category) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PropertyChangeListener createPropertyInput() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PropertyChangeListener createApplyRuleOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
