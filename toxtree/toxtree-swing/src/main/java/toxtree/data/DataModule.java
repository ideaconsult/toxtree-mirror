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
package toxtree.data;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.core.IDecisionMethod;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.logging.TTLogger;
import ambit2.core.data.MoleculeTools;
import ambit2.jchempaint.editor.JChemPaintDialog;

public abstract class DataModule extends Observable implements Serializable, Observer {
	protected static TTLogger logger = new TTLogger(DataModule.class);
	//data
	protected DataContainer dataContainer = null;
	protected int useDatabase = 0; //-1 : not available; 0 - unknown, have to check; 1 : available
	//jcp
	protected JChemPaintDialog jcpDialog = null;
	protected IChemModel jcpModel = null;  //for JCP use
	//batch
	protected BatchProcessing batch = null;	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8605583116332611996L;

	public DataModule(File inputFile) {
		super();
		dataContainer = createDataContainer(inputFile);
		if (dataContainer != null) dataContainer.addObserver(this);
        //jcpModel = MoleculeTools.newChemModel(SilentChemObjectBuilder.getInstance());  
		jcpModel = MoleculeTools.newChemModel(DefaultChemObjectBuilder.getInstance());
        /*
        jcpModel.setMoleculeSet(jcpModel.getBuilder().newMoleculeSet());
        jcpModel.getMoleculeSet().addAtomContainer(
        		jcpModel.getBuilder().newMolecule());
        		*/        
        jcpModel.setID("JChemPaint");
        //jcpModel.setTitle("JChemPaint structure diagram editor");
        //jcpModel.setAuthor(JCPPropertyHandler.getInstance().getJCPProperties().getProperty("General.UserName"));
        //Package jcpPackage = Package.getPackage("org.openscience.cdk.applications.jchempaint");
        //String version = jcpPackage.getImplementationVersion();
        //jcpModel.setSoftware("JChemPaint " + version);
        //jcpModel.setGendate((Calendar.getInstance()).getTime().toString());

	}
	
	

    public void batch(BatchProcessing bp) {
    	this.batch = bp;
    	new SwingWorker<BatchProcessing, Object>() {
			 
 	       @Override
 	       public BatchProcessing doInBackground() {
            	try {
            		batch.start();
            	} catch (BatchProcessingException x) {
            		logger.error(x);
            		ToxTreeActions.showMsg("Error on batch processing",x.getMessage());
            	}               	
                return batch;
 	       }
 	       @Override
 	       protected void done() {
               setChanged();
               notifyObservers();
 	       }
 	   }.execute(); 	 

 	   /*
        final toxtree.ui.GUIWorker worker = new toxtree.ui.GUIWorker() {
            @Override
			public Object construct() {
            	try {
            		batch.start();
            	} catch (BatchProcessingException x) {
            		logger.error(x);
            		ToxTreeActions.showMsg("Error on batch processing",x.getMessage());
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
        */
    }
    public DataContainer getDataContainer() {
        return  dataContainer;
    }
    /*
    public void editMolecule(boolean editable, JFrame frame) {
        ILoggingTool lg =
            LoggingToolFactory.createLoggingTool(JChemPaintMenuHelper.class);
  
    	lg.debug("Creating menu: ", "sss");
    	JChemPaint.showEmptyInstance(true);
    }
    */

    public void editMolecule(boolean editable, JFrame frame) {
    	IMoleculeSet molecule4edit = null;
    	try {
    		molecule4edit = dataContainer.containers.getMoleculeForEdit();
    		
    	} catch (Exception x) {
    		x.printStackTrace();
    		return;
    	}
    	if (molecule4edit != null) { 
	        if (jcpDialog == null) {

	        	jcpModel.setMoleculeSet(molecule4edit );
                
	        	jcpDialog = new JChemPaintDialog(frame,false,jcpModel) {
					private static final long serialVersionUID = -492805673357520991L;

					@Override
					public IMolecule okAction() {
						
						result = JOptionPane.OK_OPTION;
						setVisible(false);
					        
						
						IChemObjectBuilder builder4toxtree = SilentChemObjectBuilder.getInstance();
					    IMolecule updatedMolecule = builder4toxtree.newInstance(IMolecule.class);
					        
					    IMoleculeSet m = jcpep.getChemModel().getMoleculeSet();  
					    //copy  
					    for (int i=0; i < m.getAtomContainerCount(); i++)  {
					          updatedMolecule.add(MoleculeTools.copyChangeBuilders(m.getMolecule(i),builder4toxtree));
					    }

                        getDataContainer().setEnabled(true);
                        SmilesGenerator g = new SmilesGenerator(true);
                        updatedMolecule.setProperty("SMILES",g.createSMILES(updatedMolecule));
                                                
                        getDataContainer().setMolecule(updatedMolecule);
                        getActions().allActionsEnable(true);

                        dispose();
	    	    		jcpDialog = null;
                        return updatedMolecule;
	        		};
                    
	        		@Override
					public void cancelAction() {
	        			super.cancelAction();
                        
                        getDataContainer().setEnabled(true);
                        getActions().allActionsEnable(true);
	        			//data.getDataContainer().setEnabled(true);
	        			//data.getActions().allActionsEnable(true);
	    	    		dispose();
	    	    		jcpDialog = null;
	        			
	        		}
                    
	        	};
	        	jcpDialog.setTitle("JChemPaint structure diagram editor");
                jcpDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent arg0) {
                        super.windowClosing(arg0);
                        getDataContainer().setEnabled(true);
                        getActions().allActionsEnable(true);
                        jcpDialog = null;
                    }
                });
	            //TODO center it 
	        	//TODO nonmodal
	        } else jcpModel.setMoleculeSet(molecule4edit);
            


            jcpDialog.cleanup();
	        jcpDialog.toFront();	        
			dataContainer.setEnabled(false);
			getActions().allActionsEnable(false);
	        jcpDialog.show();
	              
    	}
    }
   
    
    public abstract ActionList getActions();
    protected abstract DataContainer createDataContainer(File inputFile);
    public abstract void viewMethod(IDecisionMethod method,boolean editable) throws DecisionMethodException ;
    public String showMetabolites() throws Exception {return null; };

}
