package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

/**
 * This rule considers ALL possible functional groups, not only the ones listed!
 * based on toxTree.tree.cramer.Rule3FuncGroups
 * @author nina
 *
 */
public class RuleQ17 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1100391840872017540L;

	public RuleQ17() throws SMARTSException {
		super();
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		BundleRuleResource.retrieveStrings(this, examples);
		super.setContainsAllSubstructures(false);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		// throw new
		// DecisionMethodException("Not implemented (SMARTS component level grouping)");
		int functionalGroups = 0;
		ISmartsPattern<IAtomContainer> p1;
		ISmartsPattern<IAtomContainer> p2;
		try {
			// should be counted as one: ester and acids,
			p1 = smartsPatterns.get(_ester);
			p2 = smartsPatterns.get(_carboxylic_acid);
			if (p1.hasSMARTSPattern(mol) > 0 || p2.hasSMARTSPattern(mol) > 0) {
				logger.fine(_ester + " or " + _carboxylic_acid);
				functionalGroups++;
			}
			// ketals and ketones, 
			p1 = smartsPatterns.get(_ketone);
			p2 = smartsPatterns.get(_ketal);
			if (p1.hasSMARTSPattern(mol) > 0 || p2.hasSMARTSPattern(mol) > 0) {
				logger.fine(_ketone + " or " + _ketal);
				functionalGroups++;
			}
			
			//acetals,
			p1 = smartsPatterns.get(_acetal);
			if (p1.hasSMARTSPattern(mol) > 0) {
				logger.fine(_acetal);
				functionalGroups++;	
			}
			
			// and aldehydes
			p1 = smartsPatterns.get(_aldehyde);
			if (p1.hasSMARTSPattern(mol) > 0) {
				logger.fine(_aldehyde);
				functionalGroups++;		
			}
			
			//methoxy and ethoxy,
			p1 = smartsPatterns.get(_methoxy_or_ethoxy);
			if (p1.hasSMARTSPattern(mol) > 0) {
				logger.fine(_methoxy_or_ethoxy);
				functionalGroups++;	
			}
			
			// secondary and tertiary alcohol)?
			p1 = smartsPatterns.get(_secondary_alcohol);
			p2 = smartsPatterns.get(_tertiary_alcohol);
			if (p1.hasSMARTSPattern(mol) > 0 || p2.hasSMARTSPattern(mol) > 0) {
				logger.fine(_secondary_alcohol + " " + _tertiary_alcohol);
				functionalGroups++;		
			}
			logger.finest("Functional groups found:\t" + functionalGroups);
			String[][] smarts = getSMARTS();
			for (int i=9; i < smarts.length; i++) {
				p1 = smartsPatterns.get(smarts[i][0]);
				if (p1.hasSMARTSPattern(mol) > 0) {
					logger.fine(smarts[i][0]);
					functionalGroups++;	
				}
			}
			logger.fine(String.format("Functional groups \t%s", functionalGroups));
			return functionalGroups>=4;
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}
	}

	private final static String _ester = "ester";
	private final static String _carboxylic_acid = "carboxylic acid";
	private final static String _ketone = "ketone";
	private final static String _ketal = "ketal";
	private final static String _aldehyde = "aldehyde";
	private final static String _acetal = "acetal";
	private final static String _methoxy_or_ethoxy = "methoxy or ethoxy";
	private final static String _secondary_alcohol = "secondary alcohol";
	private final static String _tertiary_alcohol = "tertiary alcohol";
	
	private final static String _amines = "amines"; 
	private final static String _cyano  = "cyano";
	private final static String _nitro  = "nitro";
	private final static String _nitroso  = "nitroso";
	private final static String _diazo1   = "diazo1";
	private final static String _diazo2  = "diazo2";
	private final static String _triazeno     = "triazeno";
	private final static String _saltOfCarboxylicAcid  = "_saltOfCarboxylicAcid";
	private final static String _ether   = "ether";
	private final static String _primary_alcohol  = "primary alcohol";
	private final static String _sulphide        = "sulphide";
	private final static String _mercaptan      = "mercaptan";
	private final static String _thioester       = "thioester";
	private final static String _sulphate        = "sulphate";
	private final static String _sulphamate     = "sulphamate";
	private final static String _sulphonate     = "sulphonate";
	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				{ _ester, "[CX3](=[OX1])O" },
				{ _carboxylic_acid, "[CX3](=O)[OX2H1]" },
				{ _ketone, "[#6][CX3](=O)[#6]" },
				{ _ketal, "[$([CX4]([#6])[#6])]([O][#6])([O][#6])" },
				{ _aldehyde, "[CX3H1](=O)[#6]" },
				{ _acetal, "[$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])" },
				{ _methoxy_or_ethoxy,
						"[#6][OX2H0][C;!R;$([CH3]),$([CX4H2][CH3])]" },
				{ _secondary_alcohol, "[#6][C;$([CX4H][#6])][OX2H]" },
				{ _tertiary_alcohol, "[#6][C;$([CX4H0]([#6])[#6])][OX2H]" },
		
		
				{_amines,"[#6][$([NX4;!$(N~N)&!$(N~O)]),$([NX3;!$(N~N)&!$(N~O)])]"},
				{_cyano   ,"[NX1]#[CX2]"},
				{_nitro   ,"[#6][$([NX3](=O)=O),$([N+X3](=O)[O-])]"},
				{_nitroso ,"[#6][NX2]=O"},
				{_diazo1   ,"[#6X3]=[NX2+]=[NX1-]"},
				{_diazo2 ,"[#6X3-][NX2+]#[NX1]"},
				{_triazeno        ,"[#6][NX2]=[NX2][NH2]"},
				{_saltOfCarboxylicAcid    ,"[C;H1,$(C[#6])](=[OX1])[O-]"},
				{_ether   ,"[#6][OX2;!$([O][CX3]=[OX1])][#6]"},
				{_primary_alcohol  ,"[#6][C;H2][OX2H]"},
				{_sulphide        ,"[#16X2H0;!$(S([#6])[CX3](=[OX1])[#6])]"},
				{_mercaptan       ,"[SX2H1][#6]"},
				{_thioester       ,"[C;$(C[#6])](=[OX1])[S][#6,H]"},
				{_sulphate        ,"[O-][SX4](=[OX1])(=[OX1])[O-]"},
				{_sulphamate      ,"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1]"},
				{_sulphonate      ,"[SX4](=[OX1])(=[OX1])([#6])[O-1]"}
						
						
				};
						
	}
	@Override
	public String getImplementationDetails() {
		return "Custom implementation using SMARTS\n" + super.getImplementationDetails();
	}
}
