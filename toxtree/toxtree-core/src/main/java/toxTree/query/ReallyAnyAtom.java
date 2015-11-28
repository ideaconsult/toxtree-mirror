package toxTree.query;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyAtom;

/**
 * To be used instead of org.openscience.cdk.isomorphism.matchers.smarts.AnyAtom
 * until its matcher code is fixed
 * 
 * <pre>
 * public boolean matches(IAtom atom) {
 * 	if (atom.getSymbol().equals(&quot;H&quot;)) {
 * 		Integer massNumber = atom.getMassNumber();
 * 		return massNumber != null;
 * 	}
 * 	return true;
 * }
 * </pre>
 * 
 * @author nina
 * 
 */
public class ReallyAnyAtom extends AnyAtom {

	public ReallyAnyAtom(IChemObjectBuilder builder) {
		super(builder);
	}

	@Override
	public boolean matches(IAtom atom) {

		return true;
	}

	public String toString() {
		return "AnyAtom()";
	}
}
