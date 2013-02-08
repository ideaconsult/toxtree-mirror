package toxtree.plugins.skinsensitisation.rules;

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlert;
import toxtree.plugins.skinsensitisation.categories.SkinSensitisationAlerts;
import ambit2.smarts.query.SMARTSException;

/**
 *  Parent class for all skin sens rules
 * @author nina
 *
 */
public class SkinSensitisationRule extends StructureAlert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1658487497286276204L;
	protected String shortName;
	public SkinSensitisationRule(SkinSensitisationAlerts alert) {
		super();
		try {
			
			//setID(Integer.toString(alert.ordinal()+1));
			setID(alert.getShortName());
			setTitle(alert.getTitle());
			setExplanation(alert.getExplanation());
			setContainsAllSubstructures(false);
			String[] smarts = alert.getSMARTS();
			for (int i=0; i < smarts.length;i++)
				addSubstructure(Integer.toString(i+1),smarts[i]);
			
			examples[0] = alert.getExample(false);
			examples[1] = alert.getExample(true);	
	
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}	

}
