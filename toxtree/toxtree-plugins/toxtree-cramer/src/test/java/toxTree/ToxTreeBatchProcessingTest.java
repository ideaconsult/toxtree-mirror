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
package toxTree;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import toxTree.core.IDecisionMethod;
import toxTree.core.Introspection;
import toxTree.io.batch.BatchFactory;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ToxTreeBatchProcessing;
import toxTree.tree.cramer.CramerRules;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class ToxTreeBatchProcessingTest {

	protected static Logger logger = Logger.getLogger(ToxTreeBatchProcessing.class.getName());
	protected ToxTreeBatchProcessing batch = null;
	protected String config = "data/Misc/batch.cfg";
	protected String configInterrupted = "data/Misc/batchInterrupted.cfg";
	protected IDecisionMethod  rules;

	@Before
	public void setUp() throws Exception {
        Introspection.setLoader(getClass().getClassLoader());
        rules = new CramerRules();
        try {
        	URL url1 = getClass().getClassLoader().getResource("data/Misc/test.sdf");
            batch = new ToxTreeBatchProcessing(
                    url1.getFile(),
                    "batchResults.sdf"
                    );
            ((ToxTreeBatchProcessing) batch).setDecisionMethod(rules);
            File f = new File(config);
            batch.setConfigFile(f);
            batch.saveConfig();
            batch.close();
            batch = null;

        } catch (BatchProcessingException x) {
            x.printStackTrace();
        }
     
	}
	@Test
	public void testSuccessfullBatch() throws Exception  {
			URL url1 = getClass().getClassLoader().getResource("data/Misc/test.sdf");
			ToxTreeBatchProcessing bp = new ToxTreeBatchProcessing(
					url1.getFile(),
					"batchResults.sdf"
					);
			bp.setDecisionMethod(rules);
			bp.addObserver(new Observer() {
				public void update(Observable o, Object arg) {
					BatchProcessing b =	(BatchProcessing) o;
					b.deleteObservers();
					Assert.assertTrue(b.getConfigFile().exists());
				}
			});
			bp.start();
			Assert.assertFalse(bp.getConfigFile().exists()); //tmp files deleted on success
			Assert.assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));
			Assert.assertEquals(bp.getReadRecordsCount(),29);
			Assert.assertEquals(bp.getWrittenRecordsCount(),29);

	}
	/*
	public void testBatchInThread() {
		try {
			ToxTreeBatchProcessing bp = new ToxTreeBatchProcessing(
					"cellbox:/src/toxTree/src/toxTree/config/test.sdf",
					"cellbox:/src/toxTree/src/toxTree/config/batchResults.sdf"
					);
			bp.setDecisionMethod(new CramerRules());			
			Thread thread = new Thread(bp);
			thread.run();
			
			assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));
			assertEquals(bp.getReadRecordsCount(),29);
			assertEquals(bp.getWrittenRecordsCount(),29);
			
		} catch (BatchProcessingException x) {
			x.printStackTrace();
			fail();
		}
	}
	*/
	@Test
	public void testBatchInThread() throws Exception {
		URL url1 = getClass().getClassLoader().getResource("data/Misc/test.sdf");
			ToxTreeBatchProcessing bp = new ToxTreeBatchProcessing(
					url1.getFile(),
					//"cellbox:/nina/Databases/ligand_info_subset_1_dos.sdf", space after property line
					//"cellbox:/nina/Databases/nciopen_3D_fixed.sdf",
					"batchResults.sdf"
					);
			
			bp.addObserver(new Observer() {

				public void update(Observable o, Object arg) {
					if (o instanceof BatchProcessing) {
						System.out.println(((BatchProcessing) o));

					}

				}
			});
					
			bp.setDecisionMethod(rules);			
			bp.start();
			
			
			Assert.assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));
			Assert.assertEquals(bp.getReadRecordsCount(),29);
			Assert.assertEquals(bp.getWrittenRecordsCount(),29);

	}
	@Test
	public void testBatchFromConfig() throws Exception {
		URL url1 = getClass().getClassLoader().getResource(config);
			ToxTreeBatchProcessing bp = (ToxTreeBatchProcessing)BatchFactory.createFromConfig(new File(url1.getFile()));
			Assert.assertNotNull(bp.getDecisionMethod());
//			bp.setDecisionMethod(new CramerRules());			
			bp.setSaveStateFrequency(10);
			bp.addObserver(new Observer() {

				public void update(Observable o, Object arg) {
					if (o instanceof BatchProcessing) {			
						BatchProcessing b =	(BatchProcessing) o;
						System.out.println(b);
						if (b.getReadRecordsCount()> 11) 
								b.deleteObservers();
						try {
								b.pause();
						} catch (Exception x) {
							x.printStackTrace();
						}
					}

				}
			});			
			bp.start();
			
			Assert.assertTrue(bp.isStatus(BatchProcessing.STATUS_PAUSED));
			Assert.assertEquals(12,bp.getReadRecordsCount());
			Assert.assertEquals(12,bp.getWrittenRecordsCount());
			
			bp.addObserver(new Observer() {
				/* (non-Javadoc)
				 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
				 */
				public void update(Observable o, Object arg) {
					System.out.println("Continued ...\t" + o);

				}
			}
					);
			bp.start();
			

	}
	@Test
	public void testInterruptedJob() throws Exception  {
		
		URL url1 = getClass().getClassLoader().getResource("data/Misc/test.sdf");
			batch = new ToxTreeBatchProcessing(
					url1.getFile(),
					"batchInterruptedResults.sdf"
					);
			batch.setDecisionMethod(new CramerRules());
			File f = new File(configInterrupted);
			batch.setConfigFile(f);
			//batch.saveConfig();
			((Observable)batch).addObserver(new Observer() {
				public void update(Observable o, Object arg) {
					if (((BatchProcessing) o).getReadRecordsCount() > 11) {
						((BatchProcessing) o).deleteObservers();
						try {
						((BatchProcessing) o).pause();
						} catch (BatchProcessingException x) {
							x.printStackTrace();
						}
					}

				}
			});
			batch.start();
			Assert.assertEquals(12,batch.getReadRecordsCount());
			System.err.println("???\t" + batch);
			batch.saveConfig();  //configuration should be saved after paused batch
			batch.cancel(); //otherwise files will not be closed !!!!!!!!!!!
			batch.close();
			batch = null;			

//------------------			
			url1 = getClass().getClassLoader().getResource(configInterrupted);
			ToxTreeBatchProcessing bp = (ToxTreeBatchProcessing)BatchFactory.
							createFromConfig(new File(url1.getFile()));
			
			Assert.assertEquals(BatchProcessing.STATUS_PAUSED,bp.getStatus());
			
			Assert.assertEquals(21,bp.getReadRecordsCount());
			Assert.assertEquals(21,bp.getWrittenRecordsCount());		

			/*
//			bp.setDecisionMethod(new CramerRules());			
			bp.addObserver(new Observer() {

				public void update(Observable o, Object arg) {
					if (o instanceof BatchProcessing) {			
						BatchProcessing b =	(BatchProcessing) o;
						System.out.println(b);
						if (b.getReadRecordsCount()> 11) 
							try {
								b.deleteObservers();
								b.pause();
								System.out.println(b);
							} catch (BatchProcessingException x) {
								x.printStackTrace();
								fail();
							}

					}

				}
			});			
			bp.start();
			
			assertTrue(bp.isStatus(BatchProcessing.STATUS_PAUSED));
			assertEquals(bp.getReadRecordsCount(),21);
			assertEquals(bp.getWrittenRecordsCount(),21);
			
			bp.addObserver(new Observer() {
				public void update(Observable o, Object arg) {
					System.out.println("Continued ...\t" + o);

				}
			}
					);
			bp.start();
*/

	}	

}
