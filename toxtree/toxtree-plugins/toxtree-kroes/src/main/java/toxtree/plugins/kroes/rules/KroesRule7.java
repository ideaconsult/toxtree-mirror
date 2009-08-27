package toxtree.plugins.kroes.rules;


/**
 *Does estimated intake exceed TTC of 18\u00B5g/day ?
 * @author nina
 *
 */
public class KroesRule7 extends RuleVerifyIntake {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5853268905275906969L;
	public KroesRule7() {
		setID("Q7");
		setTitle("Does estimated intake exceed TTC of 18\u00B5g/day ?");
		setExplanation(getTitle());
		propertyStaticValue = 18;
	}

}
