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

import java.io.File;
import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionMethodsList;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.stats.ConfusionMatrix;
import toxtree.data.DecisionMethodData;

/**
 * Main data container of  {@link ToxForestApp}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 16, 2006
 */
public class ToxForestData extends DecisionMethodData {

	public ToxForestData(File inputFile) {
		super(inputFile);

	}
	@Override
	public String getStatus() {
		return "";
	}
	@Override
	protected void setResult(IDecisionResult treeResult, IAtomContainer molecule) {
		//if (!enabled) return;
		try {
			
            treeResult.assignResult(molecule);
            /*
	        molecule.setProperty(
	        		treeResult.getDecisionMethod().getClass().getName() + 
	        		"#" +
	        		treeResult.getDecisionMethod().toString()
	        		,
	        		treeResult.getCategory().toString());
	        molecule.setProperty(
	        		treeResult.getClass().getName() +
	        		"#" +
	        		treeResult.getDecisionMethod().toString(),
	        		treeResult.explain(false).toString());
	        */
	        if (molecule.getAtomCount() == 0) {
	        	String smiles = treeResult.explain(false).toString();
		        IAtomContainer a = FunctionalGroups.createAtomContainer(smiles);
		        
		        if ((a != null) && (a.getAtomCount() > 0)) {
		        	molecule.add(a);
		        	molecule.setProperty("SMILES",smiles);
		        }
		        
	        }
	        modified = true;

		} catch (DecisionResultException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
	@Override
	public void classify(IDecisionResult treeResult) throws DecisionResultException {
		super.classify(treeResult);
        setChanged();
        notifyObservers();
	}
	@Override
	public ConfusionMatrix classifyAll(IDecisionMethodsList methods) throws DecisionResultException {
		ConfusionMatrix matrix =super.classifyAll(methods);
        setChanged();
        notifyObservers();		
        return matrix;
	}
	
	
	
}
