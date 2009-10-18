package mutant.descriptors;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternCDK;

public class DescriptorBridgedBiphenyl extends DescriptorStructurePresence<IAtomContainer> {
	protected static String BiBr = "I(BiBr)";

	public DescriptorBridgedBiphenyl() {
		super();
		try {
		setParameters(new Object[] {
//				"c1ccccc1!@[*;!N]!@c2ccccc2",
				//"[$(c1ccccc1!@[*;!N]!@c2ccccc2),$(c1ccccc1!@[N]!@[$(ccN),$(cccN),$(ccccN)])]",
				"[$(c1ccccc1!@[*;!N]!@c2ccccc2),$(c1ccccc1!@[N]!@c2c(N)cccc2),$(c1ccccc1!@[N]!@c2cc(N)ccc2),$(c1ccccc1!@[N]!@c2ccc(N)cc2),$(c1ccccc1!@[N]!@c2cccc(N)c2),$(c1ccccc1!@[N]!@c2ccccc(N)2)]",

				//"c1ccccc1!@[N]!@c2ccccc2",
				BiBr}
				);
		} catch (CDKException x) {

			setResultName(BiBr);
			logger.error(x);
		}
	}
	
	 public DescriptorSpecification getSpecification() {
	        return new DescriptorSpecification(
	            "True if contains bridged biphenyl group, false otherwise. The bridge should not be composed by a functional amino group.",
	            this.getClass().getName(),
	            "$Id: DescriptorBridgedBiphenyl.java  2007-08-21 10:31 nina$",
	            "ToxTree Mutant plugin");
	    }

    @Override
    protected ISmartsPattern<IAtomContainer> createSmartsPattern() {
        return new SmartsPatternCDK();
    }
}
