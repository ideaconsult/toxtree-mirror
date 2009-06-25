/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package eye.rules;

public class RuleAqueousSolubility1 extends RuleAqueousSolubility {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9071883269025129927L;
	public RuleAqueousSolubility1()
	{
		this(AqueousSolubility,"g/l",condition_lower, 5.0E-6);
		
	}
	public RuleAqueousSolubility1(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		setID("5");
		setTitle(getPropertyName() + getCondition() + getProperty());
		
		examples[0] = "C1(C(N(C(=O)NC1=O)C)=O)(CC)CC";
		examples[1] = "c12c3c4ccc1c1c(cccc1)cc2ccc3ccc4";	
	}	
}


