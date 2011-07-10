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


import java.util.Observable;

import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

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
public class VerhaarScheme2 extends UserDefinedTree {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8432896817503373854L;
	public final static transient String[] c_rules = { 
		"toxtree.plugins.verhaar2.rules.Rule01", // 1
		"toxtree.plugins.verhaar2.rules.RuleLogPRange",  // 2
		"toxtree.plugins.verhaar2.rules.Rule03", // 3
		"toxtree.plugins.verhaar2.rules.Rule11", // 4
		"toxtree.plugins.verhaar2.rules.RuleIonicGroups", // 5
		"toxtree.plugins.verhaar2.rules.Rule13", // 6
		"toxtree.plugins.verhaar2.rules.Rule14", // 7
		"toxtree.plugins.verhaar2.rules.Rule141", // 8
		"toxtree.plugins.verhaar2.rules.Rule142", // 9
		"toxtree.plugins.verhaar2.rules.Rule143", // 10
		"toxtree.plugins.verhaar2.rules.Rule144", // 11
		"toxtree.plugins.verhaar2.rules.Rule15", // 12
		"toxtree.plugins.verhaar2.rules.Rule151", // 13
		"toxtree.plugins.verhaar2.rules.Rule152", // 14
		"toxtree.plugins.verhaar2.rules.Rule153", // 15
		"toxtree.plugins.verhaar2.rules.Rule154", // 16
		"toxtree.plugins.verhaar2.rules.Rule16", // 17
		"toxtree.plugins.verhaar2.rules.Rule161", // 18
		"toxtree.plugins.verhaar2.rules.Rule17", // 19
		"toxtree.plugins.verhaar2.rules.Rule171", // 20
		"toxtree.plugins.verhaar2.rules.Rule21", // 21
		"toxtree.plugins.verhaar2.rules.Rule22", // 22
		"toxtree.plugins.verhaar2.rules.Rule23", // 23
		"toxtree.plugins.verhaar2.rules.Rule24", // 24
		"toxtree.plugins.verhaar2.rules.Rule25", // 25
		"toxtree.plugins.verhaar2.rules.Rule31", // 26
		"toxtree.plugins.verhaar2.rules.Rule32", // 27
		"toxtree.plugins.verhaar2.rules.Rule33", // 28
		"toxtree.plugins.verhaar2.rules.Rule34", // 29
		"toxtree.plugins.verhaar2.rules.Rule35", // 30
		"toxtree.plugins.verhaar2.rules.Rule36", // 31
		"toxtree.plugins.verhaar2.rules.Rule37", // 32
		"toxtree.plugins.verhaar2.rules.Rule38", // 33
		"toxtree.plugins.verhaar2.rules.Rule4", // 34
	};	
	private final static transient int c_transitions[][] ={
		// {if no go to, if yes go to, assign if no, assign if yes}
		{34,2,0,0}, // Q0.1 1
		{34,3,0,0}, // Q0.2 2
		{34,4,0,0},  // Q0.3 3
		{34,5,0,0},  // Q1.1 4
		{34,6,0,0},  // Q1.2 5
		{7,0,0,1},  // Q1.3 6
		{12,8,0,0},  // Q1.4 7
		{9,0,0,1},  // Q1.4.1 8
		{10,0,0,1},  // Q1.4.2 9
		{11,0,0,1},  // Q1.4.3 10
		{12,0,0,1},  // Q1.4.4 11
		{17,13,0,0},  // Q1.5 12
		{14,0,0,1},  // Q1.5.1 13
		{15,0,0,1},  // Q1.5.2 14
		{16,0,0,1},  // Q1.5.3 15
		{17,0,0,1},  // Q1.5.4 16
		{19,18,0,0},  // Q1.6 17
		{19,0,0,1},  // Q1.6.1 18
		{21,20,0,0},  // Q1.7 19
		{21,0,0,1},  // Q1.7.1 20
		{22,0,0,2},  // Q2.1 21
		{23,0,0,2},  // Q2.2 22
		{24,0,0,2},  // Q2.3 23
		{25,0,0,2},  // Q2.4 24
		{26,0,0,2},  // Q2.5 25
		{27,0,0,3},  // Q3.1 26
		{28,0,0,3},  // Q3.2 27
		{29,0,0,3},  // Q3.3 28
		{30,0,0,3},  // Q3.4 29
		{31,0,0,3},  // Q3.5 30
		{32,0,0,3},  // Q3.6 31
		{33,0,0,3},  // Q3.7 32
		{34,0,0,3},  // Q3.8 33
		{0,0,5,4},  // Q4 34
		
	};	
	protected final static String[] c_categories = {
			"toxtree.plugins.verhaar2.categories.Class1BaselineToxicity",
			"toxtree.plugins.verhaar2.categories.Class2LessInertCompounds",
			"toxtree.plugins.verhaar2.categories.Class3UnspecificReactivity",
			"toxtree.plugins.verhaar2.categories.Class4SpecificMechanism",			
			"toxtree.plugins.verhaar2.categories.Class5Unknown",			
	};
	/**
	 * 
	 */
	public VerhaarScheme2() throws DecisionMethodException {
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);		
		setTitle("Verhaar scheme (Modified)");
		setExplanation("S.J. Enoch, M. Hewitt, M.T.D. Cronin, S. Azam, J.C. Madden, Classification of chemicals according to mechanism of aquatic toxicity: An evaluation of the implementation of the Verhaar scheme in Toxtree, Chemosphere 73 (2008) 243–248"	);
		setChanged();
		notifyObservers();
        setPriority(10);
	}	
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/verhaar.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}  
}
