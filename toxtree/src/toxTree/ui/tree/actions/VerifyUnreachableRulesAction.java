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
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRuleList;
import toxTree.ui.tree.images.ImageTools;

/**
 * Verifies if there are unreachable rules; If found, ask user to delete them.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class VerifyUnreachableRulesAction extends AbstractTreeAction  {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8060426219000130503L;

	/**
	 * @param tree
	 */
	public VerifyUnreachableRulesAction(IDecisionMethod tree) {
		this(tree,"Find unused rules");
	}

	/**
	 * @param tree
	 * @param name
	 */
	public VerifyUnreachableRulesAction(IDecisionMethod tree, String name) {
		this(tree, name,ImageTools.getImage("tree_delete.png"));
	}

	/**
	 * @param tree
	 * @param name
	 * @param icon
	 */
	public VerifyUnreachableRulesAction(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION, "Identifies which rules within this list are not used in the decision tree and flags them as unused.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	//	JOptionPane.showMessageDialog(null,"to be done");
		IDecisionRuleList unreachableRules = tree.hasUnreachableRules();
		if (unreachableRules != null) {
            
       	 	if (JOptionPane.showConfirmDialog(getParentFrame(),
       	 		"The decision tree has unreachable rules. \nDelete unreachable rules?","Please confirm",JOptionPane.YES_NO_OPTION)
        		==JOptionPane.YES_OPTION) {
       	 		IDecisionRuleList rules = tree.getRules();
       	 		for (int i=0;i<unreachableRules.size();i++) {
       	 			rules.remove(unreachableRules.get(i));
       	 		}
       	 		System.out.println("Rules new size\t" + rules.size());
       	 		for (int i=0;i<rules.size();i++) {
       	 			rules.getRule(i).setNum(i);
       	 		}
       	 		//((Observable)rules).setChanged();
       	 		((Observable)rules).notifyObservers();
       	 	}
		}
		//tree.getRules().remove(rule);
	}


}
