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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.openscience.cdk.qsar.IDescriptor;

import toxTree.core.IToxTreeEditor;
import toxTree.qsar.AbstractQSARModel;
import toxtree.ui.tree.ListPanel;
import toxtree.ui.tree.ListTableModel;

public class QSARModelEditor extends JTabbedPane implements IToxTreeEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4958415500498598721L;

	public QSARModelEditor(AbstractQSARModel model) {
		super();
		
		OptionsPanel<AbstractQSARModel> m = new OptionsPanel<AbstractQSARModel>(model) {
			/**
		     * 
		     */
		    private static final long serialVersionUID = -4814907876586431137L;
			@Override
			public int getHeaderRows() {
				return 0;
			}
			@Override
			public JLabel createHeaderLabel(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public JFormattedTextField createHeaderTextField(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public int getDataRows() {
				return 1+getObject().getNumberofDescriptors();
			}
			@Override
			public JLabel createDataLabel(int index) {
				if (index==0)
					return new JLabel("Predicted property");
				else 
					return new JLabel("Descriptor");
			}
			@Override
			public JFormattedTextField createDataTextField(int index) {
				if (index==0)
					return new JFormattedTextField(getObject().getPredictedproperty().toString());
				else 
					return new JFormattedTextField(getObject().getDescriptorNames().get(index-1));
			}
		};
		/*
		protected List<String> descriptorNames;
		private transient List<IMolecularDescriptor> descriptors;
		private List<String> descriptorsClass;
		protected String predictedproperty;
		protected String name;
		*/
		//descriptors
		final DescriptorOptions o = new DescriptorOptions();
		ListPanel p = new ListPanel("Descriptor",new ListTableModel(model.getDescriptors()),null) {
			
			/**
		     * 
		     */
		    private static final long serialVersionUID = 4572409802215466122L;

			@Override
			public void setSelectedObject(Object selectedObject, int index) {
				super.setSelectedObject(selectedObject, index);
				if (selectedObject instanceof IDescriptor)
					o.setObject((IDescriptor)selectedObject);
			}
		};
		p.setPreferredSize(new Dimension(500,100));
		p.addListSelectionListener(p);
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,o);
		//sp.setDividerLocation(200);
		
		addTab("Model", m);
		addTab("Descriptors", sp);

		setPreferredSize(new Dimension(500,500));

	}
	
	public Component getVisualCompoment() {
		return this;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEditable(boolean editable) {
		// TODO Auto-generated method stub

	}

}


