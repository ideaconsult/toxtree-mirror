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
package toxTree.ui.tree.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.ui.tree.images.ImageTools;

/**
 * Launches {@link toxTree.core.IDecisionRuleEditor} of a preset rule.
 * An {@link toxTree.ui.tree.actions.AbstractTreeAction} descendant
 * 
 * @author Nina  jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class EditRuleAction extends AbstractTreeAction implements IRuleAction {
	protected IDecisionRule rule;
	private static final long serialVersionUID = 2770657186363871323L;

	public EditRuleAction(IDecisionMethod tree) {
		this(tree,"Edit rule");
	}

	public EditRuleAction(IDecisionMethod tree, String arg0) {
		this(tree, arg0,ImageTools.getImage("tree.png"));
	}

	public EditRuleAction(IDecisionMethod tree, String arg0, Icon arg1) {
		super(tree, arg0, arg1);
		rule = null;
		putValue(AbstractAction.SHORT_DESCRIPTION,"Launches rule visualization/editor.");	

	}


	public void actionPerformed(ActionEvent arg0) {
		if (rule != null) {
            /*
			JFrame frame = null;
			Object p = getValue(AbstractTreeAction.PARENTKEY);
			if (p!=null) {
				if (p  instanceof JFrame) frame = (JFrame) p;
				else 
				while (p !=null) {
					p = ((Component) p).getParent();
					if (p instanceof JFrame) {
						frame = (JFrame) p;
						break;
					}
				}
			}
            */
			rule.getEditor().edit(getParentFrame(),rule);
		}	
	}
	public IDecisionRule getRule() {
		return rule;
	}
	public void setRule(IDecisionRule rule) {
		this.rule = rule;
		
	}

}
