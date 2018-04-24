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
package toxTree.tree.rules;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;

import org.apache.commons.io.FilenameUtils;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;

import ambit2.rendering.IAtomContainerHighlights;
import net.idea.modbcum.i.exceptions.AmbitException;
import toxTree.core.Introspection;
import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.Tools;
import toxTree.tree.AbstractRule;

/**
 * A rule, which returns true if the query is isomorphic to one of the
 * structures read from a preconfigured file of a type SDF, SMI, CSV
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-9-6
 */
public class RuleStructuresList extends AbstractRule {

	protected transient ILookupFile lookupFile;
	protected String filename = null;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1445819316236082148L;

	/**
	 * 
	 */
	public RuleStructuresList() {
		this(new File(Introspection.getToxTreeRoot() + "bodymol.sdf"));

	}

	public RuleStructuresList(String resource) {
		this(Tools.getFileFromResourceSilent(resource));
	}

	public RuleStructuresList(File file) {
		super();

		try {
			setFile(file);

		} catch (Exception x) {
			try {
				setFile(Tools.getFileFromResource(file.getName()));
			} catch (Exception xx) {
				lookupFile = null;
			}
			logger.log(Level.SEVERE, x.getMessage(), x);
		}
		setExplanation(
				"Returns true if the query is isomorphic to one of the structures loaded from a preconfigured file of a type SDF, SMI, CSV ");
		setTitle("Exact search");

	}

	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (lookupFile == null)
			throw new DRuleNotImplemented();
		return lookupFile.find(mol);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return lookupFile != null && lookupFile.isEnabled();
	}

	/**
	 * @return Returns the file.
	 */
	public synchronized File getFile() {
		return lookupFile.getFile();
	}

	/**
	 * @param file
	 *            The file to set.
	 */

	public synchronized void setFile(File file) throws CDKException, IOException {
		String ext = FilenameUtils.getExtension(file.getName());
		if ("inchi".equals(ext))
			lookupFile = new InChILookupFile(file);
		else
			lookupFile = new LookupFile(file);
		setFilename(file.getAbsolutePath());

		logger.fine("Will be using file\t" + file.getAbsoluteFile());

	}

	/**
	 * public synchronized boolean isUsingCache() { return
	 * lookupFile.isUseCache(); } public synchronized void setUsingCache(boolean
	 * useCache) { lookupFile.setUseCache(useCache); } /*
	 * 
	 * @Override public IDecisionRuleEditor getEditor() { return new
	 *           RuleStructuresPanel(this); }
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		try {
			lookupFile = new LookupFile(getFilename());
		} catch (Exception x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			lookupFile = null;
		}
	}

	public synchronized String getFilename() {
		return filename;
	}

	public synchronized void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		boolean ok = verifyRule(mol);
		if (ok && (selected != null))
			selected.add(mol);
		return ok;
	}

	public IAtomContainerHighlights getSelector() {
		return new IAtomContainerHighlights() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1941242188425144120L;

			public IChemObjectSelection process(IAtomContainer mol) throws AmbitException {
				// try {
				// IAtomContainer selected =
				// SilentChemObjectBuilder.getInstance().newAtomContainer();
				// verifyRule(mol, selected);
				return new SingleSelection<IAtomContainer>(mol);
				// } catch (DecisionMethodException x) {
				// throw new AmbitException(x);
				// }
			}

			public boolean isEnabled() {
				return true;
			}

			public long getID() {
				return 0;
			}

			public void setEnabled(boolean arg0) {
			}

			@Override
			public void open() throws Exception {
			}

			@Override
			public void close() throws Exception {
			}

		};
	}

}
