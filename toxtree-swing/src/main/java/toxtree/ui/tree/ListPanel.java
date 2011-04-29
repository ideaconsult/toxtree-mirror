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
package toxtree.ui.tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * A JPanel, displaying a table based on {@link ListTableModel}.
 * @author Nina Jeliazkova
 * 
 */
public class ListPanel extends JPanel implements ListSelectionListener {
	protected ListTableModel model;
	protected JTable table;
	ListSelectionModel listSelectionModel;
	Object selectedObject = null;
	String caption;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765802952299904184L;

	/**
	 * 
	 */
	public ListPanel(String caption, ListTableModel listModel, ActionMap actions) {
		super();
		addWidgets(caption, listModel, actions);
	}

	/**
	 * @param arg0
	 */
	public ListPanel(String caption, ListTableModel listModel, boolean arg0, ActionMap actions) {
		super(arg0);
		addWidgets(caption, listModel,actions);
	}

	/**
	 * @param arg0
	 */
	public ListPanel(String caption, ListTableModel listModel, LayoutManager arg0, ActionMap actions) {
		super(arg0);
		addWidgets(caption, listModel,actions);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ListPanel(String caption,ListTableModel listModel, LayoutManager arg0, boolean arg1, ActionMap actions) {
		super(arg0, arg1);
		addWidgets(caption, listModel,actions);
	}


	protected void addWidgets(String caption, ListTableModel listModel, ActionMap actions) {
		this.caption = caption;
		setLayout(new BorderLayout());
		this.model = listModel;
		

		
		table = new JTable(model,createColumnModel(model));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setPreferredScrollableViewportSize(new Dimension(
				Integer.MAX_VALUE, Integer.MAX_VALUE));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setOpaque(true);
		

		listSelectionModel = table.getSelectionModel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane p = new JScrollPane(table);
		p.setAutoscrolls(true);
		p.setPreferredSize(new Dimension(400, 200));
		p.setMinimumSize(new Dimension(200, 100));
		p.setOpaque(true);
		add(p, BorderLayout.CENTER);

		if (actions != null) {
			JToolBar actionPanel = new JToolBar();
			actionPanel.setOrientation(JToolBar.VERTICAL);
			actionPanel.setFloatable(false);
			//actionPanel.setPreferredSize(new Dimension(100, 200));
			add(actionPanel, BorderLayout.NORTH);
			
			Dimension d = new Dimension(Integer.MAX_VALUE, 24);
			Object[] keys = actions.keys();
			for (int i=0; i < keys.length; i++) {
				JButton b = new JButton(actions.get(keys[i]));
				b.setHorizontalAlignment(SwingConstants.CENTER);
				b.setForeground(Color.blue);
				b.setPreferredSize(d);
				actionPanel.add(b);
			}
			
		}
		
		setBorder(BorderFactory.createTitledBorder(caption));
		/*
		deleteAction = new AbstractAction() {
			private static final long serialVersionUID = 2703762228592980943L;
			public void actionPerformed(ActionEvent arg0) {
				model.list.remove(selectedObject);
				selectedObject = null;
			}
		};
		*/
		
	}
    public TableColumnModel createColumnModel(ListTableModel model) {
        TableColumnModel m = new DefaultTableColumnModel();
        for (int i=0;i<model.getColumnCount();i++) {
            TableColumn t = new TableColumn(i);
            if (i==0) t.setWidth(300); else {
                t.setMaxWidth(64);
                t.setWidth(48);
            }
            
            TableCellRenderer r = new DefaultTableCellRenderer() {
                /* (non-Javadoc)
                 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
                 */
                @Override
				public Component getTableCellRendererComponent(JTable table,
                        Object value, boolean isSelected, boolean hasFocus,
                        int row, int column) {
                	Object s = table.getValueAt(row,column);
                	if (s == null) s = "";
                    setToolTipText(table.getColumnName(column) + " [" + s.toString()+"]");
                    return super.getTableCellRendererComponent(table, value,
                            isSelected, hasFocus, row, column);
                    
                    
                }
            };
            t.setHeaderValue(model.getColumnName(i));
            t.setCellRenderer(r);
            m.addColumn(t);
        }
        
        return m;
    }
	public void addListSelectionListener(ListSelectionListener listener) {
		listSelectionModel.addListSelectionListener(listener);
	}

	public Object getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Object selectedObject,int index) {
		this.selectedObject = selectedObject;
	};

	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		if (!lsm.isSelectionEmpty())
			try {
                //System.out.println(lsm.getMinSelectionIndex());
				setSelectedObject(model.list.get(lsm.getMinSelectionIndex()),lsm.getMinSelectionIndex());
			} catch (Exception x) {
				x.printStackTrace();
			}
	}
	public void setList(List list) {
		model.setList(list);
	}
	@Override
	public String toString() {

	    return caption;
	}
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model.getValueAt(rowIndex, columnIndex);
    }

	public ListTableModel getModel() {
		return model;
	}

	public void setModel(ListTableModel model) {
		this.model = model;
		table.setModel(model);
	}
}

