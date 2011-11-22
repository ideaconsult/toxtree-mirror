/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.rules;

import java.util.ArrayList;
import java.util.List;

import mutant.categories.CategoryError;
import mutant.descriptors.AromaticAmineSubstituentsDescriptor;
import mutant.descriptors.DescriptorIDist;

import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.exceptions.DRuleException;
import toxTree.qsar.IDescriptorPreprocessor;
import toxTree.qsar.LinearDiscriminantRule;
import toxTree.qsar.LinearPreprocessor;
import toxTree.qsar.LinearQSARModel;
import ambit2.base.exceptions.QSARModelException;
import ambit2.mopac.DescriptorMopacShell;
/**
 * Returns true if structure is predicted as mutagen , based on linear discriminant analysis. AM1
 * <pre>
 * w = - 2.85 HOMO + 1.84 LUMO + 0.70 MR2 + 0.69 MR3 + 1.90 MR6  +  3.36 Idist 
 * w >  25.04  non-mutagens
 * w <  25.04  mutagens
 * </pre>
 * 21 Sep 2007 - the equation was changed (RE Prof. R.Benigni) in order to use PM3 instead of AM1 calculations.
 * <pre>
 * w = - 3.14 HOMO + 1.76 LUMO + 0.62 MR2 + 0.75 MR3 + 1.88 MR6  +  3.75
> Idist     (8ter)
> w(mean,Class1) =  28.42       N1 = 47  (non-mutagens)
> w(mean,Class2) =  26.44       N2 = 64  (mutagens)
> Threshold =  27.43 
 * </pre>
 */
public class RuleDAMutagenicityAromaticAmines extends LinearDiscriminantRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2700747255861380207L;

	public RuleDAMutagenicityAromaticAmines() throws Exception {
		super(createMutagenicityofAromaticAminesModel(),27.43);
		setID("QSAR6");
		setTitle(model.getName());
        getModel().setPredictedproperty("w_QSAR6");
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<br>");
		b.append("w = -3.14 HOMO + 1.76 LUMO + 0.62 MR2 + 0.75 MR3 + 1.88 MR6  +  3.75 Idist");          
		b.append("<br>");
		b.append("w(mean,Class1) =  28.42       N1 = 47  (non-mutagens)");
		b.append("<br>");
		b.append("w(mean,Class2) =  26.44       N2 = 64  (mutagens)");
		b.append("<br>");
		b.append("Threshold =  27.43 If the outcome of the model is less than the threshold, returns YES, otherwise - NO.");
		b.append("<br>");

		b.append("The algorithm was generated through Canonical Discriminant Analysis.");
		b.append("W(mean, Class) is the average value for each of the two classes in the training set. When calculated for an individual chemical, W indicates to which class the chemical should be assigned, based on the Threshold (given above) that separates the two classes.");
		b.append("The model has  raw coefficients. In this way it can be applied directly to the descriptors, without any preliminary transformation of the values.");
		b.append("<br>");
		
		b.append("<b>Descriptors (total number = 6):</b>");
		b.append(hint());
		b.append("<ul>");
		b.append("<li>");
		b.append("The PM3 molecular orbital energies for the Highest Occupied Molecular Orbital (HOMO) and the Lowest Unoccupied Molecular orbital (LUMO) are given in eV.");
		b.append("<li>");
		b.append("MR2 , MR3 , MR6  are the MR contributions of substituents in position 2, 3, and 6 to the amino group.");
        b.append("a)The functional amino group is always in position 1, additional amino groups are treated as substituents.");
        b.append("b)If more than one amino group is present, the -N with lowest steric hindrance ( 1. primary amines; 2. secondary amines; 3. tertiary amines; in increasing steric hindrance order) is considered to be the functional group.");
        b.append("c)If more than one amino group is present, and all of them have the same steric hindrance on the -N,  the one that is considered to be the functional group is that which has a substituent in an adjacent position (ortho-substituent).");
        b.append("d)When the numbering can go in two opposite directions, the substituent position with highest steric hindrance is given the lowest substitution number.");
        b.append("e)If two amino groups in ortho position are present and there are other substituents attached to the ring, the amino group that is considered to be the functional group is that which allow to assign the minimum position number to the substituent.");
        b.append("f)In case the amino group is attached to more then one aromatic ring, the more extended aromatic system is to be considered as the one bearing the functional amino group;");
        b.append("gIn case the amino group is attached to more than one aromatic ring, with the aromatic systems equally extended,  the ring bearing the functional amino group is chosen in such a way that the sterimol is the highest possible");
		b.append("<li>");
        b.append("The indicator variable Idist is a structural parameter coding for the ");
        b.append("presence (Idist = 1, otherwise Idist = 0) of crowded substituents on the positions ");
        b.append("3'-, 4'- and 5'- of 4-aminobiphenyl. ");
        b.append("In particular, Methyl, Ethyl or Propyl substituents are not defined as ");
        b.append("crowded, whilst tri-substituted methyl and butyl (or bigger) substituents are defined ");
        b.append("as crowded substituents.");
        b.append("For the other functional groups, those who have Partial MR values >= 1.96 (butyl), should have Idist =1.");
        b.append("Please note that trifluoromethyl has Idist=1 irrespective of the MR value (0.5)");
/*  old explanation      
		b.append("The indicator variable Idist is a structural parameter coding for the presence (Idist = 1, otherwise Idist = 0) of substituents on the positions 3'-, 4'- and 5'- of 4-aminobiphenyl,");
		b.append("<br>and position 7- of 2-aminofluorene (e.g.: 4'-nButyl-4-aminobiphenyl; 4'-tButyl-4-aminobiphenyl;	4'-Trifluoromethyl-4-aminobiphenyl; 3'-Trifluoromethyl-4-aminobiphenyl).");
*/        
		b.append("</ul>");
		//[*]C1=CC(=CC([*])=C1([*]))C2=CC=C(N)C=C2  substituents on the positions 3-, 4- and 5- of 4-aminobiphenyl
		//[*]C1=CC=C3C(=C1)C=C2CC(N)C=CC23  subst at position 7- of 2-aminofluorene

		b.append("<b>References:</b>");
		b.append("<ul>");
		b.append("<li>");
		b.append("R. Benigni, C. Bossa, T. Netzeva, A. Rodomonte, and I. Tsakovska (2007) Mechanistic QSAR of aromatic amines: new models for discriminating between mutagens and nonmutagens, and validation of models for carcinogens. Environ mol mutag 48:754-771");
		b.append("</ul>");
        
        b.append("<br>Partial MR :");
        b.append("<ul>");
        b.append("<li>");
        b.append("C. Hansch, A. Leo and D. Hoeckman, (1995) Exploring QSAR, hydrophobic, electronic and steric constants, ACS, Washington DC");
        b.append("</ul>");
        
		b.append("</html>");
		setExplanation(b.toString());
		
		examples[0] = "C1=CC=C(C(=C1)C2=CC=CC=C2N)N";
		examples[1] = "C1=CC=C2C(=C1)C3=CC=CC4=C3C2=C(C=C4)N";	
	}

	public static LinearQSARModel createMutagenicityofAromaticAminesModel() throws Exception {
		String reason_notfound = "Most probably this substituent is not found in the current set of tabulated values (substituents.sdf)";
		String[][] nd = {
				{"EHOMO","PM3 molecular orbital energies for the Highest Occupied Molecular Orbital (HOMO)","Verify if OpenMopac is available and properly configured"},
				{"ELUMO","PM3 molecular orbital energies for the Lowest Unoccupied Molecular orbital (LUMO)","Verify if OpenMopac is available and properly configured"},
				{"MR2","MR2 is the MR contribution of substituents in position 2 to the amino group.",reason_notfound},
				{"MR3","MR3 is the MR contribution of substituents in position 3 to the amino group.",reason_notfound},
				{"MR6","MR6 is the MR contribution of substituents in position 6 to the amino group.",reason_notfound},				
				{"Idist","Indicator for crowded position","?"},
		};
		List<String> names = new ArrayList<String>();
		for (String[] n : nd) {
			names.add(n[0]);
			dictionary.put(n[0], n[1] + " not available. " + n[2]);
		}		

		AromaticAmineSubstituentsDescriptor mr = new AromaticAmineSubstituentsDescriptor();		
		List<IMolecularDescriptor> descriptors = new ArrayList<IMolecularDescriptor>();
		DescriptorMopacShell mopac = new DescriptorMopacShell();
		mopac.setErrorIfDisconnected(false);
		descriptors.add(mopac);
		descriptors.add(mopac);
		descriptors.add(mr);
		descriptors.add(mr);
		descriptors.add(mr);
		descriptors.add(new DescriptorIDist());
		
		String result = "W(QSAR6)" ;
		
		//double weights[] = new double[] {- 2.85,1.84,0.70, 0.69, 1.90, 3.36,0};
		double weights[] = new double[] {- 3.14,1.76,0.62, 0.75, 1.88, 3.75,0};
		LinearQSARModel model = new LinearQSARModel(names,descriptors,result,weights) {
			@Override
			protected void processNaNDescriptors(String name) throws QSARModelException {
				throw new QSARModelException(dictionary.get(name));
			}
			@Override
			protected void processUnavailableDescriptors(String name)
					throws QSARModelException {
				throw new QSARModelException(dictionary.get(name));
			}			
		};
		
/**
The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of w (thus, an intermediate step is necessary between calculation of the variables and calculation of w. The values to be shown are those corrected).  


homo_s = ehomo_tt * 1.03383 + 0.30348;
lumo_s = elumo_tt * 0.98963 - 0.04037;

The variables "_s" are the final (corrected) values of the variables; the variables "_tt" are the variables calculated by Toxtree.

 */		
		IDescriptorPreprocessor p = null;
		try {		
			p = new LinearPreprocessor(
					new double[] {1.03383,0.98963,1,1,1,1},
					new double[] {0.30348,- 0.04037,0,0,0,0}
					);
			((LinearPreprocessor)p).setExplanation("The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of W.");
		} catch (Exception x) {
			logger.error(x);
			p = null;
		}	
		model.setPreprocessor(p);
		model.setName("Mutagenic activity in Salmonella typhimurium TA100, with S9 metabolic activation; aromatic amines.");
		return model;
		
	}
	
	@Override
	protected boolean compare(double value, double threshold) {
		return value < threshold;
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("The answer is YES if the value calculated by the linear discriminant model is W < ");
		b.append(threshold);
		b.append("\n");
		b.append("W = ");
		b.append(model.toString());
		return b.toString();
	}
    @Override
    protected DRuleException createException(QSARModelException x) {
    	/*
    	IDecisionCategory c = new QSARApplicable(8);
    	c.setExplanation(x.getMessage());
        return new DRuleException(x,c,false);    	
        */
        return new DRuleException(x,new CategoryError("Error when processing "+getID() + ": " + x.getMessage()),false);
    	

    }    
}


