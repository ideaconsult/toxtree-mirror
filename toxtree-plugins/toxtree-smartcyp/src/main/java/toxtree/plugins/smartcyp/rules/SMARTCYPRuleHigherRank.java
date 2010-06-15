package toxtree.plugins.smartcyp.rules;


public class SMARTCYPRuleHigherRank extends SMARTCYPRuleRank1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711634544165374724L;
	public SMARTCYPRuleHigherRank() {
		super(3);
		setID(String.format("Rank>%d",rank));
		setTitle("SMARTCyp");
		setExplanation(String.format("Rank > %d",rank));		
	}
	protected boolean hasRank(int atom_rank) {
		return atom_rank>getRank();
	}
}
