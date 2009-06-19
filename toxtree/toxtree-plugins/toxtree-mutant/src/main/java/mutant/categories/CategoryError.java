package mutant.categories;

public class CategoryError extends MutantCategory {
	public CategoryError() {
		this("Error when applying the decision tree");
	}
	public CategoryError(String explanation) {

		super(explanation,9,9);
        setExplanation(explanation);
	}	
}
