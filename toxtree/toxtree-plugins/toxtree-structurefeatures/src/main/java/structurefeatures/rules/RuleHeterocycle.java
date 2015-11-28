package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleHeterocycle extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleHeterocycle() {
		super();		
		try {
			//quinone
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([o,n]=c1ccc(=[o,n])cc1),$([O,N]=C1C=CC(=[O,N])C=C1),$([O,N]=C1[#6]:,=[#6]C(=[O,N])[#6]:,=[#6]1)]");
			//5 member aromatic heterocycle w/ 2double bonds. contains N & another non C (N,O,S)
			super.initSingleSMARTS(super.smartsPatterns,"2", "[$([nr5]:[nr5,or5,sr5]),$([nr5]:[cr5]:[nr5,or5,sr5])]");
			//thioketones
			super.initSingleSMARTS(super.smartsPatterns,"3", "CC(=S)C");
			//heteroamino
			super.initSingleSMARTS(super.smartsPatterns,"4", "[$([nX3](:*):*),$([nX2](:*):*),$([#7X2]=*),$([NX3](=*)=*),$([#7X3+](-*)=*),$([#7X3+H]=*)]");
			//pyrazine
			//super.initSingleSMARTS(super.smartsPatterns,"5", "[$([N=C1[#6]:,=[#6]C(=[C,N])[#6]:,=[#6]1])],$(a-[N=C1[#6]:,=[#6]C(=[C,N])[#6]:,=[#6]1])]");
			//gives Incorrect bond expression: [$([N=C1[#6]:,=[#6]C(=[C,N])[#6]:,=[#6]1])],
			super.initSingleSMARTS(super.smartsPatterns,"5","c1cnccn1");
			
			id = "16";
			title = "Heterocycle";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
