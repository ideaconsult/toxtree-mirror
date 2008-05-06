/*
Copyright Nina Jeliazkova (C) 2005-2006  
Contact: nina@acad.bg

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
package verhaar.query;

import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.InverseSymbolSetQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolAndChargeQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolSetQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyOrderQueryBond;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.TopologyAnyBond;
import toxTree.query.TopologyOrderQueryBond;

/**
 * A singleton class providing static methods for various functional groups in {@link verhaar.VerhaarScheme}.
 * In future could be modified to use a different approach rather than static methods.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-30
 */
public class FunctionalGroups extends toxTree.query.FunctionalGroups {
	public static final String IONICGROUP = "IONICGROUP";
	public static final String RINGSUBSTITUTED = "RINGSUBSTITUTED";
	public static final String HALOGEN_BETA_FROM_UNSATURATION = "HALOGEN_BETA_FROM_UNSATURATION";
	public static final String HALOGEN_ALPHA_FROM_UNSATURATION = "HALOGEN_ALPHA_FROM_UNSATURATION";
	public static final String HALOGEN = "HALOGEN";
	public static final String EPOXIDE = "EPOXIDE";
	public static final String AZARIDINE = "AZARIDINE";
	public static final String PEROXIDE = "PEROXIDE";
	public static final String PHENOL = "PHENOL";
	public static final String ANILINE = "ANILINE";
	public static final String PYRIDINE = "PYRIDINE";
	public static final String BENZYLALCOHOL = "BENZYLALCOHOL";
	public static final String KETONE_A_B_UNSATURATED = "KETONE_A_B_UNSATURATED";
	public static final String CYCLICESTER = "CYCLICESTER";
	

	/**
	 * 
	 */
	protected FunctionalGroups() {
		super();
	}
	public static QueryAtomContainer ionicGroup() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(IONICGROUP);
		AnyAtom a1 = new AnyAtom();
		AnyAtom a2 = new AnyAtom();
		q.addAtom(a1);q.addAtom(a2);
		q.addBond(new QueryAssociationBond(a1,a2));
		return q;
	}
	public static QueryAtomContainer ringSubstituted(String substituent) {
		
		QueryAtomContainer q = new QueryAtomContainer();
		if (substituent == null)
			q.setID(RINGSUBSTITUTED);
		else q.setID(RINGSUBSTITUTED+substituent);
		IAtom a[] = new IAtom[3];
		for (int i=0;i<a.length;i++) {
			
			if (i==1) {
				a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
			} else a[i] = new AnyAtom(); 
			q.addAtom(a[i]);
			if (i > 0) {
				TopologyAnyBond b = new TopologyAnyBond(a[i],a[i-1],true);
				q.addBond(b);
			}
		}
		if (substituent != null) {
				SymbolQueryAtom s = new SymbolQueryAtom(
						new org.openscience.cdk.Atom(substituent));
				q.addAtom(s);
				q.addBond(new TopologyAnyBond(a[1],s,false));
			}
		return q;
	}
	/**
	 * Halogen at alpha position from unsaturation.
	 * "X=X[Hal]"
	 * @return
	 */
	public static QueryAtomContainer halogenAtAlphaFromUnsaturation(String[] halogens) {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(HALOGEN_ALPHA_FROM_UNSATURATION);
		IAtom a[] = new IAtom[3];
		for (int i=0;i<a.length;i++) {
			if (i==2) {
				a[i] = new SymbolSetQueryAtom();
				for (int j=0; j < halogens.length;j++)
					((SymbolSetQueryAtom)a[i]).addSymbol(halogens[j]);
			} else a[i] = new AnyAtom(); 
			q.addAtom(a[i]);
			switch (i) {
			case 0: break;
			case 1: {
				q.addBond(new QueryUnsaturatedBond(a[i],a[i-1]));
				break;
			}
			default: {
				q.addBond(new OrderQueryBondAromatic((IQueryAtom)a[i],(IQueryAtom)a[i-1],CDKConstants.BONDORDER_SINGLE,false));
				break;
			}
			}
		}	
		return q;
	}	
	/**
	 * Halogen at beta position from unsaturation.
	 * "X=XX[Hal]"
	 * @return
	 */
	public static QueryAtomContainer halogenAtBetaFromUnsaturation(String[] halogens) {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(HALOGEN_BETA_FROM_UNSATURATION);
		Atom a[] = new Atom[4];
		for (int i=0;i<a.length;i++) {
			if (i==3) {
				a[i] = new SymbolSetQueryAtom();
				for (int j=0; j < halogens.length;j++)
					((SymbolSetQueryAtom)a[i]).addSymbol(halogens[j]);
			} else a[i] = new AnyAtom(); 
			q.addAtom(a[i]);
			switch (i) {
			case 0: break;
			case 1: {
				q.addBond(new QueryUnsaturatedBond(a[i],a[i-1]));
				break;
			}
			default: {
				q.addBond(new OrderQueryBondAromatic((IQueryAtom)a[i],(IQueryAtom)a[i-1],CDKConstants.BONDORDER_SINGLE,false));
				break;
			}
			}
		}	
		return q;
	}
	public static QueryAtomContainer halogen() {
		String[] h = {"Cl","F","Br","I"};
		return halogen(h);
	}
	public static QueryAtomContainer halogen(String[] halogens) {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(HALOGEN);
		SymbolSetQueryAtom h = new SymbolSetQueryAtom();
		h.setSymbol("*");
		for (int j=0; j < halogens.length;j++)
			h.addSymbol(halogens[j]);
		InverseSymbolSetQueryAtom a = new InverseSymbolSetQueryAtom();
		a.setSymbol("*");  //so the bond could be marked
		//a.setProperty(DONTMARK,q.getID());
		q.addAtom(h);
		q.addAtom(a);
		q.addBond(new AnyOrderQueryBond((IQueryAtom)a,(IQueryAtom)h,CDKConstants.BONDORDER_SINGLE));
		return q;
		
	}
	public static QueryAtomContainer epoxide() {
		QueryAtomContainer q = createQuery("C1OC1",EPOXIDE);
		return q;
	}
	public static QueryAtomContainer azaridine() {
		QueryAtomContainer q = createQuery("C1NC1",AZARIDINE);
		return q;
	}
	
	public static QueryAtomContainer peroxide() {
		QueryAtomContainer q = createQuery("OO",PEROXIDE);
		return q;
	}
	public static QueryAtomContainer phenol() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(PHENOL);
		Atom[] a = new Atom[6];
		for (int i=0;i<a.length;i++) {
			a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
			q.addAtom(a[i]);
			if (i > 0) {
				q.addBond(new  verhaar.query.AromaticQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1]));
			}
		}
		q.addBond(new verhaar.query.AromaticQueryBond((IQueryAtom)a[0],(IQueryAtom)a[5]));
		
		SymbolQueryAtom o = new SymbolQueryAtom(new org.openscience.cdk.Atom("O"));
		q.addAtom(o);
		SymbolQueryAtom h = new SymbolQueryAtom(new org.openscience.cdk.Atom("H"));
		q.addAtom(h);
		q.addBond(new OrderQueryBond(o,h,CDKConstants.BONDORDER_SINGLE));
		q.addBond(new OrderQueryBond(o,(IQueryAtom)a[0],CDKConstants.BONDORDER_SINGLE));
		
		return q;
	}
	public static QueryAtomContainer aniline() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(ANILINE);
		Atom[] a = new Atom[6];
		for (int i=0;i<a.length;i++) {
			a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
			q.addAtom(a[i]);
			if (i > 0) {
				q.addBond(new  verhaar.query.AromaticQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1]));
			}
		}
		q.addBond(new verhaar.query.AromaticQueryBond((IQueryAtom)a[0],(IQueryAtom)a[5]));
		
		SymbolQueryAtom o = new SymbolQueryAtom(new org.openscience.cdk.Atom("N"));
		q.addAtom(o);
		SymbolQueryAtom h = new SymbolQueryAtom(new org.openscience.cdk.Atom("H"));
		q.addAtom(h);
		q.addBond(new OrderQueryBond(o,h,CDKConstants.BONDORDER_SINGLE));
		q.addBond(new OrderQueryBond(o,(IQueryAtom)a[0],CDKConstants.BONDORDER_SINGLE));
		return q;		
	}

	public static QueryAtomContainer pyridine() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(PYRIDINE);
		Atom[] a = new Atom[6];
		for (int i=0;i<a.length;i++) {
			if (i==0) a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("N"));
			else a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
			q.addAtom(a[i]);
			if (i > 0) {
				q.addBond(new  verhaar.query.AromaticQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1]));
			}
		}
		q.addBond(new verhaar.query.AromaticQueryBond((IQueryAtom)a[0],(IQueryAtom)a[5]));
		return q;		
	}	
	public static QueryAtomContainer benzylAlcohol() {
		QueryAtomContainer q = createQuery("c1ccccc1CO[H]",BENZYLALCOHOL);
		try {
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(q);
			CDKHueckelAromaticityDetector.detectAromaticity(q);
		} catch (CDKException x) {
			//FunctionalGroups.logger.error(x);
			x.printStackTrace();
		}
		
		return q;
	}
	public static QueryAtomContainer ketone_a_b_unsaturated() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(KETONE_A_B_UNSATURATED);
		Atom[] a = new Atom[5];
		for (int i=0; i < a.length; i++) {
			switch (i) {
			case 0: { a[i] = new AnyAtom(); break;}
			case 1: { a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
					q.addBond(new QueryUnsaturatedBond(a[0],a[1]));
					break;}
			case 2: { 
				a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("C")); 
				q.addBond(new OrderQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1],CDKConstants.BONDORDER_SINGLE));
				break;}			
			case 3: { a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("O"));
					q.addBond(new OrderQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1],CDKConstants.BONDORDER_DOUBLE));
					break;}
			case 4: { 
				a[i] = new SymbolSetQueryAtom();
				((SymbolSetQueryAtom)a[i]).addSymbol("C");
				((SymbolSetQueryAtom)a[i]).addSymbol("H");
				q.addBond(new OrderQueryBond((IQueryAtom)a[i],(IQueryAtom)a[2],CDKConstants.BONDORDER_SINGLE));
				break;}
			}
			q.addAtom(a[i]);
			
		}
		return q;
	}
	public static Molecule makeNitroPhenol() {
		  Molecule mol = new Molecule();
		  Atom a1 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a1);
		  Atom a2 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a2);
		  Atom a3 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a3);
		  Atom a4 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a4);
		  Atom a5 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a5);
		  Atom a6 = new org.openscience.cdk.Atom("C");
		  mol.addAtom(a6);
		  Atom a7 = new org.openscience.cdk.Atom("O");
		  mol.addAtom(a7);
		  Atom a8 = new org.openscience.cdk.Atom("N");
		  mol.addAtom(a8);
		  Atom a9 = new org.openscience.cdk.Atom("O");
		  mol.addAtom(a9);
		  Atom a10 = new org.openscience.cdk.Atom("O");
		  mol.addAtom(a10);
		  Bond b1 = new Bond(a1, a2, IBond.Order.DOUBLE);
		  mol.addBond(b1);
		  Bond b2 = new Bond(a2, a3, IBond.Order.SINGLE);
		  mol.addBond(b2);
		  Bond b3 = new Bond(a3, a4, IBond.Order.DOUBLE);
		  mol.addBond(b3);
		  Bond b4 = new Bond(a4, a5, IBond.Order.SINGLE);
		  mol.addBond(b4);
		  Bond b5 = new Bond(a5, a6, IBond.Order.DOUBLE);
		  mol.addBond(b5);
		  Bond b6 = new Bond(a6, a1, IBond.Order.SINGLE);
		  mol.addBond(b6);
		  Bond b7 = new Bond(a4, a7, IBond.Order.SINGLE);
		  mol.addBond(b7);
		  Bond b8 = new Bond(a2, a8, IBond.Order.SINGLE);
		  mol.addBond(b8);
		  Bond b9 = new Bond(a8, a9, IBond.Order.DOUBLE);
		  mol.addBond(b9);
		  Bond b10 = new Bond(a8, a10, IBond.Order.DOUBLE);
		  mol.addBond(b10);
		  return mol;
		}
	public static QueryAtomContainer createAutoQueryContainer(String smiles) {
		return createAutoQueryContainer(createAtomContainer(smiles,false,smiles));
	}
    public static QueryAtomContainer createAutoQueryContainer(IAtomContainer container) {
        QueryAtomContainer queryContainer = new QueryAtomContainer();
        
        for (int i = 0; i < container.getAtomCount(); i++) {
        	IAtom atom = container.getAtom(i);
        	if (atom instanceof IPseudoAtom)
        		queryContainer.addAtom(new AnyAtom());
        	else if (atom.getSymbol().equals("X")) { //halogen
        		SymbolSetQueryAtom a = new SymbolSetQueryAtom();
        		a.addSymbol("Cl");
        		a.addSymbol("Br");
        		a.addSymbol("F");
        		a.addSymbol("I");
        	} else queryContainer.addAtom(new SymbolAndChargeQueryAtom(atom));
        }
        
        for (int i = 0; i < container.getBondCount(); i++) {
        	IBond bond = container.getBond(i);
            int index1 = container.getAtomNumber(bond.getAtom(0));
            int index2 = container.getAtomNumber(bond.getAtom(1));
            if (bond.getFlag(CDKConstants.ISAROMATIC)) {
                queryContainer.addBond(new verhaar.query.AromaticQueryBond((IQueryAtom) queryContainer.getAtom(index1),
                                      (IQueryAtom) queryContainer.getAtom(index2)));
            } else {
                queryContainer.addBond(new OrderQueryBond((IQueryAtom) queryContainer.getAtom(index1),
                                      (IQueryAtom) queryContainer.getAtom(index2),
                                      bond.getOrder()));
            }
        }
        return queryContainer;
    }    
    //TODO cyclic sulphonic/sulphuric esters
    public static QueryAtomContainer cyclicEster() {
        QueryAtomContainer query = new QueryAtomContainer();
        query.setID(CYCLICESTER);        
        AnyAtom r = new AnyAtom();
        r.setProperty(DONTMARK,query.getID());
        InverseSymbolSetQueryAtom e = new InverseSymbolSetQueryAtom();
        e.addSymbol("H");
        e.setProperty(DONTMARK,query.getID());
        
        SymbolQueryAtom c = new SymbolQueryAtom(new org.openscience.cdk.Atom("C"));
        SymbolQueryAtom o1 = new SymbolQueryAtom(new org.openscience.cdk.Atom("O"));
        SymbolQueryAtom o2 = new SymbolQueryAtom(new org.openscience.cdk.Atom("O"));
        query.addAtom(r); query.addAtom(e); query.addAtom(c);
        query.addAtom(o1); query.addAtom(o2);
        query.addBond(new OrderQueryBond(r, c, CDKConstants.BONDORDER_SINGLE));
        query.addBond(new TopologyOrderQueryBond(c, o1, CDKConstants.BONDORDER_SINGLE,true));
        query.addBond(new OrderQueryBond(c, o2, CDKConstants.BONDORDER_DOUBLE));
        query.addBond(new OrderQueryBond(o1, e, CDKConstants.BONDORDER_SINGLE));
        return query;
    }
}
