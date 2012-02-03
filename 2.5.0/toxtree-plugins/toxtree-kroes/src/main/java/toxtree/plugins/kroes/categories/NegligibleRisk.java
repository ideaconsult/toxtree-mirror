package toxtree.plugins.kroes.categories;

import toxTree.tree.DefaultCategory;

public class NegligibleRisk extends DefaultCategory {

    public NegligibleRisk()
    {
        this("Negligible risk (low probability of a life-time cancer risk greater than 1 in 10^6", 2);
    }

    public NegligibleRisk(String name, int id)
    {
        super(name, id);
    }

    private static final long serialVersionUID = 0xddb992b08012516fL;
}
