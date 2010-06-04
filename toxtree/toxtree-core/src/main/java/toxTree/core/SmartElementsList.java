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

package toxTree.core;


import java.util.HashSet;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Same as HashSet, but understands "X" as halogen elements. 
 * <pre>
 * SmartElementsList list = new SmartElementsList();
 * list.add("X");
 * assertTrue(list.contains("Cl"));
 * assertTrue(list.contains("Br"));
 * assertTrue(list.contains("I"));
 * assertTrue(list.contains("F"));
 * </pre>
 * @author Nina Jeliazkova
 *
 */

public class SmartElementsList extends HashSet<String>   {

	public HashSet<String> halogens;
	public static String halogen = "X";
	public static String hydrogen = "H";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8123152913411974522L;

	public SmartElementsList() {
		super();
		halogens = new HashSet<String>();
		halogens.add("F");
		halogens.add("Cl");
		halogens.add("Br");
		halogens.add("I");

	}
	@Override
	public boolean contains(Object element) {
		if (halogens.contains(element))
		//for (int i=0; i < halogens.length; i++)
//			if (halogens[i].equals(element.toString()))
				return super.contains(halogen);
		return super.contains(element);
	}
	
	public void select(IAtomContainer mol, IAtomContainer atomcontainer, boolean found) {
		if (atomcontainer==null) return;
		for (int i=0; i < mol.getAtomCount();i++)
			if (!(contains(mol.getAtom(i).getSymbol()) ^ found))
				atomcontainer.addAtom(mol.getAtom(i));
			
	}
	
	@Override
	public boolean add(String element) {
		if (hydrogen.equals(element)) return true;
		if (halogens.contains(element))
		//for (int i=0; i < halogens.length; i++)
//			if (halogens[i].equals(element.toString())) {
				return super.add(halogen);
	//		}
		return super.add(element);
				
	}


	public HashSet<String> getHalogens() {
		return halogens;
	}
	public void setHalogens(HashSet<String> halogens) {
		this.halogens = halogens;
	}
	public void setHalogens(String[] halogens) {
		this.halogens.clear();
		for (int i=0; i < halogens.length; i++)
			this.halogens.add(halogens[i]);
	}
	public boolean hasOnlySpecifiedElements(SmartElementsList list) {
		return equals(list);
	}
	public boolean hasSpecifiedElements(SmartElementsList list) {
		return equals(list);
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof SmartElementsList) {
			return super.equals((SmartElementsList)o) && (halogens.equals(((SmartElementsList)o).halogens));
		} else return false;
		
	}

}


