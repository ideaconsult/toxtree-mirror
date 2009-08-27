package toxtree.plugins.kroes.rules;


/**
 * 
 * @author nina
 *
 */
public class KroesRule9 extends RuleVerifyIntake {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9202526263556621253L;
	public KroesRule9() {
		setID("Q9");
		setTitle("Does estimated intake exceed 90 \u00B5g/day ?");
		setExplanation(getTitle());
		propertyStaticValue = 90;
	}


}
