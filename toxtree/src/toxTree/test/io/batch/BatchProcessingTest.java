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
package toxTree.test.io.batch;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;
import toxTree.core.Introspection;
import toxTree.io.batch.BatchFactory;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ChemObjectBatchProcessing;
import toxTree.logging.TTLogger;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class BatchProcessingTest extends TestCase {
	static TTLogger logger = new TTLogger(BatchProcessingTest.class);
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(BatchProcessingTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
        Introspection.setLoader(getClass().getClassLoader());
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for BatchProcessingTest.
	 * @param arg0
	 */
	public BatchProcessingTest(String arg0) {
		super(arg0);
		TTLogger.configureLog4j(true);
	}

	public void testCreateConfigFile() {
		ChemObjectBatchProcessing bp = new ChemObjectBatchProcessing();
		File file = bp.createConfigFile();
		assertNotNull(file);
		file.delete();
	}


	public void testRoundTrip() throws Exception {

			ChemObjectBatchProcessing bp = new ChemObjectBatchProcessing("toxTree/data/Misc/hbMolecules.sdf",
					"toxTree/data/Misc/results.sdf");
				assertEquals(bp.getStatus(),BatchProcessing.STATUS_NOTSTARTED);			

				//writing
				File fwrite = bp.createConfigFile();
				bp.setConfigFile(fwrite);
				bp.saveConfig();
				bp.close();
				String filename = fwrite.getAbsolutePath();
				logger.debug("Temporary file created\t",filename);
				
				//reading
				File fread = new File(filename);
				
				BatchProcessing newBP = (BatchProcessing)BatchFactory.createFromConfig(fread);
				
				assertEquals(bp,newBP);
				
				newBP.addObserver(new Observer() {
					public void update(Observable o, Object arg) {
						BatchProcessing b = (BatchProcessing) o;
						if (b.getReadRecordsCount() == 20)
							try {
								//cancel batch job
								b.deleteObserver(this);
								b.cancel();
								//save configuration
								b.saveConfig();
								assertEquals(b.getStatus(),BatchProcessing.STATUS_ABORTED);
								assertEquals(b.getReadRecordsCount(),20);
								assertEquals(b.getWrittenRecordsCount(),20);
								b.close();
								
								File configFile = b.getConfigFile();
								BatchProcessing b1 =(BatchProcessing)BatchFactory.createFromConfig(configFile);
								assertEquals(b1.getStatus(),BatchProcessing.STATUS_ABORTED);
								assertEquals(b1.getReadRecordsCount(),20);
								assertEquals(b1.getWrittenRecordsCount(),20);
								
								b1.start();
								assertEquals(b1.getStatus(),BatchProcessing.STATUS_FINISHED);
								assertEquals(b1.getReadRecordsCount(),37);
								assertEquals(b1.getWrittenRecordsCount(),37);
								
								configFile.delete();
								//trying to create from nonexistent config file
								try {
									BatchFactory.createFromConfig(configFile);
								} catch (BatchProcessingException x) {
									logger.error(x);
									assertTrue(true);
								}	
																
								
							} catch (BatchProcessingException x) {
								x.printStackTrace();
								fail();
							}

					}
				});
				newBP.start();				
				
			

	}

}
