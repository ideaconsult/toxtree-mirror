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

package toxTree.ui.wizard;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionCategoryEditor;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.Introspection;
import toxTree.core.ToxTreePackageEntries;
import toxTree.core.ToxTreePackageEntry;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.DefaultCategory;
import toxTree.tree.cramer.RuleHeteroaromatic;
import toxTree.tree.rules.RuleAllSubstructures;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleAromatic;
import toxTree.tree.rules.RuleDescriptorRange;
import toxTree.tree.rules.RuleManyAromaticRings;
import toxTree.tree.rules.RuleMolecularMassRange;
import toxTree.tree.rules.RuleStructuresList;
import toxTree.tree.rules.RuleVerifyProperty;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import toxTree.ui.tree.ListPanel;
import toxTree.ui.tree.ListTableModel;
import toxTree.ui.tree.ToxTreePackageEntryModel;
import toxTree.ui.tree.actions.NewRuleAction;
import toxTree.ui.tree.rules.DecisionNodesListTableModel;

import com.nexes.wizard.WizardPanelDescriptor;

public class DecisionNodeWizard extends DecisionTreeWizard implements ListSelectionListener {
	protected static String options_allsubtructure = "All substructures";
	protected static String options_anysubtructure = "Any substructure";
	protected static String options_smarts = "SMARTS";
	protected static String options_structure = "Exact structures (from file)";
	protected static String options_heteroaromatic = "Heteroaromatic";
	protected static String options_aromatic = "Aromatic";
	protected static String options_aromaticrings = "Number of aromatic rings";
	protected static String options_descriptorrrange = "Descriptor";
	protected static String options_property = "Property";
	protected static String options_molweight = "Molecular weight";
    protected static String[] pages = {"options","categoryoptions","ruleoptions","panel","details","introspection"};
    public static int pageOptions = 0;
    public static int pageCategoryOptions = 1;
    public static int pageRuleOptions = 2;
    public static int pagePanel = 3;
    public static int pageDetails = 4;
    public static int pageIntrospection = 5;
    public static int pageAll = 5;
    protected ToxTreeWizardPanelDescriptor[] descriptors;
    protected EditorPanel editorPanel = null;
    protected EditorPanel nodePanel = null;
    protected Object selectedObject = null;
    protected EditorPanel introspectionPanel = null;
    protected ListPanel categoriesListPanel = null;
    protected ListPanel rulesListPanel = null;
    protected int startIndex = pageOptions;
    
    
    
    public synchronized Object getSelectedObject() {
        if (selectedObject instanceof DecisionNode)
            return selectedObject;
        else if (selectedObject instanceof IDecisionCategory)
            return selectedObject;
        else if (selectedObject instanceof IDecisionRule) {
            return createNewNode((IDecisionRule)selectedObject);
        } else return selectedObject;
    }

    public synchronized void setSelectedObject(Object selectedObject) {
        this.selectedObject = selectedObject;
    }

		
    public DecisionNodeWizard(IDecisionMethod tree,ListPanel[] panels) {
        super(tree);
        addWidgets(panels);
    }

    public DecisionNodeWizard(Dialog arg0, IDecisionMethod tree,ListPanel[] panels) {
        super(arg0, tree);
        addWidgets(panels);
    }

    public DecisionNodeWizard(Frame frame, IDecisionMethod tree,ListPanel[] panels) {
        super(frame, tree);
        addWidgets(panels);
    }

    public static DecisionNodeWizard createWizard(Frame frame, IDecisionMethod tree, int start) {
    	ListPanel[] panels = new ListPanel[2];
    	if (tree.getRules() instanceof DecisionNodesList)
    		panels[0] = new ListPanel("Rules",new DecisionNodesListTableModel(tree.getRules()),null);
    	else	
    		panels[0] = new ListPanel("Rules",new ListTableModel(tree.getRules()),null);
    	panels[1] = new ListPanel("Categories",new ListTableModel(tree.getCategories()),null);
    	DecisionNodeWizard wizard = new DecisionNodeWizard(frame,tree,panels);
    	wizard.setStartIndex(start);
    	return wizard;
    }
    public void setStartIndex(int start) {
    	ToxTreeWizardPanelDescriptor d = getDescriptor(start);
    	d.setBackId(null);
    	setCurrentPanel(getDescriptor(start).getId().toString());
    	startIndex = start;    	
    }
    protected ToxTreeWizardPanelDescriptor getDescriptor(int index) {
    	return descriptors[index];
    }
    protected void addWidgets(ListPanel[] panels) {
        descriptors = new ToxTreeWizardPanelDescriptor[pageAll+1];
        ArrayList options = new ArrayList();
        for (int i=0; i < panels.length;i++) {
            options.add(panels[i]);
            panels[i].addListSelectionListener(this);
        }
        int selected = 0;
        RadioBoxPanel optionsPanel = new RadioBoxPanel("Select",options,selected) {
            @Override
			public void selectObject(ActionEvent e, Object object) {
                if (object instanceof ListPanel) {
                    ListPanel lp = ((ListPanel) object);
                    editorPanel.setVisualComponent(lp);
                    switch (getSelectedIndex()) {
                    case 1: {//  category
                    	descriptors[pageOptions].setNextId(pages[pageCategoryOptions]); 
                    	descriptors[pageCategoryOptions].setBackId(pages[pageOptions]);
                    	break;
                    }  //rule
                    default: { 
                		descriptors[pageOptions].setNextId(pages[pageRuleOptions]); 
                		descriptors[pageRuleOptions].setBackId(pages[pageOptions]);
                		break;
                    }
                    
                    }
                }
            };
        };
        
        descriptors[pageOptions] = new ToxTreeWizardPanelDescriptor(
                pages[pageOptions],pages[pageRuleOptions],null,optionsPanel,null);

        /** category */
        ArrayList categoryOptions = new ArrayList();
        categoryOptions.add("Select from categories used in the tree");
        categoryOptions.add("Select from all available categories");        
        categoryOptions.add("Create new category");        
        RadioBoxPanel categoryOptionsPanel = new RadioBoxPanel("Select",categoryOptions,0) {
            @Override
			public void selectObject(ActionEvent e, Object object) {
            	if (object.equals("Create new category")) {
            		IDecisionCategory c = new DefaultCategory();
            		tree.getCategories().add(c);
                    c.setName(Integer.toString(c.getID()));
            		setSelectedObject(c);
            		nodePanel.setEditor(c.getEditor());
            		descriptors[pageCategoryOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageCategoryOptions]);
            	} else if (object.equals("Select from all available categories")){
            		if (categoriesListPanel == null) {
	           			ToxTreePackageEntries categoryTypes = Introspection.getAvailableCategoryTypes(this.getClass().getClassLoader());
	           			categoriesListPanel = new ListPanel("Categories",new ToxTreePackageEntryModel(categoryTypes),null);
	           			categoriesListPanel.addListSelectionListener(new ListSelectionListener() {
	           	            public void valueChanged(ListSelectionEvent e) {
	           	                if (e.getValueIsAdjusting()) return;
	           	                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

	           	                if (!lsm.isSelectionEmpty())
	           	                    try {
	           	                        Object o = categoriesListPanel.getValueAt(lsm.getMinSelectionIndex(), 2);
	           	                        processSelectedObject(o);
	           	                    } catch (Exception x) {
	           	                        x.printStackTrace();
	           	                    }
	           	            }
	           	        });		           			
            		}
           			introspectionPanel.setVisualComponent(categoriesListPanel);            		
           			descriptors[pageCategoryOptions].setNextId(pages[pageIntrospection]);
            		descriptors[pageIntrospection].setBackId(pages[pageCategoryOptions]);
            		descriptors[pageDetails].setBackId(pages[pageIntrospection]);         
            		
            	} else {	
            		descriptors[pageCategoryOptions].setNextId(pages[pagePanel]);
            		descriptors[pagePanel].setBackId(pages[pageCategoryOptions]);
            	}	
            };
        };        

        descriptors[pageCategoryOptions] = new ToxTreeWizardPanelDescriptor(
                pages[pageCategoryOptions],pages[pagePanel],null,categoryOptionsPanel,null);
        
        /** rule */
        ArrayList ruleOptions = new ArrayList();
        ruleOptions.add("Select from rules used in the tree");
        ruleOptions.add("Select from all available rules");
        ruleOptions.add(options_allsubtructure);
        ruleOptions.add(options_anysubtructure);
        ruleOptions.add(options_smarts);
        ruleOptions.add(options_structure);
        ruleOptions.add(options_aromatic);
        ruleOptions.add(options_aromaticrings);
        ruleOptions.add(options_heteroaromatic);
        //ruleOptions.add(options_descriptorrrange);
        ruleOptions.add(options_property);
        ruleOptions.add(options_molweight);
        
        RadioBoxPanel ruleOptionsPanel = new RadioBoxPanel("Select",ruleOptions,0) {
            @Override
			public void selectObject(ActionEvent e, Object object) {
            	if (object.equals(options_smarts)) {
            		RuleSMARTSubstructure c = new RuleSMARTSubstructure();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
            		//createNewNode(c);
            	} else 	if (object.equals(options_allsubtructure)) {
            		RuleAllSubstructures c = new RuleAllSubstructures();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
            	} else 	if (object.equals(options_anysubtructure)) {
            		RuleAnySubstructure c = new RuleAnySubstructure();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
                    //createNewNode(c);
            	} else 	if (object.equals(options_structure)) { //structures from file
            		RuleStructuresList c = new RuleStructuresList();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
            	} else 	if (object.equals(options_aromatic)) { //structures from file
            		RuleAromatic c = new RuleAromatic();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
            	} else 	if (object.equals(options_heteroaromatic)) { 
            		RuleHeteroaromatic c = new RuleHeteroaromatic();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);            		
            	} else 	if (object.equals(options_aromaticrings)) { 
            		RuleManyAromaticRings c = new RuleManyAromaticRings();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
            		            		
            	} else 	if (object.equals(options_descriptorrrange)) { //descriptor
            		RuleDescriptorRange c = new RuleDescriptorRange();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
                    //createNewNode(c);
            	} else 	if (object.equals(options_property)) { //property
            		RuleVerifyProperty c = new RuleVerifyProperty();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);
                    //createNewNode(c);
            	} else 	if (object.equals(options_molweight)) { //property
            		RuleMolecularMassRange c = new RuleMolecularMassRange();
                    setSelectedObject(c);
                    nodePanel.setEditor(c.getEditor());
            		descriptors[pageRuleOptions].setNextId(pages[pageDetails]);
            		descriptors[pageDetails].setBackId(pages[pageRuleOptions]);            		
            		
            	} else if (object.equals("Select from all available rules")) {
            		if (rulesListPanel == null) {
	           			ToxTreePackageEntries ruleTypes = Introspection.getAvailableRuleTypes(this.getClass().getClassLoader());
	           			rulesListPanel = new ListPanel("Rules",new ToxTreePackageEntryModel(ruleTypes),null);
	           			rulesListPanel.addListSelectionListener(new ListSelectionListener() {
	           	            public void valueChanged(ListSelectionEvent e) {
	           	                if (e.getValueIsAdjusting()) return;
	           	                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

	           	                if (!lsm.isSelectionEmpty())
	           	                    try {
	           	                        Object o = rulesListPanel.getValueAt(lsm.getMinSelectionIndex(), 2);
	           	                        processSelectedObject(o);
	           	                    } catch (Exception x) {
	           	                        x.printStackTrace();
	           	                    }
	           	            }
	           	        });	           			
            		}
           			introspectionPanel.setVisualComponent(rulesListPanel);
           			descriptors[pageRuleOptions].setNextId(pages[pageIntrospection]);
            		descriptors[pageIntrospection].setBackId(pages[pageRuleOptions]);
            		descriptors[pageDetails].setBackId(pages[pageIntrospection]);         
           			/*
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
            				*/

            	} else {
            		descriptors[pageRuleOptions].setNextId(pages[pagePanel]);
            		descriptors[pagePanel].setBackId(pages[pageRuleOptions]);
            	}	
            };
        };        
        
        descriptors[pageRuleOptions] = new ToxTreeWizardPanelDescriptor(
                pages[pageRuleOptions],pages[pagePanel],pages[pageOptions],ruleOptionsPanel,null) {
            @Override
			public void aboutToDisplayPanel() {
                super.aboutToDisplayPanel();
                setBackButtonEnabled(startIndex == pageOptions);
            }        	
        };
        

        editorPanel = new EditorPanel();
        editorPanel.setVisualComponent(panels[selected]);
        descriptors[pagePanel] = new ToxTreeWizardPanelDescriptor(
                pages[pagePanel],pages[pageDetails],pages[pageOptions],editorPanel,null) {
            @Override
            public void aboutToDisplayPanel() {
                super.aboutToDisplayPanel();
                setNextFinishButtonEnabled(false);
            }
        };
        
        
        nodePanel = new EditorPanel();
        descriptors[pageDetails] = new ToxTreeWizardPanelDescriptor(
                pages[pageDetails],WizardPanelDescriptor.FINISH,pages[pagePanel],nodePanel,null);
        
        ToxTreePackageEntries ruleTypes = Introspection.getAvailableRuleTypes(this.getClass().getClassLoader());
        introspectionPanel = new EditorPanel();

        descriptors[pageIntrospection] = new ToxTreeWizardPanelDescriptor(
                pages[pageIntrospection],pages[pageDetails],pages[pageOptions],introspectionPanel,null) {
            @Override
            public void aboutToDisplayPanel() {
                super.aboutToDisplayPanel();
                setNextFinishButtonEnabled(false);
            }        	
        };
        
        
        for (int i=0; i <= pageAll; i++)
            registerWizardPanel(descriptors[i].getId().toString(), descriptors[i]);
        
        setStartIndex(pageOptions);
        //setCurrentPanel(descriptors[pageOptions].getId().toString());
        //setCurrentPanel(descriptors[pageRuleOptions].getId().toString());

    }
    
    private DecisionNode createNewNode(IDecisionRule rule) {
		DecisionNode node = NewRuleAction.updateNode(tree,new DecisionNode(rule));
		node.setID(Integer.toString(tree.getRules().size()));
		node.setEditable(true);
		tree.getRules().add(node);
		setSelectedObject(node);
		nodePanel.setEditor(node.getEditor());
		return node;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!lsm.isSelectionEmpty())
            try {
                Component c = editorPanel.getVisualComponent();
                if (c instanceof ListPanel) {
                    Object o = ((ListPanel) c).getValueAt(lsm.getMinSelectionIndex(), 0);
                    processSelectedObject(o);
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
    }
    
    public void processSelectedObject(Object o) {
        if (o instanceof ToxTreePackageEntry) {
        	o = Introspection.loadCreateObject(((ToxTreePackageEntry) o).getClassName());
        	if (o instanceof DecisionNode) {
        		setNextFinishButtonEnabled(false);    
        		return;
        	}
    	}                    
        
        if (o instanceof DecisionNode) o = ((DecisionNode)o).getRule();
        setSelectedObject(o);
        
        if (o instanceof IDecisionRule) {
            ((IDecisionRule)o).setEditable(true);
            IDecisionRuleEditor editor = ((IDecisionRule)o).getEditor();
            editor.setRule((IDecisionRule)o);
            nodePanel.setEditor(editor);
            setNextFinishButtonEnabled(true);
        } else if (o instanceof IDecisionCategory) {
            setNextFinishButtonEnabled(true);
            IDecisionCategoryEditor editor = ((IDecisionCategory)o).getEditor();
            //editor.setEditable(true);
            editor.setCategory((IDecisionCategory)o);                        
            nodePanel.setEditor(editor);
        } else {
            setNextFinishButtonEnabled(false);    
        }
    }
}



