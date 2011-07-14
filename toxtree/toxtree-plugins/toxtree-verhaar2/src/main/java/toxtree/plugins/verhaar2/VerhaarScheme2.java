/*
Copyright Ideaconsult Ltd (C) 2005-2011 
Contact: jeliazkova.nina@gmail.com

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
package toxtree.plugins.verhaar2;


import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.exceptions.DecisionMethodException;
import verhaar.VerhaarScheme;

/**
 * An implementaton of Verhaar scheme for predicting toxicity mode of action.
 * <ul>
 * <li>Verhaar HJM, van Leeuwen CJ and Hermens JLM (1992) Classifying environmental pollutants. 1. Structure-activity relationships for prediction of aquatic toxicity. Chemosphere 25, 471-491. 
 * <li>Verhaar HJM, Mulder W and Hermens JLM (1995) QSARs for ecotoxicity. In: Overview of structure-activity relationships for environmental endpoints, Part 1: General outline and procedure. Hermens JLM (Ed), Report prepared within the framework of the project "QSAR for Prediction of Fate and Effects of Chemicals in the Environment", an international project of the Environment; Technologies RTD Programme (DGXII/D-1) of the Europenan Commission under contract number EV5V-CT92-0211. 
 * <li>Verhaar HJM, Solbe J, Speksnijder J, van Leeuwen CJ and Hermens JLM (2000) Classifying environmental pollutants: Part 3. External validation of the classification system. Chemosphere 40, 875-883. 
 * </ul>
 * Updated according to recommendation in
 * <ul> 
 * <li>S.J. Enoch, M. Hewitt, M.T.D. Cronin, S. Azam, J.C. Madden, Classification of chemicals according to mechanism of aquatic toxicity:
 * An evaluation of the implementation of the Verhaar scheme in Toxtree, Chemosphere 73 (2008) 243–248
 * </ul> 
 * <p> Rules: 
 * <ul>
 * <li>LogP limits
 * <li>structural rules 
 * <li>implementation in {@link verhaar.rules} package
 * </ul>
 * <p>Categories:
 * <ul>
 * <ul>
 * <li>implementation in {@link verhaar.categories} package
 * </ul>
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-14
 */
public class VerhaarScheme2 extends VerhaarScheme {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8432896817503373854L;

	/**
	 * 
	 */
	public VerhaarScheme2() throws DecisionMethodException {
		this(true);
	}
	public VerhaarScheme2(boolean reversed) throws DecisionMethodException {
		super(reversed);	
		setTitle("Verhaar scheme (Modified)");
		setExplanation("S.J. Enoch, M. Hewitt, M.T.D. Cronin, S. Azam, J.C. Madden, Classification of chemicals according to mechanism of aquatic toxicity: An evaluation of the implementation of the Verhaar scheme in Toxtree, Chemosphere 73 (2008) 243–248"	);
		setChanged();
		notifyObservers();
        setPriority(11);
	}	
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/verhaar2.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}  
}
