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
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.io.Tools;
import toxTree.ui.tree.rules.RulePanel;

/**
 * 
 * Displays decision tree by standart {@link JTree} component.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class TreePanel extends JPanel implements TreeSelectionListener, Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6284436109070270704L;

	protected DecisionTreeModel treeModel = null;
	protected JTextArea reference = null;
	protected JTree tree = null;

	protected RulePanel rulePanel = null;

	DecisionNodePanel nodePanel = null;

	public TreePanel(IDecisionMethod treeMethod) {
		super(new BorderLayout());
		this.treeModel = new DecisionTreeModel(treeMethod);
		addWidgets(treeMethod);
	}

	protected void addWidgets(IDecisionMethod treeMethod) {
		JPanel p = new JPanel(new BorderLayout());
		reference = new JTextArea();
		reference.setWrapStyleWord(true);
		reference.setColumns(30);
		reference.setEditable(treeMethod.isEditable());
		reference.setAutoscrolls(false);
		reference.setPreferredSize(new Dimension(500, 24));
		reference.setText(treeMethod.getExplanation());
		reference.setToolTipText(treeMethod.getExplanation());
		JScrollPane referenceView = new JScrollPane(reference);
		referenceView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		referenceView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		p.add(referenceView,BorderLayout.NORTH);
		
		if (treeModel == null)
			this.treeModel = new DecisionTreeModel();
		tree = new JTree(treeModel);
		   // Retrieve the three icons
	    Icon leafIcon = Tools.getImage("tick.png");
	    Icon openIcon = Tools.getImage("open.gif");
	    Icon closedIcon = Tools.getImage("open.gif");
	    
	    // Update only one tree instance
	    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)tree.getCellRenderer();
	    renderer.setLeafIcon(leafIcon);
	    renderer.setClosedIcon(closedIcon);
	    renderer.setOpenIcon(openIcon);
	    
	    // Change defaults so that all new tree components will have new icons
	    UIManager.put("Tree.leafIcon", leafIcon);
	    UIManager.put("Tree.openIcon", openIcon);
	    UIManager.put("Tree.closedIcon", closedIcon);
	    
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		JScrollPane treeView = new JScrollPane(tree);
		treeView.setPreferredSize(new Dimension(500, 300));

		rulePanel = new RulePanel(null);

		p.add(treeView, BorderLayout.CENTER);
		nodePanel = new DecisionNodePanel(null);

		p.add(nodePanel, BorderLayout.SOUTH);

		JSplitPane treePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p,
				rulePanel);
		treePane.setOneTouchExpandable(false);
		treePane.setDividerLocation(250);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		p.setMinimumSize(minimumSize);
		rulePanel.setMinimumSize(minimumSize);

		treePane.setBorder(BorderFactory.createTitledBorder("Decision Tree"));

		add(treePane, BorderLayout.CENTER);
	}

	public void valueChanged(TreeSelectionEvent e) {
		Object node = (Object) tree.getLastSelectedPathComponent();

		if (node == null)
			return;

		if (node instanceof IDecisionRule) {
			IDecisionRule nodeR = (IDecisionRule) node;
			nodePanel.setNode((Observable) node);

			rulePanel.setRule(nodeR);
			IDecisionCategory category;
			IDecisionRule branch = treeModel.decisionMethod.getBranch(nodeR,
					false);

			if (branch != null)
				nodePanel.setNoBranch((Observable) branch);
			else {
				category = treeModel.decisionMethod.getCategory(nodeR, false);
				if (category == null) {
					// shouldn't get here
					System.err.print("ERROR: no branch and no category!");
					System.err.print(nodeR.toString());
					System.err.print("\t");
					System.err.println(nodeR);
				}
				nodePanel.setNoBranch((Observable) category);
			}

			branch = treeModel.decisionMethod.getBranch(nodeR, true);
			if (branch != null)
				nodePanel.setYesBranch((Observable) branch);

			else {
				category = treeModel.decisionMethod.getCategory(nodeR, true);
				nodePanel.setYesBranch((Observable) category);
			}
		}
	}

	public void setDecisionMethod(IDecisionMethod decisionMethod) {
		treeModel.decisionMethod = decisionMethod;

	}

	public void update(Observable arg0, Object arg1) {
		treeModel.fireStructureChanged();
		
	}

	
}
