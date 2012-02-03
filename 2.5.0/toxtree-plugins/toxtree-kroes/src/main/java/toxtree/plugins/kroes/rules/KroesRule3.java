package toxtree.plugins.kroes.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;


/**
 * Is the chemical an aflatoxin-like, azoxy-, or N-nitroso compound
 * @author nina
 *
 */
public class KroesRule3 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6122710066223986724L;
	public KroesRule3() {
		setID("Q3");
		setTitle("Is the chemical an aflatoxin-like, azoxy-, or N-nitroso compound");
		setExplanation(getTitle());
		try {
			addSubstructure("aflatoxin","COC1=C2C3=C(C(=O)OCC3)C(=O)OC2=C2C3C=COC3OC2=C1");
			addSubstructure("azoxy","N=[N+][O-]");
			addSubstructure("N-nitroso","NN=O");
		} catch (SMARTSException x) {
			smartsPatterns.clear();
		}
		examples[0] = "c1ccccc1N=Nc2ccccc2";
		examples[1] = "COC1=C2C3=C(C(=O)OCC3)C(=O)OC2=C2C3C=COC3OC2=C1";
	}

}
