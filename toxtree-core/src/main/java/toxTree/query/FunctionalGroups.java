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

 *//**
 * <b>Filename</b> FunctionalGroups.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-8
 * <b>Project</b> toxTree
 * 
 */
package toxTree.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.graph.PathTools;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.InverseSymbolSetQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainerCreator;
import org.openscience.cdk.isomorphism.matchers.SymbolAndChargeQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolSetQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AliphaticSymbolAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyOrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.smarts.AromaticSymbolAtom;
import org.openscience.cdk.isomorphism.mcss.RMap;
import org.openscience.cdk.ringsearch.SSSRFinder;
import org.openscience.cdk.silent.AtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.core.ConnectionMatrix;
import toxTree.tree.AbstractRule;
import ambit2.core.data.MoleculeTools;
import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.core.smiles.SmilesParserWrapper;
import ambit2.core.smiles.SmilesParserWrapper.SMILES_PARSER;

/**
 * This class provides static methods for various functional groups In addition
 * there are several static methods to manipulate searching
 * 
 * @author Nina Jeliazkova <br>
 *         <b>Modified</b> 2005-8-8
 */
public class FunctionalGroups {
	public static Logger logger = Logger.getLogger(FunctionalGroups.class
			.getName());
	/**
	 * Messages
	 */
	public static final String MSG_HASGROUP = "\thas group \t";
	public static final String MSG_MOLECULEIS = "Molecule is\t";
	/**
	 * These constants are used in Atom.setProperty(CH3,true) fashion in order
	 * to mark which atoms/bonds belong to the corresponding group Used by
	 * {@link #markMaps(IAtomContainer, IAtomContainer, List)} method
	 */
	public static final String CH3 = "CH3";
	public static final String CH2 = "CH2";
	public static final String CH = "CH";
	public static final String C = "C";
	public static final String HYDROCARBON = "hydrocarbon";
	public static final String PRIMARY_AMINE = "Primary amine";
	public static final String SECONDARY_AMINE = "Secondary amine";
	public static final String TERTIARY_AMINE = "tertiary amine";
	public static final String AROMATIC_AMINE = "aromatic amine";

	public static final String SECONDARY_AMINE_ALIPHATIC = "Aliphatic Secondary amine";
	public static final String CYANO = "cyano";
	public static final String NITRO = "nitro";
	public static final String NNITROSO = "N-nitroso";
	public static final String DIAZO = "diazo";
	public static final String TRIAZENO = "triazeno";
	public static final String CARBOXYLIC_ACID_SALT = "carboxylic acid salt";
	public static final String CARBOXYLIC_ACID = "carboxylic acid";
	public static final String ACETAL = "acetal";
	public static final String ETHER = "ether";
	public static final String METHYLETHER = "methylether";
	public static final String SULPHIDE = "sulphide";
	public static final String MERCAPTAN = "mercaptan";
	public static final String ESTER = "ester";
	public static final String THIOESTER = "thioester";
	public static final String KETONE = "ketone";
	public static final String KETONE_SIDECHAIN = "ketone_sidechain";
	public static final String ALDEHYDE = "aldehyde";
	public static final String CARBONYL = "carbonyl";
	public static final String CARBONYL_ABUNSATURATED = "CARBONYL_ABUNSATURATED";
	public static final String ALCOHOL = "alcohol";
	public static final String SULPHONATE = "sulphonate";
	public static final String SULPHAMATE = "sulphamate";
	public static final String SULPHATE = "sulphate";
	public static final String PHOSPHATE = "phosphate";
	public static final String POLYOXYETHYLENE = "polyoxyethylene";
	public static final String HYDROCHLORIDE_OF_AMINE = "hydrochloride of amine";
	public static final String SULPHATE_OF_AMINE = "sulphate of amine";
	public static final String ACETYLENIC = "acetylenic";
	public static final String LACTONE = "lactone";
	public static final String CYCLIC_DIESTER = "cyclic diester";
	public static final String ISOPRENE = "isoprene unit";
	public static final String HYDROXY = "hydroxy";
	public static final String METHOXY = "methoxy";
	public static final String HYDROXY1 = "1'hydroxy";
	public static final String HYDROXYESTERSUBSTITED = "hydroxy ester substituted";
	public static final String QUATERNARY_NITROGEN = "quaternary nitrogen";
	public static final String QUATERNARY_NITROGEN_EXCEPTION = "quaternary nitrogen exception";
	public static final String ANHYDRIDE = "ANHYDRIDE";
	public static final String CARBONATE = "CARBONATE";
	public static final String AROMATIC_N_OXIDE = "Aromatic ring N oxide";

	public static final String RING_NUMBERING = "RingNumbering";
	/**
	 * Use DONTMARK constant when building a query QueryAtom atom;
	 * atom.setProperty(DONTMARK,query.getID()); This is used as a flag that the
	 * atom belongs not to the group but to the neighboring radicals It is also
	 * essential for the
	 * {@link #detachGroup(IAtomContainer, QueryAtomContainer)} procedure to
	 * work, as the bond to break is the one with one atom having the property
	 * set, while the other has the property unset
	 */
	public static final String DONTMARK = "DONTMARK";
	// TODO replace current marking with ALLOCATED
	/**
	 * The intention is to use this property to mark if an atom/bond was already
	 * associated with a functional group. It will help to avoid wrong
	 * overlapping allocation of different group to the same atom Finally,
	 * marking by setProperty(query.getID(),new Boolean(true)) may be replaced
	 * with setProperty(ALLOCATED,query.getID()) perhaps in v0.04 release :)
	 */
	public static final String ALLOCATED = "ALLOCATED";

	protected static CDKHydrogenAdder h = CDKHydrogenAdder
			.getInstance(SilentChemObjectBuilder.getInstance());

	/**
     * 
     */
	protected FunctionalGroups() {
		super();
	}

	/**
	 * Query for Methyl group.
	 * 
	 * @return
	 */
	public static QueryAtomContainer methyl() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(CH3);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(c);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addAtom(h3);
		query.addBond(new OrderQueryBond(c, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h3, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	/**
	 * Query for methoxy group.
	 * 
	 * @return
	 */
	public static QueryAtomContainer methoxy() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(METHOXY);
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setSymbol("R");
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(r);
		query.addAtom(o);
		query.addAtom(c);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addAtom(h3);
		query.addBond(new OrderQueryBond(r, o, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h3, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer methoxy_ring() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(METHOXY);
		TopologyAnyAtom r = new TopologyAnyAtom(true);
		r.setSymbol("R");
		r.setProperty(DONTMARK, query.getID());
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(r);
		query.addAtom(o);
		query.addAtom(c);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addAtom(h3);
		query.addBond(new OrderQueryBond(r, o, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h3, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer noxide_aromatic() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(AROMATIC_N_OXIDE);
		AromaticSymbolAtom n = new AromaticSymbolAtom("N");
		n.setCharge(1.0);
		TopologySymbolQueryAtom o = new TopologySymbolQueryAtom(
				MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
						Elements.OXYGEN), false);
		o.setCharge(-1.0);
		query.addAtom(o);
		query.addAtom(n);
		query.addBond(new OrderQueryBond(o, n, CDKConstants.BONDORDER_SINGLE));

		return query;
	}

	public static QueryAtomContainer hydroxy_ring() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(HYDROXY);
		TopologySymbolQueryAtom c = new TopologySymbolQueryAtom(
				MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
						Elements.CARBON), true);
		c.setProperty(DONTMARK, query.getID());
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(h);
		query.addAtom(o);
		query.addAtom(c);
		query.addBond(new OrderQueryBond(o, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, h, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer hydroxy1() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(HYDROXY1);
		// aliphatic c
		IQueryAtom c = new AliphaticSymbolAtom("C");
		// old - fails to match safrole
		// TopologySymbolQueryAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON),false);
		c.setProperty(DONTMARK, query.getID());
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(h);
		query.addAtom(o);
		query.addAtom(c);
		query.addBond(new OrderQueryBond(o, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, h, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// CCCC...COC(=O)R
	public static QueryAtomContainer hydroxyEsterSubstituted() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(HYDROXYESTERSUBSTITED);
		SymbolQueryAtom c1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom c2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom r = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(r);
		query.addAtom(o);
		query.addAtom(o2);
		query.addAtom(c1);
		query.addAtom(c2);
		query.addBond(new OrderQueryBond(c1, o, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, c2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o2, c2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(r, c2, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// R-CH2-R
	public static QueryAtomContainer ethyl() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(CH2);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		InverseSymbolSetQueryAtom h3 = new InverseSymbolSetQueryAtom();
		h3.addSymbol("H");
		h3.setProperty(DONTMARK, query.getID());
		InverseSymbolSetQueryAtom h4 = new InverseSymbolSetQueryAtom();
		h4.addSymbol("H");
		h4.setProperty(DONTMARK, query.getID());

		query.addAtom(c);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addAtom(h3);
		query.addBond(new OrderQueryBond(c, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, h2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new AnyOrderQueryBond(c, h3,
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new AnyOrderQueryBond(c, h3,
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer hydrocarbon() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID(CH);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		query.addAtom(c);
		SymbolSetQueryAtom[] neighbors = new SymbolSetQueryAtom[4];
		for (int i = 0; i < 4; i++) {
			neighbors[i] = new SymbolSetQueryAtom();
			neighbors[i].addSymbol("C");
			neighbors[i].addSymbol("H");
			query.addAtom(neighbors[i]);
			query.addBond(new OrderQueryBond(c, neighbors[i],
					CDKConstants.BONDORDER_SINGLE));
		}
		return query;
	}

	public static QueryAtomContainer primaryAmine(boolean aliphatic) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(PRIMARY_AMINE);
		IQueryAtom r;
		if (aliphatic)
			r = new TopologySymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON),
					false);
		else
			r = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		((IAtom) r).setProperty(DONTMARK, query.getID());
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom((IAtom) r);
		query.addAtom(n);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n, h2, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	/**
	 * SMARTS: [C;R0]-;!@[N;R0;H1]-;!@[C;R0]
	 * 
	 * @param aliphatic
	 * @return
	 */
	public static QueryAtomContainer secondaryAmine(boolean aliphatic) {
		QueryAtomContainer query = new QueryAtomContainer();
		if (aliphatic)
			query.setID(SECONDARY_AMINE_ALIPHATIC);
		else
			query.setID(SECONDARY_AMINE);
		SymbolQueryAtom[] c = new SymbolQueryAtom[2];

		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(0);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));

		for (int i = 0; i < 2; i++) {
			// if (aliphatic) c[i]= new
			// TopologySymbolQueryAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON),false);
			// else
			if (aliphatic)
				c[i] = new CHQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			else
				c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(c[i]);
			if (aliphatic)
				query.addBond(new TopologyOrderQueryBond(c[i], n,
						CDKConstants.BONDORDER_SINGLE, false));
			else
				query.addBond(new OrderQueryBond(c[i], n,
						CDKConstants.BONDORDER_SINGLE));
		}
		query.addAtom(n);
		query.addAtom(h);
		query.addBond(new OrderQueryBond(n, h, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer tertiaryAmine() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(TERTIARY_AMINE);
		InverseSymbolSetQueryAtom[] r = new InverseSymbolSetQueryAtom[3];
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		for (int i = 0; i < 3; i++) {
			r[i] = new InverseSymbolSetQueryAtom();
			r[i].setSymbol("*");
			r[i].addSymbol("H");

			query.addAtom(r[i]);
			query.addBond(new OrderQueryBond(r[i], n,
					CDKConstants.BONDORDER_SINGLE));
		}
		return query;
	}

	/*
	 * public static QueryAtomContainer amine(int order, boolean aliphatic) {
	 * QueryAtomContainer query = new QueryAtomContainer(); switch (order) {
	 * //primary case 1:{query.setID(PRIMARY_AMINE); break;} //secondary case
	 * 2:{query.setID(SECONDARY_AMINE); break;} //tertiary case 3:
	 * {query.setID(TERTIARY_AMINE); break;} //any default : {order=
	 * 0;query.setID(AMINE); break;} } InverseSymbolSetQueryAtom[] r = new
	 * InverseSymbolSetQueryAtom[3]; SymbolQueryAtom n = new
	 * SymbolQueryAtom(MoleculeTools
	 * .newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN)); for
	 * (int i =0; i < order; i++) { r[i] = new InverseSymbolSetQueryAtom();
	 * r[i].addSymbol("H"); query.addAtom(r[i]); query.addBond(new
	 * OrderQueryBond(r[i], n, CDKConstants.BONDORDER_SINGLE)); } return query;
	 * }
	 */
	// [N+].[Cl-]
	public static QueryAtomContainer hydrochlorideOfAmine(int amine) {
		// TODO verify the correct way of representation hydrochlorideOfAmine
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(HYDROCHLORIDE_OF_AMINE);
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		// SymbolQueryAtom n = new SymbolQueryAtom(a);
		query.addAtom(n);

		IAtom chlor = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CHLORINE);
		chlor.setFormalCharge(-1);
		SymbolAndChargeQueryAtom cl = new SymbolAndChargeQueryAtom(chlor);
		query.addAtom(cl);
		SymbolQueryAtom[] r = new SymbolQueryAtom[4];
		for (int i = 0; i < 4; i++) {

			if ((i + 1) > amine)
				r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(),
						Elements.HYDROGEN));
			else
				r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r[i]);
			query.addBond(new OrderQueryBond(n, r[i],
					CDKConstants.BONDORDER_SINGLE));
		}
		// TODO should verify somehow for ionic bond, not any ...
		query.addBond(new AnyOrderQueryBond(n, cl,
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer hydrochlorideOfAmine3() {
		// TODO verify the correct way of representation hydrochlorideOfAmine
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(HYDROCHLORIDE_OF_AMINE);
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		// SymbolQueryAtom n = new SymbolQueryAtom(a);
		query.addAtom(n);

		IAtom chlor = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CHLORINE);
		chlor.setFormalCharge(-1);
		SymbolAndChargeQueryAtom cl = new SymbolAndChargeQueryAtom(chlor);
		query.addAtom(cl);
		SymbolQueryAtom[] r = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {

			r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r[i]);
			if (i == 0)
				query.addBond(new OrderQueryBond(n, r[i],
						CDKConstants.BONDORDER_DOUBLE));
			else
				query.addBond(new OrderQueryBond(n, r[i],
						CDKConstants.BONDORDER_SINGLE));
		}
		// TODO should verify somehow for ionic bond, not any ...
		query.addBond(new AnyOrderQueryBond(n, cl,
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// [N+].[Cl-]

	public static QueryAtomContainer hydrochlorideOfAmineBreakable() {
		// TODO verify the correct way of representation hydrochlorideOfAmine
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(HYDROCHLORIDE_OF_AMINE);
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		query.addAtom(n);

		IAtom chlor = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CHLORINE);
		chlor.setFormalCharge(-1);

		SymbolAndChargeQueryAtom cl = new SymbolAndChargeQueryAtom(chlor);
		cl.setProperty(DONTMARK, query.getID());
		query.addAtom(cl);

		// TODO moze li da ima drugi elementi zakaceni za N
		SymbolSetQueryAtom[] r = new SymbolSetQueryAtom[2];
		for (int i = 0; i < r.length; i++) {
			r[i] = new SymbolSetQueryAtom();
			r[i].addSymbol("C");
			r[i].addSymbol("H");
			query.addAtom(r[i]);
			query.addBond(new SingleOrDoubleQueryBond(n, r[i]));
		}
		// TODO should verify somehow for ionic bond, not any ...
		query.addBond(new OrderQueryBond(n, cl, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// S(=O)(=O)(OH)[O-][N+]
	public static QueryAtomContainer sulphateOfAmine(int amine) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(SULPHATE_OF_AMINE);
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		query.addAtom(s);
		IQueryAtom[] o = new IQueryAtom[4];
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				IAtom a = MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.OXYGEN);
				a.setFormalCharge(-1);
				o[i] = new SymbolAndChargeQueryAtom(a);
			} else
				o[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
			query.addAtom((IAtom) o[i]);
			if (i < 2)
				query.addBond(new OrderQueryBond(s, o[i],
						CDKConstants.BONDORDER_SINGLE));
			else
				query.addBond(new OrderQueryBond(s, o[i],
						CDKConstants.BONDORDER_DOUBLE));
		}

		/*
		 * S-[O-].[N+] S-O S=O S=O
		 */
		/*
		 * TODO verify if this is necessary SymbolQueryAtom h = new
		 * SymbolQueryAtom
		 * (MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance
		 * (),Elements.HYDROGEN)); query.addAtom(h); query.addBond(new
		 * OrderQueryBond(h, o[1], CDKConstants.BONDORDER_SINGLE));
		 */
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		// SymbolQueryAtom n = new SymbolQueryAtom(a);
		query.addAtom(n);

		if (amine > 0) { // primary, sec, etc
			SymbolSetQueryAtom[] r = new SymbolSetQueryAtom[4];
			for (int i = 0; i < 4; i++) {
				r[i] = new SymbolSetQueryAtom();
				if ((i + 1) > amine)
					r[i].addSymbol("H");
				else
					r[i].addSymbol("C");
				query.addAtom(r[i]);
				query.addBond(new OrderQueryBond(n, r[i],
						CDKConstants.BONDORDER_SINGLE)); // [N+]-R or [N+]-H
			}
		}
		// TODO should verify somehow for ionic bond, not any ...
		query.addBond(new AnyOrderQueryBond(n, o[0],
				CDKConstants.BONDORDER_SINGLE)); // [N+]-[O-]
		return query;
	}

	/**
	 * This is {@link #sulphateOfAmine(int)} with the ionic bonnd between [N+]
	 * and [O-] marked with {@link #DONTMARK} so it can be broken by
	 * {@link #detachGroup(IAtomContainer, QueryAtomContainer)} method
	 * 
	 * @return QueryAtomContainer the query
	 */
	public static QueryAtomContainer sulphateOfAmineBreakable() {
		QueryAtomContainer aminoSulphate = FunctionalGroups.sulphateOfAmine(0);
		// find [N+].[O-] bond and mark it as DONTMARK
		for (int i = 0; i < aminoSulphate.getBondCount(); i++) {
			Iterator ai = aminoSulphate.getBond(i).atoms().iterator();
			IAtom n = null;
			IAtom o = null;
			while (ai.hasNext()) {
				IAtom a = (IAtom) ai.next();
				if (a.getSymbol().equals("N"))
					n = a;
				else if (a.getSymbol().equals("O"))
					o = a;
			}

			if ((n != null) && (o != null)) { // this is the bond
				n.setProperty(FunctionalGroups.DONTMARK, aminoSulphate.getID());
				logger.fine("Ma");
				break;
			}
		}
		return aminoSulphate;
	}

	/**
	 * SMARTS: C(#N)([C,#1])
	 * 
	 * @return
	 */
	public static QueryAtomContainer cyano() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CYANO);

		SymbolSetQueryAtom a = new SymbolSetQueryAtom();
		a.addSymbol("N");
		a.addSymbol("C");
		a.addSymbol("H");
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));

		query.addAtom(a);
		query.addAtom(c);
		query.addAtom(n);
		query.addBond(new OrderQueryBond(a, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, n, CDKConstants.BONDORDER_TRIPLE));
		return query;
	}

	/**
	 * SMARTS: N(=O)(=O)([!#1])
	 * 
	 * @return
	 */
	public static QueryAtomContainer nitro2double() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(NITRO);
		InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();
		r.addSymbol("H");
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		query.addAtom(r);
		query.addAtom(n);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n, o1, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(n, o2, CDKConstants.BONDORDER_DOUBLE));
		return query;
	}

	/**
	 * SMARTS: [N+](=O)([O-])([!#1])
	 * 
	 * @return
	 */
	public static QueryAtomContainer nitro1double() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(NITRO);
		InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();

		r.addSymbol("H");
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(+1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);

		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		IAtom o = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		o.setFormalCharge(-1);
		SymbolAndChargeQueryAtom o2 = new SymbolAndChargeQueryAtom(o);

		query.addAtom(r);
		query.addAtom(n);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n, o1, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(n, o2, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	/**
	 * SMARTS: O=NN([!#1])([!#1])
	 * 
	 * @return
	 */
	public static QueryAtomContainer Nnitroso() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(NNITROSO);
		InverseSymbolSetQueryAtom r1 = new InverseSymbolSetQueryAtom();
		r1.addSymbol("H");
		InverseSymbolSetQueryAtom r2 = new InverseSymbolSetQueryAtom();
		r2.addSymbol("H");
		SymbolQueryAtom n1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom n2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		query.addAtom(r1);
		query.addAtom(r2);
		query.addAtom(n1);
		query.addAtom(n2);
		query.addAtom(o);
		query.addBond(new OrderQueryBond(r1, n1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r2, n1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n1, n2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n2, o, CDKConstants.BONDORDER_DOUBLE));
		return query;
	}

	/*
	 * SMARTS: N#N
	 */
	public static QueryAtomContainer diAzo() {
		SymbolQueryAtom n1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom n2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(DIAZO);
		query.addAtom(n1);
		query.addAtom(n2);
		query.addBond(new OrderQueryBond(n1, n2, CDKConstants.BONDORDER_TRIPLE));
		return query;
	}

	/**
	 * SMARTS: [#6][#7]=[#7][#7;H2]
	 * 
	 * @return
	 */
	public static QueryAtomContainer triAzeno() {

		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(TRIAZENO);

		SymbolQueryAtom r = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		r.setProperty(DONTMARK, query.getID());
		SymbolQueryAtom n1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom n2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom n3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));

		query.addAtom(r);
		query.addAtom(n1);
		query.addAtom(n2);
		query.addAtom(n3);
		query.addAtom(h1);
		query.addAtom(h2);
		query.addBond(new OrderQueryBond(r, n1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n1, n2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(n2, n3, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n3, h1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(n3, h2, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer quaternaryNitrogen1(boolean charged) {

		ReallyAnyAtom r1 = new ReallyAnyAtom();
		ReallyAnyAtom r2 = new ReallyAnyAtom();
		ReallyAnyAtom r3 = new ReallyAnyAtom();
		ReallyAnyAtom r4 = new ReallyAnyAtom();

		// SymbolQueryAtom n = new
		// SymbolQueryAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		IQueryAtom n;
		if (charged) {
			a.setFormalCharge(1);
			n = new SymbolAndChargeQueryAtom(a);
		} else
			n = new SymbolQueryAtom(a);

		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(QUATERNARY_NITROGEN);
		query.addAtom(r1);
		query.addAtom(r2);
		query.addAtom(r3);
		query.addAtom(r4);
		query.addAtom((IAtom) n);
		query.addBond(new OrderQueryBond(r1, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r2, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r3, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r4, n, CDKConstants.BONDORDER_SINGLE));

		return query;
	}

	public static QueryAtomContainer quaternaryNitrogen2(boolean charged) {

		ReallyAnyAtom r1 = new ReallyAnyAtom();
		ReallyAnyAtom r2 = new ReallyAnyAtom();
		ReallyAnyAtom r3 = new ReallyAnyAtom();

		// SymbolQueryAtom n = new
		// SymbolQueryAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		IQueryAtom n;
		if (charged) {
			a.setFormalCharge(1);
			n = new SymbolAndChargeQueryAtom(a);
		} else
			n = new SymbolQueryAtom(a); // else to cope with wrong record

		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(QUATERNARY_NITROGEN);
		query.addAtom(r1);
		query.addAtom(r2);
		query.addAtom(r3);
		query.addAtom((IAtom) n);
		query.addBond(new OrderQueryBond(r1, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r2, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r3, n, CDKConstants.BONDORDER_DOUBLE));

		return query;
	}

	public static QueryAtomContainer quarternaryNitrogenException() {
		ReallyAnyAtom nr1 = new ReallyAnyAtom();
		ReallyAnyAtom nr2 = new ReallyAnyAtom();
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		a.setFormalCharge(1);
		SymbolAndChargeQueryAtom n = new SymbolAndChargeQueryAtom(a);
		InverseSymbolSetQueryAtom cr1 = new InverseSymbolSetQueryAtom();
		cr1.addSymbol("H");
		InverseSymbolSetQueryAtom cr2 = new InverseSymbolSetQueryAtom();
		cr2.addSymbol("H");

		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(QUATERNARY_NITROGEN_EXCEPTION);
		query.addAtom(nr1);
		query.addAtom(nr2);
		query.addAtom(cr1);
		query.addAtom(cr2);
		query.addAtom(n);
		query.addAtom(c);
		query.addAtom(n);
		query.addBond(new OrderQueryBond(nr1, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(nr2, n, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, n, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(cr1, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(cr2, c, CDKConstants.BONDORDER_SINGLE));

		return query;
	}

	// R-C(=O)-OH
	public static QueryAtomContainer carboxylicAcid() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID);
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(r);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addAtom(h);
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(h, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));
		return query;
	}

	public static QueryAtomContainer acyclic_acetal() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ACETAL);
		SymbolQueryAtom[] c = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {
			c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(c[i]);
		}
		SymbolQueryAtom[] o = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			o[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
			query.addAtom(o[i]);
		}
		query.addBond(new TopologyOrderQueryBond(c[0], o[0],
				CDKConstants.BONDORDER_SINGLE, false));
		query.addBond(new TopologyOrderQueryBond(c[1], o[0],
				CDKConstants.BONDORDER_SINGLE, false));
		query.addBond(new TopologyOrderQueryBond(c[1], o[1],
				CDKConstants.BONDORDER_SINGLE, false));
		query.addBond(new TopologyOrderQueryBond(c[2], o[1],
				CDKConstants.BONDORDER_SINGLE, false));
		return query;

	}

	// -C-O-C-O-C-
	public static QueryAtomContainer acetal() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ACETAL);
		SymbolQueryAtom[] c = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {
			c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(c[i]);
		}
		SymbolQueryAtom[] o = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			o[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
			query.addAtom(o[i]);
		}
		query.addBond(new OrderQueryBond(c[0], o[0],
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c[1], o[0],
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c[1], o[1],
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c[2], o[1],
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// C-O-C have to check if substituted
	public static QueryAtomContainer ether() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ETHER);
		SymbolQueryAtom[] r = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			r[i] = new CHQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			// r[i].setProperty(DONTMARK,query.getID());
			query.addAtom(r[i]);
		}
		// TotalValencyAtom s = new TotalValencyAtom(2);
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(s);
		query.addBond(new OrderQueryBond(r[0], s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r[1], s, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// C-O-C have to check if substituted
	public static QueryAtomContainer alkoxy() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ETHER);
		SymbolQueryAtom[] r = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r[i]);
		}
		// TotalValencyAtom s = new TotalValencyAtom(2);
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(s);
		query.addBond(new OrderQueryBond(r[0], s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r[1], s, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// C-O-C have to check if substituted
	public static QueryAtomContainer methylether() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(METHYLETHER);

		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(o);
		SymbolQueryAtom[] r = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r[i]);
			query.addBond(new OrderQueryBond(r[i], o,
					CDKConstants.BONDORDER_SINGLE));
		}
		SymbolQueryAtom[] h = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {
			h[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
			query.addAtom(h[i]);
			query.addBond(new OrderQueryBond(h[i], r[0],
					CDKConstants.BONDORDER_SINGLE));
		}
		return query;
	}

	// C-S-C
	public static QueryAtomContainer sulphide() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(SULPHIDE);
		SymbolQueryAtom[] r = new SymbolQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			r[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r[i]);
		}
		// TotalValencyAtom s = new TotalValencyAtom(2);
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		query.addAtom(s);
		query.addBond(new OrderQueryBond(r[0], s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(r[1], s, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// R-SH
	public static QueryAtomContainer mercaptan() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(MERCAPTAN);
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));

		query.addAtom(s);
		query.addAtom(c);
		query.addAtom(h);
		query.addBond(new OrderQueryBond(c, s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(s, h, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// R-S-C(=O)-R'
	// R-S-C(=S)-R'
	public static QueryAtomContainer thioester() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(THIOESTER);
		InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();
		r.addSymbol("H");
		r.addSymbol("S");
		r.addSymbol("O");
		r.setProperty(DONTMARK, query.getID());

		InverseSymbolSetQueryAtom e = new InverseSymbolSetQueryAtom();
		e.addSymbol("H");
		e.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		SymbolSetQueryAtom o2 = new SymbolSetQueryAtom();
		o2.addSymbol("O");
		o2.addSymbol("S");

		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(s);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(s, e, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// RC(=O)OR
	public static QueryAtomContainer ester() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ESTER);
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setProperty(DONTMARK, query.getID());
		InverseSymbolSetQueryAtom e = new InverseSymbolSetQueryAtom();
		e.addSymbol("H");
		e.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(o1, e, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// ROC(=O)OR
	public static QueryAtomContainer carbonate() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBONATE);
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setProperty(DONTMARK, query.getID());
		InverseSymbolSetQueryAtom e = new InverseSymbolSetQueryAtom();
		e.addSymbol("H");
		e.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addAtom(o3);
		query.addBond(new OrderQueryBond(o3, r, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o3, c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(o1, e, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer anhydride() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ANHYDRIDE);

		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		SymbolQueryAtom r = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom e = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new TopologyAnyBond(r, c, true));
		query.addBond(new TopologyOrderQueryBond(c, o1,
				CDKConstants.BONDORDER_SINGLE, true));
		query.addBond(new TopologyOrderQueryBond(c, o2,
				CDKConstants.BONDORDER_DOUBLE, false));
		query.addBond(new TopologyAnyBond(o1, e, true));

		query.addBond(new TopologyOrderQueryBond(e, o,
				CDKConstants.BONDORDER_DOUBLE, false));
		return query;
	}

	/**
	 * 
	 * @param abUnsaturated
	 *            if true, this will be alpha -beta unsaturated lactone
	 * @return
	 */
	public static QueryAtomContainer lactone(boolean abUnsaturated) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(LACTONE);

		SymbolQueryAtom r = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom e = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new TopologyAnyBond(r, c, true));
		query.addBond(new TopologyOrderQueryBond(c, o1,
				CDKConstants.BONDORDER_SINGLE, true));
		query.addBond(new TopologyOrderQueryBond(c, o2,
				CDKConstants.BONDORDER_DOUBLE, false));
		query.addBond(new TopologyAnyBond(o1, e, true));

		if (abUnsaturated) {
			SymbolQueryAtom ab = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			ab.setProperty(DONTMARK, query.getID());
			query.addAtom(ab);
			query.addBond(new TopologyOrderQueryBond(r, ab,
					CDKConstants.BONDORDER_DOUBLE, true));
		}

		return query;
	}

	public static QueryAtomContainer lactoneBreakable() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(LACTONE);
		SymbolQueryAtom r = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		// AnyAtom r = new AnyAtom();
		SymbolQueryAtom e = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		e.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(e);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new TopologyOrderQueryBond(r, c,
				CDKConstants.BONDORDER_SINGLE, true));
		query.addBond(new TopologyOrderQueryBond(c, o1,
				CDKConstants.BONDORDER_SINGLE, true));
		query.addBond(new TopologyOrderQueryBond(c, o2,
				CDKConstants.BONDORDER_DOUBLE, false));
		TopologyOrderQueryBond b = new TopologyOrderQueryBond(o1, e,
				CDKConstants.BONDORDER_SINGLE, true);
		b.setProperty(DONTMARK, query.getID());
		query.addBond(b);
		return query;
	}

	public static boolean isCyclicDiester(IAtomContainer mol, IRingSet rings) {
		QueryAtomContainer query = lactone(false);
		query.setID(CYCLIC_DIESTER);
		List list = getUniqueBondMap(mol, query, false);

		ArrayList esterGroups = new ArrayList();
		/*
		 * Count ester groups per ring, should be exactly 2 Use the fact that
		 * bond map marks each ester group with unique identifier
		 */
		if ((list != null) && (list.size() >= 2)) { // can be
			markMaps(mol, query, list);
			for (int i = 0; i < rings.getAtomContainerCount(); i++) {
				IRing ring = (IRing) rings.getAtomContainer(i);
				esterGroups.clear();
				for (int b = 0; b < ring.getBondCount(); b++) {
					Object o = ring.getBond(b).getProperty(query.getID());
					if (o == null)
						continue;
					else if (!esterGroups.contains(o))
						esterGroups.add(o);
				}
				if (esterGroups.size() == 2) {
					logger.fine("Ring with two ester groups\tNO");
					for (int a = 0; a < ring.getAtomCount(); a++)
						if (ring.getAtom(a).getSymbol().equals("C"))
							continue;
						else if (ring.getAtom(a).getSymbol().equals("O")) {
							if (ring.getAtom(a).getProperty(query.getID()) == null) {
								// O atom only allowed if from ester group
								logger.fine("Heteroring\tYES");
								return false;
							}
						} else {
							logger.fine("Heteroring\tYES");
							return false;
						}
					logger.fine("Cyclic diester\tYES");
					return true;
				}
			}
			logger.fine("Ring with two ester groups\tNO");
			return false;
		} else {
			logger.fine("Less than 2 ester groups ");
			return false;
		}
	}

	public static QueryAtomContainer isopreneUnit() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ISOPRENE);
		SymbolQueryAtom[] c = new SymbolQueryAtom[5];
		for (int i = 0; i < c.length; i++) {
			c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(c[i]);

		}
		for (int i = 1; i < c.length - 1; i++)
			query.addBond(new SingleOrDoubleQueryBond(c[i - 1], c[i]));
		query.addBond(new OrderQueryBond(c[1], c[4],
				CDKConstants.BONDORDER_SINGLE));

		return query;
	}

	// CC(=O)C
	public static QueryAtomContainer ketone() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(KETONE);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		InverseSymbolSetQueryAtom[] r = new InverseSymbolSetQueryAtom[2];
		for (int i = 0; i < 2; i++) {
			r[i] = new InverseSymbolSetQueryAtom();
			r[i].addSymbol("H");
			r[i].addSymbol("O");
			r[i].addSymbol("S");
			r[i].addSymbol("N");
			r[i].setProperty(DONTMARK, query.getID());
			query.addAtom(r[i]);
		}

		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(o);
		query.addBond(new OrderQueryBond(r[0], c, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(r[1], c, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer ketone_ring() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(KETONE);
		SymbolQueryAtom[] c = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {
			c[i] = new TopologySymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON),
					true);
			query.addAtom(c[i]);
		}

		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(o);
		query.addBond(new OrderQueryBond(c[0], c[1],
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c[1], o, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(c[1], c[2],
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer sidechain_ketone() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(KETONE_SIDECHAIN);
		SymbolQueryAtom[] c = new SymbolQueryAtom[3];
		for (int i = 0; i < 3; i++) {
			if (i == 1)
				c[i] = new TopologySymbolQueryAtom(
						MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.CARBON), false);
			else
				c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(c[i]);
		}

		SymbolQueryAtom o = new TopologySymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN), false);
		query.addAtom(o);
		query.addBond(new OrderQueryBond(c[0], c[1],
				CDKConstants.BONDORDER_SINGLE));
		query.addBond(new TopologyOrderQueryBond(c[1], o,
				CDKConstants.BONDORDER_DOUBLE, false));
		query.addBond(new OrderQueryBond(c[1], c[2],
				CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	/**
	 * RC(=O)H An aldehyde is either a functional group consisting of a terminal
	 * carbonyl group, or a compound containing a terminal carbonyl group.
	 * (Where -R represents the carbon chain.)
	 */
	public static QueryAtomContainer aldehyde() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ALDEHYDE);
		/*
		 * InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();
		 * //r.addSymbol("H"); ne moze da poznae formaldehyde r.addSymbol("O");
		 * //shte bude ester ako e O
		 */
		SymbolSetQueryAtom r = new SymbolSetQueryAtom();
		r.addSymbol("C");
		r.addSymbol("H");
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(r);
		query.addAtom(c);
		query.addAtom(o);
		query.addAtom(h);
		query.addBond(new OrderQueryBond(c, h, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(c, o, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// C=O
	public static QueryAtomContainer carbonyl() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBONYL);
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(c);
		query.addAtom(o);
		query.addBond(new OrderQueryBond(c, o, CDKConstants.BONDORDER_DOUBLE));
		return query;
	}

	public static QueryAtomContainer ab_unsaturated_carbonyl() {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBONYL_ABUNSATURATED);
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(c);
		query.addAtom(o);
		query.addBond(new OrderQueryBond(c, o, CDKConstants.BONDORDER_DOUBLE));
		SymbolQueryAtom c1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		query.addBond(new OrderQueryBond(c, c1, CDKConstants.BONDORDER_SINGLE));
		SymbolQueryAtom c2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		query.addBond(new OrderQueryBond(c2, c1, CDKConstants.BONDORDER_DOUBLE));
		ReallyAnyAtom a = new ReallyAnyAtom();
		query.addBond(new AnyOrderQueryBond(c2, a,
				CDKConstants.BONDORDER_DOUBLE));
		a.setProperty(DONTMARK, query.getID());
		return query;
	}

	/**
	 * 
	 * @param aliphatic
	 *            - if true looks for aliphatic alcohols, if not for any
	 *            alcohols
	 * @return
	 */
	public static QueryAtomContainer alcohol(boolean aliphatic) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(ALCOHOL);
		CHQueryAtom r;
		if (aliphatic) {
			r = new AliphaticCHQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom(r);
		} else {
			r = new CHQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			query.addAtom((SymbolQueryAtom) r);
		}
		r.setProperty(DONTMARK, query.getID());
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(o);
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		query.addAtom(h);
		query.addBond(new OrderQueryBond(r, o, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o, h, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// HNS(=O)(=O)(O-Me)N

	public static QueryAtomContainer sulphamate(String[] metals) {
		SymbolSetQueryAtom me = null;
		if (metals != null) {
			me = new SymbolSetQueryAtom();
			for (int i = 0; i < metals.length; i++)
				me.addSymbol(metals[i]);
		}
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(SULPHAMATE);
		IElement atoms[] = { Elements.SULFUR, Elements.NITROGEN,
				Elements.OXYGEN, Elements.OXYGEN, Elements.OXYGEN,
				Elements.HYDROGEN };
		SymbolQueryAtom[] a = new SymbolQueryAtom[6];
		for (int i = 0; i < (atoms.length - 1); i++) {
			a[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), atoms[i]));
			q.addAtom(a[i]);
		}
		q.addBond(new OrderQueryBond(a[0], a[1], CDKConstants.BONDORDER_SINGLE));// S-N
		q.addBond(new OrderQueryBond(a[0], a[2], CDKConstants.BONDORDER_DOUBLE));// S=O
		q.addBond(new OrderQueryBond(a[0], a[3], CDKConstants.BONDORDER_DOUBLE));// S=O
		q.addBond(new OrderQueryBond(a[0], a[4], CDKConstants.BONDORDER_SINGLE));// S-O
		// q.addBond(new OrderQueryBond(a[1], a[5],
		// CDKConstants.BONDORDER_SINGLE));//N-H
		if (me != null)
			q.addBond(new OrderQueryBond(a[4], me,
					CDKConstants.BONDORDER_SINGLE));// O-Me
		return q;

	}

	// R-C(=O)O-Me
	public static QueryAtomContainer saltOfCarboxylicAcid(String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);

		SymbolSetQueryAtom r = new SymbolSetQueryAtom();
		r.setSymbol("*");
		r.addSymbol("C");
		r.addSymbol("H");
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));// R-C
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));// C=O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	// C[O-] Me+
	public static QueryAtomContainer saltOfCarboxylicAcid1(String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		a.setFormalCharge(-1);
		SymbolAndChargeQueryAtom o1 = new SymbolAndChargeQueryAtom(a);
		query.addAtom(c);
		query.addAtom(o1);

		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	// C[O-] Me+
	public static QueryAtomContainer saltOfCarboxylicAcid2(String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		a.setFormalCharge(-1);
		SymbolAndChargeQueryAtom o1 = new SymbolAndChargeQueryAtom(a);
		query.addAtom(c);
		query.addAtom(o1);

		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	public static QueryAtomContainer saltOfCarboxylicAcidBreakable(
			String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);
		m.setProperty(DONTMARK, query.getID());

		SymbolSetQueryAtom r = new SymbolSetQueryAtom();
		r.setSymbol("*");
		r.addSymbol("C");
		r.addSymbol("H");

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(c);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));// R-C
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));// C=O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	// C-[O-] Me+
	public static QueryAtomContainer saltOfCarboxylicAcidBreakable1(
			String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);
		m.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		a.setFormalCharge(-1);
		SymbolAndChargeQueryAtom o1 = new SymbolAndChargeQueryAtom(a);

		query.addAtom(c);
		query.addAtom(o1);
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	public static QueryAtomContainer saltOfCarboxylicAcidBreakable2(
			String[] metals) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(CARBOXYLIC_ACID_SALT);
		SymbolSetQueryAtom m = new SymbolSetQueryAtom();
		for (int i = 0; i < metals.length; i++)
			m.addSymbol(metals[i]);
		m.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		IAtom a = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		a.setFormalCharge(-1);
		SymbolAndChargeQueryAtom o1 = new SymbolAndChargeQueryAtom(a);

		query.addAtom(c);
		query.addAtom(o1);
		query.addBond(new OrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE));// C-O
		query.addBond(new OrderQueryBond(o1, m, CDKConstants.BONDORDER_SINGLE));// O-Me
		return query;
	}

	// RS(=O)(=O)O
	public static QueryAtomContainer sulphonate(String[] metals) {
		return sulphonate(metals, true);
	}

	public static QueryAtomContainer sulphonate(String[] metals, boolean bonded) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(SULPHONATE);
		SymbolSetQueryAtom m = null;
		if (metals != null) {
			m = new SymbolSetQueryAtom();
			for (int i = 0; i < metals.length; i++)
				m.addSymbol(metals[i]);
			query.addAtom(m);
		}
		// TODO moze li da ima izob sto atom razlicen ot C na towa miasto
		InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();
		r.addSymbol("O");
		r.addSymbol("H");
		r.addSymbol("N");

		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		query.addAtom(r);
		query.addAtom(s);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addAtom(o3);
		query.addBond(new OrderQueryBond(r, s, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(s, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(s, o2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(s, o3, CDKConstants.BONDORDER_DOUBLE));
		if ((m != null) && bonded)
			query.addBond(new OrderQueryBond(o1, m,
					CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// [setOfAtoms]-OS(O-subst)(=O)(=O)O
	public static QueryAtomContainer sulphate(String[] setOfAtoms) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(SULPHATE);
		SymbolSetQueryAtom m = null;
		if (setOfAtoms != null) {
			m = new SymbolSetQueryAtom();
			for (int i = 0; i < setOfAtoms.length; i++)
				m.addSymbol(setOfAtoms[i]);
		}
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom s = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.SULFUR));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o4 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		query.addAtom(s);
		query.addAtom(r);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addAtom(o3);

		query.addBond(new OrderQueryBond(s, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(s, o2, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(s, o3, CDKConstants.BONDORDER_DOUBLE));
		query.addBond(new OrderQueryBond(s, o4, CDKConstants.BONDORDER_SINGLE));
		if (m != null)
			query.addBond(new OrderQueryBond(o1, m,
					CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o4, r, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	public static QueryAtomContainer phosphate(String[] setOfAtoms) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(PHOSPHATE);
		SymbolSetQueryAtom m = null;
		if (setOfAtoms != null) {
			m = new SymbolSetQueryAtom();
			for (int i = 0; i < setOfAtoms.length; i++)
				m.addSymbol(setOfAtoms[i]);
		}
		ReallyAnyAtom r = new ReallyAnyAtom();
		r.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom p = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.PHOSPHORUS));
		SymbolQueryAtom o1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		// o1.setCharge(-1.0);
		SymbolQueryAtom o2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom o3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));

		query.addAtom(p);
		query.addAtom(r);
		query.addAtom(o1);
		query.addAtom(o2);
		query.addAtom(o3);

		query.addBond(new OrderQueryBond(p, o1, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(p, o2, CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(p, o3, CDKConstants.BONDORDER_DOUBLE));

		if (m != null)
			query.addBond(new OrderQueryBond(o1, m,
					CDKConstants.BONDORDER_SINGLE));
		query.addBond(new OrderQueryBond(o2, r, CDKConstants.BONDORDER_SINGLE));
		return query;
	}

	// polyoxyethylene
	public static QueryAtomContainer polyoxyethylene(int n) {
		QueryAtomContainer query = new QueryAtomContainer();
		query.setID(POLYOXYETHYLENE);
		ReallyAnyAtom a1 = new ReallyAnyAtom();
		a1.setProperty(DONTMARK, query.getID());
		ReallyAnyAtom a2 = new ReallyAnyAtom();
		a2.setProperty(DONTMARK, query.getID());

		SymbolQueryAtom o;
		SymbolQueryAtom[] c = new SymbolQueryAtom[2];
		SymbolQueryAtom[] h = new SymbolQueryAtom[4];
		for (int i = 0; i < n; i++) {
			o = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
			query.addAtom(o);
			if (i > 0)
				query.addBond(new OrderQueryBond(o, c[1],
						CDKConstants.BONDORDER_SINGLE));
			else
				query.addBond(new OrderQueryBond(o, a1,
						CDKConstants.BONDORDER_SINGLE));
			for (int j = 0; j < 2; j++) {
				c[j] = new SymbolQueryAtom(MoleculeTools.newAtom(
						SilentChemObjectBuilder.getInstance(), Elements.CARBON));
				query.addAtom(c[j]);
				for (int j1 = 0; j1 < 2; j1++) {
					h[j * 2 + j1] = new SymbolQueryAtom(MoleculeTools.newAtom(
							SilentChemObjectBuilder.getInstance(),
							Elements.HYDROGEN));
					query.addAtom(h[j * 2 + j1]);
					query.addBond(new OrderQueryBond(c[j], h[j * 2 + j1],
							CDKConstants.BONDORDER_SINGLE));
				}
			}
			query.addBond(new OrderQueryBond(o, c[0],
					CDKConstants.BONDORDER_SINGLE));
			query.addBond(new OrderQueryBond(c[0], c[1],
					CDKConstants.BONDORDER_SINGLE));
			if (i == (n - 1))
				query.addBond(new OrderQueryBond(a2, c[1],
						CDKConstants.BONDORDER_SINGLE));
		}
		return query;

	}

	public static IAtomContainer cloneDiscardRingAtomAndBonds(
			IAtomContainer ac, IRing ring) {
		IAtomContainer result = new org.openscience.cdk.AtomContainer();
		Hashtable table = new Hashtable();
		for (int i = 0; i < ac.getAtomCount(); i++) {
			IAtom a = ac.getAtom(i);
			if (ring.contains(a)) {
				// if (!a.getSymbol().equals("C")) continue;
				continue;
			}
			// Atom newAtom = (Atom) a.clone();
			table.put(a, a);
			result.addAtom(a);
		}

		for (int i = 0; i < ac.getBondCount(); i++) {
			IBond b = ac.getBond(i);

			IAtom a1 = (IAtom) table.get(b.getAtom(0));
			if (a1 == null)
				continue;
			IAtom a2 = (IAtom) table.get(b.getAtom(1));
			if (a2 == null)
				continue;
			// Bond newBond = MoleculeTools.newBond(builder,a1, a2,
			// b.getOrder());
			// newBond.setFlag(CDKConstants.ISAROMATIC,b.getFlag(CDKConstants.ISAROMATIC));
			// if (ring.contains(a1) && ring.contains(a2)) continue;
			result.addBond(b);
		}
		return result;
	}

	/**
	 * 
	 * @param mol
	 *            - The molecule to be searched {@link AtomContainer}
	 * @param q
	 *            - The query {@link AtomContainer}
	 * @param isPreprocessed
	 *            - whether the molecule was preprocessed by
	 *            {@link #preProcess(IAtomContainer)}
	 * @return List {@link List}
	 */
	public static List getBondMap(IAtomContainer mol, IAtomContainer q,
			boolean isPreprocessed) {
		logger.fine("getBondMap\t" + q.getID());
		List list = null;
		if (!isPreprocessed && needsPreprocessing(q)) {
			preProcess(mol);
			isPreprocessed = true;
		}
		try {
			UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
			list = uit.getSubgraphMaps(mol, q);
			markMaps(mol, q, list);

		} catch (Exception x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			list = null;
		}

		return list;
	}

	protected static String getKeyFromMap(RMap rmap) {
		return rmap.getId1() + "-" + rmap.getId2();
	}

	protected static boolean isOverlapped(IAtomContainer mol, IAtomContainer q,
			List first) {
		String id = q.getID();
		boolean overlaped = false;
		for (int i = 0; i < first.size(); i++) {
			RMap rmap = (RMap) first.get(i);
			IBond b1 = ((IBond) mol.getBond(rmap.getId1()));
			IBond b2 = ((IBond) q.getBond(rmap.getId2()));

			Object o = b2.getProperty(DONTMARK);
			if ((o != null) && (o.equals(id)))
				continue;

			o = b1.getProperty(ALLOCATED);
			if ((o != null) && (!o.equals(id))) {
				logger.fine("Possible overlap of group\t" + id + "\tand\t"
						+ o.toString() + "\t");
				overlaped = true;
				break;
			}
		}
		return overlaped;

	}

	public static List getUniqueBondMap(IAtomContainer mol, IAtomContainer q,
			boolean isPreprocessed) {
		if (q.getAtomCount() > mol.getAtomCount()) {
			logger.fine("A query with more atoms than a molecule!");
			return null;
		}
		List list = null;
		// try {
		// this is bonds map
		if (!isPreprocessed && needsPreprocessing(q)) {
			preProcess(mol);
			isPreprocessed = true;
		}
		try {
			UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
			list = uit.getSubgraphMaps(mol, q);
		} catch (CDKException x) {
			list = null;
			logger.log(Level.SEVERE, x.getMessage(), x);
		}
		if ((list == null) || (list.size() == 0))
			logger.fine("getUniqueBondMap\t" + q.getID() + "\t"
					+ AbstractRule.MSG_NO);
		else
			logger.fine("getUniqueBondMap\t" + q.getID() + "\t"
					+ AbstractRule.MSG_YES);
		if (list.size() < 2)
			return list;
		// else delete redundant solutions
		TreeMap all = new TreeMap(); // to contain all redundant solutions
		TreeMap aMap = new TreeMap(); // current analyzed list of Rmap in string
										// form

		// this is to get rid of symmetric and overlaping solutions (if any)
		boolean[] keep = new boolean[list.size()];
		int[] mappedBonds = new int[mol.getBondCount()];
		for (int j = 0; j < mappedBonds.length; j++)
			mappedBonds[j] = -1;

		// TODO have to take care if ArrayList of RMap is returned instead of
		// ArrayList of ArrayList of RMap
		// logger.fine(FunctionalGroups.mapToString(mol));

		for (int j = list.size() - 1; j >= 0; j--)
			if (isOverlapped(mol, q, (List) list.get(j))) {
				logger.fine("Remove overlaped group " + q.getID());
				list.remove(j);
			}

		for (int j = 0; j < list.size(); j++) {
			List thisMap = (List) list.get(j);
			aMap.clear();
			for (int i = 0; i < thisMap.size(); i++) {
				RMap rmap = (RMap) thisMap.get(i);

				// IAtom[] a2 = ((IBond) q.getBond(rmap.getId2())).getAtoms();
				IBond b2 = (IBond) q.getBond(rmap.getId2());

				if (mappedBonds[rmap.getId1()] > 0) {
					// this bond is already mapped (i.e. overlapped patterns)
					// clear mark for bonds only in the current map
					for (int k = 0; k < mappedBonds.length; k++)
						if (mappedBonds[k] == 0)
							mappedBonds[k] = -1;
					aMap.clear();
					break;
				} else {
					// mark bonds in the current map

					if (b2.getAtom(0).getSymbol().equals("R"))
						continue;
					if (b2.getAtom(1).getSymbol().equals("R"))
						continue;

					/*
					 * Object o = a2[0].getProperty(DONTMARK); if (o != null)
					 * continue; o = a2[1].getProperty(DONTMARK); if (o != null)
					 * continue;
					 */

					mappedBonds[rmap.getId1()] = 0;
					// if (a2[0].getSymbol().equals("H")) continue;
					// if (a2[1].getSymbol().equals("H")) continue;

					aMap.put(getKeyFromMap(rmap), rmap);
				}
			}
			for (int k = 0; k < mappedBonds.length; k++)
				if (mappedBonds[k] == 0)
					mappedBonds[k] = 1;
			if (aMap.size() == 0) {
				keep[j] = false;
				continue;
			} else {
				String bondMap = aMap.keySet().toString();

				// logger.fine(bondMap);
				if (all.containsKey(bondMap))
					keep[j] = false;
				else {
					keep[j] = true;
					all.put(bondMap, thisMap);
				}
			}
		}
		for (int j = (list.size() - 1); j >= 0; j--)
			if (!keep[j])
				list.remove(j);

		return list;

	}

	public static int mark(IAtomContainer mol, ArrayList elements) {
		int hydroCarbonCount = 0;
		for (int i = 0; i < mol.getAtomCount(); i++) {
			IAtom a = mol.getAtom(i);
			if (elements.contains(a.getSymbol()))
				a.setProperty(a.getSymbol(), new Integer(i));
		}
		return hydroCarbonCount;
	}

	public static int markCHn(IAtomContainer mol) {
		List neighbors = null;
		int hydroCarbonCount = 0;
		for (int i = 0; i < mol.getAtomCount(); i++) {
			IAtom a = mol.getAtom(i);
			if (!a.getSymbol().equals("C"))
				continue;
			neighbors = mol.getConnectedAtomsList(a);

			int hydrogens = 0;
			int carbons = 0;
			for (int j = 0; j < neighbors.size(); j++) {
				IAtom n = (IAtom) neighbors.get(j);
				if (n.getSymbol().equals("H"))
					hydrogens++;
				else if (n.getSymbol().equals("C"))
					carbons++;
			}

			String key = "";
			switch (hydrogens) {
			case 0: {
				if (carbons == neighbors.size())
					key = C;
				break;
			}
			case 1: {
				key = CH;
				break;
			}
			case 2: {
				key = CH2;
				break;
			}
			case 3: {
				key = CH3;
				break;

			}
			default: {
			}
			}
			hydroCarbonCount++;
			a.setProperty(key, new Integer(hydroCarbonCount));
		}
		return hydroCarbonCount;
	}

	public static void markOneMap(IAtomContainer mol, IAtomContainer q,
			List first, int j) {
		String id = q.getID();
		for (int i = 0; i < first.size(); i++) {
			RMap rmap = (RMap) first.get(i);
			IBond b2 = q.getBond(rmap.getId2());
			// IAtom[] a2 = b2.getAtoms();

			// will not mark bonds that does not belong to the group but to the
			// connected fragments
			boolean markIt = true;
			for (int k = 0; k < b2.getAtomCount(); k++) {
				IAtom a2 = b2.getAtom(k);
				if (a2 instanceof ReallyAnyAtom) {
					markIt = false;
					break;
				}
				Object o = a2.getProperty(DONTMARK);
				if (o != null) {
					markIt = false;
					break;
				}
			}
			if (!markIt)
				continue; // will not mark this bond

			Integer m = new Integer(j + 1);
			IBond b1 = mol.getBond(rmap.getId1());
			b1.setProperty(id, m);
			b1.setProperty(ALLOCATED, id);

			for (int k = 0; k < b1.getAtomCount(); k++) {
				b1.getAtom(k).setProperty(id, m);
				b1.getAtom(k).setProperty(ALLOCATED, id);
			}
		}

	}

	public static void markMaps(IAtomContainer mol, IAtomContainer q, List list) {
		if (list == null)
			return;

		if ((list.size() > 0) && (list.get(0) instanceof RMap)) {
			markOneMap(mol, q, list, 1);
		} else
			for (int j = 0; j < list.size(); j++) {
				List first = (List) list.get(j);
				markOneMap(mol, q, first, j);
			}
	}

	public static void mapHydrocarbon(IAtomContainer mol) {
		for (int i = 0; i < mol.getAtomCount(); i++) {

		}
	}

	public static StringBuffer mapToString(IAtomContainer mol, String id) {
		StringBuffer b = new StringBuffer();

		b.append("Atoms\t");
		for (int i = 0; i < mol.getAtomCount(); i++) {
			Object o = mol.getAtom(i).getProperty(id);
			b.append((i + 1));
			b.append('\t');
			b.append(mol.getAtom(i).getSymbol());
			b.append('\t');
			b.append(id);
			b.append('\t');
			b.append(o);
			b.append('\t');
		}
		b.append("Bonds\t");
		for (int i = 0; i < mol.getBondCount(); i++) {
			Object o = mol.getBond(i).getProperty(id);
			b.append((i + 1));
			b.append('\t');
			b.append(mol.getBond(i).getAtom(0).getSymbol());
			b.append('-');
			b.append(mol.getBond(i).getAtom(1).getSymbol());
			b.append('\t');
			b.append(id);
			b.append('\t');
			b.append(o);
			b.append('\t');
		}
		return b;

	}

	public static StringBuffer mapToString(IAtomContainer mol, Collection id) {
		StringBuffer b = new StringBuffer();
		boolean[] f = null;
		IAtom a = null;
		for (int i = 0; i < mol.getAtomCount(); i++) {
			a = mol.getAtom(i);
			b.append((i + 1));
			b.append('\t');
			b.append(a.getSymbol());
			f = a.getFlags();
			for (int j = 0; j < f.length; j++)
				if (f[j])
					switch (j) {
					case (CDKConstants.ISALIPHATIC): {
						b.append("\taliphatic\t");
						break;
					}
					case (CDKConstants.ISAROMATIC): {
						b.append("\taromatic\t");
						break;
					}
					case (CDKConstants.ISINRING): {
						b.append("\tin ring\t");
						break;
					}
					}

			Iterator iterator = id.iterator();
			while (iterator.hasNext()) {
				Object anID = iterator.next();
				Object o = a.getProperty(anID);
				if (o != null) {
					b.append('\t');
					b.append(anID);
					b.append('\t');
					b.append(o);
				}
			}
			b.append('\t');
		}
		return b;
	}

	public static StringBuilder mapToString(IAtomContainer mol) {
		StringBuilder b = new StringBuilder();
		boolean[] f = null;
		for (int i = 0; i < mol.getAtomCount(); i++) {

			IAtom a = mol.getAtom(i);
			b.append((i + 1));
			b.append(')');
			b.append(a.getSymbol());

			if ((a.getFormalCharge() != null) && (a.getFormalCharge() != 0))
				b.append(a.getFormalCharge());
			b.append(' ');

			f = a.getFlags();
			for (int j = 0; j < f.length; j++)
				if (f[j])
					switch (j) {
					case (CDKConstants.ISALIPHATIC): {
						b.append("[aliphatic] ");
						break;
					}
					case (CDKConstants.ISAROMATIC): {
						b.append("[aromatic] ");
						break;
					}
					case (CDKConstants.ISINRING): {
						b.append("[in ring] ");
						break;
					}
					}

			Map h = a.getProperties();
			for (Iterator e = h.keySet().iterator(); e.hasNext();) {
				Object key = e.next();
				Object o = a.getProperty(key);
				if (o != null) {
					b.append('[');
					b.append(key);
					b.append("] ");
					b.append(o);
				}
			}

			b.append(' ');

		}
		b.append("\nBonds\t");
		for (int i = 0; i < mol.getBondCount(); i++) {
			b.append((i + 1));
			b.append('\t');
			b.append(mol.getBond(i).getAtom(0).getSymbol());
			b.append('-');
			b.append(mol.getBond(i).getAtom(1).getSymbol());
			b.append('\t');
			Map h = mol.getBond(i).getProperties();
			for (Iterator e = h.keySet().iterator(); e.hasNext();) {
				Object key = e.next();
				Object o = mol.getBond(i).getProperty(key);
				if (o != null) {
					b.append('[');
					b.append(key);
					b.append("] ");
					b.append(o);
				}
			}
			b.append('\t');
		}

		return b;
	}

	/*
	 * public static boolean isSubstructure(AtomContainer mol, AtomContainer
	 * query ) throws CDKException {
	 * logger.fine("Checking if has substructure \t",query.getID()); if
	 * (UniversalIsomorphismTester.isSubgraph(mol,query)) {
	 * logger.fine("Substructure FOUND \t",query.getID()); return true; } else {
	 * logger.fine("Substructure NOT found \t",query.getID()); return false; } }
	 */
	/**
	 * @param mol
	 * @param query
	 *            ArrayList of AtomContainer - - the list of substructures to be
	 *            searched for
	 * @return true if any of the fragments are a substructure of mol
	 * @throws CDKException
	 */
	public static boolean hasAnySubstructure(IAtomContainer mol, ArrayList query)
			throws CDKException {
		return hasAnySubstructure(mol, query, null);
	}

	public static boolean hasAnySubstructure(IAtomContainer mol,
			ArrayList query, IAtomContainer selected) throws CDKException {
		if ((query == null) || (query.size() == 0))
			return false;
		else
			for (int i = 0; i < query.size(); i++)
				if (FunctionalGroups.hasGroup(mol,
						(IAtomContainer) query.get(i), selected))
					return true;
		return false;
	}

	/**
	 * @param mol
	 * @param query
	 *            ArrayList of AtomContainer - - the list of substructures to be
	 *            searched for
	 * @return true if all of the fragments are a substructure of mol
	 * @throws CDKException
	 */
	public static boolean hasAllSubstructure(IAtomContainer mol,
			ArrayList query, boolean isPreprocessed) throws CDKException {
		if ((query == null) || (query.size() == 0))
			return false;
		else
			for (int i = 0; i < query.size(); i++) {
				IAtomContainer q = (IAtomContainer) query.get(i);
				if (!isPreprocessed && needsPreprocessing(q)) {
					preProcess(mol);
					isPreprocessed = true;
				}
				if (!FunctionalGroups.hasGroup(mol, q, false))
					return false;
			}
		return true;
	}

	protected static boolean needsPreprocessing(IAtomContainer query) {
		String id = query.getID();
		return (id != null)
				&& ((id.equals(ALCOHOL)) || (id.equals(ETHER))
						|| (id.equals(SECONDARY_AMINE_ALIPHATIC))
						|| (id.equals(SECONDARY_AMINE)) || (id.equals(LACTONE)));
	}

	public static void preProcess(IAtomContainer mol) {
		markCHn(mol);
	}

	public static boolean markUniqueBondMap(IAtomContainer mol,
			ArrayList query, boolean isPreprocessed) {
		if ((query == null) || (query.size() == 0))
			return false;
		else
			for (int i = 0; i < query.size(); i++) {
				QueryAtomContainer q = (QueryAtomContainer) query.get(i);
				if (!isPreprocessed && needsPreprocessing(q)) {
					preProcess(mol);
					isPreprocessed = true;
				}
				List list = FunctionalGroups.getUniqueBondMap(mol, q,
						isPreprocessed);
				FunctionalGroups.markMaps(mol, q, list);
			}
		return true;
	}

	public static boolean hasOnlyTheseGroups(IAtomContainer mol,
			QueryAtomContainers query, Collection ids, boolean isPreprocessed) {
		return hasOnlyTheseGroups(mol, query, ids, isPreprocessed, null);
	}

	public static boolean hasOnlyTheseGroups(IAtomContainer mol,
			QueryAtomContainers query, Collection ids, boolean isPreprocessed,
			IAtomContainer selected) {
		final String MSG = "Has only these groups\t";
		if ((query == null) || (query.size() == 0))
			return false;
		else {
			logger.fine("Checking if it has only these groups\t" + ids);
			List list = null;
			int found = 0;
			for (int i = 0; i < query.size(); i++) {
				IAtomContainer q = (IAtomContainer) query.get(i);
				list = FunctionalGroups
						.getUniqueBondMap(mol, q, isPreprocessed);
				if ((list == null) || (list.size() == 0))
					continue;
				FunctionalGroups.markMaps(mol, q, list);
				logger.fine(mapToString(mol).toString());
				if (hasMarkedOnlyTheseGroups(mol, ids, selected, null)) {
					logger.fine(MSG + ids + "\tYES");
					return true;
				} else
					found++;
			}
			if (logger.isLoggable(Level.FINE)) {
				logger.fine(mapToString(mol, ids).toString());
			}
			if (hasMarkedOnlyTheseGroups(mol, ids, selected, null)) {
				logger.fine(MSG + ids + "\tYES");
				return true;
			} else {
				logger.fine("Has only these groups\t" + ids + "\tNO");
				return false;
			}
		}
	}

	/**
	 * Has to be preprocessed with getBondMap or getUniqueBondMap and markMaps
	 * 
	 * @param mol
	 * @param id
	 *            Collection of functional group identifiers as used by the
	 *            procedures listed above
	 * @return true if mol contains only specified groups (i.e. there are no
	 *         unmarked atoms)
	 */

	public static boolean hasMarkedOnlyTheseGroups(IAtomContainer mol,
			Collection id) {
		return hasMarkedOnlyTheseGroups(mol, id, null, null);
	}

	public static boolean hasMarkedOnlyTheseGroups(IAtomContainer mol,
			Collection id, IAtomContainer selection,
			IAtomContainer selectionOther) {
		IAtom a = null;

		for (int i = 0; i < mol.getAtomCount(); i++) {
			a = mol.getAtom(i);
			if (a.getSymbol().equals("H"))
				continue;
			if (a.getSymbol().equals("C"))
				continue;
			Iterator iterator = id.iterator();
			boolean ok = false;
			while (iterator.hasNext()) {
				Object anID = iterator.next();
				Object o = a.getProperty(anID);

				if (o != null) {
					// this atom has at least one mark, no need to check for
					// more
					ok = true;
					break;
				}

			}
			if (!ok) {
				if (selectionOther != null)
					selectionOther.addAtom(a);
				logger.fine("Atom " + a.getSymbol()
						+ " belongs to a forbidden group");
				logger.fine(mapToString(mol, id).toString());
				return false; // at least one atom is unmarked
			} else if (selection != null)
				selection.addAtom(a);
		}
		logger.fine("No forbidden groups found");
		return true;
	}

	/*
	 * public static boolean hasOtherThanMarkedGroups(IAtomContainer
	 * mol,Collection id,IAtomContainer selection) { IAtom a = null; for (int i
	 * =0; i < mol.getAtomCount(); i++) { a = mol.getAtom(i); if
	 * (a.getSymbol().equals("H")) continue; Iterator iterator = id.iterator();
	 * boolean otherAtom = false; while(iterator.hasNext()) { Object anID =
	 * iterator.next(); Object o = a.getProperty(anID); if (o==null) { //this
	 * atom has at least one mark, no need to check for more otherAtom = true;
	 * break; } }
	 * 
	 * 
	 * if (otherAtom) { logger.fine("Atom "+ a.getSymbol() +
	 * " belongs to a forbidden group"); logger.fine(mapToString(mol,id)); if
	 * (selection != null) selection.addAtom(a); else return true; //at least
	 * one atom is unmarked } } logger.fine("No forbidden groups found"); return
	 * selection==null?false:selection.getAtomCount()>0; }
	 */
	public static boolean hasGroupMarked(IAtomContainer mol, String id) {
		for (int i = 0; i < mol.getAtomCount(); i++)
			if (mol.getAtom(i).getProperty(id) != null)
				return true;
		return false;
	}

	public static boolean hasGroupMarked(IAtomContainer mol, String id,
			IAtomContainer selection) {
		if (selection == null)
			return hasGroupMarked(mol, id);
		boolean ok = false;
		for (int i = 0; i < mol.getAtomCount(); i++)
			if (mol.getAtom(i).getProperty(id) != null) {
				ok = true;
				if (selection != null)
					selection.addAtom(mol.getAtom(i));
			}
		return ok;
	}

	public static boolean hasMarkedOnlyTheseGroups(IAtomContainer mol,
			QueryAtomContainers query) {
		IAtom a = null;

		for (int i = 0; i < mol.getAtomCount(); i++) {
			a = mol.getAtom(i);
			if (a.getSymbol().equals("H"))
				continue;
			Iterator iterator = query.iterator();
			boolean ok = false;
			while (iterator.hasNext()) {
				QueryAtomContainer q = (QueryAtomContainer) iterator.next();
				Object o = a.getProperty(q.getID());
				if (o != null) {
					// this atom has at least one mark, no need to check for
					// more
					ok = true;
					break;
				}
			}

			if (!ok) {

				logger.fine("Atom " + a.toString()
						+ " belongs to a forbidden group");

				return false; // at least one atom is unmarked
			}
		}
		logger.fine("No forbidden groups found");
		return true;
	}

	public static IAtomContainer createAtomContainer(String smiles, String id) {
		return createAtomContainer(smiles, false, id, null);
	}

	public static IAtomContainer createAtomContainer(String smiles, String id,
			SMILES_PARSER mode) {
		return createAtomContainer(smiles, false, id, mode);
	}

	public static IAtomContainer createAtomContainer(String smiles) {
		return createAtomContainer(smiles, (SMILES_PARSER) null);
	}

	public static IAtomContainer createAtomContainer(String smiles,
			SMILES_PARSER mode) {
		return createAtomContainer(smiles, false, smiles, mode);
	}

	public static IAtomContainer createAtomContainer(String smiles,
			boolean addHydrogens) {
		return createAtomContainer(smiles, addHydrogens, smiles);
	}

	public static IAtomContainer createAtomContainer(String smiles,
			boolean addHydrogens, String id) {
		return createAtomContainer(smiles, addHydrogens, id, null);
	}

	public static IAtomContainer createAtomContainer(String smiles,
			boolean addHydrogens, String id, SMILES_PARSER mode) {
		try {
			// logger.fine("Creating molecule from SMILES\t",smiles);
			SmilesParserWrapper sp = mode == null ? SmilesParserWrapper
					.getInstance(SMILES_PARSER.CDK) : SmilesParserWrapper
					.getInstance(mode);

			IAtomContainer mol = sp.parseSmiles(smiles);
			if ((mol != null) && (mol.getAtomCount() == 0))
				return null;

			if (addHydrogens)
				try {
					// logger.fine("Adding explicit hydrogens");
					AtomContainerManipulator
							.percieveAtomTypesAndConfigureAtoms(mol);
					h.addImplicitHydrogens(mol);
					HydrogenAdderProcessor
							.convertImplicitToExplicitHydrogens(mol);

					// h.addExplicitHydrogensToSatisfyValency((IAtomContainer)mol);
				} catch (InvalidSmilesException x) {
					logger.log(Level.SEVERE, x.getMessage(), x);
					return null;
				} catch (CDKException x) {
					logger.log(Level.SEVERE, x.getMessage(), x);
					return null;
				}

			mol.setID(id);
			sp = null;
			return mol;
		} catch (InvalidSmilesException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			return null;
		}
	}

	public static QueryAtomContainer createQuery(String smiles) {
		return createQuery(smiles, smiles);
	}

	public static QueryAtomContainer createQuery(String smiles, String id) {
		IAtomContainer mol = createAtomContainer(smiles, id, SMILES_PARSER.CDK);
		if (mol != null) {
			QueryAtomContainer q = QueryAtomContainerCreator
					.createBasicQueryContainer(mol);
			q.setID(id);
			return q;
		} else
			return null;
	}

	public static QueryAtomContainer vicinalDiKetone() {
		// TODO "[*]C(=O)C(=O)[*]"

		QueryAtomContainer q = new QueryAtomContainer();
		q.setID("vicinal diketone");
		SymbolQueryAtom a1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		q.addAtom(a1);
		SymbolQueryAtom a2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		q.addAtom(a2);
		InverseSymbolSetQueryAtom a3 = new InverseSymbolSetQueryAtom();
		a3.addSymbol("O");
		a3.addSymbol("S");
		q.addAtom(a3);
		SymbolQueryAtom a4 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		q.addAtom(a4);
		SymbolQueryAtom a5 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		q.addAtom(a5);
		InverseSymbolSetQueryAtom a6 = new InverseSymbolSetQueryAtom();
		a6.addSymbol("O");
		a6.addSymbol("S");
		q.addAtom(a6);
		OrderQueryBond b1 = new OrderQueryBond(a2, a1, IBond.Order.DOUBLE);
		q.addBond(b1);
		OrderQueryBond b2 = new OrderQueryBond(a3, a2, IBond.Order.SINGLE);
		q.addBond(b2);
		OrderQueryBond b3 = new OrderQueryBond(a4, a2, IBond.Order.SINGLE);
		q.addBond(b3);
		OrderQueryBond b4 = new OrderQueryBond(a5, a4, IBond.Order.DOUBLE);
		q.addBond(b4);
		OrderQueryBond b5 = new OrderQueryBond(a6, a4, IBond.Order.SINGLE);
		q.addBond(b5);
		return q;
	}

	public static QueryAtomContainer ketoneAttachedToTerminalVinyl() {
		QueryAtomContainer mol = new QueryAtomContainer();
		mol.setID("Ketone attached to terminal vinyl");
		SymbolQueryAtom c1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(c1);
		InverseSymbolSetQueryAtom r = new InverseSymbolSetQueryAtom();
		r.addSymbol("O");
		r.addSymbol("S");
		r.addSymbol("N");
		mol.addAtom(r);
		SymbolQueryAtom c2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(c2);
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		mol.addAtom(o);
		SymbolQueryAtom c3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(c3);
		SymbolQueryAtom h1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		mol.addAtom(h1);
		SymbolQueryAtom h2 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		mol.addAtom(h2);
		SymbolQueryAtom h3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		mol.addAtom(h3);
		OrderQueryBond b1 = new OrderQueryBond(r, c1, IBond.Order.SINGLE);
		mol.addBond(b1);
		OrderQueryBond b2 = new OrderQueryBond(c1, c2, IBond.Order.SINGLE);
		mol.addBond(b2);
		OrderQueryBond b3 = new OrderQueryBond(c1, o, IBond.Order.DOUBLE);
		mol.addBond(b3);
		OrderQueryBond b4 = new OrderQueryBond(c2, c3, IBond.Order.DOUBLE);
		mol.addBond(b4);
		OrderQueryBond b5 = new OrderQueryBond(c3, h1, IBond.Order.SINGLE);
		mol.addBond(b5);
		OrderQueryBond b6 = new OrderQueryBond(c3, h2, IBond.Order.SINGLE);
		mol.addBond(b6);
		OrderQueryBond b7 = new OrderQueryBond(c2, h3, IBond.Order.SINGLE);
		mol.addBond(b7);
		return mol;
	}

	public static QueryAtomContainer ketalAttachedToTerminalVinyl() {
		// TODO "[*]C(O)(O)C=C"
		QueryAtomContainer mol = new QueryAtomContainer();
		mol.setID("ketal attached to terminal vinyl");
		InverseSymbolSetQueryAtom r1 = new InverseSymbolSetQueryAtom();
		r1.addSymbol("O");
		r1.addSymbol("H");
		mol.addAtom(r1);
		InverseSymbolSetQueryAtom r2 = new InverseSymbolSetQueryAtom();
		r2.addSymbol("O");
		r2.addSymbol("H");
		mol.addAtom(r2);
		InverseSymbolSetQueryAtom r3 = new InverseSymbolSetQueryAtom();
		r3.addSymbol("O");
		r3.addSymbol("H");
		mol.addAtom(r3);

		SymbolQueryAtom a1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(a1);
		SymbolQueryAtom a3 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(a3);
		SymbolQueryAtom a4 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		mol.addAtom(a4);
		SymbolQueryAtom a5 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		mol.addAtom(a5);
		SymbolQueryAtom a6 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(a6);
		SymbolQueryAtom a9 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		mol.addAtom(a9);
		SymbolQueryAtom a10 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
		mol.addAtom(a10);
		OrderQueryBond b1 = new OrderQueryBond(r1, a1, IBond.Order.SINGLE);
		mol.addBond(b1);
		OrderQueryBond b2 = new OrderQueryBond(a1, a3, IBond.Order.SINGLE);
		mol.addBond(b2);
		OrderQueryBond b3 = new OrderQueryBond(a1, a4, IBond.Order.SINGLE);
		mol.addBond(b3);
		OrderQueryBond b4 = new OrderQueryBond(a1, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		OrderQueryBond b5 = new OrderQueryBond(a3, a6, IBond.Order.DOUBLE);
		mol.addBond(b5);
		OrderQueryBond b6 = new OrderQueryBond(a4, r2, IBond.Order.SINGLE);
		mol.addBond(b6);
		OrderQueryBond b7 = new OrderQueryBond(a5, r3, IBond.Order.SINGLE);
		mol.addBond(b7);
		OrderQueryBond b8 = new OrderQueryBond(a6, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		OrderQueryBond b9 = new OrderQueryBond(a6, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		return mol;
	}

	public static QueryAtomContainer alcoholSecondaryAttachedToTerminalVinyl() {
		// TODO "[*]C(O)C=C"
		// return createQuery("CC(O)C=C",);
		// return
		// createQuery("[H]OC([H])(C)C([H])=C([H])[H]","secondary alcohol attached to terminal vinyl group");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = new org.openscience.cdk.Molecule();
		IAtom a1 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.SINGLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a3, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a3, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a7, a6, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a8, a6, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a9, a8, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a10, a8, IBond.Order.SINGLE);
		mol.addBond(b9);
		QueryAtomContainer q = QueryAtomContainerCreator
				.createBasicQueryContainer(mol);
		q.setID("secondary alcohol attached to terminal vinyl group");
		return q;

	}

	public static QueryAtomContainer esterOfalcoholSecondaryAttachedToTerminalVinyl() {
		// TODO ""CC(C(=O)O[*])C=C""
		// return
		// createQuery("CC(C(=O)OC)C=C","ester of secondary alcohol attached to terminal vinyl group");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a12);
		IBond b1 = MoleculeTools.newBond(builder, a1, a2, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a1, a3, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a1, a4, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a2, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a2, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a6, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a6, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a8, a9, IBond.Order.DOUBLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a8, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools
				.newBond(builder, a10, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a6, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		QueryAtomContainer q = QueryAtomContainerCreator
				.createBasicQueryContainer(mol);
		q.setID("ester of secondary alcohol attached to terminal vinyl group");
		return q;
	}

	public static IAtomContainer allylAlcohol() {
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a10);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a1, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a2, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a3, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a3, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a4, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		mol.setID("allyl alcohol");
		return mol;
		// return createAtomContainer("C=CCO",);
	}

	public static QueryAtomContainer allylAlcoholAcetal() {
		// TODO "[*]C(OCC=C)(OCC=C)[H]"
		// return createQuery("C(OCC=C)(OCC=C)[H]","allyl alcohol acetal");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(builder, Elements.OXYGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(builder, Elements.CARBON);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a12);
		IAtom a13 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a13);
		IAtom a14 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a14);
		IAtom a15 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a15);
		IAtom a16 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a16);
		IAtom a17 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a17);
		IAtom a18 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a18);
		IAtom a19 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a19);
		IAtom a20 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a20);
		IAtom a21 = MoleculeTools.newAtom(builder, Elements.HYDROGEN);
		mol.addAtom(a21);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.SINGLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.DOUBLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a1, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a7, a6, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a8, a7, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a9, a8, IBond.Order.DOUBLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a10, a1, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a1, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a3, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		IBond b12 = MoleculeTools.newBond(builder, a3, a13, IBond.Order.SINGLE);
		mol.addBond(b12);
		IBond b13 = MoleculeTools.newBond(builder, a7, a14, IBond.Order.SINGLE);
		mol.addBond(b13);
		IBond b14 = MoleculeTools.newBond(builder, a7, a15, IBond.Order.SINGLE);
		mol.addBond(b14);
		IBond b15 = MoleculeTools.newBond(builder, a4, a16, IBond.Order.SINGLE);
		mol.addBond(b15);
		IBond b16 = MoleculeTools.newBond(builder, a8, a17, IBond.Order.SINGLE);
		mol.addBond(b16);
		IBond b17 = MoleculeTools.newBond(builder, a5, a18, IBond.Order.SINGLE);
		mol.addBond(b17);
		IBond b18 = MoleculeTools.newBond(builder, a5, a19, IBond.Order.SINGLE);
		mol.addBond(b18);
		IBond b19 = MoleculeTools.newBond(builder, a9, a20, IBond.Order.SINGLE);
		mol.addBond(b19);
		IBond b20 = MoleculeTools.newBond(builder, a9, a21, IBond.Order.SINGLE);
		mol.addBond(b20);
		QueryAtomContainer q = QueryAtomContainerCreator
				.createBasicQueryContainer(mol);
		q.setID("allyl alcohol acetal");
		return q;

	}

	public static QueryAtomContainer allylAlcoholEsterDerivative() {
		// TODO "CC=CCOC(=O)C[*]"
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a12);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a2, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a5, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a7, a5, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a8, a5, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a9, a8, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a10, a8, IBond.Order.DOUBLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools
				.newBond(builder, a11, a10, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools
				.newBond(builder, a12, a10, IBond.Order.SINGLE);
		mol.addBond(b11);
		QueryAtomContainer q = QueryAtomContainerCreator
				.createBasicQueryContainer(mol);
		q.setID("allyl alcohol ester derivative");
		return q;

		// return
		// createQuery("O=C(C)OC([H])([H])C([H])=C([H])[H]","allyl alcohol ester derivative");
	}

	public static IAtomContainer allylMercaptan() {
		// return
		// createAtomContainer("[H]SC([H])([H])C([H])=C([H])[H]","allyl mercaptan");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.SULFUR);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a1, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a2, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a3, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a3, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a4, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		mol.setID("allyl mercaptan");
		return mol;
	}

	public static IAtomContainer allylAmine() {
		// return createAtomContainer("C=CCN","allyl amine");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a1, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a2, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a3, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a3, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a4, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a4, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		mol.setID("allyl amine");
		return mol;
	}

	public static IAtomContainer allylSulphide() {
		// return createAtomContainer("C=CCSCC=C","allyl sulphide");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.SULFUR);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a12);
		IAtom a13 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a13);
		IAtom a14 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a14);
		IAtom a15 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a15);
		IAtom a16 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a16);
		IAtom a17 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a17);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a5, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a7, a6, IBond.Order.DOUBLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a1, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a1, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a2, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a3, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a3, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		IBond b12 = MoleculeTools.newBond(builder, a5, a13, IBond.Order.SINGLE);
		mol.addBond(b12);
		IBond b13 = MoleculeTools.newBond(builder, a5, a14, IBond.Order.SINGLE);
		mol.addBond(b13);
		IBond b14 = MoleculeTools.newBond(builder, a6, a15, IBond.Order.SINGLE);
		mol.addBond(b14);
		IBond b15 = MoleculeTools.newBond(builder, a7, a16, IBond.Order.SINGLE);
		mol.addBond(b15);
		IBond b16 = MoleculeTools.newBond(builder, a7, a17, IBond.Order.SINGLE);
		mol.addBond(b16);
		mol.setID("allyl sulphide");
		return mol;
	}

	public static IAtomContainer allylThioester() {
		// return createQuery("C(=O)SCC=C","allyl thioester");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.SULFUR);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a12);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a1, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a5, IBond.Order.DOUBLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a1, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a4, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a4, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a5, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a6, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a6, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		mol.setID("allyl thioester");
		return mol;
	}

	public static IAtomContainer acrolein() {
		// return createAtomContainer("C=CC=O","acrolein");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.DOUBLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a1, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a2, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a3, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		mol.setID("acrolein");
		return mol;
	}

	public static IAtomContainer methacrolein() {
		// return createAtomContainer("C=C(C)C=O","methacrolein");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a2, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.DOUBLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a1, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a3, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a3, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a3, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a4, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		mol.setID("Methacrolein");
		return mol;
	}

	public static IAtomContainer methacroleinAcetal() {
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a12);
		IAtom a13 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a13);
		IAtom a14 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a14);
		IAtom a15 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a15);
		IAtom a16 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a16);
		IAtom a17 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a17);
		IAtom a18 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a18);
		IAtom a19 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a19);
		IAtom a20 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a20);
		IBond b1 = MoleculeTools.newBond(builder, a1, a2, IBond.Order.SINGLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a2, a3, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a3, a4, IBond.Order.DOUBLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a2, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a2, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a1, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a1, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a1, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a3, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a5, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a6, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		IBond b12 = MoleculeTools.newBond(builder, a4, a13, IBond.Order.SINGLE);
		mol.addBond(b12);
		IBond b13 = MoleculeTools.newBond(builder, a4, a14, IBond.Order.SINGLE);
		mol.addBond(b13);
		IBond b14 = MoleculeTools
				.newBond(builder, a12, a15, IBond.Order.SINGLE);
		mol.addBond(b14);
		IBond b15 = MoleculeTools
				.newBond(builder, a12, a16, IBond.Order.SINGLE);
		mol.addBond(b15);
		IBond b16 = MoleculeTools
				.newBond(builder, a12, a17, IBond.Order.SINGLE);
		mol.addBond(b16);
		IBond b17 = MoleculeTools
				.newBond(builder, a11, a18, IBond.Order.SINGLE);
		mol.addBond(b17);
		IBond b18 = MoleculeTools
				.newBond(builder, a11, a19, IBond.Order.SINGLE);
		mol.addBond(b18);
		IBond b19 = MoleculeTools
				.newBond(builder, a11, a20, IBond.Order.SINGLE);
		mol.addBond(b19);
		mol.setID("Methacrolein acetal");
		return mol;

	}

	public static IAtomContainer acrylicAcid() {
		// return createAtomContainer("C=CC(O)=O","acrylic acid");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a3, IBond.Order.DOUBLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a1, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a2, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a4, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		mol.setID("acrylic acid");
		return mol;
	}

	public static IAtomContainer methacrylicAcid() {
		// return createAtomContainer("C=C(C)C(O)=O","methacrylic acid");
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mol = MoleculeTools.newMolecule(builder);
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a8);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.HYDROGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
		mol.addAtom(a12);
		IBond b1 = MoleculeTools.newBond(builder, a2, a1, IBond.Order.DOUBLE);
		mol.addBond(b1);
		IBond b2 = MoleculeTools.newBond(builder, a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		IBond b3 = MoleculeTools.newBond(builder, a4, a2, IBond.Order.SINGLE);
		mol.addBond(b3);
		IBond b4 = MoleculeTools.newBond(builder, a5, a4, IBond.Order.SINGLE);
		mol.addBond(b4);
		IBond b5 = MoleculeTools.newBond(builder, a6, a4, IBond.Order.DOUBLE);
		mol.addBond(b5);
		IBond b6 = MoleculeTools.newBond(builder, a1, a7, IBond.Order.SINGLE);
		mol.addBond(b6);
		IBond b7 = MoleculeTools.newBond(builder, a1, a8, IBond.Order.SINGLE);
		mol.addBond(b7);
		IBond b8 = MoleculeTools.newBond(builder, a3, a9, IBond.Order.SINGLE);
		mol.addBond(b8);
		IBond b9 = MoleculeTools.newBond(builder, a3, a10, IBond.Order.SINGLE);
		mol.addBond(b9);
		IBond b10 = MoleculeTools.newBond(builder, a3, a11, IBond.Order.SINGLE);
		mol.addBond(b10);
		IBond b11 = MoleculeTools.newBond(builder, a5, a12, IBond.Order.SINGLE);
		mol.addBond(b11);
		mol.setID("methacrylic acid");
		return mol;
	}

	public static boolean isAcetylenic(IAtomContainer mol) {
		for (int i = 0; i < mol.getBondCount(); i++) {
			IBond b = mol.getBond(i);
			if ((b.getOrder() == CDKConstants.BONDORDER_TRIPLE)
					&& (b.getAtom(0).getSymbol().equals("C"))
					&& (b.getAtom(1).getSymbol().equals("C"))) {
				logger.fine("Acetylenic group found");
				return true;
			}
		}
		return false;
	}

	public static QueryAtomContainer stericallyHindered() {

		QueryAtomContainer mol = new QueryAtomContainer();
		SymbolQueryAtom a[] = new SymbolQueryAtom[6];

		InverseSymbolSetQueryAtom[] r = new InverseSymbolSetQueryAtom[6];
		for (int i = 0; i < 6; i++) {
			r[i] = new InverseSymbolSetQueryAtom();
			a[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			mol.addAtom(a[i]);
			r[i].addSymbol("H");
			mol.addAtom(r[i]);
			if (i > 0) {
				AnyOrderQueryBond b = new AnyOrderQueryBond(a[i], a[i - 1],
						IBond.Order.SINGLE);
				mol.addBond(b);
			}
		}
		SymbolQueryAtom a7 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(a7);
		SymbolQueryAtom a11 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		mol.addAtom(a11);

		InverseSymbolSetQueryAtom hindered = new InverseSymbolSetQueryAtom();
		hindered.addSymbol("H"); // anything but H
		// hindered.addSymbol("C");
		mol.addAtom(hindered);
		AnyOrderQueryBond b = new AnyOrderQueryBond(a[3], hindered,
				IBond.Order.SINGLE);
		mol.addBond(b);

		OrderQueryBond b7 = new OrderQueryBond(a[2], a7, IBond.Order.SINGLE);
		mol.addBond(b7);
		OrderQueryBond b8 = new OrderQueryBond(a7, r[0], IBond.Order.SINGLE);
		mol.addBond(b8);
		OrderQueryBond b9 = new OrderQueryBond(a7, r[1], IBond.Order.SINGLE);
		mol.addBond(b9);
		OrderQueryBond b10 = new OrderQueryBond(a7, r[2], IBond.Order.SINGLE);
		mol.addBond(b10);
		OrderQueryBond b11 = new OrderQueryBond(a[4], a11, IBond.Order.SINGLE);
		mol.addBond(b11);
		OrderQueryBond b12 = new OrderQueryBond(a11, r[3], IBond.Order.SINGLE);
		mol.addBond(b12);
		OrderQueryBond b13 = new OrderQueryBond(a11, r[4], IBond.Order.SINGLE);
		mol.addBond(b13);
		OrderQueryBond b14 = new OrderQueryBond(a11, r[5], IBond.Order.SINGLE);
		mol.addBond(b14);

		mol.setID("Sterically hindered\t");
		return mol;
	}

	public static boolean hasSubstituents(IAtomContainer mol, IRingSet rings) {

		if ((rings == null) || (rings.getAtomContainerCount() == 0))
			return false;
		int size = rings.getAtomContainerCount();
		IRing r = null;
		// Atom[] neighbors = null;
		List neighbors = null;
		for (int i = 0; i < size; i++) {
			r = (IRing) rings.getAtomContainer(i);
			for (int j = 0; j < r.getAtomCount(); j++) {
				//
				neighbors = mol.getConnectedBondsList(r.getAtom(j));
				for (int n = 0; n < neighbors.size(); n++) {
					IBond nb = (IBond) neighbors.get(n);
					if (nb.getAtom(0).getSymbol().equals("H"))
						continue;
					if (nb.getAtom(1).getSymbol().equals("H"))
						continue;
					// if there is acyclic bond then it is substituent
					if (!nb.getFlag(CDKConstants.ISINRING))
						return true;
					/*
					 * if (!r.contains(neighbors[n])) {
					 * logger.fine("Ring has substituent(s)\t"
					 * ,"YES\t",neighbors[n].getSymbol()); return true; }
					 */
				}
			}
		}
		logger.fine("Ring with substituent(s)\tNO");
		return false;
	}

	public static boolean isCommonTerpene(IAtomContainer mol) {
		SSSRFinder ssrf = new SSSRFinder(mol);
		return isCommonTerpene(mol, ssrf.findSSSR());
	}

	public static boolean isCommonTerpene(IAtomContainer mol, IRingSet rings) {

		IMolecularFormula formula = MolecularFormulaManipulator
				.getMolecularFormula(mol);
		int c = MolecularFormulaManipulator.getElementCount(formula,
				MoleculeTools.newElement(formula.getBuilder(), "C"));

		if ((c % 5) != 0)
			return false;
		int n = c / 5;
		if (n < 2)
			return false;
		int h = MolecularFormulaManipulator.getElementCount(formula,
				MoleculeTools.newElement(formula.getBuilder(), "H"));

		if (h / 8 != n) {
			logger.fine(MolecularFormulaManipulator.getHillString(formula));
			return false;
		}
		int o = MolecularFormulaManipulator.getElementCount(formula,
				MoleculeTools.newElement(formula.getBuilder(), "O"));
		if ((c + h + o) < mol.getAtomCount())
			return false; // athoms different than C,H,O

		QueryAtomContainer q = isopreneUnit();
		List list = FunctionalGroups.getBondMap(mol, q, false);
		if ((list == null) || (list.size() == 0)) {
			logger.fine("NO isoprene unit found");
			return false;
		}
		FunctionalGroups.markMaps(mol, q, list);

		ArrayList ids = new ArrayList();
		ids.add(q.getID());

		int size = 0;
		if (rings != null)
			size = rings.getAtomContainerCount();
		if ((rings != null) && (size > 0) && ((c + h) == mol.getAtomCount())) {
			// w monociklicnite ima 2 double bond, w biciklicnite ima 1 double
			int db = 0;
			for (int i = 0; i < mol.getBondCount(); i++)
				if (mol.getBond(i).getOrder() == CDKConstants.BONDORDER_DOUBLE)
					db++;
			if ((size == 1) && (db != 2)) {
				logger.fine("Single ring , double bonds " + db);
				return false;
			}
			if ((size == 2) && (db != 1)) {
				logger.fine("Two rings , double bonds " + db);
				return false;
			}
			logger.fine("May be terpene");
			// za ostanalite neznam
		}

		if (o > 0) { // check for alcohol, aldehyde, carboxylic acid
			preProcess(mol);
			q = alcohol(false);
			ids.add(q.getID());
			list = FunctionalGroups.getBondMap(mol, q, true);
			FunctionalGroups.markMaps(mol, q, list);
			q = aldehyde();
			ids.add(q.getID());
			list = FunctionalGroups.getBondMap(mol, q, true);
			FunctionalGroups.markMaps(mol, q, list);
			q = carboxylicAcid();
			ids.add(q.getID());
			list = FunctionalGroups.getBondMap(mol, q, true);
			FunctionalGroups.markMaps(mol, q, list);
		}

		boolean b = hasMarkedOnlyTheseGroups(mol, ids);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine(mapToString(mol).toString());
			if (b)
				logger.fine("This compound is a terpene (terpenoid)");
			else
				logger.fine("This compound is NOT a terpene");
		}
		return b;

	}

	public static boolean singleFusedRing(IAtomContainer mol, IRingSet rings) {

		if ((rings == null) || (rings.getAtomContainerCount() == 0))
			return false;
		int size = rings.getAtomContainerCount();
		if (size == 1)
			return true;
		IRingSet allRings = rings.getConnectedRings((IRing) rings
				.getAtomContainer(0));
		boolean b = (allRings.getAtomContainerCount() == size);
		if (b)
			logger.fine("Single fused ring found");
		allRings = null;
		return b;
	}

	public static boolean hasGroup(IAtomContainer mol, IAtomContainer q) {
		return hasGroup(mol, q, null);
	}

	public static boolean hasGroup(IAtomContainer mol, IAtomContainer q,
			IAtomContainer selected) {
		// search for these groups relies on CHn marks set by markCHn
		return hasGroup(mol, q, needsPreprocessing(q), selected);
	}

	public static boolean hasGroup(IAtomContainer mol, IAtomContainer q,
			boolean preprocess) {
		return hasGroup(mol, q, preprocess, null);
	}

	public static boolean hasGroup(IAtomContainer mol, IAtomContainer q,
			boolean preprocess, IAtomContainer selected) {
		if (q == null || mol == null)
			return false;
		if (q.getAtomCount() > mol.getAtomCount()) {
			logger.fine("A query with more atoms than a molecule!");
			return false;
		}
		UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
		if (preprocess) {
			logger.fine("Marking CHn groups");
			preProcess(mol);
		}
		if (selected == null)
			try {
				if (uit.isSubgraph(mol, q)) {
					// logger.fine(mapToString(mol));
					logger.fine("Molecule \t" + mol.getID() + MSG_HASGROUP
							+ q.getID() + "\tYES");
					return true;
				} else {
					// logger.fine(mapToString(mol));
					logger.fine("Molecule \t" + mol.getID() + MSG_HASGROUP
							+ q.getID() + "\tNO");
					return false;
				}
			} catch (Exception x) {
				logger.log(Level.SEVERE, x.getMessage(), x);
				return false;
			}
		else
			try {
				List<RMap> list = uit.getSubgraphAtomsMap(mol, q);
				if (list != null)
					for (int i = 0; i < list.size(); i++) {
						RMap map = list.get(i);
						selected.addAtom(mol.getAtom(map.getId1()));
					}
				return (list != null) && (list.size() > 0);

			} catch (CDKException x) {
				logger.log(Level.SEVERE, x.getMessage(), x);
				return false;
			}
	}

	public static boolean isSubstance(IAtomContainer mol, IAtomContainer q) {
		try {
			if (mol.getAtomCount() != q.getAtomCount())
				return false; // no need to spend time on isomorphism ...
			if (mol.getBondCount() != q.getBondCount())
				return false; // no need to spend time on isomorphism ...
			UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
			boolean b = (uit.isIsomorph(mol, q));

			if (logger.isLoggable(Level.FINE))
				if (b)
					logger.fine(MSG_MOLECULEIS + q.getID() + "\tYES");
				else
					logger.fine(MSG_MOLECULEIS + q.getID() + "\tNO");
			return b;
		} catch (CDKException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			return false;
		}

	}

	private static int countGroups(IAtomContainer mol, QueryAtomContainer q) {
		List list = getUniqueBondMap(mol, q, false);
		if (list == null)
			return 0;
		else if (list.size() == 0)
			return 0;
		else {
			markMaps(mol, q, list);
			return 1;
		}
	}

	public static boolean hasManyDifferentFunctionalGroups(IAtomContainer mol,
			int threshold) {
		int groups = 0;
		boolean group = false;
		String metals[] = { "Na", "K", "Ca", "Mg", "Al" };
		IMolecularFormula formula = MolecularFormulaManipulator
				.getMolecularFormula(mol);
		markCHn(mol);
		List<IElement> elements = MolecularFormulaManipulator.elements(formula);
		logger.fine("Checking for more than " + threshold + " groups");
		for (int i = 0; i < elements.size(); i++) {
			String element = elements.get(i).getSymbol();
			if (element.equals("H"))
				continue;
			if (element.equals("C")) {
				if (isAcetylenic(mol))
					groups++;
				continue;
			}
			if (element.equals("N")) {
				group = FunctionalGroups.hasGroup(mol, secondaryAmine(false))
						|| FunctionalGroups.hasGroup(mol, tertiaryAmine())
						|| FunctionalGroups.hasGroup(mol, primaryAmine(false));
				if (group) {
					groups++;
					logger.fine("amino group found");
				}
				groups += countGroups(mol, cyano());
				groups += countGroups(mol, nitro1double());
				groups += countGroups(mol, nitro2double());
				groups += countGroups(mol, Nnitroso());
				groups += countGroups(mol, diAzo());
				groups += countGroups(mol, triAzeno());
				if (groups >= threshold)
					break;
			} else

			if (element.equals("O")) {
				int g = countGroups(mol, carboxylicAcid())
						+ countGroups(mol, FunctionalGroups.ester());
				if (g > 0) {
					groups++; // count once
					logger.fine("acid or ester group found");
				}
				groups += countGroups(mol, saltOfCarboxylicAcid(metals));
				groups += countGroups(mol, acetal());
				groups += countGroups(mol, ether());
				groups += countGroups(mol, aldehyde());
				groups += countGroups(mol, ketone());
				int alcohols = countGroups(mol, alcohol(false));
				groups += alcohols;
				if (groups >= threshold)
					break;
			} else

			if (element.equals("S")) {
				groups += countGroups(mol, sulphide());
				groups += countGroups(mol, mercaptan());
				groups += countGroups(mol, thioester());
				if (groups >= threshold)
					break;
				groups += countGroups(mol, sulphate(metals));
				groups += countGroups(mol, sulphamate(metals));
				groups += countGroups(mol, sulphonate(metals));
				if (groups >= threshold)
					break;
			} else {
				// count each unlisted atom as group ... quite rude ;)
				if (groups >= threshold)
					break;
				groups++;
			}

		}
		logger.fine("Different groups found\t" + groups);
		return (groups >= threshold);
	}

	/*
	 * public static boolean
	 * hasManyDifferentFunctionalGroups(org.openscience.cdk
	 * .interfaces.AtomContainer mol,int threshold) { int groups = 0; boolean
	 * group = false; String metals[] = {"Na","K","Ca","Mg","Al"}; MFAnalyser
	 * mf= new MFAnalyser(mol); markCHn(mol); Vector elements =
	 * mf.getElements(); logger.fine("Checking for more than " + threshold
	 * ," groups"); for (int i=0; i< elements.size(); i++) { String element =
	 * (String) elements.get(i); if (element.equals("H")) continue; if
	 * (element.equals("C")) { if (isAcetylenic(mol)) groups++; continue; } if
	 * (element.equals("N")) { group =
	 * FunctionalGroups.hasGroup(mol,secondaryAmine(false)) ||
	 * FunctionalGroups.hasGroup(mol,tertiaryAmine()) ||
	 * FunctionalGroups.hasGroup(mol,primaryAmine(false)); if (group) {
	 * groups++; logger.fine("amino group found"); } groups +=
	 * countGroups(mol,cyano()); groups += countGroups(mol,nitro1double());
	 * groups += countGroups(mol,nitro2double()); groups +=
	 * countGroups(mol,Nnitroso()); groups += countGroups(mol,diAzo()); groups
	 * += countGroups(mol,triAzeno()); if (groups >= threshold) break; } else
	 * 
	 * if (element.equals("O")) { group =
	 * FunctionalGroups.hasGroup(mol,carboxylicAcid()) ||
	 * 
	 * FunctionalGroups.hasGroup(mol,ester()); if (group) { groups++;
	 * logger.fine("acid or ester group found"); } groups +=
	 * countGroups(mol,saltOfCarboxylicAcid(metals)); groups +=
	 * countGroups(mol,acetal()); groups += countGroups(mol,ether()); groups +=
	 * countGroups(mol,aldehyde()); groups += countGroups(mol,ketone()); groups
	 * += countGroups(mol,alcohol(false)); if (groups >= threshold) break; }
	 * else
	 * 
	 * if (element.equals("S")) { groups += countGroups(mol,sulphide()); groups
	 * += countGroups(mol,mercaptan()); groups += countGroups(mol,thioester());
	 * if (groups >= threshold) break; groups +=
	 * countGroups(mol,sulphate(metals)); groups +=
	 * countGroups(mol,sulphamate(metals)); groups +=
	 * countGroups(mol,sulphonate(metals)); if (groups >= threshold) break; }
	 * else { //count each unlisted atom as group ... quite rude ;) if (groups
	 * >= threshold) break; groups++; }
	 * 
	 * } logger.fine("Different groups found\t",groups); return (groups >=
	 * threshold); }
	 */
	public static int associateIonic(IAtomContainer a) throws CDKException {

		IAtomContainerSet c = ConnectivityChecker.partitionIntoMolecules(a);
		// clear VISITED flag
		for (int i = 0; i < a.getAtomCount(); i++)
			a.getAtom(i).setFlag(CDKConstants.VISITED, false);

		int n = c.getAtomContainerCount();
		if (n == 1)
			return 0;
		logger.fine("Trying to find ionic bonds (if any)");
		IAtomContainer ac = null;
		ArrayList match = new ArrayList();
		StringBuffer notMatched = new StringBuffer();
		int ionicBonds = 0;
		for (int i = 0; i < n; i++) {
			ac = c.getAtomContainer(i);
			logger.fine("AtomContainer \t" + Integer.toString(i + 1)
					+ " atom\t" + Integer.toString(ac.getAtomCount()));
			// if there are atoms with formal charge != 0
			for (int k = 0; k < ac.getAtomCount(); k++) {
				IAtom atom = ac.getAtom(k);
				if ((atom.getFormalCharge() != null)
						&& (atom.getFormalCharge() != 0))
					match.add(atom);
			}
			if (match.size() == 0)
				continue; // nothing to match

			for (int j = i + 1; j < n; j++) {
				ac = c.getAtomContainer(j);
				for (int k = 0; k < ac.getAtomCount(); k++) {
					IAtom atom = ac.getAtom(k);

					if ((atom.getFormalCharge() != null)
							&& (atom.getFormalCharge() != 0)) {// lookup in
																// match
						Object aCharge = atom
								.getProperty(ChargeConsumed.CHARGECONSUMED);
						if (aCharge == null) {
							aCharge = new ChargeConsumed(atom);
							atom.setProperty(ChargeConsumed.CHARGECONSUMED,
									aCharge);
						} else if (((ChargeConsumed) aCharge).getCharge() == 0)
							continue;

						for (int m = 0; m < match.size(); m++) {
							IAtom mAtom = (IAtom) match.get(m);
							if ((mAtom.getFormalCharge() != null)
									&& (mAtom.getFormalCharge() == 0))
								continue;

							Object o = mAtom
									.getProperty(ChargeConsumed.CHARGECONSUMED);
							if (o == null)
								mAtom.setProperty(
										ChargeConsumed.CHARGECONSUMED,
										new ChargeConsumed(mAtom));
							else if (((ChargeConsumed) o).getCharge() == 0)
								continue; // already associated

							ChargeConsumed mCharge = (ChargeConsumed) mAtom
									.getProperty(ChargeConsumed.CHARGECONSUMED);

							if (mCharge.compatible((ChargeConsumed) aCharge)) {
								mCharge.consume((ChargeConsumed) aCharge);
								if (a.getBond(atom, mAtom) == null) {
									/*
									 * TODO replace MyAssociationBond with
									 * Association once QueryBond matches method
									 * accepts ElectronContainer instead of Bond
									 */
									// Association ionicBond = new
									// Association(atom,mAtom);
									MyAssociationBond ionicBond = new MyAssociationBond(
											atom, mAtom);
									logger.fine("Association between atom\t"
											+ atom.getSymbol() + "\tand\t"
											+ mAtom.getSymbol());

									a.addElectronContainer(ionicBond);
									ionicBonds++;
								} else
									logger.fine("Bond already exists : atom\t"
											+ atom.getSymbol() + "\tand\t"
											+ mAtom.getSymbol());
								break;
							}
						}
					}
				}
			}
			// verify if all atoms are matched
			for (int j = 0; j < match.size(); j++) {
				Object o = ((IAtom) match.get(j))
						.getProperty(ChargeConsumed.CHARGECONSUMED);
				if (o == null) {
					notMatched.append('\t');
					notMatched.append(((IAtom) match.get(j)).getSymbol());
				} else if (((ChargeConsumed) o).getCharge() != 0) {
					notMatched.append('\t');
					notMatched.append(((IAtom) match.get(j)).getSymbol());
				}
			}

		}
		if (notMatched.toString().length() > 0)
			throw new CDKException("Can't find an ionic bond for atom(s)\t"
					+ notMatched.toString());

		logger.fine("Ionic bonds found\t" + ionicBonds);
		return ionicBonds;
	}

	/**
	 * Identifies the fragment to be detached and breaks the relevant bonds In
	 * order to function properly, the group has to be defined with at least one
	 * atom marked with setProperty(DONTMARK,dontMark). This defined the bond to
	 * break. See {@link #DONTMARK}
	 * 
	 * @param a
	 *            AtomContainer to be processed
	 * @param q
	 *            {@link QueryAtomContainer} defines the group to be detached
	 * @return {@link SetOfAtomContainers} set of {@link AtomContainer} resulted
	 *         by breaking bonds as above. Hydrogens are added on the broken end
	 *         of each bond TODO care for bond orders!
	 */
	public static IAtomContainerSet detachGroup(IAtomContainer a,
			QueryAtomContainer q) {
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();

		List list = FunctionalGroups.getUniqueBondMap(a, q,
				needsPreprocessing(q));
		if ((list == null) || (list.size() == 0))
			return null;
		markMaps(a, q, list);
		ArrayList<IBond> bondsToBreak = new ArrayList<IBond>();
		for (int j = 0; j < list.size(); j++) {
			List first = (List) list.get(j);
			for (int i = 0; i < first.size(); i++) {
				RMap rmap = (RMap) first.get(i);
				IBond b2 = q.getBond(rmap.getId2());
				// IAtom[] a2 = b2.getAtoms();

				// will break bonds that does not belong to the group but to the
				// connected fragments
				// org.openscience.cdk.interfaces.Atom deleteAtom = null;
				IAtom stayAtom = null;
				// if the bond belongs to the group, the stayAtom will remain
				// null
				for (int k = 0; k < b2.getAtomCount(); k++)
					if (b2.getAtom(k) instanceof ReallyAnyAtom)
						stayAtom = b2.getAtom(k);
					else {
						Object o = b2.getAtom(k).getProperty(DONTMARK);
						if ((o != null) && (o.equals(q.getID())))
							stayAtom = b2.getAtom(k);
						// else deleteAtom = a2[k];
					}

				if (stayAtom != null) {
					// gather all bonds to be broken in a list
					IBond b1 = a.getBond(rmap.getId1());
					if (bondsToBreak.indexOf(b1) == -1)
						bondsToBreak.add(b1);
					break;
				}
			}

		}
		if (logger.isLoggable(Level.FINE))
			for (int i = 0; i < bondsToBreak.size(); i++) {
				IBond b = (IBond) bondsToBreak.get(i);
				logger.fine("Break bond between\t" + b.getAtom(0).getSymbol()
						+ "\t" + b.getAtom(1).getSymbol());
			}
		for (int i = 0; i < bondsToBreak.size(); i++) {
			IBond b = bondsToBreak.get(i);
			// IAtom[] a1 = b.getAtoms();
			a.removeElectronContainer(b);
			IAtom h = null;
			for (int k = 0; k < b.getAtomCount(); k++) {
				IAtom a1 = b.getAtom(k);
				if (q.getID().equals(CARBOXYLIC_ACID_SALT))
					if (a1.getSymbol().equals("O")
							&& (a1.getFormalCharge() != null)
							&& (a1.getFormalCharge() == -1)) {
						a1.setFormalCharge(0);
						h = MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.HYDROGEN);
						a.addAtom(h);
						a.addBond(MoleculeTools.newBond(builder, a1, h));
						a1.removeProperty(q.getID());
					} else {
						a1.setFormalCharge(0);
						// a1[k].removeProperty(DONTMARK);
						// a1[k].setProperty(q.getID(),new Boolean(true));
					}
				else if (q.getID().equals(HYDROCHLORIDE_OF_AMINE)) {
					if (a1.getSymbol().equals("N")
							&& (a1.getFormalCharge() != null)
							&& (a1.getFormalCharge() == 1)) {
						a1.setFormalCharge(0);
						// remove h
						List neighbors = a.getConnectedAtomsList(a1);
						IAtom cl = null;
						for (int ih = 0; ih < neighbors.size(); ih++) {
							IAtom nh = (IAtom) neighbors.get(ih);
							if (nh.getSymbol().equals("H")) {
								logger.fine("a H atom connected to N will be moved to Cl");
								a.removeBond(a1, nh);
								cl = b.getAtom((k + 1) % 2);
								// cl = a1[(k+1)%2]; //TODO this assumes two
								// atoms in the bond
								if (cl.getSymbol().equals("Cl")) {
									cl.setFormalCharge(0);
									a.addBond(MoleculeTools.newBond(builder,
											cl, nh));
									nh.removeProperty(q.getID());
								} else
									logger.fine("Expected Cl atom but found\t"
											+ cl);
								break;
							}
						}

					} else
						;
					/*
					 * { a1[k].setFormalCharge(0); h =
					 * MoleculeTools.newAtom(SilentChemObjectBuilder
					 * .getInstance(),Elements.HYDROGEN); a.addAtom(h);
					 * a.addBond(MoleculeTools.newBond(builder,a1[k],h)); }
					 */
				} else if (q.getID().equals(LACTONE)) {
					if (a1.getSymbol().equals("O")) {
						h = MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.HYDROGEN);
						a.addAtom(h);
						a.addBond(MoleculeTools.newBond(builder, a1, h));
						a1.removeProperty(q.getID());
					} else { // atatch OH
						h = MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.HYDROGEN);
						a.addAtom(h);
						IAtom o = MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.OXYGEN);
						a.addAtom(o);
						a.addBond(MoleculeTools.newBond(builder, o, h));
						a.addBond(MoleculeTools.newBond(builder, a1, o));

					}
				} else {
					int hydrogens = a1.getValency()
							- a.getConnectedAtomsCount(a1);
					for (int nh = 0; nh < hydrogens; nh++) {
						h = MoleculeTools.newAtom(
								SilentChemObjectBuilder.getInstance(),
								Elements.HYDROGEN);
						a.addAtom(h);
						a.addBond(MoleculeTools.newBond(builder, a1, h));
					}
				}
			}
		}
		if (bondsToBreak.size() > 0)
			return ConnectivityChecker.partitionIntoMolecules(a);
		else
			return null;
	}

	public static void clearMark(IAtomContainer a, QueryAtomContainer q) {
		clearMark(a, q.getID());
	}

	public static void clearMark(IAtomContainer a, Object id) {

		for (int i = 0; i < a.getBondCount(); i++) {
			IBond b = a.getBond(i);
			b.removeProperty(id);
			b.removeProperty(ALLOCATED);
			b.removeProperty(DONTMARK);

			for (int k = 0; k < b.getAtomCount(); k++) {
				IAtom atom = b.getAtom(k);
				atom.removeProperty(id);
				atom.removeProperty(DONTMARK);
				atom.removeProperty(ALLOCATED);
			}
		}

	}

	public static void clearMarks(IAtomContainer a) {
		for (int i = 0; i < a.getBondCount(); i++)
			a.getBond(i).getProperties().clear();
		for (int k = 0; k < a.getAtomCount(); k++)
			a.getAtom(k).getProperties().clear();

	}

	/**
	 * 
	 * @param size
	 * @return ring
	 */
	public static QueryAtomContainer ring(int size) {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID("RING " + Integer.toString(size));
		SymbolQueryAtom[] c = new SymbolQueryAtom[size];
		ReallyAnyAtom[] h = new ReallyAnyAtom[size];
		for (int i = 0; i < size; i++) {
			c[i] = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			c[i].setProperty(RING_NUMBERING, new Integer(i));
			q.addAtom(c[i]);
			if (i > 0)
				q.addBond(new AnyOrderQueryBond(c[i], c[i - 1],
						IBond.Order.SINGLE));
			h[i] = new ReallyAnyAtom();
			h[i].setProperty(RING_NUMBERING, new Integer(i));
			q.addAtom(h[i]);
			q.addBond(new AnyOrderQueryBond(c[i], h[i], IBond.Order.SINGLE));
		}
		q.addBond(new AnyOrderQueryBond(c[0], c[size - 1], IBond.Order.SINGLE));
		return q;
	}

	/**
	 * 
	 * @param mol
	 * @param q
	 */
	public static void markAtomsInRing(IAtomContainer mol, QueryAtomContainer q) {
		try {
			UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
			List list = uit.getSubgraphAtomsMap(mol, q);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					RMap map = (RMap) list.get(i);
					Object p = q.getAtom(map.getId2()).getProperty(
							RING_NUMBERING);
					if (p != null)
						mol.getAtom(map.getId1())
								.setProperty(RING_NUMBERING, p);
				}

		} catch (CDKException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
		}

	}

	/**
	 * 
	 * @param mol
	 *            {@link AtomContainer}
	 * @return the length of the longest carbon chain
	 */
	public static int getLongestCarbonChainLength(IAtomContainer mol) {
		double[][] conMat = ConnectionMatrix.getMatrix(mol);
		int[][] apsp = PathTools.computeFloydAPSP(conMat);
		int maxPathLength = 0;
		int bestStartAtom = -1;
		int bestEndAtom = -1;
		IAtom atom = null;
		IAtomContainer path = new org.openscience.cdk.AtomContainer();

		for (int f = 0; f < apsp.length; f++) {
			atom = mol.getAtom(f);
			if (mol.getConnectedBondsCount(atom) == 1) {
				for (int g = 0; g < apsp.length; g++) {
					if (apsp[f][g] > maxPathLength) {
						try {
							path.removeAllElements();
							for (int i = 0; i < mol.getAtomCount(); i++)
								mol.getAtom(i).setFlag(CDKConstants.VISITED,
										false);
							if (PathTools.depthFirstTargetSearch(mol,
									mol.getAtom(f), mol.getAtom(g), path)) {

								int c = 0;
								for (int i = 0; i < path.getAtomCount(); i++)
									if (path.getAtom(i).getSymbol().equals("C"))
										c++;

								if (c > maxPathLength) {
									maxPathLength = c;
									bestStartAtom = f;
									bestEndAtom = g;
								}
							}
						} catch (Exception x) {
							x.printStackTrace();
						}
					}
				}
			}
		}
		for (int i = 0; i < mol.getAtomCount(); i++)
			mol.getAtom(i).setFlag(CDKConstants.VISITED, false);
		logger.fine("Longest chaing in molecule is of length " + maxPathLength
				+ " between atoms " + (bestStartAtom + 1) + " and "
				+ (bestEndAtom + 1));

		return maxPathLength;
	}

	public static QueryAtomContainers getAllGroups() {
		String[] metals = { "Na", "K", "Ca" };
		QueryAtomContainers list = new QueryAtomContainers();
		IAtomContainer m = new org.openscience.cdk.AtomContainer();
		m.addAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON));
		m.setID("Empty fragment");
		list.add(m);
		list.add(methyl());
		list.add(methoxy());
		list.add(ethyl());
		list.add(primaryAmine(false));
		list.add(secondaryAmine(false));
		list.add(tertiaryAmine());
		list.add(hydrochlorideOfAmine(1));
		list.add(sulphateOfAmine(1));
		list.add(cyano());
		list.add(nitro2double());
		list.add(nitro1double());
		list.add(Nnitroso());
		list.add(diAzo());
		list.add(triAzeno());
		list.add(quaternaryNitrogen1(false));
		list.add(carboxylicAcid());
		list.add(acyclic_acetal());
		list.add(acetal());
		list.add(ether());
		list.add(sulphide());
		list.add(mercaptan());
		list.add(thioester());
		list.add(ester());
		list.add(carbonate());
		list.add(anhydride());
		list.add(lactone(false));
		list.add(ketone());
		list.add(aldehyde());
		list.add(alcohol(false));
		list.add(sulphamate(metals));
		list.add(saltOfCarboxylicAcid(metals));
		list.add(sulphonate(metals));
		list.add(sulphate(metals));
		return list;
	}

}

/**
 * 
 * Used when recognising ionic bonds
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-10-22
 */
class ChargeConsumed {
	protected IAtom atom;
	public static String CHARGECONSUMED = "ChargeConsumed";
	protected int charge = 0;

	public ChargeConsumed(IAtom atom) {
		this.atom = atom;
		this.charge = atom.getFormalCharge();
	}

	public void consume(int amount) {
		charge = charge + amount;
	}

	public void consume(ChargeConsumed amount) {
		if (charge == -amount.getCharge()) {
			int newCharge = charge + amount.getCharge();
			amount.consume(charge);
			charge = newCharge;
		} else {
			int minCharge = charge;
			if (Math.abs(amount.getCharge()) < Math.abs(charge))
				minCharge = -amount.getCharge();

			amount.consume(minCharge);
			consume(-minCharge);
		}
	}

	public int getCharge() {
		return charge;
	}

	public boolean compatible(ChargeConsumed c) {
		return (this.charge * c.getCharge()) < 0;
	}

	public String toString() {
		return atom.getSymbol() + "\tFormalCharge=" + atom.getFormalCharge()
				+ "\tCharge Left=" + charge;
	}

}
