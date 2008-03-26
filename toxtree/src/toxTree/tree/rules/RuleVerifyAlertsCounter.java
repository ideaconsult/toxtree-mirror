/*
Copyright Ideaconsult Ltd. (C) 2005-2007
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DRulePropertyNotAvailable;
import toxTree.exceptions.DecisionMethodException;

public class RuleVerifyAlertsCounter extends RuleInitAlertCounter {

	/**
	 *
	 */
	private static final long serialVersionUID = -3620796942772067195L;

	public RuleVerifyAlertsCounter() {
		super();
		setID("Any alert?");
		setTitle("At least one alert fired?");
	}
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		logger.info(toString());
        try {
        	Object value = mol.getProperty(this.propertyName);

        	if ((value == null) || ("".equals(value)))
                throw new DRuleNotImplemented(propertyName + " not assigned ");

        	//Double.valueOf returns Double, perhaps Double.parseDouble() could be used
        	return this.compare(Double.valueOf(mol.getProperty(this.propertyName).toString()),
        				this.propertyStaticValue);

        } catch (NumberFormatException x) {
        	//just in case, the property might hold any value, or be empty
        	throw new DRulePropertyNotAvailable(propertyName,propertyName + " invalid value ",x);
        } catch (NullPointerException x) {
        	//or the getProperty might be null
        	throw new DRulePropertyNotAvailable(propertyName,propertyName + " not assigned ",x);
        }
	}


	@Override
	public boolean isImplemented() {
		return true;
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("The answer is YES if \"");
		b.append(RuleVerifyAlertsCounter.ALERTSCounter);
		b.append("\" property of the verified molecule is greater than zero.");
		return b.toString();
	}
}


