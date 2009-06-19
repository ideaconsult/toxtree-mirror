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
 * <b>Filename</b> DecisionResultUIDException.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-5
 * <b>Project</b> toxTree
 */
package toxTree.exceptions;

import java.io.Serializable;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-5
 */
public class DecisionResultUIDException extends DecisionResultIOException {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -453946774551761094L;
    public static final String _mismatchMessage = "Serial Version UID mismatch! ";
    /**
     * 
     */
    public DecisionResultUIDException(Serializable object) {
        super( _mismatchMessage + object.getClass().getName());
    }

    /**
     * @param message
     */
    public DecisionResultUIDException(String message,Serializable object) {
        super( _mismatchMessage + object.getClass().getName() + message);
    }

    /**
     * @param cause
     */
    public DecisionResultUIDException(Throwable cause,Serializable object) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public DecisionResultUIDException(String message, Throwable cause,Serializable object) {
        super( _mismatchMessage + object.getClass().getName() + message, cause);
    }

}

