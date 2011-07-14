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
package verhaar;


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
public class VerhaarScheme extends UserDefinedTree {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8432896817503373854L;
	

	public final static transient String[] c_rules_reversed = { 
		"verhaar.rules.Rule4", // 1

		"verhaar.rules.Rule01", // 2
		"verhaar.rules.RuleLogPRange",  // 3
		"verhaar.rules.Rule03", // 4

		"verhaar.rules.Rule31", // 5
		"verhaar.rules.Rule32", // 6
		"verhaar.rules.Rule33", // 7
		"verhaar.rules.Rule34", // 8
		"verhaar.rules.Rule35", // 9
		"verhaar.rules.Rule36", // 10
		"verhaar.rules.Rule37", // 11
		"verhaar.rules.Rule38", // 12

		"verhaar.rules.Rule21", // 13
		"verhaar.rules.Rule22", // 14
		"verhaar.rules.Rule23", // 15
		"verhaar.rules.Rule24", // 16
		"verhaar.rules.Rule25", // 17
		
		"verhaar.rules.Rule11", // 18
		"verhaar.rules.RuleIonicGroups", // 19
		"verhaar.rules.Rule13", // 20
		"verhaar.rules.Rule14", // 21
		"verhaar.rules.Rule141", // 22
		"verhaar.rules.Rule142", // 23
		"verhaar.rules.Rule143", // 24
		"verhaar.rules.Rule144", // 25
		"verhaar.rules.Rule15", // 26
		"verhaar.rules.Rule151", // 27
		"verhaar.rules.Rule152", // 28
		"verhaar.rules.Rule153", // 29
		"verhaar.rules.Rule154", // 30
		"verhaar.rules.Rule16", // 31
		"verhaar.rules.Rule161", // 32
		"verhaar.rules.Rule17", // 33
		"verhaar.rules.Rule171", // 34
	
	};		
	//reversed - first class 4, then class 3, then class 2 , then class 1, then class 5
	
	private final static transient int c_transitions_reversed[][] ={
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
	
	public final static transient String[] c_rules_normal = { 
		"verhaar.rules.Rule01", // 1
		"verhaar.rules.RuleLogPRange",  // 2
		"verhaar.rules.Rule03", // 3
		"verhaar.rules.Rule11", // 4
		"verhaar.rules.RuleIonicGroups", // 5
		"verhaar.rules.Rule13", // 6
		"verhaar.rules.Rule14", // 7
		"verhaar.rules.Rule141", // 8
		"verhaar.rules.Rule142", // 9
		"verhaar.rules.Rule143", // 10
		"verhaar.rules.Rule144", // 11
		"verhaar.rules.Rule15", // 12
		"verhaar.rules.Rule151", // 13
		"verhaar.rules.Rule152", // 14
		"verhaar.rules.Rule153", // 15
		"verhaar.rules.Rule154", // 16
		"verhaar.rules.Rule16", // 17
		"verhaar.rules.Rule161", // 18
		"verhaar.rules.Rule17", // 19
		"verhaar.rules.Rule171", // 20
		"verhaar.rules.Rule21", // 21
		"verhaar.rules.Rule22", // 22
		"verhaar.rules.Rule23", // 23
		"verhaar.rules.Rule24", // 24
		"verhaar.rules.Rule25", // 25
		"verhaar.rules.Rule31", // 26
		"verhaar.rules.Rule32", // 27
		"verhaar.rules.Rule33", // 28
		"verhaar.rules.Rule34", // 29
		"verhaar.rules.Rule35", // 30
		"verhaar.rules.Rule36", // 31
		"verhaar.rules.Rule37", // 32
		"verhaar.rules.Rule38", // 33
		"verhaar.rules.Rule4", // 34
	};	
	private final static transient int c_transitions_normal[][] ={
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
		{21,0,0,1},  // Q1.4.4 11
		{17,13,0,0},  // Q1.5 12
		{14,0,0,1},  // Q1.5.1 13
		{15,0,0,1},  // Q1.5.2 14
		{16,0,0,1},  // Q1.5.3 15
		{21,0,0,1},  // Q1.5.4 16
		{19,18,0,0},  // Q1.6 17
		{21,0,0,1},  // Q1.6.1 18
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
			"verhaar.categories.Class1BaselineToxicity",
			"verhaar.categories.Class2LessInertCompounds",
			"verhaar.categories.Class3UnspecificReactivity",
			"verhaar.categories.Class4SpecificMechanism",			
			"verhaar.categories.Class5Unknown",			
	};
	/**
	 * 
	 */
	public VerhaarScheme() throws DecisionMethodException {
		this(false);
	}
	public VerhaarScheme(boolean reversed) throws DecisionMethodException {
		super(new CategoriesList(c_categories),null);
		rules = reversed?
				new DecisionNodesList(categories,c_rules_reversed,c_transitions_reversed):
				new DecisionNodesList(categories,c_rules_normal,c_transitions_normal);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);		
		setTitle("Verhaar scheme");
		setExplanation("Verhaar H.J.M., Van Leeuven C., Hermens J.L.M.,Classifying Environmental Pollutants. 1: Structure-Activity Relationships for Prediction of Aquatic Toxicity, Chemosphere, Vol.25, No.4, pp.471-491, 1992.");
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
