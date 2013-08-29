package toxtree.plugins.ames.categories;


public class CategoryError extends AmesMutagenicityCategory {
	public CategoryError() {
		this("Error when applying the decision tree");
	}
	public CategoryError(String explanation) {

		super(explanation,10,10);
        setExplanation(explanation);
	}	
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.InconclusiveCategory;
	}
}
