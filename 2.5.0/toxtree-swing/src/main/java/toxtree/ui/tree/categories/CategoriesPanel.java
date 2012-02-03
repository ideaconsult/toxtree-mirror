/*
Copyright (C) 2005-2006  

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
*//**
 * <b>Filename</b> CategoriesPanel.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxtree.ui.tree.categories;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionResult;
import toxTree.ui.tree.categories.CategoriesRenderer;

/**
 * Displays categories in a coloured list. Colors are prepared by {@link toxTree.ui.tree.categories.CategoriesRenderer} 
 * @author Nina Jeliazkova <br>
 *         <b>Modified</b> 2005-8-8
 */
public class CategoriesPanel extends JPanel implements Observer {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2440504663404133065L;

	protected IDecisionCategories categories = null;

	protected IDecisionResult result = null;

	protected CategoriesRenderer categoriesRenderer = null;

	CategoriesTableModel model = null;

	/**
	 * 
	 */
	public CategoriesPanel(IDecisionCategories categories,
			IDecisionResult result) {
		super();
		this.result = result;
		this.categories = categories;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		if (result instanceof Observable)
			((Observable) result).addObserver(this);
		categoriesRenderer = new CategoriesRenderer(categories);
		model = new CategoriesTableModel(categories);
		JTable table = new JTable(model) {

			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = -7529863178570951997L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.JTable#getCellRenderer(int, int)
			 */
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				DefaultTableCellRenderer tr = (DefaultTableCellRenderer) super
						.getCellRenderer(row, column);
				CategoriesTableModel m = (CategoriesTableModel) dataModel;
				if (m.isSelected(row))
					tr.setBackground(categoriesRenderer.getShowColor(row));
				else
					tr.setBackground(categoriesRenderer.getHideColor(row));
				tr.setForeground(Color.white);
				tr.setAlignmentX(CENTER_ALIGNMENT);

				IDecisionCategory c = m.getCategory(row);
				if (c != null)
					tr.setToolTipText(c.getExplanation());// +
															// c.getThreshold());

				return tr;

			}
		};
		table.setTableHeader(null);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setPreferredScrollableViewportSize(new Dimension(
				Integer.MAX_VALUE, Integer.MAX_VALUE));
		// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setOpaque(true);

		JScrollPane p = new JScrollPane(table);
		p.setPreferredSize(new Dimension(256, 3 * 48 + 2));
		p.setMinimumSize(new Dimension(256, 3 * 48 + 2));
		p.setBackground(Color.black);
		p.setOpaque(true);
		table.setRowHeight(48);
		table.setBackground(Color.black);
		add(p, BorderLayout.CENTER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o instanceof IDecisionCategories) {
			model.setList((IDecisionCategories) o);
			categories = (IDecisionCategories) o;
			categoriesRenderer.setCategories(categories);
		}
	}

	public void setData(IDecisionCategories categories, IDecisionResult result) {
		model.setList(categories);
		this.categories = categories;
		categoriesRenderer.setCategories(categories);
		repaint();
	}

}
