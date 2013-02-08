package toxtree.plugins.dnabinding.rules;

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlert;
import toxtree.plugins.dnabinding.categories.DNABindingAlerts;
import ambit2.smarts.query.SMARTSException;

/**
 *  Parent class for all skin sens rules
 * @author nina
 *
 */
public class DNABindingRule extends StructureAlert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1658487497286276204L;
	protected String shortName;
	public DNABindingRule(DNABindingAlerts alert) {
		super();
		try {
			
			//setID(Integer.toString(alert.ordinal()+1));
			setID(alert.getShortName());
			setTitle(alert.getTitle());
			setExplanation(alert.getExplanation());
			setContainsAllSubstructures(false);
			String[][] smarts = alert.getSMARTS();
			for (int i=0; i < smarts.length;i++)
				//title, smars
				addSubstructure(String.format("%s > %s",smarts[i][0],smarts[i][1]),smarts[i][2]);
			
			examples[0] = alert.getExample(false);
			examples[1] = alert.getExample(true);	
	
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}	

}
