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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA1_gen extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public SA1_gen() {
		super();
		try {
			addSubstructure("Acyl halides", "[!$([OH1,SH1])]C(=O)[Br,Cl,F,I]");
			setID("SA1_Ames");
			setTitle("Acyl halides");
			setExplanation("Acyl halide RC(=O)[Br,Cl,F,I], where R is not OH or SH.");
			
			examples[0] = "O=C(Cl)S";
			examples[1] = "CN(C)C(=O)Cl";	
	
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}

}


