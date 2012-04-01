package toxtree.plugins.smartcyp.rules;


public class SMARTCYPRuleHigherRank extends SMARTCYPRuleRank1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711634544165374724L;
	public SMARTCYPRuleHigherRank() {
		super(4);
		setExplanation(String.format("Rank >= %d",rank));	
		examples[0] = "C1C=CNC=C1";
		examples[1] = "O=[N+]([O-])c4ccc1ccc2cccc3ccc4(c1c23)";
		
	}
	protected boolean hasRank(int atom_rank) {
		return atom_rank>=getRank();
	}
}
