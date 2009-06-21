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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import toxTree.core.IDecisionMethod;
import toxTree.io.Tools;

/**
 * modifies tree explanation
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class EditTreeExplanation extends AbstractTreeAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4136365017401813379L;

	/**
	 * @param tree
	 */
	public EditTreeExplanation(IDecisionMethod tree) {
		this(tree,"Modify tree title");

	}

	/**
	 * @param tree
	 * @param name
	 */
	public EditTreeExplanation(IDecisionMethod tree, String name) {
		this(tree, name,Tools.getImage("plugin.png"));
	}

	/**
	 * @param tree
	 * @param name
	 * @param icon
	 */
	public EditTreeExplanation(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Modify decision tree title");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		 JOptionPane.showMessageDialog(getParentFrame(),
                 new TreeInfoEditor(tree),"Decision Tree information",JOptionPane.PLAIN_MESSAGE);


	}

}


class TreeInfoEditor extends JPanel implements FocusListener {
    protected JTextField nameField ;
    protected JEditorPane explanation;
    protected  IDecisionMethod tree;
    public synchronized IDecisionMethod getTree() {
        return tree;
    }
    public synchronized void setTree(IDecisionMethod tree) {
        this.tree = tree;
    }
    public TreeInfoEditor(IDecisionMethod tree) {
        super(new BorderLayout());
        setTree(tree);
        nameField = new JTextField();
        nameField.addFocusListener(this);
        nameField.setEditable(true);
        nameField.setBorder(BorderFactory.createTitledBorder("Decision tree title"));
        nameField.setText(tree.getTitle());
        add(nameField,BorderLayout.NORTH);
        
        explanation = new JEditorPane("text/html","");
        explanation.setText(tree.getExplanation());
        explanation.addFocusListener(this);
        explanation.setEditable(true);
        explanation.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(explanation,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        explanation.setPreferredSize(new Dimension(200,200));
        scrollPane.setBorder(BorderFactory.createTitledBorder("More info"));        
        add(scrollPane,BorderLayout.CENTER);
    }
    public void focusLost(FocusEvent e) {
        if (tree==null) return;
        if (e.getSource() ==nameField) {
            tree.setTitle(nameField.getText());
        } else if (e.getSource() ==explanation) 
            tree.setExplanation(explanation.getText());

    }
    public void focusGained(FocusEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}