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
package toxTree.ui.actions;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import toxTree.data.DataModule;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.io.batch.BatchFactory;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.IBatchProcessing;
import toxTree.io.batch.ToxTreeBatchProcessing;
import toxTree.ui.batch.BatchProcessingDialog;
import toxTree.ui.tree.images.ImageTools;

/**
 * Starts a batch {@link toxTree.io.batch.IBatchProcessing}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class BatchAction extends DataModuleAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -552838223008338517L;

	/**
	 * @param module
	 */
	public BatchAction(DataModule module) {
		this(module,"Batch processing");
		
	}

	/**
	 * @param module
	 * @param name
	 */
	public BatchAction(DataModule module, String name) {
		this(module, name,ImageTools.getImage("disk_multiple.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public BatchAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_B));
		putValue(AbstractAction.SHORT_DESCRIPTION,"Categorize large datasets");		
	}

	
	@Override
	public void run() {
		startBatch(null);

	}
    public void startBatch(File input) {
    	ToxTreeBatchProcessing newBatch = null;	
    	if ( (input != null) && input.exists() && input.isFile())  {
    		try {
    			File outputFile = new File(BatchFactory.generateOutputFileName(input)); 
    			newBatch = new ToxTreeBatchProcessing(input,outputFile);
    			newBatch.setDecisionMethod(((DecisionMethodsDataModule) module).getRules());
    		} catch (BatchProcessingException x) {
    			x.printStackTrace();
    			newBatch = null;
    			/*
    		} catch (IOException x) {
    			x.printStackTrace();
    			newBatch = null;
    			*/
    		}
    	}
    	
    	BatchProcessingDialog d = new BatchProcessingDialog(newBatch,
    			module.getActions().getFrame(),"Batch processing", true);
    	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    	d.setDecisionMethod(((DecisionMethodsDataModule)module).getRules());
    	d.centerScreen();
    	d.show();
    	
    	
    	if (d.getResult() == JOptionPane.CANCEL_OPTION) {
    		IBatchProcessing batch = d.getBatch();
    		if (batch != null) {

    			d.setBatch(null);
    			batch  = null;            			
    		}
    	} else if (d.getResult() == JOptionPane.OK_OPTION) {
    		IBatchProcessing batch = d.getBatch();
    		if (batch != null) {

    			d.setBatch(null);
    			batch  = null;
    		}
    	}
    	d =null;    	
    }

}
