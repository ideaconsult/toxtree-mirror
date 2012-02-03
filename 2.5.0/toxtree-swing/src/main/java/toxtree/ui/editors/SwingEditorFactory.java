package toxtree.ui.editors;

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
import toxTree.tree.rules.RuleDescriptorRange;
import toxTree.tree.rules.RuleManyAromaticRings;
import toxTree.tree.rules.RuleMolecularMassRange;
import toxTree.tree.rules.RuleStructuresList;
import toxTree.tree.rules.RuleSubstructures;
import toxTree.tree.rules.RuleVerifyProperty;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;
import toxTree.ui.IEditorFactory;
import toxtree.tree.rules.ApplyRuleOptions;
import toxtree.ui.PropertyInput;
import toxtree.ui.tree.TreeEditorPanel;
import toxtree.ui.tree.TreeOptions;
import toxtree.ui.tree.categories.CategoryPanel;
import toxtree.ui.tree.qsar.QSARModelEditor;
import toxtree.ui.tree.rules.RulePanel;
import toxtree.ui.tree.rules.RulePropertyEditor;
import toxtree.ui.tree.rules.RuleRangeEditor;
import toxtree.ui.tree.rules.RuleStructuresPanel;
import toxtree.ui.tree.rules.SMARTSRuleEditor;
import toxtree.ui.tree.rules.SubstructureRulePanel;

public class SwingEditorFactory implements IEditorFactory {
	
	public static IEditorFactory getInstance() {
		return new SwingEditorFactory();
	}
	
	public PropertyChangeListener createApplyRuleOptions() {
		return new ApplyRuleOptions();
	}	
	public PropertyChangeListener createPropertyInput() {
		return new PropertyInput();
	}
	public IDecisionCategoryEditor createCategoryEditor(IDecisionCategory category) {
		return new CategoryPanel(category);
	}
	
	public IToxTreeEditor createModelEditor(AbstractQSARModel model) {
		return new QSARModelEditor(model);
	}
	
	public JComponent optionsPanel(IDecisionMethod method, IAtomContainer atomContainer) {
	    	return new TreeOptions(method,atomContainer);
	}    	
	public IDecisionMethodEditor createTreeEditor(IDecisionMethod method) {
		return new TreeEditorPanel(method);
	}
	public IDecisionRuleEditor createEditor(IDecisionRule rule) {
		if (rule instanceof RuleDescriptorRange) return createEditor((RuleDescriptorRange)rule);
		if (rule instanceof RuleMolecularMassRange) return createEditor((RuleMolecularMassRange)rule);
		if (rule instanceof RuleManyAromaticRings) return createEditor((RuleManyAromaticRings)rule);
		if (rule instanceof RuleSubstructures) return createEditor((RuleSubstructures)rule);
		if (rule instanceof RuleStructuresList) return createEditor((RuleStructuresList)rule);
		if (rule instanceof AbstractRuleSmartSubstructure) return createEditor((AbstractRuleSmartSubstructure)rule);
		else {
			RulePanel p = new RulePanel(rule);
			p.setRule(rule);
			return p;
		}
	}
	
	/**
	 * RuleDescriptorRange
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleDescriptorRange rule) {
		RuleRangeEditor p = new RuleRangeEditor(rule);
		p.setRule(rule);
		return p;
	}	
	/**
	 * RuleMolecularMassRange
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleMolecularMassRange rule) {
		RuleRangeEditor e = new RuleRangeEditor(rule);
		e.setSetPropertyEditable(false);
		return e;
	}	
	/**
	 * RuleVerifyProperty
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleVerifyProperty rule) {
		return new RulePropertyEditor(rule);
	}	
	/**
	 * RuleManyAromaticRings
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleManyAromaticRings rule) {
		RuleRangeEditor e = new RuleRangeEditor(rule);
		e.setRule(rule);
		e.setSetPropertyEditable(false);
		return e;
	}	
	/**
	 * RuleSubstructures
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleSubstructures rule) {
		return new SubstructureRulePanel(rule);
	}	
	
	/**
	 * RuleStructuresList
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(RuleStructuresList rule) {
		return new RuleStructuresPanel(rule);
	}	
	
	/**
	 * 
	 * @param rule
	 * @return
	 */
	public IDecisionRuleEditor createEditor(AbstractRuleSmartSubstructure rule) {
		return new SMARTSRuleEditor(rule);
	}	
	
	
}
