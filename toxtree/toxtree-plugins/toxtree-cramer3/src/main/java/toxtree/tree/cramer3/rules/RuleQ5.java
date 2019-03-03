package toxtree.tree.cramer3.rules;

import java.io.InputStream;
import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import ambit2.smarts.processors.SMIRKSProcessor;
import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.cramer.RuleHasOnlySaltSulphonateSulphate;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

/**
 * Derived from Q4 {@link RuleHasOnlySaltSulphonateSulphate} with additions and
 * changes
 * 
 * @author nina
 * 
 */
public class RuleQ5 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 154980850401771648L;
	protected transient SMIRKSProcessor neutraliser;

	/*
	 * 5. Do all the elements not listed in 4 or 4A occur only as: A. sodium,
	 * potassium, calcium, magnesium, aluminum, ammonium, zinc, manganese,
	 * copper or ferric salts of a carboxylic acid, bisulfite of aldehyde or
	 * ketone, sulfonic, sulfamic, or sulfuric acid, phenol, or B. hydrochloride
	 * or sulfate salt of a primary or tertiary amine?
	 */

	public RuleQ5() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		super.setContainsAllSubstructures(false);
		try {
			InputStream resource = getClass().getClassLoader()
					.getResourceAsStream("toxtree/tree/cramer3/rules/RuleQ5_smirks.json");
			neutraliser = new SMIRKSProcessor(resource, logger);
		} catch (Exception x) {
			logger.log(Level.WARNING, x.getMessage());
			neutraliser = null;
		}
	}

	public IAtomContainer neutralise(IAtomContainer mol) throws Exception {
		return neutraliser.process(mol);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol))
			try {
				IAtomContainer result = neutralise(mol);
				IAtomContainerSet set = mol.getBuilder().newInstance(IAtomContainerSet.class);
				set.addAtomContainer(result);
				MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
				if (mf == null)
					throw new DecisionMethodException("Structure should be preprocessed!");
				mf.setResidues(set);
			} catch (Exception x) {
				logger.log(Level.WARNING, x.getMessage());
			} finally {
				return true;
			}
		else
			return false;
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				//
				// { "salt of carboxylic acid +1",
				// "[C;H1,$(C[#6])](=[OX1])[O-].[Na+,K+]" },
				/*
				 * { "salt of carboxylic acid +2",
				 * "[C;H1,$(C[#6])](=[OX1])[O-]([Ca+2,Mg+2,Zn+2,Cu+2,Mn+2])[O-][C;H1,$(C[#6])](=[OX1])"
				 * }, { "salt of carboxylic acid +3",
				 * "[C;H1,$(C[#6])](=[OX1])[O-][Al+3,Mn+3,Fe+3]([O-][C;H1,$(C[#6])](=[OX1]))[O-][C;H1,$(C[#6])](=[OX1])"
				 * }, { "bisulfite of aldehyde or ketone",
				 * "[CX4][C]([OX2H1])([SX4](=[OX1])([O-])([OH]))[C,H]" },
				 */
				{ "salt of carboxylic acid",
						"[C;H1,$(C[#6])](=[OX1])[O-].[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "bisulfite of aldehyde or ketone",
						"[CX4][C]([OX2H1])([C,H])[SX4](=[OX1])([OH])[O-].[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "salt of sulfonic acid",
						"[SX4](=[OX1])(=[OX1])([#6])([O-]).[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "sulfamic acid",
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1].[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "sulfuric acid",
						"[SX4](=[OX1])(=[OX1])([OX2;H1])[O-1].[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "phenol", "[c]:[cX3][O-1].[Na+,K+,N+,Ca+2,Mg+2,Zn+2,Cu+2,Mn+2,Al+3,Mn+3,Fe+3]" },
				{ "hcl_salt of primary amine", "[#6][$([NX4H3;+1])][Cl-]" },
				{ "so4_salt of primary amine", "[#6][$([NX4H3;+1])][O-][SX4](=[OX1])(=[OX1])[O-][$([NX4H3;+1])][#6]" },
				{ "hcl_salt of tertiary amine", "[#6][$([NX4H1;+1])][Cl-]" }, { "so4_salt of tertiary amine",
						"[#6][$([NX4H1;+1])][O-][SX4](=[OX1])(=[OX1])[O-][$([NX4H1;+1])][#6]" } };
	}

}
