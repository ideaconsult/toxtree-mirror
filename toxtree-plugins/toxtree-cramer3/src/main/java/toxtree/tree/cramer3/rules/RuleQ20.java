package toxtree.tree.cramer3.rules;

import java.util.Enumeration;
import java.util.Iterator;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternAmbit;

public class RuleQ20 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -223324583799066539L;
	private transient ISmartsPattern<IAtomContainer> longAliphaticChain;

	public RuleQ20() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(false);

		longAliphaticChain = new SmartsPatternAmbit("[C;R0]!@[C;R0]!@[C;R0]!@[C;R0]!@[C;R0]!@[C;R0]!@[C;R0]");

	}

	private static final String tag = RuleQ20.class.getName();

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		IRingSet rings = null;
		try {
			rings = Cycles.essential(mol).toRingSet();
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

		if (rings == null || rings.getAtomContainerCount() == 0
				|| rings.getAtomContainerCount() > 3) {
			logger.fine("rings = 0 or rings > 3");
			return false;
		} else
			logger.fine("mono-, bi-, or tricyclic");
		// should not have aliphatic chain > 6
		try {
			if (longAliphaticChain.hasSMARTSPattern(mol) > 0) {
				logger.fine("Found aliphatic side chains of >C6");
				return false;
			}
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

		for (int r = 0; r < rings.getAtomContainerCount(); r++) {
			if (rings.getAtomContainer(r).getFlag(CDKConstants.ISAROMATIC)) {
				logger.fine("aromatic");
				return false;
			}
			for (IAtom atom : rings.getAtomContainer(r).atoms()) {
				// tag the ring atoms
				atom.setProperty(tag, "ring");
				if (!"C".equals(atom.getSymbol())) {
					logger.fine("not carbocyclic");
					return false;
				}
			}
		}

		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {
			while (keys.hasNext()) {
				String key = keys.next();
				if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
					logger.finer("Found " + key);
					// this is not very efficient, as atomcontainer copy is
					// involved
					// todo callback
					IAtomContainer match = smartsPatterns.get(key)
							.getMatchingStructure(mol);
					for (IAtom atom : match.atoms()) {
						atom.setProperty(tag, key);
					}
				}
			}
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}
		int untagged = 0;
		for (IAtom atom : mol.atoms()) {
			if ("H".equals(atom.getSymbol()))
				continue;
			if (atom.getProperty(tag) == null)
				untagged++;
			logger.fine(String.format("[untagged:%d]\t%s\t%s:%s",untagged, atom.getSymbol(), tag,
					atom.getProperty(tag)));
		}

		return untagged == 0;
	}

	public static final String _aliphatic_chain = "aliphatic chain";
	public static final String _alcohol = "alcohol";
	public static final String _aldehyde_except_dialdehydes = "aldehyde (except for vicinal dialdehydes)";
	public static final String _acetal = "acetal";
	public static final String _carboxylic_acid = "carboxylic acid";
	public static final String _ester = "ester";
	public static final String _ketone = "ketone";
	public static final String _ketal = "ketal";
	public static final String _sulfide = "sulfide";
	public static final String _amide = "amide";
	public static final String _sulfonate_salt1 = "sulfonate salt +1";
	public static final String _sulfonate_salt2 = "sulfonate salt +2";
	public static final String _sulfonate_salt3 = "sulfonate salt +3";
	public static final String _sulfamate_salt1 = "sulfamate salt +1";
	public static final String _sulfamate_salt2 = "sulfamate salt +2";
	public static final String _sulfamate_salt3 = "sulfamate salt +3";

	@Override
	public String[][] getSMARTS() {

		return new String[][] {
				// TO DO: carbocyclic mono-, bi-, or tricyclic, fused or
				// unfused, with aliphatic side chains of ≤C6
				{"cycle","[R]"},
				{ _aliphatic_chain, "C!@[C;R0]" },
				{ _alcohol, "[#6;!$(C=O)&!$(CN)&!$(CS)&!$(CP)][OX2H]" },
				{ _aldehyde_except_dialdehydes,
						"[CX3H1;!$([#6](=[OX1])[#6](=[OX1]))](=O)[#6]" },
				{ _acetal, "[$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])" },
				{ _carboxylic_acid, "[C;H1,$(C[#6])](=[OX1])[OH]" },
				{ _ester, "[#6][CX3](=O)[OX2H0][#6]" },
				{ _ketone, "[#6][CX3](=O)[#6]" },
				{ _ketal, "[$([CX4]([#6])[#6])]([O][#6])([O][#6])" },
				{ _sulfide, "[#6][SX2H0]" },
				{ _amide, "[NX3H0,NX3H1,NX3H2][CX3](=[OX1])[#6]" },
				{ _sulfonate_salt1, "[SX4](=[OX1])(=[OX1])([#6])[O-1][Na+,K+]" },
				{
						_sulfonate_salt2,
						"[SX4](=[OX1])(=[OX1])([#6])[O-1][Ca+2,Mg+2,Zn+2,Cu+2][O-1][SX4](=[OX1])(=[OX1])([#6])" },
				{
						_sulfonate_salt3,
						"[SX4](=[OX1])(=[OX1])([#6])[O-1][Al+3,Fe+3]([O-1][SX4](=[OX1])(=[OX1])([#6]))[O-1][SX4](=[OX1])(=[OX1])([#6])" },

				{ _sulfamate_salt1,
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Na+,K+]" },
				{
						_sulfamate_salt2,
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Ca+2,Mg+2,Zn+2,Cu+2][O-1][SX4](=[OX1])(=[OX1])([NX3H2])" },
				{
						_sulfamate_salt3,
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Al+3,Fe+3]([O-1][SX4](=[OX1])(=[OX1])([NX3H2]))[O-1][SX4](=[OX1])(=[OX1])([NX3H2])" }

		};
	}

	@Override
	public String getImplementationDetails() {
		return "Custom implementation using ring detection and SMARTS\n"
				+ super.getImplementationDetails();
	}
}
