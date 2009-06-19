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
package toxTree.data;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.core.IDecisionMethodsList;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.exceptions.FilterException;
import toxTree.tree.DecisionMethodsList;

public class DecisionMethodsDataModule extends DataModule {
    protected IDecisionMethodsList methods;
    protected IDecisionMethod rules;	
    protected IDecisionResult treeResult;	
    protected transient IDecisionMethodEditor editor;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7860257982749840157L;

	public DecisionMethodsDataModule() {
		super(null);
		editor = null;
	}

	public DecisionMethodsDataModule(JFrame frame, File inputFile, DecisionMethodsList methods) {
		super(inputFile);
        this.methods = methods;
        editor = null;
		
	}
	@Override
	public ActionList getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	protected DataContainer createDataContainer(File inputFile) {
    	DecisionMethodData dc = new DecisionMethodData(inputFile);
        dc.addObserver(this);
        return dc;
	}
	public IDecisionMethodsList getMethods() {
		return methods;
	}

	public void setMethods(IDecisionMethodsList methods) {
		this.methods = methods;
	}
    public IDecisionMethod getSelectedRules() {
        return rules;
    }
    public IDecisionMethod getRules() {
        return rules;
    }
    public void setRules(IDecisionMethod rules) {
        
        this.rules = rules; 
        IDecisionResult newResult = rules.createDecisionResult();
        newResult.setDecisionMethod(rules);
        
        if (treeResult != null) {
            PropertyChangeListener[] listeners = treeResult.getPropertyChangeListeners();
            if (newResult != null)
                for (int i=0; i < listeners.length;i++) 
                    newResult.addPropertyChangeListener(listeners[i]);
            for (int i=0; i < listeners.length;i++) 
                treeResult.removePropertyChangeListener(listeners[i]);                
        }
        treeResult = newResult;
        setChanged();
        notifyObservers();
    }
    public IDecisionResult getTreeResult() {
        return treeResult;
    }
    @Override
	public void viewMethod(final IDecisionMethod method, boolean editable) throws DecisionMethodException {
    	editor = method.getEditor();
    	if (editor instanceof Window) {
    		((Window)editor).addWindowListener(new WindowAdapter() {
        		@Override
				public void windowClosing(WindowEvent arg0) {
        			//boolean isModified = ((EditTreeFrame)arg0.getWindow()).isModified();
                    boolean isModified = method.isModified();
        			boolean doClose =  !isModified || (isModified &&
        	        	 (JOptionPane.showConfirmDialog(null,"The decision tree is not saved. \nAre you sure to exit without saving the decision tree?","Please confirm",JOptionPane.YES_NO_OPTION)
        	        		==JOptionPane.YES_OPTION));
        			if (doClose) {
        				((Window)editor).setVisible(false);
        				((Window)editor).dispose();
	        			editor = null;
        	        }
        		}
        	});
    	}
    	editor.edit(null,method);    	
    }
    
    public void viewMethod(int methodIndex, boolean editable) throws DecisionMethodException {
    	IDecisionMethod method = methods.getMethod(methodIndex);
    	if (method != null) viewMethod(method,editable);
    }    
    public void clearResults() {
        treeResult.clear();
        setChanged();
        notifyObservers();
    }
    public void clearFilters() throws FilterException {
    	dataContainer.clearFilters();
    }       
    public void selectFilter(Component parentComponent) throws FilterException {
    	dataContainer.filter(treeResult);
   		dataContainer.selectFilter(parentComponent);
    }        
    public int lookup(String field,Object value) {
    	try {
    		return ((DecisionMethodData) dataContainer).lookup(field, value);
        } catch (Exception x) {
        	logger.error(x);
            return -1;
        }
    }
    public int gotoRecord(String record) throws Exception {
   		return ((DecisionMethodData) dataContainer).gotoRecord(Integer.parseInt(record));
    }
    
    public void classifyAll() {
            	try {
            		((DecisionMethodData) dataContainer).classifyAll(treeResult);
                } catch (DecisionResultException x) {
                	logger.error(x);
                    treeResult.clear();
                }
                setChanged();
                notifyObservers();
 
    }
    
    public void classify() {
                try {
                	((DecisionMethodData) dataContainer).classify(treeResult);
                } catch (DecisionResultException x) {
                	logger.error(x);
                    treeResult.clear();
                    treeResult.setError(x.getMessage());
                }
                setChanged();
                notifyObservers();

    }
    

    public void addRulesPropertyChangeListener(PropertyChangeListener l) {
        rules.addPropertyChangeListener(l);
    }
    public void addResultPropertyChangeListener(PropertyChangeListener l) {
        treeResult.addPropertyChangeListener(l);
    }
    public void explain() {
        //tr.explain()
        setChanged();
        notifyObservers();
    }
    
}
