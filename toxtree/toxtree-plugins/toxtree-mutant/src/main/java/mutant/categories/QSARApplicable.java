/*
Copyright (C) 2005-2006  

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

package mutant.categories;


public class QSARApplicable extends MutantCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3498454991194849716L;
	

	public QSARApplicable() {
		super("For a better assessment a QSAR calculation could be applied.",8,7);
        setExplanation("Assigned when one of <a href=\"#QSAR6\">QSAR6</a>, <a href=\"#QSAR8\">QSAR8</a> or <a href=\"#QSAR13\">QSAR13</a> is applicable, but the user chooses not to apply a QSAR");
	}
    public QSARApplicable(int id) {
        this();
        setID(id);
    }
	public QSARApplicable(String name, int id) {
		this();
	}

}


