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
package eye.rules;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;

/**
 * LogP > 9.0<br>
 * Expects property to be read from IMolecule.getProperty({@link #LogKow}).
 * If it is null, calculates LogP by {@link XLogPDescriptor}.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Vania Tsakovska
 * <b>Modified</b> Mar 31, 2008
 */
 
public class RuleLogP extends sicret.rules.RuleLogP 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7734658495811755955L;

	public RuleLogP()
	{
		this(LogKow,"",condition_higher,9.0);
		
	}
	public RuleLogP(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		id = "2";
		examples[0] = "N1([C@@H]2C(NC(=O)CC2)=O)C(c2ccccc2C1=O)=O";
		examples[1] = "C(CCCCCCCCCCCCCC)CCCCCCC(O)=O";		

	}

}
