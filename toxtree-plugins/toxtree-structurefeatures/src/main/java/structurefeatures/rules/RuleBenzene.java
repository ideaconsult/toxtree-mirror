package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleBenzene extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleBenzene() {
		super();		
		try {
			//benzene, 1,2,3,4-fused
			super.initSingleSMARTS(super.smartsPatterns,"1", "c12ccccc1cccc2");			
			//1-alkylamino-
			super.initSingleSMARTS(super.smartsPatterns,"2", "a-[NX3;H2,H1;!$(NC=O)][CX4,CH,CH2,CH3]");			
			//1-carbonylamino
			super.initSingleSMARTS(super.smartsPatterns,"3", "[OX1]=CN");
			//1-methyl
			super.initSingleSMARTS(super.smartsPatterns,"4", "a-[cX3H]");
			//1-heteroamino
			super.initSingleSMARTS(super.smartsPatterns,"5", "[$([nX3](:*):*),$([nX2](:*):*),$([#7X2]=*),$([NX3](=*)=*),$([#7X3+](-*)=*),$([#7X3+H]=*);$(a-[NX3;H2,H1;!$(NC=O)])]");			
			
			
			id = "6";
			title = "Benzene";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}