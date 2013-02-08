/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.rules;

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA59_Ames extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public SA59_Ames() {
		super();
		try {
			//addSubstructure("1", "c1(=O)c3ccccc3(oc2ccccc12)");
			addSubstructure("2", "C1(=O)c3ccccc3(Sc2ccccc12)");
			addSubstructure("3", "C1(=O)c3ccccc3(Nc2ccccc12)");
			
			//addSubstructure("4", "C1(=O)C3C=CC=CC=3(OC2=C1C=CC=C2)");
			addSubstructure("1", "C1(=O)c3ccccc3(Oc2ccccc12)");
			setID("SA59_Ames");
			setTitle("Xanthones, Thioxanthones, Acridones");
			setExplanation("DNA intercalating agents are defined as those compounds that are able to insert partially or completely between adjacent DNA base pairs. Fused polycyclic chemicals are classical members of this class of compounds.");
			
			 examples[0] = "c1(=S)c3ccccc3(oc2ccccc12)";
	         examples[1] = "c1(=O)c3ccccc3(oc2ccccc12)";   //C1(=O)C3C=CC=CC3(NC2=CC=CC=C12)
			 //examples[1] = "C1(=O)C3C=CC=CC=3(OC2=C1C=CC=C2)"; 
	            editable = false;
	
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}

}


