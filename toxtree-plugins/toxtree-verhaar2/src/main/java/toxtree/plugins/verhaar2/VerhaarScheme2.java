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
		"toxtree.plugins.verhaar2.rules.Rule4", // 1

		"toxtree.plugins.verhaar2.rules.Rule01", // 2
		"toxtree.plugins.verhaar2.rules.RuleLogPRange",  // 3
		"toxtree.plugins.verhaar2.rules.Rule03", // 4

		"toxtree.plugins.verhaar2.rules.Rule31", // 5
		"toxtree.plugins.verhaar2.rules.Rule32", // 6
		"toxtree.plugins.verhaar2.rules.Rule33", // 7
		"toxtree.plugins.verhaar2.rules.Rule34", // 8
		"toxtree.plugins.verhaar2.rules.Rule35", // 9
		"toxtree.plugins.verhaar2.rules.Rule36", // 10
		"toxtree.plugins.verhaar2.rules.Rule37", // 11
		"toxtree.plugins.verhaar2.rules.Rule38", // 12

		"toxtree.plugins.verhaar2.rules.Rule21", // 13
		"toxtree.plugins.verhaar2.rules.Rule22", // 14
		"toxtree.plugins.verhaar2.rules.Rule23", // 15
		"toxtree.plugins.verhaar2.rules.Rule24", // 16
		"toxtree.plugins.verhaar2.rules.Rule25", // 17
		
		"toxtree.plugins.verhaar2.rules.Rule11", // 18
		"toxtree.plugins.verhaar2.rules.RuleIonicGroups", // 19
		"toxtree.plugins.verhaar2.rules.Rule13", // 20
		"toxtree.plugins.verhaar2.rules.Rule14", // 21
		"toxtree.plugins.verhaar2.rules.Rule141", // 22
		"toxtree.plugins.verhaar2.rules.Rule142", // 23
		"toxtree.plugins.verhaar2.rules.Rule143", // 24
		"toxtree.plugins.verhaar2.rules.Rule144", // 25
		"toxtree.plugins.verhaar2.rules.Rule15", // 26
		"toxtree.plugins.verhaar2.rules.Rule151", // 27
		"toxtree.plugins.verhaar2.rules.Rule152", // 28
		"toxtree.plugins.verhaar2.rules.Rule153", // 29
		"toxtree.plugins.verhaar2.rules.Rule154", // 30
		"toxtree.plugins.verhaar2.rules.Rule16", // 31
		"toxtree.plugins.verhaar2.rules.Rule161", // 32
		"toxtree.plugins.verhaar2.rules.Rule17", // 33
		"toxtree.plugins.verhaar2.rules.Rule171", // 34
	
	};		
	//reversed - first class 4, then class 3, then class 2 , then class 1, then class 5
	
	private final static transient int c_transitions[][] ={
		// {if no go to, if yes go to, assign if no, assign if yes}
		{2,0,0,4}, 	//Rule 4  1
		
		//Class 1,2,3 precondition
		{0,3,5,0},  //Rule01 2
		{0,4,5,0},  //RuleLogPRange 3
		{0,5,5,0},  //Rule03 4
		
		//Rulebase Class 3  
		{6,0,0,3},  //q31  5
		{7,0,0,3},  //q32  6
		{8,0,0,3},  //q33  7
		{9,0,0,3},  //q34  8
		{10,0,0,3},  //q35  9
		{11,0,0,3},  //q36  10
		{12,0,0,3}, //q37  11
		{13,0,0,3}, //q38 12

		//Rules Class 2  
		{14,0,0,2},  //q21  13
		{15,0,0,2},  //q22 14
		{16,0,0,2},  //q23 15
		{17,0,0,2},  //q24 16
		{18,0,0,2},  //q25 17
		
		//Rules Class 1


		{0,19,5,0},  //Rule11 18
		{0,20,5,0},  //RuleIonicGroup  19
		{21,0,0,1},  //Rule13 20
		{26,22,0,0},  //Rule14 21
		{23,0,0,1},  //Rule141 22
		{24,0,0,1},  //Rule142 23
		{25,0,0,1},  //Rule143 24
		{0,0,5,1},  //Rule144 25
		{31,27,0,0},  //Rule15 26
		{28,0,0,1},  //Rule151 27
		{29,0,0,1},  //Rule152 28
		{30,0,0,1},  //Rule153 29
		{0,0,5,1},  //Rule154 30
		{33,32,0,0},  //Rule16 31
		{0,0,5,1},  //Rule161 32
		{0,34,5,0},  //Rule17 33
		{0,0,5,1},  //Rule171 34
		
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
