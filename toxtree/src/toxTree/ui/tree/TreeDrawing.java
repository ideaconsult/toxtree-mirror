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
package toxTree.ui.tree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import prefuse.Display;
import prefuse.util.display.ExportDisplayAction;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.tree.DecisionNode;
import toxTree.ui.tree.actions.SaveTreeAction;
import toxTree.ui.tree.images.ImageTools;
import toxTree.ui.tree.rules.RulePanel;

/**
 * Displays a clickable decision tree. On node click, the details of node and rule are displayed
 * in {@link DecisionNodePanel} and {@link RulePanel}. On right click a popup menu with {@link EditTreeActions} is launched.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class TreeDrawing extends JPanel implements PropertyChangeListener {
	protected RulePanel rulePanel = null;
	protected DecisionNodePanel nodePanel = null;
	protected ITreeView treeComponent = null;
	protected EditTreeActions actions ;
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8122365540682052287L;

	/**
	 * 
	 */
	public TreeDrawing(IDecisionMethod treeMethod, EditTreeActions actions) {
		super(new BorderLayout());
		addWidgets(treeMethod, actions);
	}

	protected void addWidgets(IDecisionMethod treeMethod, EditTreeActions actions) {
		this.actions = actions;
		JPanel p = new JPanel(new BorderLayout());
		//treeComponent = new JCustomTreeComponent(treeMethod,null);
        treeComponent = new TreePrefuse(treeMethod,null);

        treeComponent.addSelectRuleListener(this);

		JScrollPane treeView = new JScrollPane(treeComponent.getJComponent());
		treeView.setPreferredSize(new Dimension(500, 300));

		rulePanel = new RulePanel(null);

		p.add(treeView, BorderLayout.CENTER);
		
    	//ActionMap actionsNode =  null;
    	
    	if (treeMethod.isEditable() && (actions != null)) {
    		/*
    		actionsNode = new ActionMap();
	    	noBranchAction = new SetBranchAction(treeMethod,false,"Modify");
	    	actionsNode.put(DecisionNodePanel.ACTION_MODIFYNOBRANCH,noBranchAction);
	    	yesBranchAction = new SetBranchAction(treeMethod,true,"Modify");
	    	actionsNode.put(DecisionNodePanel.ACTION_MODIFYYESBRANCH,yesBranchAction);
	    	*/
	    	nodePanel = new DecisionNodePanel(actions.getTreeNodeActions());
    	} else nodePanel = new DecisionNodePanel(null);
    	
		nodePanel.setEditable(treeMethod.isEditable());
		p.add(nodePanel, BorderLayout.SOUTH);

		JSplitPane treePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p,
				rulePanel);
		treePane.setOneTouchExpandable(false);
		treePane.setDividerLocation(250);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		p.setMinimumSize(minimumSize);
		rulePanel.setMinimumSize(minimumSize);

		//treePane.setBorder(BorderFactory.createTitledBorder("Decision Tree"));

		add(treePane, BorderLayout.CENTER);
        
        JToolBar tb = new JToolBar(JToolBar.HORIZONTAL);
        add(tb,BorderLayout.NORTH);
        
        tb.add(new SaveTreeAction(treeMethod));
        if (treeComponent instanceof Display) {
            Action a = new ExportDisplayAction((Display)treeComponent);
            a.putValue(Action.SMALL_ICON, ImageTools.getImage("picture.png"));
            a.putValue(Action.SHORT_DESCRIPTION, "Export as .bmp, .jpg or .png image");
            a.putValue(Action.NAME, "Export as image");
            tb.add(a);
        }
        Action ha = treeComponent.getHelpAction();
        if (ha != null) 
            tb.add(ha);
	}
	public void setDecisionMethod(IDecisionMethod method) {
		treeComponent.setDecisionMethod(method);
	}
	public IDecisionMethod getDecisionMethod() {
		return treeComponent.getDecisionMethod();
	}	
	public void setNode(IDecisionRule node) {
		if (node instanceof IDecisionRule) {
			IDecisionRule nodeR = (IDecisionRule) node;
			nodePanel.setNode((Observable) node);
			if (node instanceof DecisionNode) {
				if (actions != null)
					actions.setNode((DecisionNode) node);
			}

			rulePanel.setRule(nodeR);
			IDecisionCategory category;
			IDecisionRule branch = treeComponent.getDecisionMethod().getBranch(nodeR,
					false);

			if (branch != null)
				nodePanel.setNoBranch((Observable) branch);
			else {
				category = treeComponent.getDecisionMethod().getCategory(nodeR, false);
				if (category == null) {
					// shouldn't get here
					System.err.print("ERROR: no branch and no category!");
					System.err.print(nodeR.getNum());
					System.err.print("\t");
					System.err.println(nodeR);
				}
				nodePanel.setNoBranch((Observable) category);
			}

			branch = treeComponent.getDecisionMethod().getBranch(nodeR, true);
			if (branch != null)
				nodePanel.setYesBranch((Observable) branch);

			else {
				category = treeComponent.getDecisionMethod().getCategory(nodeR, true);
				nodePanel.setYesBranch((Observable) category);
			}
		}		
	}

	public JComponent getJComponent() {
		return this;
	}

    public JPopupMenu getPopupMenu() {
        // TODO Auto-generated method stub
        return null;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        Object o = evt.getNewValue();
        if (o instanceof IDecisionRule)
            setNode((IDecisionRule)o);
        
    }
}
