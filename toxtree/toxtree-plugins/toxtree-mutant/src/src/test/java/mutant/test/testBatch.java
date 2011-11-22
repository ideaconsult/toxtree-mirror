/*
Copyright (C) 2005-2006  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package mutant.test;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;

import mutant.BB_CarcMutRules;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ToxTreeBatchProcessing;
import toxtree.ui.batch.BatchProcessingDialog;

public class testBatch  {
	public void run() {
		try {
			ToxTreeBatchProcessing bp = new ToxTreeBatchProcessing(
					"/data/iss2_rid.sdf",
					"/data/iss2_rid_results.sdf"
					);
			bp.setDecisionMethod(new BB_CarcMutRules());
			bp.addObserver(new Observer() {
				public void update(Observable o, Object arg) {
					BatchProcessing b =	(BatchProcessing) o;
					b.deleteObservers();
					//assertTrue(b.getConfigFile().exists());
				}
			});
			BatchProcessingDialog d = new BatchProcessingDialog(bp);
			d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    	d.centerScreen();
			d.show();

//			bp.start();
			/*
			assertFalse(bp.getConfigFile().exists()); //tmp files deleted on success
			assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));
			assertEquals(bp.getReadRecordsCount(),29);
			assertEquals(bp.getWrittenRecordsCount(),29);
			*/
		} catch (DecisionMethodException x) {
			//fail();
		} catch (BatchProcessingException x) {
			x.printStackTrace();
			//fail();
		}
	}
	public static void main(String[] args) {
		testBatch v = new testBatch();
		v.run();
	}
}


