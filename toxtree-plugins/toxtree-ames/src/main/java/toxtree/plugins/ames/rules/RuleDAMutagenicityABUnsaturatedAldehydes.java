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

package toxtree.plugins.ames.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;

import toxTree.exceptions.DRuleException;
import toxTree.qsar.IDescriptorPreprocessor;
import toxTree.qsar.LinearDiscriminantRule;
import toxTree.qsar.LinearPreprocessor;
import toxTree.qsar.LinearQSARModel;
import toxtree.plugins.ames.categories.CategoryError;
import toxtree.plugins.ames.descriptors.DescriptorMolarRefractivity;
import ambit2.base.exceptions.QSARModelException;
import ambit2.mopac.DescriptorMopacShell;

/**
 * Returns true if structure is predicted as mutagen , based on linear discriminant analysis over descriptors MR, LogP, LUMO.
 * @author nina
 *
 */
public class RuleDAMutagenicityABUnsaturatedAldehydes extends LinearDiscriminantRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6335418026822816698L;

	public RuleDAMutagenicityABUnsaturatedAldehydes() {
		super(createMutagenicityofAldehydesModel(),8.032);
		setID("QSAR13");
		setTitle("Mutagenic activity in Salmonella typhimurium TA100;  \u03B1,\u03B2-unsaturated  aliphatic aldehydes.");
        getModel().setPredictedproperty("w_QSAR13");
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<br>");
		b.append("w =  0.387 MR - 3.12 logP +  3.23 LUMO");          
		b.append("<br>");
		b.append("w(mean,Class1)  = 9.689	    N1 = 3  (non-mutagens)");
		b.append("<br>");
		b.append("w(mean,Class2)  = 6.373	N2 = 17 (mutagens)");
		b.append("<br>");
		b.append("Threshold   = 8.032.  If the outcome of the model is less than the threshold, returns YES, otherwise - NO.");
		b.append("<br>");
		b.append("The algorithm was originally derived through Linear Discriminant Analysis. The present form is mathematically equivalent to the original one, and has been generated through Canonical Discriminant Analysis for the sake of simplicity and ease of implementation.");
		b.append("W(mean, Class) is the average value for each of the two classes in the training set. When calculated for an individual chemical, W indicates to which class the chemical should be assigned, based on the Threshold (given above) that separates the two classes.");
		b.append("<br>");
		b.append("<b>Descriptors (total number = 3):</b>");
		b.append(hint());
		b.append("<ul>");
		b.append("<li>");
		b.append("Molar Refractivity (MR)");
		b.append("<li>");
		b.append("Logarithm of the partition coefficient between octanol and water (logP)");
		b.append("<li>");
		b.append("Energy of the Lowest Unoccupied Molecular Orbital (LUMO)");
		b.append("</ul>");
 
		
		b.append("<b>References:</b>");
		b.append("<ul>");
		b.append("<li>");
		b.append("R. Benigni, L. Passerini, and A. Rodomonte (2003)  Structure-activity relationships for the mutagenicity and carcinogenicity of simple and \u03B1,-\u03B2-unsaturated aldehydes. Environ.Mol.Mutag., 42: 136-143.");
		b.append("<li>");
		b.append("R. Benigni, L. Conti, R. Crebelli, A. Rodomonte, and M. R. Vari (2005) Simple and \u03B1,\u03B2unsaturated aldehydes: correct prediction of genotoxic activity through  structure-activity relationship models. Environ.Mol.Mutagenesis, 46: 268-280.");
        b.append("</ul>");
        b.append("<b>LogP calculations:</b>");
        b.append("<ul>") ;       
        b.append("<li>");
        b.append("Wang R, Fu F and Lai L (1997) A new atom-additive method for calculating partition coefficients. J. Chem Inf Comput Sci 37:615-621.");
		b.append("</ul>");
        b.append("<b>MR calculations:</b>");
        b.append("<ul>") ;       
        b.append("<li>");
        b.append("Ghose A.K. and Crippen, G.M. (1987) Atomic Physicochemical Parameters for Three-Dimensional Structure-Directed Quantitative Structure-Activity Relationships 2. Modeling Dispersive and Hydrophobic Interactions J.Comput.Chem., 27:21-35");
        b.append("</ul>");
        
         
		//b.append(model.toString());
		b.append("</html>");
		setExplanation(b.toString());
		
		examples[0] = "O=C\\C=C\\c1ccccc1";
		examples[1] = "C=CC=O";

	}
	
	public static LinearQSARModel createMutagenicityofAldehydesModel() {
		List<String> names = new ArrayList<String>();
		names.add("MR");
		names.add("LogP");
		names.add("ELUMO");
		
		List<IMolecularDescriptor> descriptors = new ArrayList<IMolecularDescriptor>();
		descriptors.add(new DescriptorMolarRefractivity());
		XLogPDescriptor descriptor = new XLogPDescriptor();
		try {descriptor.setParameters(new Object[] {Boolean.TRUE});} catch (Exception x) {}
		descriptors.add(descriptor);
		
		try {
			DescriptorMopacShell mopac = new DescriptorMopacShell();
			mopac.setErrorIfDisconnected(false);
			descriptors.add(mopac);
		} catch (Exception x) {
			descriptors.add(null);			
		}		
	
		String result = "W(QSAR13)" ;
		//MR values are multipled by 10, according to the documentation ... why on earth not report MR coefficient as 3.87 ???
		double weights[] = new double[] {0.387,-3.12,3.23,0};
		LinearQSARModel model = new LinearQSARModel(names,descriptors,result,weights);
		IDescriptorPreprocessor p = null;
/**
The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of w (thus, an intermediate step is necessary between calculation of the variables and calculation of w. The values to be shown are those corrected).  

logp_s = 0.99738 * logp_tt_ - 0.10589 ;
lumo_s = 1.07907 * lumo_tt_ - 0.01463 ;
mr_s =   0.87180 * mr_tt_   - 2.3452 ;

 */
		try {		
			p = new LinearPreprocessor(
					new double[] {0.87180, 0.99738, 1.07907},
					new double[] {-2.3452, -0.10589, - 0.01463}
					);
			((LinearPreprocessor)p).setExplanation("The following are the correction factors to be applied to the Toxtree variables, before they are entered into the final equation for the calculation of W.");
		} catch (Exception x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
			p = null;
		}
		model.setPreprocessor(p);
		model.setName("Mutagenic activity in Salmonella typhimurium TA100");
		return model;
		
	}
	
	/**
	 * Classified as mutagens if lower than the threshold
	 */
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

//        return new DRuleException(x,new QSARApplicable(8),false);
        return new DRuleException(x,new CategoryError("Error when processing "+getID() + ": " + x.getMessage()),false);
        
    }    
}


