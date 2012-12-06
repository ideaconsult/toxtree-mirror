/*
Copyright (C) 2005-2012  

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

package toxtree.plugins.ames.categories;


public class CategoryNonMutagen extends AmesMutagenicityCategory {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2260409185436019402L;

	public CategoryNonMutagen() {
		super("Unlikely to be a S. typhimurium TA100 mutagen based on QSAR",4,4);
		setExplanation("Assigned according to the output of <a href=\"#QSAR6\">QSAR6</a> or <a href=\"#QSAR13\">QSAR13</a>");
	}
}


