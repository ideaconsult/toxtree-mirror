package structurefeatures.rules;


import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
public class RuleAlkylPhosphonate extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAlkylPhosphonate() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$(P(=[OX1])([OX2][#6])([$([OX2H]),$([OX1-]),$([OX2][#6])])[$([OX2H]),$([OX1-]),$([OX2][#6]),$([OX2]P)]),$([P+]([OX1-])([OX2][#6])([$([OX2H]),$([OX1-]),$([OX2][#6])])[$([OX2H]),$([OX1-]),$([OX2][#6]),$([OX2]P)])]");			
			super.initSingleSMARTS(super.smartsPatterns,"2", "[$(P(=[OX1])([$([OX2H]),$([OX1-]),$([OX2][#6])])([$([OX2H]),$([OX1-]),$([OX2][#6])]))]");
			id = "27";
			title = "Alkyl Phosphonate";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}

