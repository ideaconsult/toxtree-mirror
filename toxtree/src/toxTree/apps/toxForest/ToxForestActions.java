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
package toxTree.apps.toxForest;

import javax.swing.JFrame;

import toxTree.data.ActionList;

/**
 * Toxforest actions.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 16, 2006
 */
public class ToxForestActions extends ActionList {
	public static final String _aSkipNotImplemented = "Skip not implemented ruiles";
	public static final String _FileAction = "File";
	public static final String _EditAction = "Edit";
	public static final String _CompoundAction = "Chemical Compounds";
	public static final String _HazardAction = "Toxic Hazard";	
	public static final String _aEditMethod = "Edit decision tree";    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1507146195264700129L;

	public ToxForestActions() {
		super();
	}

	public ToxForestActions(JFrame mainFrame) {
		super(mainFrame);
	}

}
