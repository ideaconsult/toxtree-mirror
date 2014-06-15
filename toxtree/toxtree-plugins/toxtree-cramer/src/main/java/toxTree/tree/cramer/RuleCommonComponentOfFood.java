/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleCommonComponentOfFood.java
 */
package toxTree.tree.cramer;

import java.io.File;

import toxTree.tree.rules.RuleStructuresList;

/**
 * Rule 22 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleCommonComponentOfFood extends RuleStructuresList {


    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7957714342855801107L;

	/**
	 * Constructor
	 * 
	 */
	public RuleCommonComponentOfFood() {
		 super("foodmol.sdf");
		 init();
	}
	public RuleCommonComponentOfFood(File file) {		
		super(file);
		init();

	}
	protected void init() {
		id = "22";
		title = "Common component of food";
		explanation.append("<html>Is the substance a <i>common component of food </i> (C) or <i>stucturally closed related</i> to a common component of food?");
		explanation.append("<p><i><b>(C)</b> Common component of food. In something as diverse , changing and occasionally uncertain");
		explanation.append(" as natural occurence, it is only possible to define a guideline, not a firm rule. ");
		explanation.append("For a decision tree, the term common component of food denotes a substance that has been reported ");
		explanation.append("in the recognised literature as occuring in significant quantity (approximately 50 ppm or more) ");
		explanation.append("in at least one major food, or in trace quantities at the ppm level or less in several foods, ");
		explanation.append("including minor or less frequently consumed foods. The latter include spices, herbs and ethnic specialities.");
		explanation.append("This definition excludes natural or man made contaminants and hormones.");
		explanation.append("</i>");
		if (isImplemented()) {
			explanation.append("<p><b>Note the answer of the question relies on an incomplete list of compounds, identified by an expert as a common component of food.");		
			explanation.append("If you believe a query compound is wrongly identfied as a such, or not recognised, ");
			explanation.append("please consult and/or update the list.<i>");
			explanation.append(getFile().getAbsolutePath());
			explanation.append("</i>");			
		} else {
			explanation.append("<p><b>Note the rule is not implemented and will answer <i>NO<i/> for every compound");			
		}
		explanation.append("</b></html>");
		
		
		examples[0] = "CNC1=CC=CC=C1C(=O)OC";
		examples[1] = "CCC1=C(O)C(=O)C=CO1";
		editable = false;
	}
}
