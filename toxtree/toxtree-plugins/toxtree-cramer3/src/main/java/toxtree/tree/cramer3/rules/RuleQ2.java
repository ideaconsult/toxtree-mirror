package toxtree.tree.cramer3.rules;

import java.util.Set;
import java.util.TreeSet;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import ambit2.core.processors.structure.InchiProcessor;
import net.idea.modbcum.i.exceptions.AmbitException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.MolAnalyser;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

public class RuleQ2 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5440009983989446835L;
	protected Set<String> lookupSMILES = new TreeSet<String>();
	protected transient InchiProcessor processor;

	public RuleQ2() throws CDKException, AmbitException, MolAnalyseException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(true);		
		processor = new InchiProcessor();
		SmilesParser parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		for (String[] smiles : aminoacids) {
			IAtomContainer mol = parser.parseSmiles(smiles[1]);
			MolAnalyser.analyse(mol);
			InChIGenerator inchigen = processor.process(mol);
			lookupSMILES.add(inchigen.getInchiKey());
		}

	}

	private final String[][] aminoacids = { { "Glycine", "C(C(=O)O)N" }, { "Alanine", "CC(C(=O)O)N" },
			{ "Valine", "CC(C)C(C(=O)O)N" }, { "Leucine", "CC(C)CC(C(=O)O)N" }, { "Isoleucine", "CCC(C)C(C(=O)O)N" },
			{ "Proline", "C1CC([N]C1)C(=O)[O]" }, { "Phenylalanine", "c1ccc(cc1)CC(C(=O)O)N" },
			{ "Tyrosine", "c1cc(ccc1CC(C(=O)O)N)O" }, { "Tryptophan", "c1ccc2c(c1)c(c[nH]2)CC(C(=O)O)N" },
			{ "Serine", "C(C(C(=O)O)N)O" }, { "Threonine", "C[C@@H](C(C(=O)O)N)O" }, { "Cysteine", "C(C(C(=O)O)N)S" },
			{ "Methionine", "CSCCC(C(=O)O)N" }, { "Asparagine", "C(C(C(=O)O)N)C(=O)N" },
			{ "Glutamine", "C(CC(=O)N)C(C(=O)O)N" }, { "Lysine", "C(CCN)CC(C(=O)O)N" },
			{ "Arginine", "C(CC(C(=O)O)N)CNC(=N)N" }, { "Histidine", "c1c(nc[nH]1)CC(C(=O)O)N" },
			{ "Aspartate", "C(C(C(=O)[O-])N)C(=O)[O-]" }, { "Glutamate", "C(CC(=O)[O-])C(C(=O)[O-])N" },
			{ "4-hydroxyproline", "C1C(CNC1C(=O)O)O" }, { "5-hydroxylysine", "C(CC(C(=O)O)N)C(CN)O" },
			{ "N-Methyllysine", "CNCCCCC(C(=O)O)N" }, { "gamma-carboxyglutamate", "C(C(C(=O)O)C(=O)O)C(C(=O)O)N" },
			{ "Ornithine", "C(CC(C(=O)O)N)CN" }, { "Citrulline", "C(CC(C(=O)O)N)CNC(=O)N" } };

	@Override
	public boolean isImplemented() {
		return true;
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] { { "linear, unsubstituted", "[C;R0]!:[C;!$([C;R0](!@[A])!@[C])]" },
				{ "aliphatic alcohol", "[C]!@[OX2H]" }, { "aldehyde", "[CX3H1](=O)[#6]" },
				{ "carboxylic acid", "[CX3H1](=O)[#6]" }, { "derivative (ester, acetal or orthoesters)",
						"[C;$(C([#6,H]))&!$([CH2])&!$(C([#6])([#6]))&!$([CH]([#6]))](=[OX1])" } };
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		try {
			InChIGenerator inchigen = processor.process(mol);
			if (lookupSMILES.contains(inchigen.getInchiKey())) return true;
			else return super.verifyRule(mol);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
}