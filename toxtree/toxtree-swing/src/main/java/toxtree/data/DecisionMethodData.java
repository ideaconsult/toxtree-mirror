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
 * <b>Filename</b> DecisionMethodData.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-2
 * <b>Project</b> toxTree
 */
package toxtree.data;

import java.io.File;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethodsList;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.DecisionResultsList;
import toxTree.tree.stats.ConfusionMatrix;

/**
 * Contains {@link toxTree.data.MoleculesIterator} and {@link toxTree.logging.TTLogger}
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-2
 */
public class DecisionMethodData extends DataContainer {
	
    /**
     * 
     */
	public DecisionMethodData(File inputFile) {
		super(inputFile);
	}


	protected void setResult(IDecisionResult treeResult, IAtomContainer molecule) {
        
		if (!enabled) return;
        try {
            treeResult.assignResult(molecule);
            modified = true;
            notifyObservers();
        } catch (Exception x) {
            logger.error(x);
        }
        /*
		try {
	        getMolecule().setProperty(
	        		treeResult.getDecisionMethod().getClass().getName() + 
	        		"#" +
	        		treeResult.getDecisionMethod().toString()
	        		,
	        		treeResult.getCategory().toString());
	        getMolecule().setProperty(
	        		treeResult.getClass().getName() +
	        		"#" +
	        		treeResult.getDecisionMethod().toString(),
	        		treeResult.explain(false).toString());
	        modified = true;
	        notifyObservers();
		} catch (DecisionResultException x) {
			logger.error(x);
		}
        */
	}
	public void classify(IDecisionResult treeResult)  throws DecisionResultException {
		if (!enabled) return;
		IAtomContainer ac = null;
		try {
            treeResult.getDecisionMethod().setParameters((IAtomContainer) containers.getMolecule());            
		    ac = (IAtomContainer) containers.getMolecule().clone();
		} catch (Exception x) {
		    logger.error(x);
		    ac = containers.getMolecule();
		}
		logger.debug("Start classify");
        treeResult.classify(ac);
        logger.debug("End classify");
        logger.debug(treeResult);
        setResult(treeResult,getMolecule());
        logger.debug("Result set");
        ac = null;		
	}


	public ConfusionMatrix classifyAll(IDecisionResult treeResult) throws DecisionResultException {
		
		if (!enabled) return null;
		IAtomContainer  ac = null;
		double n = containers.getMoleculesCount();
        treeResult.setNotify(false);
        ConfusionMatrix<Comparable,IDecisionCategory> cmatrix = new ConfusionMatrix<Comparable, IDecisionCategory>();
        cmatrix.setExpectedTitle("All");
        cmatrix.setPredictedTitle(treeResult.getDecisionMethod().getTitle());
		for (int record =0; record < n; record++) {

			try {
                IAtomContainer c = (IAtomContainer) containers.getAtomContainer(record);
                treeResult.getDecisionMethod().setParameters(c);
                //treeResult.setPercentEstimated((int)(100*record * n ));
			    ac = (IAtomContainer) c.clone();
				treeResult.classify(ac);
				ac = null;
				setResult(treeResult,containers.getAtomContainer(record));
				
				IDecisionCategories categories = treeResult.getAssignedCategories();
				for (IDecisionCategory category : categories) {
					cmatrix.addEntry("All", category);
				}
			} catch (Exception x) {
				logger.error("Error when processing record\t",Integer.toString(record),x);
				x.printStackTrace();
			 
			}
			ac = null;
		}
        treeResult.setNotify(true);
		first();
		modified = true;
		return cmatrix;
	}
	
	public ConfusionMatrix classifyAll(IDecisionMethodsList methods) throws DecisionResultException {
		ConfusionMatrix matrix = null;
		IAtomContainer  ac = null;
		setEnabled(false);
    	containers.setReading();
    	setChanged();
    	notifyObservers();
    	
    	for (int record =0; record < containers.getMoleculesCount(); record++) {
			
			
			try {
                for (int i=0;i < methods.size();i++) {
                    IDecisionResult treeResult =  ((DecisionResultsList)methods).getResult(i);
                    treeResult.getDecisionMethod().setParameters((IAtomContainer) containers.getAtomContainer(record));
                    treeResult.setNotify(false);
                }    
			    ac =  (IAtomContainer) containers.getAtomContainer(record).clone();
			    for (int i=0;i < methods.size();i++) {
	            	IDecisionResult treeResult =  ((DecisionResultsList)methods).getResult(i);
	            	try {
	            		
	            		//IDecisionMethod rules = treeResult.getDecisionMethod();
	            		
		            	treeResult.classify(ac);	
		            	
	    				setResult(treeResult,containers.getAtomContainer(record));
	            	
	            	} catch (DecisionResultException x) {
	            		logger.error(x);
	            		x.printStackTrace();
	            		treeResult.clear();
	            		containers.setDone(false);
	            	}
	            	
			    }
			    ac = null;
			} catch (Exception x) {
				logger.error("Error when processing record\t",Integer.toString(record),x);
				x.printStackTrace();
			 
			}
			ac = null;
		}

        for (int i=0;i < methods.size();i++) {
            IDecisionResult treeResult =  ((DecisionResultsList)methods).getResult(i);
            treeResult.setNotify(true);
        }    
        
		setEnabled(true);
		first();
		modified = true;
    	containers.setDone(true);
    	setChanged();
    	notifyObservers();
    	return matrix;
		
	}
	
	
	public void filterAll(IDecisionResult treeResult) throws DecisionResultException {
		if (!enabled) return;
		IAtomContainer  ac = null;
		
		
		for (int record =0; record < containers.getMoleculesCount(); record++) {

			try {
			    ac = (IAtomContainer) containers.getAtomContainer(record).clone();
				treeResult.classify(ac);
				ac = null;
				setResult(treeResult,containers.getAtomContainer(record));
			} catch (Exception x) {
				logger.error("Error when processing record\t",Integer.toString(record),x);
				x.printStackTrace();
			 
			}
			ac = null;
		}
		first();
		modified = true;
	}
}



