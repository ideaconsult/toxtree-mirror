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
package toxTree.io.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import toxTree.core.Introspection;
import toxTree.core.ToxTreeObjectInputStream;
import toxTree.logging.TTLogger;

/**
 * Provides a static function to create an {@link toxTree.io.batch.IBatchProcessing} object by a config file.
 * Configuration relies on java serialization mechanism. 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class BatchFactory {
	protected static TTLogger logger = new TTLogger(BatchFactory.class); 
	/**
	 * 
	 */
	protected BatchFactory() {
		super();
	}
	public static IBatchProcessing createFromConfig(File configFile) throws BatchProcessingException {
		try {
			Thread.currentThread().setContextClassLoader(Introspection.getLoader());
			ToxTreeObjectInputStream is = new ToxTreeObjectInputStream(new FileInputStream(configFile));
			IBatchProcessing newBP = (IBatchProcessing) is.readObject();
			is.close();
			newBP.setConfigFile(configFile);
			newBP.openInputFile();
			newBP.createOutputFile();
			if (newBP.isCancelled()) newBP.pause(); //otherwise starting will not be possible
			
			/*
			if (newBP instanceof ToxTreeBatchProcessing)  
				((ToxTreeBatchProcessing) newBP).setDecisionMethod();
				
			else logger.warn("Object read from batch config file\t",configFile,"\tNOT an instance of ToxTreeBatchProcessing.");
            */
			return newBP;
		} catch (IOException x) {
			throw new BatchProcessingException(x);
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
			throw new BatchProcessingException(x);
		}
	}	

	public static String generateOutputFileName(File inputFile) {
		String n = inputFile.getAbsolutePath();
		return n.substring(0,n.lastIndexOf(".")) + "_toxTree.sdf";
	}

}
