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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.core.IDescriptorBased;
import toxTree.core.IToxTreeEditor;
import toxTree.core.Introspection;
import toxTree.logging.TTLogger;
import toxTree.ui.tree.qsar.QSARModelEditor;
import ambit2.base.data.IModel;
import ambit2.base.exceptions.QSARModelException;

public abstract class AbstractQSARModel implements IModel,  Serializable, IDescriptorBased {
	protected static transient TTLogger logger = new TTLogger(AbstractQSARModel.class);
	protected List<String> descriptorNames;
    protected Hashtable<String,Boolean> flag_calculated; 
	private transient List<IMolecularDescriptor> descriptors = new ArrayList<IMolecularDescriptor>();
	private List<String> descriptorsClass;
	protected String predictedproperty;
	protected Object object;
	protected String name;
	
	public AbstractQSARModel() {
		this(null,null,"Result");
	}
	public AbstractQSARModel(
			List<String> descriptorNames,
			List<IMolecularDescriptor> descriptors, 
			String predictedproperty) {
		super();
        flag_calculated = new Hashtable<String, Boolean>();
		descriptorsClass = new ArrayList<String>();
		setDescriptorNames(descriptorNames);
		setDescriptors(descriptors);
		setPredictedproperty(predictedproperty);
		setName(getClass().getName());
	}	

	public List<IMolecularDescriptor> getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(List<IMolecularDescriptor> descriptors) {
		this.descriptors = descriptors;
		descriptorsClass.clear();
		if (descriptors!=null)
		for (int i=0; i < descriptors.size();i++)
			descriptorsClass.add(descriptors.get(i).getClass().getName());
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public abstract double predict(IAtomContainer ac) throws QSARModelException;
	public List<String> getDescriptorNames() {
		return descriptorNames;
	}
	public void setDescriptorNames(List<String> descriptorNames) {
		this.descriptorNames = descriptorNames;
        flag_calculated.clear();
	}
	public String getPredictedproperty() {
		return predictedproperty;
	}
	public void setPredictedproperty(String predictedproperty) {
		this.predictedproperty = predictedproperty;
	}
	public void verify() throws QSARModelException {
		if (descriptorNames == null) throw new QSARModelException("Descriptor names not defined!");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return getName();
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		Thread.currentThread().setContextClassLoader(Introspection.getLoader());
		in.defaultReadObject();
		
		if (descriptors == null) descriptors = new ArrayList<IMolecularDescriptor>();
		List<IMolecularDescriptor> d = new ArrayList<IMolecularDescriptor>();
		for (int i=0; i < descriptorsClass.size();i++) {
			if (!descriptorsClass.get(i).equals("")) 
				try {
					IMolecularDescriptor descriptor = (IMolecularDescriptor)Introspection.loadCreateObject(descriptorsClass.get(i));
					descriptors.add(descriptor);
				} catch (Exception x) {
					x.printStackTrace();
					descriptors.add(null);
				}
		}	
	}
	public int getNumberofDescriptors() {
		if (descriptors == null) return 0;
		else return descriptors.size();
	}
	public IMolecularDescriptor getDescriptor(int index) {
		if (descriptors == null) return null;
		else return descriptors.get(index);
	}
	public IToxTreeEditor getEditor() {
		return new QSARModelEditor(this);
	}

    public boolean isCalculated(String name) {
        Boolean b = flag_calculated.get(name);
        if (b == null) return false;
        else return b.booleanValue();
    }

    public void setCalculated(IMolecularDescriptor descriptor,String name, boolean calculated) {
        flag_calculated.put(name,new Boolean(calculated));
        
    }
}


