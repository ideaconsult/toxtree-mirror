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

public class SA57_Ames extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public SA57_Ames() {
		super();
		try {
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2aaaac2aaa1");
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2A=AAc2aaa1");
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2AA=AAc2aaa1");
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2AA=Ac2aaa1");
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2Cc3aaaac3c2aaa1");
			addSubstructure("[NX3;!R][CX4;!R][CX4;!R][NX3;!R]Cc1c2c3aaaac3Cc2aaa1");
			
			setID("SA57_Ames");
			setTitle("DNA Intercalating Agents with a basic side chain");
			setExplanation("DNA intercalating agents are defined as those compounds that are able to insert partially or completely between adjacent DNA base pairs.");
				
			 examples[0] = "NCCNCc1ccccc1Cc2ccccc2";
	            examples[1] = "NCCNCc1c2ccccc2ccc1";   
	            editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}

}

//
