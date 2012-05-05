/*
Copyright (C) 2005-2006  

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

package toxtree.ui.tree.qsar;

import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import org.openscience.cdk.IImplementationSpecification;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.IDescriptor;


public class DescriptorOptions extends OptionsPanel<IDescriptor> {
	protected static String[] s = {"<html><i>Specification</i></html>",
			"<html><i>Implementation Title</i></html>",
			"<html><i>Implementation Identifier</i></html>",
			"<html><i>Implementation Vendor</i></html>"};
	protected Dimension dim = new Dimension(Integer.MAX_VALUE,18);
	protected Dimension dimmin = new Dimension(100,18);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1067355677215575135L;

	@Override
	public int getHeaderRows() {
		return 4;
	}
	@Override
	public JLabel createHeaderLabel(int index) {
		return new JLabel(s[index]);
	}
	@Override
	public JFormattedTextField createHeaderTextField(int i) {
		JFormattedTextField t = new JFormattedTextField();
		t.setEditable(false);
		t.setPreferredSize(dim);
		t.setMinimumSize(dimmin);
		if (getObject()!=null) {
			IImplementationSpecification s = getObject().getSpecification();
			switch (i) {
			case 0: {t.setText(s.getSpecificationReference()); break;}
			case 1: {t.setText(s.getImplementationTitle());break;}
			case 2: {t.setText(s.getImplementationIdentifier());break;}
			case 3: {t.setText(s.getImplementationVendor());break;}
			}
			t.setToolTipText(t.getText());
		}
		return t;
	}
	@Override
	public int getDataRows() {
		if (getObject() == null) return 0;
		else return getObject().getParameterNames().length;
	}
	@Override
	public JFormattedTextField createDataTextField(int i) {
		StringBuffer b = new StringBuffer();
		Object[] params = getObject().getParameters();
		
		if (params[i] instanceof Object[]) {
			for (int j=0; j < ((Object[]) params[i]).length; j++) {
				if (j>0) b.append(',');
				b.append(((Object[]) params[i])[j].toString());
			}
		} else if (params[i] instanceof QueryAtomContainer) {
			b.append(((QueryAtomContainer)params[i]).getID());
		} else b.append(params[i].toString()); 
		JFormattedTextField t = new JFormattedTextField(b.toString());
		t.setPreferredSize(dim);
		t.setMinimumSize(dimmin);
		return t;
	}
	@Override
	public JLabel createDataLabel(int i) {
		String[] names = getObject().getParameterNames();
		return new JLabel(names[i]);
	}

}


