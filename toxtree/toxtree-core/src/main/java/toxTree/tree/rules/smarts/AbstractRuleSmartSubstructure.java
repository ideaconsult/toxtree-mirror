/*
Copyright (C) 2005-2007  

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

package toxTree.tree.rules.smarts;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.core.data.MoleculeTools;
import ambit2.rendering.IAtomContainerHighlights;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.ISmartsPatternFactory;
import ambit2.smarts.query.SMARTSException;
import net.idea.modbcum.i.exceptions.AmbitException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;
import toxTree.tree.AbstractRule;

public abstract class AbstractRuleSmartSubstructure<T> extends AbstractRule implements IRuleSMARTSubstructures,
		ISmartsPatternFactory {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3546551840533459896L;
	protected Map<String, ISmartsPattern<T>> smartsPatterns;
	protected boolean containsAllSubstructures = false;
	protected transient ISmartsPattern final_and_patch = null;

	public AbstractRuleSmartSubstructure() {
		super();
		smartsPatterns = new Hashtable<String, ISmartsPattern<T>>();
		explanation = new StringBuffer();
		explanation.append("Returns true if the query contains substructures specified by SMARTS patterns.");
		setTitle("Substructure by SMARTS patterns.");
		setID("SMARTS");

	}

	public void addSubstructure(String title, String smarts, boolean negate) throws SMARTSException {
		if ("".equals(title))
			addSubstructure(smarts);
		else
			smartsPatterns.put(title, createSmartsPattern(smarts, negate));

	}

	public void addSubstructure(String title, String smarts) throws SMARTSException {
		addSubstructure(title, smarts, false);

	}

	public void addSubstructure(String smarts) throws SMARTSException {
		addSubstructure(Integer.toString(smartsPatterns.size() + 1), smarts);
	}

	public void setSubstructure(String title, String smarts, boolean negate) throws SMARTSException {
		smartsPatterns.put(title, createSmartsPattern(smarts, negate));
	}

	public void setSubstructure(String title, String smarts) throws SMARTSException {
		setSubstructure(title, smarts, false);

	}

	public String getSubstructure(String title) throws SMARTSException {
		return smartsPatterns.get(title).getSmarts();
	}

	public void clearSubstructures() {
		smartsPatterns.clear();
	}

	public void deleteSubstructure(String title) {
		smartsPatterns.remove(title);
	}

	protected abstract T getObjectToVerify(IAtomContainer mol);

	public IAtomContainerHighlights getSelector() {
		return new IAtomContainerHighlights() {
			/**
		     * 
		     */
			private static final long serialVersionUID = -8219964178716366585L;

			public IChemObjectSelection process(IAtomContainer mol) throws AmbitException {
				try {
					IAtomContainer selected = MoleculeTools.newAtomContainer(SilentChemObjectBuilder.getInstance());
					try {
						MolAnalyser.analyse(mol);
					} catch (Exception x) {
					}
					;
					boolean ok = verifyRule(mol, selected);
					if (selected.getAtomCount() == 0)
						return null;
					// selected =
					// AtomContainerManipulator.removeHydrogens(selected);
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

	public boolean verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		try {

			logger.finer(getID());
			T moltotest = getObjectToVerify(mol);
			if (!isAPossibleHit(mol, moltotest)) {
				logger.fine("Not a possible hit due to the prescreen step.");
				return false;
			}

			Iterator e = smartsPatterns.keySet().iterator();
			boolean is_true = false;
			String temp_id = "";
			while (e.hasNext()) {
				temp_id = e.next().toString();

				ISmartsPattern pattern = smartsPatterns.get(temp_id);
				if (pattern == null) {
					throw new DecisionMethodException("ID '" + id + "' is missing in " + getClass().getName());
				}

				is_true = pattern.hasSMARTSPattern(moltotest) > 0;

				logger.fine("SMARTS " + temp_id + '\t' + pattern.toString() + '\t' + is_true);

				if (pattern.isNegate())
					is_true = !is_true;

				if (is_true && (selected != null)) {
					IAtomContainer hit = pattern.getMatchingStructure(mol);
					if (hit != null)
						selected.add(hit);
				}

				if (containsAllSubstructures && !is_true) {

					return false;
				} else if (!containsAllSubstructures && is_true) {
					is_true = true;
					break;
				}

			}
			if (final_and_patch != null) {
				boolean b = final_and_patch.hasSMARTSPattern(moltotest) > 0;
				if (b && (selected != null))
					selected.add(final_and_patch.getMatchingStructure(mol));

				if (final_and_patch.isNegate())
					b = !b;
				return is_true && b;
			} else
				return is_true;
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		} catch (DecisionMethodException x) {
			throw x;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	/*
	 * public boolean verifyRule(org.openscience.cdk.interfaces.IAtomContainer
	 * mol) throws DecisionMethodException { try { logger.info(getID()); T
	 * moltotest = getObjectToVerify(mol); if (!isAPossibleHit(mol,moltotest)) {
	 * logger.debug("Not a possible hit due to the prescreen step."); return
	 * false; }
	 * 
	 * Enumeration e = smartsPatterns.keys(); boolean is_true = false; String
	 * temp_id = ""; while(e.hasMoreElements()){ temp_id =
	 * e.nextElement().toString();
	 * 
	 * ISmartsPattern pattern = smartsPatterns.get(temp_id); if (pattern ==
	 * null) { throw new DecisionMethodException("ID '" + id +
	 * "' is missing in " + getClass().getName()); }
	 * 
	 * is_true = pattern.hasSMARTSPattern(moltotest)>0;
	 * 
	 * logger.debug("SMARTS " + temp_id,'\t',pattern.toString(),'\t',is_true);
	 * 
	 * if (pattern.isNegate()) is_true = ! is_true;
	 * 
	 * if(containsAllSubstructures && !is_true){
	 * 
	 * return false; } else if(!containsAllSubstructures && is_true){ is_true =
	 * true; break; }
	 * 
	 * 
	 * 
	 * } if (final_and_patch != null) {
	 * 
	 * boolean b = final_and_patch.hasSMARTSPattern(moltotest)>0; if
	 * (final_and_patch.isNegate()) b = !b; return is_true && b; } else return
	 * is_true; } catch (SMARTSException x) { throw new
	 * DecisionMethodException(x); } catch (DecisionMethodException x) { throw
	 * x; } catch (Exception x) { throw new DecisionMethodException(x); } }
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	public void removeSingleSMARTS(Hashtable table, String id) throws SMARTSException {

		if (!table.containsKey(id)) {
			throw new SMARTSException("Invalid(id:" + id + ") '" + "' defined in " + getClass().getName());
		}

		table.remove(id);
	}

	public void initSingleSMARTS(Map<String, ISmartsPattern<T>> table, String id, String smartPattern)
			throws SMARTSException {
		ISmartsPattern smarts = createSmartsPattern(smartPattern, false);
		table.put(id, smarts);
	}

	@Override
	public boolean isImplemented() {
		return (smartsPatterns != null) && (smartsPatterns.size() > 0);
	}

	/*
	 * @Override public IDecisionRuleEditor getEditor() { return new
	 * SMARTSRuleEditor(this); }
	 */
	public boolean containsAllSubstructures() {
		return containsAllSubstructures;
	}

	public void setContainsAllSubstructures(boolean allSmarts) {
		this.containsAllSubstructures = allSmarts;
	}

	public Map<String, ISmartsPattern<T>> getSmartsPatterns() {
		return smartsPatterns;
	}

	public void setSmartsPatterns(Hashtable<String, ISmartsPattern<T>> smartsPatterns) {
		this.smartsPatterns = smartsPatterns;
	}

	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("\t\tName\tSMARTS\n");
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		String op = null;
		while (keys.hasNext()) {
			String key = keys.next();
			ISmartsPattern sp = smartsPatterns.get(key);
			printCondition(b, op, key, sp);
			if (containsAllSubstructures())
				op = "AND";
			else
				op = "OR";
			b.append('\n');
		}
		if (final_and_patch != null) {
			printCondition(b, "AND", "", final_and_patch);
			b.append('\n');
		}
		return b.toString();
	}

	private void printCondition(StringBuffer b, String op, String spName, ISmartsPattern sp) {
		if (op != null) {
			b.append("<u>");
			b.append(op);
			b.append("</u>");
		}
		b.append('\t');
		if (sp.isNegate())
			b.append("<u>NOT</u>");
		b.append('\t');
		b.append("\"<b>");
		b.append(spName);
		b.append("</b>\"");
		b.append('\t');
		// b.append(sp);
		String smarts = sp.getSmarts();
		int len = 80;
		while (!"".equals(smarts)) {
			if (smarts.length() < len) {
				b.append(smarts);
				break;
			}
			b.append(smarts.substring(0, len));
			b.append("<br>");
			smarts = smarts.substring(len);
		}
	}

	/**
	 * Returns true always. Override to apply simple prescreening for the rule;
	 * 
	 * @param mol
	 * @return
	 */
	protected boolean isAPossibleHit(IAtomContainer mol, T processedObject) throws DecisionMethodException {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractRuleSmartSubstructure))
			return false;

		AbstractRuleSmartSubstructure r = (AbstractRuleSmartSubstructure) obj;
		if (r.smartsPatterns.size() != smartsPatterns.size())
			return false;

		Iterator<String> keys = smartsPatterns.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			ISmartsPattern sp = smartsPatterns.get(key);
			if (!sp.equals(r.smartsPatterns.get(key)))
				return false;

		}
		return true;
	}

}
