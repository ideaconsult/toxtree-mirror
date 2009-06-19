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
package toxTree.query;

import java.util.Collection;

import toxTree.data.ListOfAtomContainers;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-17
 */
public class QueryAtomContainers extends ListOfAtomContainers {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3659124291428478506L;

	/**
	 * @param initialCapacity
	 */
	public QueryAtomContainers(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 
	 */
	public QueryAtomContainers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public QueryAtomContainers(Collection c) {
		super(c);

	}
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return super.containsAll(c);
	}
	/* (non-Javadoc)
	 * @see java.util.ArrayList#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object elem) {
		// TODO Auto-generated method stub
		return super.contains(elem);
	}
}
