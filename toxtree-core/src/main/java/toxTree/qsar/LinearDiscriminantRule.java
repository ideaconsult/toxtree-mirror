/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.qsar;

import java.util.Hashtable;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.model.QSARModelException;

import ambit2.base.interfaces.IProcessor;

import toxTree.core.IDescriptorBased;
import toxTree.core.IImplementationDetails;
import toxTree.exceptions.DRuleException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Uses {@link LinearQSARModel} and returns true if {@link LinearQSARModel#predict(IAtomContainer)} is >= {@link #getThreshold()} and false otherwise.
 * @author nina
 *
 */
public abstract class LinearDiscriminantRule extends AbstractRule implements IImplementationDetails,IDescriptorBased {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4543098733633840805L;
	protected static Hashtable<String,String> dictionary = new Hashtable<String, String>();	
	protected LinearQSARModel model;
	protected double threshold = 0;
	
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public LinearDiscriminantRule(LinearQSARModel model, double threshold) {
		super();
		setModel(model);
		setThreshold(threshold);
	}	
	public LinearDiscriminantRule() {
		this(null,0);
	}
	public LinearQSARModel getModel() {
		return model;
	}
	public void setModel(LinearQSARModel model) {
		this.model = model;
	}
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (model == null) throw new DecisionMethodException("Linear discriminant equation not assigned!");
		try {
			model.verify();
			double value = model.predict(mol);
            mol.setProperty(getModel().getPredictedproperty(), new Double(value));
			logger.debug("Compare ",value," with threshold=",threshold);
			return compare(value, threshold);
		} catch (QSARModelException x) {
			throw createException(x);
		}
	}
    protected abstract DRuleException createException(QSARModelException x);

	@Override
	public boolean isImplemented() {
		return model != null;
	}

	protected boolean compare(double value,double threshold) {
		return value >= threshold; 
	}
	protected String hint() {
		StringBuffer b = new StringBuffer();
		b.append("<br>Please note the values are expected as property with names ");
		for (int i=0; i < model.getDescriptorNames().size();i++) {
			if (i>0) b.append(" , ");
			b.append("\"<b>");
			b.append(model.getDescriptorNames().get(i));
			b.append("</b>\"");
		}
		b.append("<br>");
		return b.toString();
		
			
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("The answer is YES if the value calculated by the linear discriminant model is W >= ");
		b.append(threshold);
		b.append("\n");
		b.append("W = ");
		b.append(model.toString());
		return b.toString();
	}
    public boolean isCalculated(String name) {
        if (model != null) return model.isCalculated(name);
        else return false;
    }
    public void setCalculated(IMolecularDescriptor descriptor,String name, boolean calculated) {
        if (model != null)
            model.setCalculated(descriptor,name, calculated);
    }
    public IProcessor<IAtomContainer, IAtomContainer> getSelector() {
    	return null;
    }
}


