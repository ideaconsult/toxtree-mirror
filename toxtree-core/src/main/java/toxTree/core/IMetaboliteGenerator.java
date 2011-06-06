package toxTree.core;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

public interface IMetaboliteGenerator  {
	IAtomContainerSet getProducts(IAtomContainer reactant) throws Exception;
}
