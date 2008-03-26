/*
Copyright Ideaconsult (C) 2005-2006  
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
package verhaar;


import java.util.Observable;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

/**
 * An implementaton of verhaar scheme for predicting toxicity classes TODO bring
 * it out of toxTree source tree and use as a module
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-14
 */
public class VerhaarScheme extends UserDefinedTree {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8432896817503373854L;
	public final static transient String[] c_rules = { 
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
	protected static String[] c_categories = {
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
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);		
		setTitle("Verhaar scheme");
		setExplanation("Verhaar H.J.M., Van Leeuven C., Hermens J.L.M.,Classifying Environmental Pollutants. 1: Structure-Activity Relationships for Prediction of Aquatic Toxicity, Chemosphere, Vol.25, No.4, pp.471-491, 1992.");
		setChanged();
		notifyObservers();
	}	

}
