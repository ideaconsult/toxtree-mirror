/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.descriptors;

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
    //protected static String _RING="RING";
    protected static Boolean yes = new Boolean(true);
    protected static String atom_C = "C";
	protected QueryAtomContainer ringQuery;
	protected UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
	protected static Logger logger = Logger.getLogger(SubstituentExtractor.class.getName());
	public SubstituentExtractor() {
		this(null);
	}
	public SubstituentExtractor(QueryAtomContainer ringQuery) {
		super();
		setRingQuery(ringQuery);
	}
	/**
	 * Note: Aromaticity flags should already be set before running this method.
	 * @param a
	 * @return
	 * @throws Exception
	 */
	public Hashtable<String,IAtomContainerSet> extractSubstituents(IAtomContainer a) throws CDKException {

		logger.finer("extract substituents");
		if (ringQuery == null) throw new CDKException("Query not assigned");
        List list = uit.getSubgraphAtomsMaps(a,ringQuery);
        list = getUniqueAtomMaps(list);
        Hashtable<String,IAtomContainerSet> substituents = new Hashtable<String,IAtomContainerSet>();            
        
        for (int l=0; l < list.size();l++) {
            String mark=FunctionalGroups.RING_NUMBERING+"_" + Integer.toString(l+1);
            if (markAtomsInRing(mark,(List)list.get(l),a, ringQuery)) {
            	/*
               IAtom anchor = null;
                for (int i=0; i < a.getAtomCount();i++) {
                    IAtom atom = a.getAtom(i);
                    //System.out.println(atom.getSymbol() + "\t" + atom.getProperty(FunctionalGroups.RING_NUMBERING));
                    SubstituentPosition position = SubstituentExtractor.getSubstituentNumber(mark,a,i);
                    if (atom_C.equals(atom.getSymbol()) &&
                            (position != null) && (position.getPosition() == 1)) 
                            anchor = atom;
                }
                */
                IAtomContainer mc = cloneDiscardRingAtomAndBonds(a,mark);
                IAtomContainerSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
                
                substituents.put(mark,s);
            } else 
                throw new CDKException(ringQuery.toString() + "\t not found.");
        }
        return substituents;        
	}
    protected List getUniqueAtomMaps(List list) {
        int[] prev = null;
        int[] next = null;
        for (int i=list.size()-1; i >= 0;i--) {
            List sublist = (List)list.get(i);
            prev = next;
            next = new int[sublist.size()];
            for (int j=0; j < sublist.size();j++) {
                next[j] = ((RMap)sublist.get(j)).getId1(); 
                //System.out.print(((RMap)sublist.get(j)).getId1());
                //System.out.print('\t');
            }
            Arrays.sort(next);
            //System.out.print('\n');
            if ((prev != null) && (next != null) && Arrays.equals(next,prev)) {
                //System.out.println("Same");
                list.remove(i);
            }
            
            
        }
        return list;
    }
	/**
     * 
     * @param list  List of RMap
     * @param mol
     * @param q
     * @return
     * @throws CDKException
	 */
	public static boolean markAtomsInRing(String property,List list,IAtomContainer mol, QueryAtomContainer q) throws CDKException{
			if (list != null) {
				for (int i=0; i < list.size();i++) {
					RMap map = (RMap)list.get(i);
					if (map.getId2()>=0) {
                        //System.out.print("Atom\t" + mol.getAtom(map.getId1()).getSymbol() + " FUSED_RINGS=" + mol.getAtom(map.getId1()).getProperty("FUSED_RINGS") + " MR=" + mol.getAtom(map.getId1()).getProperty("MR")  + " RING=" + q.getAtom(map.getId2()).getProperty(SubstituentExtractor._RING)  + " QID "+ q.getAtom(map.getId2()).getID() + " [ " + map.getId1() + " <-> " + map.getId2() + " ] ");                    

						SubstituentPosition p = SubstituentExtractor.getSubstituentNumber(FunctionalGroups.RING_NUMBERING,q,map.getId2());
						if (p != null) {
							//System.out.println("\tNumber\t"+p);
                            SubstituentExtractor.setSubstituentNumber(property,mol,map.getId1(),p);
                            mol.getAtom(map.getId1()).setID(Integer.toString((map.getId1())));
						} 
						//System.out.println();
					}
				}
				return true;
			}	
			else return false;
	}
	
    public static IAtomContainer cloneDiscardRingAtomAndBonds(
    		IAtomContainer ac,String mark) {
        IAtomContainer result = new org.openscience.cdk.AtomContainer();
        Hashtable<IAtom,IAtom> table = new Hashtable<IAtom,IAtom>();
        List<IBond> forbiddenBonds = new ArrayList<IBond>();
        //System.out.println("ring " + ring.getAtomCount());
        for (int i =0; i < ac.getAtomCount(); i++) {
        	IAtom a = ac.getAtom(i);
            SubstituentPosition pr = getSubstituentNumber(mark,ac,i);
            if (pr != null)
                if (pr.isRing()) {
                    //System.out.println("Skipping "+pr);
                    continue;
                } else { 
                    List<IAtom> atoms = new ArrayList<IAtom>();
                    atoms.add(a);
                    boolean breakBond = breakBond(ac, a, mark, pr.getPosition(),atoms);
                    List<IBond> bonds = ac.getConnectedBondsList(a);
                    //System.out.print("bonds "+pr);
                    for (int j=0; j < bonds.size(); j++) {
                        /*
                        System.out.print(bonds.get(j).getAtom(0).getSymbol());
                        System.out.print(bonds.get(j).getAtom(1).getSymbol());
                        System.out.print(' ');
                        System.out.print(bonds.get(j).getFlag(CDKConstants.ISINRING));
                        System.out.print('\t');
                        */
                        if (breakBond && bonds.get(j).getFlag(CDKConstants.ISINRING)) {
                            
                            forbiddenBonds.add(bonds.get(j));
                        }
                    }
                    // System.out.println();
                    
                }
            table.put(a,a);
            result.addAtom(a);
        }
        
        for (int i =0; i < ac.getBondCount(); i++) {
            IBond b = ac.getBond(i);
            
            
            IAtom a1 = (IAtom) table.get(b.getAtom(0));
            IAtom a2 = (IAtom) table.get(b.getAtom(1));
            
            if ((a1 != null) && (a2 != null)) 
                if (forbiddenBonds.indexOf(b) == -1) {
                    result.addBond(b);    
                } else {
                    IAtom a = null;
                    if (a1 != null) a = a1;
                    else if (a2 != null) a = a2;                    
                    IAtom h = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.HYDROGEN);
                    result.addAtom(h);
                    result.addBond(
                    		MoleculeTools.newBond(SilentChemObjectBuilder.getInstance(),a, h, CDKConstants.BONDORDER_SINGLE));                    
                }
            else {
            	
            	IAtom a = null;
            	if (a1 != null) a = a1;
            	else if (a2 != null) a = a2;
	            	
	            if (a != null) {
	            	//System.out.println("Substituent starting with "+a.getSymbol() + " " + a.getProperties());
	            	//IAtom r = SilentChemObjectBuilder.getInstance().newAtom("R");
                    IAtom r = MoleculeTools.newPseudoAtom(SilentChemObjectBuilder.getInstance(),"R");
	            	result.addAtom(r);
	            	result.addBond(MoleculeTools.newBond(SilentChemObjectBuilder.getInstance(),a, r, b.getOrder()));
	            }
            }
        }
        return result;
    }    
	protected static boolean breakBond(IAtomContainer ac, IAtom a, String mark, int position, List<IAtom> atoms) {
        List<IBond> bonds = ac.getConnectedBondsList(a);
        for (int i=0; i < bonds.size(); i++) {
            IBond b = bonds.get(i);
            for (int j=0; j < b.getAtomCount();j++) {
                IAtom atom = b.getAtom(j);
                if (atoms.indexOf(atom)>=0) continue;
                SubstituentPosition pos = SubstituentExtractor.getSubstituentNumber(mark, atom);
                if (pos != null) {
                    if (pos.getPosition() == position) {
                        atoms.add(atom);
                        continue;
                    }
                    if (!pos.isRing() && (pos.getPosition() != position)) {
                        //that is, we found a cycle connecting to another substituent
                        //System.out.println("Found cycle "+position + " "+pos);
                        return true;
                    }
                }
                atoms.add(atom);
                if (breakBond(ac, atom, mark, position,atoms))
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
    public static SubstituentPosition getSubstituentNumber(String property,IAtomContainer m, int atom) {
        return getSubstituentNumber(property,  m.getAtom(atom));
    }    
    public static SubstituentPosition getSubstituentNumber(String property,IAtom atom) {
        Object o = atom.getProperty(property);
        if (o == null) return null;
        else if (o instanceof SubstituentPosition) return (SubstituentPosition) o;
        else {
            logger.finer("Expected Integer type instead of "+o.getClass().getName());
            return null;
        }
    }
    public static void setSubstituentNumber(String property,IAtomContainer m, int atom,SubstituentPosition position) {
        setSubstituentNumber(property,m.getAtom(atom),position);    
    }    
    public static void setSubstituentNumber(String property, IAtom atom,SubstituentPosition position) {
        atom.setProperty(property,position);    
    }    
        
}
