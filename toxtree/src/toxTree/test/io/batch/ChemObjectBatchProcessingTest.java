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

import junit.framework.TestCase;
import toxTree.core.Introspection;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ChemObjectBatchProcessing;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-9-4
 */
public class ChemObjectBatchProcessingTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ChemObjectBatchProcessingTest.class);
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
	 * Constructor for ChemObjectBatchProcessingTest.
	 * @param arg0
	 */
	public ChemObjectBatchProcessingTest(String arg0) {
		super(arg0);
	}

	public void testBatch() {
		try {
			ChemObjectBatchProcessing bp = new ChemObjectBatchProcessing(
					"toxTree/data/Misc/test.sdf",
					"toxTree/data/Misc/batchResults.sdf"
					);
			bp.start();
			assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));

		} catch (BatchProcessingException x) {
			x.printStackTrace();
			fail();
		}
		
	}


}
