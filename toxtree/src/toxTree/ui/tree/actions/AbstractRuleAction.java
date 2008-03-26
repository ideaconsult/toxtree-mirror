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
package toxTree.ui.tree.actions;

import java.awt.Component;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;

import toxTree.core.IDecisionRule;

/**
 * An {@link javax.swing.AbstractAction} descendant , performing an action on a preset {@link toxTree.core.IDecisionRule}
 * @author Nina Jeliazkova
 *
 */
public abstract class AbstractRuleAction extends AbstractAction implements IRuleAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8969299296011499641L;
	protected IDecisionRule rule;
	
	public AbstractRuleAction(IDecisionRule rule) {
		super();
		this.rule = rule;
	}

	public AbstractRuleAction(IDecisionRule rule,String arg0) {
		super(arg0);
		this.rule = rule;
	}

	public AbstractRuleAction(IDecisionRule rule,String arg0, Icon arg1) {
		super(arg0, arg1);
		this.rule = rule;
	}



	public IDecisionRule getRule() {
		return rule;
	}

	public void setRule(IDecisionRule rule) {
		this.rule = rule;
	}
    public JFrame getParentFrame() {
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
        return frame;
    }
}
