/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.test.query;

import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-24
 */
public class TestCasesFactory {

	/**
	 * 
	 */
	protected TestCasesFactory() {
		super();
		
	}
	public static IMolecule phenazineMethosulphate() 
	{
		  IMolecule mol = DefaultChemObjectBuilder.getInstance().newMolecule();
		  IAtom nq = DefaultChemObjectBuilder.getInstance().newAtom(Elements.NITROGEN);
		  nq.setFormalCharge(+1);
		  mol.addAtom(nq);
		  IAtom a2 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.SULFUR);
		  mol.addAtom(a2);
		  IAtom a3 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.NITROGEN);
		  mol.addAtom(a3);
		  IAtom a4 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a4);
		  IAtom a5 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a5);
		  IAtom a6 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a6);
		  IAtom a7 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a7);
		  IAtom o1 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN);
		  o1.setFormalCharge(-1);
		  mol.addAtom(o1);
		  IAtom a9 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN);
		  mol.addAtom(a9);
		  IAtom a10 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN);
		  mol.addAtom(a10);
		  IAtom a11 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN);
		  mol.addAtom(a11);
		  IAtom a12 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a12);
		  IAtom a13 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a13);
		  IAtom a14 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a14);
		  IAtom a15 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a15);
		  IAtom a16 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a16);
		  IAtom a17 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a17);
		  IAtom a18 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a18);
		  IAtom a19 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a19);
		  IAtom a20 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a20);
		  IAtom a21 = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
		  mol.addAtom(a21);
		  Bond b1 = new Bond(a3, a7, IBond.Order.SINGLE);
		  mol.addBond(b1);
		  Bond b2 = new Bond(a4, nq, IBond.Order.SINGLE);
		  mol.addBond(b2);
		  Bond b3 = new Bond(a5, nq, IBond.Order.DOUBLE);
		  mol.addBond(b3);
		  Bond b4 = new Bond(a6, a5, IBond.Order.SINGLE);
		  mol.addBond(b4);
		  Bond b5 = new Bond(a7, a4, IBond.Order.DOUBLE);
		  mol.addBond(b5);
		  Bond b6 = new Bond(o1, a2, IBond.Order.SINGLE);
		  mol.addBond(b6);
		  Bond b7 = new Bond(a9, a2, IBond.Order.DOUBLE);
		  mol.addBond(b7);
		  Bond b8 = new Bond(a10, a2, IBond.Order.DOUBLE);
		  mol.addBond(b8);
		  Bond b9 = new Bond(a11, a2, IBond.Order.SINGLE);
		  mol.addBond(b9);
		  Bond b10 = new Bond(a12, nq, IBond.Order.SINGLE);
		  mol.addBond(b10);
		  Bond b11 = new Bond(a13, a4, IBond.Order.SINGLE);
		  mol.addBond(b11);
		  Bond b12 = new Bond(a14, a5, IBond.Order.SINGLE);
		  mol.addBond(b12);
		  Bond b13 = new Bond(a15, a6, IBond.Order.SINGLE);
		  mol.addBond(b13);
		  Bond b14 = new Bond(a16, a7, IBond.Order.SINGLE);
		  mol.addBond(b14);
		  Bond b15 = new Bond(a17, a11, IBond.Order.SINGLE);
		  mol.addBond(b15);
		  Bond b16 = new Bond(a18, a13, IBond.Order.DOUBLE);
		  mol.addBond(b16);
		  Bond b17 = new Bond(a19, a14, IBond.Order.DOUBLE);
		  mol.addBond(b17);
		  Bond b18 = new Bond(a20, a19, IBond.Order.SINGLE);
		  mol.addBond(b18);
		  Bond b19 = new Bond(a21, a16, IBond.Order.DOUBLE);
		  mol.addBond(b19);
		  Bond b20 = new Bond(a6, a3, IBond.Order.DOUBLE);
		  mol.addBond(b20);
		  Bond b21 = new Bond(a18, a21, IBond.Order.SINGLE);
		  mol.addBond(b21);
		  Bond b22 = new Bond(a15, a20, IBond.Order.DOUBLE);
		  mol.addBond(b22);
		  return mol;
		}
	

}
