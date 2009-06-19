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
package toxTree.exceptions;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-24
 */
public class MolAnalyseException extends Exception {
	protected IAtomContainer atomContainer = null;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 9169305769013001316L;

	/**
	 * 
	 */
	public MolAnalyseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MolAnalyseException(String message) {
		super(message);
	}
	public MolAnalyseException(String message,IAtomContainer mol) {
		super(message);
		this.atomContainer = mol;
	}

	/**
	 * @param cause
	 */
	public MolAnalyseException(Throwable cause) {
		super(cause);
	}

	public MolAnalyseException(Throwable cause, IAtomContainer mol) {
		super(cause);
		this.atomContainer = mol;
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public MolAnalyseException(String message, Throwable cause) {
		super(message, cause);
	}
	public MolAnalyseException(String message, Throwable cause, IAtomContainer mol) {
		super(message, cause);
		this.atomContainer = mol;		
	}

	/**
	 * @return Returns the atomContainer.
	 */
	public synchronized IAtomContainer getAtomContainer() {
		return atomContainer;
	}
	/**
	 * @param atomContainer The atomContainer to set.
	 */
	public synchronized void setAtomContainer(IAtomContainer atomContainer) {
		this.atomContainer = atomContainer;
	}
}
