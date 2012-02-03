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
/**
 * <b>Filename</b> IDecisionCategories.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxTree.core;

import java.io.Serializable;
import java.util.List;

/**
 * A list of {@link toxTree.core.IDecisionCategory} instances
 * @author Nina Jeliazkova nina@acad.bg<br>
 * <b>Modified</b> 2005-8-8
 */
public interface IDecisionCategories extends  Serializable, List<IDecisionCategory> {
    IDecisionCategory getCategory(IDecisionCategory key);
    void addCategory(IDecisionCategory category);
	boolean isMultilabel();
	void setMultilabel(boolean multilabel);
    void setSelected(IDecisionCategory category);
    void selectAll(boolean selected);
    int size();
    void clear();

}

