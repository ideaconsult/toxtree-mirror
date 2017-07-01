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

package toxtree.ui.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.Tools;
import toxTree.tree.AbstractTree;
import toxTree.tree.DecisionNode;
import toxtree.ui.tree.categories.CategoriesTableModel;
import toxtree.ui.tree.rules.DecisionNodesListTableModel;
import toxtree.ui.tree.rules.RulePanel;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

/**
 * The default {@link IDecisionMethodEditor} for {@link AbstractTree} descendants. 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class TreeEditorPanel extends JPanel implements IDecisionMethodEditor {

    protected static Logger logger = Logger.getLogger(TreeEditorPanel.class.getName());
    protected JTaskPane taskPane;
    protected ListPanel rulesPanel;
    protected ListPanel categoriesPanel;    
    protected JCustomTreeComponent treeComponent;
    protected DecisionNodePanel nodePanel;
    protected RulePanel rulePanel;
    protected IDecisionMethod method;
    protected EditTreeActions editTreeActions;
    protected static JFrame frame =null;
    
    /**
     * 
     */
    private static final long serialVersionUID = -8216394526738359939L;

    public TreeEditorPanel() {
        this(null);
    }
    public TreeEditorPanel(IDecisionMethod method) {
        super(new BorderLayout());
        setBorder(null);
        setMethod(method);
        addWidgets();
        setMinimumSize(new Dimension(400,300));
        setPreferredSize(new Dimension(800,600));
    }    
    protected void addWidgets() {
        editTreeActions = new EditTreeActions(method);
        editTreeActions.setParentComponent(editTreeActions.getTreeNodeActions(),this);
        editTreeActions.setParentComponent(editTreeActions.getRuleActions(),this);
        editTreeActions.setParentComponent(editTreeActions.getCategoryActions(),this);  
        //Actions on the left
        taskPane = new JTaskPane(); 

        addActions(taskPane,"Decision tree", editTreeActions.getTreeActions());
        addActions(taskPane,"Decision node", editTreeActions.getTreeNodeActions());        
        addActions(taskPane,"Decision rules", editTreeActions.getRuleActions());
        addActions(taskPane,"Categories", editTreeActions.getCategoryActions());
        JScrollPane scroll = new JScrollPane(taskPane);
        scroll.setBorder(null);
        add(scroll,BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new GridLayout(2,1));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Decision tree", createTreePanel());
        tabbedPane.addTab("Rules",createRulesPanel(method.getRules()));
        tabbedPane.addTab("Categories",createCategoriesPanel(method.getCategories()));

        centerPanel.add(tabbedPane);
        centerPanel.add(createNodePanel(editTreeActions.getNode()));
        add(centerPanel,BorderLayout.CENTER);
        
        JTaskPaneGroup aGroup = new JTaskPaneGroup();
        aGroup.setExpanded(true);
        aGroup.setTitle("Decision node");
        aGroup.setSpecial(true);
        aGroup.add(nodePanel);
        taskPane.add(aGroup);

        
        
    }
    protected ListPanel createCategoriesPanel(IDecisionCategories categories) {
        categoriesPanel = null;
/*        
        if (method.isEditable())
            categoriesPanel = new ListPanel("Categories", new ListTableModel(categories), editTreeActions.getCategoryActions());
        else
         */
        //categoriesPanel = new ListPanel("Categories",new ListTableModel(categories), null);
        categoriesPanel = new toxtree.ui.tree.ListPanel("Categories",new CategoriesTableModel(categories,2), null);

        categoriesPanel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Ignore extra messages.
                if (e.getValueIsAdjusting())
                    return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                if (!lsm.isSelectionEmpty())
                    try {
                        Object category = categoriesPanel.model.list.get(lsm
                                .getMinSelectionIndex());
                        if (category instanceof IDecisionCategory)
                            editTreeActions.setCategory((IDecisionCategory) category);

                    } catch (Exception x) {
                    	logger.log(Level.SEVERE,x.getMessage(),x);
                    }
            }
        });
        return categoriesPanel;
    }
    protected ListPanel createRulesPanel(IDecisionRuleList rules) {
    	/*
        if (method.isEditable())
            rulesPanel = new ListPanel("Rules", new DecisionNodesListTableModel(rules), editTreeActions.getRuleActions());
        else
        */ 
        rulesPanel = new ListPanel("Rules", new DecisionNodesListTableModel(rules), null);

        rulesPanel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Ignore extra messages.
                if (e.getValueIsAdjusting())
                    return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                if (!lsm.isSelectionEmpty())
                    try {
                        Object rule = rulesPanel.model.list.get(lsm
                                .getMinSelectionIndex());
                        if (rule != null) {
                            if (rule instanceof IDecisionRule)
                                setRule((IDecisionRule) rule);
                            if (rule instanceof DecisionNode) {
                                setNode((DecisionNode) rule);
                            }
                        }

                    } catch (Exception x) {
                    	logger.log(Level.SEVERE,x.getMessage(),x);
                    }
            }
        });
        return rulesPanel;
    }
    protected JComponent createTreePanel() {
        ActionMap actions = null;
        if (method.isEditable()) actions = editTreeActions.getTreeNodeActions();
        treeComponent = new JCustomTreeComponent(method,actions) ;
        JScrollPane treeView = new JScrollPane(treeComponent);
        treeView.setPreferredSize(new Dimension(500, 300));

        // Listen for when the selection changes.
        treeComponent.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                Object o = ((JCustomTreeComponent) e.getSource()).objectAt(e.getX(),e.getY());
                if (o != null) {
                    if (o instanceof DecisionNode) {
                        setNode((DecisionNode) o);
                        if (e.getButton() == MouseEvent.BUTTON3) 
                            showPopupMenu(e);                 
                    } else if (o instanceof IDecisionRule) {
                        setRule((IDecisionRule) o);
                        if (e.getButton() == MouseEvent.BUTTON3) 
                            showPopupMenu(e);                     
                    } else if (o instanceof IDecisionCategory) {
                        if (e.getButton() == MouseEvent.BUTTON3) 
                            showCategory((IDecisionCategory) o);
                    }

                    
                }   
            }
        });
        return treeView;
    }
    protected void showCategory(IDecisionCategory c) {
        c.getEditor().edit(this, c);
        treeComponent.setDecisionMethod(treeComponent.getDecisionMethod());
    }
    protected void setNode(DecisionNode node) {
        editTreeActions.setNode(node);
        nodePanel.setNode(node);
        setRule(node.getRule());
    }
    protected void setRule(IDecisionRule rule) {
        rulePanel.setRule(rule);
        editTreeActions.setRule(rule);      
    }

    protected void showPopupMenu(MouseEvent e) {
        treeComponent.showPopup(e);
        treeComponent.repaint();

    }
    protected JComponent createNodePanel(DecisionNode node) {
        nodePanel = new DecisionNodePanel(editTreeActions.getTreeNodeActions());
        nodePanel.setEditable(method.isEditable());
        nodePanel.setVisible(method.isEditable());
        nodePanel.setPreferredSize(new Dimension(200,100));

        rulePanel = new RulePanel(node);
        rulePanel.setEditable(method.isEditable());

        return rulePanel;
    }
    public void addActions(JTaskPane taskpane, String groupName,  ActionMap actions) {
        JTaskPaneGroup aGroup = new JTaskPaneGroup();
        aGroup.setExpanded(true);
        aGroup.setTitle(groupName);
        aGroup.setSpecial(true);
        
        Object[] keys = actions.keys();
        for (int j=0;j<keys.length;j++) 
             aGroup.add(actions.get(keys[j]));
           
        taskPane.add(aGroup);
    }
    protected boolean close(Window editor) {
        boolean isModified = method.isModified();
		return !isModified || (isModified &&
        	 (JOptionPane.showConfirmDialog(editor,"The decision tree is not saved. \nAre you sure to exit without saving the decision tree?","Please confirm",JOptionPane.YES_NO_OPTION)
        		==JOptionPane.YES_OPTION));
		
    }
    public IDecisionMethod edit(Component owner, IDecisionMethod method)
            throws DecisionMethodException {
        setMethod(method);
        if (frame !=null) {frame.getContentPane().removeAll(); frame.dispose(); frame = null; }
        frame = new JFrame();
		(frame).addWindowListener(new WindowAdapter() {
    		@Override
			public void windowClosing(WindowEvent arg0) {
    			//boolean isModified = ((EditTreeFrame)arg0.getWindow()).isModified();
    			boolean doClose =  close(frame);
    			if (doClose) {
    				frame.setVisible(false);
    				frame.dispose();
        			frame = null;
    	        }
    		}
    	});        
        frame.setIconImage(getIcon().getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setTitle(method.getTitle());
        frame.pack();
        frame.setVisible(true);
        return method;
        
/*
        if (JOptionPane.showConfirmDialog(owner,this,"",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null)
                == JOptionPane.OK_OPTION) 
            return method;
        else return null;
*/
    }
    protected ImageIcon getIcon() {
        try {
        return Tools.getImage(Tools._logo);
        } catch (Exception x) {
            return null;
        }
    }

    public IDecisionMethod getMethod() {
        return method;
    }

    public void setMethod(IDecisionMethod method) {
        this.method = method;

    }
    public Component getVisualCompoment() {
    	return this;
    }
    public boolean isEditable() {
        return method.isEditable();
    }
    public void setEditable(boolean editable) {
        method.setEditable(true);
        
    }
}
