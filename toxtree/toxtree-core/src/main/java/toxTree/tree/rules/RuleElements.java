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

import java.util.HashSet;
import java.util.List;

import net.idea.modbcum.i.exceptions.AmbitException;
import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.core.SmartElementsList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import ambit2.core.data.MoleculeTools;

/**
 * Verifies if the molecule consists of only allowed elements
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-14
 */
public class RuleElements extends AbstractRule {
	public static String[] comparison = { "Has only specified elements",
			"Has all of specified elements", "Has any of specified elements" };
	public static final int modeOnlySpecifiedElements = 0;
	public static final int modeAllSpecifiedElements = 1;
	public static final int modeAnySpecifiedElements = 2;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6678188211661773753L;
	protected SmartElementsList elements;

	protected int mode = 0;

	/**
	 * 
	 */
	public RuleElements() {
		super();
		elements = new SmartElementsList();
		setComparisonMode(0);
		explanation.append(title);
		id = getClass().getName();

	}

	public void setHalogens(String[] halogens) {
		elements.setHalogens(halogens);
	}

	public void setHalogens(HashSet halogens) {
		elements.setHalogens(halogens);
	}

	public HashSet gethalogens() {
		return elements.getHalogens();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}

	/**
	 * @return Returns the hasOnlySpecifiedElements.
	 */
	public boolean hasOnlySpecifiedElements() {
		return mode == 0;
	}

	/**
	 * @param hasOnlySpecifiedElements
	 *            The hasOnlySpecifiedElements to set.
	 */
	public void setComparisonMode(int mode) {
		this.mode = mode;
		title = comparison[mode];

		explanation = new StringBuffer();
		explanation.append(getTitle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#getName()
	 */
	@Override
	public String getTitle() {
		return title + elements.toString();
	}

	public void addElement(String element) {
		// this is a set, no duplicates will be added
		elements.add(element);
	}

	public void removeElement(String element) {
		elements.remove(element);
	}

	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean contains(String element) {
		return elements.contains(element);
	}

	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	public boolean verifyRule(
			org.openscience.cdk.interfaces.IAtomContainer mol,
			IAtomContainer selected) throws DecisionMethodException {
		logger.finer(toString());
		IMolecularFormula formula = MolecularFormulaManipulator
				.getMolecularFormula(mol);
		// int c =
		// MolecularFormulaManipulator.getElementCount(formula,formula.getBuilder().newElement("C"));

		List<IElement> v = MolecularFormulaManipulator.elements(formula);
		SmartElementsList list = new SmartElementsList();
		list.setHalogens(elements.getHalogens());
		for (int i = 0; i < v.size(); i++)
			list.add(v.get(i).getSymbol());

		boolean ok = false;
		switch (mode) {
		case (modeOnlySpecifiedElements): {
			ok = elements.containsAll(list);
			select(mol, selected);

			logger.finer("Has only elements: " + elements + "\t" + list + "\t"
					+ ok);
			return ok;
		}
		case modeAllSpecifiedElements: {
			ok = list.equals(elements);
			select(mol, selected);

			logger.finer("Has all of elements: " + elements + "\t" + list
					+ "\t" + ok);
			return ok;
		}
		case modeAnySpecifiedElements: {
			// intersection - e.g. has any of the specified elements
			list.retainAll(elements);
			ok = list.size() > 0;
			select(mol, selected);

			logger.finer("Has any of elements: " + elements + "\t" + list
					+ "\t" + ok);
			return ok;
		}
		default:
			throw new DecisionMethodException("Undefined comparison mode!"
					+ mode);
		}

		/*
		 * String s; int c = 0; for (int i=0 ; i < v.size(); i++) { s = (String)
		 * v.get(i); if (elements.contains(s)) c++; } if
		 * (hasOnlySpecifiedElements) return c == v.size(); else return (c > 0);
		 */

	}

	public void select(IAtomContainer mol, IAtomContainer selected) {
		if (selected != null)
			elements.select(mol, selected, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& elements.equals(((RuleElements) obj).elements);
	}

	public SmartElementsList getElements() {
		return elements;
	}

	public void setElements(SmartElementsList elements) {
		this.elements = elements;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return new IProcessor<IAtomContainer, IChemObjectSelection>() {
			/**
		     * 
		     */
		    private static final long serialVersionUID = -369980300586785085L;

			public IChemObjectSelection process(IAtomContainer mol)
					throws AmbitException {
				try {
					IAtomContainer selected = MoleculeTools
							.newAtomContainer(SilentChemObjectBuilder
									.getInstance());
					verifyRule(mol, selected);
					return new SingleSelection<IAtomContainer>(selected);
				} catch (DecisionMethodException x) {
					throw new AmbitException(x);
				}
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
