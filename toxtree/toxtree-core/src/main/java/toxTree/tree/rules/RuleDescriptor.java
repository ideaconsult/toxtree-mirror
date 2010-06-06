/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.tree.rules;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.openscience.jchempaint.renderer.selection.SingleSelection;

import toxTree.core.Introspection;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import ambit2.base.exceptions.AmbitException;
import ambit2.base.interfaces.IProcessor;
import ambit2.core.data.MoleculeTools;

/**
 * An abstract class to allow for descriptor analysis
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-18
 */
public abstract class RuleDescriptor extends AbstractRule {
	protected transient IDescriptor descriptor = null;
	protected String descriptorClass;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1869386647652135625L;

	public RuleDescriptor() {
		super();
		setDescriptor(null);
	}

	public IDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(IDescriptor descriptor) {
		this.descriptor = descriptor;
		if (descriptor != null) {
			descriptorClass = descriptor.getClass().getName();
			setTitle(descriptor.toString());
		} else descriptorClass = "";	
	}

	@Override
	public boolean isImplemented() {
		return (descriptor != null);
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}


	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		if (!descriptorClass.equals("")) 
			try {
				descriptor = (IDescriptor)Introspection.loadCreateObject(descriptorClass);
			} catch (Exception x) {
				descriptor = null;
			}
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RuleDescriptor)
			return descriptorClass.equals(((RuleDescriptor)obj).descriptorClass);
		else return false;
	}

	public boolean  verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol,IAtomContainer selected) throws DecisionMethodException {
		boolean ok = verifyRule(mol);
		if (ok && (selected!=null))
			selected.add(mol);
		return ok;
	}
	
	 public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
	    	return new IProcessor<IAtomContainer, IChemObjectSelection>() {
	    		public IChemObjectSelection process(IAtomContainer mol)
	    				throws AmbitException {
	    			try {
	    				IAtomContainer selected = MoleculeTools.newAtomContainer(NoNotificationChemObjectBuilder.getInstance());
		    			verifyRule(mol, selected);
		    			return new SingleSelection<IAtomContainer>(selected);
	    			} catch (DecisionMethodException x) {
	    				throw new AmbitException(x);
	    			}
	    		}
	    		public boolean isEnabled() {
	    			return true;
	    		}
	    		public long getID() {
	    			return 0;
	    		}
	    		public void setEnabled(boolean arg0) {
	    		}
	    	};
	    }	
}
