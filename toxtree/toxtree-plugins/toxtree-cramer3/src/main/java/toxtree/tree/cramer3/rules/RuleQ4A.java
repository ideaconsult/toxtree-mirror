package toxtree.tree.cramer3.rules;

import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ4A extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7747302914787228995L;

	public RuleQ4A() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], true);
		super.setContainsAllSubstructures(true);
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				/*
				 * 4A. Does the substance contain two or less of either Cl or F
				 * for every six carbons with no halogen positioned either alpha
				 * or beta with respect to an ether oxygen or oxygenated
				 * functional group (alcohol, including phenol, ketone,
				 * aldehyde, acid, ester, acetal, or ketal) with no other
				 * functional group present in the substance?
				 */
				{"ether alpha","[O;D2][#6][Cl,F,Br,I]"},
				{"ether beta","[O;D2][#6][#6][Cl,F,Br,I]"},
				{"alcohol / phenol alpha","[OH][#6][Cl,F,Br,I]"}, //TODO example
				{"alcohol /phenol beta","[OH][#6]C[Cl,F,Br,I]"},
				{"aldehydes and ketones alpha","O=CC[Cl,F,Br,I]"},
				{"aldehydes and ketones beta","O=CCC[Cl,F,Br,I]"},
				{"ester alpha","C(OC)(=O)C[Cl,F,Br,I]"},
				{"ester beta","C(OC)(=O)CC[Cl,F,Br,I]"},
				//{"acetals and ketals alpha","C(OC)(=O)C[Cl,F]"}, ??TODO verify
				//{"acetals and ketals beta","C(OC)(=O)CC[Cl,F]"},
				/*		
				{ "ether", "[OD2]([#6])[C;$([C][Cl,F])][C,H;$([C][C][Cl,F])]" },
				{ "alcohol", "[#6;!$(C=O)&!$(CN)&!$(CS)&!$(CP)&!$([C][Cl,F])&!$([C][C][Cl,F])][OX2H]" },
				{ "ether", "[OD2]([#6])[C;!$([C][Cl,F])][C,H;!$([C][C][Cl,F])]" },
				{ "phenol", "[OX2H][cX3]:[c;!$([C][Cl,F])&!$([C][C][Cl,F])]" },
				{ "ester", "[#6;!$([C][Cl,F])&!$([C][C][Cl,F])][CX3](=O)[OX2H0][#6]" },
				{ "aldehydes and ketones", "[$([CX3]([#6])),$([CX3H])](=O)[#6;!$([C][Cl,F])&!$([C][C][Cl,F])]" },
				{
						"acetals and ketals",
						"[$([CX4]([#6;!$([C][Cl,F])&!$([C][C][Cl,F])])[#6;!$([C][Cl,F])&!$([C][C][Cl,F])]),$([CX4H][#6;!$([C][Cl,F])&!$([C][C][Cl,F])]),$([CX4H2])]([O][#6])([O][#6])" } 
						*/
		};
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer arg1)
			throws DecisionMethodException {
		int count_c = 0; 
		int count_hal = 0;
		for (IAtom atom : mol.atoms()) {
			if ("C".equals(atom.getSymbol())) count_c++;
			else if ("Cl".equals(atom.getSymbol()) || "F".equals(atom.getSymbol())) count_hal++;
		}
		if ((count_hal>0) && (count_hal * 3 > count_c)) {
			logger.log(Level.FINE, "more than two of either Cl or F  for every six carbons" );
			return false;
		}
		return super.verifyRule(mol, arg1);
	}
}
