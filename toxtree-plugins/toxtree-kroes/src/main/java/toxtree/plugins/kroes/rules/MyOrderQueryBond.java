
package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;

public class MyOrderQueryBond extends OrderQueryBond {

    public MyOrderQueryBond(IChemObjectBuilder builder)   {
    	super(builder);
    }

    public MyOrderQueryBond(IQueryAtom atom1, IQueryAtom atom2, IBond.Order order,IChemObjectBuilder builder)  {
        super(atom1, atom2, order,builder);
    }

    @Override
    public boolean matches(IBond bond)
    {
        if(getOrder() == bond.getOrder())
            return true;
        return getFlag(4) && bond.getFlag(4);
    }

    private static final long serialVersionUID = 0x382d4471e3895945L;
}
