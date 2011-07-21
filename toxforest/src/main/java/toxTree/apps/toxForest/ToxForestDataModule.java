/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.apps.toxForest;

import java.awt.Component;
import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.exceptions.DecisionResultException;
import toxTree.exceptions.FilterException;
import toxTree.tree.BatchDecisionResultsList;
import toxTree.tree.DecisionMethodsList;
import toxTree.tree.DecisionResultsList;
import toxTree.tree.stats.ConfusionMatrix;
import toxtree.data.ActionList;
import toxtree.data.DataContainer;
import toxtree.data.DecisionMethodData;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.data.ToxTreeActions;
import toxtree.ui.actions.AboutAction;
import toxtree.ui.actions.BatchAction;
import toxtree.ui.actions.ClearResultAction;
import toxtree.ui.actions.EditCompoundAction;
import toxtree.ui.actions.EstimateAction;
import toxtree.ui.actions.EstimateAllAction;
import toxtree.ui.actions.NewMoleculeAction;
import toxtree.ui.actions.OpenFileAction;
import toxtree.ui.actions.QuitAction;
import toxtree.ui.actions.SaveFileAction;
import toxtree.ui.molecule.ClearFilteredSubsetsAction;
import toxtree.ui.molecule.GoToRecordAction;
import toxtree.ui.molecule.LookupCompoundAction;
import toxtree.ui.molecule.ShowFilteredFileAction;
import toxtree.ui.tree.actions.ClearMethodsList;
import toxtree.ui.tree.actions.LiadMethodsListAction;
import toxtree.ui.tree.actions.LoadTreeAction;
import toxtree.ui.tree.actions.NewTreeAction;
import toxtree.ui.tree.actions.SaveMethodsListAction;
import toxtree.ui.tree.actions.SelectNewTreeAction;

/**
 * Main data class of {@link ToxForestApp}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 16, 2006
 */
public class ToxForestDataModule extends DecisionMethodsDataModule {
	ToxForestActions actions = null;
    protected EstimateAction estimateAction; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6517247854283253903L;

	public ToxForestDataModule() {
		this(null,null,null);
		
	}
	
	public ToxForestDataModule(JFrame frame, File inputFile, DecisionMethodsList methods) {
		super(frame,inputFile,methods);
		actions = new ToxForestActions(frame);
		actions.addAction(new NewMoleculeAction(getDataContainer()),ToxTreeActions._FileAction,"F");
		actions.addAction(new OpenFileAction(this),ToxTreeActions._FileAction,"F");
		actions.addAction(new SaveFileAction(this),ToxTreeActions._FileAction,"F");
		actions.addAction(new BatchAction(this),ToxTreeActions._FileAction,"F");
		actions.addAction(new QuitAction(this),ToxTreeActions._FileAction,"F");

        actions.addAction(new EditCompoundAction(this),ToxTreeActions._CompoundAction,"E");
        actions.addAction(new GoToRecordAction(this),ToxTreeActions._CompoundAction,"G");
        actions.addAction(new LookupCompoundAction(this),ToxTreeActions._CompoundAction,"E");
        actions.addAction(new ShowFilteredFileAction(this),ToxTreeActions._CompoundAction,"E");
        actions.addAction(new ClearFilteredSubsetsAction(this),ToxTreeActions._CompoundAction,"E");
        
		addHazardActions(ToxTreeActions._HazardAction,"R");
        addTreeActions("Decision tree","T");
        
        
        actions.addAction(new SaveMethodsListAction(this),"Decision Forest","S");
        actions.addAction(new LiadMethodsListAction(this),"Decision Forest","H");
        actions.addAction(new ClearMethodsList(this),"Decision Forest","C");
        
        AboutAction about = new toxtree.ui.actions.AboutAction(this);
        about.setPackageName("toxTree.apps.toxForest");
        actions.addAction(about,"Help","H");
		
	}

    protected void addTreeActions(String title,String mnemonic) {
    	
    	actions.addAction(new NewTreeAction(this,"New empty tree"),title,mnemonic);
        actions.addAction(new SelectNewTreeAction(this,"New tree from list"),title,mnemonic);
        /*
        actions.addAction(new SelectTreeAction(this) {
            @Override
            public void addTree(IDecisionMethod tree) {
                ((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(tree);
            }
        },title,mnemonic);
        */
        actions.addAction(new LoadTreeAction(this),title,mnemonic);
    }
    protected void addHazardActions(String title,String mnemonic) {
        estimateAction = new EstimateAction(this);
    	actions.addAction(estimateAction,title,mnemonic);
    	//actions.addAction(new ExplainAction(this),title,mnemonic);
    	actions.addAction(new EstimateAllAction(this),title,mnemonic);
    	actions.addAction(new ClearResultAction(this),title,mnemonic);
    }
	@Override
	public ActionList getActions() {
		return actions;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
    @Override
	public void setRules(IDecisionMethod rules) {
        for (int i=0;i< methods.size();i++) 
        	if (methods.getMethod(i) == rules) {
        		this.rules = methods.getMethod(i);
        		this.treeResult = ((DecisionResultsList) methods).getResult(i);
                setChanged();
                notifyObservers();
                return;
        	}
        logger.error("Not found\t",rules.toString());	

    }

	@Override
	protected DataContainer createDataContainer(File inputFile) {
    	DecisionMethodData dc = new ToxForestData(inputFile);
        dc.addObserver(this);
        return dc;		
	}
	
    @Override
	public ConfusionMatrix classifyAll() {
    	ConfusionMatrix matrix = null;
    	if (methods instanceof BatchDecisionResultsList)
    		((BatchDecisionResultsList)methods).setEstimating(); 
    		
    		((DecisionMethodData) dataContainer).setEnabled(false);
            	try {
	        		matrix = ((DecisionMethodData) dataContainer).classifyAll(methods);
	                //JOptionPane.showMessageDialog(null, "Done");

            	} catch (DecisionResultException x) {
                    JOptionPane.showMessageDialog(null, x.getMessage());

            	}
                ((DecisionMethodData) dataContainer).setEnabled(true);
          if (methods instanceof BatchDecisionResultsList)
            		((BatchDecisionResultsList)methods).setEstimated();            
                setChanged();
                notifyObservers();
        return matrix;      
    }
    
    @Override
	public void classify() {
                
                for (int i=0;i < methods.size();i++) {
                	treeResult =  ((DecisionResultsList)methods).getResult(i);
                	rules = treeResult.getDecisionMethod();
                	try {
                		((DecisionMethodData) dataContainer).classify(treeResult);
                	
                	} catch (DecisionResultException x) {
                		logger.error(x);
                		treeResult.clear();
                	}
                }	
                setChanged();
                notifyObservers();
    }
    
    @Override
	public void clearResults() {
        for (int i=0;i < methods.size();i++) {
        	treeResult =  ((DecisionResultsList)methods).getResult(i);
        	treeResult.clear();
        }	
        setChanged();
        notifyObservers();
    }

    public synchronized EstimateAction getEstimateAction() {
        return estimateAction;
    }
    @Override
    public IDecisionMethod getRules() {
        if (methods instanceof IDecisionMethod) return (IDecisionMethod) methods;
        else return super.getRules();
    }
    
    public void selectFilter(Component parentComponent) throws FilterException {
    	 //dataContainer.clearFilters();
    	 for (int i=0;i < methods.size();i++) 
         	dataContainer.filter(((DecisionResultsList)methods).getResult(i));
   		dataContainer.selectFilter(parentComponent);
    }
    
}
