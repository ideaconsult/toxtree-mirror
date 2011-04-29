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

public interface IEditorFactory {
	public JComponent optionsPanel(IDecisionMethod method, IAtomContainer atomContainer);
	public IDecisionMethodEditor createTreeEditor(IDecisionMethod method);
	public IDecisionRuleEditor createEditor(IDecisionRule rule);
	public IToxTreeEditor createModelEditor(AbstractQSARModel model) ;
	public IDecisionCategoryEditor createCategoryEditor(IDecisionCategory category);
	public PropertyChangeListener createPropertyInput() ;
	public PropertyChangeListener createApplyRuleOptions() ;
}
