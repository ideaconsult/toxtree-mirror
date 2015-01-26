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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyOrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.smarts.AromaticQueryBond;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.data.MoleculesFile;
import toxTree.io.Tools;
import toxTree.query.FunctionalGroups;
import toxTree.query.TopologySymbolQueryAtom;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternCDK;

/**
 * returns "MR","LSTM","B1STM","B5STM" for substituents at postitions 1 to 6 (amine group at 1 position).
 * TODO verify how substituted amine group should be treated (params of amine group + substituent, only substituent, largest substituent, average, etc.)
 * <ol>
 * <li>the functional amino group is always in position 1, additional amino groups are treated as substituents;
 * <li>if more than one amino group is present, the -N with lowest steric hindrance ( 1. primary amines; 2. secondary amines; 3. tertiary amines; in increasing steric hindrance order) is considered to be the functional group;
 * <li>if more than one amino group is present, and all of them have the same steric hindrance on the -N,  the one that is considered to be the functional group is that which has a substituent in an adjacent position (ortho-substituent).
 * <li>if two amino groups in ortho position are present and there are other substituents attached to the ring, the amino group that is considered to be the functional group is that which allow to assign the minimum position number to the substituent
 * <li>when the numbering can go in two opposite directions, the substituent position with highest steric hindrance is given the lowest substitution number
 * <li>in case the amino group is attached to more then one aromatic ring,
 * the more extended aromatic system is to be considered as the one bearing the functional amino group;
 * <li>in case the amino group is attached to more than one aromatic ring, 
 * with the aromatic systems equally extended,  the ring bearing the
 * functional amino group is chosen in such a way that the sterimol is the highest possible."
 *  
 * </ol> 
 * @author Nina Jeliazkova
 *
 */
public class AromaticAmineSubstituentsDescriptor extends SubstituentsDescriptor {
    protected static String AromAmine="aromamine";
    protected static String MR="MR";
    protected static String H="H";
    protected static String N="N";
    protected static String R="R";
	protected MoleculesFile lookup;
    protected int[] reverse_numbers = {1,6,5,4,3,2};
    Object[][] fusedrings_patterns;
    
	public AromaticAmineSubstituentsDescriptor() {
		super(aromaticAmine(FunctionalGroups.RING_NUMBERING));
		setDescriptorNames(new String[] {MR,"LSTM","B1STM","B5STM"});
		try {
			lookup = new MoleculesFile(Tools.getFileFromResource("substituents.sdf"),DefaultChemObjectBuilder.getInstance());
            fusedrings_patterns = new Object[][] {
                    {AromAmine,new Boolean(true),new SmartsPatternCDK("Nc1c(~[*,#1])c(~[*,#1])c(~[*,#1])c(~[*,#1])c1(~[*,#1])")},                    
                    {MR,new Double(0.8),new SmartsPatternCDK("[cR2,cR3](:c)(:c)(:c)")},                    
                    //{MR,new Double(0.56),new SmartsPatternCDK("[cr5;cR2](:c)(:c)(:,-[#6])")},
                    {MR,new Double(0.56),new SmartsPatternCDK("[cr5;cR2](:c)(:c)(:,-[#6])")},
                    {MR,new Double(0.69),new SmartsPatternCDK("[cr5;cR2](:c)(:c)(:,-[$([#6]=O)])")},
                    {MR,new Double(0.54),new SmartsPatternCDK("[cr5;cR2](:c)(:c)(:,-[$([#7][#1])])")}, 
                    {MR,new Double(0.65),new SmartsPatternCDK("[cr6]:[cR2r6]:[nR1r6]")}, //C=1C=CC=2N=CC=CC=2(C=1)")},
                 
            };            
		} catch (Exception x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
			lookup = null;
		}
		
	}
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "Molar refractivity and Sterimol descriptors of aromatic amine substituents (at postitions 1 to 6 , where amine group is at 1 position)",
                this.getClass().getName(),
                "$Id: AromaticAmineSubstituentsDescriptor.java 6171 2007-08-06 14:09:00 +0000 (Mon, 06 August 2007) nina $",
                "Toxtree plugin");
	}	
	@Override
	public DescriptorValue calculate(IAtomContainer a)  {
        try {
            //checking for fused rings and assigning MR
            for (int p = 0; p < fusedrings_patterns.length;p++) {
                int matches = ((ISmartsPattern)fusedrings_patterns[p][2]).match(a);
                List<List<Integer>> list = ((ISmartsPattern)fusedrings_patterns[p][2]).getUniqueMatchingAtoms();
                /*
                System.out.print(matches);
                System.out.print('\t');
                System.out.print(fusedrings_patterns[p][0]);
                System.out.print('=');
                System.out.print(fusedrings_patterns[p][1]);
                System.out.print('\t');
                System.out.println(list.size());
                */
                for (int j=0; j < list.size();j++) {
                    List<Integer> l = list.get(j); 
                    boolean ok = true;
                    //This is to make sure that only atoms of aromatic amine (and substituents) will be marked
                    for (int k=0; k < l.size();k++) {
                        IAtom atom = a.getAtom(l.get(k));
                        ok = ok && ((p==0) || (atom.getProperty(AromAmine) != null));
                        if (!ok) break;
                    }   
                    
                    if (ok)
                        for (int k=0; k < l.size();k++) {
                            IAtom atom = a.getAtom(l.get(k));
                            atom.setProperty(fusedrings_patterns[p][0],fusedrings_patterns[p][1]);
                            /*
                                System.out.print(a.getAtom(l.get(k)).getSymbol());
                                System.out.print('[');
                                System.out.print(l.get(k));
                                System.out.print(']');
                                System.out.print('\t');
                              */
                            }
                        }   
                    //System.out.print('\n');
      
            }
            return super.calculate(a);
        } catch (SMARTSException x) {
    		return new DescriptorValue(getSpecification(), getParameterNames(),
    				getParameters(), null, null ,x);
        }
    }
	@Override
	public DescriptorValue calculate(IAtomContainerSet substituents, String mark) throws CDKException {
		if (lookup == null) throw new CDKException("Substituents list not defined!");
		double values[][] = new double[getDescriptorNames().length][6];
		for (int i=0; i < getDescriptorNames().length;i++) 
			for (int j=0; j <6;j++) values[i][j]=Double.NaN; 
		
        //boolean reverse = reverseNumbers(substituents,mark);
        boolean reverse = false;
		for (int k = 0; k < substituents.getAtomContainerCount(); k++) {
            logger.finer("Substituent "+k);
			IAtomContainer m = substituents.getAtomContainer(k);
		    if (m!=null) {
                SubstituentPosition place = null;
			    for (int j=0;j <m.getAtomCount();j++) {
			    	place = SubstituentExtractor.getSubstituentNumber(mark,m,j);
                    IMolecule substituent = (IMolecule)m;

			    	if (place != null)  
			    	try {
                        int p = place.getPosition();
                        if (p==1) {
                            //amino group
                            substituent = getAminoGroupSubstituents(m,j);
                        } else { 
                            //if (reverse)  p = reverse_numbers[p-1];
                            //if assigned from fused rings check above
                            Object mr = m.getAtom(j).getProperty(MR);
                            if ((mr != null) && (mr instanceof Double)) {
                                values[0][p-1]= ((Number)mr).doubleValue();
                                logger.finer("Fused rings at position "+p + " atom "+ j + " MR=" + ((Number)mr).doubleValue());
                                continue;
                            }
                        }
                        //logger.debug("Position "+place + " atom "+ j);
			    		int index = lookup.find(substituent);
                        
			    		if (index > -1) {
                            IAtomContainer mol = lookup.getAtomContainer(index);   
			    			for (int i=0; i < getDescriptorNames().length;i++) {
			    				Object d = lookup.getProperty(index,getDescriptorNames()[i]);
			    				if (d != null) {
                                        double v = Double.NaN;
                                        if (d instanceof Number) {
                                            v = ((Number)d).doubleValue();
                                            values[i][p-1]= v;
                                        } else  try {
                                            v = Double.parseDouble(d.toString());
                                            values[i][p-1]= v;
                                        } catch (NumberFormatException x) {
                                            v = Double.NaN;    
                                        }
		    							logger.finer((mol.getProperty("Group") +" "+mol.getProperty("Position") +" "+getDescriptorNames()[i] + "_" + Integer.toString(p)+ " = " +v));
			    					}
			    			}
                        }

                        
			    	} catch (Exception x) {
                        logger.log(Level.SEVERE,x.getMessage(),x);
			    	}
              
			    } 
		    }
		}

		/*
        for (int n=0; n < 6; n++) {
            System.out.print(values[0][n]);
            System.out.print('\t');
            }
            System.out.println();      
          */  
		DoubleArrayResult results = new DoubleArrayResult();		

		String[] d = new String[6*getDescriptorNames().length];
		for (int j=0; j < 6;j++)
			for (int i=0; i < getDescriptorNames().length;i++) {
				//if (count[i]>1) values[i][0] /= count[i];
				d[j*getDescriptorNames().length+i] = getDescriptorNames()[i]+Integer.toString(j+1);
				results.add(values[i][j]);
			}		
		return new DescriptorValue(getSpecification(), getParameterNames(),
			getParameters(), results, d );
	}
	protected IMolecule getAminoGroupSubstituents(IAtomContainer ac,int natom) throws CDKException {
        IMolecule aminogroup_subst = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());
        List<IAtom> neighbors = ac.getConnectedAtomsList(ac.getAtom(natom));
        
        for (int i=0; i< ac.getAtomCount();i++) {
            IAtom a = ac.getAtom(i);
            if (a instanceof IPseudoAtom) continue;
            if (i==natom) continue;
            aminogroup_subst.addAtom(a);
        }
        IAtom nitroAtom = ac.getAtom(natom);
        for (int j=0; j< ac.getBondCount();j++) {
            IBond b = ac.getBond(j);
            //System.out.println("Examine bond " + b.getAtom(0).getSymbol() + " " +  b.getAtom(1).getSymbol());            
            boolean addBond = true;
            for (int i=0; i< b.getAtomCount();i++) {
                IAtom a = b.getAtom(i);
                if (a instanceof IPseudoAtom) {
                    addBond = false;
                    break;
                }
                if (a == nitroAtom) {
                    addBond = false;
                    IAtom r = MoleculeTools.newPseudoAtom(SilentChemObjectBuilder.getInstance(),R);
                    aminogroup_subst.addAtom(r);
                    IAtom otherAtom = b.getConnectedAtom(a);
                    aminogroup_subst.addBond(
                    		MoleculeTools.newBond(SilentChemObjectBuilder.getInstance(),
                            otherAtom, r, b.getOrder()));
              //      System.out.println("Adding bond " + a.getSymbol() + " " + otherAtom.getSymbol());
                    break;
                }
            }
            if (addBond) {
                IBond newBond = MoleculeTools.newBond(SilentChemObjectBuilder.getInstance(),
                        b.getAtom(0), b.getAtom(1), b.getOrder());
                aminogroup_subst.addBond(newBond);
            }
        }
        IMoleculeSet  s = ConnectivityChecker.partitionIntoMolecules(aminogroup_subst);
        IMolecule m = null;
        int size = 0;
        for (int i=0; i < s.getMoleculeCount();i++) {
            IMolecule a = s.getMolecule(i);
            int asize = 0;
            
            for (int j=0; j < a.getAtomCount();j++)
                if (!H.equals(a.getAtom(j).getSymbol()))
                        asize ++;
            
            if (size < asize) {
                size = asize;
                m = a;
            }
        }
        return m;    
    }
    public String select(Hashtable<String,IAtomContainerSet> substituents) throws CDKException {
        if (substituents == null) 
            throw new CDKException("Substituents null!");
        else {
            Enumeration<String> e = substituents.keys();

            double[] order = null;
            
            String selectedMark = null; 
            while (e.hasMoreElements()) {
                String mark = e.nextElement();
                //if (substituents.size() == 1) return mark;
                //otherwise calculate amino group order
                IAtomContainerSet subst = substituents.get(mark);
                double neworder[] = getAminoGroupOrder(subst,mark);
                //System.out.println(" order "+neworder[0]+'-'+neworder[1]+ '-'+ neworder[2]+ mark);                
                if (order == null) {
                    selectedMark = mark;
                    order = neworder;
                } else {
                    boolean less = false;
                    for (int i=0; i < 3; i++) 
                        if (neworder[i]<order[i]) {
                            less = true;
                            break;
                        } else if (neworder[i]>order[i]) {
                            less = false;
                            break;
                        }
                    
                    if (less) {
                        selectedMark = mark;
                        order = neworder;                        
                    }
                }
            }
            //System.out.println("Number of aromatic amines: " + substituents.size() + " selected " + selectedMark);            
            if (selectedMark == null)
                throw new CDKException("No substituents!");
            else
                return selectedMark;    
        }
           
    }
    /*
    public boolean reverseNumbers(IAtomContainerSet substituents,String mark) throws CDKException {
        double[] positions = new double[6];
        for (int i=0; i < 6; i++) positions[i] = 0;
        
        for (int k = 0; k < substituents.getAtomContainerCount(); k++) {
            IAtomContainer m = substituents.getAtomContainer(k);
            if (m!=null) 
                for (int j=0;j <m.getAtomCount();j++) {
                    SubstituentPosition place = SubstituentExtractor.getSubstituentNumber(mark,m,j);
                    if (place != null) {  
                        int p = place.getPosition();
                        if (H.equals(m.getAtom(j).getSymbol()))
                           positions[p-1] = 0;
                        else {
                            Object o = m.getAtom(j).getProperty(MR);
                            if (m.getAtom(j).getProperty(MR) != null) {//ring
                                if (o instanceof Number)
                                    positions[p-1]=((Number)o).doubleValue();
                                else positions[p-1] = 1;
                            } else {
                                positions[p-1]=0;
                                for (int n=0; n< m.getAtomCount();n++)
                                    if (!H.equals(m.getAtom(n).getSymbol()))
                                        positions[p-1]++;
                            }                            
                            //positions[p-1] = m.getAtomCount();
                        }
                        System.out.println(k+ " pos " + (p-1) + " atoms " + positions[p-1]);
                    }
                }
                
        }
        boolean reverse = false;
        for (int i=1; i < 3; i++) 
            if (positions[i] > positions[6-i]) 
                break;
            else if (positions[i] < positions[6-i]) {
                reverse = true;
                break;
            }
        if (reverse)
            System.out.println("Reverse numbering");
        return reverse;
    }
    */
    /**
     * If more than one amino group is present, the -N with lowest steric hindrance ( 1. primary amines; 2. secondary amines; 3. tertiary amines; in increasing steric hindrance order) is considered to be the functional group;
     * <br> 
     * If more than one amino group is present, and all of them have the same steric hindrance on the -N,  the one that is considered to be the functional group is that which has a substituent in an adjacent position (ortho-substituent).
     * @param substituents
     * @param mark the name of the property under which the substituent number is stored
     * @return number, ordering the amino group as explained above. The number is composed as A + 1/B,
     * where A = 10, 20 or 30 for primary, secondary or tertiary amine. 
     * B = 1 if there are no ortho substituents to amine group; B=2 if there is one ortho substituents and 
     * B=3 if there are two ortho substituents. The size of ortho substituents is not taken into account.
     * This number effectively orders the amino groups as explained above.
     * 
     */
    /*
    protected double getAminoGroupOrder(IAtomContainerSet substituents,String mark) {
        
        double order = 0; 
        double ortho = 1;
        double amino_ortho = 0;
        for (int k = 0; k < substituents.getAtomContainerCount(); k++) {
            IAtomContainer m = substituents.getAtomContainer(k);
            if (m!=null) 
                for (int j=0;j <m.getAtomCount();j++) {
                    SubstituentPosition place = SubstituentExtractor.getSubstituentNumber(mark,m,j);
                    if (place == null) continue;
                    if ((place.getPosition() == 2) || (place.getPosition() == 6)) {
                        System.out.println(mark + " ortho " + m.getAtom(j).getSymbol());
                        if (!H.equals(m.getAtom(j).getSymbol())) ortho ++;
                        if (N.equals(m.getAtom(j).getSymbol())) amino_ortho = place.getPosition(); 
                    } else if (place.getPosition() == 1) {
                        //found an amine
                        System.out.print(m.getAtom(j).getSymbol());
                        List<IAtom> neighbors = m.getConnectedAtomsList(m.getAtom(j));
                        for (int i=0; i < neighbors.size(); i++) {
                            IAtom n = neighbors.get(i); 
                            if ((n instanceof IPseudoAtom) || (H.equals(n.getSymbol()))) continue;
                            else order = order + 1;
                            System.out.print('\t');                            
                            System.out.print(neighbors.get(i).getSymbol());
                        }
                        System.out.println();   
                    } else {
                    	System.out.println(mark + '\t' + place + '\t' +  m.getAtom(j).getSymbol());
                    }
                }
        }    
        System.out.println("Amine Order "+order + " Ortho "+ortho + " Amino group at ortho position "+amino_ortho);
        return order*10 + 1/ortho;
    }
    */
    protected double[] getAminoGroupOrder(IAtomContainerSet substituents,String mark) {
        ISubstituentAction<Double> action = new ISubstituentAction<Double>() {
        	public Double processSubstituent(IAtomContainer m,String mark, SubstituentPosition place, int j) {

                if (place.getPosition() == 1) {
                    //found an amine
                    //System.out.print(m.getAtom(j).getSymbol());
                    List<IAtom> neighbors = m.getConnectedAtomsList(m.getAtom(j));

                    double order = 0; 
                    for (int i=0; i < neighbors.size(); i++) {
                        IAtom n = neighbors.get(i); 
                        if ((n instanceof IPseudoAtom) || (H.equals(n.getSymbol()))) continue;
                        else order = order + 100;
                        Object ringsizes = n.getProperty(CDKConstants.RING_SIZES);
                        //penalty for an extended aromatic system as a substituent 
                        if ((ringsizes != null) && (ringsizes instanceof ArrayList) && (n.getFlag(CDKConstants.ISAROMATIC))) 
                            order += ((ArrayList) ringsizes).size();
                        
                        //System.out.println(mark + " " + ringsizes +  " N::"+ m.getAtomCount());
                        //System.out.print('\t');                            
                        //System.out.print(neighbors.get(i).getSymbol());
                    }
                    //System.out.println();
                    return order;
                } else        		
	                if (H.equals(m.getAtom(j).getSymbol()))
	                   return 0.0;
	                else {
	                    Object o = m.getAtom(j).getProperty(MR);
	                    if (m.getAtom(j).getProperty(MR) != null) {//ring
	                        if (o instanceof Number)
	                            return 1.0/((Number)o).doubleValue();
	                        else return 1.0;
	                    } else {
	                        double p = 0;
	                        for (int n=0; n< m.getAtomCount();n++) {
	                            if (m.getAtom(n) instanceof IPseudoAtom) continue;
	                            else if (!H.equals(m.getAtom(n).getSymbol()))
	                                p += 10;
	                            if (N.equals(m.getAtom(n).getSymbol()))
	                            	p += 5;
	                        }    	
	                        return p;
	                    }                            
	                    //positions[p-1] = m.getAtomCount();
	                }

                
 
 
        	}
        };
        double[] positions = new double[6];
        for (int i=0; i < 6; i++) positions[i] = 0;        
        
        iterateSubstituents(substituents, mark, action,positions);

        boolean reverse = false;
        for (int i=1; i < 3; i++) 
            if (positions[i] > positions[6-i]) 
                break;
            else if (positions[i] < positions[6-i]) {
                reverse = true;
                break;
            }
        if (reverse) {
        	//System.out.println("Reverse numbering");
        	ISubstituentAction<Double> renumberAction = new ISubstituentAction<Double>() {
        		public Double processSubstituent(IAtomContainer substituent, String mark, SubstituentPosition place, int atomIndex) {
        			if (place.getPosition() > 1) {
        				SubstituentPosition newp = new SubstituentPosition(reverse_numbers[place.getPosition()-1],place.isRing());
        				SubstituentExtractor.setSubstituentNumber(mark,substituent,atomIndex,newp);
        			}
        			return 0.0;
        		}
        	};
        	iterateSubstituents(substituents, mark, renumberAction,null);
        } 
        //System.out.print("Positions\t");
        /*
         *  subrule iv) if two amino groups in ortho position are present and there are other substituents 
         *  attached to the ring, the amino group that is considered to be the functional group 
         *  is that which allow to assign the minimum position number to the substituent
         */
        int subrule4 = 0;
        for (int i=0; i < 6; i++) {
            /*
        	if (reverse)
        		System.out.print(positions[reverse_numbers[i]-1]);
        	else	
        		System.out.print(positions[i]);
        	System.out.print('\t');
            */
        	if ((i>2) && (positions[i]>0)) subrule4++; 
        }
        //System.out.print('\n');        
        double ortho = 1 + positions[1] + positions[5];
        //if (positions[1]>0) ortho ++;
        //if (positions[5]>0) ortho ++;
        double corder = positions[0]*1000 + 100.0/(ortho) + 1.0/subrule4;
        //System.out.println(mark + "\tAmine Order "+positions[0] + " Ortho "+(ortho) + " subrule4 "+ subrule4 + " c" + corder);
        double[] order = new double[3];
        order[0] = positions[0];
        order[1] = 1.0/ortho;
        if (subrule4 == 0) order[2] = 0;
        else order[2] = 1.0/subrule4;
                             
        return order;        


    }    
	
    protected void iterateSubstituents(IAtomContainerSet substituents,String mark,ISubstituentAction<Double> action,double[] positions) {
        
        for (int k = 0; k < substituents.getAtomContainerCount(); k++) {
            IAtomContainer m = substituents.getAtomContainer(k);
            if (m!=null) 
                for (int j=0;j <m.getAtomCount();j++) {
                    SubstituentPosition place = SubstituentExtractor.getSubstituentNumber(mark,m,j);
                    if (place != null) {
                    	Double o = action.processSubstituent(m,mark,place,j);
                    	if (positions != null)
                    		positions[place.getPosition()-1] = o;
                    }	
                }
        }    
    }
        
    public static QueryAtomContainer aromaticAmine(String mark) {
        QueryAtomContainer query = new QueryAtomContainer() {
        	/**
	     * 
	     */
	    private static final long serialVersionUID = -7083727815979524493L;

		@Override
        	public String toString() {
        		return getID();
        	}
        	
        };
        query.setID(FunctionalGroups.AROMATIC_AMINE);
        IQueryAtom[] ring = new IQueryAtom[6];
        
        IAtom a  = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN);
        a.setFormalCharge(0);
        
        TopologySymbolQueryAtom n = new TopologySymbolQueryAtom(a,false);
        SubstituentExtractor.setSubstituentNumber(mark,n,new SubstituentPosition(1,false));
        n.setID("N");
        query.addAtom(n);
        //ring
        for (int i=0; i < 6; i++) {
        	//commented for test only
            ring[i] = new TopologySymbolQueryAtom(
            		MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON),true);

        	//ring[i] = new AnyAtom();ring[i].setSymbol(R);
            ring[i].setID(Integer.toString(i+1));
            SubstituentExtractor.setSubstituentNumber(mark,ring[i],new SubstituentPosition(i+1,true));
            //ring[i].setProperty(SubstituentExtractor._RING,SubstituentExtractor.yes);
            query.addAtom(ring[i]);
            if (i>0) {
                query.addBond(new AromaticQueryBond(ring[i], ring[i-1],CDKConstants.BONDORDER_SINGLE));
            }
        }
        query.addBond(new AromaticQueryBond(ring[0], ring[5],CDKConstants.BONDORDER_SINGLE));
        query.addBond(new OrderQueryBond(ring[0], n, CDKConstants.BONDORDER_SINGLE));
        
        
        AnyAtom r = new ReallyAnyAtom();
        r.setSymbol(R);r.setID("R1");
        
        query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));
        r = new ReallyAnyAtom();
        r.setSymbol(R);r.setID("R2");
        query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));        
        //substituents
        
        for (int i=1; i < 6; i++) {
            AnyAtom any = new ReallyAnyAtom(); any.setSymbol(R);
            SubstituentExtractor.setSubstituentNumber(mark,any,new SubstituentPosition(i+1,false));
            any.setID("Subst"+Integer.toString(i+1));
            query.addAtom(any);
            query.addBond(new AnyOrderQueryBond(ring[i],any,CDKConstants.BONDORDER_SINGLE));
        }
        
          return query;
    }
    
/*
 *    public static QueryAtomContainer aromaticAmine(String mark) {
        QueryAtomContainer query = new QueryAtomContainer() {
        	@Override
        	public String toString() {
        		return getID();
        	}
        	
        };
        query.setID(FunctionalGroups.AROMATIC_AMINE);
        IQueryAtom[] ring = new IQueryAtom[6];
        
        IAtom a  = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN);
        a.setFormalCharge(0);
        
        TopologySymbolQueryAtom n = new TopologySymbolQueryAtom(a,false);
        SubstituentExtractor.setSubstituentNumber(mark,n,new SubstituentPosition(1,false));
        n.setID("N");
        query.addAtom(n);
        //ring
        for (int i=0; i < 6; i++) {
        	//commented for test only
            ring[i] = new TopologySymbolQueryAtom(
            		MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON),true);

        	//ring[i] = new AnyAtom();ring[i].setSymbol(R);
            ring[i].setID(Integer.toString(i+1));
            SubstituentExtractor.setSubstituentNumber(mark,ring[i],new SubstituentPosition(i+1,true));
            //ring[i].setProperty(SubstituentExtractor._RING,SubstituentExtractor.yes);
            query.addAtom(ring[i]);
            if (i>0) {
                query.addBond(new AromaticQueryBond(ring[i], ring[i-1],CDKConstants.BONDORDER_SINGLE));
            }
        }
        query.addBond(new AromaticQueryBond(ring[0], ring[5],CDKConstants.BONDORDER_SINGLE));
        query.addBond(new OrderQueryBond(ring[0], n, CDKConstants.BONDORDER_SINGLE));
        
        AnyAtom r = new AnyAtom();r.setSymbol(R);r.setID("R1");
        
        query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));
        r = new AnyAtom();r.setSymbol(R);;r.setID("R2");
        query.addBond(new OrderQueryBond(r, n, CDKConstants.BONDORDER_SINGLE));        
        //substituents

        for (int i=1; i < 6; i++) {
            AnyAtom any = new AnyAtom(); any.setSymbol(R);
            SubstituentExtractor.setSubstituentNumber(mark,any,new SubstituentPosition(i+1,false));
            any.setID("Subst"+Integer.toString(i+1));
            query.addAtom(any);
            query.addBond(new AnyOrderQueryBond(ring[i],any,CDKConstants.BONDORDER_SINGLE));
        }
          return query;
    }
 */

}

interface ISubstituentAction<T> {
	T processSubstituent(IAtomContainer substituent,String mark, SubstituentPosition place, int atomIndex);
}

/**
 * want to return true ALWAYS
 * @author nina
 *
 */
class ReallyAnyAtom extends AnyAtom {
	/**
     * 
     */
    private static final long serialVersionUID = 2545315806695658613L;

	@Override
	public boolean matches(IAtom atom) {
		return true;
	}
}