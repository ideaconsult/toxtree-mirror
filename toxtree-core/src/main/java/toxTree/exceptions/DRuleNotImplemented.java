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
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.exceptions
 * Filename: DRuleNotImplemented.java
 */
package toxTree.exceptions;

import toxTree.core.IDecisionRule;


/**
 * Thrown when a rule is not implemented. ({@link IDecisionRule#isImplemented()} returns false.)
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class DRuleNotImplemented extends DecisionMethodException {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 9158149761604757898L;
    protected static final String _MESSAGE = "Rule not implemented!\t";
	/**
	 * Constructor
	 * 
	 */
	public DRuleNotImplemented() {
		this("");
	}

	/**
	 * Constructor
	 * @param message
	 */
	public DRuleNotImplemented(String message) {
		super(_MESSAGE + message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public DRuleNotImplemented(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public DRuleNotImplemented(String message, Throwable cause) {
		super(_MESSAGE + message, cause);
	}

}
