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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.Introspection;
import toxTree.core.ToxTreePackageEntries;
import toxTree.core.ToxTreePackageEntry;
import toxTree.tree.DecisionNode;

/**
 * A singleton class, providing several methods to select an object from a {@link java.util.List}.
 * <br>
 * Launches a modal dialogs with optional buttons on the right. Buttons are created from an optional 
 * {@link javax.swing.ActionMap}
 * <br> Uses {@link javax.swing.JOptionPane}
 * @author Nina Jeliazkova
 *
 */
public class SelectListDialog {

	/**
	 * Singleton class
	 *
	 */
	protected SelectListDialog() {
		super();
	}

	public static Object selectFromList(Component parent, String dlgCaption, String panelCaption,
			ListTableModel list,
			ActionMap actions) {
		ListPanel panel = new ListPanel(panelCaption, list, actions);
		panel.addListSelectionListener(panel);
        DetailsTextPane text = new DetailsTextPane(list.getList());
        text.setEditable(false);
        panel.addListSelectionListener(text);
        text.setPreferredSize(new Dimension(300,150));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel,new JScrollPane(text)); 
            
		JOptionPane pane = new JOptionPane(split, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);


		/*
		 * for( int i = 0; i < options.length; i++ )
		 * options[i].addActionListener( new OptionPaneActionListener( pane ) );
		 */
		JDialog dialog = pane.createDialog(parent, dlgCaption);
		dialog.setVisible(true);
		if (pane.getValue() == null) return null;
		int value = ((Integer) pane.getValue()).intValue();
		if (value == 0)
			return panel.getSelectedObject();
		else
			return null;
	}

	/**
	 * Launches a dialog with a several lists arranged in a {@link JTabbedPane}.
	 *  
	 * @param caption dialog caption
	 * @param panels  an array of {@link ListPanel} to be arranged as tab pages 
	 * @return Object the selected object
	 */
	public static Object selectFromList(String caption, ListPanel[] panels) {
		JTabbedPane tabbedPane = new JTabbedPane();

		for (int i = 0; i < panels.length; i++) {
			panels[i].addListSelectionListener(panels[i]);
			tabbedPane.addTab(panels[i].caption, null, panels[i],
					panels[i].caption);
		}
		JOptionPane pane = new JOptionPane(tabbedPane,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);

		JDialog dialog = pane.createDialog(null, caption);
		dialog.setVisible(true);
		
		Object o = pane.getValue();
		if (o == null) return null;
		int value = ((Integer) pane.getValue()).intValue();

		if (value == 0) {
			ListPanel panel = panels[tabbedPane.getSelectedIndex()];
			return panel.getSelectedObject();
		} else
			return null;
	}
	
	/**
	 * Provides a list of names of the classes implementing {@link IDecisionRule}
	 * If the user selects an object, the rule is created and returned <br>
	 * Uses {@link Introspection#getAvailableRuleTypes(ClassLoader)}
	 * @param classLoader
	 * @return the rule selected
	 */

	public static IDecisionRule selectNewRule(Component parent,ClassLoader classLoader) {
		ToxTreePackageEntries ruleTypes = Introspection.getAvailableRuleTypes(classLoader);
		
		Object name = selectFromList(parent,"Select a rule","Available rules:", 
				new ToxTreePackageEntryModel(ruleTypes), null);
		if ((name != null) && (name instanceof ToxTreePackageEntry)) {
			Object o = Introspection.loadCreateObject(((ToxTreePackageEntry) name).getClassName());
			if (o instanceof IDecisionRule)
				return (IDecisionRule) o;
			else {
				o = null;
				return null;
			}
		} else
			return null;
	}
	/**
	 * Provides a list of names of the classes implementing {@link IDecisionRule}
	 * If the user selects an object, a {@link DecisionNode} containing the rule is created and returned <br>
	 * Uses {@link Introspection#getAvailableRuleTypes(ClassLoader)}
	 * @param classLoader
	 * @return the decision node
	 */
	public static DecisionNode selectNewNode(Component parent,ClassLoader classLoader) {
		ToxTreePackageEntries ruleTypes = Introspection.getAvailableRuleTypes(classLoader);
		Object name = selectFromList(parent,"Select a rule","Available rules:", new ToxTreePackageEntryModel(ruleTypes), null);
		if ((name != null) && (name instanceof ToxTreePackageEntry)) {
			Object o = Introspection.loadCreateObject(((ToxTreePackageEntry) name).getClassName());
			if (o instanceof IDecisionRule)
				return new DecisionNode((IDecisionRule) o);
			else {
				o = null;
				return null;
			}
		} else
			return null;
	}

	/**
	 * Provides a list of names of the classes implementing {@link IDecisionMethod}
	 * If the user selects a name, the tree is created and returned <br>
	 * Uses {@link Introspection#getAvailableTreeTypes(ClassLoader)}
	 * @param classLoader
	 * @return tree instance {@link IDecisionMethod}
	 */
	public static IDecisionMethod selectNewTree(Component parent,ClassLoader classLoader) {
		ArrayList ruleTypes = Introspection.getAvailableTreeTypes(classLoader);
		Object name = selectFromList(parent,"Select a tree","Available decision trees:", new ListTableModel(ruleTypes),
				null);
		if ((name != null) && (name instanceof ToxTreePackageEntry)) {
			Object o = Introspection.loadCreateObject(((ToxTreePackageEntry) name).getClassName());
			if (o instanceof IDecisionMethod)
				return (IDecisionMethod) o;
			else {
				o = null;
				return null;
			}
		} else
			return null;
	}

	/**
 	 * Provides a list of names of the classes implementing {@link IDecisionCategory}
	 * If the user selects an object, the tree is created and returned <br>
	 * Uses {@link Introspection#getAvailableCategoryTypes(ClassLoader)}
	 * @param classLoader
	 * @return a decision category {@link IDecisionCategory}
	 */
	public static IDecisionCategory selectNewCategory(Component parent,ClassLoader classLoader) {
		ArrayList ruleTypes = Introspection.getAvailableCategoryTypes(classLoader);
		Object name = selectFromList(parent,"Select category","Available category:", new ListTableModel(ruleTypes), null);
		if ((name != null) && (name instanceof ToxTreePackageEntry)) {
			Object o = Introspection.loadCreateObject(((ToxTreePackageEntry) name).getClassName());
			if (o instanceof IDecisionCategory)
				return (IDecisionCategory) o;
			else {
				o = null;
				return null;
			}
		} else
			return null;
	}
	
}

class SelectAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8468861279706657194L;

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		super.putValue(arg0, arg1);
	}
}

class CancelAction extends AbstractAction {
	private static final long serialVersionUID = 7787228466222735812L;

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		super.putValue(arg0, arg1);
	}

	
}

class OptionPaneActionListener extends Object implements ActionListener {

	/**
	 * The JOptionPane which the action source we are listening on lives.
	 */
	JOptionPane optionPane;

	public OptionPaneActionListener(JOptionPane optionPane) {
		this.optionPane = optionPane;
	} // OptionPaneActionListener

	public void actionPerformed(ActionEvent event) {
		optionPane.setValue(event.getSource());
	} // actionPerformed

} // OptionPaneButton

class DetailsTextPane extends JTextPane implements ListSelectionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 8340434694455983277L;
    protected List list;
    public DetailsTextPane(List list) {
        super();
        setEditorKit(new HTMLEditorKit());
        this.list = list;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!lsm.isSelectionEmpty())
            try {
                //System.out.println(lsm.getMinSelectionIndex());
                if (list == null) setText("");
                else {
                    Object o = list.get(lsm.getMinSelectionIndex());
                    if (o == null) setText("");
                    else if (o instanceof IDecisionMethod) {
                        setText(((IDecisionMethod)o).getExplanation());
                    } else if (o instanceof IDecisionCategory) {
                        setText(((IDecisionCategory)o).getExplanation());    
                    } else if (o instanceof IDecisionRule) {
                        setText(((IDecisionRule)o).getExplanation());
                    }                                
                    else setText(o.toString()); 
                }
            } catch (Exception x) {
                setText("");
                //x.printStackTrace();
            }

        
    }
}