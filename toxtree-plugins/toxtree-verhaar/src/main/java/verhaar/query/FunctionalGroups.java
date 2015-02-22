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

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
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
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.ReallyAnyAtom;
import toxTree.query.TopologyAnyBond;
import toxTree.query.TopologyOrderQueryBond;
import ambit2.core.data.MoleculeTools;

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
    public static final String n = "n";
	

	/**
	 * 
	 */
	protected FunctionalGroups() {
		super();
	}
	public static QueryAtomContainer ionicGroup() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(IONICGROUP);
		AnyAtom a1 = new ReallyAnyAtom();
		AnyAtom a2 = new ReallyAnyAtom();
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
			} else a[i] = new ReallyAnyAtom(); 
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
			} else a[i] = new ReallyAnyAtom(); 
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
		IAtom a[] = new IAtom[4];
		for (int i=0;i<a.length;i++) {
			if (i==3) {
				a[i] = new SymbolSetQueryAtom();
				for (int j=0; j < halogens.length;j++)
					((SymbolSetQueryAtom)a[i]).addSymbol(halogens[j]);
			} else a[i] = new ReallyAnyAtom(); 
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
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(PHENOL);
		IAtom[] a = new IAtom[6];
		for (int i=0;i<a.length;i++) {
			a[i] = new SymbolQueryAtom(MoleculeTools.newAtom(builder,"C"));
			q.addAtom(a[i]);
			if (i > 0) {
				q.addBond(new  verhaar.query.AromaticQueryBond((IQueryAtom)a[i],(IQueryAtom)a[i-1]));
			}
		}
		q.addBond(new verhaar.query.AromaticQueryBond((IQueryAtom)a[0],(IQueryAtom)a[5]));
		
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(builder,"O"));
		q.addAtom(o);
		SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(builder,"H"));
		q.addAtom(h);
		q.addBond(new OrderQueryBond(o,h,CDKConstants.BONDORDER_SINGLE));
		q.addBond(new OrderQueryBond(o,(IQueryAtom)a[0],CDKConstants.BONDORDER_SINGLE));
		
		return q;
	}
	public static QueryAtomContainer aniline() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(ANILINE);
		IAtom[] a = new IAtom[6];
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
		IAtom[] a = new IAtom[6];
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

	public static QueryAtomContainer pyridine_character() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID(PYRIDINE);
		IAtom[] a = new IAtom[6];
		for (int i=0;i<a.length;i++) {
			if (i==0) a[i] = new SymbolQueryAtom(new org.openscience.cdk.Atom("N"));
			else {
				
				SymbolSetQueryAtom set = new SymbolSetQueryAtom();
				set.addSymbol("C");
				set.addSymbol("N");
				a[i] = set;
			}
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
		IAtom[] a = new IAtom[5];
		for (int i=0; i < a.length; i++) {
			switch (i) {
			case 0: { a[i] = new ReallyAnyAtom(); break;}
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
	public static IAtomContainer makeNitroPhenol() {
		 IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		  IAtomContainer mol = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());
		  IAtom a1 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a1);
		  IAtom a2 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a2);
		  IAtom a3 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a3);
		  IAtom a4 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a4);
		  IAtom a5 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a5);
		  IAtom a6 = MoleculeTools.newAtom(builder,"C");
		  mol.addAtom(a6);
		  IAtom a7 = MoleculeTools.newAtom(builder,"O");
		  mol.addAtom(a7);
		  IAtom a8 = MoleculeTools.newAtom(builder,"N");
		  mol.addAtom(a8);
		  IAtom a9 = MoleculeTools.newAtom(builder,"O");
		  mol.addAtom(a9);
		  IAtom a10 = MoleculeTools.newAtom(builder,"O");
		  mol.addAtom(a10);
		  IBond b1 = MoleculeTools.newBond(builder, a1, a2, IBond.Order.DOUBLE);
		  mol.addBond(b1);
		  IBond b2 = MoleculeTools.newBond(builder,a2, a3, IBond.Order.SINGLE);
		  mol.addBond(b2);
		  IBond b3 = MoleculeTools.newBond(builder,a3, a4, IBond.Order.DOUBLE);
		  mol.addBond(b3);
		  IBond b4 = MoleculeTools.newBond(builder,a4, a5, IBond.Order.SINGLE);
		  mol.addBond(b4);
		  IBond b5 = MoleculeTools.newBond(builder,a5, a6, IBond.Order.DOUBLE);
		  mol.addBond(b5);
		  IBond b6 = MoleculeTools.newBond(builder,a6, a1, IBond.Order.SINGLE);
		  mol.addBond(b6);
		  IBond b7 = MoleculeTools.newBond(builder,a4, a7, IBond.Order.SINGLE);
		  mol.addBond(b7);
		  IBond b8 = MoleculeTools.newBond(builder,a2, a8, IBond.Order.SINGLE);
		  mol.addBond(b8);
		  IBond b9 = MoleculeTools.newBond(builder,a8, a9, IBond.Order.DOUBLE);
		  mol.addBond(b9);
		  IBond b10 = MoleculeTools.newBond(builder,a8, a10, IBond.Order.DOUBLE);
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
        		queryContainer.addAtom(new ReallyAnyAtom());
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
        AnyAtom r = new ReallyAnyAtom();
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
