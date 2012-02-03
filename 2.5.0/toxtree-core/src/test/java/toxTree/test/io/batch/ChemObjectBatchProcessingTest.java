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

import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import toxTree.core.Introspection;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.ChemObjectBatchProcessing;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-9-4
 */
public class ChemObjectBatchProcessingTest {

	@Before
	public void setUp() throws Exception {
        Introspection.setLoader(getClass().getClassLoader());
	}
	@Test
	public void testBatch() throws Exception {
		URL url1 = this.getClass().getClassLoader().getResource("data/Misc/test.sdf");

		ChemObjectBatchProcessing bp = new ChemObjectBatchProcessing(
					url1.getFile(),
					"batchResults.sdf"
					);
			bp.start();
			Assert.assertTrue(bp.isStatus(BatchProcessing.STATUS_FINISHED));
		
	}


}
