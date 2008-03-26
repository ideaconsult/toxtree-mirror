/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.qsar;

import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.model.QSARModelException;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IntegerResult;

public class LinearQSARModel extends AbstractQSARModel  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2519667129342556469L;
	protected IDescriptorPreprocessor preprocessor = null;
	protected double[] weights;
	
	public LinearQSARModel() {
		this(null,null,"result",null);
	}

	public LinearQSARModel(List<String> descriptorNames,List<IMolecularDescriptor> descriptors, String predictedProperty, double[] weights) {
		super(descriptorNames,descriptors,predictedProperty);
		setWeights(weights);
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void verify() throws QSARModelException {
		super.verify();
		if (weights == null) throw new QSARModelException("Weights not defined!");
		if ((descriptorNames.size()+1) != (weights.length)) throw new QSARModelException("Number of descriptors and number of coefficient don't match!");
	}
	public void predict() throws QSARModelException {
		verify();
		if (object == null) throw new QSARModelException("N/A");
		if (object instanceof IAtomContainer) 
			predict ((IAtomContainer)object);
		else throw new QSARModelException("Unsupported format"); 
			
	}
	public void build() throws QSARModelException {
		throw new QSARModelException("Not implemented!");
		
	}
	/*
	public double predict(IAtomContainer ac) throws QSARModelException {
		double prediction = 0; 
		try {
            flag_calculated.clear();
			for (int i=0; i < descriptorNames.size();i++) {
                
				Object descriptor = ac.getProperty(descriptorNames.get(i));
				String[] names = null;
				if (descriptor == null)  {
					//try to calculate value
					logger.info("Trying to calculate " + descriptorNames.get(i));
					if (getDescriptor(i) == null)
						throw new QSARModelException("Can't calculate descriptor "+ descriptorNames.get(i));
					
					DescriptorValue dvalue = getDescriptor(i).calculate(ac);
                    String [] dnames = dvalue.getNames();
                    for (int n=0; n < dnames.length;n++)
                        setCalculated(getDescriptor(i),dnames[n], true);
					names = dvalue.getNames();
					if ((names != null) && logger.isDebugEnabled()) 
						for (int j=0; j < names.length;j++)
							logger.debug("Estimated ",names[j]);
					logger.debug(dvalue.getValue()," by ",getDescriptor(i));
					descriptor = dvalue.getValue();
					
				} 
				double value=Double.NaN;
				ac.setProperty(descriptorNames.get(i), descriptor);
				setCalculated(getDescriptor(i),descriptorNames.get(i), true);
				
				if (descriptor instanceof Number) 
					value = ((Number)descriptor).doubleValue();
				else if (descriptor instanceof DoubleResult) 
					value = ((DoubleResult) descriptor).doubleValue();
				else if (descriptor instanceof IntegerResult) 
					value = ((IntegerResult) descriptor).intValue();
				else if (descriptor instanceof BooleanResult) 
					if (((BooleanResult) descriptor).booleanValue()) 
						value = 1; 
					else 
						value = 0;
				else if (descriptor instanceof DoubleArrayResult) { 
					if ((names != null) && (names.length == ((DoubleArrayResult)descriptor).length())) {
						boolean calculated = false;
						for (int j=0; j<names.length;j++) {
							ac.setProperty(names[j], ((DoubleArrayResult)descriptor).get(j));
							if (names[j].equals(descriptorNames.get(i))) { 
								value = ((DoubleArrayResult)descriptor).get(j);
								calculated = true;
							}	
						}
						if (!calculated)
							throw new QSARModelException(descriptorNames.get(i) + " not available");
					}
				} else	
					value = Double.parseDouble(descriptor.toString());
				
				logger.debug("Using "+descriptorNames.get(i) + "=" + value);
				if (Double.isNaN(value))
					throw new QSARModelException(descriptorNames.get(i)+" is NaN");
				prediction += weights[i]*value;
			}
		} catch (CDKException x) {
			throw new QSARModelException(x.getMessage()); 
		} catch (NumberFormatException x) {
			throw new QSARModelException(x.getMessage()); 
		}		
		prediction += weights[descriptorNames.size()];
		ac.setProperty(getPredictedproperty(), prediction);
        setCalculated(null,getPredictedproperty(), true);
		logger.info("Predicted value ",prediction);
		if (Double.isNaN(prediction))
			throw new QSARModelException("Predicted value is NaN");
		return prediction;
	}
	*/
	public double[] getDescriptors(IAtomContainer ac) throws QSARModelException {
		double[] values = new double[descriptorNames.size()];

		try {
            flag_calculated.clear();
			for (int i=0; i < descriptorNames.size();i++) {
                
				Object descriptor = ac.getProperty(descriptorNames.get(i));
				String[] names = null;
				if (descriptor == null)  {
					//try to calculate value
					logger.info("Trying to calculate " + descriptorNames.get(i));
					if (getDescriptor(i) == null)
						throw new QSARModelException("Can't calculate descriptor "+ descriptorNames.get(i));
					
					DescriptorValue dvalue = getDescriptor(i).calculate(ac);
                    String [] dnames = dvalue.getNames();
                    for (int n=0; n < dnames.length;n++)
                        setCalculated(getDescriptor(i),dnames[n], true);
					names = dvalue.getNames();
					if ((names != null) && logger.isDebugEnabled()) 
						for (int j=0; j < names.length;j++)
							logger.debug("Estimated ",names[j]);
					logger.debug(dvalue.getValue()," by ",getDescriptor(i));
					descriptor = dvalue.getValue();
					
				} 
				double value=Double.NaN;
				ac.setProperty(descriptorNames.get(i), descriptor);
				setCalculated(getDescriptor(i),descriptorNames.get(i), true);
				
				if (descriptor instanceof Number) 
					value = ((Number)descriptor).doubleValue();
				else if (descriptor instanceof DoubleResult) 
					value = ((DoubleResult) descriptor).doubleValue();
				else if (descriptor instanceof IntegerResult) 
					value = ((IntegerResult) descriptor).intValue();
				else if (descriptor instanceof BooleanResult) 
					if (((BooleanResult) descriptor).booleanValue()) 
						value = 1; 
					else 
						value = 0;
				else if (descriptor instanceof DoubleArrayResult) { 
					if ((names != null) && (names.length == ((DoubleArrayResult)descriptor).length())) {
						boolean calculated = false;
						for (int j=0; j<names.length;j++) {
							ac.setProperty(names[j], ((DoubleArrayResult)descriptor).get(j));
							if (names[j].equals(descriptorNames.get(i))) { 
								value = ((DoubleArrayResult)descriptor).get(j);
								calculated = true;
							}	
						}
						if (!calculated)
							throw new QSARModelException(descriptorNames.get(i) + " not available");
					}
				} else	
					value = Double.parseDouble(descriptor.toString());
				
				logger.debug("Using "+descriptorNames.get(i) + "=" + value);
				if (Double.isNaN(value))
					throw new QSARModelException(descriptorNames.get(i)+" is NaN");
				
				values[i] = value;
			}
		} catch (CDKException x) {
			throw new QSARModelException(x.getMessage()); 
		} catch (NumberFormatException x) {
			throw new QSARModelException(x.getMessage()); 
		}		
		return values;

	}	
	@Override
	public double predict(IAtomContainer ac) throws QSARModelException {
		// TODO Auto-generated method stub
		double[] values = getDescriptors(ac);
		try {
		if (getPreprocessor() != null)
			values = getPreprocessor().process(values);
		} catch (Exception x) {
			x.printStackTrace();
		}
		double prediction = 0;
		for (int i=0; i < descriptorNames.size();i++)
			prediction += weights[i]*values[i];
		
		prediction += weights[descriptorNames.size()];
		ac.setProperty(getPredictedproperty(), prediction);
        setCalculated(null,getPredictedproperty(), true);
		logger.info("Predicted value ",prediction);
		if (Double.isNaN(prediction))
			throw new QSARModelException("Predicted value is NaN");
		return prediction;		
	}
	@Override
	public String toString() {
		if ((weights != null) && (descriptorNames != null) && ((descriptorNames.size()+1) ==weights.length)) {
			StringBuffer b = new StringBuffer();
			for (int i=0; i < descriptorNames.size();i++) {
				b.append(" ");
				if ((i>0) && (weights[i]>=0)) b.append("+ ");
				b.append(weights[i]);
				b.append(" ");
				b.append(descriptorNames.get(i));
				
			}
			b.append(" ");
			if (weights[weights.length-1]!=0) {
				if (weights[weights.length-1]>=0) b.append("+");
				b.append(weights[weights.length-1]);
			}
			b.append("<br>");
			b.append("<br>");
			for (int i=0; i < descriptorNames.size();i++) {
				b.append("The descriptor ");
				b.append(descriptorNames.get(i));
				b.append(" is expected to be available as a property of the molecule (e.g. read from a file).");
				if (getDescriptor(i)!=null){
					b.append("<br>");
					b.append("If not available, it is calculated by ");
					b.append(getDescriptor(i).getClass().getName());
					DescriptorSpecification ds= getDescriptor(i).getSpecification();
					b.append("<br>Reference: ");
					b.append(ds.getSpecificationReference());
						
				}
				b.append("<br>");	
				b.append("\n");	
			}
			if (preprocessor != null) {
				b.append("Preprocessor\n");
				b.append(preprocessor.toString());
			}	
			return b.toString();
		} else return super.toString();
	}
	/*
	@Override
	public IDecisionRuleEditor getEditor() {
		return new RuleQSARModelEditor(this);
	}
	*/

	public IDescriptorPreprocessor getPreprocessor() {
		return preprocessor;
	}

	public void setPreprocessor(IDescriptorPreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}

}


