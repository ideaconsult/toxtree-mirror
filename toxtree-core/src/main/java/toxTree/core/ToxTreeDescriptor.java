package toxTree.core;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.exceptions.DecisionResultException;

import ambit2.core.data.ArrayResult;
import ambit2.core.data.StringDescriptorResultType;

public class ToxTreeDescriptor implements IMolecularDescriptor {
	protected String[] paramNames = {"decisionTree"};
	protected Object[] parameters = new Object[1];
	protected transient IDecisionMethod decisionTree = null;
	protected String[] descriptorNames =  new String[] {"default"};
	
	public DescriptorValue calculate(IAtomContainer mol) throws CDKException {
		if (decisionTree == null) 
			try {
				decisionTree = (IDecisionMethod) Introspection.loadCreateObject(getParameters()[0].toString());
			} catch (Exception x) {
				throw new CDKException(x.getMessage());
			}
			
		if (decisionTree == null) throw new CDKException("No decision tree!");
		IDecisionResult result = decisionTree.createDecisionResult();
		try {
			result.classify(mol);
			result.assignResult(mol);
			
			
			descriptorNames = result.getResultPropertyNames();
			ArrayResult<String> value = new ArrayResult<String>(new String[descriptorNames.length]);
			for (int i=0; i <  descriptorNames.length;i++)
				try {
					value.set(i,mol.getProperty(descriptorNames[i]).toString());
				} catch (Exception x) {
					value.set(i,null);
				}

			return new DescriptorValue(
						getSpecification(),
						getParameterNames(),
						getParameters(),
						value,
						descriptorNames
						);				

		} catch (DecisionResultException x) {
			throw new CDKException(x.getMessage());
		}
	}

	public IDescriptorResult getDescriptorResultType() {
		return new StringDescriptorResultType("");
	}

	public String[] getParameterNames() {
		return paramNames;
	}

	public Object getParameterType(String arg0) {
		for (int i=0; i < paramNames.length;i++)
			if (arg0.equals(paramNames[i]))
			return parameters[i];
		return null;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxTree.sourceforge.net",
                this.getClass().getName(),
                decisionTree==null?getParameters()[0].toString():decisionTree.getTitle(),
                "Toxtree plugin");
	}

	public void setParameters(Object[] arg0) throws CDKException {
		for (int i=0; i < parameters.length;i++) {
			if (arg0[i] instanceof IDecisionMethod) 
				if ((decisionTree==null) || !getParameters()[i].equals(arg0[i].getClass().getName())) {
					parameters[i] = arg0[i].getClass().getName();
					decisionTree = (IDecisionMethod)arg0[i];					
				} else {}
			else
				parameters[i] = arg0[i];
		}
		
	}

}
