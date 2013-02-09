package toxtree.plugins.lewis.rules;

import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;

import toxTree.tree.rules.RuleDescriptorRange;

public class Rule_highLogP extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1943709360390046218L;

	public Rule_highLogP() {
		super();
		id = "LogP";
		XLogPDescriptor d = new XLogPDescriptor();
		setDescriptor(d);
		setMaxValue(Double.MAX_VALUE);
		setMinValue(0.9);
		/*
		try {
			getDescriptor().setParameters(params);
		} catch (CDKException x) {
			logger.error(x);
		}
		*/
		setTitle("LogP > 0.9");
		explanation = new StringBuffer();
		explanation.append("LogP > 0.9"
				);
		examples[0] = "c(c(c(c(c1)ccc2)c2)cc(c3c(c(c4)ccc5)c5)c4)(c1)c3";  //Kowwin logKow 6.67
		examples[1] = "O=C(N(N(C1(=O))c(cccc2)c2)c(cccc3)c3)C1CCCC";  //Kowwin logKow 3.523
		editable = false;
	}
	
}
