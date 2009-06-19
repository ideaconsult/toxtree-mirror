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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;
import toxTree.tree.DecisionNode;

/**
 * Displays a decision node {@link toxTree.tree.DecisionNode}.
 * Used in {@link toxTree.ui.tree.EditTreeFrame} and 
 * {@link toxTree.ui.tree.TreeFrame}
 * 
 * @author Nina Jeliazkova
 *  <b>Modified</b> 2005-9-22
 */
public class DecisionNodePanel extends JPanel implements Observer {
	protected Color bgColor = new Color(214,223,247);
	JLabel nodeLabel = null;

	JLabel noBranchLabel = null;

	JLabel yesBranchLabel = null;

	JLabel nodeEdit = null;

	JLabel noBranchEdit = null;

	JLabel yesBranchEdit = null;

	/*
	JButton modifyNode = null;

	JButton modifyNoBranch = null;

	JButton modifyYesBranch = null;
	*/
	Action modifyNode = null;
	Action modifyNoBranch = null;
	Action modifyYesBranch = null;

	protected Observable node = null;

	protected Observable noBranch = null;

	protected Observable yesBranch = null;

	boolean editable = false;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7334738022319991601L;

	/**
	 * 
	 */
	public DecisionNodePanel(ActionMap actions) {
		super(new SpringLayout());
		this.setPreferredSize(new Dimension(500, 64));
		addWidgets(actions);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public DecisionNodePanel(boolean isDoubleBuffered, ActionMap actions) {
		super(new SpringLayout(), isDoubleBuffered);
		this.setPreferredSize(new Dimension(500, 64));
		addWidgets(actions);
	}

	/**
	 * @param layout
	 */
	public DecisionNodePanel(LayoutManager layout, ActionMap actions) {
		super(new SpringLayout());
		this.setPreferredSize(new Dimension(500, 64));
		addWidgets(actions);
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public DecisionNodePanel(LayoutManager layout, boolean isDoubleBuffered,
			ActionMap actions) {
		super(new SpringLayout(), isDoubleBuffered);
		this.setPreferredSize(new Dimension(600, 64));
		addWidgets(actions);

	}

	protected void addWidgets(ActionMap actions) {
		// Dimensions
		
		Dimension dl = new Dimension(100, 36);
		Dimension d = new Dimension(500, 36);
		Dimension dm = new Dimension(Integer.MAX_VALUE, 36);

		// Rule data
		nodeLabel = new JLabel("<html><b><u>Decision node:</u></b></html>");
		nodeLabel.setToolTipText("Click here to edit the rule");
		nodeLabel.setPreferredSize(dl);
		nodeLabel.setMaximumSize(d);
		nodeLabel.setMinimumSize(dl);
		add(nodeLabel);

		nodeEdit = new JLabel("Click on a tree node to see details");
        nodeEdit.setToolTipText(nodeEdit.getText());
		nodeEdit.setPreferredSize(d);
		nodeEdit.setMaximumSize(dm);
		nodeLabel.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mousePressed(MouseEvent e) {
	   			if (modifyNode != null)
	   			modifyNode.actionPerformed(new ActionEvent(this,0,""));
	   		}
	    });	  		
		

		add(nodeEdit);

		
		if (actions != null)
		modifyNode = actions.get(EditTreeActions.ACTION_MODIFYNODE);

		// branch NO
		noBranchLabel = new JLabel("");
		noBranchLabel.setPreferredSize(dl);
		noBranchLabel.setMaximumSize(d);
		noBranchLabel.setToolTipText("Click here to change <No> branch assignment");
		add(noBranchLabel);

		noBranchEdit = new JLabel("");
		noBranchEdit.setPreferredSize(d);
		noBranchEdit.setMaximumSize(dm);
		noBranchLabel.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mousePressed(MouseEvent e) {
	   			if (modifyNoBranch != null)
	   				modifyNoBranch.actionPerformed(new ActionEvent(this,0,""));
	   		}
	    });	  		
						
		add(noBranchEdit);

		if (actions != null)
			modifyNoBranch = actions.get(EditTreeActions.ACTION_MODIFYNOBRANCH);
		// branch YES
		yesBranchLabel = new JLabel("");
		yesBranchLabel.setToolTipText("Click here to change <Yes> branch assignment");		
		yesBranchLabel.setPreferredSize(dl);
		yesBranchLabel.setMaximumSize(d);
		add(yesBranchLabel);

		yesBranchEdit = new JLabel("");
		yesBranchEdit.setPreferredSize(d);
		yesBranchEdit.setMaximumSize(dm);
		yesBranchLabel.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mousePressed(MouseEvent e) {
	   			if (modifyYesBranch != null)
	   				modifyYesBranch.actionPerformed(new ActionEvent(this,0,""));
	   		}
	    });	  		
		
		add(yesBranchEdit);

		if (actions != null)
			modifyYesBranch = actions.get(EditTreeActions.ACTION_MODIFYYESBRANCH);
		
		toxTree.ui.SpringUtilities.makeCompactGrid(this, 3, 2, // rows, cols
				2, 2, // initX, initY
				1, 1); // xPad, yPad

		setBackground(bgColor);
	}

	/**
	 * @return Returns the noBranch.
	 */
	public synchronized Observable getNoBranch() {
		return noBranch;
	}

	/**
	 * @param noBranch
	 *            The noBranch to set.
	 */
	public synchronized void setNoBranch(Observable noBranch) {
		this.noBranch = noBranch;
		update(noBranch, null);
	}

	/**
	 * @return Returns the yesBranch.
	 */
	public synchronized Observable getYesBranch() {
		return yesBranch;
	}

	/**
	 * @param yesBranch
	 *            The yesBranch to set.
	 */
	public synchronized void setYesBranch(Observable yesBranch) {
		this.yesBranch = yesBranch;
		update(yesBranch, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o == null)
			return;
		if (o instanceof IDecisionCategory) {
			if (o == noBranch) {
                
				noBranchLabel.setText("<html><b><u>If 'NO' assign </u></b></html>");
				noBranchEdit.setText("<html>" + noBranch.toString()
						+ "</html>");
                noBranchEdit.setToolTipText(noBranch.toString());
			} else if (o == yesBranch) {
				yesBranchLabel.setText("<html><b><u>If 'YES' assign </u></b></html>");
				yesBranchEdit.setText("<html>" + yesBranch.toString()
						+ "</html>");
                yesBranchEdit.setToolTipText(yesBranch.toString());
			}
		} else if (o instanceof IDecisionRule) {
			if (o == node) {
				nodeEdit.setText(node.toString());
                nodeEdit.setToolTipText(node.toString());
				if (o instanceof DecisionNode) {
					DecisionNode node = (DecisionNode) o;
					if (node.getBranch(false) != null)
						setNoBranch((Observable) node.getBranch(false));
					else
						setNoBranch((Observable) node.getCategory(false));

					if (node.getBranch(true) != null)
						setYesBranch((Observable) node.getBranch(true));
					else
						setYesBranch((Observable) node.getCategory(true));
				}
			} else if (o == noBranch) {
				noBranchLabel.setText("<html><b><u>If 'NO' go to </u></b></html>");
				noBranchEdit.setText("<html>Q." + ((IDecisionRule)noBranch).getID()
						+ "</html>");
                noBranchEdit.setToolTipText(noBranch.toString());
			} else if (o == yesBranch) {
				yesBranchLabel.setText("<html><b><u>If 'YES' go to </u> </b></html>");
				yesBranchEdit.setText("<html>Q." + ((IDecisionRule)yesBranch).getID()
						+ "</html>");
                yesBranchEdit.setToolTipText(yesBranch.toString());
			}
		}

	}

	/**
	 * @return Returns the node.
	 */
	public synchronized Observable getNode() {
		return node;
	}

	/**
	 * @param node
	 *            The node to set.
	 */
	public synchronized void setNode(Observable node) {
		if (this.node instanceof DecisionNode) {
			((Observable) this.node).deleteObserver(this);
		}
		this.node = node;
		setEditable(editable);
		if (node instanceof DecisionNode) {
			node.addObserver(this);
			DecisionNode dNode = (DecisionNode) node;
			Observable o = (Observable) dNode.getBranch(false);
			if (o == null)
				o = (Observable) dNode.getCategory(false);
			setNoBranch(o);

			o = (Observable) dNode.getBranch(true);
			if (o == null)
				o = (Observable) dNode.getCategory(true);
			setYesBranch(o);
		}
		update(node, node);
	}

	/**
	 * @return Returns the editable.
	 */
	public synchronized boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable
	 *            The editable to set.
	 */
	public synchronized void setEditable(boolean editable) {
		this.editable = editable;
        
		/*
		modifyNoBranch.setVisible(editable && (node != null));
		modifyYesBranch.setVisible(editable && (node != null));
		modifyNode.setVisible(editable && (node != null));
		*/
	}
	
}
