package mutant.descriptors;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.logging.TTLogger;

public abstract class SubstituentsDescriptor implements IMolecularDescriptor {
	protected static TTLogger logger = new TTLogger(SubstituentsDescriptor.class); 
	protected String[] paramNames = {"ring","descriptor_names"};
	protected String[] descriptorNames = null;
	protected SubstituentExtractor extractor;
	
	
	public SubstituentsDescriptor() {
		this(null);
	}
	public SubstituentsDescriptor(QueryAtomContainer query) {
		super();
		extractor = new SubstituentExtractor(query);
	}

	public DescriptorValue calculate(IAtomContainer arg0) throws CDKException {
        Hashtable<String,IAtomContainerSet> substituents = extractor.extractSubstituents(arg0);
        String mark = select(substituents);
        Enumeration<String> marks = substituents.keys();
        while (marks.hasMoreElements()) {
            String m = marks.nextElement();
            if (m.equals(mark)) continue;
                for (int i=0; i < arg0.getAtomCount();i++)
                    arg0.getAtom(i).removeProperty(m);
        }
		return calculate(substituents.get(mark),mark);
	}
    public abstract String select(Hashtable<String,IAtomContainerSet> substituents) throws CDKException;
	
	public abstract DescriptorValue calculate(IAtomContainerSet substituents,String mark) throws CDKException;
	
	public IDescriptorResult getDescriptorResultType() {
		return new DoubleArrayResult();
	}

	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxTree.sf.net",
                this.getClass().getName(),
                "$Id: SubstituentsDescriptor.java 6171 2007-08-06 14:09:00 +0000 (Mon, 06 August 2007) nina $",
                "Toxtree plugin");
	}
	public Object[] getParameters() {
		return new Object[] {extractor.getRingQuery(),descriptorNames};
	}

	public void setParameters(Object[] params) throws CDKException {
		if (params.length > 2) throw new CDKException(getClass().getName() + " expects 2 parameters only.");
		if (params.length >= 1)
			if (params[0] instanceof QueryAtomContainer )
				extractor.setRingQuery((QueryAtomContainer) params[0]);
			else 
				throw new CDKException(params[0] + " not an instance of IQueryAtomContainer");
		if ((params.length == 2) && (params[1] instanceof String[])) {
			setDescriptorNames((String[])params[1]);
		}	
	}
	public Object getParameterType(String arg0) {
		if (paramNames[0].equals(arg0))
			return new QueryAtomContainer();
		else if (paramNames[1].equals(arg0)) return new String[] {};
		else return null;
	}
	public String[] getParameterNames() {
		return paramNames;
	}

	public void setParameterNames(String[] paramNames) {
		this.paramNames = paramNames;
	}
	public String[] getDescriptorNames() {
		return descriptorNames;
	}
	public void setDescriptorNames(String[] descriptorNames) {
		this.descriptorNames = descriptorNames;
	}
	public QueryAtomContainer getRingQuery() {
		return extractor.getRingQuery();
	}
	public void setRingQuery(QueryAtomContainer ringQuery) {
		extractor.setRingQuery(ringQuery);
	}
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (int i=0; i < descriptorNames.length;i++) {
			if (i>0) b.append(",");
			b.append(descriptorNames[i]);
		}	
		return b.toString();
	}
 
}
