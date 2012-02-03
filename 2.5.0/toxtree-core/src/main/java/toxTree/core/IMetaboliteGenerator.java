package toxTree.core;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.tree.RuleResult;

public interface IMetaboliteGenerator  {
	IAtomContainerSet getProducts(IAtomContainer reactant,RuleResult ruleResult) throws Exception;
	String getHelp(RuleResult ruleResult);
}
