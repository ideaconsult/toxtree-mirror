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

package toxtree.ui.tree.rules;

import java.awt.Container;
import java.util.Observable;

import javax.swing.JOptionPane;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxtree.ui.tree.ToxTreeEditorPanel;

public class RuleEditor extends ToxTreeEditorPanel implements
		IDecisionRuleEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3020833157127429753L;
	protected IDecisionRule rule = null;

	public RuleEditor(IDecisionRule object) {
		super(object);
		setRule(object);
	}

	public RuleEditor() {
		this(null);

	}
	@Override
	public void setID(String id) {
		if (rule != null) rule.setID(id);
	}

	@Override
	public void setTitle(String title) {
		if (rule != null) rule.setTitle(title);
	}

	@Override
	public void setExplanation(String explanation) {
		if (rule != null) rule.setExplanation(explanation);

	}

	@Override
	public String getID() {
		if (rule != null) return rule.getID();
		else return "";
	}

	@Override
	public String getTitle() {
		if (rule != null) return rule.getTitle();
		else return "";
	}

	@Override
	public String getExplanation() {
		if (rule != null) return rule.getExplanation();
		else return "";
	}

	public void setRule(IDecisionRule rule) {
		if (this.rule != null) 
	        if (this.rule instanceof Observable) 
	            ((Observable) this.rule).deleteObserver(this);
		
		this.rule = rule;
        if (rule instanceof Observable) { 
            ((Observable) rule).addObserver(this);
            update(((Observable) rule), null);
        }		
        
	}

	public IDecisionRule getRule() {
		return rule;
	}
	public IDecisionRule edit(Container owner, IDecisionRule rule) {
		setRule(rule);
		if (JOptionPane.showConfirmDialog(owner, this,"Rule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE )
				== JOptionPane.OK_OPTION)
			return rule;
		else
			return null;
	}
}


