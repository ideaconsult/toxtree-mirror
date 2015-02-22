package mutant.descriptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.mcss.RMap;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.query.FunctionalGroups;
import ambit2.core.data.MoleculeTools;

public class SubstituentExtractor {
	// protected static String _RING="RING";
	protected static Boolean yes = new Boolean(true);
	protected static String atom_C = "C";
	protected QueryAtomContainer ringQuery;
	protected static Logger logger = Logger
			.getLogger(SubstituentExtractor.class.getName());

	public SubstituentExtractor() {
		this(null);
	}

	public SubstituentExtractor(QueryAtomContainer ringQuery) {
		super();
		setRingQuery(ringQuery);
	}

	/**
	 * Note: Aromaticity flags should already be set before running this method.
	 * 
	 * @param a
	 * @return
	 * @throws Exception
	 */
	public Hashtable<String, IAtomContainerSet> extractSubstituents(
			IAtomContainer a) throws CDKException {

		logger.finer("extract substituents");
		if (ringQuery == null)
			throw new CDKException("Query not assigned");
		UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
		List list = uit.getSubgraphAtomsMaps(a, ringQuery);
		list = getUniqueAtomMaps(list);
		Hashtable<String, IAtomContainerSet> substituents = new Hashtable<String, IAtomContainerSet>();

		for (int l = 0; l < list.size(); l++) {
			String mark = FunctionalGroups.RING_NUMBERING + "_"
					+ Integer.toString(l + 1);
			if (markAtomsInRing(mark, (List) list.get(l), a, ringQuery)) {

				IAtomContainer mc = cloneDiscardRingAtomAndBonds(a, mark);
				IAtomContainerSet s = ConnectivityChecker
						.partitionIntoMolecules(mc);

				substituents.put(mark, s);
			} else
				throw new CDKException(ringQuery.toString() + "\t not found.");
		}
		return substituents;
	}

	protected List getUniqueAtomMaps(List list) {
		int[] prev = null;
		int[] next = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			List sublist = (List) list.get(i);
			prev = next;
			next = new int[sublist.size()];
			for (int j = 0; j < sublist.size(); j++) {
				next[j] = ((RMap) sublist.get(j)).getId1();
			}
			Arrays.sort(next);
			if ((prev != null) && (next != null) && Arrays.equals(next, prev)) {

				list.remove(i);
			}

		}
		return list;
	}

	/**
	 * 
	 * @param list
	 *            List of RMap
	 * @param mol
	 * @param q
	 * @return
	 * @throws CDKException
	 */
	public static boolean markAtomsInRing(String property, List list,
			IAtomContainer mol, QueryAtomContainer q) throws CDKException {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				RMap map = (RMap) list.get(i);
				if (map.getId2() >= 0) {
					SubstituentPosition p = SubstituentExtractor
							.getSubstituentNumber(
									FunctionalGroups.RING_NUMBERING, q,
									map.getId2());
					if (p != null) {
						SubstituentExtractor.setSubstituentNumber(property,
								mol, map.getId1(), p);
						mol.getAtom(map.getId1()).setID(
								Integer.toString((map.getId1())));
					}
				}
			}
			return true;
		} else
			return false;
	}

	public static IAtomContainer cloneDiscardRingAtomAndBonds(
			IAtomContainer ac, String mark) {
		IAtomContainer result = new org.openscience.cdk.AtomContainer();
		Hashtable<IAtom, IAtom> table = new Hashtable<IAtom, IAtom>();
		List<IBond> forbiddenBonds = new ArrayList<IBond>();

		for (int i = 0; i < ac.getAtomCount(); i++) {
			IAtom a = ac.getAtom(i);
			SubstituentPosition pr = getSubstituentNumber(mark, ac, i);
			if (pr != null)
				if (pr.isRing()) {

					continue;
				} else {
					List<IAtom> atoms = new ArrayList<IAtom>();
					atoms.add(a);
					boolean breakBond = breakBond(ac, a, mark,
							pr.getPosition(), atoms);
					List<IBond> bonds = ac.getConnectedBondsList(a);
					// System.out.print("bonds "+pr);
					for (int j = 0; j < bonds.size(); j++) {
						/*
						 * System.out.print(bonds.get(j).getAtom(0).getSymbol());
						 * System
						 * .out.print(bonds.get(j).getAtom(1).getSymbol());
						 * System.out.print(' ');
						 * System.out.print(bonds.get(j).getFlag
						 * (CDKConstants.ISINRING)); System.out.print('\t');
						 */
						if (breakBond
								&& bonds.get(j).getFlag(CDKConstants.ISINRING)) {

							forbiddenBonds.add(bonds.get(j));
						}
					}
				}
			table.put(a, a);
			result.addAtom(a);
		}

		for (int i = 0; i < ac.getBondCount(); i++) {
			IBond b = ac.getBond(i);

			IAtom a1 = (IAtom) table.get(b.getAtom(0));
			IAtom a2 = (IAtom) table.get(b.getAtom(1));

			if ((a1 != null) && (a2 != null))
				if (forbiddenBonds.indexOf(b) == -1) {
					result.addBond(b);
				} else {
					IAtom a = null;
					if (a1 != null)
						a = a1;
					else if (a2 != null)
						a = a2;
					IAtom h = MoleculeTools.newAtom(
							SilentChemObjectBuilder.getInstance(),
							Elements.HYDROGEN);
					result.addAtom(h);
					result.addBond(MoleculeTools.newBond(
							SilentChemObjectBuilder.getInstance(), a, h,
							CDKConstants.BONDORDER_SINGLE));
				}
			else {

				IAtom a = null;
				if (a1 != null)
					a = a1;
				else if (a2 != null)
					a = a2;

				if (a != null) {
					// Substituent starting with "+a.getSymbol() + " " +
					// a.getProperties());
					IAtom r = MoleculeTools.newPseudoAtom(
							SilentChemObjectBuilder.getInstance(), "R");
					result.addAtom(r);
					result.addBond(MoleculeTools.newBond(
							SilentChemObjectBuilder.getInstance(), a, r,
							b.getOrder()));
				}
			}
		}
		return result;
	}

	protected static boolean breakBond(IAtomContainer ac, IAtom a, String mark,
			int position, List<IAtom> atoms) {
		List<IBond> bonds = ac.getConnectedBondsList(a);
		for (int i = 0; i < bonds.size(); i++) {
			IBond b = bonds.get(i);
			for (int j = 0; j < b.getAtomCount(); j++) {
				IAtom atom = b.getAtom(j);
				if (atoms.indexOf(atom) >= 0)
					continue;
				SubstituentPosition pos = SubstituentExtractor
						.getSubstituentNumber(mark, atom);
				if (pos != null) {
					if (pos.getPosition() == position) {
						atoms.add(atom);
						continue;
					}
					if (!pos.isRing() && (pos.getPosition() != position)) {
						// that is, we found a cycle connecting to another
						// substituent
						return true;
					}
				}
				atoms.add(atom);
				if (breakBond(ac, atom, mark, position, atoms))
					return true;
			}
		}
		return false;
	}

	public QueryAtomContainer getRingQuery() {
		return ringQuery;
	}

	public void setRingQuery(QueryAtomContainer ringQuery) {
		this.ringQuery = ringQuery;
	}

	public static SubstituentPosition getSubstituentNumber(String property,
			IAtomContainer m, int atom) {
		return getSubstituentNumber(property, m.getAtom(atom));
	}

	public static SubstituentPosition getSubstituentNumber(String property,
			IAtom atom) {
		Object o = atom.getProperty(property);
		if (o == null)
			return null;
		else if (o instanceof SubstituentPosition)
			return (SubstituentPosition) o;
		else {
			logger.severe("Expected Integer type instead of "
					+ o.getClass().getName());
			return null;
		}
	}

	public static void setSubstituentNumber(String property, IAtomContainer m,
			int atom, SubstituentPosition position) {
		setSubstituentNumber(property, m.getAtom(atom), position);
	}

	public static void setSubstituentNumber(String property, IAtom atom,
			SubstituentPosition position) {
		atom.setProperty(property, position);
	}

}
