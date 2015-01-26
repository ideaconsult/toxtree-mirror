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

package toxtree.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IFilteredMolecules;
import toxTree.data.IObjectFilter;
import toxTree.data.ListOfAtomContainers;
import toxTree.data.MoleculesIterator;
import toxTree.exceptions.FilterException;
import toxtree.ui.HashtableModel;

public class FilteredMoleculesIterator extends MoleculesIterator implements IFilteredMolecules {
    protected Hashtable<IObjectFilter, ListOfAtomContainers> filteredSets;
    protected ListOfAtomContainers activeContainer = null;
    protected IObjectFilter activeFilter = null;

    public FilteredMoleculesIterator() {
	super();
	filteredSets = new Hashtable<IObjectFilter, ListOfAtomContainers>();
	activeContainer = containers;
    }

    protected ListOfAtomContainers filter(IObjectFilter filter) {
	ListOfAtomContainers filtered = new ListOfAtomContainers();
	for (int i = 0; i < containers.size(); i++) {
	    IAtomContainer a = containers.getAtomContainer(i);
	    if (filter.accept(a))
		filtered.addAtomContainer(a);
	}
	return filtered;
    }

    protected void deleteAllFilters() {
	Enumeration<IObjectFilter> e = filteredSets.keys();
	while (e.hasMoreElements()) {
	    ListOfAtomContainers a = filteredSets.get(e.nextElement());
	    a.removeAllAtomContainers();
	}
	filteredSets.clear();
	setActiveContainer(containers);
    }

    public void addFilter(IObjectFilter filter) throws toxTree.exceptions.FilterException {
	setActiveContainer(containers);
	ListOfAtomContainers a = filteredSets.get(filter);
	if (a != null)
	    a.removeAllAtomContainers();
	filteredSets.remove(filter);
	filteredSets.put(filter, filter(filter));
    }

    public void deleteFilter(IObjectFilter filter) throws toxTree.exceptions.FilterException {
	ListOfAtomContainers a = filteredSets.get(filter);
	if (a != null) {
	    filteredSets.remove(filter);
	    a.removeAllAtomContainers();
	}
    }

    /**
     * Sets the current set of molecules. If null, this will be the parent set.
     */
    public boolean setFilter(IObjectFilter filter, boolean update) throws FilterException {
	activeFilter = filter;
	if (filter == null)
	    setActiveContainer(containers);
	else {
	    if (update)
		addFilter(filter);
	    ListOfAtomContainers list = filteredSets.get(filter);
	    if (list.getAtomContainerCount() > 0)
		setActiveContainer(list);
	    else
		setActiveContainer(containers);
	}
	return true;
    }

    public void update(IObjectFilter filter) throws toxTree.exceptions.FilterException {
	ListOfAtomContainers a = filteredSets.get(filter);
	if (a != null)
	    a.removeAllAtomContainers();
	addFilter(filter);
    }

    @Override
    public void addMolecule(IAtomContainer mol) {
	// Will add only to the parent set, not to the filtered set
	if (containers != getActiveContainer())
	    setActiveContainer(containers);
	super.addMolecule(mol);
    }

    @Override
    public void setMolecules(List molecules) {
	deleteAllFilters();
	super.setMolecules(molecules);

    }

    @Override
    public void clear() {
	deleteAllFilters();
	super.clear();
    }

    @Override
    protected ListOfAtomContainers getContainers() {
	return activeContainer;
    }

    protected ListOfAtomContainers getActiveContainer() {
	return activeContainer;
    }

    protected void setActiveContainer(ListOfAtomContainers activeContainer) {
	this.activeContainer = activeContainer;
	first();
    }

    public boolean selectFilter(Component parentComponent) throws FilterException {
	FilterPanel panel = new FilterPanel(filteredSets, getActiveContainer() != containers);
	if (JOptionPane.showConfirmDialog(parentComponent, panel, "Select set of compounds to display",
		JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null) == JOptionPane.OK_OPTION) {
	    if (panel.getFilter() != null)
		return setFilter(panel.getFilter(), false);
	    else
		return false;
	} else
	    return false;
    }

    @Override
    public String toString() {
	if (activeFilter != null)
	    return super.toString() + " [" + activeFilter.toString() + "]";
	else
	    return super.toString();
    }

}

class FilterPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1758344584424726368L;
    protected IObjectFilter filter = null;
    protected JTable table;
    protected JRadioButton all;
    protected JRadioButton subsets;

    public FilterPanel(Hashtable<IObjectFilter, ListOfAtomContainers> filteredSets, boolean filtered) {
	super(new BorderLayout());
	HashtableModel model = new HashtableModel(filteredSets) {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -6200245985396616635L;

	    @Override
	    public Object getValueAt(int row, int col) {
		switch (col) {
		case 1: {
		    Object o = super.getValueAt(row, col);
		    if (o instanceof ListOfAtomContainers)
			return ((ListOfAtomContainers) o).getAtomContainerCount();
		    else
			return o.toString();
		}
		default:
		    return super.getValueAt(row, col);
		}

	    }

	    @Override
	    public String getColumnName(int arg0) {
		switch (arg0) {
		case 0:
		    return "Categories";
		case 1:
		    return "Compounds";
		default:
		    return "";
		}
	    }
	};
	table = new JTable(model) {

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 6707892334942053004L;

	    public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (c instanceof JComponent)
		    ((JComponent) c).setToolTipText(getValueAt(row, column).toString());
		return c;
	    };
	};
	table.getColumnModel().getColumn(1).setMaxWidth(100);
	table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
		    return;
		else {

		    setFilter(((ListSelectionModel) e.getSource()).getMinSelectionIndex());
		}
	    }
	});
	add(new JScrollPane(table), BorderLayout.CENTER);

	all = new JRadioButton("All");

	subsets = new JRadioButton("Subsets");
	ButtonGroup g = new ButtonGroup();

	g.add(all);
	g.add(subsets);

	if (filtered)
	    subsets.setSelected(filtered);
	else
	    all.setSelected(!filtered);

	JPanel t = new JPanel();
	t.add(all);
	t.add(subsets);
	add(t, BorderLayout.NORTH);
	/*
	 * JToolBar t = new JToolBar(); Object[] keys = actions.keys(); for (int
	 * i=0; i < keys.length;i++) t.add(actions.get(keys[i]));
	 */

    }

    protected void setFilter(int selectedIndex) {
	Object o = table.getValueAt(selectedIndex, 0);
	if (o instanceof IObjectFilter)
	    setFilter((IObjectFilter) o);
    }

    public IObjectFilter getFilter() {
	if (all.isSelected())
	    return null;
	else
	    return filter;
    }

    public void setFilter(IObjectFilter filter) {
	this.filter = filter;
	subsets.setSelected(true);
    }
}
