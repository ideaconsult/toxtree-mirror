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

import mutant.categories.QSARApplicable;
import mutant.descriptors.AromaticAmineSubstituentsDescriptor;
import mutant.descriptors.DescriptorBridgedBiphenyl;
import mutant.descriptors.DescriptorHasNO2Group;
import mutant.descriptors.DescriptorIsAniline;

import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.model.QSARModelException;

import toxTree.exceptions.DRuleException;
import toxTree.qsar.DescriptorMopacShell;
import toxTree.qsar.IDescriptorPreprocessor;
import toxTree.qsar.LinearDiscriminantRule;
import toxTree.qsar.LinearPreprocessor;
import toxTree.qsar.LinearQSARModel;

public class RuleDACancerogenicityAromaticAmines extends LinearDiscriminantRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2196148992556022225L;

	public RuleDACancerogenicityAromaticAmines() {
		super(createCancerogenicityAromaticAminesModel(),29.08);
		setID("QSAR8");
		setTitle(model.getName());
		getModel().setPredictedproperty("w_QSAR8");
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<b>");
		b.append("w = -3.79 L(R) + 3.52 B5(R) - 4.12 HOMO + 4.41 LUMO + 3.09 MR3 + 2.60 MR5 + 4.63 MR6 - 3.49 I(An) + 1.80 I(NO2) - 1.78 I(BiBr)");
		b.append("<br>");
		b.append("w(mean,Class1) = 27.82			N1 = 12  (non-carcinogens)");
		b.append("<br>");
		b.append("w(mean,Class2) = 30.34			N2 = 52  (carcinogens)");
		b.append("<br>");
		b.append("Threshold =  29.08");
		b.append("</b>");
        b.append(" If the outcome of the model is less than the threshold, returns YES, otherwise - NO.");
		b.append("<br>");
		b.append("The algorithm was generated through Canonical Discriminant Analysis. <br>	W(mean, Class) is the average value for each of the two classes in the training set. When calculated for an individual chemical, W indicates to which class the chemical should be assigned, based on the Threshold (given above) that separates the two classes."); 
		b.append("The model originally published contained standardized coefficients, so it could be applied only after standardization of the descriptors. The present model has been re-calculated, and has  raw coefficients. In this way it can be applied directly to the descriptors, without any preliminary transformation of the values."); 
		b.append("<br>");
		b.append("Descriptors (Total number = 10)");
		b.append(hint());
		b.append("<ul>");
		b.append("<li>");
		b.append("L(R ) (length) and B5( R) (maximal width) are Sterimol parameters (tabulated in (Verloop 1987)).");
		b.append("<li>");
		b.append("The PM3 (erroneously AM1 in the original paper) molecular orbital energies for the Highest Occupied Molecular Orbital (HOMO) and the Lowest Unoccupied Molecular orbital (LUMO) are given in eV.");
		b.append("<li>");
		b.append("MR3 , MR5 , MR6 are the MR contributions of substituents in position 3, 5, and 6 to the amino group.");
		b.append("<li>");
		b.append(" I(An), I(NO2) and I(BiBr) are indicator variables that take value = 1 for anilines, for the presence of a NO2 group, and for biphenyls with a bridge between the phenyl rings, respectively.");
		b.append("</ul>");
		
		b.append("<ul>");
		b.append("<br>References:");
		b.append("<ul>");
		b.append("<li>");
		b.append("Franke R., A. Gruska, A. Giuliani, and R. Benigni (2001) Prediction of rodent carcinogenicity of aromatic amines: a quantitative structure-activity relationships model. Carcinogenesis, 22: 1561-1571.");
		b.append("<li>");
		b.append("Benigni, R., Bossa, C., Netzeva, T. I., and Worth, A. P. (2007) Collection and evaluation of (Q)SAR models for mutagenicity and carcinogenicity. EUR - Scientific and Technical Research Series; EUR 22772 EN. Luxenbourg, Office for the Official Publications of the European Communities; pp 1 - 119.");
        b.append("</ul>");        
        b.append("<br>Partial MR and Sterimol descriptors:");
        b.append("<ul>");
        b.append("<li>");
        b.append("C. Hansch, A. Leo and D. Hoeckman, (1995) Exploring QSAR, hydrophobic, electronic and steric constants, ACS, Washington DC");
		b.append("</ul></html>");
		setExplanation(b.toString());
		examples[0] = "CC(C)OC(=O)NC1=CC(=CC=C1)Cl";
		examples[1] = "C1=CC=C2C=C(C=CC2=C1)N";
	}

	public static LinearQSARModel createCancerogenicityAromaticAminesModel() {
		List<String> names = new ArrayList<String>();

		names.add("LSTM1");  //L_sterimol of amine group substituent
		names.add("B5STM1"); //B5_sterimol of amine group substituent 		
		names.add("EHOMO");
		names.add("ELUMO");
		names.add("MR3");
		names.add("MR5");
		names.add("MR6");
		names.add("I(An)");
		names.add("I(NO2)");
		names.add("I(BiBr)");

		AromaticAmineSubstituentsDescriptor d = new AromaticAmineSubstituentsDescriptor();
		List<IMolecularDescriptor> descriptors = new ArrayList<IMolecularDescriptor>();

		descriptors.add(d);
		descriptors.add(d);
		try {
			DescriptorMopacShell mopac = new DescriptorMopacShell();		
			mopac.setErrorIfDisconnected(false);
			descriptors.add(mopac);
			descriptors.add(mopac);
		} catch (Exception x) {
			descriptors.add(null);
			descriptors.add(null);			
		}
		descriptors.add(d);
		descriptors.add(d);
		descriptors.add(d);
		descriptors.add(new DescriptorIsAniline());
		descriptors.add(new DescriptorHasNO2Group());
		descriptors.add(new DescriptorBridgedBiphenyl());		
		
		String result = "W(QSAR8)" ;

		double weights[] = new double[] {-3.79,+ 3.52, -4.12, +4.41, +3.09, + 2.60, +4.63,-3.49, +1.80, -1.78,0};
		LinearQSARModel model = new LinearQSARModel(names,descriptors,result,weights);
		
/**
The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of w (thus, an intermediate step is necessary between calculation of the variables and calculation of w. The values to be shown are those corrected).  

homos = ehomo_tt * 0.88239 - 1.0381;
lumos = elumo_tt * 0.96239 - 0.01521

The variables "_s" are the final (corrected) values of the variables; the variables "_tt" are the variables calculated by Toxtree.

 */		
		IDescriptorPreprocessor p = null;
		try {		
			p = new LinearPreprocessor(
					new double[] {1,1,0.88239,0.96239,1,1,1,1,1,1},
					new double[] {0,0,- 1.0381,- 0.01521,0,0,0,0,0,0}
					);
			((LinearPreprocessor)p).setExplanation("The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of W.");
			
		} catch (Exception x) {
			logger.error(x);
			p = null;
		}	
		model.setPreprocessor(p);
		model.setName("Carcinogenicity in rodents (mice, rats); aromatic amines");
		return model;
		
	}
	
	@Override
	protected boolean compare(double value, double threshold) {
		return value < threshold;
	}
    /**
Thus, if calculated w >29.08, then the substance should be carcinogen 
if calculated w< 29.08, then the substance should be noncarcinogen 
     */
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

        return new DRuleException(new QSARApplicable(8),false);
    }
}


