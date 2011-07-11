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
package toxtree.plugins.verhaar2.categories;


import toxTree.tree.DefaultCategory;

/**
 * 
 * Class 3 (unspecific reactivity).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Class3UnspecificReactivity extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6264556087368900618L;

	/**
	 * 
	 */
	public Class3UnspecificReactivity() {
		super("Class 3 (unspecific reactivity)",3);
		setExplanation("<html><h3>Reactive chemicals:</h3>chemicals that react unselectively with biomolecules, <br>or substances that are bioactivated via metabolism");
	}

	/**
	 * @param name
	 * @param id
	 */
	public Class3UnspecificReactivity(String name, int id) {
		super(name, id);

	}

}
