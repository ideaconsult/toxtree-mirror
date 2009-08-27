
package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;

public class MyOrderQueryBond extends OrderQueryBond
{

    public MyOrderQueryBond()
    {
    }

    public MyOrderQueryBond(IQueryAtom atom1, IQueryAtom atom2, Order order)
    {
        super(atom1, atom2, order);
    }

    public boolean matches(IBond bond)
    {
        if(getOrder() == bond.getOrder())
            return true;
        return getFlag(4) && bond.getFlag(4);
    }

    private static final long serialVersionUID = 0x382d4471e3895945L;
}
