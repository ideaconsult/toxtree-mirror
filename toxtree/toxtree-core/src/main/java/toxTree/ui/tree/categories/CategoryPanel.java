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
*/

package toxTree.ui.tree.categories;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Observable;

import javax.swing.JOptionPane;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionCategoryEditor;
import toxTree.ui.tree.ToxTreeEditorPanel;

public class CategoryPanel extends ToxTreeEditorPanel implements IDecisionCategoryEditor {
	protected IDecisionCategory category;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7058699257406721869L;


	public CategoryPanel(IDecisionCategory category) {
		super(category);
	}

	public CategoryPanel() {
		this(null);
		

	}

	@Override
	protected void addWidgets(Object object) {
		super.addWidgets(object);
		setCategory((IDecisionCategory)object);
		add(explanationPane,BorderLayout.CENTER);
	}
	public void setCategory(IDecisionCategory category) {
		if (this.category != null) 
	        if (this.category instanceof Observable) 
	            ((Observable) this.category).deleteObserver(this);
		
		this.category = category;
        if (category instanceof Observable) { 
            ((Observable) category).addObserver(this);
            update(((Observable) category), null);
        }
        
	}

	public IDecisionCategory getCategory() {
		return category;
	}

	public IDecisionCategory edit(Component owner, IDecisionCategory category) {
		setCategory(category);
		if (JOptionPane.showConfirmDialog(owner, this,"Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE )
				== JOptionPane.OK_OPTION)
			return category;
		else
			return null;
	}

	@Override
	public void setID(String id) {
		if (category!=null)
			try {
				category.setID(Integer.parseInt(id));
			} catch (Exception x) {
				category.setID(1);
			}
	}

	@Override
	public void setTitle(String title) {
		if (category!=null) category.setName(title);
		
	}

	@Override
	public void setExplanation(String explanation) {
		if (category!=null) category.setExplanation(explanation);
	}

	@Override
	public String getID() {
		if (category!=null) return Integer.toString(category.getID());
		else return "";
	}

	@Override
	public String getTitle() {
		if (category!=null) return category.getName();
		else return "";
	}

	@Override
	public String getExplanation() {
		if (category!=null) return category.getExplanation();
		else return "";
	}

	

}


