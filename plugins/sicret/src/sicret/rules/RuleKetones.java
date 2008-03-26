/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package sicret.rules;



import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import toxTree.tree.rules.smarts.SMARTSException;

/**
 * Ketones.<br>
 * SMARTS pattern  <ul>
 * <li>[#6][CX3](=[OX1])[#6]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleKetones extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleKetones() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[#6][CX3](=[OX1])[#6]");
			id = "51";
			title = "Ketones";
			
			examples[0] = "C=CC(=O)";
			examples[1] = "C=CC(=O)C";	
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}
