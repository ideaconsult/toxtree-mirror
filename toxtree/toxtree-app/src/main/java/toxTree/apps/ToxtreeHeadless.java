/*
Ideaconsult Ltd. (C) 2005-2015  
Contact: www.ideaconsult.net

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
package toxTree.apps;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.Introspection;
import toxTree.query.MolAnalyser;
import toxTree.tree.DecisionMethodsList;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.io.FileInputState;
import ambit2.core.io.FileOutputState;

public class ToxtreeHeadless {
	protected static Logger logger = Logger.getLogger(ToxTreeApp.class
			.getName());
	protected ToxtreeOptions options;

	public ToxtreeHeadless(ToxtreeOptions options) {
		this.options = options;

	}

	public void run() throws Exception {
		DecisionMethodsList methods = new DecisionMethodsList();
		try {
			// methods.loadAllFromPlugins();
			methods.loadPluginsByConfiguration();

		} catch (Exception x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
		}
		
		IDecisionMethod tree = null;

		String className = options.getModule();
		if (className == null)
			className = "toxTree.tree.cramer.CramerRules";

		Object c = Introspection.loadCreateObject(className);
		if (c instanceof IDecisionMethod)
			tree = (IDecisionMethod) c;
	
		if (tree == null)
			throw new Exception("Decision tree not defined");
		IDecisionResult result = tree.createDecisionResult();
		IIteratingChemObjectReader reader = null;
		IChemObjectWriter writer = null;
		FileOutputStream out;
		try {
			reader = FileInputState.getReader(options.getInput());
			out = new FileOutputStream(options.getOutput());
			writer = FileOutputState.getWriter(out, options.getOutput()
					.getName());
			long now = System.currentTimeMillis();
			logger.log(Level.INFO, String.format("%s:Processing started\tInput:%s\tOutput:%s",className,options.getInput().getAbsolutePath(),options.getOutput().getAbsolutePath()));
			int r = 0;
			while (reader.hasNext()) {
				Object o = reader.next();
				if (o instanceof IAtomContainer) try {
					IAtomContainer mol = (IAtomContainer) o;
					MolAnalyser.analyse(mol);
					result.classify(mol);
					result.assignResult(mol);
					// System.out.println(mol.getProperties());
					writer.write(mol);
					r++;
					if ((r % 1000)==0) 
						logger.log(Level.INFO, String.format("%s:Records processed %d",r));
				} catch (Exception x) {
					logger.log(Level.WARNING, x.getMessage());
				}
			}
			logger.log(Level.INFO, String.format("%s:Records processed %d\t%s ms\tInput:%s\tOutput:%s",
					className,r,System.currentTimeMillis()-now,options.getInput().getAbsolutePath(),options.getOutput().getAbsolutePath()));
		} catch (Exception x) {
			logger.log(Level.WARNING, x.getMessage(), x);
		} finally {
			try {
				reader.close();
			} catch (Exception x) {
			}
			try {
				writer.close();
			} catch (Exception x) {
			}
		}
	}
}
