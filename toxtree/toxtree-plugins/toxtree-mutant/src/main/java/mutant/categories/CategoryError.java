
package mutant.categories;

public class CategoryError extends MutantCategory {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8788740394316042120L;
	public CategoryError() {
		this("Error when applying the decision tree");
	}
	public CategoryError(String explanation) {

		super(explanation,10,10);
        setExplanation(explanation);
	}	

}
