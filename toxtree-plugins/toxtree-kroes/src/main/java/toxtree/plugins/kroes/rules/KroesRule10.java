package toxtree.plugins.kroes.rules;

import toxTree.tree.cramer.CramerClass2;

/**
 * 
 * @author nina
 * Is the compound in Cramer structural class II?
 */
public class KroesRule10 extends KroesRule8 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8716961767047489196L;
	public KroesRule10() {
		setID("Q10");
		setTitle("Is the compound in Cramer structural class II?");
		setExplanation(getTitle());
		category = new CramerClass2();
		examples[0]="O=C2OC(COc1ccccc1(OC))CN2";
		examples[1]="O=C1C=COC(=C1(O))CC";		
	}

}
