/*
Copyright Ideaconsult Ltd (C) 2005-2011 
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
package verhaar.categories;


import toxTree.tree.DefaultCategory;

/**
 * 
 * Class 4 (compounds and groups of compounds acting by a specific mechanism).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Class4SpecificMechanism extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -4846111390893325568L;

	/**
	 * 
	 */
	public Class4SpecificMechanism() {
		super("Class 4 (compounds and groups of compounds acting by a specific mechanism)",4);
		setExplanation("<html><h3>Specifically acting chemicals:</h3> chemicals that interact with receptor biomolecules. <br>Examples : <ul><li>DDT and analogues<li>(dithio) carbamates<li>organotin compounds<li>pyrethroids<li>organophosphorothionate esters</ul></html>");
		setThreshold("");
	}

	/**
	 * @param name
	 * @param id
	 */
	public Class4SpecificMechanism(String name, int id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

}
