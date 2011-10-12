package toxtree.plugins.moa;

import toxTree.core.IDecisionInteractive;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;

public class MOARules extends UserDefinedTree  implements IDecisionInteractive{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7898289602233747585L; 

	static enum ruleNames {
		RuleAlertsNarcosis1_1_1 {
			@Override
			ruleNames ifNegativeGoTo() {
				return RuleAlertsNarcosis1_1_2_1;
			}
		},
		RuleAlertsNarcosis1_1_1_1 {
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.Narcosis1;
			}	
		},
		RuleAlertsNarcosis1_1_2 {
			
		},
		RuleAlertsNarcosis1_1_2_1 {
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.Narcosis1;
			}				
		},
		

		RuleAlertsNarcosis2_2_1,
		VerifyAlertsNarcosis2 {
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}			
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.Narcosis2;
			}
		},
		RuleAlertsNarcosis3,
		VerifyAlertsNarcosis3 {
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}			
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.Narcosis3;
			}				
		},
		RuleAlertsUncouplers,
		VerifyAlertsUncouplers {
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}			
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.OxidativePhosphorylationUncouplers;
			}				
		},
		RuleAlertsReactive,
		VerifyAlertsReactive {
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}			
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.ElectrophileProelectrophileReactivity;
			}				
		},
		RuleAlertsAche,
		VerifyAlertsAche {
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}			
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.AcetylcholinesteraseInhibitors;
			}				
		},
		RuleAlertsCNS,
		VerifyAlertsCNS {
			@Override
			categoryNames ifPositiveAssign() {
				return categoryNames.CentralNervousSystemSeizureAgents;
			}		
			@Override
			categoryNames ifNegativeAssign() {
				return categoryNames.Unknown;
			}
			@Override
			ruleNames ifNegativeGoTo() {
				return null;
			}
			@Override
			ruleNames ifPositiveGoTo() {
				return null;
			}
		};
		
		ruleNames ifNegativeGoTo() { return values()[ordinal()+1]; } //next node
		ruleNames ifPositiveGoTo() { return null; } //next node
		categoryNames ifNegativeAssign() { return null; }
		categoryNames ifPositiveAssign() { return null; }			
	}
	
	static enum categoryNames {
		Narcosis1,
		Narcosis2,
		Narcosis3,
		OxidativePhosphorylationUncouplers,
		ElectrophileProelectrophileReactivity,
		AcetylcholinesteraseInhibitors,	
		CentralNervousSystemSeizureAgents,
		Unknown
	}	
	
	  private final static transient int c_transitions[][] ={
          //{if no go to, if yes go to, assign if no, assign if yes}
          {2,2,0,0}, //Rule 1  1
          {3,3,0,0}, //sa1 2
          {4,4,0,0}, //sa2 3
          {5,5,0,0}, //sa3  4
	  }   ;
    public static int[][] createTransitions() { 
			int n = ruleNames.values().length;
			 int[][] c_transitions = new int[n][4];
			 for (ruleNames rule: ruleNames.values()) {
				 c_transitions[rule.ordinal()] = new int[] {
				     rule.ifNegativeGoTo()==null?0:rule.ifNegativeGoTo().ordinal()+1,
				     rule.ifPositiveGoTo()==null?0:rule.ifPositiveGoTo().ordinal()+1,
				     rule.ifNegativeAssign()==null?0:rule.ifNegativeAssign().ordinal()+1,
				     rule.ifPositiveAssign()==null?0:rule.ifPositiveAssign().ordinal()+1,
				 };
			 }
			 return c_transitions;
	};
	    
	public static String[] createRules() { 
		int n = ruleNames.values().length;
		 String[] c_rules = new String[n];
		 for (int i=0; i < n; i++) {
			 c_rules[i] = String.format("toxtree.plugins.moa.rules.%s",ruleNames.values()[i].name());
		 }
		 return c_rules;
    };	
	public static String[] createCategories() { 
		int n = categoryNames.values().length;
		 String[] c_rules = new String[n];
		 for (int i=0; i < n; i++) {
			 c_rules[i] = String.format("toxtree.plugins.moa.categories.Category%s",categoryNames.values()[i].name());
		 }
		 return c_rules;
    };	    
	
	public MOARules() throws DecisionMethodException {
		super(new CategoriesList(createCategories(),true),createRules(),createTransitions(),new DecisionNodesFactory(true));
        //getRule(53).setID("SA10a");
		setChanged();
		notifyObservers();
		setTitle("Toxicity Mode of Actions");
        setPriority(15);
        setFalseIfRuleNotImplemented(false);
        
        //setFalseIfRuleNotImplemented(false); //this will cause exception if an error occurs in a rule
	}	
}
