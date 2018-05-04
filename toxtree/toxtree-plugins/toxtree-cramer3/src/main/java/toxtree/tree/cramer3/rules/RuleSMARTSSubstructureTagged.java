package toxtree.tree.cramer3.rules;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.cramer3.groups.E_Groups;

public class RuleSMARTSSubstructureTagged extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5952223728765270277L;
	protected String tag;

	public RuleSMARTSSubstructureTagged() throws SMARTSException {
		super();
		tag = getClass().getName();
	}

	public Map<String, Integer> tagAtoms(IAtomContainer mol,
			IAtomContainer selected,
			Map<String, ISmartsPattern<IAtomContainer>> smartsPatterns)
			throws DecisionMethodException {
		Map<String, Integer> histogram = new HashMap<String, Integer>();
		String key = "H";
		for (IAtom atom : mol.atoms())
			if (key.equals(atom.getSymbol())) {
				Integer i = histogram.get(key);
				if (i == null)
					histogram.put(key, 1);
				else
					histogram.put(key, i.intValue() + 1);
			}
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {
			while (keys.hasNext()) {
				key = keys.next();
				ISmartsPattern<IAtomContainer> pattern = smartsPatterns.get(key);
				logger.finer(String.format(
						"Checking %s\t%s",	 key, pattern.getSmarts()));
				if (pattern.hasSMARTSPattern(mol) > 0) {
					// this is not very efficient, as atomcontainer copy is
					// involved todo callback
					logger.fine(String.format(
							"Found %s",	 key));
					IAtomContainer match = smartsPatterns.get(key)
							.getMatchingStructure(mol);
					
					for (IAtom atom : match.atoms()) {
						Object tagValue = atom.getProperty(tag);
						if (tagValue != null)
							logger.finer(String.format(
									"Overlapping groups [%s] and [%s] !!!",
									tagValue, key));
						else {
							atom.setProperty(tag, key);
							Integer i = histogram.get(key);
							if (i == null)
								histogram.put(key, 1);
							else
								histogram.put(key, i.intValue() + 1);
						}
					}

				}
			}
			if (histogram != null)
				logger.fine(String.format("All atoms %d\ttagged %s", mol.getAtomCount(),histogram.toString()));
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}
		return histogram;
	}

	
	protected boolean uniqueGroup(int allatoms, String key,
			Map<String, Integer> histogram) {
		Integer i = histogram.get(key);
		if (i == null)
			return false;
		Integer h = histogram.get("H");
		Integer c = histogram.get(E_Groups.aliphatic_chain.name());
		return allatoms == (h == null ? 0 : h) + (c == null ? 0 : c) + i;

	}
}
