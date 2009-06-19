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

*//**
 * Created on 2005-5-3

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleReadilyHydrolised29.java
 */
package toxTree.tree.cramer;


/**
 * Rule 29 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleReadilyHydrolysed29 extends RuleReadilyHydrolysedMononuclear {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4499063717714245662L;

    /**
	 * Constructor
	 * 
	 */
	public RuleReadilyHydrolysed29() {
		super();
		//this is almost the same as the parent class, but has some specifics (see Cramer paper)
		id = "29";
		explanation = new StringBuffer();
		explanation.append("<html>Is it <i>readily hydrolysed</i>(H) to mononuclear residues?");
		explanation.append("If YES, treat the individual aromatic mononuclear residues by Q.30 and any other residue by Q19.</html>");
		examples[0] = "O=C(NC1=CC=CC=C1)C2=CC=CC=C2";
		examples[1] = "O=C(OCCOC(=O)C1=CC=CC=C1)C2=CC=CC=C2";
		editable = false;
	}

}
