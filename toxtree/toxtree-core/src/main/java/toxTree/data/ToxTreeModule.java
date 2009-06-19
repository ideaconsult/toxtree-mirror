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
/**
 * <b>Filename</b> toxTreeData.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-1
 * <b>Project</b> toxTree
 */
package toxTree.data;

import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

import toxTree.core.IDecisionMethod;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ToxTreeBatchProcessing;
import toxTree.tree.DecisionMethodsList;
import toxTree.ui.tree.TreeFrame;

/**
 * Contains data essential for {@link toxTree.apps.ToxTreeApp} application
 * <ul>
 * <li>the current method {@link toxTree.core.IDecisionMethod}
 * <li>the result {@link toxTree.core.IDecisionResult}
 * <li>the list of available methods {@link toxTree.core.IDecisionMethodsList}
 * <li>the data {@link toxTree.data.DecisionMethodData}
 * <li>the tree data {@link toxTree.data.DecisionMethodData}
 * <li>the batch processing object {@link toxTree.io.batch.ToxTreeBatchProcessing}
 * </ul>
 * Also contains some UI frames :
 * <ul>
 * <li> a Frame to view a decision tree {@link toxTree.ui.tree.TreeFrame}
 * <li> a Frame to edit a decision tree {@link toxTree.ui.tree.EditTreeFrame}
 * <li> a dialog with structure diagram editor {@link toxTree.ui.molecule.JChemPaintDialog}
 * <li> and a {@link org.openscience.cdk.applications.jchempaint.JChemPaintModel}
 * </ul>  
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-1
 */
public class ToxTreeModule extends DecisionMethodsDataModule {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8292481405980136292L;
    

	private TreeFrame treeFrame = null;

//	protected IDecisionRule currentRule = null;
	//protected EditTreeFrame editMethodFrame = null;

	ToxTreeActions actions;
	
    /**
     * 
     */
    public ToxTreeModule(JFrame frame, File inputFile, DecisionMethodsList methods) {
        super(frame,inputFile,methods);
        if (methods.size()>0)
        	setRules(methods.getMethod(0));
        
        actions = new ToxTreeActions(frame,this);

    }
    @Override
	protected DataContainer createDataContainer(File inputFile) {
    	DecisionMethodData dc = new DecisionMethodData(inputFile);
        dc.addObserver(this);
        return dc;
    }
    /*
    public boolean databaseAvailable() {
        if (useDatabase == 0) //have to check
	        try { 
	            Class.forName("toxTree.database.QueryDB").newInstance(); 
	            useDatabase = 1;            
	        } catch (Exception ex) { 
	            // handle the error 
	            useDatabase = -1;
	        }
	    return (useDatabase==1);    
    }
    */
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
	public void update(Observable o, Object arg) {
        if (treeResult.isEstimating()) return; //can't change compound during estimation
        if (o instanceof DecisionMethodData)
            treeResult.clear();

    }


    @Override
	public void batch(BatchProcessing bp) {
    	this.batch = bp;
    	if (bp instanceof ToxTreeBatchProcessing)
    	((ToxTreeBatchProcessing) this.batch).setDecisionMethod(rules);
        final toxTree.ui.GUIWorker worker = new toxTree.ui.GUIWorker() {
            @Override
			public Object construct() {
            	try {
            		batch.start();
            	} catch (BatchProcessingException x) {
            		logger.error(x);
            		ToxTreeActions.showMsg("Error on batch processing",x.getMessage());
            	} catch (Throwable x) {
            		logger.error(x);
            	}
                return batch;
            }
            //Runs on the event-dispatching thread.
            @Override
			public void finished() {
                setChanged();
                notifyObservers();
            }
        };
        worker.start(); 
    }	

    
    @Override
	public ActionList getActions() {
        return actions;
    }

    
    public void viewMethod(boolean editable) {
        if (treeFrame == null) 
            treeFrame = new TreeFrame(rules);
        else 
            treeFrame.setDecisionMethod(rules);

        treeFrame.setVisible(true);
    }

    

    @Override
	public void setRules(IDecisionMethod rules) {

    	super.setRules(rules);

        if (treeFrame != null) { 
            treeFrame.dispose();
            treeFrame = null;
        }    
    }

    /*
    public void launchEditFrame(IDecisionMethod tree) {
        if (editMethodFrame == null)  {
        	
        	editMethodFrame = new EditTreeFrame(tree);
        	editMethodFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        	editMethodFrame.setVisible(true);
        	editMethodFrame.addWindowListener(new WindowAdapter() {
        		public void windowClosing(WindowEvent arg0) {
        			boolean doClose =  (!editMethodFrame.isModified()) || (
        				editMethodFrame.isModified() &&
        	        	 (JOptionPane.showConfirmDialog(null,"The decision tree is not saved. \nAre you sure to exit without saving the decision tree?","Please confirm",JOptionPane.YES_NO_OPTION)
        	        		==JOptionPane.YES_OPTION));
        			if (doClose) {
        			editMethodFrame.setVisible(false);
        			editMethodFrame.dispose();
        			editMethodFrame = null;
        	        }
        		}
        	});
        }

    }
      */  

}


