package toxtree.plugins.kroes.rules;


/**
 * 
 * @author nina
 * Does estimated intake exceed 540 \u00B5g/day ?
 */
public class KroesRule11 extends RuleVerifyIntake {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6146027047205787207L;
	public KroesRule11() {
		setID("Q11");
		setTitle("Does estimated intake exceed 540 \u00B5g/day ?");
		setExplanation(getTitle());
		propertyStaticValue = 540;
		
	}

}
