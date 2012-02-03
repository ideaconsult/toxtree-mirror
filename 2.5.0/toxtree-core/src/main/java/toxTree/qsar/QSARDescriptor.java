/*
Copyright (C) 2005-2008  

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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.core.Introspection;

/**
 * Encapsulating descriptor name (in a model) and descriptor calculation 
 * @author nina
 *
 */
public class QSARDescriptor implements Serializable, Comparable<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3702178459249696653L;
	protected String descriptorName;
	protected transient IMolecularDescriptor descriptor;
	//protected IDescriptorPreprocessor preprocessor;
	protected boolean calculated;
	protected String descriptorClass;

	public QSARDescriptor(String name, IMolecularDescriptor descriptor) {
		setDescriptorName(name);
		setDescriptor(descriptor);
		//setPreprocessor(preprocessor);
		setCalculated(false);
	}
	public IMolecularDescriptor getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(IMolecularDescriptor descriptor) {
		this.descriptor = descriptor;
		this.descriptorClass = descriptor.getClass().getName();
	}
	public String getDescriptorName() {
		return descriptorName;
	}
	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}
	/*
	public IDescriptorPreprocessor getPreprocessor() {
		return preprocessor;
	}
	public void setPreprocessor(IDescriptorPreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}
	*/
	public boolean isCalculated() {
		return calculated;
	}
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		Thread.currentThread().setContextClassLoader(Introspection.getLoader());
		in.defaultReadObject();
		
		if (!descriptorClass.equals("")) 
		try {
			descriptor = (IMolecularDescriptor)Introspection.loadCreateObject(descriptorClass);
		} catch (Exception x) {
			x.printStackTrace();
			descriptor = null;
		}
		setCalculated(false);

	}
	public int compareTo(String arg0) {
		return descriptorName.compareTo(arg0);
	}
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof QSARDescriptor) {
			QSARDescriptor qd = (QSARDescriptor) arg0;
			return (qd.getDescriptor() == getDescriptor()) 
			&& (qd.getDescriptorName().equals(getDescriptorName()));
		} else return descriptorName.equals(arg0.toString());
	}
}


