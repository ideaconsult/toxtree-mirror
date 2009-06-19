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


package toxTree.ui.molecule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.openscience.cdk.interfaces.IAtomContainer;

public class PropertyPanel extends JPanel {
	protected AtomContainerPropertyTableModel tableModel;
	/**
	 * 
	 */
	private static final long serialVersionUID = -712606150162538423L;

	public PropertyPanel() {
		this(Color.white,Color.BLACK);
	}
	
	public PropertyPanel(Color bgColor, Color fColor) {
		super(new BorderLayout());
		addWidgets(bgColor,fColor);
	}

	protected void addWidgets(Color bgColor, Color fColor) {
        JLabel labelA = new JLabel("<html><b>Available structure attributes</b></html>");
        labelA.setOpaque(true);
        labelA.setBackground(bgColor);
        labelA.setForeground(fColor);
        labelA.setSize(120,32);
        labelA.setAlignmentX(CENTER_ALIGNMENT);
        labelA.setBorder(BorderFactory.createMatteBorder(5,0,0,0,bgColor));
        add(labelA,BorderLayout.NORTH);
        
        //molecule properties instead cas/name text boxes
        tableModel = new AtomContainerPropertyTableModel();
		JTable table = new JTable(tableModel) {
			public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int rowIndex, int vColIndex) {
				Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
	            if (c instanceof JComponent) {
	                JComponent jc = (JComponent)c;
	                jc.setToolTipText((String)getValueAt(rowIndex, vColIndex));
	            }
	            return c;

			};
		};
		table.setTableHeader(null);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setPreferredScrollableViewportSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setOpaque(true);
		
		
		JScrollPane p = new JScrollPane(table);
     	p.setPreferredSize(new Dimension(256,3*24+2));
     	p.setMinimumSize(new Dimension(256,3*24+2));
     	p.setBackground(bgColor);
     	p.setOpaque(true);
		table.setBackground(Color.white);
		add(p,BorderLayout.CENTER);

	}

	public AtomContainerPropertyTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(AtomContainerPropertyTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setAtomContainer(IAtomContainer ac) {
		tableModel.setAtomContainer(ac);
	}
}


