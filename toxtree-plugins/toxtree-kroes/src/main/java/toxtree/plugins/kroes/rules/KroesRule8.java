package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.rendering.IAtomContainerHighlights;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.AbstractRule;
import toxTree.tree.DefaultCategory;
import toxTree.tree.cramer.CramerClass3;
import toxTree.tree.cramer.CramerRules;
import toxTree.tree.cramer.CramerTreeResult;

/**
 * Is the compound in Cramer structural class III?
 * @author nina
 *
 */
public class KroesRule8 extends AbstractRule {
	protected CramerRules cramerRules = null;
	protected CramerTreeResult cramerResults = null;
	protected DefaultCategory category;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9074211771162398484L;
	public KroesRule8() {
		setID("Q8");
		setTitle("Is the compound in Cramer structural class III?");
		setExplanation(getTitle());
		category = new CramerClass3();
		examples[1]="O=C2OC(COc1ccccc1(OC))CN2";
		examples[0]="O=C1C=COC(=C1(O))CC";
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		if (cramerResults == null) cramerResults = new toxTree.tree.cramer.CramerTreeResult();
		Object result = mol.getProperty(cramerResults.getResultPropertyNames()[0]);
		if (result == null) { // estimate
			if (cramerRules==null) cramerRules = new CramerRules();
			cramerResults.setDecisionMethod(cramerRules);
			cramerRules.classify(mol,cramerResults);
			try {
				cramerResults.assignResult(mol);
				result = mol.getProperty(cramerResults.getResultPropertyNames()[0]);
				return result.equals(category.toString());
			} catch (DecisionResultException x) {
				throw new DecisionMethodException(x);
			} catch (Exception x) {
				throw new DecisionMethodException(x);
			}
		} else {
			return result.equals(category.toString());
		}
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	public IAtomContainerHighlights getSelector() {
		return null;
	}
}
