/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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
package verhaar.rules;


import toxTree.tree.rules.StructureAlert;

/**
 * 
 * Compounds acting by a specific mechanism. 
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule4 extends StructureAlert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5579727497271403749L;

	/**
Finally both databases contain several groups of
chemicals which should fall into Verhaar class 4 (chemicals acting
via a specific (non-covalent) mechanism); typically these include
the weak acid respiratory uncouplers.
	 */
	public Rule4() {
		super();
		id = "4";
		setTitle("Compounds acting by a specific mechanism");
		explanation.append("It is not possible to give definitive structural rules for this class.");
		explanation.append("Inclusion in this class must and should, be based on specific knowledge on ");
		explanation.append("mode of toxic actions of (groups of) chemicals. Examples of groups of compounds that ");
		explanation.append("are known to act by a specific mode of toxic action are ");
		explanation.append("<UL>");
		explanation.append("<LI>DDT and analogues");
		explanation.append("<LI>(dithio) carbamates");
		explanation.append("<LI>organotin compounds");
		explanation.append("<LI>pyrethroids");
		explanation.append("<LI>organophosphorothionate esters");
		explanation.append("</UL>");
		explanation.append("Compounds that cannot be classified as belonging to class 1,2 or 3 and that are not known ");
		explanation.append("to be compounds acting by a specific mechanism can only be classified as ");
		explanation.append("\"not possible to classify according to these rules\"");
		editable = false;
        setContainsAllSubstructures(false);
        try {
            super.initSingleSMARTS(super.smartsPatterns,"organotin", "C[Sn]");
            super.initSingleSMARTS(super.smartsPatterns,"DDT and analogues", "c1ccc(cc1)C(c2ccccc2)=,-C(Cl)Cl");
            super.initSingleSMARTS(super.smartsPatterns,"DDT and analogues1", "[R](Cl)[R][R](Cl)[R][R](Cl)");
            super.initSingleSMARTS(super.smartsPatterns,"DDT and analogues2", "Claa-aaCl");
            super.initSingleSMARTS(super.smartsPatterns,"pyrethroids", "O=C(O)C1C(C=C)C1(C)(C)");
            
            super.initSingleSMARTS(super.smartsPatterns,"carbamates", "OC(=O)[NX3]");
            
            super.initSingleSMARTS(super.smartsPatterns,"monothiocarbamates", "[NX3]C(=O)S");
            super.initSingleSMARTS(super.smartsPatterns,"dithiocarbamates", "[NX3]C(=S)S");
            super.initSingleSMARTS(super.smartsPatterns,"triphenyl phosphate", "O=P(Oc1ccccc1)(Oc2ccccc2)Oc3ccccc3");
            super.initSingleSMARTS(super.smartsPatterns,"organophosphorothionate esters", "P(O)(O)(O)(=S)");
            super.initSingleSMARTS(super.smartsPatterns,"organophosphorothionate", "P(O)(O)(S)(=S)");
            super.initSingleSMARTS(super.smartsPatterns,"organophosphate1", "P(O)(C)(S)(=S)");
            

            
        } catch (Exception x) {
            logger.error(x);
        }
        setExamples(new String[] {"NCS","Clc1ccc(cc1)C(c2ccc(Cl)cc2)C(Cl)(Cl)Cl"});
	}

	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}

}
