package toxtree.plugins.smartcyp.rules;


public class SMARTCYPRuleHigherRank extends SMARTCYPRuleRank1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711634544165374724L;
	public SMARTCYPRuleHigherRank() {
		super(4);
		setExplanation(String.format("Rank >= %d",rank));	
		
	}
	protected boolean hasRank(int atom_rank) {
		return atom_rank>=getRank();
	}
}
