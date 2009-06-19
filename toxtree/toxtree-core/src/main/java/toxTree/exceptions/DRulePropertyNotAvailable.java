/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

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


package toxTree.exceptions;

public class DRulePropertyNotAvailable extends DRuleNotImplemented {
    protected String propertyName = "";
    /**
     * 
     */
    private static final long serialVersionUID = 883553923579355912L;
    /**
     * Constructor
     * 
     */
    public DRulePropertyNotAvailable(String propertyname) {
        this("","");
    }

    /**
     * Constructor
     * @param message
     */
    public DRulePropertyNotAvailable(String propertyname,String message) {
        super(_MESSAGE + message);
        setPropertyName(propertyName);
    }

    /**
     * Constructor
     * @param cause
     */
    public DRulePropertyNotAvailable(String propertyname,Throwable cause) {
        super(cause);
        setPropertyName(propertyName);
    }

    /**
     * Constructor
     * @param message
     * @param cause
     */
    public DRulePropertyNotAvailable(String propertyname,String message, Throwable cause) {
        super(_MESSAGE + message, cause);
        setPropertyName(propertyName);
    }

    public synchronized String getPropertyName() {
        return propertyName;
    }

    public synchronized void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}


